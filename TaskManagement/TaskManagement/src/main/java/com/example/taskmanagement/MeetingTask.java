package com.example.taskmanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingTask extends Task {

    public MeetingTask(String taskName, String taskDescription, Priority priority, boolean completed, Date deadline) {
        super(taskName, taskDescription, priority, completed, deadline);
    }

    @Override
    public String getTaskType() {
        return "Meeting";
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String completionStatus = isCompleted() ? "Completed" : "Not Completed";
        return "Meeting Task: " + getTaskName() + " - " + getTaskDescription() + " (Priority: " + getPriority() +
                ", Status: " + completionStatus + ", Deadline: " + dateFormat.format(getDeadline()) + ")";
    }

    @Override
    public boolean isCompleted() {
        return false;
    }


}
