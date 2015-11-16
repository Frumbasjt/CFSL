/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.ase;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.util.TextAreaAutoSizer;

/**
 *
 * @author Richard
 */
public class ASEController extends NodeController {
    @FXML
    private TextArea textInput;

    public ASEController() {
        TextAreaAutoSizer.addAutoSizeListener(textInput, 30);
        textInput.setCache(false);
    }
    
    @Override
    public void initCanvas(CanvasController canvas) {
        super.initCanvas(canvas);
        textInput.requestFocus();
    }
}
