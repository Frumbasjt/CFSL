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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
        System.out.println("compiling");
        
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
            System.out.println("Created new ASE");
            if (ase.isKeyElement()) {
                addSelfEdge(ase, CfslLabels.KEY_ELEMENT_NODE_LABEL);
                keyElement = ase;
            }
            for (String label : ase.getLabels()) {
                addSelfEdge(ase, label);
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
            System.out.println("Created new stop node");
        }
        
        for (BranchNode branchNode : branchNodes) {
            PlainNode source = nodeMap.get(branchNode.getBranchSource());
            
            for (Edge edge : branchNode.getOutgoingEdgesUnmodifiable()) {
                BranchEdge branchEdge = (BranchEdge) edge;
                PlainNode end = nodeMap.get(branchEdge.getEndNode());
                PlainNode condition = nodeMap.get(aseById.get(branchNode.getConditionId()));
                
                // Build branch node
                PlainNode cfslNode = addNode();
                addSelfEdge(cfslNode, CfslLabels.BRANCH_NODE_LABEL);
                
                // Add branch, flow and condition edges
                addEdge(source, CfslLabels.BRANCH_LABEL, cfslNode);
                addEdge(cfslNode, CfslLabels.FLOW_LABEL, end);
                addEdge(cfslNode, CfslLabels.CONDITION_LABEL, condition);
                
                // Add branchOn label or branchDefault label
                if (branchEdge.isDefault()) {
                    addSelfEdge(cfslNode, CfslLabels.BRANCH_DEFAULT_LABEL);
                } else {
                    PlainNode branchOn = nodeMap.get(aseById.get(branchEdge.getValue()));
                    if (branchOn == null) {
                        branchOn = addNode();
                        addSelfEdge(branchOn, branchEdge.getValue());
                    }
                    addEdge(cfslNode, CfslLabels.BRANCH_ON_LABEL, branchOn);
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
            if (edge.getEndNode() instanceof AbortStateNode) {
                
            } else {
                PlainNode cfslNode = addNode();
                addSelfEdge(cfslNode, CfslLabels.ABORT_NODE_LABEL);
                
                addEdge(nodeMap.get(edge.getStartNode()), getAbortLabel(edge), cfslNode);
                List<PlainNode> reasonNodes = parseAbortReason(edge.getReason());
                for (PlainNode reasonNode : reasonNodes) {
                    addEdge(cfslNode, CfslLabels.REASON_LABEL, reasonNode);
                }
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
        System.out.println("Created new self edge with label " + label);
        return result.addEdge(node, label, node);
    }
    
    private PlainEdge addEdge(Node from, String label, Node to) {
        return addEdge(nodeMap.get(from), label, nodeMap.get(to));
    }
    
    private PlainEdge addEdge(PlainNode from, String label, PlainNode to) {
        System.out.println("Created new edge from node " + from + " to node " + to + " with label " + label);
        return result.addEdge(from, label, to);
    }
    
    private List<PlainNode> parseAbortReason(String reason) {
        List<PlainNode> result = new ArrayList<>();
        
        int lastI = 0;
        Stack<PlainNode> parentNodes = new Stack<>();
        String parentLabel = null;
        for (int i = 0; i < reason.length(); i++) {
            switch(reason.charAt(i)) {
                case ',':
                    if (parentNodes.isEmpty()) {
                        Node reasonNode = aseById.get(reason.substring(lastI, i));
                        if (reasonNode != null) {
                            result.add(nodeMap.get(reasonNode));
                            lastI = i + 1;
                            break;
                        }
                    }
                case '(':
                    PlainNode parentNode = addNode();
                    addSelfEdge(parentNode, reason.substring(lastI, i));
                    lastI = i + 1;

                    if (parentNodes.isEmpty()) {
                        result.add(parentNode);
                    }

                    if (parentLabel != null) {
                        addEdge(parentNodes.peek(), parentLabel, parentNode);
                    }

                    parentNodes.push(parentNode);
                    break;
                case ')':
                    PlainNode thisNode = addNode();
                    addSelfEdge(thisNode, reason.substring(lastI, i));
                    lastI = i + 1;

                    if (parentLabel != null) {
                        addEdge(parentNodes.pop(), parentLabel, thisNode);
                    }
                    break;
                case ':':
                    parentLabel = reason.substring(lastI, i);
                    lastI = i + 1;
                    break;
            }
        }
        
        if (lastI == 0) {
            Node reasonNode = aseById.get(reason);
            if (reasonNode != null) {
                result.add(nodeMap.get(reasonNode));
            } else {
                PlainNode node = addNode();
                addSelfEdge(node, reason);
                result.add(node);
            }
        }
        
        return result;
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
