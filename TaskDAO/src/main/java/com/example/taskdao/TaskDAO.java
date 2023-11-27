package com.example.taskdao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private static final String JDBC_URL = "jdbc:postgresql:testdb";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";

    public TaskDAO() {
        // Ensure the tasks table exists in the database
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "taskId SERIAL PRIMARY KEY," +
                    "taskType VARCHAR(255) NOT NULL," +
                    "taskName VARCHAR(255) NOT NULL," +
                    "taskDescription VARCHAR(255)," +
                    "priority VARCHAR(20)," +
                    "deadline DATE NOT NULL," +
                    "subject VARCHAR(255)," +
                    "location VARCHAR(255)," +
                    "shoppingList VARCHAR(255)" +
                    ")";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new task in the database
    public void createTask(Task task) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String taskType = determineTaskType(task);

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tasks (taskName, taskDescription, priority, deadline, taskType) " +
                            "VALUES (?, ?, ?, ?, ?)")) {

                statement.setString(1, task.getTaskName());
                statement.setString(2, task.getTaskDescription());
                statement.setString(3, task.getPriority().name());
                statement.setTimestamp(4, new Timestamp(task.getDeadline().getTime()));  // Ensure deadline is not null
                statement.setString(5, determineTaskType(task));  // Ensure determineTaskType is implemented


                if (task instanceof HomeworkTask) {
                    statement.setString(6, ((HomeworkTask) task).getSubject());
                    statement.setNull(7, Types.VARCHAR);
                    statement.setNull(8, Types.VARCHAR);
                } else if (task instanceof MeetingTask) {
                    statement.setNull(6, Types.VARCHAR);
                    statement.setString(7, ((MeetingTask) task).getLocation());
                    statement.setNull(8, Types.VARCHAR);
                } else if (task instanceof ShoppingTask) {
                    statement.setNull(6, Types.VARCHAR);
                    statement.setNull(7, Types.VARCHAR);
                    statement.setString(8, ((ShoppingTask) task).getShoppingList());
                }

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a task in the database
    public void updateTask(Task task) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String taskType = determineTaskType(task);
            String priorityString = (task.getPriority() != null) ? task.getPriority().toString() : "No Priority";
            try (PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET taskType = ?, taskName = ?, taskDescription = ?, priority = ?, deadline = ?, subject = ?, location = ?, shoppingList = ? WHERE taskId = ?")) {
                statement.setString(1, taskType);
                statement.setString(2, task.getTaskName());
                statement.setString(3, task.getTaskDescription());
                if (task.getPriority() != null) {
                    statement.setString(3, task.getPriority().toString());
                } else {
                    statement.setNull(3, Types.VARCHAR);
                }                statement.setDate(5, new java.sql.Date(task.getDeadline().getTime()));

                if (task instanceof HomeworkTask) {
                    statement.setString(6, ((HomeworkTask) task).getSubject());
                    statement.setNull(7, Types.VARCHAR);
                    statement.setNull(8, Types.VARCHAR);
                } else if (task instanceof MeetingTask) {
                    statement.setNull(6, Types.VARCHAR);
                    statement.setString(7, ((MeetingTask) task).getLocation());
                    statement.setNull(8, Types.VARCHAR);
                } else if (task instanceof ShoppingTask) {
                    statement.setNull(6, Types.VARCHAR);
                    statement.setNull(7, Types.VARCHAR);
                    statement.setString(8, ((ShoppingTask) task).getShoppingList());
                }

                statement.setInt(9, task.getTaskID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a task from the database by taskId
    public void deleteTask(int taskId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE taskId = ?")) {

            statement.setInt(1, taskId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks")) {

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        String taskType = resultSet.getString("taskType");
        int taskId = resultSet.getInt("taskId");

        Task task;
        switch (taskType) {
            case "Homework":
                task = new HomeworkTask();
                ((HomeworkTask) task).setSubject(resultSet.getString("subject"));
                break;
            case "Meeting":
                task = new MeetingTask();
                ((MeetingTask) task).setLocation(resultSet.getString("location"));
                break;
            case "Shopping":
                task = new ShoppingTask();
                ((ShoppingTask) task).setShoppingList(resultSet.getString("shoppingList"));
                break;
            default:
                task = new Task();
                break;
        }

        task.setTaskID(taskId);
        task.setTaskName(resultSet.getString("taskName"));
        task.setTaskDescription(resultSet.getString("taskDescription"));
        task.setPriority(Priority.valueOf(resultSet.getString("priority")));
        task.setDeadline(resultSet.getDate("deadline"));

        return task;
    }

    private String determineTaskType(Task task) {
        if (task instanceof HomeworkTask) {
            return "Homework";
        } else if (task instanceof MeetingTask) {
            return "Meeting";
        } else if (task instanceof ShoppingTask) {
            return "Shopping";
        } else { return "False";
        }
    }
}