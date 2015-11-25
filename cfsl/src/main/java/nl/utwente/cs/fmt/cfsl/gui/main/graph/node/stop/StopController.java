/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.stop;

import javafx.geometry.Pos;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.StopNode;

/**
 *
 * @author Richard
 */
public class StopController extends NodeController<StopNode> {

    public StopController(StopNode model) {
        super("Stop Node", model);
    }
    
    // METHODS

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
}
