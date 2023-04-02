/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica5;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author raul
 */
public abstract class Linea extends Line2D {
    public abstract boolean isNear(Point2D p);
    public abstract boolean contains(Line2D p);  
}
