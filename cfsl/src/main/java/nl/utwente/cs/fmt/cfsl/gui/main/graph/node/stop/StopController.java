/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.stop;

import javafx.geometry.Pos;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController.EdgePosition;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;

/**
 *
 * @author Richard
 */
public class StopController extends NodeController {

    @Override
    public void afterAddedToGraph(GraphController canvas) {
        EdgeConnectorController connector;

        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
            Pos.TOP_CENTER, 
            null, 
            connector.getView().radiusProperty().multiply(-1));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
            Pos.BOTTOM_CENTER, 
            null, 
            connector.getView().radiusProperty());
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
            Pos.CENTER_LEFT, 
            connector.getView().radiusProperty().multiply(-1), 
            null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
            Pos.CENTER_RIGHT, 
            connector.getView().radiusProperty(),
            null); 
    }
    
    @Override
    public boolean canConnect(EdgeController edge, EdgePosition position) {
        return position == EdgePosition.END;
    }

    @Override
    public boolean connect(EdgeController edge, EdgePosition position) {
        if (position == EdgePosition.END) {
            connectedEdgeAnchors.add(edge.getEndAnchor());
            return true;
        }
        return false;
    }

    @Override
    public void disconnect(EdgeController edge, EdgePosition position) {
        connectedEdgeAnchors.remove(edge.getEndAnchor());
    }
    
}
