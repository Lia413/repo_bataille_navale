package test_projet;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

// Fenetre

public class Fenetre {
    private final Stage stage;
    private Button[][] tab_boutons;
    private final int taille;
    private String[] liste_bateaux;
    
    public Fenetre(Stage st, int taille, String[] liste_bateaux) {
        this.stage = st;
        this.taille = taille;
        this.liste_bateaux = liste_bateaux;
        stage.setTitle("Bataille Navale");
    }
    
    // Écran titre pendant 5 secondes
    public void afficherEcranTitre() {
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        StackPane root = new StackPane(titre);
        root.setStyle("-fx-background-color: #3498db;");
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> creerGrid());
        pause.play();
    }
    
    public HBox creerBateau() {
        HBox hb = new HBox(10); 
        hb.setAlignment(Pos.CENTER);
        hb.setStyle("-fx-padding: 20;");
        
        for (String bat : liste_bateaux) {

                Button btnBateau = new Button(bat);
                btnBateau.setPrefSize(100, 50);
                btnBateau.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-weight: bold;");
                hb.getChildren().add(btnBateau);
            }
        
        
        return hb;
    }
    
    public void creerGrid() {
        // Titre au-dessus
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // La grille
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        tab_boutons = new Button[taille][taille];
        
        for (int m = 0; m < taille; m++) {
            for (int n = 0; n < taille; n++) {
                Button btn = new Button();
                btn.setPrefSize(30, 30);  
                btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                tab_boutons[m][n] = btn;
                grid.add(btn, n, m);
            }
        }
        
        // Encadrer la grille
        StackPane cadre = new StackPane(grid);
        cadre.setStyle(
            "-fx-padding: 10;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 3;" +
            "-fx-border-style: solid;"
        );
        
        // Créer la HBox des bateaux
        HBox hboxBateaux = creerBateau();
        
        // Tout mettre dans une VBox
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #ecf0f1;");
        root.setAlignment(Pos.TOP_CENTER);
        
        // Ajouter : titre, cadre de la grille, puis HBox des bateaux
        root.getChildren().addAll(titre, cadre, hboxBateaux);
        
        // Afficher la scène
        Scene scene = new Scene(root, 800, 700); // Augmenté la hauteur pour la HBox
        stage.setScene(scene);
    }
    
    public void mettreBateau() {
        // À implémenter
    }
}