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
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 *
 * @author Richard
 */
public class TextAreaAutoSizer {
    private static final Map<TextArea, InvalidationListener> listenerByArea = new HashMap<>();
    
    public static void addAutoSizeListener(TextArea textArea, double margin) {
        InvalidationListener listener = o -> {
            updateTextArea(textArea, margin);
        };
        
        textArea.textProperty().addListener(listener);
        listenerByArea.put(textArea, listener);
    }
    
    public static void removeAutoSizeListener(TextArea textArea) {
        textArea.textProperty().removeListener(listenerByArea.remove(textArea));
    }
    
    private static void updateTextArea(TextArea textArea, double margin) {
        String string = textArea.getText();
            if (string.equals("") || string.endsWith("\n")) {
                string += " ";
            }
            String[] lines = string.split("\n");
            double maxWidth = 0;

            Group root = new Group();
            Scene tempScene = new Scene(root);
            //tempScene.getStylesheets().addAll(textArea.getScene().getStylesheets());
            for (String line: lines) {
                Text text = new Text(line);
                text.setFont(textArea.getFont());
                text.applyCss();
                maxWidth = Math.max(maxWidth, text.getLayoutBounds().getWidth());
            }
            
            textArea.setPrefRowCount(lines.length);
            textArea.setPrefWidth(Math.ceil(maxWidth + margin));
    }
}
