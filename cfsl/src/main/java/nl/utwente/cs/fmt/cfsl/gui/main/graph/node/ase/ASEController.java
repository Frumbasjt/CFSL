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
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.EdgeConnectorController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.util.MultiLineTextInputController;
import nl.utwente.cs.fmt.cfsl.model.AbstractSyntaxElement;

/**
 *
 * @author Richard
 */
public class ASEController extends NodeController<AbstractSyntaxElement> {
    @FXML private StackPane identifierPane;
    @FXML private TextField identifierTextInput;
    
    private final MultiLineTextInputController textInput;
    
    private FlowController flowOut;
    private BranchEdgeController branchOut;
    
    public ASEController(AbstractSyntaxElement model) {
        super(model);
        
        textInput = new MultiLineTextInputController("Abstract Syntax Element");
        ((StackPane)getView()).getChildren().add(textInput.getView());
        
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
                identifierTextInput.setText("");
            }
        });
        
        keyElement.addListener(o -> { 
            if (isKeyElement()) {
                getView().getStyleClass().add("key-element");
                getModel().setKeyElement(true);
            } else {
                getView().getStyleClass().remove("key-element");
                getModel().setKeyElement(false);
            }
        });
    }
    
    // PROPERTIES
    
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
    
    @Override
    public String getToolName() {
        return "Abstract Syntax Element";
    }
    
    // GRAPH ELEMENT CONTROLLER IMPLEMENTATION
    
    @Override
    public void afterAddedToGraph(GraphController canvas) {
        StackPane thisView = (StackPane) getView();
        EdgeConnectorController connector;
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.TOP_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.TOP_CENTER, 
                null, 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.TOP_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().multiply(-1).subtract(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.BOTTOM_LEFT, 
                thisView.widthProperty().divide(6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.BOTTOM_CENTER, 
                null, 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(),
                Pos.BOTTOM_RIGHT, 
                thisView.widthProperty().divide(-6), 
                connector.getView().radiusProperty().add(10));
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(), 
                Pos.CENTER_LEFT, 
                connector.getView().radiusProperty().multiply(-1).subtract(10), 
                null);
        
        connector = new EdgeConnectorController(this);
        addEdgeConnector(connector, canvas.getContainer(), 
                Pos.CENTER_RIGHT, 
                connector.getView().radiusProperty().add(10), 
                null);
        
        textInput.requestFocus();
    }
}
