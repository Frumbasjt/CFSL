/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

/**
 *
 * @author Richard
 */
public class EdgeCurveControlAnchorController extends EdgeAnchorController {
    
    public EdgeCurveControlAnchorController(EdgeController edge) {
        super(edge);
        
        edge.selectedProperty().addListener(o -> { 
            setVisible(edge.isSelected());
        });
    }
}
