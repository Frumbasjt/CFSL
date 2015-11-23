/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool;

import nl.utwente.cs.fmt.cfsl.Symbol;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.ResolveAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.ResumeAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort.StartAbortController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.branch.BranchEdgeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.child.ChildController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.ase.ASEController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.flow.FlowController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.branch.BranchNodeController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.start.StartController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.node.stop.StopController;

/**
 *
 * @author Richard
 */
public class ToolController extends Controller<VBox> {
    @FXML private Label label;
    
    private final Symbol symbol;
    private final GraphElementController graphElement;
    
    public ToolController(Symbol symbol) {
        super();
        
        this.symbol = symbol;
        
        String text = "";
        switch (symbol) {
            case ABSTRACT_SYNTAX_ELEMENT:
                graphElement = new ASEController();
                text = "Abstract Syntax Element";
                break;
            case FLOW:
                graphElement = new FlowController();
                text = "Flow Edge";
                break;
            case START:
                graphElement = new StartController();
                text = "Start Node";
                break;
            case STOP:
                graphElement = new StopController();
                text = "Stop Node";
                break;
            case CHILD:
                graphElement = new ChildController();
                text = "Child Edge";
                break;
            case BRANCH_EDGE:
                graphElement = new BranchEdgeController();
                text = "Branch Edge";
                break;
            case BRANCH_NODE:
                graphElement = new BranchNodeController();
                text = "Branch Node";
                break;
            case START_ABORT:
                graphElement = new StartAbortController();
                text = "Initiate Abort Edge";
                break;
            case RESOLVE_ABORT:
                graphElement = new ResolveAbortController();
                text = "Resolve Abort Edge";
                break;
            case RESUME_ABORT:
                graphElement = new ResumeAbortController();
                text = "Resume Abort Edge";
                break;
            default:
                graphElement = null;
                break;
        }
        
        label.setText(text);
        graphElement.getView().setDisable(true);
        getView().getChildren().add(graphElement.getView());
    }
    
    private double initX;
    private double initY;
    private double localX;
    private double localY;
    
    @FXML
    void mousePressed(MouseEvent event) {
        initX = event.getSceneX();
        initY = event.getSceneY();
        Point2D localP = graphElement.getView().sceneToLocal(new Point2D(initX, initY));
        localX = localP.getX();
        localY = localP.getY();
        
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
            canvas.addNewGraphElement(symbol, point.getX() - localX, point.getY() - localY);
        }
        
        event.consume();
    }
}
