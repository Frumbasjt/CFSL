/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

import javafx.scene.shape.Line;
import nl.utwente.cs.fmt.cfsl.gui.Controller;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public class EdgeCurveControlLineController extends Controller<Line> {
    private final EdgeCurveControlAnchorController anchor;
    
    public EdgeCurveControlLineController(EdgeCurveControlAnchorController anchor) {
        this.anchor = anchor;
        
        TactilePane.setDraggable(getView(), false);
        
        anchor.visibleProperty().addListener(o -> { 
            if (anchor.isVisible()) {
                getView().setOpacity(0.6);
            } else {
                getView().setOpacity(0);
            }
        });
    }
}
