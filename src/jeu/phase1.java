package jeu;

import java.util.ArrayList;
import java.util.Random;

public class phase1 {
    ArrayList<Bateau> liste_Bat;
    Plateau plateau;
    Random random;
    
    public phase1(ArrayList<Bateau> liste_Bat, Plateau plateau) {
        this.liste_Bat = liste_Bat;
        this.plateau = plateau;
        this.random = new Random();
    }
    
    public void placerBat() {
        for (Bateau bateau : liste_Bat) {
            boolean place = false;
            
            // Essayer jusqu'à ce que le bateau soit placé
            while (!place) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                boolean horizontal = random.nextBoolean(); 
                
                if (peutPlacerBat(x, y, bateau.getTaille(), horizontal)) {
                    placerBat_plateau(x, y, bateau.getTaille(), horizontal);
                    place = true;
                }
            }
        }
    }
    
    private boolean peutPlacerBat(int x, int y, int taille, boolean horizontal) {
        Cellule[][] tab = plateau.getTab();
        
        // Vérifier que le bateau ne dépasse pas le plateau
        if (horizontal && x + taille > 10) return false;
        if (!horizontal && y + taille > 10) return false;
        
        // Vérifier que toutes les cases du bateau et les cases autour sont libres
        for (int i = 0; i < taille; i++) {
            int X_bat;
            int Y_bat;
            if (horizontal) {
                X_bat = x + i;
                Y_bat = y;
            } else {
                X_bat = x;
                Y_bat = y + i;
            }
            
            // Vérifier la case du bateau
            if (tab[X_bat][Y_bat].getOccupe()) {
                return false;
            }
            
            // Vérifier les cases autour du bateau, si c'est occuper on peut pas écrire notre bateau donc on va devoir sortir de la fontion
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int verifX = X_bat + dx;
                    int verifY = Y_bat + dy;
                    
                    // Vérifier que la case est dans les limites du plateau
                    if (verifX >= 0 && verifX < 10 && verifY >= 0 && verifY < 10) {
                        if (tab[verifX][verifY].getOccupe()) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    private void placerBat_plateau(int x, int y, int taille, boolean horizontal) {
        Cellule[][] tab = plateau.getTab();
        
        for (int i = 0; i < taille; i++) {
            int newX;
            int newY;
            if (horizontal) {
                newX = x + i;
                newY = y;
            } else {
                newX = x;
                newY = y + i;
            }
            tab[newX][newY].setOccupe(true);
        }
    }
}