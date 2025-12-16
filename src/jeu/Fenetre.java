package jeu;

import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Fenetre {
    private final Stage stage;

    // Connexion / chat
    private GridPane gpcnx;
    private TextField tfnom, tfipdist;
    private Button bserv, bcnx;
    private Label iploc, lnom, lmess;
    private TextField tftxt;

    // Plateau de jeu
    private Plateau plateau;
    private final int taille;
    
    private ArrayList<Bateau> liste_Bat;
    

    public Fenetre(Stage stage, int taille, ArrayList<Bateau> liste_Bat) {
        this.stage = stage;
        this.taille = taille;
        this.liste_Bat = liste_Bat;

        stage.setTitle("Bataille Navale");
        stage.setOnCloseRequest(e -> Platform.exit());

        afficherEcranTitre();

        // Pause avant d'afficher le bouton start
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> afficherBoutonStart());
        pause.play();
    }

    // Écran titre
    public void afficherEcranTitre() {
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane root = new StackPane(titre);
        root.setStyle("-fx-background-color: #3498db;");
        StackPane.setAlignment(titre, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Bouton start
    public void afficherBoutonStart() {
        Button bt_jeu = new Button("DEMARRER UNE PARTIE");
        bt_jeu.setStyle(
                "-fx-font-size: 36px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: black;" +
                "-fx-background-radius: 30;" +
                "-fx-padding: 10 20 10 20;"
        );

        bt_jeu.setOnAction(e -> afficherGridEtBateaux());

        StackPane root = new StackPane(bt_jeu);
        root.setStyle("-fx-background-color: #3498db;");
        StackPane.setAlignment(bt_jeu, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    // Écran connexion / pseudo
    public void afficherEcranConnexion() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #4A90E2, #1B4F72);");

        VBox card = new VBox(20);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(500);
        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.95);" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0, 0, 5);"
        );

        Label titre = new Label("Connexion");
        titre.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        gpcnx = new GridPane();
        gpcnx.setVgap(15);
        gpcnx.setHgap(15);
        gpcnx.setAlignment(Pos.CENTER);

        // Pseudo
        Label lpseudo = new Label("Pseudo :");
        tfnom = new TextField();
        tfnom.setPrefWidth(150);
        gpcnx.add(lpseudo, 0, 0);
        gpcnx.add(tfnom, 1, 0);

        // Mode serveur
        bserv = new Button("Mode serveur");
        bserv.setOnAction(e -> {
            if (lmess != null) lmess.setText("");
            String ps = tfnom.getText();
            if (ps.isBlank()) {
                lmess.setText("Définissez votre pseudo");
            } else if (interdit(ps)) {
                lmess.setText("Caractères autorisés : A-Z, a-z, 0-9, _");
            } else {
                afficherGridEtBateaux();
            }
        });
        gpcnx.add(bserv, 2, 0);

        // IP locale
        Label lip = new Label("IP locale :");
        iploc = new Label("");
        gpcnx.add(lip, 0, 1);
        gpcnx.add(iploc, 1, 1);

        // Connexion distante
        Label lipdist = new Label("IP serveur :");
        tfipdist = new TextField();
        tfipdist.setPrefWidth(150);
        gpcnx.add(lipdist, 0, 2);
        gpcnx.add(tfipdist, 1, 2);

        bcnx = new Button("Connexion");
        bcnx.setOnAction(e -> {
            String ps = tfnom.getText();
            String ip = tfipdist.getText();
            if (ps.isBlank()) {
                lmess.setText("Définissez votre pseudo");
            } else if (ip.isBlank()) {
                lmess.setText("Indiquez l'adresse IP du serveur");
            } else if (interdit(ps)) {
                lmess.setText("Caractères autorisés : A-Z, a-z, 0-9, _");
            } else {
                afficherGridEtBateaux();
            }
        });
        gpcnx.add(bcnx, 2, 2);

        // Nom distant
        Label ldistant = new Label("Distant :");
        lnom = new Label("—");
        gpcnx.add(ldistant, 0, 3);
        gpcnx.add(lnom, 1, 3);

        // Message
        lmess = new Label();
        lmess.setStyle("-fx-text-fill: red;");

        card.getChildren().addAll(titre, gpcnx, lmess);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    // Création HBox bateaux
    private HBox creerBateau() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(20));
        for (Bateau bat : liste_Bat) {
            Button btnBateau = new Button(bat.getnom());
            btnBateau.setPrefSize(120, 50);
            btnBateau.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;");
            
            // Effet hover
            btnBateau.setOnMouseEntered(e -> 
                btnBateau.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;")
            );
            btnBateau.setOnMouseExited(e -> 
                btnBateau.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px;")
            );
            
            hb.getChildren().add(btnBateau);
        }
        return hb;
    }

    // Création plateau Canvas et HBox bateaux
    private void afficherGridEtBateaux() {
        // Création du plateau Canvas
        plateau = new Plateau(400, 400);
        
        // Dessiner le plateau
        GraphicsContext gc = plateau.getGraphicsContext2D();
        plateau.paint(gc);
        
        // Cadre autour du plateau
        StackPane cadre = new StackPane(plateau);
        cadre.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 3; -fx-border-style: solid;");

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #ecf0f1;");

        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        root.getChildren().addAll(titre, cadre, creerBateau());

        Scene scene = new Scene(root, 800, 700);
        stage.setScene(scene);
    }

    private boolean interdit(String txt) {
        for (char c : txt.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') ||
                  (c >= 'a' && c <= 'z') ||
                  (c >= '0' && c <= '9') ||
                  c == '_')) {
                return true;
            }
        }
        return false;
    }
    
    // Getters
    public Plateau getPlateau() {
        return plateau;
    }
    
    public TextField getTfnom() {
        return tfnom;
    }
    
    public Label getLnom() {
        return lnom;
    }
    
    public Label getIploc() {
        return iploc;
    }
}