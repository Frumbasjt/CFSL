/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.node;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgeAnchorController;
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
            if (controller instanceof EdgeAnchorController) {
                getView().setFill(Color.GREEN);
            }
        });
        
        TactilePane.setOnProximityLeft(getView(), e -> { 
            for (Node node: TactilePane.getNodesInProximity(getView())) {
                Controller controller = Controller.getController(node);
                if (controller != null && controller instanceof EdgeAnchorController) {
                    return;
                }
            }
            getView().setFill(Color.RED);
        });
    }
}
