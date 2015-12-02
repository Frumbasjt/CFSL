/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import java.util.HashSet;
import java.util.Set;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbstractSyntaxElement;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchNode;

/**
 *
 * @author Richard
 */
public abstract class Graph {
    private final Set<Node> nodes = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();
    
    public void add(GraphElement graphElement) {
        if (graphElement instanceof Node) {
            nodes.add((Node) graphElement);
        } else if (graphElement instanceof Edge) {
            edges.add((Edge) graphElement);
        }
    }
    
    public void remove(GraphElement graphElement) {
        if (graphElement instanceof Node) {
            nodes.remove((Node) graphElement);
        } else if (graphElement instanceof Edge) {
            edges.remove((Edge) graphElement);
        }
    }
    
    public Set<Node> getNodes() {
        return nodes;
    }
    
    public Set<Edge> getEdges() {
        return edges;
    }
}
