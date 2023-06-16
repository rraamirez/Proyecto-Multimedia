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
import javax.sound.sampled.Clip;
import javax.swing.JLabel;

/**
 *
 * @author raul
 */
public class Lienzo2D extends javax.swing.JPanel {

    //Atributos de Shape//
    Forma figura;
    ArrayList<Forma> formas;

    //////GETTERS Y SETTERS DEL SHAPE////
    public Forma getFigura() {
        return figura;
    }

    public void setFigura(Forma figura) {
        this.figura = figura;
    }

    public ArrayList<Forma> getFormas() {
        return formas;
    }

    public void setFormas(ArrayList<Forma> formas) {
        this.formas = formas;
    }

    //*************DECLARACION DE VARIABLES***************//
    boolean relleno;
    boolean mover;
    boolean transparente;
    boolean liso;
    int grosor = 1;
    int discontinuidad = 0;
    tipos tipo = tipos.LINEA;
    Color color = Color.BLACK;
    Color colorRelleno = Color.BLACK;
    BufferedImage imagen;
    Stroke stroke;
    AlphaComposite alphaComposite;

    /**
     * Para movimiento de figuras.
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
     * Para mover el trazo libre
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

    //************************CONSTRUCTOR**********************//
    public Lienzo2D() {
        initComponents();
        stroke = new BasicStroke();
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        formas = new ArrayList<>();
        figura = new Linea();

    }

    //*************************GETTERS AND SETTERS*************************//
    /////////////////ATRIBUTOS DEL LIENZO QUE COGERÁN NUESTRAS FORMAS/////////
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

    public Point getInicial() {
        return puntoInicial;
    }

