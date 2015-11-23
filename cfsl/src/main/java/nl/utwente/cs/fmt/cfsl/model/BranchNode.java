/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Richard
 */
public class BranchNode extends Node {
    

    // PROPERTIES
    
    /**
     * The id of the abstract syntax element that is the condition of the branch node.
     */
    private final StringProperty conditionId = new SimpleStringProperty();

    public String getConditionId() {
        return conditionId.get();
    }

    public void setConditionId(String value) {
        conditionId.set(value);
    }

    public StringProperty conditionIdProperty() {
        return conditionId;
    }
    
    // METHODS
    
    @Override
    public boolean connect(Edge edge, EdgePosition position) {
        if (edge instanceof BranchEdge) {
            if (position == EdgePosition.END) {
                for (Edge inEdge : incomingEdges) {
                    if (inEdge instanceof BranchEdge) {
                        return false;
                    }
                }
                edge.setEndNode(this);
            } else {
                edge.setStartNode(this);
            }
            incomingEdges.add(edge);
            return true;
        }
        return false;
    }

    @Override
    public boolean canConnect(Edge edge, EdgePosition position) {
        if (edge instanceof BranchEdge) {
            if (position == EdgePosition.END) {
                for (Edge inEdge : incomingEdges) {
                    if (inEdge instanceof BranchEdge) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
}
