package com.sujit.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.sujit.controller.TaskController;
import com.sujit.model.Task;

public class TodoApp {

    TaskController taskController = new TaskController();
    JTextField taskTF;
    private final DefaultTableModel model;

    public TodoApp() {
        JFrame win = new JFrame("Todo app");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(400, 400);

        JPanel panel = new JPanel();
        win.setContentPane(panel);
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel titleLbl = new JLabel("Todo App");
        taskTF = new JTextField();
        JButton addBtn = new JButton("Add");

        // Table setup
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only action column is editable
            }
        };

        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Action");

        

        refreshTableData();

        JTable table = new JTable(model);
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(taskController));

        // Set column widths
        TableColumn idColumn = table.getColumnModel().getColumn(0); // ID column
        idColumn.setPreferredWidth(50); // Set preferred width to 50 pixels
        idColumn.setMinWidth(30); // Minimum width of 30 pixels
        idColumn.setMaxWidth(100); // Maximum width of 100 pixels

        TableColumn titleColumn = table.getColumnModel().getColumn(1); // Title column
        titleColumn.setPreferredWidth(200); // Set preferred width to 200 pixels

        TableColumn actionColumn = table.getColumnModel().getColumn(2); // Action column
        actionColumn.setPreferredWidth(80); // Set preferred width to 80 pixels
        actionColumn.setMinWidth(80); // Fixed width for action column
        actionColumn.setMaxWidth(80);


        JScrollPane scrollPane = new JScrollPane(table);

        addBtn.addActionListener((ActionEvent ar) -> {
            String taskTitle = taskTF.getText().trim();
            taskTF.setText("");
            if (!taskTitle.isEmpty()) {
                Task newTask = new Task(0, taskTitle);
                taskController.addTask(newTask);
                refreshTableData();
            }
        });

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(titleLbl)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(taskTF)
                                .addComponent(addBtn))
                        .addComponent(scrollPane)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLbl)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(taskTF)
                                .addComponent(addBtn))
                        .addComponent(scrollPane)
        );

        win.setVisible(true);
    }

    private void refreshTableData() {
        SwingUtilities.invokeLater(() -> {
            model.setRowCount(0); // Clear existing data
            List<Task> tasks = taskController.getAllTasks();
            for (Task task : tasks) {
                model.addRow(new Object[]{
                    task.getId(),
                    task.getTitle(),
                    "Delete"
                });
            }
        });
    }

    // Custom button renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Delete");
            return this;
        }
    }

    // Custom button editor
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        private final JButton button;
        private int currentRow;
        private int taskId; // Store ID before refresh

        public ButtonEditor(TaskController controller) {
            button = new JButton("Delete");
            button.addActionListener(e -> {
                // 1. Get ID before any model changes
                taskId = (Integer) model.getValueAt(currentRow, 0);

                // 2. Stop editing first
                fireEditingStopped();

                // 3. Perform delete operation
                controller.deleteTask(taskId);

                // 4. Refresh model after editing is stopped
                refreshTableData();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Delete";
        }
    }

}
