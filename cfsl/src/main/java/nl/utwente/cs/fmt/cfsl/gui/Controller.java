/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author Richard
 * @param <T> The type of Node the Controller controls
 */
public abstract class Controller<T extends Node> {
    @FXML private T root;
    
    /**
     * Constructs a new Controller, and loads the default FXML file.
     */
    public Controller() {
        String simpleName = getClass().getSimpleName();
        String viewName = getClass().getSimpleName().substring(0, simpleName.length() - 10);

        loadFXML(viewName + "View");
    }
    
    /**
     * Constructs a new Controller, and loads the FXML file with the given name. The name must not contain
     * the .fxml extension, and must reside in the /fxml directory. The .fxml extension must be in lower-case.
     * 
     * @param fxmlName the name of the FXML file
     */
    public Controller(String fxmlName) {
        loadFXML(fxmlName);
    }
    
    /**
     * Constructs a new Controller for a given View.
     * 
     * @param view the View that the Controller should control.
     */
    public Controller(T view) {
        root = view;
    }
    
    /**
     * Loads the FXML file at /fxml/[fxmlName].fxml, and sets this object as its controller.
     * 
     * @param fxmlName the name of the FXML file
     */
    private void loadFXML(String fxmlName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/fxml/%s.fxml", fxmlName)));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load /fxml/%sView.fxml", fxmlName), e);
        }
    }
    
    /**
     * Returns the View of this Controller. This is the Node that is marked as "root" in the FXML file.
     * 
     * @return the View Node
     */
    public T getView() {
        return root;
    }
}
