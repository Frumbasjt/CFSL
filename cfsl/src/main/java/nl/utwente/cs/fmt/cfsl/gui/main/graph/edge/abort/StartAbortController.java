/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

import nl.utwente.cs.fmt.cfsl.model.StartAbortEdge;

/**
 *
 * @author Richard
 */
public class StartAbortController extends AbortController<StartAbortEdge> {

    public StartAbortController(StartAbortEdge model) {
        super(model, "abort:");
        getView().getStyleClass().add("start-abort");
    }
    
    @Override
    public String getToolName() {
        return "Start Abort Edge";
    }
}
