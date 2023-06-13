package SM.RRA.IU;

import SM.RRA.GRAFICOS.Curva;
import SM.RRA.GRAFICOS.DibujoLibre;
import SM.RRA.GRAFICOS.Elipse;
import SM.RRA.GRAFICOS.Forma;
import SM.RRA.GRAFICOS.Linea;
import SM.RRA.GRAFICOS.Rectangulo;
import SM.RRA.GRAFICOS.Smile;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
import javax.swing.JLabel;

/**
 *
 * @author raul
 */
public class Lienzo2D extends javax.swing.JPanel {

    /////PRACTICA FINAL/////////////
    Forma figura;
    Linea linea;
    ArrayList<Forma> formas;

    public Forma getFigura() {
        return figura;
    }

    public void setFigura(Forma figura) {
        this.figura = figura;
    }

    //*************DECLARACION DE VARIABLES***************//
    boolean relleno;
    boolean mover;
    boolean transparente;
    boolean liso;
    int grosor = 1;
    int discontinuidad = 0;
    //Line2D linea;
    tipos tipo = tipos.LINEA;
    Shape forma;
    ArrayList<Shape> vShape;
    Color color = Color.BLACK;
    Color colorRelleno = Color.BLACK;
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

    /**
     * ************************************************************
     */
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();
    private javax.swing.JLabel statusBar;
    //private MouseEvent lastMouseEvent;

    //************************CONSTRUCTOR**********************//
    public Lienzo2D() {
        initComponents();
        stroke = new BasicStroke();
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        vShape = new ArrayList<>();
        formas = new ArrayList<>();
        forma = new Line2D.Float(0, 0, 0, 0);
        figura = new Linea();
        statusBar = new JLabel();
        
    }

    //*************************GETTERS AND SETTERS*************************//
    /*public MouseEvent getLastMouseEvent() {
        return lastMouseEvent;
    }*/

    public JLabel getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(JLabel statusBar) {
        this.statusBar = statusBar;
    }

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

    public Color getColorRelleno() {
        return colorRelleno;
    }

    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
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

    public int getDiscontinuidad() {
        return discontinuidad;
    }

