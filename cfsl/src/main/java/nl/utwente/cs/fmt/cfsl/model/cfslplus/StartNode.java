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
public class StartNode extends Node {

    @Override
    public boolean canConnect(Edge edge, EdgeSide position) {
        return getOutgoingEdgesUnmodifiable().isEmpty() && position == EdgeSide.START && edge instanceof FlowEdge;
    }
    
}
