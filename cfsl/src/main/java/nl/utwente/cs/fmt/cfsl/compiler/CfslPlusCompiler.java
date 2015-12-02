/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.compiler;

import groove.graph.plain.PlainGraph;
import nl.utwente.cs.fmt.cfsl.model.cfslplus.CfslPlusGraph;

/**
 * Compiles a CFSL+ graph into a CFSL graph. The resulting CFSL graph is in GROOVE format.
 * 
 * @author Richard
 */
public class CfslPlusCompiler implements Compiler<CfslPlusGraph, PlainGraph> {

    @Override
    public PlainGraph compile(CfslPlusGraph graph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