    public void setDiscontinuidad(int discontinuidad) {
        this.discontinuidad = discontinuidad;
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
            //Shape oldClip = g2d.getClip();  // guarda el antiguo clip
            //g2d.setClip(0, 0, imagen.getWidth(), imagen.getHeight());  // establece el nuevo clip

            g2d.drawImage(imagen, 0, 0, this);

            for (Forma f : formas) {
                f.pintar(g2d);
            }

            //g2d.setClip(oldClip);  // restaura el antiguo clip solo después de terminar de dibujar
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
    private Forma getFiguraSeleccionada(Point2D p) {
        System.out.print("Figura seleccionada");
        for (int i = formas.size() - 1; i >= 0; i--) {
            Forma s = formas.get(i);
            if (s.contains(p)) {
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

    /**
     * Implementación extremadamente sencilla para el volcado de figuras. Se ha
     * tenido en cuenta el relleno, el antialiasing, la transparencia y el color
     * de nuestra figura a volcar.
     *
     * @param numFigura La figura que vamos a volcar
     */
    public void vuelca(int numFigura) {
        if (imagen != null) {
            Graphics2D g2dImagen = imagen.createGraphics();
            g2dImagen.setPaint(color);

            if (relleno) {
                g2dImagen.fill(vShape.get(numFigura));
            }
            if (isTransparente()) {
                this.setAlphaComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            } else {
                this.setAlphaComposite(alphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
            if (isLiso()) {
                g2dImagen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
            g2dImagen.setPaint(color);
            g2dImagen.setStroke(stroke);
            g2dImagen.setComposite(alphaComposite);
            g2dImagen.draw(vShape.get(numFigura));
        }

        this.getvShape().remove(numFigura);
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
            figura = getFiguraSeleccionada(evt.getPoint());
        }

        if (!mover) {
            if (tipo == tipos.LINEA) {
                //forma = new Line2D.Float(evt.getPoint(), evt.getPoint());
                figura = new Linea(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());

            }

            if (tipo == tipos.ELIPSE) {
                figura = new Elipse(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.getColorRelleno(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
            }

            if (tipo == tipos.RECTANGULO) {
                figura = new Rectangulo(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.getColorRelleno(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
            }

            if (tipo == tipos.CURVA) {
                if (setPuntoControl) {
                    puntoControl = evt.getPoint();
                    pauxC = evt.getPoint();
                } else {
                    figura = new Curva(evt.getPoint(), evt.getPoint(), evt.getPoint(), this.getColor(),
                            this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
                    punto1 = evt.getPoint();
                }
                this.repaint();
            }

            if (tipo == tipos.LIBRE) {
                figura = new DibujoLibre(this.getColor(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
                ((DibujoLibre) figura).getPath().moveTo(evt.getX(), evt.getY());
            }

            if (tipo == tipos.SMILE) {
                figura = new Smile(evt.getPoint(), this.getColor());

                this.repaint();
            }
            puntoInicial = evt.getPoint();

            if (!setPuntoControl) {
                vShape.add(forma);
                notifyShapeAddedEvent(new LienzoEvent(this, forma, color));
                formas.add(figura);
            }
        }


    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if (mover) {
            if (figura instanceof Linea) {

                anchura = ((Linea) figura).getLinea().getX2() - ((Linea) figura).getLinea().getX1();
                altura = ((Linea) figura).getLinea().getY2() - ((Linea) figura).getLinea().getY1();
                Line2D linea_temp = new Line2D.Double(evt.getPoint().getX(), evt.getPoint().getY(),
                        evt.getPoint().getX() + anchura, evt.getPoint().getY() + altura);
                ((Linea) figura).getLinea().setLine(linea_temp);
            }

            if (figura instanceof Rectangulo) {
                ((Rectangulo) figura).getRectangulo().setRect(evt.getPoint().x - ((Rectangulo) figura).getRectangulo().getWidth() / 2,
                        evt.getPoint().y - ((Rectangulo) figura).getRectangulo().getHeight() / 2, ((Rectangulo) figura).getRectangulo().getWidth(),
                        ((Rectangulo) figura).getRectangulo().getHeight());
            }

            if (figura instanceof Elipse) {
                ((Elipse) figura).getElipse().setFrameFromDiagonal(evt.getPoint().x - ((Elipse) figura).getElipse().getWidth() / 2,
                        evt.getPoint().y - ((Elipse) figura).getElipse().getHeight() / 2, evt.getPoint().x + ((Elipse) figura).getElipse().getWidth() / 2,
                        evt.getPoint().y + ((Elipse) figura).getElipse().getHeight() / 2);
            }

            if (figura instanceof Curva) {
                int disX = evt.getX() - pauxC.x;
                int disY = evt.getY() - pauxC.y;

                QuadCurve2D curva = ((Curva) figura).getCurva();
                Point2D.Float ini = (Point2D.Float) curva.getP1();
                Point2D.Float fin = (Point2D.Float) curva.getP2();
                Point2D.Float control = (Point2D.Float) curva.getCtrlPt();

                ini.x += disX;
                ini.y += disY;
                fin.x += disX;
                fin.y += disY;
                control.x += disX;
                control.y += disY;

                curva.setCurve(ini, control, fin);

                pauxC = evt.getPoint();
            }

            if (figura instanceof DibujoLibre) {
                int disX = evt.getX() - pauxTl.x;
                int disY = evt.getY() - pauxTl.y;
                GeneralPath dibujo = ((DibujoLibre) figura).getPath();
                AffineTransform at = AffineTransform.getTranslateInstance(disX, disY);
                dibujo.transform(at);
                pauxTl = evt.getPoint();
            }

            if (figura instanceof Smile) {
                int disX = evt.getX() - pauxSmile.x;
                int disY = evt.getY() - pauxSmile.y;
                ((Smile) figura).getCara().transform(AffineTransform.getTranslateInstance(disX, disY));
                pauxSmile = evt.getPoint();
            }

        }

        if (!mover) {
            if (tipo == tipos.LINEA) {
                //((Line2D) forma).setLine(((Line2D) forma).getP1(), evt.getPoint());
                //anchura = evt.getX() - puntoInicial.x;
                //altura = evt.getY() - puntoInicial.y;
                ((Linea) figura).getLinea().setLine(((Linea) figura).getLinea().getP1(), evt.getPoint());

            }

            if (tipo == tipos.ELIPSE) {
                ((Elipse) figura).getElipse().setFrameFromDiagonal(puntoInicial, evt.getPoint());
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }

            if (tipo == tipos.RECTANGULO) {
                ((Rectangulo) figura).getRectangulo().setFrameFromDiagonal(puntoInicial, evt.getPoint());
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }

            if (tipo == tipos.CURVA) {
                if (setPuntoControl) {
                    puntoControl = evt.getPoint();
                    ((Curva) figura).getCurva().setCurve(((Curva) figura).getCurva().getP1(), puntoControl, ((Curva) figura).getCurva().getP2());
                    pauxC = evt.getPoint();
                } else {
                    ((Curva) figura).getCurva().setCurve(((Curva) figura).getCurva().getP1(), ((Curva) figura).getCurva().getP1(), evt.getPoint());
                    punto2 = evt.getPoint();
                }

            }

            if (tipo == tipos.LIBRE) {
                ((DibujoLibre) figura).getPath().lineTo(evt.getX(), evt.getY());
                pauxTl = evt.getPoint();
            }
        }

        this.repaint();

    }//GEN-LAST:event_formMouseDragged


    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (tipo == tipos.CURVA) {
            setPuntoControl = !setPuntoControl;
        }


    }//GEN-LAST:event_formMouseReleased

    /**
     * Metodo que añade el listener
     *
     * @param listener
     */
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

    /**
     *
     * @return vector de formas
     */
    public ArrayList getShapeList() {
        return vShape;
    }
    
    /**
     *
     * @return vector de formas nuevo
     */
    public ArrayList getNewShapeList() {
        return formas;
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
