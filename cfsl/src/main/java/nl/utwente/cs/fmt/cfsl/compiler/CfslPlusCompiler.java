/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.compiler;

import groove.graph.GraphRole;
import groove.graph.plain.PlainEdge;
import groove.graph.plain.PlainGraph;
import groove.graph.plain.PlainNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.utwente.cs.fmt.cfsl.model.cfsl.CfslLabels;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbortStateNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.AbstractSyntaxElement;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.BranchNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.CfslPlusGraph;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ChildEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.Edge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ExitEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.FlowEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.Node;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ResolveAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.ResumeAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StartAbortEdge;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StartNode;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.StopNode;

/**
 * Compiles a CFSL+ graph into a CFSL graph. The resulting CFSL graph is in GROOVE format.
 * 
 * @author Richard
 */
public class CfslPlusCompiler implements Compiler<CfslPlusGraph, PlainGraph> {

    private PlainGraph result;
    private Map<Node, PlainNode> nodeMap;
    private Map<String, AbstractSyntaxElement> aseById;
    
    @Override
    public PlainGraph compile(CfslPlusGraph graph) {
        result = new PlainGraph("", GraphRole.NONE);
        nodeMap = new HashMap<>();
        aseById = new HashMap<>();
        
        AbstractSyntaxElement keyElement = null;
        Set<AbstractSyntaxElement> abstractSyntaxElements = new HashSet<>();
        Set<StopNode> stopNodes = new HashSet<>();
        Set<FlowEdge> flowEdges = new HashSet<>();
        Set<ChildEdge> childEdges = new HashSet<>();
        Set<ExitEdge> exitEdges = new HashSet<>();
        Set<AbortEdge> abortEdges = new HashSet<>();
        Set<BranchNode> branchNodes = new HashSet<>();
        
        for (Node node : graph.getNodesUnmodifiable()) {
            if (node instanceof AbstractSyntaxElement) {
                abstractSyntaxElements.add((AbstractSyntaxElement) node);
            } else if (node instanceof StopNode) {
                stopNodes.add((StopNode) node);
            } else if (node instanceof BranchNode) {
                branchNodes.add((BranchNode) node);
            }
        }
        
        for (Edge edge : graph.getEdgesUnmodifiable()) {
            if (edge instanceof FlowEdge) {
                flowEdges.add((FlowEdge) edge);
            } else if (edge instanceof ChildEdge) {
                childEdges.add((ChildEdge) edge);
            } else if (edge instanceof AbortEdge) {
                abortEdges.add((AbortEdge) edge);
            } else if (edge instanceof ExitEdge) {
                exitEdges.add((ExitEdge) edge);
            }
        }
        
        for (AbstractSyntaxElement ase : abstractSyntaxElements) {
            addNode(ase);
            
            for (String label : ase.getLabels()) {
                addSelfEdge(ase, label);
            }
            if (ase.isKeyElement()) {
                addSelfEdge(ase, CfslLabels.KEY_ELEMENT_NODE_LABEL);
                keyElement = ase;
            }
            if (ase.getId() != null && ase.getId().length() > 0) {
                aseById.put(ase.getId(), ase);
            }
        }
        
        for (StopNode stopNode : stopNodes) {
            addNode(stopNode);
            // No exit edges defined implies stop node is the exit of key element
            if (exitEdges.isEmpty()) {
                addEdge(keyElement, CfslLabels.EXIT_LABEL, stopNode);
            }
        }
        
        for (BranchNode branchNode : branchNodes) {
            PlainNode source = nodeMap.get(branchNode.getBranchSource());
            PlainNode condition = nodeMap.get(aseById.get(branchNode.getConditionId()));
            
            for (Edge edge : branchNode.getOutgoingEdgesUnmodifiable()) {
                BranchEdge branchEdge = (BranchEdge) edge;
                PlainNode end = nodeMap.get(branchEdge.getEndNode());
                
                PlainNode cfslNode = addNode();
                addSelfEdge(cfslNode, CfslLabels.BRANCH_NODE_LABEL);
                
                addEdge(source, CfslLabels.BRANCH_LABEL, cfslNode);
                addEdge(cfslNode, CfslLabels.FLOW_LABEL, end);
                addEdge(cfslNode, CfslLabels.CONDITION_LABEL, condition);
                
                if (branchEdge.isDefault()) {
                    addSelfEdge(cfslNode, CfslLabels.BRANCH_DEFAULT_LABEL);
                } else {
                    PlainNode branchOn = nodeMap.get(aseById.get(branchEdge.getValue()));
                    if (branchOn == null) {
                        branchOn = addNode();
                        addSelfEdge(branchOn, branchEdge.getValue());
                        addEdge(cfslNode, CfslLabels.BRANCH_ON_LABEL, branchOn);
                    } else {
                        PlainNode variableNode = addNode();
                        addSelfEdge(variableNode, CfslLabels.VARIABLE_NODE_LABEL);
                        addEdge(cfslNode, CfslLabels.BRANCH_ON_LABEL, variableNode);
                        addEdge(branchOn, CfslLabels.VALUE_LABEL, variableNode);
                    }
                    
                }
            }
        }
        
        for (FlowEdge edge : flowEdges) {
            if (edge.getStartNode() instanceof StartNode) {
                addEdge(keyElement, CfslLabels.ENTRY_LABEL, edge.getEndNode());
            } else {
                addEdge(edge.getStartNode(), CfslLabels.FLOW_LABEL, edge.getEndNode());
            }
        }
        
        for (ChildEdge edge : childEdges) {
            addEdge(edge.getStartNode(), edge.getLabel(), edge.getEndNode());
        }
        
        for (AbortEdge edge : abortEdges) {
            PlainNode cfslNode = null;
            if (edge.getEndNode() instanceof AbortStateNode) {
                cfslNode = addNode(edge.getEndNode());
            } else {
                cfslNode = addNode();
            }
            addSelfEdge(cfslNode, CfslLabels.ABORT_NODE_LABEL);

            addEdge(nodeMap.get(edge.getStartNode()), getAbortLabel(edge), cfslNode);
            parseAbortReason(cfslNode, CfslLabels.REASON_LABEL, edge.getReason(), true);

            if (!(edge.getEndNode() instanceof AbortStateNode)) {
                addEdge(cfslNode, CfslLabels.FLOW_LABEL, nodeMap.get(edge.getEndNode()));
            }
        }
        
        return result;
    }
    
