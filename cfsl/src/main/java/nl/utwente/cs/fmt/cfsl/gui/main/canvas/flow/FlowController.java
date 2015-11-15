/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.flow;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;

/**
 *
 * @author Richard
 */
public class FlowController extends CanvasElementController<Group> {

    private FlowAnchorController fromAnchor;
    private FlowAnchorController toAnchor;

    @FXML
    private Line line;

    @FXML
    private Pane arrowWrapper;
    
    public FlowController() {
        arrowWrapper.layoutXProperty().bind(line.endXProperty().subtract(arrowWrapper.widthProperty().divide(2)));
        arrowWrapper.layoutYProperty().bind(line.endYProperty().subtract(arrowWrapper.heightProperty().divide(2)));
        
        arrowWrapper.rotateProperty().bind(Bindings.createDoubleBinding(() -> {
            double deltaX = line.getEndX() - line.getStartX();
            double deltaY = line.getEndY() - line.getStartY();
            return Math.toDegrees(-Math.atan2(deltaX, deltaY)) + 180;
        }, line.startXProperty(), line.startYProperty(), line.endXProperty(), line.endYProperty()));
    }
    
    @Override
    public void initCanvas(CanvasController canvas) {
        line.startXProperty().bind(fromAnchor.getView().layoutXProperty());
        line.startYProperty().bind(fromAnchor.getView().layoutYProperty());
        line.endXProperty().bind(toAnchor.getView().layoutXProperty());
        line.endYProperty().bind(toAnchor.getView().layoutYProperty());
    }
            
}
