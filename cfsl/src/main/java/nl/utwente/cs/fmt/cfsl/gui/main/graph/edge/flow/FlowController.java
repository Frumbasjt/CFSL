/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.model.FlowEdge;

/**
 * Controller class for flow edges.
 * 
 * @author Richard
 */
public class FlowController extends EdgeController<FlowEdge> {
    @FXML private Pane headWrapper;
    @FXML private Label label;
    
    /**
     * Creates a new FlowController.
     * @param model
     */
    public FlowController(FlowEdge model) {
        super("Flow Edge", model);
        
        // Bind head's location to curve's end
        headWrapper.layoutXProperty().bind(curve.endXProperty().subtract(headWrapper.widthProperty().divide(2)));
        headWrapper.layoutYProperty().bind(curve.endYProperty().subtract(headWrapper.heightProperty().divide(2)));
        
        // Rotate edge head with curve
        headWrapper.rotateProperty().bind(Bindings.createDoubleBinding(() -> {
            double size = Math.max(curve.getBoundsInLocal().getWidth(), curve.getBoundsInLocal().getHeight());
            double scale = size / 4d;
            Point2D tan = Utils.evalDt(curve, 1).normalize().multiply(scale);
            return Math.toDegrees(Math.atan2( tan.getY(), tan.getX())) + 90;
        }, curve.startXProperty(), curve.startYProperty(), curve.endXProperty(), curve.endYProperty(),
        curve.controlXProperty(), curve.controlYProperty()));
        
        // Center text input
        middleProperty().addListener(o -> { 
            updateLabelLocation();
        });
    }
    
    // HELP METHODS
    
    private void updateLabelLocation() {
        Point2D middle = getMiddle();
        label.relocate(middle.getX() - label.getWidth() / 2, middle.getY() - label.getHeight() / 2);
    }
    
}
