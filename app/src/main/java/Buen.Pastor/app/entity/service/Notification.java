package Buen.Pastor.app.entity.service;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private Teacher teacher;

    private String message;

    private String sentAt;

    private boolean isRead;

    public Notification() {
    }

    public Notification(int id, Teacher teacher, String message, String sentAt, boolean isRead) {
        this.id = id;
        this.teacher = teacher;
        this.message = message;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
