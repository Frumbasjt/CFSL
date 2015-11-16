/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.node;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;

/**
 *
 * @author Richard
 */
public abstract class NodeController extends CanvasElementController<StackPane> {
    EdgeConnectorController ec[];
    
    @Override
    public void initCanvas(CanvasController canvas) {
        ec = new EdgeConnectorController[8];
        Circle ecv[] = new Circle[8];
        StackPane thisView = (StackPane) getView();
        
        ec[0] = new EdgeConnectorController(this);
        ecv[0] = (Circle) ec[0].getView();
        StackPane.setAlignment(ecv[0], Pos.TOP_CENTER);
        ecv[0].translateYProperty().bind(ecv[0].radiusProperty().multiply(-1).subtract(10));
        
        ec[1] = new EdgeConnectorController(this);
        ecv[1] = (Circle) ec[1].getView();
        StackPane.setAlignment(ecv[1], Pos.TOP_CENTER);
        ecv[1].translateYProperty().bind(ecv[1].radiusProperty().multiply(-1).subtract(10));
        ecv[1].translateXProperty().bind(thisView.widthProperty().divide(-3));
        
        ec[2] = new EdgeConnectorController(this);
        ecv[2] = (Circle) ec[2].getView();
        StackPane.setAlignment(ecv[2], Pos.TOP_CENTER);
        ecv[2].translateYProperty().bind(ecv[2].radiusProperty().multiply(-1).subtract(10));
        ecv[2].translateXProperty().bind(thisView.widthProperty().divide(3));
        
        ec[3] = new EdgeConnectorController(this);
        ecv[3] = (Circle) ec[3].getView();
        StackPane.setAlignment(ecv[3], Pos.BOTTOM_CENTER);
        ecv[3].translateYProperty().bind(ecv[3].radiusProperty().add(10));
        
        ec[4] = new EdgeConnectorController(this);
        ecv[4] = (Circle) ec[4].getView();
        StackPane.setAlignment(ecv[4], Pos.BOTTOM_CENTER);
        ecv[4].translateYProperty().bind(ecv[4].radiusProperty().add(10));
        ecv[4].translateXProperty().bind(thisView.widthProperty().divide(-3));
        
        ec[5] = new EdgeConnectorController(this);
        ecv[5] = (Circle) ec[5].getView();
        StackPane.setAlignment(ecv[5], Pos.BOTTOM_CENTER);
        ecv[5].translateYProperty().bind(ecv[5].radiusProperty().add(10));
        ecv[5].translateXProperty().bind(thisView.widthProperty().divide(3));
        
        ec[6] = new EdgeConnectorController(this);
        ecv[6] = (Circle) ec[6].getView();
        StackPane.setAlignment(ecv[6], Pos.CENTER_LEFT);
        ecv[6].translateXProperty().bind(ecv[6].radiusProperty().multiply(-1).subtract(10));
        
        ec[7] = new EdgeConnectorController(this);
        ecv[7] = (Circle) ec[7].getView();
        StackPane.setAlignment(ecv[7], Pos.CENTER_RIGHT);
        ecv[7].translateXProperty().bind(ecv[7].radiusProperty().add(10));
        
        for (int i = 0; i < 8; i++) {
            thisView.getChildren().add(ecv[i]);
            canvas.getCanvasView().getActiveNodes().add(ecv[i]);
        }
    }
    
    public void showEdgeConnectors(boolean show) {
        if (ec != null) {
            for (int i = 0; i < ec.length; i++) {
                ec[i].getView().setVisible(show);
            }
        }
    }
}
