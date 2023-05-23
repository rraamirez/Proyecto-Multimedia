/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IMAGENES;

import java.awt.Color;
import java.awt.image.BufferedImage;
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

        int ancho = src.getWidth();
        int alto = src.getHeight();

        int[] srcPixels = src.getRGB(0, 0, ancho, alto, null, 0, ancho);
        int[] destPixels = new int[srcPixels.length];

        float[] hsb = new float[3];
        float phi = tono / 360.0f;

        for (int i = 0; i < srcPixels.length; i++) {
            int srcPixel = srcPixels[i];
            int r = (srcPixel >> 16) & 0xFF;
            int g = (srcPixel >> 8) & 0xFF;
            int b = srcPixel & 0xFF;

            Color.RGBtoHSB(r, g, b, hsb);

            float hue = (hsb[0] * 360 + tono) % 360;
            hsb[0] = hue / 360.0f;

            int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
            destPixels[i] = (srcPixel & 0xFF000000) | (rgb & 0x00FFFFFF);
        }

        dest.setRGB(0, 0, ancho, alto, destPixels, 0, ancho);
        return dest;
    }

}
