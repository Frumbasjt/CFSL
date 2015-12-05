/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Richard
 */
public abstract class Graph {
    private final Set<Node> nodes = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();
    private final Map<Integer, GraphElement> elementMap = new TreeMap<>();
    
    public void add(GraphElement graphElement) {
        if (graphElement instanceof Node) {
            nodes.add((Node) graphElement);
        } else if (graphElement instanceof Edge) {
            edges.add((Edge) graphElement);
        }
        elementMap.put(graphElement.getElementNumber(), graphElement);
    }
    
    public void remove(GraphElement graphElement) {
        if (graphElement instanceof Node) {
            nodes.remove((Node) graphElement);
        } else if (graphElement instanceof Edge) {
            edges.remove((Edge) graphElement);
        }
        elementMap.remove(graphElement.getElementNumber(), graphElement);
    }
    
    public Set<Node> getNodesUnmodifiable() {
        return Collections.unmodifiableSet(nodes);
    }
    
    public Set<Edge> getEdgesUnmodifiable() {
        return Collections.unmodifiableSet(edges);
    }
    
    public GraphElement getElement(int elementNumber) {
        return elementMap.get(elementNumber);
    }
}
