package com.example.taskdao;

import java.util.Date;

public class MeetingTask extends Task {
    private String location;

    public MeetingTask() {
        // Default constructor
    }

    public MeetingTask(int taskID) {
        super(taskID);
    }

    public MeetingTask(String taskName) {
        super(taskName);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MeetingTask{" +
                "location='" + location + '\'' +
                ", " + super.toString() +
                '}';
    }
}
