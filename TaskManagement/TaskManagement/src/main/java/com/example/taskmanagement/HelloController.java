package com.example.taskmanagement;

import com.example.taskmanagement.HomeworkTask;
import com.example.taskmanagement.MeetingTask;
import com.example.taskmanagement.ShoppingTask;
import com.example.taskmanagement.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Date;

public class HelloController {
    @FXML
    private ListView<Task> listView;

    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskDescriptionField;
    @FXML
    private RadioButton homeworkRadioButton;
    @FXML
    private RadioButton meetingRadioButton;
    @FXML
    private RadioButton shoppingRadioButton;
    @FXML
    private ChoiceBox<Priority> priorityChoiceBox;

    private ToggleGroup taskTypeGroup;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> completionStatusChoiceBox;

    @FXML
    private Label selectedText;

    @FXML
    private DatePicker deadlineDatePicker;
    public void initialize() {
        // Initialize the ListView
        listView.setItems(tasks);

        // Initialize the ToggleGroup
        taskTypeGroup = new ToggleGroup();
        homeworkRadioButton.setToggleGroup(taskTypeGroup);
        meetingRadioButton.setToggleGroup(taskTypeGroup);
        shoppingRadioButton.setToggleGroup(taskTypeGroup);

        // Initialize the Priority ChoiceBox
        priorityChoiceBox.setItems(FXCollections.observableArrayList(Priority.values()));
        priorityChoiceBox.setValue(Priority.LOW);
    }

    @FXML
    protected void onListClicked() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedText.setText(selectedTask.toString());
        }
    }

    @FXML
    protected void onAddTaskClick() {
        String name = taskNameField.getText();
        String description = taskDescriptionField.getText();
        RadioButton selectedRadioButton = (RadioButton) taskTypeGroup.getSelectedToggle();
        Priority priority = Priority.valueOf(String.valueOf(priorityChoiceBox.getValue()));
        String completionStatusValue = completionStatusChoiceBox.getValue();
        boolean completed = (completionStatusValue != null && completionStatusValue.equals("Completed"));

        // Get the selected date from the DatePicker
        Date deadline = Date.from(deadlineDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (name.isEmpty() || description.isEmpty() || selectedRadioButton == null) {
            // Handle error or show a message to the user
            return;
        }

        String selectedTaskType = selectedRadioButton.getText();
        Task newTask = null;

        if (selectedTaskType.equals("Homework")) {
            newTask = new HomeworkTask(name, description, priority, completed, deadline);
        } else if (selectedTaskType.equals("Meeting")) {
            newTask = new MeetingTask(name, description, priority, completed, deadline);
        } else if (selectedTaskType.equals("Shopping")) {
            newTask = new ShoppingTask(name, description, priority, completed, deadline);
        }

        if (newTask != null) {
            tasks.add(newTask);
            // Clear input fields
            taskNameField.clear();
            taskDescriptionField.clear();
            taskTypeGroup.selectToggle(null);
            completionStatusChoiceBox.setValue("Not Completed");
            deadlineDatePicker.setValue(null); // Clear the DatePicker
        }
    }



    @FXML
    protected void onCompleteTaskClick() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < tasks.size()) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedTask.setCompleted(true);
            tasks.remove(selectedIndex);
        }
    }
}
