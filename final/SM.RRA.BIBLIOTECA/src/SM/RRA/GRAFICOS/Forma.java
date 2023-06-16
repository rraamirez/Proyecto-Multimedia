/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.GRAFICOS;

import SM.RRA.IU.tipos;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author raule
 */
public abstract class Forma {
    /////INICIALIZACIÓN DE ATRIBUTOS///////////
    /**
     * Relleno: Considera si hacer fill a nuestra figura
     * Mover: Considera si nuestra operación consiste en dibujar una figura o moverla.
     * Transparente: Considera si aplicar un filtro de transparencia
     * Liso: Considera si aplicar un filtro de antialiasing
     * Grosor: Considera el grosor al dibujar
     */
    boolean relleno = false;
    boolean mover = false;
    boolean transparente = false;
    boolean liso = false;
    int grosor = 5;
    int discontinuidad = 0;
    
    /**
     * Color al dibujar (draw).
     * Relleno (fill).
     * Stroke (grosor, discontinuidad).
     * Alphacomposite (transparencia).
     */
    Color color;
    Color colorRelleno;
    Stroke stroke;
    AlphaComposite alphaComposite;
    
    
    
    //////////GETTERS Y SETTERS///////////////////

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

    public boolean isTransparente() {
        return transparente;
    }

    public void setTransparente(boolean transparente) {
        this.transparente = transparente;
    }

    public boolean isLiso() {
        return liso;
    }

    public void setLiso(boolean liso) {
        this.liso = liso;
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

    public Color getColorRelleno() {
        return colorRelleno;
    }

    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
    }
    

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
    
    
    //******MÉTODOS ABSTRACTOS*//////////////////////////
    
    public abstract Shape figura();
    
    /**
     * Este método que se utiliza para pintar nuestra figura de tipo shape en un objeto Graphics. Aplica
     * las propiedades configuradas en el trazo, como el color, la
     * transparencia, la suavidad, el grosor y la discontinuidad, y luego dibuja el trazo.
     * 
     * Tener en cuenta que en función al tipo de forma habrá más o menos atributos de dibujo.
     *
     * @param g
     */
    public abstract void pintar(Graphics g);
    
    /**
     * Verifica si un punto específico está en nuestra forma.
     * @param p punto a verificar.
     * @return true si está contenida.
     */
    public abstract boolean contains(Point2D p);
    
    
        
}
