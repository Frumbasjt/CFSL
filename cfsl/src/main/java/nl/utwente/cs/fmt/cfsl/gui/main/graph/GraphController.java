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
import nl.utwente.cs.fmt.cfsl.Symbol;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.AbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.ResolveAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.ResumeAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.StartAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.child.ChildController;
import nl.utwente.cs.fmt.cfsl.gui.util.Utils;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.NodeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.branch.BranchNodeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.start.StartController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.stop.StopController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class GraphController extends Controller<StackPane> {
    
    @FXML private TactilePane canvas;
    
    private final SelectionController selectionBox;
    
    public GraphController() {
        canvas.setProximityThreshold(10);
        
        selectionBox = new SelectionController(this);
        canvas.getChildren().add(selectionBox.getView());
        
        canvas.setOnMousePressed(e -> setSelectedElement(null));
    }
    
    // PROPERTIES
    
    /**
     * Returns the TactilePane that contains the GraphElements.
     * 
     * @return a TactilePane
     */
    public TactilePane getContainer() {
        return canvas;
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
    
    public void addNewGraphElement(Symbol symbol, double x, double y) {
        
        GraphElementController graphElement = null;
        switch (symbol) {
            case ABSTRACT_SYNTAX_ELEMENT:
                graphElement = new ASEController();
                break;
            case FLOW:
                graphElement = new FlowController();
                break;
            case START:
                graphElement = new StartController();
                break;
            case STOP:
                graphElement = new StopController();
                break;
            case CHILD:
                graphElement = new ChildController();
                break;
            case BRANCH_EDGE:
                graphElement = new BranchEdgeController();
                break;
            case BRANCH_NODE:
                graphElement = new BranchNodeController();
                break;
            case START_ABORT:
                graphElement = new StartAbortController();
                break;
            case RESOLVE_ABORT:
                graphElement = new ResolveAbortController();
                break;
            case RESUME_ABORT:
                graphElement = new ResumeAbortController();
                break;
        }
        
        if (graphElement == null) return;
        
        // Add new view to the canvas
        Node view = graphElement.getView();
        canvas.getChildren().add(view);
        view.applyCss();
        
        // Relocate the view so its center is located at (centerX, centerY), if space allows
        Bounds viewBounds = view.getBoundsInLocal();
        view.relocate(Math.max(x, 0), Math.max(y, 0));
        
        // Resize the canvas if necessary
        Bounds minBounds = Utils.getChildrenBounds(canvas);
        canvas.setPrefWidth(Math.max(canvas.getWidth(), Math.max(minBounds.getWidth(), minBounds.getMaxX())));
        canvas.setPrefHeight(Math.max(canvas.getHeight(), minBounds.getMaxY()));
        
        // Initialise graph element
        graphElement._addToGraph(this);
    }
    
    public void showEdgeConnectors(boolean show) {
        List<Node> children = new ArrayList<>();
        for (Node child: canvas.getChildren()) {
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
}
