/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class EdgeConnectorController extends Controller<Circle> {
    private final NodeController parent;
    
    public EdgeConnectorController(NodeController parent) {
        this.parent = parent;
        
        TactilePane.setOnProximityEntered(getView(), e -> { 
            Controller controller = Controller.getController(e.getOther());
            if (controller instanceof EdgePositionAnchorController) {
                EdgePositionAnchorController anchor = (EdgePositionAnchorController) controller;
                if (canConnect(anchor)) {
                    getView().setFill(Color.BLUE);
                } else {
                    getView().setFill(Color.RED);
                }
            }
        });
        
        TactilePane.setOnProximityLeft(getView(), e -> { 
            for (Node node: TactilePane.getNodesInProximity(getView())) {
                Controller controller = Controller.getController(node);
                if (controller != null && controller instanceof EdgePositionAnchorController) {
                    return;
                }
            }
            getView().setFill(Color.TRANSPARENT);
        });
    }
    
    // PROPERTIES
    
    public NodeController getNode() {
        return this.parent;
    }
    
    // METHODS
    
    public final boolean connect(EdgePositionAnchorController anchor) {
        return getNode().connect(anchor.getEdge(), anchor.getPosition());
    }
    
    public final void disconnect(EdgePositionAnchorController anchor) {
        getNode().disconnect(anchor.getEdge(), anchor.getPosition());
    }
    
    public final boolean canConnect(EdgePositionAnchorController anchor) {
        return getNode().canConnect(anchor.getEdge(), anchor.getPosition());
    }
}
