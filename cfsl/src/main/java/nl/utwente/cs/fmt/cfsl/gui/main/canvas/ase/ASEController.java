/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.ase;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.util.TextFieldAutoSizer;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;

/**
 *
 * @author Richard
 */
public class ASEController extends CanvasElementController<StackPane> {
    @FXML
    private TextField textInput;

    public ASEController() {
        TextFieldAutoSizer.addAutoSizeListener(textInput, 30);
        
        // Remove focus when enter is pressed
        textInput.addEventFilter(KeyEvent.KEY_PRESSED, e -> { 
            if (e.getCode() == KeyCode.ENTER) {
                getView().requestFocus();
            }
        });
    }
    
    @Override
    public void initCanvas(CanvasController canvas) {
        textInput.requestFocus();
    }
}
