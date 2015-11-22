/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.branch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Pos;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController.EdgePosition;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;

/**
 *
 * @author Richard
 */
public class BranchNodeController extends NodeController {

    private BranchEdgeController edgeIn = null;
    private Set<BranchEdgeController> edgesOut = new HashSet<>();
    
    @Override
    public boolean canConnect(EdgeController edge, EdgePosition position) {
        return edge instanceof BranchEdgeController && 
                (edgeIn == null && position == EdgePosition.END) || 
                position == EdgePosition.START;
    }

    @Override
    public boolean connect(EdgeController edge, EdgePosition position) {
        if (edge instanceof BranchEdgeController) {
            BranchEdgeController branchEdge = (BranchEdgeController) edge;
            if (position == EdgePosition.END) {
                if (edgeIn == null) {
                    edgeIn = branchEdge;
                    branchEdge.setFromBranchNode(false);
                    return true;
                }
            } else {
                edgesOut.add(branchEdge);
                branchEdge.setFromBranchNode(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(EdgeController edge, EdgePosition position) {
        if (position == EdgePosition.END && edge == edgeIn) {
            edgeIn = null;
        } else if (position == EdgePosition.START) {
            ((BranchEdgeController) edge).setFromBranchNode(false);
            edgesOut.remove(edge);
        }
    }

    @Override
    public void initCanvas(GraphController canvas) {
        EdgeConnectorController connector;

        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
            Pos.TOP_CENTER, 
            null, 
            connector.getView().radiusProperty().multiply(-1));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
            Pos.BOTTOM_CENTER, 
            null, 
            connector.getView().radiusProperty());
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
            Pos.CENTER_LEFT, 
            connector.getView().radiusProperty().multiply(-1), 
            null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
            Pos.CENTER_RIGHT, 
            connector.getView().radiusProperty(),
            null); 
    }
    
}
