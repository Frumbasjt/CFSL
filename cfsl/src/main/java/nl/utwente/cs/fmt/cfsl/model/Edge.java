/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 *
 * @author Richard
 */
public class Edge extends GraphElement {
    private final ObjectProperty<Node> startNode = new ReadOnlyObjectWrapper<>();

    public Node getStartNode() {
        return startNode.get();
    }

    void setStartNode(Node value) {
        startNode.set(value);
    }
    
    public ObjectProperty startNodeProperty() {
        return startNode;
    }
    
    private final ObjectProperty<Node> endNode = new ReadOnlyObjectWrapper<>();

    public Node getEndNode() {
        return endNode.get();
    }

    void setEndNode(Node value) {
        endNode.set(value);
    }
    
    public ObjectProperty endNodeProperty() {
        return endNode;
    }
}
