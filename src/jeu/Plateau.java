package jeu;

import java.awt.Dimension;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Plateau extends Canvas {
    Cellule[][] tab = new Cellule[10][10];
    Dimension ech = new Dimension();
    private Random random = new Random(); // Instanciation de Random
    
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
        // largeur 1 case = largeur zone de dessin / largeur de la grille
        ech.width = (int)(getWidth() / tab.length);
        // hauteur 1 case = hauteur zone de dessin / hauteur de la grille
        ech.height = (int)(getHeight() / tab[0].length);
    }
    
    public void paint(GraphicsContext gc) {
        calculeEchelle();
        // Met la couleur
        gc.setFill(Color.CYAN);
        gc.fillRect(0, 0, getWidth(), getHeight());
        // Dessine les cellules
        for (int x = 0; x < tab.length; x++) {
            for (int y = 0; y < tab[x].length; y++) {
                tab[x][y].dessineToi(gc, x, y, ech);
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