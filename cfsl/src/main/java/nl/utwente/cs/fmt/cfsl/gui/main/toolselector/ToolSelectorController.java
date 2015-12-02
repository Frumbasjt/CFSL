/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.toolselector;

import javafx.scene.layout.VBox;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.cs.fmt.cfsl.gui.main.toolselector.tool.ToolController;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbortStateNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbstractSyntaxElement;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ChildEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ExitEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.FlowEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.GraphElement;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ResolveAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ResumeAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StartAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StartNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StopNode;

/**
 *
 * @author Richard
 */
public class ToolSelectorController extends Controller<VBox> {
    public ToolSelectorController() {
        addTool(AbstractSyntaxElement.class);
        addTool(StartNode.class);
        addTool(StopNode.class);
        addTool(AbortStateNode.class);
        addTool(BranchNode.class);
        addTool(ChildEdge.class);
        addTool(ExitEdge.class);
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
