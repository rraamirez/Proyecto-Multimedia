/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.GRAFICOS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 *
 * @author raul
 */
public class Smile extends Forma {

    private Area cara;

    public Smile(Point p, Color color) {
        super();
        cara = new Area(new Ellipse2D.Float(p.x - 10, p.y - 10, 20, 20));

        Shape ojoIzda = new Ellipse2D.Float(p.x - 6, p.y - 5, 2, 4);
        Shape ojoDcha = new Ellipse2D.Float(p.x + 4, p.y - 5, 2, 4);
        Shape boca = new QuadCurve2D.Double(p.x - 5, p.y + 2, p.x, p.y + 6, p.x + 5, p.y + 2);
        cara.subtract(new Area(ojoIzda));
        cara.subtract(new Area(ojoDcha));
        cara.subtract(new Area(boca));

        setColor(color);
    }

    public Area getCara() {
        return cara;
    }

    @Override
    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(getColor());
        g2d.draw(cara);

    }

    @Override
    public void setLocation(Point2D p) {
        // Asumiendo que quieres mover la cara completa, pero esto debería
        // ajustarse para cada parte (ojos, boca) si es necesario.
        AffineTransform at = AffineTransform.getTranslateInstance(p.getX() - cara.getBounds2D().getX(),
                p.getY() - cara.getBounds2D().getY());
        cara.transform(at);
    }

    @Override
    public boolean contains(Point2D p) {
        return cara.contains(p);
    }
}
