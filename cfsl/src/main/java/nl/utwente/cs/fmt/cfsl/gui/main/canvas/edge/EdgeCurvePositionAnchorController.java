/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.EdgeConnectorController;
import nl.utwente.ewi.caes.tactilefx.control.Anchor;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class EdgeCurvePositionAnchorController extends EdgeAnchorController {
    
    public EdgeCurvePositionAnchorController(EdgeController edge) {
        super(edge);
        
        edge.selectedProperty().addListener(o -> { 
            setVisible(edge.isSelected() || getConnector() == null);
        });
    }
    
    // PROPERTIES
    
    private final ReadOnlyObjectWrapper<EdgeConnectorController> connector = new ReadOnlyObjectWrapper<>();

    public EdgeConnectorController getConnector() {
        return connector.get();
    }

    public ReadOnlyObjectProperty connectorProperty() {
        return connector.getReadOnlyProperty();
    }
    
    // EVENT HANDLING
    
    @FXML
    void mouseEntered(MouseEvent event) {
        setVisible(true);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        if (getConnector() != null && !getEdge().isSelected()) {
            setVisible(false);
        }
    }
    
    @FXML
    void mousePressed(MouseEvent event) {
        MainController.getInstance().getCanvas().showEdgeConnectors(true);
        this.connector.set(null);
    }
    
    @FXML
    void mouseReleased(MouseEvent event) {
        for (Node node: TactilePane.getNodesInProximity(getView())) {
            Controller controller = Controller.getController(node);
            if (controller != null && controller instanceof EdgeConnectorController) {
                TactilePane.setAnchor(getView(), new Anchor(node, 5, 5));
                this.connector.set((EdgeConnectorController) controller);
                setVisible(false);
            }
        }
        MainController.getInstance().getCanvas().showEdgeConnectors(false);
    }
}
