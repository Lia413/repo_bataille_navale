package jeu;

import java.util.Random;

public class TirMissiles {
    private Plateau plateau;
    private Random random;
    private boolean[][] dejaVise; 
    
    public TirMissiles(Plateau plateau) {
        this.plateau = plateau;
        this.random = new Random();
        this.dejaVise = new boolean[10][10];
        
        // Dire que tte les cases n'ont jamais été visées
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                dejaVise[x][y] = false;
            }
        }
    }
    
    public int[] tirerMissile() {
        int x, y;
        
        // Chercher une case pas déjà visée
        while (true) {
            x = random.nextInt(10); 
            y = random.nextInt(10);
            if (!dejaVise[x][y]) break; // sortir dès qu'on trouve une case pas déjà visée
        }
        
        // Visée
        dejaVise[x][y] = true;
        
        // Vérifier si la case est occupée par un bateau
        Cellule[][] tab = plateau.getTab();
        boolean touche = tab[x][y].getOccupe();
        
        if (touche) {
            tab[x][y].setTouche(true); // touché
        } else {
            tab[x][y].setRate(true); // raté (plouf)
        }
        
        int resultat;
        if (touche) {
            resultat = 1;
        } else {
            resultat = 0;
        }
        return new int[]{x, y, resultat};
    }
    
    public boolean tirerMissile(int x, int y) {
        // Vérifier si la case est valide
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {/*|| c'est un ou*/
            return false;
        }
        
        // Vérifier si déjà visée
        if (dejaVise[x][y]) {
            return false;
        }
        
        // Marquer la case comme visée
        dejaVise[x][y] = true;
        
        // Vérifier si la case est occupée par un bateau
        Cellule[][] tab = plateau.getTab();
        boolean touche = tab[x][y].getOccupe();
        
        if (touche) {
            tab[x][y].setTouche(true);
        } else {
            tab[x][y].setRate(true); // raté (plouf)
        }
        
        return touche;
    }
    
    /* Vérifie si ttes les cases ont été visées */
    public boolean toutesLesCasesVisees() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!dejaVise[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /* Compte le nombre de tirs effectués */
    public int nombreDeTirs() {
        int cpt = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (dejaVise[x][y]) {
                    cpt++;
                }
            }
        }
        return cpt;
    }
    
    /* Réinitialise tous les tirs */
    public void reinitialiser() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                dejaVise[x][y] = false;
            }
        }
    }
    
    /* Vérifie si une case a déjà été visée */
    public boolean dejaVise(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) { /*|| c'est un ou*/
            return true; // Déjà visée
        }
        return dejaVise[x][y];
    }
}