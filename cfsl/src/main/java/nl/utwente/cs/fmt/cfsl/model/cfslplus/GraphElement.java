/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.model.cfslplus;

/**
 *
 * @author Richard
 */
public abstract class GraphElement {
    private static int elementCount = 0;
    
    protected final int number;
    
    protected GraphElement() {
        number = elementCount++;
    }
    
    public int getElementNumber() {
        return number;
    }
}
