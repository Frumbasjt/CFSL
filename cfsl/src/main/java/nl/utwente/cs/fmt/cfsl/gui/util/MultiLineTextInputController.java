/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.util;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;

/**
 *
 * @author Richard
 */
public class MultiLineTextInputController extends Controller<VBox> {
    @FXML private TextField firstInput;
    
    // CONSTRUCTOR
    
    public MultiLineTextInputController() {
        this("");
    }
    
    public MultiLineTextInputController(String initialText) {
        firstInput.setText(initialText);
        addEventHandlers(firstInput);
        TextFieldAutoSizer.addAutoSizeListener(firstInput, 30);
    }
    
    // PUBLIC METHODS
    
    public void requestFocus() {
        firstInput.requestFocus();
    }
    
    // HELP METHODS
    
    private TextField newInput() {
        TextField result = new TextField();
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("minimal");
        TextFieldAutoSizer.addAutoSizeListener(result, 30);
        addEventHandlers(result);
        return result;
    }
    
    private void addEventHandlers(TextField tf) {
        tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> handleKeyPress(tf, e));
        tf.focusedProperty().addListener(o -> { 
            if (!tf.isFocused()) handleLostFocus(tf);
        });
    }
    
    // EVENT HANDLING
    
    private void handleKeyPress(TextField tf, KeyEvent e) {
        int index;
        switch (e.getCode()) {
            case ENTER:
                index = getView().getChildren().indexOf(tf);
                TextField newInput = newInput();
                getView().getChildren().add(index + 1, newInput);
                newInput.requestFocus();
                break;
            case LEFT:
                if (tf.getCaretPosition() == 0) {
                    index = getView().getChildren().indexOf(tf);
                    if (index > 0) {
                        TextField prev = (TextField) getView().getChildren().get(index - 1);
                        prev.requestFocus();
                        prev.positionCaret(prev.getText().length());
                    }
                }
                break;
            case RIGHT:
                if (tf.getCaretPosition() == tf.getText().length()) {
                    index = getView().getChildren().indexOf(tf);
                    if (index < getView().getChildren().size() - 1) {
                        TextField next = (TextField) getView().getChildren().get(index + 1);
                        next.requestFocus();
                        next.positionCaret(0);
                    }
                }
                break;
        }
    }
    
    private void handleLostFocus(TextField tf) {
        if (tf != firstInput) {
            if (tf.getText().isEmpty()) {
                getView().getChildren().remove(tf);
            }
        }
    }
}
