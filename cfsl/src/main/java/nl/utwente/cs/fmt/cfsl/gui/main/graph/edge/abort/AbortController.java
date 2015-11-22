/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;

/**
 *
 * @author Richard
 */
public class AbortController extends EdgeController<Group> {
    @FXML private Pane headWrapper;
    private final MultiLineTextInputController textInput;
    
    /**
     * Creates a new AbortController.
     */
    public AbortController() {
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
        
        // Create text input
        textInput = new MultiLineTextInputController("abort");
        ((Group)getView()).getChildren().add(textInput.getView());
        
        // Center text input
        textInput.getView().layoutBoundsProperty().addListener(o -> updateLabelLocation());
        middleProperty().addListener(o -> updateLabelLocation());
    }
    
    private void updateLabelLocation() {
        Point2D middle = getMiddle();
        textInput.getView().relocate(middle.getX() - textInput.getView().getWidth() / 2, middle.getY() - textInput.getView().getHeight() / 2);
    }
    
}
