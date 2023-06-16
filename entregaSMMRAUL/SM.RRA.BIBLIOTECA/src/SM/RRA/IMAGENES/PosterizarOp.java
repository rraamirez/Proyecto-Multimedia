/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IMAGENES;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author raul
 */
public class PosterizarOp extends BufferedImageOpAdapter {

    private int niveles;

    /**
     * Constructor
     * @param niveles 
     */
    public PosterizarOp(int niveles) {
        this.niveles = niveles;
    }
    
    /**
     * Método que "filtra" la imagen. Para posterizado.
     * @param src imagen entrada
     * @param dest imagen salida
     * @return img destino
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int sample;
        float k = 256.0f / niveles;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    
                    sample = (int) (k * (int)(sample / k));
                    
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
