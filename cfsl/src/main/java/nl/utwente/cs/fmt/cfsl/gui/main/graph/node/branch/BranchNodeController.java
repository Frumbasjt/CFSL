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
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.EdgePosition;

/**
 *
 * @author Richard
 */
public class BranchNodeController extends NodeController {

    @FXML private TextField textInput;
    
    private BranchEdgeController edgeIn = null;
    private Set<BranchEdgeController> edgesOut = new HashSet<>();
    
    @Override
    public boolean canConnect(EdgeController edge, EdgePosition position) {
        return edge instanceof BranchEdgeController && 
                ((edgeIn == null && position == EdgePosition.END) || 
                position == EdgePosition.START);
    }

    @Override
    public boolean connect(EdgeController edge, EdgePosition position) {
        if (edge instanceof BranchEdgeController) {
            BranchEdgeController branchEdge = (BranchEdgeController) edge;
            if (position == EdgePosition.END) {
                if (edgeIn == null) {
                    edgeIn = branchEdge;
                    branchEdge.setFromBranchNode(false);
                    connectedEdgeAnchors.add(edge.getEndAnchor());
                    return true;
                }
            } else {
                edgesOut.add(branchEdge);
                branchEdge.setFromBranchNode(true);
                connectedEdgeAnchors.add(edge.getStartAnchor());
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(EdgeController edge, EdgePosition position) {
        if (position == EdgePosition.END && edge == edgeIn) {
            connectedEdgeAnchors.remove(edge.getEndAnchor());
            edgeIn = null;
        } else if (position == EdgePosition.START) {
            ((BranchEdgeController) edge).setFromBranchNode(false);
            connectedEdgeAnchors.remove(edge.getStartAnchor());
            edgesOut.remove(edge);
        }
    }

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
