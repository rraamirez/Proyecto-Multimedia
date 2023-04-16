package SM.RRA.IU;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author raul
 */
public class Lienzo2D extends javax.swing.JPanel{
    
    //*************DECLARACION DE VARIABLES***************//
    
    boolean relleno;
    boolean mover;
    boolean transparente;
    Line2D linea;
    tipos tipo = tipos.LINEA;
    Shape forma = new Line2D.Float(0,0,0,0); 
    ArrayList<Shape> vShape = new ArrayList<>(); 
    Color color = Color.BLACK;
    BufferedImage imagen;
    Stroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    Area smileArea;
    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

    
    
    /**
     * Para movimiento de figuras
     */
    Point puntoInicial = new Point();
    Point puntoTmp;
    double anchura, altura;
    
    /**
     * Para la curva
     */
    Point punto1, punto2, puntoControl;
    
    
    //************************CONSTRUCTOR**********************//
    
    public Lienzo2D() {
        initComponents();
        
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
    

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public void setGrosor(){
        
    }
    
    public void setAlisado(){
        
    }

    public boolean isTransparente() {
        return transparente;
    }

    public void setTransparente(boolean transparente) {
        this.transparente = transparente;
    }
    
    
    
    //**************** MÉTODO PAINT ******************//
    
    /**
     * El metodo paint será el encargado de (solo y exclusivamente)
     * pintar la forma que tengamos.
     * No es correcto declarar objetos forma dentro ni modificarlos.
     * @param g Se le hace casting para que funcione como si fuese de Graphics2D
     */
  
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        if(imagen != null){
            g2d.drawImage(imagen, 0, 0, this);
        }
        
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        g2d.setComposite(alphaComposite);
        
        for(Shape forma:vShape){
            if(relleno){
                g2d.fill(forma);
            }
            
            AlphaComposite alphaComposite1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            AlphaComposite alphaComposite2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            if(transparente){
                this.alphaComposite = alphaComposite1;
            }else{
                this.alphaComposite = alphaComposite2;
            }
            
            g2d.draw(forma);
        }
        
    }
    
    /**
     * Este metodo al ser invocado se encarga de dejar "la pizarra en blanco".
     * Para eso, limpiamos el vector de shape.
     */
    public void nuevoLienzo(){
        vShape.clear();
        this.repaint();
    }
    
    
    /**
     * Este metodo devuelve la figura seleccionada dado un punto
     */
    
     private Shape getFiguraSeleccionada(Point2D p){
        for (int i = vShape.size() - 1; i >= 0; i--) {
            Shape s = vShape.get(i);
            if (s.contains(p)) {
                return s;
            }
            if (s instanceof Line2D && ((Line2D)s).ptLineDist(p) <= 2.0) {
                return s;
            }
        }
        return null;        
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
        if(mover){
            forma = getFiguraSeleccionada(evt.getPoint());
        }
        
        if(!mover){
            if(tipo == tipos.LINEA){
                forma = new Line2D.Float(evt.getPoint(), evt.getPoint());
            }

            if(tipo == tipos.ELIPSE){
                forma = new Ellipse2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);          
            }

            if(tipo == tipos.RECTANGULO){
                forma = new Rectangle2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);     
            }   
            
            if(tipo == tipos.CURVA){
                   if (punto1 == null) {
                        punto1 = evt.getPoint();
                    } else if (punto2 == null) {
                        punto2 = evt.getPoint();
                    } else {
                        puntoControl = evt.getPoint();
                        // Dibujar la curva usando los puntos y el punto de control
                        QuadCurve2D curva = new QuadCurve2D.Double(punto1.getX(), punto1.getY(),
                            puntoControl.getX(), puntoControl.getY(), punto2.getX(), punto2.getY());
                        // Agregar la curva al conjunto de figuras
                        vShape.add(curva);
                        // Reiniciar los puntos para poder dibujar una nueva curva
                        punto1 = null;
                        punto2 = null;
                        puntoControl = null;
                    }
            }
            
            if(tipo == tipos.LIBRE){
                
            }
            
