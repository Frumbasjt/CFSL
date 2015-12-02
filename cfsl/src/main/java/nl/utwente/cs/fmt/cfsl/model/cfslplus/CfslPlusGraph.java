/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Richard
 */
public class CfslPlusGraph extends Graph {
    
    private void checkState() {
        for (Edge edge : getEdges()) {
            if (edge.getStartNode() == null || edge.getEndNode() == null) {
                throw new IllegalStateException("Not all edges in the graph are connected");
            }
        }
        Set<String> ids = new HashSet<>();
        for (Node node : getNodes()) {
            if (node instanceof AbstractSyntaxElement) {
                AbstractSyntaxElement aseNode = (AbstractSyntaxElement) node;
                if (aseNode.getId() != null && !ids.add(aseNode.getId())) {
                    throw new IllegalStateException("More than one abstract syntax element with id " + aseNode.getId() + " defined");
                }
            }
        }
        for (Node node : getNodes()) {
            if (node instanceof BranchNode) {
                BranchNode branchNode = (BranchNode) node;
                if (!ids.contains(branchNode.getConditionId())) {
                    throw new IllegalStateException("No abstract syntax element exists with id " + branchNode.getConditionId());
                }
            }
        }
    }
}
