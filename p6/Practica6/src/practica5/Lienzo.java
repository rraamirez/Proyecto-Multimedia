package practica5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
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
    //ArrayList<Shape> vShapeR = new ArrayList<>();
    Color color = Color.BLACK;
    
    Ellipse2D clipArea = new Ellipse2D.Float(100,100,500,500);
    /**
     * Crearemos punto puntoInicial que será necesario para poder dibujar 
     * rectángulos y elipses
     */
    
    /**
     * Para movimiento de figuras
     */
    Point puntoInicial = new Point();
    Point puntoTmp;
    int anchura, altura;
    
    
    //************************CONSTRUCTOR**********************//
    
    public Lienzo() {
        initComponents();
        
    }

    
    //*************************GETTERS AND SETTERS*************************//
    
    public tipos getTipo() {
        return this.tipo;
    }

    public void setTipo(tipos tipo) {
        this.tipo = tipo;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRelleno() {
        return this.relleno;
    }

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    public boolean isMover() {
        return this.mover;
    }

    public void setMover(boolean mover) {
        this.mover = mover;
    }

    public Line2D getLinea() {
        return this.linea;
    }

    public void setLinea(Line2D linea) {
        this.linea = linea;
    }

    public Shape getForma() {
        return this.forma;
    }

    public void setForma(Shape forma) {
        this.forma = forma;
    }

    public Point getInicial() {
        return this.puntoInicial;
    }

    public void setInicial(Point inicial) {
        this.puntoInicial = inicial;
    }

    public ArrayList<Shape> getvShape() {
        return this.vShape;
    }

    public void setvShape(ArrayList<Shape> vShape) {
        this.vShape = vShape;
    }

    /*public ArrayList<Shape> getvShapeR() {
        return vShapeR;
    }

    public void setvShapeR(ArrayList<Shape> vShapeR) {
        this.vShapeR = vShapeR;
    }*/

    public Point getPuntoInicial() {
        return this.puntoInicial;
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
        this.pruebaAtributos(g2d);
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
        //vShapeR.clear();
        this.repaint();
    }
    
    private void pruebaAtributos(Graphics2D g2d){
        //Trazo
        Stroke trazo;
        float[] patronDiscontinuidad = {15.0f, 15.0f};
        // TODO: Código para crear trazo
        trazo = new BasicStroke(10.0f,
        BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_MITER, 1.0f,
        patronDiscontinuidad, 0.0f);
        g2d.setStroke(trazo);
        g2d.draw(new Line2D.Float(40,40,160,160));
        //Relleno
        Paint relleno;
        relleno = new Color(255, 100, 0);
        g2d.setPaint(relleno);
        g2d.draw(new Rectangle(170,40,120,120));
        g2d.fill(new Rectangle(300,40,120,120));
        Point pc1 = new Point(430,40), pc2 = new Point(550,160);
        relleno = new GradientPaint(pc1, Color.RED, pc2, Color.BLUE);
        g2d.setPaint(relleno);
        g2d.fill(new Rectangle(430,40,120,120));
        //Composición
        Composite composicion;
        // TODO: Código para crear composición
        //g2d.setComposite(composicion);
        //Transformación
        AffineTransform transformacion;
        // TODO: Código para crear transformación
        //g2d.setTransform(transformacion);
        //Fuente
        Font fuente;
        // TODO: Código para crear fuente
        //g2d.setFont(fuente);
        //Renderización
        RenderingHints render;
        // TODO: Código para crear renderizado
        //g2d.setRenderingHints(render);
        //Recorte
        Shape clip;
        // TODO: Código para crear clip
        //g2d.setClip(clip);
    }
    
    
    /**
     * Este metodo devuelve la figura seleccionada dado un punto
     * Hay que mejorarlo para que coja el ultimo
     */
    
     private Shape getFiguraSeleccionada(Point2D p){
        for(Shape s:vShape){
            if(s instanceof Line2D)
                if(((Line2D)s).ptLineDist(p) <= 2.0) 
                    return s;
        
            if(s.contains(p)) return s;
        }
            
        return null;
    }
    
    /*public boolean isNear(Point2D p){
        // Caso p1=p2 (punto)
        if(this.getP1().equals(this.getP2())) return this.getP1().distance(p)<=2.0;
        // Caso p1!=p2
        return this.ptLineDist(p)<=2.0;
    }
    
    @Override
    public boolean contains(Line2D p) {
        return isNear(p);
    }*/
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();

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

        jCheckBox1.setText("jCheckBox1");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 299, Short.MAX_VALUE)
                .addComponent(jCheckBox1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        
        if(mover){
            forma = getFiguraSeleccionada(evt.getPoint());
        }
        
        if(!mover){
            if(tipo == tipos.LINEA){
                this.forma = new Line2D.Float(evt.getPoint(), evt.getPoint());
            }

            if(tipo == tipos.ELIPSE){
                this.forma = new Ellipse2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
                this.puntoInicial = evt.getPoint();
            }

            if(tipo == tipos.RECTANGULO){
                this.forma = new Rectangle2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
                this.puntoInicial = evt.getPoint();     
            }    

            vShape.add(forma);
        }
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if(mover){
            if (forma!=null && forma instanceof Line2D){
                this.puntoTmp = new Point(evt.getPoint().x + anchura, evt.getPoint().y + altura);
                ((Line2D)forma).setLine(evt.getPoint(), puntoTmp);
            }
            
            if (forma!=null && forma instanceof Rectangle2D){
                ((Rectangle2D)forma).setRect(evt.getPoint().x-anchura/2, 
                        evt.getPoint().y-altura/2, anchura, altura);
            }
            
            if (forma!=null && forma instanceof Ellipse2D){
                Rectangle2D rect_elipse = new Rectangle2D.Float(evt.getPoint().x-anchura/2,
                        evt.getPoint().y-altura/2, anchura, altura);
                
                ((Ellipse2D)forma).setFrame(rect_elipse);
            } 
            
        }
        
        if(!mover){
            if(tipo == tipos.LINEA){
                ((Line2D)forma).setLine(((Line2D)forma).getP1(), evt.getPoint());
                this.anchura = evt.getX() - puntoInicial.x;
                this.altura = evt.getY() - puntoInicial.y;
            }

            if(tipo == tipos.ELIPSE){
                ((Ellipse2D)forma).setFrameFromDiagonal(puntoInicial, evt.getPoint()); 
                this.anchura = abs(-evt.getX() + puntoInicial.x);
                this.altura = abs(-evt.getY() + puntoInicial.y);
            }

            if(tipo == tipos.RECTANGULO){
                ((Rectangle2D)forma).setFrameFromDiagonal(puntoInicial, evt.getPoint());  
                this.anchura = abs(-evt.getX() + puntoInicial.x);
                this.altura = abs(-evt.getY() + puntoInicial.y);
            }
        }
        
        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
        g2d.setClip(clipArea);
        repaint();
}
    }//GEN-LAST:event_jCheckBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    // End of variables declaration//GEN-END:variables
}
