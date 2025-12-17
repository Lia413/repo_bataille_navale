package jeu;

import java.awt.Dimension;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cellule {
    private boolean occupe;
    private boolean touche;
    
    public Cellule() {
        this.occupe = false;
        this.touche = false;
    }
    
    public void dessineToi(GraphicsContext gc, int x, int y, Dimension ech) {
        // Couleur de la cellule selon son état
        if (touche) {
            gc.setFill(Color.RED);
        } else if (occupe) {
            gc.setFill(Color.DARKGRAY);
        } else {
            gc.setFill(Color.LIGHTBLUE);
        }
        
        // Dessine le rectangle rempli
        gc.fillRect(x * ech.width, y * ech.height, ech.width, ech.height);
        
        // Dessine le contour noir
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x * ech.width, y * ech.height, ech.width, ech.height);
    }
    
    public boolean getOccupe() {
        return occupe;
    }
    
    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }
    
    public boolean getTouche() {
        return touche;
    }
    
    public void setTouche(boolean touche) {
        this.touche = touche;
    }
    
}