/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool;

import nl.utwente.cs.fmt.cfsl.Symbol;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.child.ChildController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.start.StartController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.stop.StopController;

/**
 *
 * @author Richard
 */
public class ToolController extends Controller<StackPane> {
    
    private final Symbol symbol;
    private final GraphElementController graphElement;
    
    public ToolController(Symbol symbol) {
        super();
        
        this.symbol = symbol;
        
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
            default:
                graphElement = null;
                break;
        }
        
        graphElement.getView().setDisable(true);
        getView().getChildren().add(graphElement.getView());
    }
    
    private double initX;
    private double initY;
    
    @FXML
    void mousePressed(MouseEvent event) {
        initX = event.getSceneX();
        initY = event.getSceneY();
        
        event.consume();
    }
    
    @FXML
    void mouseDragged(MouseEvent event) {
        graphElement.getView().setTranslateX(event.getSceneX() - initX);
        graphElement.getView().setTranslateY(event.getSceneY() - initY);
        
        event.consume();
    }
    
    @FXML
    void mouseReleased(MouseEvent event) {
        graphElement.getView().setTranslateX(0);
        graphElement.getView().setTranslateY(0);
        
        GraphController canvas = MainController.getInstance().getCanvas();
        Point2D point = canvas.getView().sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
        if (canvas.getView().getBoundsInLocal().contains(point)) {
            canvas.addNewCanvasElement(symbol, point.getX(), point.getY());
        }
        
        event.consume();
    }
}
