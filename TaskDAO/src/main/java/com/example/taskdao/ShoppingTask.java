package com.example.taskdao;

import java.util.Date;

public class ShoppingTask extends Task {
    private String shoppingList;

    public ShoppingTask() {
        // Default constructor
    }

    public ShoppingTask(int taskID) {
        super(taskID);
    }

    public ShoppingTask(String taskName) {
        super(taskName);
    }

    public String getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString() {
        return "ShoppingTask{" +
                "shoppingList='" + shoppingList + '\'' +
                ", " + super.toString() +
                '}';
    }
}

