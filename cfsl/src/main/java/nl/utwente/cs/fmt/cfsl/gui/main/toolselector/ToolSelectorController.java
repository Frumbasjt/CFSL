/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector;

import java.util.List;
import javafx.scene.Node;
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
        List<Node> tools = getView().getChildren();
        tools.add(new ToolController(Symbol.ABSTRACT_SYNTAX_ELEMENT).getView());
        tools.add(new ToolController(Symbol.START).getView());
        tools.add(new ToolController(Symbol.STOP).getView());
        tools.add(new ToolController(Symbol.BRANCH_NODE).getView());
        tools.add(new ToolController(Symbol.CHILD).getView());
        tools.add(new ToolController(Symbol.FLOW).getView());
        tools.add(new ToolController(Symbol.BRANCH_EDGE).getView());
        tools.add(new ToolController(Symbol.START_ABORT).getView());
        tools.add(new ToolController(Symbol.RESOLVE_ABORT).getView());
        tools.add(new ToolController(Symbol.RESUME_ABORT).getView());
    }
}
