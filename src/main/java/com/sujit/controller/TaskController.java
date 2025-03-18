package com.sujit.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sujit.model.Task;
import com.sujit.util.DBUtil;

public class TaskController {

    public void addTask(Task task) {
        String sql = "INSERT INTO task (title) VALUES (?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, task.getTitle());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            handleException("Error adding task", e);
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";

        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException e) {
            handleException("Error fetching task", e);
        }
        return tasks;
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM task WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            handleException("Error deleting task", e);
        }
    }

    private void handleException(String message, SQLException e) {
        System.err.println(message);
        e.printStackTrace();
        throw new RuntimeException(message, e);
    }

}
