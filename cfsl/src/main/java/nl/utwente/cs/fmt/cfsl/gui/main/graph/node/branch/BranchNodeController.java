/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.branch;

import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.BranchNode;

/**
 *
 * @author Richard
 */
public class BranchNodeController extends NodeController<BranchNode> {

    @FXML private TextField textInput;
    
    private BranchEdgeController edgeIn = null;
    private Set<BranchEdgeController> edgesOut = new HashSet<>();

    public BranchNodeController(BranchNode model) {
        super(model);
    }
    
    // PROPERTIES
    
    @Override
    public String getToolName() {
        return "Branch Node";
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
        
        textInput.requestFocus();
    }
    
}
