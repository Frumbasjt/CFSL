/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

import nl.utwente.cs.fmt.cfsl.model.cfslplus.ResolveAbortEdge;

/**
 *
 * @author Richard
 */
public class ResolveAbortController extends AbortController<ResolveAbortEdge> {
    
    public ResolveAbortController(ResolveAbortEdge model) {
        super("Resolve Abort Edge", model, "resolve abort:");
        getView().getStyleClass().add("resolve-abort");
    }
}
