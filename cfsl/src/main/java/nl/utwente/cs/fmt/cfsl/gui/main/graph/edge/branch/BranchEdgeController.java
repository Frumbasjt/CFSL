/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.Node;

/**
 *
 * @author Richard
 */
public class BranchEdgeController extends EdgeController<BranchEdge> {
    @FXML private Pane headWrapper;
    @FXML private TextField textInput;
    
    /**
     * Creates a new BranchEdgeController.
     * @param model
     */
    public BranchEdgeController(BranchEdge model) {
        super("Branch Edge", model);
        
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
        
        // Model bindings
        model.startNodeProperty().addListener(o -> { 
            Node start = model.getStartNode();
            if(start != null && start instanceof BranchNode) {
                textInput.setEditable(true);
                textInput.setText(null);
                textInput.requestFocus();
            } else {
                textInput.setEditable(false);
                textInput.setText("branch");
            }
        });
        model.valueProperty().bind(textInput.textProperty());
    }
    
    // HELP METHODS
    
    private void updateLabelLocation() {
        Point2D middle = getMiddle();
        textInput.relocate(middle.getX() - textInput.getWidth() / 2, middle.getY() - textInput.getHeight() / 2);
    }
}
