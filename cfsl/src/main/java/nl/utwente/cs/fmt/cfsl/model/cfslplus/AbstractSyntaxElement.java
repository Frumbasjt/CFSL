/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Richard
 */
public class AbstractSyntaxElement extends Node {
    private final List<String> labels = new ArrayList<>();
    
    // PROPERTIES
    
    /**
     * The labels this abstract syntax element has.
     * @return a List of Strings
     */
    public List<String> getLabels() {
        return labels;
    }
    
    /**
     * Whether this abstract syntax element is the key element.
     */
    private final BooleanProperty keyElement = new SimpleBooleanProperty();

    public boolean isKeyElement() {
        return keyElement.get();
    }

    public void setKeyElement(boolean value) {
        keyElement.set(value);
    }

    public BooleanProperty keyElementProperty() {
        return keyElement;
    }
    
    /**
     * The identifier of this abstract syntax element.
     */
    private final StringProperty id = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String value) {
        id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }
    
    // METHODS

    @Override
    public boolean canConnect(Edge edge, EdgeSide position) {
        if (position == EdgeSide.START) {
            Set<Edge> outgoingEdges = getOutgoingEdgesUnmodifiable();
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
        // Else, can connect if edge is of proper type
        return edge instanceof AbortEdge || edge instanceof FlowEdge || edge instanceof BranchEdge
                || edge instanceof ExitEdge || edge instanceof ChildEdge;
    }
    
    
}
