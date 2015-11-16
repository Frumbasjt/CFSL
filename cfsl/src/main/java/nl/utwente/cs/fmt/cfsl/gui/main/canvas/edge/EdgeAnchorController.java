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
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.EdgeConnectorController;
import nl.utwente.ewi.caes.tactilefx.control.Anchor;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class EdgeAnchorController extends Controller<Circle> {
    private final EdgeController edge;
    
    public EdgeAnchorController(EdgeController edge) {
        super();
        
        edge.selectedProperty().addListener(o -> { 
            if (edge.isSelected() || getConnector() == null) {
                getView().setOpacity(0.6);
            } else {
                getView().setOpacity(0);
            }
        });
        
        this.edge = edge;
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
        getView().setOpacity(0.6);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        if (getConnector() != null && !edge.isSelected()) {
            getView().setOpacity(0);
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
                getView().setOpacity(0);
            }
        }
        MainController.getInstance().getCanvas().showEdgeConnectors(false);
    }
}
