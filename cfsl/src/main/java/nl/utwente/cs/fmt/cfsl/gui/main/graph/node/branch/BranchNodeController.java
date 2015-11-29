/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.branch;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.BranchNode;

/**
 *
 * @author Richard
 */
public class BranchNodeController extends NodeController<BranchNode> {

    @FXML private TextField textInput;

    public BranchNodeController(BranchNode model) {
        super("Branch Node", model);
        
        // Control model
        model.conditionIdProperty().bind(textInput.textProperty());
    }
    
    // METHODS

    @Override
    public void afterAddedToGraph(GraphController graph) {
        super.afterAddedToGraph(graph);
        
        EdgeConnectorController connector;

        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
            Pos.TOP_CENTER, 
            null, 
            connector.getView().radiusProperty().multiply(-1));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
            Pos.BOTTOM_CENTER, 
            null, 
            connector.getView().radiusProperty());
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
            Pos.CENTER_LEFT, 
            connector.getView().radiusProperty().multiply(-1), 
            null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
            Pos.CENTER_RIGHT, 
            connector.getView().radiusProperty(),
            null); 
        
        textInput.requestFocus();
    }
    
}
