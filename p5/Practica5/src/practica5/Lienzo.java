package practica5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author raul
 */
public class Lienzo extends javax.swing.JPanel{
    
    //*************DECLARACION DE VARIABLES***************//
    
    boolean relleno;
    boolean mover;
    Line2D linea;
    tipos tipo = tipos.LINEA;
    Shape forma = new Line2D.Float(0,0,0,0); 
    ArrayList<Shape> vShape = new ArrayList<>(); 
    Color color = Color.BLACK;
    
    /**
     * Para movimiento de figuras
     */
    Point puntoInicial = new Point();
    Point puntoTmp;
    double anchura, altura;
    
    
    //************************CONSTRUCTOR**********************//
    
    public Lienzo() {
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
        g2d.setPaint(color);
        
        for(Shape forma:vShape){
            if(relleno){
                g2d.fill(forma);
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
            
            puntoInicial = evt.getPoint();
            vShape.add(forma);
        }
        
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
        }
        
        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
