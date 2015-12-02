/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Richard
 */
public abstract class Node implements GraphElement {
    private final Set<Edge> outgoingEdges = new HashSet<>();
    private final Set<Edge> incomingEdges = new HashSet<>();
    
    /**
     * Connects the position of the given edge to this node, if possible.
     * @param edge the Edge to connect
     * @param position the side of the Edge to connect
     * @return whether the Edge could be connected to this Node
     */
    public final boolean connect(Edge edge, EdgeSide position) {
        if (canConnect(edge, position)) {
            if (position == EdgeSide.START) {
                edge.setStartNode(this);
                outgoingEdges.add(edge);
            } else {
                edge.setEndNode(this);
                incomingEdges.add(edge);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Whether the given position of the given Edge can connect to this Node.
     * @param edge the Edge to connect
     * @param position the side of the Edge to connect
     * @return whether the Edge could be connected to this Node
     */
    public abstract boolean canConnect(Edge edge, EdgeSide position);
    
    /**
     * Disconnects the given position of the given Edge from this Node.
     * @param edge the Edge to disconnect
     * @param position the side of the Edge to disconnect
     */
    public final void disconnect(Edge edge, EdgeSide position) {
        if (position == EdgeSide.START) {
            edge.setStartNode(null);
            outgoingEdges.remove(edge);
        } else {
            edge.setEndNode(null);
            incomingEdges.remove(edge);
        }
    }
    
    public final Set<Edge> getIncomingEdgesUnmodifiable() {
        return Collections.unmodifiableSet(incomingEdges);
    }
    
    public final Set<Edge> getOutgoingEdgesUnmodifiable() {
        return Collections.unmodifiableSet(outgoingEdges);
    }
}
