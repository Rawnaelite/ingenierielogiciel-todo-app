package com.sujit;

import javax.swing.SwingUtilities;

import com.sujit.view.TodoApp;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        

        SwingUtilities.invokeLater(() -> {
            new TodoApp();
        }
        );

    }
}
