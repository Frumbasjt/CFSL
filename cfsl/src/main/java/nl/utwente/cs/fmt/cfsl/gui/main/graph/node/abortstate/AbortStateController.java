/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.abortstate;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbortStateNode;

/**
 *
 * @author Richard
 */
public class AbortStateController extends NodeController<AbortStateNode>{
    @FXML private Circle circle;
    @FXML private Line line1;
    @FXML private Line line2;
    
    public AbortStateController(AbortStateNode model) {
        super("Abort State Node", model);
        
        double m = Math.sqrt(2) / 2;
        DoubleProperty r = circle.radiusProperty();
        line1.startXProperty().bind(r.subtract(r.multiply(m)));
        line1.startYProperty().bind(r.subtract(r.multiply(m)));
        line1.endXProperty().bind(r.add(r.multiply(m)));
        line1.endYProperty().bind(r.add(r.multiply(m)));
        line2.startXProperty().bind(r.add(r.multiply(m)));
        line2.startYProperty().bind(r.subtract(r.multiply(m)));
        line2.endXProperty().bind(r.subtract(r.multiply(m)));
        line2.endYProperty().bind(r.add(r.multiply(m)));
        
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
    }
}
