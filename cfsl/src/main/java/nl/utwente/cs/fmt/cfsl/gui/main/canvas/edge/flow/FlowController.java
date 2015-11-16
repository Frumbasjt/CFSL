/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.flow;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgeController;

/**
 * Controller class for flow edges.
 * 
 * @author Richard
 */
public class FlowController extends EdgeController<Group> {

    @FXML Label label;
    
    /**
     * Creates a new FlowController.
     */
    public FlowController() {
        // Center text input
        middleProperty().addListener(o -> { 
            updateLabelLocation();
        });
    }
    
    private void updateLabelLocation() {
        Point2D middle = getMiddle();
        label.relocate(middle.getX() - label.getWidth() / 2, middle.getY() - label.getHeight() / 2);
    }
    
}
