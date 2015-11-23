/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector;

import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool.ToolController;
import nl.utwente.cs.fmt.cfsl.model.AbstractSyntaxElement;
import nl.utwente.cs.fmt.cfsl.model.BranchEdge;
import nl.utwente.cs.fmt.cfsl.model.BranchNode;
import nl.utwente.cs.fmt.cfsl.model.ChildEdge;
import nl.utwente.cs.fmt.cfsl.model.FlowEdge;
import nl.utwente.cs.fmt.cfsl.model.GraphElement;
import nl.utwente.cs.fmt.cfsl.model.ResolveAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.ResumeAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.StartAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.StartNode;
import nl.utwente.cs.fmt.cfsl.model.StopNode;

/**
 *
 * @author Richard
 */
public class ToolSelectorController extends Controller<VBox> {
    public ToolSelectorController() {
        addTool(AbstractSyntaxElement.class);
        addTool(StartNode.class);
        addTool(StopNode.class);
        addTool(BranchNode.class);
        addTool(ChildEdge.class);
        addTool(FlowEdge.class);
        addTool(BranchEdge.class);
        addTool(StartAbortEdge.class);
        addTool(ResolveAbortEdge.class);
        addTool(ResumeAbortEdge.class);
    }
    
    private void addTool(Class<? extends GraphElement> type) {
        getView().getChildren().add(new ToolController(type).getView());
    }
}
