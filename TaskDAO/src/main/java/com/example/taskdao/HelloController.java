package com.example.taskdao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class HelloController {
    @FXML
    private TextField taskNameField;

    @FXML
    private TextField taskDescriptionField;

    @FXML
    private ChoiceBox<Priority> priorityChoiceBox;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private TextField additionalField;

    @FXML
    private ListView<Task> taskListView;

    private TaskDAO taskDAO;

    public HelloController() {
        this.taskDAO = new TaskDAO();
    }

    @FXML
    private void initialize() {
        // Initialize the Priority ChoiceBox with enum values
        priorityChoiceBox.setItems(FXCollections.observableArrayList(Priority.values()));
        refreshTaskListView();
    }

    @FXML
    private void createTask() throws ParseException {
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        Priority priority = priorityChoiceBox.getValue();
        // Retrieve the selected date from the DatePicker
        String deadline = deadlinePicker.getValue().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format as needed
        Date specificDate = dateFormat.parse("2023-12-31"); // Replace with your specific date
        Task newTask = new Task();
        newTask.setTaskName(taskName);
        newTask.setTaskDescription(taskDescription);
        newTask.setPriority(priority);
        newTask.setDeadline(specificDate);

        taskDAO.createTask(newTask);

        refreshTaskListView();
        clearFields();
    }
    @FXML
    private void updateTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            selectedTask.setTaskName(taskNameField.getText());
            selectedTask.setTaskDescription(taskDescriptionField.getText());
            selectedTask.setPriority(priorityChoiceBox.getValue());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date specificDate = dateFormat.parse("2023-12-31"); // Replace with your specific date
                selectedTask.setDeadline(specificDate);
            } catch (ParseException e) {
                // Handle the parsing exception
                e.printStackTrace();
            }

            if (selectedTask instanceof HomeworkTask) {
                ((HomeworkTask) selectedTask).setSubject(taskDescriptionField.getText());
            } else if (selectedTask instanceof MeetingTask) {
                ((MeetingTask) selectedTask).setLocation(taskDescriptionField.getText());
            } else if (selectedTask instanceof ShoppingTask) {
                ((ShoppingTask) selectedTask).setShoppingList(taskDescriptionField.getText());
            }

            taskDAO.updateTask(selectedTask);

            refreshTaskListView();
            clearFields();
        }
    }

    @FXML
    private void deleteTask() {
        // Retrieve selected task from the list view
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            // Call the DAO method to delete the task from the database
            taskDAO.deleteTask(selectedTask.getTaskID());

            // Refresh the task list view
            refreshTaskListView();
        }
    }

    private void refreshTaskListView() {
        // Clear UI fields
        clearFields();

        // Retrieve all tasks from the database and update the list view
        List<Task> tasks = taskDAO.getAllTasks();
        taskListView.getItems().setAll(tasks);
    }

    private void clearFields() {
        taskNameField.clear();
        taskDescriptionField.clear();
        priorityChoiceBox.getSelectionModel().clearSelection();
        deadlinePicker.setValue(null);
    }

}
