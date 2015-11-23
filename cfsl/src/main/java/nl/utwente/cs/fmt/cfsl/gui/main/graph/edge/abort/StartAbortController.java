/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.graph.edge.abort;

/**
 *
 * @author Richard
 */
public class StartAbortController extends AbortController {

    public StartAbortController() {
        super("abort:");
        getView().getStyleClass().add("start-abort");
    }
    
}
