/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

import nl.utwente.cs.fmt.cfsl.model.ResumeAbortEdge;

/**
 *
 * @author Richard
 */
public class ResumeAbortController extends AbortController<ResumeAbortEdge> {
    
    public ResumeAbortController(ResumeAbortEdge model) {
        super(model, "resume abort:");
        getView().getStyleClass().add("resume-abort");
    }
    
    @Override
    public String getToolName() {
        return "Resume Abort Edge";
    }
}
