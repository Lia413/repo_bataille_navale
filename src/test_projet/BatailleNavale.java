package test_projet;

import javafx.application.Application;
import javafx.stage.Stage;

public class BatailleNavale extends Application {
    
    @Override
    public void start(Stage primaryStage) {
String[] liste_bateaux = {"Croiseur", "Sous-marin", "Torpilleur","Cuirasse"};
        Fenetre fenetre = new Fenetre(primaryStage,20,liste_bateaux);
        fenetre.afficherEcranTitre();
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}