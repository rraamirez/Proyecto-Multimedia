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
public class AzulOp extends BufferedImageOpAdapter {

    private int umbral;

    /**
     * Constructor
     *
     * @param umbral
     */
    public AzulOp(int umbral) {
        this.umbral = umbral;
    }

    /**
     * Método filter para destacar color azul sobre los demás.
     *
     * @param src img entrada
     * @param dest img destino
     * @return img destino.
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
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];
        int sumaBandas;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                sumaBandas = pixelComp[2];
                for (int i = 0; i < pixelComp.length; ++i) {
                    if (i != 2) {
                        sumaBandas -= pixelComp[i];
                    }
                }

                if (sumaBandas < umbral) {
                    for (int i = 0; i < pixelComp.length; ++i) {
                        pixelCompDest[i] = (int) ((pixelComp[0] + pixelComp[1] + pixelComp[2]) / 3);
                    }
                } else {
                    pixelCompDest = pixelComp;
                }
                destRaster.setPixel(x, y, pixelCompDest);
            }
        }
        return dest;
    }
}
