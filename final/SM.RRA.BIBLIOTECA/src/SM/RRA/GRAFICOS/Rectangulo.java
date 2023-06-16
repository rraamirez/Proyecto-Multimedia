/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.GRAFICOS;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author raul
 */
public class Rectangulo extends Forma {

    private Rectangle2D rectangulo;

    public Rectangulo() {
        super();
        rectangulo = new Rectangle2D.Float();
    }

    public Rectangulo(Point p1, Point p2, Color color, Color colorRelleno, boolean relleno, boolean transparente, boolean liso, int grosor, int discontinuidad) {
        super();
        rectangulo = new Rectangle2D.Float(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
        setColor(color);
        setColorRelleno(colorRelleno);
        setRelleno(relleno);
        setTransparente(transparente);
        setLiso(liso);
        setGrosor(grosor);
        setDiscontinuidad(discontinuidad);
    }

    public Rectangle2D getRectangulo() {
        return rectangulo;
    }

    public void setRectangulo(Rectangle2D rectangulo) {
        this.rectangulo = rectangulo;
    }

    @Override
    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BasicStroke lineStroke;
        if (getDiscontinuidad() > 0) {
            float[] dashPattern = {getDiscontinuidad() * 5, getDiscontinuidad() * 5};
            lineStroke = new BasicStroke(getGrosor(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashPattern, 0);
        } else {
            lineStroke = new BasicStroke(getGrosor(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        }

        setStroke(lineStroke);

        setAlphaComposite(isTransparente() ? AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
                : AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        if (isLiso()) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        g2d.setComposite(getAlphaComposite());

        if (isRelleno()) {
            g2d.setPaint(getColorRelleno());
            g2d.fill(rectangulo);
        }

        g2d.setPaint(getColor());
        g2d.setStroke(getStroke());
        g2d.draw(rectangulo);
    }

    @Override
    public boolean contains(Point2D p) {
        return rectangulo.contains(p);
    }

    @Override
    public String toString() {
        return "Rectangulo";
    }

    @Override
    public Shape figura() {
        return rectangulo;
    }
}
