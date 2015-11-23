/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model;

/**
 *
 * @author Richard
 */
public class StopNode extends Node {

    @Override
    public boolean connect(Edge edge, EdgePosition position) {
        if (position == EdgePosition.END && !(edge instanceof ChildEdge)) {
            incomingEdges.add(edge);
            edge.setEndNode(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean canConnect(Edge edge, EdgePosition position) {
        return position == EdgePosition.END && !(edge instanceof ChildEdge);
    }
    
}
