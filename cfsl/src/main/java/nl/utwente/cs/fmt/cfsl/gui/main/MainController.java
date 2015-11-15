/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.toolselector.ToolSelectorController;

/**
 *
 * @author Richard
 */
public class MainController extends Controller<BorderPane>{
    private static final MainController instance = new MainController();
    
    public static MainController getInstance() {
        return instance;
    }
    
    @FXML
    private ScrollPane canvasContainer;
    
    private final CanvasController canvasController = new CanvasController();
    private final ToolSelectorController toolSelectorController = new ToolSelectorController();
    
    private MainController() {
        canvasContainer.setContent(new Group(canvasController.getView()));
        getView().setLeft(toolSelectorController.getView());
    }
    
    public CanvasController getCanvas() {
        return this.canvasController;
    }
}
