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
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author raule
 */
public class Linea extends Forma {

    Line2D linea = new Line2D.Float();
    

    public Linea() {
        super();
        linea = new Line2D.Float();

    }

    public Linea(Point p1, Point p2, Color color, boolean transparente, boolean liso, int grosor, int discontinuidad) {
        super();
        linea = new Line2D.Float(p1, p2);
        setColor(color);
        setTransparente(transparente);
        setLiso(liso);
        setGrosor(grosor);
        setDiscontinuidad(discontinuidad);
    }

    public Line2D getLinea() {
        return linea;
    }

    public void setLinea(Line2D linea) {
        this.linea = linea;
    }


    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BasicStroke lineStroke;
        if (getDiscontinuidad() > 0) {
            // Multiplica la discontinuidad por 5 para hacerla más notable
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

        g2d.setPaint(getColor());
        g2d.setStroke(getStroke());
        g2d.setComposite(getAlphaComposite());
        g2d.draw(linea);
    }

    public String toString() {
        return "Linea";
    }

    public boolean isNear(Point2D p)
    {
        return this.getLinea().ptLineDist(p) <= 2.0;
    }
    
    @Override
    public boolean contains(Point2D p)
    {
        return isNear(p);
    }

    @Override
    public Shape figura() {
        return linea;
    }

}
