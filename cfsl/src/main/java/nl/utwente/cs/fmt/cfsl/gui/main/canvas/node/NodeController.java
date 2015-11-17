/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.node;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgePositionAnchorController.EdgePosition;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public abstract class NodeController extends CanvasElementController<StackPane> {
    private final List<EdgeConnectorController> edgeConnectors = new ArrayList<>();
    
    public void showEdgeConnectors(boolean show) {
        for (EdgeConnectorController controller: edgeConnectors) {
            controller.getView().setVisible(show);
        }
    }
    
    protected void addEdgeConnector(EdgeConnectorController connector, TactilePane tracker, Pos alignment,
            ObservableDoubleValue offsetX, ObservableDoubleValue offsetY) {
        
        StackPane thisView = (StackPane) getView();
        StackPane.setAlignment(connector.getView(), alignment);
        if (offsetX != null) connector.getView().translateXProperty().bind(offsetX);
        if (offsetY != null) connector.getView().translateYProperty().bind(offsetY);
        thisView.getChildren().add(connector.getView());
        tracker.getActiveNodes().add(connector.getView());
        edgeConnectors.add(connector);
    }
    
    public abstract boolean canConnect(EdgeController edge, EdgePosition position);
    
    public abstract boolean connect(EdgeController edge, EdgePosition position);
    
    public abstract void disconnect(EdgeController edge, EdgePosition position);
}
