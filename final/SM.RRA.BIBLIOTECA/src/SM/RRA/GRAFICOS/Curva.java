/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.GRAFICOS;

import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;

/**
 *
 * @author raul
 */
public class Curva extends Forma {

    //Atributo curva
    private QuadCurve2D curva;

    /**
     * Constructor parametrizado. Esencial para poder coger los colores de
     * nuestro Lienzo2D
     */
    public Curva(Point p1, Point p2, Point ctrl, Color color,
            boolean transparente, boolean liso, int grosor, int discontinuidad) {
        super();
        curva = new QuadCurve2D.Float(p1.x, p1.y, ctrl.x, ctrl.y, p2.x, p2.y);

        setColor(color);
        setColorRelleno(colorRelleno);
        setRelleno(relleno);
        setTransparente(transparente);
        setLiso(liso);
        setGrosor(grosor);
        setDiscontinuidad(discontinuidad);
    }

    public QuadCurve2D getCurva() {
        return curva;
    }

    
    @Override
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
        g2d.draw(curva);
    }

    @Override
    public boolean contains(Point2D p) {
        return curva.contains(p);
    }

    @Override
    public Shape figura() {
        return curva;
    }

    @Override
    public String toString() {
        return "Curva";
    }

}
