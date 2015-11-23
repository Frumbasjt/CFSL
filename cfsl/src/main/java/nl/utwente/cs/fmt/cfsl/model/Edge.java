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
public class Edge extends GraphElement {
    private Node startNode;
    private Node endNode;
    
    public Node getStartNode() {
        return startNode;
    }
    
    void setStartNode(Node startNode) {
        this.startNode = startNode;
    }
    
    public Node getEndNode() {
        return endNode;
    }
    
    void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
}
