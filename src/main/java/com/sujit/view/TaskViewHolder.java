package com.sujit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.sujit.model.Task;

public class TaskViewHolder extends JPanel implements ListCellRenderer<Task> {

    JLabel titleLbl;
    JButton removeBtn;
    JCheckBox isCompletedChkBox;

    public TaskViewHolder() {
        setLayout(new BorderLayout());
        setOpaque(true); // Ensure background is painted
        titleLbl = new JLabel();
        removeBtn = new JButton("X");
        isCompletedChkBox = new JCheckBox();
        add(isCompletedChkBox, BorderLayout.WEST);
        add(titleLbl, BorderLayout.CENTER);
        add(removeBtn, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index,
            boolean isSelected, boolean cellHasFocus) {
        titleLbl.setText(value.getTitle());
        if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.WHITE);
        }
        return this;
    }
}
