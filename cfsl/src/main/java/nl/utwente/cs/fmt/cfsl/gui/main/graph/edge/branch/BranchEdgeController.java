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
import nl.utwente.cs.fmt.cfsl.model.BranchEdge;

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
        super(model);
        
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
        
        textInput.editableProperty().bind(fromBranchNode);
        fromBranchNode.addListener(o -> { 
            if (isFromBranchNode()) {
                textInput.setText(null);
                textInput.requestFocus();
            } else {
                textInput.setText("branch");
            }
        });
        
        model.valueProperty().bind(textInput.textProperty());
    }
    
    // PROPERTIES
    
    /**
     * Whether the start of this edge is connected to a branch node.
     */
    private final BooleanProperty fromBranchNode = new SimpleBooleanProperty(false);

    public boolean isFromBranchNode() {
        return fromBranchNode.get();
    }

    public void setFromBranchNode(boolean value) {
        fromBranchNode.set(value);
    }

    public BooleanProperty fromBranchNodeProperty() {
        return fromBranchNode;
    }
    
    @Override
    public String getToolName() {
        return "Branch Edge";
    }
    
    // HELP METHODS
    
    private void updateLabelLocation() {
        Point2D middle = getMiddle();
        textInput.relocate(middle.getX() - textInput.getWidth() / 2, middle.getY() - textInput.getHeight() / 2);
    }
}
