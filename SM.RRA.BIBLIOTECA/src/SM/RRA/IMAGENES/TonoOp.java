/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IMAGENES;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author raul
 */
public class TonoOp extends BufferedImageOpAdapter {

    private float tono;

    public TonoOp(float tono) {
        this.tono = tono;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }

        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();

        int[] srcPixels = new int[srcRaster.getNumBands()];
        int[] destPixels = new int[srcRaster.getNumBands()];

        float[] hsb = new float[3];

        for (int i = 0; i < src.getWidth(); ++i) {
            for (int j = 0; j < src.getHeight(); ++j) {
                //Convertimos de RGB a HSB
                srcRaster.getPixel(i, j, srcPixels);
                Color.RGBtoHSB(srcPixels[0], srcPixels[1], srcPixels[2], hsb);
                
                //Transformación a aplicar
                hsb[0] = (((hsb[0] * 360) + tono) % 360) / 360;
                
                //COnvertimos de nuevo a RGB (estabamos en HSB)
                int vueltaRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                destPixels[0] = (vueltaRGB >> 16) & 0xFF;
                destPixels[1] = (vueltaRGB >> 8) & 0xFF;
                destPixels[2] = vueltaRGB & 0xFF;
                
                destRaster.setPixel(i, j, destPixels);
            }
        }

        return dest;
    }

}
