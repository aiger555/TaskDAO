
package com.example.taskmanagement;

import java.util.Date;

public abstract class Task {

    private int taskID;
    private String taskName;
    private String taskDescription;
    private Priority priority;
    private Date deadline;

    public Task(){
    }

    public Task(int taskID) {
        this.taskID = taskID;
    }

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public Task(String taskName, String taskDescription, Priority priority, boolean completed, Date deadline) {
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }



    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public abstract String getTaskType();

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", priority=" + priority +
                ", deadline=" + deadline +
                '}';
    }

    public void setCompleted(boolean b) {
    }

    public abstract boolean isCompleted();
}
























//package com.example.taskmanagement;
//
//import java.util.Date;
//
//public interface Task {
//    String getTaskName();
//    String getDescription();
//    boolean isCompleted();
//    void setCompleted(boolean completed);
//    String getTaskType(); // You can use this to distinguish between task types
//    String getPriority();
//    Date getDeadline(); // Add the getDeadline method
//    void setDeadline(Date date);
//
//
//    void setTaskId(int anInt);
//
//    int getTaskId();
//}
//
