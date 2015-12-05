/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

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
    
    public AbstractSyntaxElement getBranchSource() {
        for (Edge inEdge : getIncomingEdgesUnmodifiable()) {
            Node source = inEdge.getStartNode();
            if (source != null && source instanceof AbstractSyntaxElement) {
                return (AbstractSyntaxElement) source;
            }
        }
        return null;
    }
    
    // METHODS

    @Override
    public boolean canConnect(Edge edge, EdgeSide position) {
        if (edge instanceof BranchEdge) {
            if (position == EdgeSide.END) {
                for (Edge inEdge : getIncomingEdgesUnmodifiable()) {
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
