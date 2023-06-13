/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IU;

import java.awt.Color;
import java.awt.Shape;
import java.util.EventObject;

/**
 *
 * @author raul
 */
public class LienzoEvent extends EventObject {

    private Shape forma;
    private Color color;

    public LienzoEvent(Object source, Shape forma, Color color) {
        super(source);
        this.forma = forma;
        this.color = color;
    }

    public Shape getForma() {
        return forma;
    }

    public Color getColor() {
        return color;
    }
}