    public void setInicial(Point inicial) {
        this.puntoInicial = inicial;
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
     * El metodo paint será el encargado de pintar la forma que tengamos.
     * Invocamos al método pintar implementado en nuestras formas a dibujar.
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
            for (Forma f : formas) {
                f.pintar(g2d);
            }
        }
    }
    

    /**
     * Este metodo al ser invocado se encarga de dejar "la pizarra en blanco".
     * Para eso, limpiamos el vector de shape.
     */
    public void nuevoLienzo() {
        formas.clear();
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
     * de nuestra figura a volcar. Uso de Graphics2D.
     *
     * @param numFigura La figura que vamos a volcar
     */
    public void vuelca(int numFigura) {
        if (imagen != null) {

            Graphics2D g2dImagen = imagen.createGraphics();
            g2dImagen.setPaint(formas.get(numFigura).getColorRelleno());

            if (formas.get(numFigura).isRelleno()) {
                g2dImagen.fill(formas.get(numFigura).figura());
            }
            BasicStroke lineStroke;
            if (getDiscontinuidad() > 0) {
                float[] dashPattern = {getDiscontinuidad() * 5, getDiscontinuidad() * 5};
                lineStroke = new BasicStroke(getGrosor(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashPattern, 0);
            } else {
                lineStroke = new BasicStroke(getGrosor(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            }

            g2dImagen.setStroke(lineStroke);

            if (formas.get(numFigura).isTransparente()) {
                g2dImagen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            } else {
                g2dImagen.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }

            if (isLiso()) {
                g2dImagen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            g2dImagen.setPaint(formas.get(numFigura).getColor());
            g2dImagen.draw(formas.get(numFigura).figura());
        }

        this.getFormas().remove(numFigura);
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
        // Si se quiere mover una figura y hay una cerca del punto donde se hizo click
        if (mover && hayFiguraCerca(evt.getPoint())) {
            // Selecciona la figura más cercana al punto de click
            figura = getFiguraSeleccionada(evt.getPoint());
        }

        // Si no se quiere mover una figura
        if (!mover) {
            // Crea una nueva línea si el tipo seleccionado es Línea
            if (tipo == tipos.LINEA) {
                figura = new Linea(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());

            }

            // Crea una nueva elipse si el tipo seleccionado es Elipse
            if (tipo == tipos.ELIPSE) {
                figura = new Elipse(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.getColorRelleno(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
            }

            // Crea un nuevo rectángulo si el tipo seleccionado es Rectángulo
            if (tipo == tipos.RECTANGULO) {
                figura = new Rectangulo(evt.getPoint(), evt.getPoint(), this.getColor(),
                        this.getColorRelleno(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
            }

            // Crea una nueva curva si el tipo seleccionado es Curva
            if (tipo == tipos.CURVA) {
                // Si el punto de control está establecido, guarda el punto de control
                if (setPuntoControl) {
                    puntoControl = evt.getPoint();
                    pauxC = evt.getPoint();
                } else {
                    // Si el punto de control no está establecido, crea una nueva curva
                    figura = new Curva(evt.getPoint(), evt.getPoint(), evt.getPoint(), this.getColor(),
                            this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
                    punto1 = evt.getPoint();
                }
                this.repaint();
            }

            // Crea un nuevo dibujo libre si el tipo seleccionado es Libre
            if (tipo == tipos.LIBRE) {
                figura = new DibujoLibre(this.getColor(), this.isRelleno(), this.isTransparente(), this.isLiso(), this.getGrosor(), this.getDiscontinuidad());
                ((DibujoLibre) figura).getPath().moveTo(evt.getX(), evt.getY());
            }

            // Crea una nueva cara sonriente si el tipo seleccionado es Smile
            if (tipo == tipos.SMILE) {
                figura = new Smile(evt.getPoint(), this.getColor());

                this.repaint();
            }
            puntoInicial = evt.getPoint();

            // Si el punto de control no está establecido, añade la nueva figura a la lista de figuras
            if (!setPuntoControl) {
                notifyShapeAddedEvent(new LienzoEvent(this, figura, color));
                formas.add(figura);
            }
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if (mover) {
            // Si la figura seleccionada es una línea
            if (figura instanceof Linea) {
                // Se calcula la anchura y la altura de la línea actual
                anchura = ((Linea) figura).getLinea().getX2() - ((Linea) figura).getLinea().getX1();
                altura = ((Linea) figura).getLinea().getY2() - ((Linea) figura).getLinea().getY1();

                // Se crea una nueva línea que comienza en el punto donde se ha arrastrado el ratón
                // y termina en un punto que mantiene la misma anchura y altura respecto al punto inicial
                Line2D linea_temp = new Line2D.Double(evt.getPoint().getX(), evt.getPoint().getY(),
                        evt.getPoint().getX() + anchura, evt.getPoint().getY() + altura);

                // Se actualiza la línea original con la nueva línea
                ((Linea) figura).getLinea().setLine(linea_temp);
            }

            // Si la figura seleccionada es un rectángulo
            if (figura instanceof Rectangulo) {
                // Se establece una nueva posición para el rectángulo
                // El punto central del rectángulo se coloca en el punto donde se ha arrastrado el ratón
                ((Rectangulo) figura).getRectangulo().setRect(evt.getPoint().x - ((Rectangulo) figura).getRectangulo().getWidth() / 2,
                        evt.getPoint().y - ((Rectangulo) figura).getRectangulo().getHeight() / 2, ((Rectangulo) figura).getRectangulo().getWidth(),
                        ((Rectangulo) figura).getRectangulo().getHeight());
            }

            // Si la figura seleccionada es una elipse
            if (figura instanceof Elipse) {
                // Se establece una nueva posición para la elipse
                // El punto central de la elipse se coloca en el punto donde se ha arrastrado el ratón
                ((Elipse) figura).getElipse().setFrameFromDiagonal(evt.getPoint().x - ((Elipse) figura).getElipse().getWidth() / 2,
                        evt.getPoint().y - ((Elipse) figura).getElipse().getHeight() / 2, evt.getPoint().x + ((Elipse) figura).getElipse().getWidth() / 2,
                        evt.getPoint().y + ((Elipse) figura).getElipse().getHeight() / 2);
            }

            if (figura instanceof Curva) {
                // Se calcula la distancia en X y en Y que el ratón ha sido arrastrado desde la última posición
                int disX = evt.getX() - pauxC.x;
                int disY = evt.getY() - pauxC.y;

                // Se obtiene la curva actual
                QuadCurve2D curva = ((Curva) figura).getCurva();

                // Se obtienen los puntos de la curva
                Point2D.Float ini = (Point2D.Float) curva.getP1();
                Point2D.Float fin = (Point2D.Float) curva.getP2();
                Point2D.Float control = (Point2D.Float) curva.getCtrlPt();

                // Se actualizan las posiciones de los puntos de la curva sumando la distancia que el ratón ha sido arrastrado
                ini.x += disX;
                ini.y += disY;
                fin.x += disX;
                fin.y += disY;
                control.x += disX;
                control.y += disY;

                // Se actualiza la curva con los nuevos puntos
                curva.setCurve(ini, control, fin);

                // Se actualiza la última posición conocida del ratón
                pauxC = evt.getPoint();
            }

            if (figura instanceof DibujoLibre) {
                // Se calcula la distancia en X y en Y que el ratón ha sido arrastrado desde la última posición conocida
                int disX = evt.getX() - pauxTl.x;
                int disY = evt.getY() - pauxTl.y;

                // Se obtiene el camino del dibujo libre
                GeneralPath dibujo = ((DibujoLibre) figura).getPath();

                // Se crea una transformación para mover el camino en la cantidad que el ratón ha sido arrastrado
                AffineTransform at = AffineTransform.getTranslateInstance(disX, disY);

                // Se aplica la transformación al camino
                dibujo.transform(at);

                // Se actualiza la última posición conocida del ratón
                pauxTl = evt.getPoint();
            }

            // Si la figura seleccionada es una sonrisa (Smile)
            if (figura instanceof Smile) {
                // Se calcula la distancia en X y en Y que el ratón ha sido arrastrado desde la última posición conocida
                int disX = evt.getX() - pauxSmile.x;
                int disY = evt.getY() - pauxSmile.y;

                // Se crea una transformación para mover la sonrisa en la cantidad que el ratón ha sido arrastrado
                ((Smile) figura).getCara().transform(AffineTransform.getTranslateInstance(disX, disY));

                // Se actualiza la última posición conocida del ratón
                pauxSmile = evt.getPoint();
            }

        }

        if (!mover) {
            if (tipo == tipos.LINEA) {
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

    /**
     * 
     * @param evt 
     */
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
     * @return vector de formas nuevo
     */
    public ArrayList getNewShapeList() {
        return formas;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