    private PlainNode addNode() {
        return result.addNode();
    }
    
    private PlainNode addNode(Node node) {
        PlainNode cfslNode = addNode();
        nodeMap.put(node, cfslNode);
        return cfslNode;
    }
    
    private PlainEdge addSelfEdge(Node node, String label) {
        return addSelfEdge(nodeMap.get(node), label);
    }
    
    private PlainEdge addSelfEdge(PlainNode node, String label) {
        return result.addEdge(node, label, node);
    }
    
    private PlainEdge addEdge(Node from, String label, Node to) {
        return addEdge(nodeMap.get(from), label, nodeMap.get(to));
    }
    
    private PlainEdge addEdge(PlainNode from, String label, PlainNode to) {
        return result.addEdge(from, label, to);
    }
    
    private void parseAbortReason(PlainNode parent, String parentLabel, String reason, boolean depth0) {
        if (reason == null) return;
        
        reason = reason.replaceAll("\n", ",");
        reason = reason.replaceAll(",,", ",");
        reason = reason.replaceAll("\\s", "");
        
        int lastI = 0;
        int scope = 0;
        String parentReasonPart = null;
        String childReasonPart = null;
        String customLabel = null;
        boolean foundValue = false;
        
        for (int i = 0; i < reason.length(); i++) {
            char c = reason.charAt(i);
            
            if (c == ',' && scope == 0) {
                if (parentReasonPart == null) {
                    parentReasonPart = reason.substring(lastI, i);
                }
                
                PlainNode reasonNode = null;
                if (depth0 && childReasonPart == null) {
                    reasonNode = nodeMap.get(aseById.get(parentReasonPart));
                }
                if (reasonNode == null) {
                    reasonNode = addNode();
                    addSelfEdge(reasonNode, parentReasonPart);
                }
                
                if (customLabel == null) {
                    addEdge(parent, parentLabel, reasonNode);
                } else {
                    addEdge(parent, customLabel, reasonNode);
                }
                
                if (foundValue) {
                    PlainNode valueNode = nodeMap.get(aseById.get(reason.substring(lastI, i)));
                    PlainNode variableNode = addNode();
                    addSelfEdge(variableNode, CfslLabels.VARIABLE_NODE_LABEL);
                    addEdge(reasonNode, CfslLabels.VALUE_LABEL, variableNode);
                    addEdge(valueNode, CfslLabels.VALUE_LABEL, variableNode);
                }
                
                parseAbortReason(reasonNode, CfslLabels.CHILD_LABEL, childReasonPart, false);
                
                // Reset
                parentReasonPart = null;
                childReasonPart = null;
                customLabel = null;
                foundValue = false;
                
                lastI = i + 1;
            } else if (c == '(') {
                if (scope == 0) {
                    parentReasonPart = reason.substring(lastI, i);
                    lastI = i + 1;
                }
                scope++;
            } else if (c == ')') {
                scope--;
                if (scope == 0) {
                    childReasonPart = reason.substring(lastI, i);
                }
            } else if (c == ':' && scope == 0) {
                customLabel = reason.substring(lastI, i);
                lastI = i + 1;
            } else if (c == '=' && scope == 0) {
                if (parentReasonPart == null) {
                    parentReasonPart = reason.substring(lastI, i);
                }
                foundValue = true;
                lastI = i + 1;
            }
        }
        
        String lastReason = reason.substring(lastI);
        
        if(lastReason.length() > 0) {
            parentReasonPart = parentReasonPart == null ? lastReason : parentReasonPart;
            
            PlainNode reasonNode = nodeMap.get(aseById.get(parentReasonPart));
            if (reasonNode == null) {
                reasonNode = addNode();
                addSelfEdge(reasonNode, parentReasonPart);
            }
            if (customLabel == null) {
                addEdge(parent, parentLabel, reasonNode);
            } else {
                addEdge(parent, customLabel, reasonNode);
            }
            
            if (foundValue) {
                PlainNode valueNode = nodeMap.get(aseById.get(lastReason));
                PlainNode variableNode = addNode();
                addSelfEdge(variableNode, CfslLabels.VARIABLE_NODE_LABEL);
                addEdge(reasonNode, CfslLabels.VALUE_LABEL, variableNode);
                addEdge(valueNode, CfslLabels.VALUE_LABEL, variableNode);
            }
            
            parseAbortReason(reasonNode, CfslLabels.CHILD_LABEL, childReasonPart, false);
        }
    }
    
    private String getAbortLabel(AbortEdge abortEdge) {
        if (abortEdge instanceof StartAbortEdge) {
            return CfslLabels.ABORT_LABEL;
        } else if (abortEdge instanceof ResolveAbortEdge) {
            return CfslLabels.ABORT_FROM_LABEL;
        } else if (abortEdge instanceof ResumeAbortEdge) {
            return CfslLabels.RESUME_ABORT_LABEL;
        }
        return null;
    }
}
