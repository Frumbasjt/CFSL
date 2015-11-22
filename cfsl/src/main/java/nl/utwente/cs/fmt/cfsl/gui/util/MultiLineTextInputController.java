/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.util;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;

/**
 * A multi line text input control. New rows are automatically added each time 
 * the user presses enter, and removed when empty and lost focus. Each line
 * resizes automatically to accommodate text width.
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
        firstInput.setContextMenu(new ContextMenu());
        addEventHandlers(firstInput);
        TextFieldAutoSizer.addAutoSizeListener(firstInput, 30);
    }
    
    // PROPERTIES
    
    /**
     * The textual content of this control.
     */
    private final ReadOnlyStringWrapper totalText = new ReadOnlyStringWrapper();

    public String getTotalText() {
        return totalText.get();
    }

    public ReadOnlyStringProperty totalTextProperty() {
        return totalText.getReadOnlyProperty();
    }
    
    // PUBLIC METHODS
    
    public void requestFocus() {
        firstInput.requestFocus();
    }
    
    // HELP METHODS
    
    private TextField newInput() {
        TextField result = new TextField();
        result.setContextMenu(new ContextMenu());
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("minimal");
        TextFieldAutoSizer.addAutoSizeListener(result, 30);
        addEventHandlers(result);
        return result;
    }
    
    private void addEventHandlers(TextField tf) {
        tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> onKeyPressed(tf, e));
        tf.focusedProperty().addListener(o -> { 
            if (!tf.isFocused()) onLostFocus(tf);
        });
        tf.textProperty().addListener(o -> {
            StringBuilder sb = new StringBuilder();
            for (Node node : getView().getChildren()) {
                if (node instanceof TextField) {
                    sb.append(((TextField) node).getText());
                    sb.append("\n");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            this.totalText.set(sb.toString());
        });
    }
    
    // EVENT HANDLING
    
    private void onKeyPressed(TextField tf, KeyEvent e) {
        int index;
        switch (e.getCode()) {
            case ENTER:
                index = getView().getChildren().indexOf(tf);
                int caretPos = tf.getCaretPosition();
                TextField newInput = newInput();
                newInput.setText(tf.getText().substring(caretPos));
                tf.setText(tf.getText().substring(0, caretPos));
                getView().getChildren().add(index + 1, newInput);
                newInput.requestFocus();
                break;
            case LEFT:
            case BACK_SPACE:
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
    
    private void onLostFocus(TextField tf) {
        if (tf != firstInput) {
            if (tf.getText().isEmpty()) {
                getView().getChildren().remove(tf);
            }
        }
    }
}
