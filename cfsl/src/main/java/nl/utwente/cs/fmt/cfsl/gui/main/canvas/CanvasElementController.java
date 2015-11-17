/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;

/**
 *
 * @author Richard
 * @param <T>
 */
public abstract class CanvasElementController<T> extends Controller{
    
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
            
    
    public CanvasElementController() {
        getView().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> mousePressed(e));
        
        selected.addListener(o -> { 
            System.out.println(this + ": " + isSelected());
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
    
    /**
     * Called when added to the canvas. Does nothing by default.
     * 
     * @param canvas the Canvas that the CanvasElement was added to
     */
    public void initCanvas(CanvasController canvas) { }
    
    // EVENT HANDLING
        
    private void mousePressed(MouseEvent event) {
        setSelected(true);
        MainController.getInstance().getCanvas().setSelectedElement(this);
        event.consume();
    }
}
