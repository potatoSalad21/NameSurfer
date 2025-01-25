/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
    public NameSurfer() {
        graph = new NameSurferGraph();
        add(graph);
    }
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
        addInteractors();
        try { // in case file read operation fails
            db = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addActionListeners();
    }


/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (e.getSource() == textField || command.equals("Graph")) {
            String name = textField.getText();
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(); // make input case-insensitive
            NameSurferEntry entry = db.findEntry(name);
            if (entry != null) {
                graph.addEntry(entry);
                graph.update();
            }
        } else if (command.equals("Clear")) {
            graph.clear();
        }
	}

    // adds all interactors on the bottom
    private void addInteractors() {
        // add text field
        JLabel label = new JLabel("");
        textField = new JTextField("", 50);
        textField.addActionListener(this);
        // add buttons
        JButton graphBtn = new JButton("Graph");
        JButton clearBtn = new JButton("Clear");

        add(label, SOUTH);
        add(textField, SOUTH);
        add(graphBtn, SOUTH);
        add(clearBtn, SOUTH);
    }

    /* INSTANCE VARIABLES */

    JTextField textField;
    NameSurferDataBase db;
    NameSurferGraph graph;
}
