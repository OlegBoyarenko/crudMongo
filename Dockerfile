FROM openjdk:12-oracle
VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#EXPOSE 8080
ADD build/libs/mongoCrud-0.0.1-SNAPSHOT.jar crud-mongo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/crud-mongo.jar"]
