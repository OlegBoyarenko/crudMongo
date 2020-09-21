package com.olegB.mongoCrud.model;


import com.olegB.mongoCrud.history.Changes;
import com.olegB.mongoCrud.history.Type;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Store history of changes
 */
@Document(collation = "history")
public class History {
    private String dateTime;
    private Type type;
    private List<Changes> changes;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Changes> getChanges() {
        return changes;
    }

    public void setChanges(List<Changes> changes) {
        this.changes = changes;
    }

    public History() {
        this.changes = new ArrayList<>();
    }

    public History(String dateTime, Type type, List<Changes> changes) {
        this.changes = new ArrayList<>();
        this.dateTime = dateTime;
        this.type = type;
        this.changes = changes;
    }

    public History(String dateTime, Type type) {
        this.dateTime = dateTime;
        this.type = type;
    }
}
