package com.example.taskdao;

import java.util.Date;

public class HomeworkTask extends Task {
    private String subject;

    public HomeworkTask() {
        // Default constructor
    }

    public HomeworkTask(int taskID) {
        super(taskID);
    }

    public HomeworkTask(String taskName) {
        super(taskName);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "HomeworkTask{" +
                "subject='" + subject + '\'' +
                ", " + super.toString() +
                '}';
    }
}

