package com.example.taskmanagementapp2;

import java.util.Date;

public class task {
    private String title;
    private String description;
    private Date dueDate;
    private boolean isDone;

    public task(String title, String description, Date dueDate, boolean isDone) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isDone() {
        return isDone;
    }

    // Setter method to mark task as done
    public void setDone(boolean done) {
        this.isDone = done;
    }
}
