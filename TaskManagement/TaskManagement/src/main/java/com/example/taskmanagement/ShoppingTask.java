package com.example.taskmanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShoppingTask extends Task {

    public ShoppingTask(String taskName, String taskDescription, Priority priority, boolean completed, Date deadline) {
        super(taskName, taskDescription, priority, completed, deadline);
    }

    @Override
    public String getTaskType() {
        return "Shopping";
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String completionStatus = isCompleted() ? "Completed" : "Not Completed";
        return "Shopping Task: " + getTaskName() + " - " + getTaskDescription() +
                " (Priority: " + getPriority() + ", Status: " + completionStatus +
                ", Deadline: " + dateFormat.format(getDeadline()) + ")";
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}






















































//package com.example.taskmanagement;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//public class ShoppingTask implements Task {
//    private String taskName;
//    private String description;
//    private boolean completed;
//    private Priority priority;
//    private Date deadline;
//
//    public ShoppingTask(String taskName, String description, Priority priority, boolean completed, Date deadline) {
//        this.taskName = taskName;
//        this.description = description;
//        this.completed = completed;
//        this.priority = priority;
//
//        if (deadline != null) {
//            this.deadline = deadline;
//        } else {
//            // Set a default deadline, for example, one week from the current date
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_MONTH, 7);
//            this.deadline = calendar.getTime();
//        }
//    }
//
//    @Override
//    public String getTaskName() {
//        return taskName;
//    }
//
//    @Override
//    public String getDescription() {
//        return description;
//    }
//
//    @Override
//    public boolean isCompleted() {
//        return completed;
//    }
//
//    @Override
//    public void setCompleted(boolean completed) {
//        this.completed = completed;
//    }
//
//    @Override
//    public String getTaskType() {
//        return "Shopping";
//    }
//    @Override
//    public String getPriority() {
//        return priority;
//    }
//    @Override
//    public Date getDeadline() {
//        return deadline;
//    }
//
//    @Override
//    public void setDeadline(Date date) {
//        this.deadline = date;
//    }
//    @Override
//    public String toString() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        String completionStatus = isCompleted() ? "Completed" : "Not Completed";
//        return "Shopping Task: " + getTaskName() + " - " + getDescription() + " (Priority: " + getPriority() +
//                ", Status: " + completionStatus + ", Deadline: " + dateFormat.format(deadline) + ")";
//    }
//}
