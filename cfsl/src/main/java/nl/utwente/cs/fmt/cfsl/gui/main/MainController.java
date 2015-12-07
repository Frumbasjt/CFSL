/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main;

import groove.graph.plain.PlainGraph;
import groove.io.graph.AutIO;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import nl.utwente.cs.fmt.cfsl.compiler.CfslPlusCompiler;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.graph.GraphController;
import nl.utwente.cs.fmt.cfsl.gui.main.toolselector.ToolSelectorController;

/**
 *
 * @author Richard
 */
public class MainController extends Controller<BorderPane>{
    // STATIC
    
    private static final MainController instance = new MainController();
    
    public static MainController getInstance() {
        return instance;
    }
    
    // INSTANCE
    
    @FXML
    private ScrollPane canvasContainer;
    
    private final GraphController graphController = new GraphController();
    private final ToolSelectorController toolSelectorController = new ToolSelectorController();
    
    private MainController() {
        Group canvasWrapper = new Group(graphController.getView());
        canvasWrapper.setCache(false);
        canvasContainer.setContent(canvasWrapper);
        getView().setLeft(toolSelectorController.getView());
    }
    
    public GraphController getGraph() {
        return this.graphController;
    }
    
    @FXML
    void exportToGrooveClicked(ActionEvent event) {
        CfslPlusCompiler compiler = new CfslPlusCompiler();
        PlainGraph cfslGraph = compiler.compile(graphController.getModel());
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export diagram to GROOVE");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CADP automata files", "aut"));
        fileChooser.setInitialFileName("diagram.aut");
        File file = fileChooser.showSaveDialog(getView().getScene().getWindow());
        
        if (file != null) {
            try {
                AutIO io = new AutIO();
                io.saveGraph(cfslGraph, file);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
