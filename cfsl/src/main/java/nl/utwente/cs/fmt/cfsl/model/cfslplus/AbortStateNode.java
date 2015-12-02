/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

/**
 *
 * @author Richard
 */
public class AbortStateNode extends Node {

    @Override
    public boolean canConnect(Edge edge, EdgeSide position) {
        return position == EdgeSide.END && (edge instanceof StartAbortEdge || edge instanceof ResumeAbortEdge);
    }
    
}
