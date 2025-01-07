/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
        update();
	}

	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
        entryList.clear();

        for (GLine line : lines) {
            remove(line);
        }
        lines.clear();

        for (GLabel label : labels) {
            remove(label);
        }
        labels.clear();
	}

	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
        entryList.add(entry);
	}

	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
        removeAll();
        drawGraph();
	}

    // draws/updates the graph and labels
    private void drawGraph() {
        double decadeSeparatorWidth = (double) getWidth() / NDECADES;

        drawGrid(decadeSeparatorWidth);
        drawDecades(decadeSeparatorWidth);
        drawNames(decadeSeparatorWidth);
    }

    // draws the decade labels
    private void drawDecades(double separatorWidth) {
        for (int i = 0; i < NDECADES; i++) {
            double dateX = i * separatorWidth;
            GLabel decadeLabel = new GLabel(Integer.toString(START_DECADE + 10 * i));
            add(decadeLabel, dateX, getHeight() - GRAPH_MARGIN_SIZE / 4.0);
        }
    }

    private void drawGrid(double separatorWidth) {
        // draw borders
        GLine marginLineUpper = new GLine(0, getWidth() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        GLine marginLineLower = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        add(marginLineUpper);
        add(marginLineLower);

        for (int i = 0; i < NDECADES; i++) {
            double lineX = separatorWidth * i;
            GLine line = new GLine(lineX, 0, lineX, getHeight());
            add(line);
        }
    }

    private void drawNames(double separatorWidth) {
        for (int i = 0; i < entryList.size(); i++) {
            Color color = getColor(i % 4);

            drawSurfLines(entryList.get(i), color, separatorWidth);
            drawSurfNames(entryList.get(i), color, separatorWidth);
        }
    }

    private void drawSurfNames(NameSurferEntry entry, Color color, double separatorWidth) {
        for (int i = 0; i < NDECADES; i++) {
            int rank = entry.getRank(i);
            double x = separatorWidth * i;
            double y = getNameY(rank);

            String name = entry.getName();
            if (rank == 0) {
                y -= UNRANKED_DATE_PADDING;
                name += "*";
            } else {
                name += " " + rank;
            }

            GLabel nameLabel = new GLabel(name);
            nameLabel.setColor(color);
            labels.add(nameLabel);
            add(nameLabel, x, y);
        }
    }

    private void drawSurfLines(NameSurferEntry entry, Color color, double separatorWidth) {
        for (int i = 0; i < NDECADES - 1; i++) {
            double x = separatorWidth * i;
            double y = getNameY(entry.getRank(i));
            double nextDecadeY = getNameY(entry.getRank(i + 1));

            GLine line = new GLine(x, y, x + separatorWidth, nextDecadeY);
            line.setColor(color);

            lines.add(line);
            add(line);
        }
    }

    // calculates the Y coordinate based on the rank
    private double getNameY(int rank) {
        if (rank == 0) {
            return getHeight() - GRAPH_MARGIN_SIZE;
        }

        double yRatio = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK;
        return GRAPH_MARGIN_SIZE + rank * yRatio;
    }

    // returns a color based on the number of graphs
    private Color getColor(int count) {
        switch (count) {
        case 0:
            return Color.BLACK;
        case 1:
            return Color.RED;
        case 2:
            return Color.BLUE;
        case 3:
            return Color.YELLOW;
        default:
            return Color.GREEN;
        }
    }

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

    private static final int UNRANKED_DATE_PADDING = 5;

    /* INSTANCE VARIABLES */
    private ArrayList<NameSurferEntry> entryList = new ArrayList<>();
    private ArrayList<GLine> lines = new ArrayList<>();
    private ArrayList<GLabel> labels = new ArrayList<>();
}
