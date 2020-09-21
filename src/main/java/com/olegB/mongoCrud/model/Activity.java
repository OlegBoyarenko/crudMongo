package com.olegB.mongoCrud.model;

import com.olegB.mongoCrud.exeption.ActivityIsEqualsException;
import com.olegB.mongoCrud.history.Changes;
import com.olegB.mongoCrud.history.Type;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Entity used to store activities in the mongo database.
 * Contains a embedded History entity
 */
@Document(collection = "activity")
public class Activity {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    public Long id;

    @NotBlank
    @Size(max=100, message = "max 100 char available")
    public String title;

    @NotBlank
    @Size(max=25, message = "max 25 char available")
    public String summary;

    @NotBlank
    @Size(max=200, message = "max 200 char available")
    public String description;

    @NotBlank
    public String startDateTime;
    @NotBlank
    public String endDateTime;

    public String info;
    public List<History> history = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history.add(history);
    }

    public Activity() {
    }

//    public void setupChanges(Activity activityDetails) throws ActivityIsEqualsException {
//        List<Changes> changes = new ArrayList<>();
//        if (this.equals(activityDetails)) {
//            throw new ActivityIsEqualsException("Activity is equals");
//        } else if (activityDetails.getTitle() != null && !activityDetails.getTitle().equals(this.getTitle())) {
//            changes.add(new Changes("Title", this.getTitle(), activityDetails.getTitle()));
//            this.setTitle(activityDetails.getTitle());
//        } if (activityDetails.getDescription() != null && !activityDetails.getDescription().equals(this.getDescription())) {
//            changes.add(new Changes("Description", this.getDescription(), activityDetails.getDescription()));
//            this.setDescription(activityDetails.getDescription());
//        } if (activityDetails.getSummary() != null && !activityDetails.getSummary().equals(this.getSummary())) {
//            changes.add(new Changes("Summary", this.getSummary(), activityDetails.getSummary()));
//            this.setSummary(activityDetails.getSummary());
//        } if (activityDetails.getInfo() != null && !activityDetails.getInfo().equals(this.getInfo())) {
//            changes.add(new Changes("Info", this.getInfo(), activityDetails.getInfo()));
//            this.setInfo(activityDetails.getInfo());
//        } if (activityDetails.getStartDateTime() != null && !activityDetails.getStartDateTime().equals(this.getStartDateTime())) {
//            changes.add(new Changes("startDateTime", this.getStartDateTime(), activityDetails.getStartDateTime()));
//            this.setStartDateTime(activityDetails.getStartDateTime());
//        } if (activityDetails.getEndDateTime() != null && !activityDetails.getEndDateTime().equals(this.getEndDateTime())) {
//            changes.add(new Changes("endDateTime", this.getEndDateTime(), activityDetails.getEndDateTime()));
//            this.setEndDateTime(activityDetails.getEndDateTime());
//        }
//
//        TimeZone tz = TimeZone.getTimeZone("UTC");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-HH:mm");
//		df.setTimeZone(tz);
//		String nowAsISO = df.format(new Date());
//
//        history.add(new History(nowAsISO, Type.UPDATE, changes));
//    }

    /**
     * This is where reflection is used to validate fields and store change history.
     * @param activityDetails Data for making changes to activities
     * @throws ActivityIsEqualsException Custom exception, throw when the new fields are equal to the existing ones
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setupChanges(Activity activityDetails) throws ActivityIsEqualsException, InvocationTargetException, IllegalAccessException {
        List<Changes> changes = new ArrayList<>();
        if (this.equals(activityDetails)) {
            throw new ActivityIsEqualsException("All activity fields is equals");
        }
        for (Method method :this.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && (method.getName().startsWith("get"))
                    && (!method.getName().startsWith("getHistory") && !method.getName().startsWith("getId"))
            ) {
                Object value = method.invoke(this);
                Object value2 = method.invoke(activityDetails);
                if (value2 != null && !value2.equals(value)) {
                    changes.add(new Changes(method.getName().substring(3), value.toString(), value2.toString()));
                }
            }
        }

        TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-HH:mm");
		df.setTimeZone(tz);
		String nowAsISO = df.format(new Date());

        history.add(new History(nowAsISO, Type.UPDATE, changes));
        nullAwareBeanCopy(this, activityDetails);
    }

    /**
     * Overwrites event fields with new ones
     * @param dest Original activity
     * @param source ata for making changes to activities
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void nullAwareBeanCopy(Object dest, Object source) throws IllegalAccessException, InvocationTargetException {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        }.copyProperties(dest, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(title, activity.title) &&
                Objects.equals(summary, activity.summary) &&
                Objects.equals(description, activity.description) &&
                Objects.equals(startDateTime, activity.startDateTime) &&
                Objects.equals(endDateTime, activity.endDateTime) &&
                Objects.equals(info, activity.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, summary, description, startDateTime, endDateTime, info);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
