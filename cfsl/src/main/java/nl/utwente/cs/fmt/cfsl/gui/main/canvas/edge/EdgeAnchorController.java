/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.Controller;

/**
 * Controller class for edge anchors. Edge anchors are handles for the user to
 * manipulate the position or shape of edges.
 * 
 * @author Richard
 */
public abstract class EdgeAnchorController extends Controller<Circle> {
    private final EdgeController edge;
    
    /**
     * Creates a new EdgeAnchorController that manipulates the given edge.
     * 
     * @param edge the edge this anchor manipulates. May not be null.
     */
    public EdgeAnchorController(EdgeController edge) {
        super();
        
        this.edge = edge;
        this.visible.addListener(o -> { 
            if (isVisible()) {
                getView().setOpacity(1);
            } else {
                getView().setOpacity(0);
            }
        });
    }
    
    // PROPERTIES
    
    /**
     * Returns the edge that this anchor manipulates.
     * 
     * @return an EdgeController
     */
    public EdgeController getEdge() {
        return edge;
    }
    
    /**
     * Whether the anchor is visible to the user
     */
    private final BooleanProperty visible = new SimpleBooleanProperty();

    public boolean isVisible() {
        return visible.get();
    }

    public void setVisible(boolean value) {
        visible.set(value);
    }

    public BooleanProperty visibleProperty() {
        return visible;
    }
    
    
}
