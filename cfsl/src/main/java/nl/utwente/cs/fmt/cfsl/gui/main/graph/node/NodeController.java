/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController;
import nl.utwente.cs.fmt.cfsl.model.Edge;
import nl.utwente.cs.fmt.cfsl.model.EdgePosition;
import nl.utwente.cs.fmt.cfsl.model.Node;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 * @param <M>
 */
public abstract class NodeController<M extends Node> extends GraphElementController<StackPane, M> {
    private final List<EdgeConnectorController> edgeConnectors = new ArrayList<>();
    protected final List<EdgePositionAnchorController> connectedEdgeAnchors = new ArrayList<>();
    
    public NodeController(M model) {
        super(model);
        TactilePane.setGoToForegroundOnContact(getView(), false);
    }
    
    public void showEdgeConnectors(boolean show) {
        for (EdgeConnectorController controller: edgeConnectors) {
            controller.getView().setVisible(show);
        }
    }
    
    @Override
    protected void beforeRemovedFromGraph(GraphController graph) {
        for (EdgeConnectorController connector : edgeConnectors) {
            graph.getContainer().getActiveNodes().remove(connector.getView());
        }
        for (EdgePositionAnchorController anchor : connectedEdgeAnchors) {
            anchor.setConnector(null);
            TactilePane.setAnchor(anchor.getView(), null);
        }
    }
    
    // HELP METHOD
    
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
    
    // PUBLIC METHODS
    
    public final boolean canConnect(EdgeController edge, EdgePosition position) {
        return getModel().canConnect((Edge) edge.getModel(), position);
    }
    
    public final boolean connect(EdgeController edge, EdgePosition position) {
        boolean connected = getModel().connect((Edge) edge.getModel(), position);
        if (connected) {
            if (position == EdgePosition.START) {
                connectedEdgeAnchors.add(edge.getStartAnchor());
            } else {
                connectedEdgeAnchors.add(edge.getEndAnchor());
            }
        }
        return connected;
    }
    
    public final void disconnect(EdgeController edge, EdgePosition position) {
        getModel().disconnect((Edge) edge.getModel(), position);
        if (position == EdgePosition.START) {
            connectedEdgeAnchors.remove(edge.getStartAnchor());
        } else {
            connectedEdgeAnchors.remove(edge.getEndAnchor());
        }
    }
}
