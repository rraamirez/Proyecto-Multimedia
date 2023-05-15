package SM.RRA.IU;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author raul
 */
public class Lienzo2D extends javax.swing.JPanel {

    //*************DECLARACION DE VARIABLES***************//
    boolean relleno;
    boolean mover;
    boolean transparente;
    boolean liso;
    int grosor = 1;
    Line2D linea;
    tipos tipo = tipos.LINEA;
    Shape forma;
    ArrayList<Shape> vShape;
    Color color = Color.BLACK;
    BufferedImage imagen;
    Stroke stroke;
    Area smileArea;
    AlphaComposite alphaComposite;

    /**
     * Para movimiento de figuras (las tres iniciales)
     */
    Point puntoInicial = new Point();
    Point puntoTmp = new Point();
    double anchura, altura;

    /**
     * Para la curva necesitamos su punto de inicio, fin y el control. El
     * booleano setPuntoControl está hecho para saber si estamos en el momento
     * de trazar la línea o marcar el punto de control en nuestro lienzo.
     */
    Point punto1 = new Point();
    Point punto2 = new Point();
    Point puntoControl = new Point();
    boolean setPuntoControl = false;
    Point pauxC = new Point();

    /**
     * Para mover el trazo
     */
    Point pauxTl = new Point();

    /**
     * Para mover el smilenecesitamos un punto auxiliar que guarde la posición
     * del area.
     */
    Point pauxSmile = new Point();

    //************************CONSTRUCTOR**********************//
    public Lienzo2D() {
        initComponents();
        stroke = new BasicStroke();
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        vShape = new ArrayList<>();
        forma = new Line2D.Float(0, 0, 0, 0);
    }

    //*************************GETTERS AND SETTERS*************************//
    public tipos getTipo() {
        return tipo;
    }

