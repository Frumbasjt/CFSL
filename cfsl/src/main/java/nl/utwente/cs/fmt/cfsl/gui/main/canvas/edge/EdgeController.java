/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas.edge;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasController;
import nl.utwente.cs.fmt.cfsl.gui.main.canvas.CanvasElementController;
import nl.utwente.ewi.caes.tactilefx.control.TactilePane;

/**
 *
 * @author Richard
 */
public abstract class EdgeController<T> extends CanvasElementController<Group> {
    @FXML protected CubicCurve curve;
    @FXML protected Pane headWrapper;
    
    protected final EdgeCurvePositionAnchorController startAnchor = new EdgeCurvePositionAnchorController(this);
    protected final EdgeCurvePositionAnchorController endAnchor = new EdgeCurvePositionAnchorController(this);
    protected final EdgeCurveControlAnchorController control1Anchor = new EdgeCurveControlAnchorController(this);
    protected final EdgeCurveControlAnchorController control2Anchor = new EdgeCurveControlAnchorController(this);
    protected final EdgeCurveControlLineController control1Line = new EdgeCurveControlLineController(control1Anchor);
    protected final EdgeCurveControlLineController control2Line = new EdgeCurveControlLineController(control2Anchor);
    
    public EdgeController() {
        TactilePane.setDraggable(getView(), false);
        
        // Bind head's location to line's end
        headWrapper.layoutXProperty().bind(curve.endXProperty().subtract(headWrapper.widthProperty().divide(2)));
        headWrapper.layoutYProperty().bind(curve.endYProperty().subtract(headWrapper.heightProperty().divide(2)));
        
        // Rotate edge head with line
        headWrapper.rotateProperty().bind(Bindings.createDoubleBinding(() -> {
            double size = Math.max(curve.getBoundsInLocal().getWidth(), curve.getBoundsInLocal().getHeight());
            double scale = size / 2d;
            Point2D tan = evalDt(curve, 1).normalize().multiply(scale);
            return Math.toDegrees(Math.atan2( tan.getY(), tan.getX())) + 90;
        }, curve.startXProperty(), curve.startYProperty(), curve.endXProperty(), curve.endYProperty(),
        curve.controlX1Property(), curve.controlX2Property(), curve.controlY1Property(), curve.controlY2Property()));
    }
    
    // CANVASELEMENTCONTROLLER IMPLEMENTATION
    
    @Override
    public void initCanvas(CanvasController canvas) {
        // Add anchors to the canvas
        List canvasViewChildren = canvas.getCanvasView().getChildren();
        canvasViewChildren.add(startAnchor.getView());
        canvasViewChildren.add(endAnchor.getView());
        canvasViewChildren.add(control1Anchor.getView());
        canvasViewChildren.add(control2Anchor.getView());
        canvasViewChildren.add(control1Line.getView());
        canvasViewChildren.add(control2Line.getView());
        
        // Relocate anchors
        double layoutX = getView().getLayoutX();
        double layoutY = getView().getLayoutY();
        startAnchor.getView().relocate(layoutX + curve.getStartX(), layoutY + curve.getStartY());
        endAnchor.getView().relocate(layoutX + curve.getEndX(), layoutY + curve.getEndY());
        control1Anchor.getView().relocate(layoutX + curve.getControlX1(), layoutY + curve.getControlY1());
        control2Anchor.getView().relocate(layoutX + curve.getControlX2(), layoutY + curve.getControlY2());
        
        // Relocate entire group to top left of canvas to compensate for bindings
        Bounds b = getView().getBoundsInLocal();
        getView().relocate(b.getMinX(), b.getMinY());
        
        // Bind curve position to position of position anchors
        curve.startXProperty().bind(startAnchor.getView().layoutXProperty());
        curve.startYProperty().bind(startAnchor.getView().layoutYProperty());
        curve.endXProperty().bind(endAnchor.getView().layoutXProperty());
        curve.endYProperty().bind(endAnchor.getView().layoutYProperty());
        
        // Bind curve control to position of control anchors
        curve.controlX1Property().bind(control1Anchor.getView().layoutXProperty());
        curve.controlY1Property().bind(control1Anchor.getView().layoutYProperty());
        curve.controlX2Property().bind(control2Anchor.getView().layoutXProperty());
        curve.controlY2Property().bind(control2Anchor.getView().layoutYProperty());
        
        // Bind control lines between control anchors and curve
        control1Line.getView().startXProperty().bind(curve.controlX1Property());
        control1Line.getView().startYProperty().bind(curve.controlY1Property());
        control1Line.getView().endXProperty().bind(curve.startXProperty());
        control1Line.getView().endYProperty().bind(curve.startYProperty());
        control2Line.getView().startXProperty().bind(curve.controlX2Property());
        control2Line.getView().startYProperty().bind(curve.controlY2Property());
        control2Line.getView().endXProperty().bind(curve.endXProperty());
        control2Line.getView().endYProperty().bind(curve.endYProperty());
        
        // Track position anchors
        canvas.getCanvasView().getActiveNodes().add(startAnchor.getView());
        canvas.getCanvasView().getActiveNodes().add(endAnchor.getView());
    }
    
    // HELP METHODS
    
    /**
     * Evaluate the cubic curve at a parameter 0<=t<=1, returns a Point2D
     * @param c the CubicCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    protected final Point2D eval(CubicCurve c, float t){
        Point2D p=new Point2D(Math.pow(1-t,3)*c.getStartX()+
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
     * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
     * @param c the CubicCurve 
     * @param t param between 0 and 1
     * @return a Point2D 
     */
    protected final Point2D evalDt(CubicCurve c, float t){
        Point2D p=new Point2D(-3*Math.pow(1-t,2)*c.getStartX()+
                3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlX1()+
                3*((1-t)*2*t-t*t)*c.getControlX2()+
                3*Math.pow(t, 2)*c.getEndX(),
                -3*Math.pow(1-t,2)*c.getStartY()+
                3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlY1()+
                3*((1-t)*2*t-t*t)*c.getControlY2()+
                3*Math.pow(t, 2)*c.getEndY());
        return p;
    }
}
