/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.Symbol;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.NodeController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class CanvasController extends Controller<StackPane> {
    @FXML
    private TactilePane canvas;
    
    public CanvasController() {
        canvas.setProximityThreshold(10);
    }
    
    // PROPERTIES
    
    public TactilePane getCanvasView() {
        return canvas;
    }
    
    private final ObjectProperty<CanvasElementController> selectedElement = new SimpleObjectProperty<CanvasElementController>() {
        @Override
        public void set(CanvasElementController value) {
            if (get() != value) {
                if (get() != null) get().setSelected(false);
                super.set(value);
            }
        }
    };

    public CanvasElementController getSelectedElement() {
        return selectedElement.get();
    }

    public void setSelectedElement(CanvasElementController value) {
        selectedElement.set(value);
    }

    public ObjectProperty selectedElementProperty() {
        return selectedElement;
    }
    
    // PUBLIC METHODS
    
    public void addNewCanvasElement(Symbol symbol, double centerX, double centerY) {
        
        CanvasElementController canvasElement = null;
        switch (symbol) {
            case ABSTRACT_SYNTAX_ELEMENT:
                canvasElement = new ASEController();
                break;
            case FLOW:
                canvasElement = new FlowController();
                break;
        }
        
        if (canvasElement == null) return;
        
        // Add new view to the canvas
        Node view = canvasElement.getView();
        canvas.getChildren().add(view);
        view.applyCss();
        
        // Relocate the view so its center is located at (centerX, centerY), if space allows
        Bounds viewBounds = view.getBoundsInLocal();
        view.relocate(Math.max(0 - viewBounds.getMinX(), centerX - viewBounds.getWidth() / 2), Math.max(0 - viewBounds.getMinY(), centerY - viewBounds.getHeight() / 2));
        
        // Resize the canvas if necessary
        Bounds minBounds = Utils.getChildrenBounds(canvas);
        canvas.setPrefWidth(Math.max(canvas.getWidth(), Math.max(minBounds.getWidth(), minBounds.getMaxX())));
        canvas.setPrefHeight(Math.max(canvas.getHeight(), minBounds.getMaxY()));
        
        // Initialise canvas element
        canvasElement.initCanvas(this);
    }
    
    public void showEdgeConnectors(boolean show) {
        for (Node child: canvas.getChildren()) {
            Controller controller = Controller.getController(child);
            if (controller != null && controller instanceof NodeController) {
                ((NodeController) controller).showEdgeConnectors(show);
            }
        }
    }
    
    // EVENT HANDLING
    
    private double initWidth;
    private double initHeight;
    private double initX;
    private double initY;
    
    @FXML
    void anchorMousePressed(MouseEvent event) {
        initWidth = canvas.getWidth();
        initHeight = canvas.getHeight();
        initX = event.getScreenX();
        initY = event.getScreenY();
    }
    
    @FXML
    void verticalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(canvas);
        canvas.setPrefHeight(Math.max(minBounds.getMaxY(), initHeight + (event.getScreenY() - initY)));
    }
    
    @FXML
    void horizontalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(canvas);
        canvas.setPrefWidth(Math.max(minBounds.getMaxX(), initWidth + (event.getScreenX() - initX)));
    }
    
    @FXML
    void diagonalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(canvas);
        canvas.setPrefWidth(Math.max(minBounds.getMaxX(), initWidth + (event.getScreenX() - initX)));
        canvas.setPrefHeight(Math.max(minBounds.getMaxY(), initHeight + (event.getScreenY() - initY)));
    }
    
    @FXML
    void canvasClicked(MouseEvent event) {
        setSelectedElement(null);
    }
}
