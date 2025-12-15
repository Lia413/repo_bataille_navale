
package jeu;

import javafx.application.Application;
import javafx.stage.Stage;
import reseau.Reseau;

/**
 *
 * @author olivier
 */
public class Main extends Application{

    static Reseau reseau;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creation du réseau sous le thread principal
        reseau = new Reseau(2000);
        launch(args);
    }

    @Override
    public void start(Stage st) throws Exception {
        Jeu jeu = new Jeu();
        jeu.setReseau(reseau);
        Fenetre fen = new Fenetre(st, jeu);
        fen.afficherEcranTitre();
    }
    
}
