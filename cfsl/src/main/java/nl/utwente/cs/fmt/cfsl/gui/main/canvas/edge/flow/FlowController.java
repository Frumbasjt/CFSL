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
 *
 * @author Richard
 */
public class FlowController extends EdgeController<Group> {

    @FXML Label label;
    
    public FlowController() {
        // Center text input
        label.setSnapToPixel(true);
        curve.startXProperty().addListener(o -> updateLabelLocation());
        curve.startYProperty().addListener(o -> updateLabelLocation());
        curve.endXProperty().addListener(o -> updateLabelLocation());
        curve.endYProperty().addListener(o -> updateLabelLocation());
        curve.controlX1Property().addListener(o -> updateLabelLocation());
        curve.controlY1Property().addListener(o -> updateLabelLocation());
        curve.controlX2Property().addListener(o -> updateLabelLocation());
        curve.controlY2Property().addListener(o -> updateLabelLocation());
    }
    
    private void updateLabelLocation() {
        Point2D location = super.eval(curve, 0.5f);
        label.relocate(location.getX() - label.getWidth() / 2, location.getY() - label.getHeight() / 2);
    }
    
}
