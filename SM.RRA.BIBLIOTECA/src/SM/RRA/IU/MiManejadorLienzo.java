/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.IU;

import java.awt.Shape;

/**
 *
 * @author raul
 */
public class MiManejadorLienzo extends LienzoAdapter {
      
        public void shapeAdded(LienzoEvent evt) {
            String forma = evt.getForma().toString();
            System.out.println("Figura " + forma + " añadida");
        }
    
}
