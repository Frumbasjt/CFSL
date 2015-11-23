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
public class AbortEdge extends Edge {
    
    /**
     * The reason for the abort formatted as text.
     */
    private final StringProperty reason = new SimpleStringProperty();

    public String getReason() {
        return reason.get();
    }

    public void setReason(String value) {
        reason.set(value);
    }

    public StringProperty reasonProperty() {
        return reason;
    }
    
}
