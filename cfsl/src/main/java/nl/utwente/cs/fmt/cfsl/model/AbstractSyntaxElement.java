/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class AbstractSyntaxElement extends Node {
    private final List<String> labels = new ArrayList<>();

    private boolean keyElement = false;
    private String identifier = null;
    
    // PROPERTIES
    
    public List<String> getLabels() {
        return labels;
    }
    
    public boolean isKeyElement() {
        return keyElement;
    }

    public void setKeyElement(boolean keyElement) {
        this.keyElement = keyElement;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    // METHODS
    
    @Override
    public boolean connect(Edge edge, EdgePosition position) {
        if (position == EdgePosition.START) {
            // If edge is flow or branch, and there is already an outgoing flow or branch, don't connect
            if (edge instanceof FlowEdge) {
                for (Edge outEdge : outgoingEdges) {
                    if (outEdge instanceof FlowEdge) {
                        return false;
                    }
                }
            } else if (edge instanceof BranchEdge) {
                for (Edge outEdge : outgoingEdges) {
                    if (outEdge instanceof BranchEdge) {
                        return false;
                    }
                }
            } 
            // Else, connect
            outgoingEdges.add(edge);
        } else {
            // ASE can have an infinite number of any incoming edges
            incomingEdges.add(edge);
        }
        return true;
    }

    @Override
    public boolean canConnect(Edge edge, EdgePosition position) {
        if (position == EdgePosition.START) {
            // If edge is flow or branch, and there is already an outgoing flow or branch, can't connect
            if (edge instanceof FlowEdge) {
                for (Edge outEdge : outgoingEdges) {
                    if (outEdge instanceof FlowEdge) {
                        return false;
                    }
                }
            } else if (edge instanceof BranchEdge) {
                for (Edge outEdge : outgoingEdges) {
                    if (outEdge instanceof BranchEdge) {
                        return false;
                    }
                }
            } 
        }
        // Else, can connect
        return true;
    }
    
    
}
