/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

/**
 * The controller for an edge anchor that acts as the control point of the 
 * edge's Bezier curve.
 * 
 * @author Richard
 */
public class EdgeCurveControlAnchorController extends EdgeAnchorController {
    
    /**
     * Creates a new EdgeCurveControlAnchorController that manipulates the given
     * edge.
     * 
     * @param edge the edge this anchor manipulates. May not be null.
     */
    public EdgeCurveControlAnchorController(EdgeController edge) {
        super(edge);
        
        visibleProperty().bind(edge.selectedProperty());
    }
}
