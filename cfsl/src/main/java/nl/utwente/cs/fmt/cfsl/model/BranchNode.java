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
public class BranchNode extends Node {
    private String identifier;

    // PROPERTIES
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    // METHODS
    
    @Override
    public boolean connect(Edge edge, EdgePosition position) {
        if (edge instanceof BranchEdge) {
            for (Edge inEdge : incomingEdges) {
                if (inEdge instanceof BranchEdge) {
                    return false;
                }
            }
            incomingEdges.add(edge);
            return true;
        }
        return false;
    }

    @Override
    public boolean canConnect(Edge edge, EdgePosition position) {
        if (edge instanceof BranchEdge) {
            for (Edge inEdge : incomingEdges) {
                if (inEdge instanceof BranchEdge) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}
