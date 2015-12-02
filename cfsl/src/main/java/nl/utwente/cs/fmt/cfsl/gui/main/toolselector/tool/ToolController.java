/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.MainController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphElementController;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphElementControllerFactory;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.GraphElement;

/**
 *
 * @author Richard
 */
public class ToolController extends Controller<VBox> {
    @FXML private Label label;
    
    private final Class<? extends GraphElement> type;
    private final GraphElementController graphElement;
    
    public ToolController(Class<? extends GraphElement> type) {
        super();
        
        this.type = type;
        graphElement = GraphElementControllerFactory.build(type);
        
        label.setText(graphElement.getToolName());
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
            canvas.addNewGraphElement(type, point.getX() - localX, point.getY() - localY);
        }
        
        event.consume();
    }
}
