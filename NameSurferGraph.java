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

        for (int i = 0; i < entries.size(); i++) {
            remove(entries.get(i));
        }
        entries.clear();
	}

	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
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
        drawGrid();
	}

    private void drawBorder() {
        GLine marginLineUpper = new GLine(0, getWidth() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        GLine marginLineLower = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        add(marginLineUpper);
        add(marginLineLower);
    }

    private void drawGrid() {
        drawBorder();

        double lineStartX = 0;
        for (int i = 0; i < NDECADES; i++) {
            GLine line = new GLine(lineStartX, 0, lineStartX, getHeight());
            add(line);

            lineStartX += getWidth() / NDECADES;
        }
    }

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

    /* INSTANCE VARIABLES */
    private ArrayList<NameSurferEntry> entryList = new ArrayList<>();
}
