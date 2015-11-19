/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.child;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.TextFieldAutoSizer;

/**
 *
 * @author Richard
 */
public class ChildController extends EdgeController<Group> {
    @FXML private TextField textInput;
    @FXML private Circle origin;
    
    public ChildController() {
        // Bind origin circle to start of curve
        origin.layoutXProperty().bind(curve.startXProperty());
        origin.layoutYProperty().bind(curve.startYProperty());
        
        // Center text input
        middleProperty().addListener(o -> { 
            updateTextLocation();
        });
        
        // Autosize text input
        TextFieldAutoSizer.addAutoSizeListener(textInput, 30);
    }
    
    private void updateTextLocation() {
        Point2D middle = getMiddle();
        textInput.relocate(middle.getX() - textInput.getWidth() / 2, middle.getY() - textInput.getHeight() / 2);
    }
}
