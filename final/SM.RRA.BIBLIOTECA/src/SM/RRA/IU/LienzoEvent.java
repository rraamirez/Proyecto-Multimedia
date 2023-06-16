/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IU;

import SM.RRA.GRAFICOS.Forma;
import java.awt.Color;
import java.awt.Shape;
import java.util.EventObject;

/**
 *
 * @author raul
 */
public class LienzoEvent extends EventObject {
    private Forma figura;
    private Color color;

    public LienzoEvent(Object source, Forma forma, Color color) {
        super(source);
        this.figura = forma;
        this.color = color;
    }

    public Forma getFigura() {
        return figura;
    }

    public void setFigura(Forma figura) {
        this.figura = figura;
    }

        public Color getColor() {
        return color;
    }
}
