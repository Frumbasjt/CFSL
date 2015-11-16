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
 *
 * @author Richard
 */
public abstract class EdgeAnchorController extends Controller<Circle> {
    private final EdgeController edge;
    
    public EdgeAnchorController(EdgeController edge) {
        super();
        
        this.edge = edge;
        this.visible.addListener(o -> { 
            if (isVisible()) {
                getView().setOpacity(0.6);
            } else {
                getView().setOpacity(0);
            }
        });
    }
    
    // PROPERTIES
    
    public EdgeController getEdge() {
        return edge;
    }
    
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
