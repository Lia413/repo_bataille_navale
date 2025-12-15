package jeu;


import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Fenetre {
    Stage st;
    Jeu jeu;

    // zone de connexion
    GridPane gpcnx;
    TextField tfnom;
    Button bserv;
    Label iploc;
    Button bcnx;
    TextField tfipdist;
    Label lnom;     
    Label lmess;

    public Fenetre(Stage st, Jeu jeu) {
        this.st = st;
        this.jeu = jeu;
        jeu.setFenetre(this);

        st.setTitle("Jeu");
        st.setOnCloseRequest(eh -> Platform.exit());

        afficherEcranTitre();

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> afficherBoutonStart());
        pause.play();
       

        st.show();
    }


    // ÉCRAN TITRE
  
    public void afficherEcranTitre() {
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane root = new StackPane(titre);
        root.setStyle("-fx-background-color: #3498db;");
        StackPane.setAlignment(titre, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        st.setScene(scene);
    }

    // ÉCRAN CONNEXION

    public void afficherEcranConnexion() {

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#4A90E2, #1B4F72);");

        VBox card = new VBox();
        card.setSpacing(20);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.95);" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0, 0, 5);"
        );

        Label titre = new Label("Connexion");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        gpcnx = new GridPane();
        gpcnx.setVgap(15);
        gpcnx.setHgap(15);
        gpcnx.setAlignment(Pos.CENTER);

        // Pseudo
        Label lpseudo = new Label("Pseudo :");
        lpseudo.setFont(Font.font(16));
        tfnom = new TextField();
        tfnom.setPrefWidth(150);
        gpcnx.add(lpseudo, 0, 0);
        gpcnx.add(tfnom, 1, 0);

        //Mode serveur
        bserv = new Button("Mode serveur");
        bserv.setPrefWidth(140);
        bserv.setStyle("-fx-font-weight: bold;");
        bserv.setOnAction(eh -> {
            lmess.setText("");
            String ps = tfnom.getText();
            if (ps.isBlank()) lmess.setText("Définissez votre pseudo");
            else if (interdit(ps)) lmess.setText("Caractères autorisés : A-Z, a-z, 0-9, _");
            else {
                jeu.setPseudo(ps);
                jeu.lancerServeur();
            }
        });
        gpcnx.add(bserv, 2, 0);

        // IP locale
        Label lip = new Label("IP locale :");
        lip.setFont(Font.font(16));
        iploc = new Label("");
        gpcnx.add(lip, 0, 1);
        gpcnx.add(iploc, 1, 1);

        // Nom du client distant (manquant → cause crash)
        Label ldistant = new Label("Distant :");
        ldistant.setFont(Font.font(16));
        lnom = new Label("—");             // ← RECREÉ !
        lnom.setFont(Font.font(16));
        gpcnx.add(ldistant, 0, 2);
        gpcnx.add(lnom, 1, 2);

        // Connexion distante 
        Label lipdist = new Label("IP serveur :");
        lipdist.setFont(Font.font(16));
        tfipdist = new TextField();
        tfipdist.setPrefWidth(150);

        bcnx = new Button("Connexion");
        bcnx.setPrefWidth(140);
        bcnx.setStyle("-fx-font-weight: bold;");
        bcnx.setOnAction(eh -> {
            lmess.setText("");
            String ps = tfnom.getText();
            String ip = tfipdist.getText();

            if (ps.isBlank()) lmess.setText("Définissez votre pseudo");
            else if (ip.isBlank()) lmess.setText("Indiquez l’adresse IP du serveur");
            else if (interdit(ps)) lmess.setText("Caractères autorisés : A-Z, a-z, 0-9, _");
            else jeu.connexion(ip, ps);
        });

        gpcnx.add(lipdist, 0, 3);
        gpcnx.add(tfipdist, 1, 3);
        gpcnx.add(bcnx, 2, 3);

        // Messages
        lmess = new Label();
        lmess.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lmess.setStyle("-fx-text-fill: red;");

        card.getChildren().addAll(titre, gpcnx, lmess);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 800, 600);
        st.setScene(scene);
    }

   
    // MÉTHODES D’AIDE

    public void setIP(String ip) { iploc.setText(ip); }
    public void setMess(String mess) { lmess.setText(mess); }
    public void setNom(String pseudo) { lnom.setText(pseudo); }

    private boolean interdit(String txt) {
        for (char c : txt.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') ||
                  (c >= 'a' && c <= 'z') ||
                  (c >= '0' && c <= '9') ||
                  c == '_')) return true;
        }
        return false;
    }
    
 public void afficherBoutonStart(){
    Button bt_jeu = new Button("DEMARRER UNE PARTIE");
    bt_jeu.setStyle(
    "-fx-font-size: 48px;" +
    "-fx-font-weight: bold;" +
    "-fx-text-fill: black;" +
    "-fx-background-radius: 30;" +
    "-fx-padding: 10 20 10 20;"
    );

    bt_jeu.setOnAction(e -> afficherCanvaGrid());
    
    StackPane root = new StackPane(bt_jeu);
    root.setStyle("-fx-background-color: #3498db;");
    StackPane.setAlignment(bt_jeu, Pos.CENTER);

    // Ajouter au Stage
    Scene scene = new Scene(root, 800, 600);
    st.setScene(scene);  
}

    public void Creer_grid(Canvas cv){
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(Color.CYAN);
        gc.fillRect(0, 0, 400, 400);
        //Creer la grille
        for (int i=0;i<401;i+=40){
            gc.strokeLine(i, 0, i, 400);
        } for (int j=0;j<401;j+=40){
            gc.strokeLine(0,j,400,j);
        }
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
    }
    
    public void afficherCanvaGrid(){
        //Creer le canva
        Canvas cv = new Canvas(800,600);
        Creer_grid(cv);
        HBox root = new HBox(cv);
        root.setStyle("-fx-background-color: #3498db;");
        root.setMargin(cv, new Insets(200, 0, 0, 100));
        root.setPadding(new Insets(0));
        
        
       
        
        Scene scene = new Scene(root, 800, 600);
        st.setScene(scene);  
    }
    
    
}
