/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.ase;

import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;

/**
 *
 * @author Richard
 */
public class ASEController extends NodeController {

    private final MultiLineTextInputController textInput;
    
    public ASEController() {
        textInput = new MultiLineTextInputController("Abstract Syntax Element");
        ((StackPane)getView()).getChildren().add(textInput.getView());
    }
    
    @Override
    public void initCanvas(CanvasController canvas) {
        super.initCanvas(canvas);
        textInput.requestFocus();
    }
}
