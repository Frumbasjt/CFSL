/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import nl.utwente.cs.fmt.cfsl.gui.Controller;

/**
 *
 * @author Richard
 */
public class SelectionController extends Controller<Rectangle>{
    private final GraphController graph;
    
    public SelectionController(GraphController graph) {
        this.graph = graph;
        
        // Bindings
        final Rectangle box = getView();
        final ChangeListener<Bounds> boundsListener = (obs, oldBounds, newBounds) -> { 
            box.setX(newBounds.getMinX());
            box.setY(newBounds.getMinY());
            box.setWidth(newBounds.getWidth());
            box.setHeight(newBounds.getHeight());
            box.toFront();
        };
        
        getView().setVisible(false);
        graph.selectedElementProperty().addListener((obs, oldElement, newElement) -> {
            if (oldElement != null) {
                oldElement.getView().boundsInParentProperty().removeListener(boundsListener);
            }
            if (newElement != null) {
                newElement.getView().boundsInParentProperty().addListener(boundsListener);
                Bounds bounds = newElement.getView().getBoundsInParent();
                box.setX(bounds.getMinX());
                box.setY(bounds.getMinY());
                box.setWidth(bounds.getWidth());
                box.setHeight(bounds.getHeight());
                box.setVisible(true);
                box.toFront();
            } else {
                box.setVisible(false);
            }
        });
    }
}
