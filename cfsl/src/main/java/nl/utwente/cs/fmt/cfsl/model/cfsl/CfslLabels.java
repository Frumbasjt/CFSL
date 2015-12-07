/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfsl;

/**
 *
 * @author Richard
 */
public class CfslLabels {
    // Standard labels
    public static final String ENTRY_LABEL = "entry";
    public static final String EXIT_LABEL = "exit";
    public static final String ABORT_LABEL = "abort";
    public static final String ABORT_FROM_LABEL = "abortFrom";
    public static final String RESUME_ABORT_LABEL = "resumeAbort";
    public static final String REASON_LABEL = "reason";
    public static final String BRANCH_LABEL = "branch";
    public static final String BRANCH_ON_LABEL = "branchOn";
    public static final String BRANCH_DEFAULT_LABEL = "branchDefault";
    public static final String FLOW_LABEL = "flow";
    public static final String CONDITION_LABEL = "condition";
    public static final String VALUE_LABEL = "value";
    public static final String CHILD_LABEL = "child";
    
    // Standard labels for self edges that are used to label nodes
    public static final String KEY_ELEMENT_NODE_LABEL = "KeyElement";
    public static final String ABORT_NODE_LABEL = "Abort";
    public static final String BRANCH_NODE_LABEL = "Branch";
    public static final String VARIABLE_NODE_LABEL = "?";
}
