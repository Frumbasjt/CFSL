/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector;

import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.Symbol;
import nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool.ToolController;

/**
 *
 * @author Richard
 */
public class ToolSelectorController extends Controller<VBox> {
    public ToolSelectorController() {
        getView().getChildren().add(new ToolController(Symbol.ABSTRACT_SYNTAX_ELEMENT).getView());
        getView().getChildren().add(new ToolController(Symbol.FLOW).getView());
    }
}
