/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.child;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.EdgeController;
import nl.utwente.cs.fmt.cfsl.gui.util.TextFieldAutoSizer;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ChildEdge;

/**
 *
 * @author Richard
 */
public class ChildController extends EdgeController<ChildEdge> {
    @FXML private TextField textInput;
    @FXML private Rectangle origin;
    @FXML private Rectangle end;
    
    public ChildController(ChildEdge model) {
        super("Child Edge", model);
        
        // Bind origin circle to start of curve
        origin.layoutXProperty().bind(curve.startXProperty().subtract(origin.widthProperty().divide(2)));
        origin.layoutYProperty().bind(curve.startYProperty().subtract(origin.heightProperty().divide(2)));
        end.layoutXProperty().bind(curve.endXProperty().subtract(end.widthProperty().divide(2)));
        end.layoutYProperty().bind(curve.endYProperty().subtract(end.heightProperty().divide(2)));
        
        // Center text input
        middleProperty().addListener(o -> updateTextLocation());
        textInput.layoutBoundsProperty().addListener(o -> updateTextLocation());
        
        // Autosize text input
        TextFieldAutoSizer.addAutoSizeListener(textInput, 30);
        
        model.labelProperty().bind(textInput.textProperty());
    }
    
    // HELP METHODS
    
    private void updateTextLocation() {
        Point2D middle = getMiddle();
        textInput.relocate(middle.getX() - textInput.getWidth() / 2, middle.getY() - textInput.getHeight() / 2);
    }
}
