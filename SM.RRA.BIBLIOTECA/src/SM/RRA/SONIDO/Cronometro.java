/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SM.RRA.SONIDO;

/**
 *
 * @author raul
 */
public class Cronometro {

    private long startTime;
    private long pausedTime;
    private boolean isRunning;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getPausedTime() {
        return pausedTime;
    }

    public void setPausedTime(long pausedTime) {
        this.pausedTime = pausedTime;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    
    
    public void play() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }
    
    

    public void pause() {
        if (isRunning) {
            pausedTime = System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    public void resume() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - pausedTime;
            isRunning = true;
        }
    }

    public String actualizarTiempo() {
        if (isRunning) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long segundos = elapsedTime / 1000;
            long minutos = segundos / 60;

            segundos %= 60;

            String tiempo = String.format("%02d:%02d", minutos, segundos);
            return tiempo;
        }

        return "00:00";
    }

    public void stop() {
        isRunning = false;
    }
}
