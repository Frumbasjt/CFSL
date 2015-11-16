/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgeAnchorController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public abstract class EdgeController<T> extends CanvasElementController<Group> {
    @FXML protected Line line;
    @FXML protected Pane headWrapper;
    @FXML protected Line transparentLine;
    
    protected final EdgeAnchorController startAnchor = new EdgeAnchorController(this);
    protected final EdgeAnchorController endAnchor = new EdgeAnchorController(this);
    
    public EdgeController() {
        TactilePane.setDraggable(getView(), false);
        
        // Bind transparent line's location to visible line
        transparentLine.startXProperty().bind(line.startXProperty());
        transparentLine.startYProperty().bind(line.startYProperty());
        transparentLine.endXProperty().bind(line.endXProperty());
        transparentLine.endYProperty().bind(line.endYProperty());
        
        // Bind head's location to line's end
        headWrapper.layoutXProperty().bind(line.endXProperty().subtract(headWrapper.widthProperty().divide(2)));
        headWrapper.layoutYProperty().bind(line.endYProperty().subtract(headWrapper.heightProperty().divide(2)));
        
        // Rotate edge head with line
        headWrapper.rotateProperty().bind(Bindings.createDoubleBinding(() -> {
            double deltaX = line.getEndX() - line.getStartX();
            double deltaY = line.getEndY() - line.getStartY();
            return Math.toDegrees(-Math.atan2(deltaX, deltaY)) + 180;
        }, line.startXProperty(), line.startYProperty(), line.endXProperty(), line.endYProperty()));
    }
    
    // CANVASELEMENTCONTROLLER IMPLEMENTATION
    
    @Override
    public void initCanvas(CanvasController canvas) {
        // Add anchors to the canvas
        canvas.getCanvasView().getChildren().add(startAnchor.getView());
        canvas.getCanvasView().getChildren().add(endAnchor.getView());
        startAnchor.getView().relocate(getView().getLayoutX() + line.getStartX(), getView().getLayoutY() + line.getStartY());
        endAnchor.getView().relocate(getView().getLayoutX() + line.getEndX(), getView().getLayoutY() + line.getEndY());
        
        // Bind line to position of anchors
        Bounds b = getView().getBoundsInLocal();
        getView().relocate(b.getMinX(), b.getMinY());
        line.startXProperty().bind(startAnchor.getView().layoutXProperty());
        line.startYProperty().bind(startAnchor.getView().layoutYProperty());
        line.endXProperty().bind(endAnchor.getView().layoutXProperty());
        line.endYProperty().bind(endAnchor.getView().layoutYProperty());
        
        // Track anchors
        canvas.getCanvasView().getActiveNodes().add(startAnchor.getView());
        canvas.getCanvasView().getActiveNodes().add(endAnchor.getView());
    }
    
    // PROPERTIES
    
    private final ReadOnlyBooleanWrapper startAnchorVisible = new ReadOnlyBooleanWrapper(true);

    public boolean isStartAnchorVisible() {
        return startAnchorVisible.get();
    }

    public ReadOnlyBooleanProperty startAnchorVisibleProperty() {
        return startAnchorVisible.getReadOnlyProperty();
    }
    
    private final ReadOnlyBooleanWrapper endAnchorVisible = new ReadOnlyBooleanWrapper(true);

    public boolean isEndAnchorVisible() {
        return endAnchorVisible.get();
    }

    public ReadOnlyBooleanProperty endAnchorVisibleProperty() {
        return endAnchorVisible.getReadOnlyProperty();
    }
}
