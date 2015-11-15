/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.util;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Richard
 */
public class TextFieldAutoSizer {
    private static final Map<TextField, InvalidationListener> listenerByField = new HashMap<>();
    
    public static void addAutoSizeListener(TextField textField, double margin) {
        InvalidationListener listener = o -> {
            textField.setPrefWidth(getTextWidth(textField) + margin);
        };
        
        textField.textProperty().addListener(listener);
        listenerByField.put(textField, listener);
    }
    
    public static void removeAutoSizeListener(TextField textField) {
        textField.textProperty().removeListener(listenerByField.remove(textField));
    }
    
    private static double getTextWidth(TextField textField) {
        Text text = new Text(textField.getText());
        text.setFont(textField.getFont());
        Scene tempScene = new Scene(new Group(text));
        //tempScene.getStylesheets().addAll(textField.getScene().getStylesheets());
        text.applyCss();
        
        return text.getLayoutBounds().getWidth();
    }
}
