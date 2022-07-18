package NameSurferService;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    /* value input */
    JTextField text;

    private NameSurferGraph graph;

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        this.add(new JLabel("Name"), "North");
        this.add(text = new JTextField(30), "North");

        text.setActionCommand("Enter");
        text.addActionListener(this);

        this.add(new JButton("Graph"), "North");
        this.add(new JButton("Clear"), "North");
        this.add(graph = new NameSurferGraph());

        this.addActionListeners();
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        NameSurferEntry data;

        if (e.getActionCommand().equals("Graph") || e.getActionCommand().equals("Enter")) {
            data = new NameSurferDataBase(NAMES_DATA_FILE).findEntry(text.getText());

            /* if the name is in database add its graph */
            if (data != null) {
                graph.addEntry(data);
                text.setText("");
            }
        }

        /* delete all graphs from the window */
        if (e.getActionCommand().equals("Clear"))
            graph.clear();
    }
}
