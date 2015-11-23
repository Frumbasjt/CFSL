/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 * @param <T>
 */
public abstract class GraphElementController<T> extends Controller{
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
            
    private GraphController graph;
    
    public GraphElementController() {
        initialize();
    }
    
    protected GraphElementController(String viewName) {
        super(viewName);
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
    
    // METHODS
    
    // Called by GraphController
    final void _addToGraph(GraphController graph) {
        this.graph = graph;
        afterAddedToGraph(graph);
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
                graph.getContainer().getChildren().remove(getView());
            }
        }
    }
}
