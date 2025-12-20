package jeu;

import java.awt.Dimension;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cellule {
    private boolean occupe;
    private boolean touche;
    private boolean rate;
    
    public Cellule() {
        this.occupe = false;
        this.touche = false;
        this.rate = false;
    }
    
    // Dessine la cellule (affiche les bateaux)
    public void dessineToi(GraphicsContext gc, int x, int y, Dimension ech) {
        dessineToi(gc, x, y, ech, true);
    }
    
    // Dessine la cellule avec option de masquer les bateaux
    public void dessineToi(GraphicsContext gc, int x, int y, Dimension ech, boolean afficherBateaux) {
        // Couleur de la cellule selon son état
        if (touche) {
            // Case touchée = rouge avec X
            gc.setFill(Color.RED);
            gc.fillRect(x * ech.width, y * ech.height, ech.width, ech.height);
            
            // Dessiner un X noir
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3);
            gc.strokeLine(x * ech.width + 5, y * ech.height + 5, 
                         (x + 1) * ech.width - 5, (y + 1) * ech.height - 5);
            gc.strokeLine((x + 1) * ech.width - 5, y * ech.height + 5, 
                         x * ech.width + 5, (y + 1) * ech.height - 5);
        } else if (rate) {
            // Case ratée = bleu avec cercle blanc (plouf)
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(x * ech.width, y * ech.height, ech.width, ech.height);
            
            // Dessiner un cercle blanc pour le plouf
            gc.setFill(Color.WHITE);
            double centreX = x * ech.width + ech.width / 2.0;
            double centreY = y * ech.height + ech.height / 2.0;
            double rayon = Math.min(ech.width, ech.height) / 3.0;
            gc.fillOval(centreX - rayon, centreY - rayon, rayon * 2, rayon * 2);
            
            // Contour bleu foncé du plouf
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(2);
            gc.strokeOval(centreX - rayon, centreY - rayon, rayon * 2, rayon * 2);
        } else if (occupe && afficherBateaux) {
            // Afficher le bateau seulement si afficherBateaux = true
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(x * ech.width, y * ech.height, ech.width, ech.height);
        } else {
            // Case vide (eau)
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(x * ech.width, y * ech.height, ech.width, ech.height);
        }
        
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
    
    public boolean getRate() {
        return rate;
    }
    
    public void setRate(boolean rate) {
        this.rate = rate;
    }
}