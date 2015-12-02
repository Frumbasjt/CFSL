/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.compiler;

import nl.utwente.cs.fmt.cfsl.model.cfslplus.Graph;

/**
 * Interface for objects that compile objects of one type objects of another type. Typically
 * the input would represent a graph, whereas the output may be anything.
 * 
 * @author Richard
 */
public interface Compiler<F, T> {
    
    /**
     * Compiles the provided object of type F to an object of type T.
     * 
     * @param object the object to be compiled
     * @return the resulting object
     */
    public T compile(F object);
}
