/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.GraphElement;

/**
 *
 * @author Richard
 * @param <T>
 * @param <M>
 */
public abstract class GraphElementController<T extends Node, M extends GraphElement> extends Controller<T>{
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
            
    private GraphController graph;
    private final M model;
    private final String toolName;
    
    protected GraphElementController(String toolName, M model) {
        this.toolName = toolName;
        this.model = model;
        initialize();
    }
    
    protected GraphElementController(String toolName, M model, String viewName) {
        super(viewName);
        this.toolName = toolName;
        this.model = model;
        initialize();
    }
    
    private void initialize() {
        getView().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> mousePressed(e));
        getView().addEventHandler(KeyEvent.KEY_PRESSED, e-> keyPressed(e));
        
        selected.addListener(o -> { 
            if(isSelected()) {
                getView().requestFocus();
            }
        });
    }
    
    // PROPERTIES
    
    /**
     * Whether the graph element is selected.
     */
    private final BooleanProperty selected = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            getView().pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, get());
            super.invalidated();
        }
    };

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
    
    /**
     * The model this controller controls.
     * @return a GraphElement object
     */
    public M getModel() {
        return model;
    }
    
    /**
     * The controller for the Graph this GraphElement is part of, if any.
     * @return a GraphController object
     */
    public GraphController getGraph() {
        return graph;
    }
    
    /**
     * Returns the name of the graph element as shown in the tool bar.
     * @return a String object
     */
    public final String getToolName() {
        return toolName;
    }
    
    // METHODS
    
    // Called by GraphController
    final void _setGraph(GraphController graph) {
        this.graph = graph;
    }
    
    /**
     * Called after the element is added to the graph.
     * 
     * @param graph the Graph that the GraphElement was added to.
     */
    protected abstract void afterAddedToGraph(GraphController graph);
    
    /**
     * Called before the element is removed from the graph.
     * 
     * @param graph the Graph that the GraphElement was removed from.
     */
    protected abstract void beforeRemovedFromGraph(GraphController graph);
    
    // EVENT HANDLING
        
    private void mousePressed(MouseEvent event) {
        setSelected(true);
        MainController.getInstance().getCanvas().setSelectedElement(this);
        event.consume();
    }
    
    private void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE) {
            if (graph != null) {
                beforeRemovedFromGraph(graph);
                graph.removeGraphElement(this);
            }
        }
    }
}
