package Buen.Pastor.app.entity.service;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private Teacher teacher;

    private String message;

    private LocalDateTime sentAt;

    private boolean isRead;

    public Notification() {
    }

    public Notification(Teacher teacher, String message, LocalDateTime sentAt, boolean isRead) {
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

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
