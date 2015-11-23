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
public class Graph {
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
    
    private void checkState() {
        for (Edge edge : edges) {
            if (edge.getStartNode() == null || edge.getEndNode() == null) {
                throw new IllegalStateException("Not all edges in the graph are connected");
            }
        }
        Set<String> ids = new HashSet<>();
        for (Node node : nodes) {
            if (node instanceof AbstractSyntaxElement) {
                AbstractSyntaxElement aseNode = (AbstractSyntaxElement) node;
                if (aseNode.getId() != null && !ids.add(aseNode.getId())) {
                    throw new IllegalStateException("More than one abstract syntax element with id " + aseNode.getId() + " defined");
                }
            }
        }
        for (Node node : nodes) {
            if (node instanceof BranchNode) {
                BranchNode branchNode = (BranchNode) node;
                if (!ids.contains(branchNode.getConditionId())) {
                    throw new IllegalStateException("No abstract syntax element exists with id " + branchNode.getConditionId());
                }
            }
        }
    }
}
