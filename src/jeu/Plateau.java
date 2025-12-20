package jeu;

import java.awt.Dimension;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plateau extends Canvas {
    Cellule[][] tab = new Cellule[10][10];
    Dimension ech = new Dimension();
    private Random random = new Random();
    
    public Plateau(int largeur, int hauteur) {
        super(largeur, hauteur);
        // Créer toutes les cellules
        for (int x = 0; x < tab.length; x++) {
            for (int y = 0; y < tab[x].length; y++) {
                tab[x][y] = new Cellule();
            }
        }
    }
    
    public void calculeEchelle() {
        ech.width = (int)(getWidth() / tab.length);
        ech.height = (int)(getHeight() / tab[0].length);
    }
    
    // Dessine le plateau (affiche les bateaux)
    public void paint(GraphicsContext gc) {
        paint(gc, true);
    }
    
    // Dessine le plateau ou pas
    public void paint(GraphicsContext gc, boolean afficherBateaux) {
        calculeEchelle();
        gc.setFill(Color.CYAN);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        // Dessine les cellules
        for (int x = 0; x < tab.length; x++) {
            for (int y = 0; y < tab[x].length; y++) {
                tab[x][y].dessineToi(gc, x, y, ech, afficherBateaux);
            }
        }
    }
    
    // Retourne une cellule aléatoire
    public Cellule getCelluleAleatoire() {
        int x = random.nextInt(tab.length);
        int y = random.nextInt(tab[0].length);
        return tab[x][y];
    }
    
    // Retourne les coordonnées d'une cellule aléatoire
    public int[] getCoordAleatoires() {
        int x = random.nextInt(tab.length);
        int y = random.nextInt(tab[0].length);
        return new int[]{x, y};
    }
    
    // Retourne une cellule aléatoire non occupée
    public Cellule getCelluleAleatoireLibre() {
        int x, y;
        do {
            x = random.nextInt(tab.length);
            y = random.nextInt(tab[0].length);
        } while (tab[x][y].getOccupe());
        
        return tab[x][y];
    }
    
    // Retourne les coordonnées d'une cellule aléatoire non occupée
    public int[] getCoordAleatoiresLibres() {
        int x, y;
        do {
            x = random.nextInt(tab.length);
            y = random.nextInt(tab[0].length);
        } while (tab[x][y].getOccupe());
        
        return new int[]{x, y};
    }
    
    // Getter pour accéder au tableau
    public Cellule[][] getTab() {
        return tab;
    }
    
    // Récupérer une cellule spécifique
    public Cellule getCellule(int x, int y) {
        if (x >= 0 && x < tab.length && y >= 0 && y < tab[0].length) {
            return tab[x][y];
        }
        return null;
    }
}