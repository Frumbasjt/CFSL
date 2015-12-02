/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.Graph;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.GraphElement;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.CfslPlusGraph;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class GraphController extends Controller<StackPane> {
    
    @FXML private TactilePane container;
    
    private final SelectionController selectionBox;
    private final Graph model;
    
    public GraphController() {
        this(new CfslPlusGraph());
    }
    
    public GraphController(CfslPlusGraph model) {
        this.model = model;
        
        container.setProximityThreshold(10);
        
        selectionBox = new SelectionController(this);
        container.getChildren().add(selectionBox.getView());
        
        container.setOnMousePressed(e -> setSelectedElement(null));
    }
    
    // PROPERTIES
    
    /**
     * Returns the TactilePane that contains the GraphElements.
     * 
     * @return a TactilePane
     */
    public TactilePane getContainer() {
        return container;
    }
    
    /**
     * The CanvasElement that is currently selected.
     */
    private final ObjectProperty<GraphElementController> selectedElement = new SimpleObjectProperty<GraphElementController>() {
        @Override
        public void set(GraphElementController value) {
            if (get() != value) {
                if (get() != null) get().setSelected(false);
                if (value != null) value.setSelected(true);
                super.set(value);
            }
        }
    };

    public GraphElementController getSelectedElement() {
        return selectedElement.get();
    }

    public void setSelectedElement(GraphElementController value) {
        selectedElement.set(value);
    }

    public ObjectProperty<GraphElementController> selectedElementProperty() {
        return selectedElement;
    }
    
    /**
     * The Abstract Syntax Element that is the key element.
     */
    private final ObjectProperty<ASEController> keyElement = new SimpleObjectProperty<ASEController>() {
        @Override
        public void set(ASEController value) {
            if (get() != value) {
                if (get() != null) get().setKeyElement(false);
                if (value != null) value.setKeyElement(true);
                super.set(value);
            }
        }
    };

    public ASEController getKeyElement() {
        return keyElement.get();
    }

    public void setKeyElement(ASEController value) {
        keyElement.set(value);
    }

    public ObjectProperty keyElementProperty() {
        return keyElement;
    }
    
    // PUBLIC METHODS
    
    /**
     * Creates a new GraphElementController and adds it to the graph.
     * 
     * @param type the type of the model of the controller
     * @param x the horizontal location of the graph element
     * @param y the vertical location of the graph element
     */
    public void addNewGraphElement(Class<? extends GraphElement> type, double x, double y) {
        GraphElementController graphElement = null;
        graphElement = GraphElementControllerFactory.build(type);
        
        if (graphElement == null) return;
        
        // Update model
        model.add(graphElement.getModel());
        
        // Add new view to the canvas
        Node view = graphElement.getView();
        container.getChildren().add(view);
        view.relocate(Math.max(x, 0), Math.max(y, 0));
        
        // Resize the canvas if necessary
        Bounds minBounds = Utils.getChildrenBounds(container);
        container.setPrefWidth(Math.max(container.getWidth(), Math.max(minBounds.getWidth(), minBounds.getMaxX())));
        container.setPrefHeight(Math.max(container.getHeight(), minBounds.getMaxY()));
        
        // Initialise graph element
        graphElement._setGraph(this);
        graphElement.afterAddedToGraph(this);
    }
    
    /**
     * Removes a graph element from the graph
     * @param graphElement the controller of the graph element
     */
    public void removeGraphElement(GraphElementController graphElement) {
        if (getSelectedElement() == graphElement) {
            setSelectedElement(null);
        }
        model.remove(graphElement.getModel());
        getContainer().getChildren().remove(graphElement.getView());
    }
    
    /**
     * Show or hide all edge connectors from all nodes in the graph.
     * @param show whether to show or hide the connectors
     */
    public void showAllEdgeConnectors(boolean show) {
        List<Node> children = new ArrayList<>();
        for (Node child: container.getChildren()) {
            children.add(child);
        }
        for (Node child: children) {
            Controller controller = Controller.getController(child);
            if (controller != null && controller instanceof NodeController) {
                ((NodeController) controller).showEdgeConnectors(show);
            }
        }
    }
    
    // EVENT HANDLING
    
    private double initX;
    private double initY;
    private double initWidth;
    private double initHeight;
    
    @FXML
    void anchorMousePressed(MouseEvent event) {
        initX = event.getScreenX();
        initY = event.getScreenY();
        initWidth = container.getWidth();
        initHeight = container.getHeight();
    }
    
    @FXML
    void verticalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(container);
        container.setPrefHeight(Math.max(minBounds.getMaxY(), initHeight + (event.getScreenY() - initY)));
    }
    
    @FXML
    void horizontalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(container);
        container.setPrefWidth(Math.max(minBounds.getMaxX(), initWidth + (event.getScreenX() - initX)));
    }
    
    @FXML
    void diagonalAnchorMouseDragged(MouseEvent event) {
        Bounds minBounds = Utils.getChildrenBounds(container);
        container.setPrefWidth(Math.max(minBounds.getMaxX(), initWidth + (event.getScreenX() - initX)));
        container.setPrefHeight(Math.max(minBounds.getMaxY(), initHeight + (event.getScreenY() - initY)));
    }
}
