/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgePositionAnchorController.EdgePosition;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;

/**
 *
 * @author Richard
 */
public class ASEController extends NodeController {
    private final MultiLineTextInputController textInput;
    
    private FlowController flowOut;
    
    public ASEController() {
        textInput = new MultiLineTextInputController("Abstract Syntax Element");
        ((StackPane)getView()).getChildren().add(textInput.getView());
    }
    
    @Override
    public void initCanvas(GraphController canvas) {
        StackPane thisView = (StackPane) getView();
        EdgeConnectorController connector;
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.TOP_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.TOP_CENTER, 
                null, 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.TOP_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.BOTTOM_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.BOTTOM_CENTER, 
                null, 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(),
                Pos.BOTTOM_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(), 
                Pos.CENTER_LEFT, 
                connector.getView().radiusProperty().multiply(-1).subtract(10), 
                null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getCanvasView(), 
                Pos.CENTER_RIGHT, 
                connector.getView().radiusProperty().add(10), 
                null);
        
        textInput.requestFocus();
    }

    @Override
    public boolean connect(EdgeController edge, EdgePosition position) {
        if (position == EdgePosition.END) {
            return true;
        } else {
            // Only one outgoing flow possible
            if (edge instanceof FlowController) {
                if (flowOut == null) {
                    flowOut = (FlowController) edge;
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void disconnect(EdgeController edge, EdgePosition position) {
        if (position == EdgePosition.END) {
            
        } else {
            if (edge instanceof FlowController) {
                if (flowOut == edge) {
                    flowOut = null;
                }
            }
        }
    }

    @Override
    public boolean canConnect(EdgeController edge, EdgePosition position) {
        return position == EdgePosition.END || !(edge instanceof FlowController) || flowOut == null;
    }
}