            if(tipo == tipos.SMILE){
                Point mousePosition = evt.getPoint();

                    // Crear el objeto Area que contiene el smile
                    Point2D p1 = new Point2D.Double(mousePosition.getX() - 50, mousePosition.getY() - 50);
                    Point2D p2 = new Point2D.Double(mousePosition.getX() + 50, mousePosition.getY() - 50);
                    Point2D cp = new Point2D.Double(mousePosition.getX(), mousePosition.getY());
                    QuadCurve2D smileCurve = new QuadCurve2D.Double(p1.getX(), p1.getY(), cp.getX(), cp.getY(), p2.getX(), p2.getY());
                    Ellipse2D leftEye = new Ellipse2D.Double(mousePosition.getX() - 40, mousePosition.getY() - 70, 10, 10);
                    Ellipse2D rightEye = new Ellipse2D.Double(mousePosition.getX() + 20, mousePosition.getY() - 70, 10, 10);
                    Area smile = new Area(smileCurve);
                    smile.add(new Area(leftEye));
                    smile.add(new Area(rightEye));

                    // Agregar el objeto Area al panel y repintar
                    smileArea = smile;
                    repaint();
            }
        }
            
        puntoInicial = evt.getPoint();
        vShape.add(forma);
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if(mover){
            if (forma!=null && forma instanceof Line2D){
                anchura = ((Line2D)forma).getX2()-((Line2D)forma).getX1();
                altura = ((Line2D)forma).getY2()-((Line2D)forma).getY1();  
                Line2D linea_temp = new Line2D.Double(evt.getPoint().getX(), evt.getPoint().getY(),
                        evt.getPoint().getX()+anchura, evt.getPoint().getY()+altura);
                ((Line2D)forma).setLine(linea_temp);
            }
            
            if (forma!=null && forma instanceof Rectangle2D){
                ((Rectangle2D)forma).setRect(evt.getPoint().x-((Rectangle2D)forma).getWidth()/2, 
                        evt.getPoint().y-((Rectangle2D)forma).getHeight()/2, ((Rectangle2D)forma).getWidth(),
                      ((Rectangle2D)forma).getHeight());
            }
            
            if (forma!=null && forma instanceof Ellipse2D){
                ((Ellipse2D)forma).setFrameFromDiagonal(evt.getPoint().x-((Ellipse2D)forma).getWidth()/2, 
                        evt.getPoint().y-((Ellipse2D)forma).getHeight()/2, evt.getPoint().x+((Ellipse2D)forma).getWidth()/2,
                        evt.getPoint().y+((Ellipse2D)forma).getHeight()/2);
            }
            
            if(forma != null && forma instanceof QuadCurve2D){
                ((QuadCurve2D)forma).setCurve(((QuadCurve2D)forma).getP1(), evt.getPoint(), ((QuadCurve2D)forma).getP2());
            }
            
            
        }
        
        if(!mover){
            if(tipo == tipos.LINEA){
                ((Line2D)forma).setLine(((Line2D)forma).getP1(), evt.getPoint());
                anchura = evt.getX() - puntoInicial.x;
                altura = evt.getY() - puntoInicial.y;
            }

            if(tipo == tipos.ELIPSE){
                ((Ellipse2D)forma).setFrameFromDiagonal(puntoInicial, evt.getPoint()); 
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }

            if(tipo == tipos.RECTANGULO){
                ((Rectangle2D)forma).setFrameFromDiagonal(puntoInicial, evt.getPoint());  
                anchura = abs(-evt.getX() + puntoInicial.x);
                altura = abs(-evt.getY() + puntoInicial.y);
            }
            
            if (tipo == tipos.CURVA) {
                if (punto1 == null) {
                    punto1 = evt.getPoint();
                    } else if (punto2 == null) {
                        punto2 = evt.getPoint();
                    } else {
                        puntoControl = evt.getPoint();
                        // Dibujar la curva usando los puntos y el punto de control
                        QuadCurve2D curva = new QuadCurve2D.Double(punto1.getX(), punto1.getY(),
                                puntoControl.getX(), puntoControl.getY(), punto2.getX(), punto2.getY());
                        // Reemplazar la figura de la posición anterior por la nueva curva
                        vShape.set(vShape.size() - 1, curva);
                        // Reiniciar los puntos para poder dibujar una nueva curva
                        punto1 = null;
                        punto2 = null;
                        puntoControl = null;
                    }
            }
            
            if(tipo == tipos.LIBRE){
                
            }
        }
        
        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
