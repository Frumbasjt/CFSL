/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.exit;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.model.ExitEdge;

/**
 *
 * @author Richard
 */
public class ExitEdgeController extends EdgeController<ExitEdge>{
    @FXML private Label label;
    
    public ExitEdgeController(ExitEdge model) {
        super("Exit Relation Edge", model);
        
        // Center text input
        middleProperty().addListener(o -> updateTextLocation());
        label.layoutBoundsProperty().addListener(o -> updateTextLocation());
    }
    
    // HELP METHODS
    
    private void updateTextLocation() {
        Point2D middle = getMiddle();
        label.relocate(middle.getX() - label.getWidth() / 2, middle.getY() - label.getHeight() / 2);
    }
}
