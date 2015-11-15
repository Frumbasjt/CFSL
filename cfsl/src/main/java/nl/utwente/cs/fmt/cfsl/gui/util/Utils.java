/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.util;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;

/**
 *
 * @author Richard
 */
public class Utils {
    public static Bounds getChildrenBounds(Region node) {
        if (node.getChildrenUnmodifiable().isEmpty()) {
            return new BoundingBox(0, 0, 0, 0);
        }
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        
        for (Node child : node.getChildrenUnmodifiable()) {
            Bounds layoutBounds = child.getBoundsInParent();
            
            minX = Math.min(minX, layoutBounds.getMinX());
            minY = Math.min(minY, layoutBounds.getMinY());
            
            maxX = Math.max(maxX, layoutBounds.getMaxX());
            maxY = Math.max(maxY, layoutBounds.getMaxY());
        }
        
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
}
