package com.example.taskmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private Connection conn;

    public TaskDAO() {
        String url = "jdbc:postgresql:testdb";
        String username = "postgres";
        String password = "postgres";

        try {
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database successfully connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = getTaskFromResultSet(rs);
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE taskid=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getTaskFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int createTask(Task newTask) {
        int generatedId = -1;

        String sql = "INSERT INTO tasks(taskname, description, deadline, priority, completed) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newTask.getTaskName());
            stmt.setString(2, newTask.getTaskDescription());
            stmt.setDate(3, new java.sql.Date(newTask.getDeadline().getTime()));
            stmt.setString(4, newTask.getPriority().toString());
            stmt.setBoolean(5, newTask.isCompleted());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating task failed.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET taskname=?, description=?, deadline=?, priority=?, completed=? " +
                "WHERE taskid=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskName());
            stmt.setString(2, task.getTaskDescription());
            stmt.setDate(3, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setString(4, task.getPriority().toString());
            stmt.setBoolean(5, task.isCompleted());
            stmt.setInt(6, task.getTaskID());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Update failed.");
            }
            System.out.println("Updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE taskid=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();

            if (result == 0) {
                throw new SQLException("Delete operation failed with id=" + id);
            }

            System.out.println("Row successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task getTaskFromResultSet(ResultSet rs) throws SQLException {
        int taskID = rs.getInt("taskid");
        String taskName = rs.getString("taskname");
        String description = rs.getString("description");
        Date deadline = rs.getDate("deadline");
        String priority = rs.getString("priority");
        boolean completed = rs.getBoolean("completed");
        String taskType = rs.getString("tasktype"); // Assuming there's a column named "tasktype" in your database

        Task task;

        switch (taskType) {
            case "Homework":
                task = new HomeworkTask(taskName, description, Priority.valueOf(priority), completed, deadline);
                break;
            case "Meeting":
                task = new MeetingTask(taskName, description, Priority.valueOf(priority), completed, deadline);
                break;
            case "Shopping":
                task = new ShoppingTask(taskName, description, Priority.valueOf(priority), completed, deadline);
                break;
            default:
                throw new IllegalArgumentException("Unsupported task type: " + taskType);
        }

        task.setTaskID(taskID);
        return task;
    }

}
