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
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge.flow.FlowController;

/**
 *
 * @author Richard
 */
public class ToolController extends Controller<StackPane> {
    @FXML
    private StackPane root;
    
    private final Symbol symbol;
    private final CanvasElementController canvasElement;
    
    public ToolController(Symbol symbol) {
        super();
        
        this.symbol = symbol;
        
        switch (symbol) {
            case ABSTRACT_SYNTAX_ELEMENT:
                canvasElement = new ASEController();
                break;
            case FLOW:
                canvasElement = new FlowController();
                break;
            default:
                canvasElement = null;
                break;
        }
        
        canvasElement.getView().setDisable(true);
        getView().getChildren().add(canvasElement.getView());
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
        canvasElement.getView().setTranslateX(event.getSceneX() - initX);
        canvasElement.getView().setTranslateY(event.getSceneY() - initY);
        
        event.consume();
    }
    
    @FXML
    void mouseReleased(MouseEvent event) {
        canvasElement.getView().setTranslateX(0);
        canvasElement.getView().setTranslateY(0);
        
        CanvasController canvas = MainController.getInstance().getCanvas();
        Point2D point = canvas.getView().sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
        if (canvas.getView().getBoundsInLocal().contains(point)) {
            canvas.addNewCanvasElement(symbol, point.getX(), point.getY());
        }
        
        event.consume();
    }
}
