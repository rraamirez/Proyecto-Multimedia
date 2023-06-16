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
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author raul
 */
public class DibujoLibre extends Forma {

    private GeneralPath path;

    public DibujoLibre() {
        super();
        path = new GeneralPath();
    }

    public DibujoLibre(Color color, boolean relleno, boolean transparente, boolean liso, int grosor, int discontinuidad) {
        super();
        path = new GeneralPath(Path2D.WIND_EVEN_ODD);
        setColor(color);
        setRelleno(relleno);
        setTransparente(transparente);
        setLiso(liso);
        setGrosor(grosor);
        setDiscontinuidad(discontinuidad);
    }

    public GeneralPath getPath() {
        return path;
    }

    public void setPath(GeneralPath path) {
        this.path = path;
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


        g2d.setPaint(getColor());
        g2d.setStroke(getStroke());
        g2d.draw(path);
    }

    @Override
    public boolean contains(Point2D p) {
        return path.contains(p);
    }

    @Override
    public String toString() {
        return "GeneralPathForma";
    }

    @Override
    public Shape figura() {
        return path;
    }
}
