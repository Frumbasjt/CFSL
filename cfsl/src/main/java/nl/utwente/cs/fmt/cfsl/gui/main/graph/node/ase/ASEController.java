/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbstractSyntaxElement;

/**
 *
 * @author Richard
 */
public class ASEController extends NodeController<AbstractSyntaxElement> {
    @FXML private StackPane identifierPane;
    @FXML private TextField identifierTextInput;
    
    private final MultiLineTextInputController textInput;
    
    public ASEController(AbstractSyntaxElement model) {
        super("Abstract Syntax Element", model);
        
        textInput = new MultiLineTextInputController("Abstract Syntax Element");
        getView().getChildren().add(textInput.getView());
        
        identifierPane.setVisible(false);
        
        // Add context menu
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem item1 = new MenuItem("Set identifier");
        item1.setOnAction(e -> setIdentifierVisible(!isIdentifierVisible()));
        contextMenu.getItems().add(item1);
        
        MenuItem item2 = new MenuItem("Make key element");
        item2.setOnAction(e -> MainController.getInstance().getCanvas().setKeyElement(this));
        contextMenu.getItems().add(item2);
        
        getView().setOnMouseClicked(e -> { 
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(getView(), e.getScreenX(), e.getScreenY());
            }
        });
        
        identifierVisible.addListener(o -> { 
            if (isIdentifierVisible()) {
                item1.setText("Remove identifier");
                identifierPane.setVisible(true);
                identifierTextInput.requestFocus();
            } else {
                item1.setText("Set identifier");
                identifierPane.setVisible(false);
                identifierTextInput.setText(null);
            }
        });
        
        keyElement.addListener(o -> { 
            if (isKeyElement()) {
                getView().getStyleClass().add("key-element");
            } else {
                getView().getStyleClass().remove("key-element");
            }
        });
        
        // Control model
        model.keyElementProperty().bind(keyElement);
        model.idProperty().bind(identifierTextInput.textProperty());
        textInput.totalTextProperty().addListener(o -> { 
            model.getLabels().clear();
            String labels[] = textInput.getTotalText().split("\n");
            for (int i = 0; i < labels.length; i++) {
                model.getLabels().add(labels[i]);
            }
        });
    }
    
    // PROPERTIES
    
    /**
     * Whether the identifier should be visible.
     */
    private final BooleanProperty identifierVisible = new SimpleBooleanProperty(false);

    public boolean isIdentifierVisible() {
        return identifierVisible.get();
    }

    public void setIdentifierVisible(boolean value) {
        identifierVisible.set(value);
    }

    public BooleanProperty identifierVisibleProperty() {
        return identifierVisible;
    }
    
    /**
     * Whether the abstract syntax element is the key element.
     */
    private final BooleanProperty keyElement = new SimpleBooleanProperty(false);

    public boolean isKeyElement() {
        return keyElement.get();
    }

    public void setKeyElement(boolean value) {
        keyElement.set(value);
    }

    public BooleanProperty keyElementProperty() {
        return keyElement;
    }
    
    // GRAPH ELEMENT CONTROLLER IMPLEMENTATION
    
    @Override
    public void afterAddedToGraph(GraphController graph) {
        super.afterAddedToGraph(graph);
        
        StackPane thisView = (StackPane) getView();
        EdgeConnectorController connector;
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.TOP_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.TOP_CENTER, 
                null, 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.TOP_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.BOTTOM_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.BOTTOM_CENTER, 
                null, 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(),
                Pos.BOTTOM_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(), 
                Pos.CENTER_LEFT, 
                connector.getView().radiusProperty().multiply(-1).subtract(10), 
                null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, graph.getContainer(), 
                Pos.CENTER_RIGHT, 
                connector.getView().radiusProperty().add(10), 
                null);
        
        textInput.requestFocus();
    }
}
