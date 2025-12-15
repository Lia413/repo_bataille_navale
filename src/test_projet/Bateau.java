package test_projet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Bateau {
    int taille;
    private int touchesRecues;
    protected BufferedImage imageComplete;
    protected BufferedImage[] morceaux;
    
    public Bateau(int taille) {
        this.taille=taille;
        this.touchesRecues = 0;
    }
    
    public int getTaille() {
        return taille;
    }
    
    public void toucher() {
        touchesRecues++;
    }
    
    public boolean estCoule() {
        return touchesRecues >= taille;
    }
    
    public int getTouchesRecues() {
        return touchesRecues;
    }
    
    public String orientation(){
        return "";}
    
    protected void decouperImageHorizontale(String cheminImage) throws IOException {
        // Charger l'image
        this.imageComplete = ImageIO.read(new File(cheminImage));
        
        // Calculer la largeur de chaque morceau
        int largeurMorceau = imageComplete.getWidth() / taille;
        int hauteurComplete = imageComplete.getHeight();
        
        // Tableau pour stocker les morceaux
        this.morceaux = new BufferedImage[taille];
        
        // Découper l'image de gauche à droite
        for (int i = 0; i < taille; i++) {
            morceaux[i] = imageComplete.getSubimage(
                i * largeurMorceau,  // position x qui avance
                0,                   // position y reste à 0
                largeurMorceau,      // largeur du morceau
                hauteurComplete      // hauteur complète de l'image
            );
        }
        
        System.out.println("Image découpée horizontalement en " + taille + " morceaux");
    }
    
    /**
     * Découpe l'image verticalement (de haut en bas)
     * @param cheminImage
     * @throws java.io.IOException
     */
    protected void decouperImageVerticale(String cheminImage) throws IOException {
        // Charger l'image
        this.imageComplete = ImageIO.read(new File(cheminImage));
        
        // Calculer la hauteur de chaque morceau
        int largeurComplete = imageComplete.getWidth();
        int hauteurMorceau = imageComplete.getHeight() / taille;
        
        // Tableau pour stocker les morceaux
        this.morceaux = new BufferedImage[taille];
        
        // Découper l'image de haut en bas
        for (int i = 0; i < taille; i++) {
            morceaux[i] = imageComplete.getSubimage(
                0,                   // position x reste à 0
                i * hauteurMorceau,  // position y qui descend
                largeurComplete,     // largeur complète de l'image
                hauteurMorceau       // hauteur du morceau
            );
        }
        
        System.out.println("Image découpée verticalement en " + taille + " morceaux");
    }
    
    // Récupérer un morceau spécifique
    public BufferedImage getMorceau(int index) {
        return morceaux[index];
    }
    
    // Récupérer tous les morceaux
    public BufferedImage[] getMorceaux() {
        return morceaux;
    }
    
}