    public void setTipo(tipos tipo) {
        this.tipo = tipo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRelleno() {
        return relleno;
    }

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    public boolean isMover() {
        return mover;
    }

    public void setMover(boolean mover) {
        this.mover = mover;
    }

    public Line2D getLinea() {
        return linea;
    }

    public void setLinea(Line2D linea) {
        this.linea = linea;
    }

    public Shape getForma() {
        return forma;
    }

    public void setForma(Shape forma) {
        this.forma = forma;
    }

    public Point getInicial() {
        return puntoInicial;
    }

    public void setInicial(Point inicial) {
        this.puntoInicial = inicial;
    }

    public ArrayList<Shape> getvShape() {
        return vShape;
    }

    public void setvShape(ArrayList<Shape> vShape) {
        this.vShape = vShape;
    }

    public Point getPuntoInicial() {
        return puntoInicial;
    }

    public void setPuntoInicial(Point puntoInicial) {
        this.puntoInicial = puntoInicial;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * @brief Asignamos imagen pasada como parámetro
     * @param imagen Imagen que pasamos.
     */
    public void setImagen(BufferedImage imagen) {
        if (imagen != null) {
            setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
        }
        this.imagen = imagen;
    }

    public boolean isTransparente() {
        return transparente;
    }

    public void setTransparente(boolean transparente) {
        this.transparente = transparente;
    }

    public int getGrosor() {
        return grosor;
    }

    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }

    public boolean isLiso() {
        return liso;
    }

    public void setLiso(boolean liso) {
        this.liso = liso;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public AlphaComposite getAlphaComposite() {
        return alphaComposite;
    }

    public void setAlphaComposite(AlphaComposite alphaComposite) {
        this.alphaComposite = alphaComposite;
    }
    
    

    //**************** MÉTODO PAINT ******************//
    /**
     * El metodo paint será el encargado de (solo y exclusivamente) pintar la
     * forma que tengamos.
     *
     * @param g Se le hace casting para que funcione como si fuese de Graphics2D
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //Si hay imagen la pintamos
        if (imagen != null) {
            g2d.drawImage(imagen, 0, 0, this);
        }

        //Establecemos nuestro trazo
        this.setStroke(new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Shape forma : vShape) {
            if (relleno) {
                g2d.fill(forma);
            }

            //En función  a si es transparente o no, le damos un alfa distinto.
            if (isTransparente()) {
                this.setAlphaComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            } else {
                this.setAlphaComposite(alphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }

            //Si el bool es true, activamos antialiasing
            if (isLiso()) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            g2d.setPaint(color);
            g2d.setStroke(stroke);
            g2d.setComposite(alphaComposite);
            g2d.draw(forma);
        }

    }

    /**
     * Este metodo al ser invocado se encarga de dejar "la pizarra en blanco".
     * Para eso, limpiamos el vector de shape.
     */
    public void nuevoLienzo() {
        vShape.clear();
        this.repaint();
    }

    /**
     *
     * @param p Punto presionado
     * @return true o false si tenemos una figura cerca de nuestro pressed
     */
    public boolean hayFiguraCerca(Point2D p) {
        return getFiguraSeleccionada(p) != null;
    }

    /**
     * Este metodo devuelve la figura seleccionada dado un punto
     */
    private Shape getFiguraSeleccionada(Point2D p) {
        for (int i = vShape.size() - 1; i >= 0; i--) {
            Shape s = vShape.get(i);
            if (s.contains(p)) {
                return s;
            }
            if (s instanceof Line2D && ((Line2D) s).ptLineDist(p) <= 2.0) {
                return s;
            }
        }
        return null;
    }

    /**
     * @brief Se encarga de "coger" la imagen con sus dibujos.
     * @param pintaVector Si hemos dibujado encima de la imagen para poder
     * guardar los dibujos.
     * @return nuestra imagen final de salida
     */
    public BufferedImage getImagen(boolean pintaVector) {
        if (pintaVector) {
            BufferedImage imgout = new BufferedImage(imagen.getWidth(),
                imagen.getHeight(),
                imagen.getType());
            Graphics2D g2dImagen = imgout.createGraphics();
            this.paint(imgout.createGraphics());
            return (imgout);
        } else {
            return imagen;
        }
    }
    
    public BufferedImage getImagen(int numFigura) {
            BufferedImage imgout = new BufferedImage(imagen.getWidth(),
                imagen.getHeight(),
                imagen.getType());
            Graphics2D g2dImagen = imgout.createGraphics();
            g2dImagen.draw(vShape.get(numFigura));
            return (imgout);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed

        if (mover && hayFiguraCerca(evt.getPoint())) {
            forma = getFiguraSeleccionada(evt.getPoint());
        }

        if (!mover) {
            if (tipo == tipos.LINEA) {
                forma = new Line2D.Float(evt.getPoint(), evt.getPoint());
            }

            if (tipo == tipos.ELIPSE) {
                forma = new Ellipse2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
            }

            if (tipo == tipos.RECTANGULO) {
                forma = new Rectangle2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
            }

            if (tipo == tipos.CURVA) {
                if (setPuntoControl) {
                    puntoControl = evt.getPoint();
                    pauxC = evt.getPoint();
                } else {
                    forma = new QuadCurve2D.Double(evt.getPoint().getX(), evt.getPoint().getY(), evt.getPoint().getX(),
                            evt.getPoint().getY(), evt.getPoint().getX(), evt.getPoint().getY());
                    punto1 = evt.getPoint();
                }
                this.repaint();
            }

            if (tipo == tipos.LIBRE) {
                forma = new GeneralPath(Path2D.WIND_EVEN_ODD);
                ((GeneralPath) forma).moveTo(evt.getX(), evt.getY());
            }

            if (tipo == tipos.SMILE) {
                Point punto = evt.getPoint();
                Area cara = new Area(new Ellipse2D.Float(punto.x - 10, punto.y - 10, 20, 20));

                Shape ojoIzda = new Ellipse2D.Float(punto.x - 6, punto.y - 5, 2, 4);
                Shape ojoDcha = new Ellipse2D.Float(punto.x + 4, punto.y - 5, 2, 4);
                Shape boca = new QuadCurve2D.Double(punto.x - 5, punto.y + 2, punto.x, punto.y + 6, punto.x + 5, punto.y + 2);
                cara.subtract(new Area(ojoIzda));
                cara.subtract(new Area(ojoDcha));
                cara.subtract(new Area(boca));

                pauxSmile = evt.getPoint();

                vShape.add(cara);
                this.repaint();
            }
            puntoInicial = evt.getPoint();
            vShape.add(forma);
            notifyShapeAddedEvent( new LienzoEvent(this,forma,color) );
        }


    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if (mover) {
            if (forma != null && forma instanceof Line2D) {
                anchura = ((Line2D) forma).getX2() - ((Line2D) forma).getX1();
                altura = ((Line2D) forma).getY2() - ((Line2D) forma).getY1();
                Line2D linea_temp = new Line2D.Double(evt.getPoint().getX(), evt.getPoint().getY(),
                        evt.getPoint().getX() + anchura, evt.getPoint().getY() + altura);
                ((Line2D) forma).setLine(linea_temp);
            }

            if (forma != null && forma instanceof Rectangle2D) {
                ((Rectangle2D) forma).setRect(evt.getPoint().x - ((Rectangle2D) forma).getWidth() / 2,
                        evt.getPoint().y - ((Rectangle2D) forma).getHeight() / 2, ((Rectangle2D) forma).getWidth(),
                        ((Rectangle2D) forma).getHeight());
            }

            if (forma != null && forma instanceof Ellipse2D) {
                ((Ellipse2D) forma).setFrameFromDiagonal(evt.getPoint().x - ((Ellipse2D) forma).getWidth() / 2,
                        evt.getPoint().y - ((Ellipse2D) forma).getHeight() / 2, evt.getPoint().x + ((Ellipse2D) forma).getWidth() / 2,
                        evt.getPoint().y + ((Ellipse2D) forma).getHeight() / 2);
            }

            /**
             * IMPORTANTE Por el motivo que sea, esta implementación no termina
             * de funcionar en todos los casos, ya que genera una
             * nullpointerexception si tenemos varias curvas juntas y queremos
             * mover una.
             */
            if (forma != null && forma instanceof QuadCurve2D) {
                int disX = evt.getX() - pauxC.x;
                int disY = evt.getY() - pauxC.y;

                QuadCurve2D curva = (QuadCurve2D) forma;
                Point2D.Double ini = (Point2D.Double) curva.getP1();
                Point2D.Double fin = (Point2D.Double) curva.getP2();
                Point2D.Double control = (Point2D.Double) curva.getCtrlPt();

                ini.x += disX;
                ini.y += disY;
                fin.x += disX;
                fin.y += disY;
                control.x += disX;
                control.y += disY;

                curva.setCurve(ini, control, fin);

                pauxC = evt.getPoint();
            }

            if (forma != null && forma instanceof GeneralPath) {
                int disX = evt.getX() - pauxTl.x;
                int disY = evt.getY() - pauxTl.y;
                GeneralPath dibujo = (GeneralPath) forma;
                AffineTransform at = AffineTransform.getTranslateInstance(disX, disY);
                dibujo.transform(at);
                pauxTl = evt.getPoint();
            }

            if (forma != null && forma instanceof Area) {
                int disX = evt.getX() - pauxSmile.x;
                int disY = evt.getY() - pauxSmile.y;
                ((Area) forma).transform(AffineTransform.getTranslateInstance(disX, disY));
                pauxSmile = evt.getPoint();
            }

        }

        if (!mover) {
            if (tipo == tipos.LINEA) {
                ((Line2D) forma).setLine(((Line2D) forma).getP1(), evt.getPoint());
                anchura = evt.getX() - puntoInicial.x;
                altura = evt.getY() - puntoInicial.y;
            }

            if (tipo == tipos.ELIPSE) {
                ((Ellipse2D) forma).setFrameFromDiagonal(puntoInicial, evt.getPoint());
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }

            if (tipo == tipos.RECTANGULO) {
                ((Rectangle2D) forma).setFrameFromDiagonal(puntoInicial, evt.getPoint());
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }

            if (tipo == tipos.CURVA) {
                if (setPuntoControl) {
                    puntoControl = evt.getPoint();
                    ((QuadCurve2D) forma).setCurve(((QuadCurve2D) forma).getP1(), puntoControl, ((QuadCurve2D) forma).getP2());
                    pauxC = evt.getPoint();
                } else {
                    ((QuadCurve2D) forma).setCurve(((QuadCurve2D) forma).getP1(), ((QuadCurve2D) forma).getP1(), evt.getPoint());
                    punto2 = evt.getPoint();
                }

            }

            if (tipo == tipos.LIBRE) {
                ((GeneralPath) forma).lineTo(evt.getX(), evt.getY());
                pauxTl = evt.getPoint();
            }
        }

        this.repaint();

    }//GEN-LAST:event_formMouseDragged


    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (!setPuntoControl) {
            setPuntoControl = true;
        } else
            setPuntoControl = false;
    }//GEN-LAST:event_formMouseReleased

    /**
     * ******* PRACTICA 9-10
     *///////////////
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();

    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            lienzoEventListeners.add(listener);
        }
    }

    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }

    private void notifyPropertyChangeEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.propertyChange(evt);
            }
        }
    }

    public ArrayList getShapeList() {
        return vShape;
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
