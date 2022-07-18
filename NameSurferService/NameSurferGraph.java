package NameSurferService;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

    /* array which contains names and its ranks */
    LinkedHashMap<String, NameSurferEntry> namesAndRankData = new LinkedHashMap<String, NameSurferEntry>();

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        this.addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        /* delete all selected name in our container */
        namesAndRankData = new LinkedHashMap<>();

        update();
    }

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        /* add selected name and ranks to container */
        namesAndRankData.put(entry.getName(), entry);

        update();
    }

    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        /* remove all graphic objects */
        removeAll();

        drawWindowGrid();

        /* draw graphic */
        if (namesAndRankData.size() != 0)
            drawRankGraphAndNameLabels();
    }

    /**
     * Draws vertical and horizontal grid
     */
    private void drawWindowGrid() {
        /* Draws vertical NDECADES(quantity) lines which are on equal distance between
        each other using parameters equal distance between vertical lines */

        /* x-coordinate for the next vertical line */
        double distanceBetweenVerticalLines = getWidth() / NDECADES;

        /* decade value(text) to draw a label */
        int decadeValue = START_DECADE;

        for (int i = 0; i < NDECADES; i++) {
            /* draws vertical line using given params */
            add(new GLine(distanceBetweenVerticalLines * i, getHeight(), distanceBetweenVerticalLines * i, 0));

            /* draws label(decade) line using given params */
            GLabel label = new GLabel(Integer.toString(decadeValue));
            label.setFont("Helvetica-14");
            add(label, distanceBetweenVerticalLines * i, getHeight() - label.getDescent());

            decadeValue += 10;
        }

        /* draw horizontal line at the top */
        add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));

        /* draw horizontal line at the bottom */
        add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
    }

    /**
     * Using namesAndRankData data for each name program will draw graph that
     * displays the rank for particular name and name label above it.
     */
    private void drawRankGraphAndNameLabels() {
        /* index of color to use for each graph */
        int color = 0;

        for (Map.Entry<String, NameSurferEntry> entry : namesAndRankData.entrySet()) {

            if (color > 3)
                color = 0;

            /* get object for current name */
            NameSurferEntry nameToWorkWith = entry.getValue();

            /* calculate coefficient to find proportions between number of total rating and graphic height. */
            double coefficient = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / (double) MAX_RANK;

            /* basic x coordinate for further calculations */
            int xFirstCoordinate = getWidth() / NDECADES;

            for (int i = 0; i < NDECADES; i++) {
                if (i < NDECADES - 1) {
                    /* draw lines on graphic */
                    add(drawGraphLine(xFirstCoordinate * i, nameToWorkWith.getRank(i),
                            nameToWorkWith.getRank(i + 1), coefficient, COLORS[color]));
                }

                /* draw labels on graphic */
                add(drawGraphNameLabel(nameToWorkWith.getName(), nameToWorkWith.getRank(i),
                        xFirstCoordinate * i, coefficient, COLORS[color]));
            }

            /* change color for the next name */
            color++;
        }
    }

    /**
     * Draw lines using given parameters
     *
     * @param xFirstCoordinate - X-axis coordinate
     * @param rankNumber       - rank position for current decade
     * @param nextRank         - rank position for next decade
     * @param coefficient      - value to make rank value and window height proportional
     * @param color            - color for that line
     */
    private GLine drawGraphLine(int xFirstCoordinate, Integer rankNumber, double nextRank, double coefficient, Color color) {
        /* coordinates to plot lines */
        int xSecondCoordinate = xFirstCoordinate + (getWidth() / NDECADES);
        int yFirstCoordinate = rankNumber == 0 ? getHeight() - GRAPH_MARGIN_SIZE : (int) (GRAPH_MARGIN_SIZE + rankNumber * coefficient);
        int ySecondCoordinate = nextRank == 0 ? getHeight() - GRAPH_MARGIN_SIZE : (int) (GRAPH_MARGIN_SIZE + nextRank * coefficient);

        GLine newLine = new GLine(xFirstCoordinate, yFirstCoordinate, xSecondCoordinate, ySecondCoordinate);
        newLine.setColor(color);

        return newLine;
    }

    /**
     * Draw labels using given parameters
     *
     * @param name        - string which contains text of the label
     * @param rankNumber  - rank position for current decade
     * @param xCoordinate - X-axis coordinate
     * @param coefficient - value to make name value and window height proportional
     * @param color       - color for that line
     */
    private GLabel drawGraphNameLabel(String name, Integer rankNumber, int xCoordinate, double coefficient, Color color) {
        /* define y coordinate for label */
        int yCoordinate = rankNumber == 0 ? getHeight() - GRAPH_MARGIN_SIZE : (int) (GRAPH_MARGIN_SIZE + rankNumber * coefficient);

        /* if rank is 0 change display logic of this rank (*) instead of number */
        String labelName = rankNumber == 0 ? name + " *" : name + " " + rankNumber;

        GLabel newLabel = new GLabel(labelName, xCoordinate, yCoordinate);
        newLabel.setColor(color);
        newLabel.setFont("Helvetica-10");

        return newLabel;
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}