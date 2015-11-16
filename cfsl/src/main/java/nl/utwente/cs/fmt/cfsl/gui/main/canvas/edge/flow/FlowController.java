/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.flow;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.EdgeController;

/**
 *
 * @author Richard
 */
public class FlowController extends EdgeController<Group> {

    @FXML Label label;
    
    public FlowController() {
        // Center text input
        label.setSnapToPixel(true);
        label.layoutXProperty().bind(label.widthProperty().divide(-2).add(line.endXProperty().add(line.startXProperty()).divide(2)));
        label.layoutYProperty().bind(label.heightProperty().divide(-2).add(line.endYProperty().add(line.startYProperty()).divide(2)));
    }
    
}
