/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.model.AbortEdge;

/**
 *
 * @author Richard
 * @param <M>
 */
public abstract class AbortController<M extends AbortEdge> extends EdgeController<M> {
    @FXML private Pane headWrapper;
    @FXML private VBox textInputContainer;
    @FXML private Label label;
    
    private final MultiLineTextInputController textInput;
    
    /**
     * Creates a new AbortController.
     * 
     * @param model
     * @param label the label text that tells what kind of abort flow this edge represents
     */
    protected AbortController(M model, String label) {
        super(model, "AbortView");
        
        this.label.setText(label);
        textInput = new MultiLineTextInputController("Reason");
        textInputContainer.getChildren().add(textInput.getView());
        
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
        textInputContainer.layoutBoundsProperty().addListener(o -> updateTextLocation());
        middleProperty().addListener(o -> updateTextLocation());
    }
    
    private void updateTextLocation() {
        Point2D middle = getMiddle();
        textInputContainer.relocate(middle.getX() - textInputContainer.getWidth() / 2, middle.getY() - textInputContainer.getHeight() / 2);
    }
    
    @Override
    protected void afterAddedToGraph(GraphController graph) {
        super.afterAddedToGraph(graph);
        textInput.requestFocus();
    }
    
}
