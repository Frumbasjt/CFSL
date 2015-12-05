/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * Represents an edge between two nodes.
 * 
 * @author Richard
 */
public abstract class Edge extends GraphElement {
    
    /**
     * The node that is the origin of this edge. This may be null while a user is still drawing.
     * It cannot be set directly, use {@link #utwente.cs.fmt.cfsl.model.Node.connect Node.connect} instead.
     */
    private final ObjectProperty<Node> startNode = new ReadOnlyObjectWrapper<>();

    public Node getStartNode() {
        return startNode.get();
    }

    protected void setStartNode(Node value) {
        startNode.set(value);
    }
    
    public ObjectProperty startNodeProperty() {
        return startNode;
    }
    
    /**
     * The node that is the end of this edge. This may be null while a user is drawing. It cannot
     * be set directly, use {@link #utwente.cs.fmt.cfsl.model.Node.connect Node.connect} instead.
     */
    private final ObjectProperty<Node> endNode = new ReadOnlyObjectWrapper<>();

    public Node getEndNode() {
        return endNode.get();
    }

    protected void setEndNode(Node value) {
        endNode.set(value);
    }
    
    public ObjectProperty endNodeProperty() {
        return endNode;
    }
    
    /**
     * Whether both the start and end of the edge are connected to a node.
     * 
     * @return true when both the start and end of the edge are connected to a node, false otherwise.
     */
    public boolean isConnected() {
        return getStartNode() != null && getEndNode() != null;
    }
}
