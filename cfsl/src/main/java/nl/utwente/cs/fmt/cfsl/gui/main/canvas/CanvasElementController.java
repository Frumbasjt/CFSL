/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.cs.fmt.cfsl.gui.main.canvas;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import nl.utwente.cs.fmt.cfsl.gui.Controller;

/**
 *
 * @author Richard
 * @param <T>
 */
public abstract class CanvasElementController<T> extends Controller{
    
    /**
     * Whether the View is in preview mode or not. Preview mode is used for
     * canvas elements that aren't actually part of the canvas, but are shown in
     * the tool bar. They get the 'preview' style class.
     */
    private final BooleanProperty preview = new SimpleBooleanProperty(false) {
        @Override
        public void set(boolean value) {
            if (value != get()) {
                if (value) {
                    getView().getStyleClass().add("preview");
                } else {
                    getView().getStyleClass().remove("preview");
                }
                super.set(value);
            }
        }
    };

    public boolean isPreview() {
        return preview.get();
    }

    public void setPreview(boolean value) {
        preview.set(value);
    }

    public BooleanProperty previewProperty() {
        return preview;
    }
    
    /**
     * Called when added to the canvas. Does nothing by default.
     * 
     * @param canvas the Canvas that the CanvasElement was added to
     */
    public void initCanvas(CanvasController canvas) { }
}
