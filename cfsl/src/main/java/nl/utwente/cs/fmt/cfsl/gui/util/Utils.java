/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.util;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author Richard
 */
public class Utils {
    
    /**
     * Retrieves the minimum binding box that holds all the Region's children.
     * 
     * @param region the Region
     * @return the Bounds
     */
    public static Bounds getChildrenBounds(Region region) {
        if (region.getChildrenUnmodifiable().isEmpty()) {
            return new BoundingBox(0, 0, 0, 0);
        }
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        
        for (Node child : region.getChildrenUnmodifiable()) {
            Bounds layoutBounds = child.getBoundsInParent();
            
            minX = Math.min(minX, layoutBounds.getMinX());
            minY = Math.min(minY, layoutBounds.getMinY());
            
            maxX = Math.max(maxX, layoutBounds.getMaxX());
            maxY = Math.max(maxY, layoutBounds.getMaxY());
        }
        
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
    
    /**
     * Evaluate the cubic curve at a parameter 0<=t<=1, returns a Point2D
     * 
     * @param c the CubicCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    public static Point2D eval(CubicCurve c, float t){
        Point2D p = new Point2D(
                Math.pow(1-t,3)*c.getStartX()+
                3*t*Math.pow(1-t,2)*c.getControlX1()+
                3*(1-t)*t*t*c.getControlX2()+
                Math.pow(t, 3)*c.getEndX(),
                
                Math.pow(1-t,3)*c.getStartY()+
                3*t*Math.pow(1-t, 2)*c.getControlY1()+
                3*(1-t)*t*t*c.getControlY2()+
                Math.pow(t, 3)*c.getEndY());
        return p;
    }
    
    /**
     * Evaluate the quad curve at a parameter 0<=t<=1, returns a Point2D
     * 
     * @param q the QuadCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    public static Point2D eval(QuadCurve q, float t){
        Point2D p = new Point2D(
                Math.pow(1-t,3)*q.getStartX()+
                t*Math.pow(1-t,2)*(q.getStartX() + 2 * q.getControlX())+
                (1-t)*t*t*(q.getEndX() + 2 * q.getControlX())+
                Math.pow(t, 3)*q.getEndX(),
                
                Math.pow(1-t,3)*q.getStartY()+
                t*Math.pow(1-t, 2)*(q.getStartY() + 2 * q.getControlY())+
                (1-t)*t*t*(q.getEndY() + 2 * q.getControlY())+
                Math.pow(t, 3)*q.getEndY());
        return p;
    }

    /**
     * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
     * 
     * @param c the CubicCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    public static Point2D evalDt(CubicCurve c, float t){
        Point2D p = new Point2D(
                -3*Math.pow(1-t,2)*c.getStartX()+
                3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlX1()+
                3*((1-t)*2*t-t*t)*c.getControlX2()+
                3*Math.pow(t, 2)*c.getEndX(),
                
                -3*Math.pow(1-t,2)*c.getStartY()+
                3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlY1()+
                3*((1-t)*2*t-t*t)*c.getControlY2()+
                3*Math.pow(t, 2)*c.getEndY());
        return p;
    }
    
    /**
     * Evaluate the tangent of the quad curve at a parameter 0<=t<=1, returns a Point2D
     * 
     * @param q the QuadCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    public static Point2D evalDt(QuadCurve q, float t){
        Point2D p = new Point2D(
                -3*Math.pow(1-t,2)*q.getStartX() +
                (Math.pow(1-t, 2) - 2*t*(1-t))*(q.getStartX() + 2*q.getControlX()) +
                ((1-t)*2*t - t*t) * ((q.getEndX() + 2*q.getControlX())) +
                3*Math.pow(t, 2)*q.getEndX(),
                
                -3*Math.pow(1-t,2)*q.getStartY() +
                (Math.pow(1-t, 2)-2*t*(1-t))*(q.getStartY() + 2*q.getControlY())+
                ((1-t)*2*t - t*t)*((q.getEndY() + 2*q.getControlY()))+
                3*Math.pow(t, 2)*q.getEndY());
        return p;
    }
    
    /**
     * Converts the given QuadCurve to an equivalent CubicCurve
     * 
     * @param quad the QuadCurve to convert
     * @return the CubicCurve
     */
    public static final CubicCurve toCubic(QuadCurve quad) {
        return new CubicCurve(quad.getStartX(), quad.getStartY(), 
                (quad.getStartX() + 2 * quad.getControlX()) / 3, (quad.getStartY() + 2 * quad.getControlY()) / 3,
                (quad.getEndX() + 2 * quad.getControlX()) / 3, (quad.getEndY() + 2 * quad.getControlY()) / 3,
                quad.getEndX(), quad.getEndY());
    }
}
