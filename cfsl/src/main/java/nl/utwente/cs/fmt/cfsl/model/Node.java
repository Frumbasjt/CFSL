/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Richard
 */
public abstract class Node extends GraphElement {
    protected final Set<Edge> outgoingEdges = new HashSet<>();
    protected final Set<Edge> incomingEdges = new HashSet<>();
    
    /**
     * Connects the position of the given edge to this node, if possible.
     * @param edge the Edge to connect
     * @param position the side of the Edge to connect
     * @return whether the Edge could be connected to this Node
     */
    public abstract boolean connect(Edge edge, EdgePosition position);
    
    /**
     * Whether the given position of the given Edge can connect to this Node.
     * @param edge the Edge to connect
     * @param position the side of the Edge to connect
     * @return whether the Edge could be connected to this Node
     */
    public abstract boolean canConnect(Edge edge, EdgePosition position);
    
    /**
     * Disconnects the given position of the given Edge from this Node.
     * @param edge the Edge to disconnect
     * @param position the side of the Edge to disconnect
     */
    public void disconnect(Edge edge, EdgePosition position) {
        if (position == EdgePosition.START) {
            outgoingEdges.remove(edge);
        } else {
            incomingEdges.remove(edge);
        }
    }
}
