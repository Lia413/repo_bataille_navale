package jeu;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        int taille = 10;
        ArrayList<Bateau> liste_Bat =new ArrayList();
        
        Porte_avions porte_a = new Porte_avions();
        Croiseurs croiseur = new Croiseurs();
        Contre_torpilleurs c_torp = new Contre_torpilleurs();
        Sous_marin sous_m = new Sous_marin();
        Torpilleur torp = new Torpilleur();
        
        // Ajout des bateaux à la liste
        liste_Bat.add(porte_a);
        liste_Bat.add(croiseur);
        liste_Bat.add(c_torp);
        liste_Bat.add(c_torp);
        /*liste_Bat.add(sous_m);*/
        liste_Bat.add(torp);
        
        // Création de la fenêtre principale
        new Fenetre(stage, taille, liste_Bat);
        
        // Affichage de la fenêtre (déjà fait dans Fenetre.afficherEcranTitre())
        stage.show();
    }
}