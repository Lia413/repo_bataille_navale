package jeu;

import java.awt.Dimension;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
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
import javafx.scene.paint.Color;
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
    private Plateau plateau1;
    private Plateau plateau_adv;
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

    // Création plateau Canvas et HBox bateaux
    private void afficherGridEtBateaux() {
        // Création du plateau
        plateau1 = new Plateau(400, 400);
        
        // Placement automatique des bateaux
        phase1 placement = new phase1(liste_Bat, plateau1);
        placement.placerBat();
        
        // Dessiner le plateau
        Label l1 = new Label("Mon plateau");
        GraphicsContext gc1 = plateau1.getGraphicsContext2D();
        plateau1.paint(gc1);
        
        // Bouton Reload pour replacer les bateaux
        Button btn_reload = new Button("⟳ Replacer");
        btn_reload.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 5;"
        );
        btn_reload.setOnAction(e -> {
            // Réinitialiser toutes les cellules
            Cellule[][] tab = plateau1.getTab();
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    tab[x][y].setOccupe(false);
                    tab[x][y].setTouche(false);
                    tab[x][y].setRate(false);
                }
            }
            
            // Replacer les bateaux
            placement.placerBat();
            
            // Redessiner le plateau
            plateau1.paint(gc1);
        });
        
        // Effet hover pour le bouton reload
        btn_reload.setOnMouseEntered(e -> 
            btn_reload.setStyle(
                "-fx-background-color: #c0392b;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-background-radius: 5;"
            )
        );
        btn_reload.setOnMouseExited(e -> 
            btn_reload.setStyle(
                "-fx-background-color: #e74c3c;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-background-radius: 5;"
            )
        );
        
        // Bouton Valider
        Button btn_valider = new Button("✓ Valider");
        btn_valider.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 5;"
        );
        btn_valider.setOnAction(e -> afficher2GridEtBateaux());
        
        // Effet hover pour le bouton valider
        btn_valider.setOnMouseEntered(e -> 
            btn_valider.setStyle(
                "-fx-background-color: #229954;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-background-radius: 5;"
            )
        );
        btn_valider.setOnMouseExited(e -> 
            btn_valider.setStyle(
                "-fx-background-color: #27ae60;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-background-radius: 5;"
            )
        );
        
        // HBox pour les boutons
        HBox boutons = new HBox(15, btn_reload, btn_valider);
        boutons.setAlignment(Pos.CENTER);
        
        // Cadre autour du plateau
        VBox cadre = new VBox(5, l1, plateau1);
        cadre.setAlignment(Pos.CENTER);
        cadre.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 3; -fx-border-style: solid;");
        
        // Conteneur principal
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Titre
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // Info
        Label info = new Label("Cliquez sur 'Replacer' pour changer la position des bateaux");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        
        // Ajout des éléments
        root.getChildren().addAll(titre, info, cadre, boutons);
        
        Scene scene = new Scene(root, 800, 700);
        stage.setScene(scene);
    }

    private void afficher2GridEtBateaux() {
        // Dessiner le plateau adversaire
        Label l2 = new Label("Plateau adversaire");
        plateau_adv = new Plateau(400, 400);
        
        // Placer les bateaux adversaires automatiquement
        phase1 placementAdv = new phase1(liste_Bat, plateau_adv);
        placementAdv.placerBat();
        
        GraphicsContext gc_adv = plateau_adv.getGraphicsContext2D();
        plateau_adv.paint(gc_adv, false); // MASQUER les bateaux adverses
        
        // Dessiner mon plateau
        Label l1 = new Label("Mon plateau");
        GraphicsContext gc1 = plateau1.getGraphicsContext2D();
        plateau1.paint(gc1);
        
        // Créer les TirMissiles pour les deux plateaux
        TirMissiles tirAdversaire = new TirMissiles(plateau_adv); // Je tire sur l'adversaire
        TirMissiles tirSurMoi = new TirMissiles(plateau1); // L'adversaire tire sur moi
        
        // Variables pour l'animation de la cible
        final int[] cibleX = {0};
        final int[] cibleY = {0};
        final boolean[] animationEnCours = {false};
        final boolean[] monTour = {true}; // true = mon tour, false = tour adversaire
        
        // Label pour indiquer le tour
        Label labelTour = new Label("À votre tour ! Cliquez sur une case du plateau adverse");
        labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        // Gérer le clic sur le plateau adversaire
        plateau_adv.setOnMouseClicked(event -> {
            if (!animationEnCours[0] && monTour[0]) {
                // Calculer la case cliquée
                int caseWidth = (int)(plateau_adv.getWidth() / 10);
                int caseHeight = (int)(plateau_adv.getHeight() / 10);
                int x = (int)(event.getX() / caseWidth);
                int y = (int)(event.getY() / caseHeight);
                
                // Vérifier que la case est valide et non déjà visée
                if (x >= 0 && x < 10 && y >= 0 && y < 10 && !tirAdversaire.dejaVise(x, y)) {
                    animationEnCours[0] = true;
                    monTour[0] = false;
                    labelTour.setText("Tir en cours...");
                    labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");
                    
                    final int finalX = x;
                    final int finalY = y;
                    
                    // Montrer la cible sur la case choisie
                    plateau_adv.paint(gc_adv, false); // MASQUER les bateaux
                    dessinerCible(gc_adv, finalX, finalY);
                    
                    // Pause avant le tir
                    PauseTransition pause = new PauseTransition(Duration.millis(800));
                    pause.setOnFinished(ev -> {
                        boolean touche = tirAdversaire.tirerMissile(finalX, finalY);
                        
                        if (touche) {
                            System.out.println("Vous avez touché en [" + finalX + ", " + finalY + "] !");
                        } else {
                            System.out.println("Vous avez raté en [" + finalX + ", " + finalY + "]");
                        }
                        
                        plateau_adv.paint(gc_adv, false); // MASQUER les bateaux
                        
                        // Passer au tour de l'adversaire
                        labelTour.setText("⏳ Tour de l'adversaire...");
                        labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                        
                        // L'adversaire tire après 1 seconde
                        PauseTransition tourAdv = new PauseTransition(Duration.seconds(1));
                        tourAdv.setOnFinished(ev2 -> {
                            tirAdversaire(tirSurMoi, gc1, labelTour, animationEnCours, monTour);
                        });
                        tourAdv.play();
                    });
                    pause.play();
                }
            }
        });
        
        // Bouton pour tirer un missile (maintenant optionnel - pour tir aléatoire)
        Button btn_tirer = new Button("Tir aléatoire");
        btn_tirer.setStyle(
            "-fx-background-color: #9b59b6;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-background-radius: 5;"
        );
        
        btn_tirer.setOnAction(e -> {
            if (!tirAdversaire.toutesLesCasesVisees() && !animationEnCours[0] && monTour[0]) {
                animationEnCours[0] = true;
                monTour[0] = false;
                
                // Trouver une case aléatoire non visée
                int targetX, targetY;
                do {
                    targetX = (int)(Math.random() * 10);
                    targetY = (int)(Math.random() * 10);
                } while (tirAdversaire.dejaVise(targetX, targetY));
                
                final int finalX = targetX;
                final int finalY = targetY;
                final int[] compteur = {0};
                
                labelTour.setText("Tir aléatoire");
                labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #9b59b6;");
                
                // Animation toutes les 100ms
                PauseTransition animation = new PauseTransition(Duration.millis(100));
                animation.setOnFinished(event -> {
                    // Déplacer la cible aléatoirement
                    cibleX[0] = (int)(Math.random() * 10);
                    cibleY[0] = (int)(Math.random() * 10);
                    
                    plateau_adv.paint(gc_adv, false); // MASQUER les bateaux
                    dessinerCible(gc_adv, cibleX[0], cibleY[0]);
                    
                    compteur[0]++;
                    
                    // Continuer l'animation 15 fois
                    if (compteur[0] < 15) {
                        animation.play();
                    } else {
                        // Arrêter sur la position finale
                        plateau_adv.paint(gc_adv, false); // MASQUER les bateaux
                        dessinerCible(gc_adv, finalX, finalY);
                        
                        // Pause puis montrer le résultat
                        PauseTransition pause = new PauseTransition(Duration.millis(500));
                        pause.setOnFinished(ev -> {
                            boolean touche = tirAdversaire.tirerMissile(finalX, finalY);
                            
                            if (touche) {
                                System.out.println("Vous avez touché en [" + finalX + ", " + finalY + "] !");
                            } else {
                                System.out.println("Vous avez raté en [" + finalX + ", " + finalY + "]");
                            }
                            
                            plateau_adv.paint(gc_adv, false); // MASQUER les bateaux
                            
                            // Passer au tour de l'adversaire
                            labelTour.setText("⏳ Tour de l'adversaire...");
                            labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                            
                            // L'adversaire tire après 1 seconde
                            PauseTransition tourAdv = new PauseTransition(Duration.seconds(1));
                            tourAdv.setOnFinished(ev2 -> {
                                tirAdversaire(tirSurMoi, gc1, labelTour, animationEnCours, monTour);
                            });
                            tourAdv.play();
                        });
                        pause.play();
                    }
                });
                animation.play();
            } else if (tirAdversaire.toutesLesCasesVisees()) {
                System.out.println("Toutes les cases ont été visées !");
            }
        });
        
        // Effet hover pour le bouton
        btn_tirer.setOnMouseEntered(e -> {
            if (monTour[0] && !animationEnCours[0]) {
                btn_tirer.setStyle(
                    "-fx-background-color: #8e44ad;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 20 10 20;" +
                    "-fx-background-radius: 5;"
                );
            }
        });
        btn_tirer.setOnMouseExited(e -> {
            btn_tirer.setStyle(
                "-fx-background-color: #9b59b6;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-background-radius: 5;"
            );
        });
        
        // Organisation des plateaux côte à côte
        VBox plateauGauche = new VBox(5, l1, plateau1);
        plateauGauche.setAlignment(Pos.CENTER);
        
        VBox plateauDroit = new VBox(5, l2, plateau_adv);
        plateauDroit.setAlignment(Pos.CENTER);
        
        HBox cadre = new HBox(30, plateauGauche, plateauDroit);
        cadre.setAlignment(Pos.CENTER);
        cadre.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 3; -fx-border-style: solid;");
        
        // Conteneur principal
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Titre
        Label titre = new Label("BATAILLE NAVALE");
        titre.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // Ajout des éléments
        root.getChildren().addAll(titre, labelTour, cadre, btn_tirer);
        
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
    }
    
    // Méthode pour le tir de l'adversaire
    private void tirAdversaire(TirMissiles tirSurMoi, GraphicsContext gc1, Label labelTour, 
                               boolean[] animationEnCours, boolean[] monTour) {
        
        // Trouver une case aléatoire pas déjà visée
        int targetX, targetY;
        do {
            targetX = (int)(Math.random() * 10);
            targetY = (int)(Math.random() * 10);
        } while (tirSurMoi.dejaVise(targetX, targetY));
        
        final int finalX = targetX;
        final int finalY = targetY;
        final int[] compteur = {0};
        final int[] cibleX = {0};
        final int[] cibleY = {0};
        
        // Animation de la cible sur mon plateau
        PauseTransition animation = new PauseTransition(Duration.millis(100));
        animation.setOnFinished(event -> {
            cibleX[0] = (int)(Math.random() * 10);
            cibleY[0] = (int)(Math.random() * 10);
            
            plateau1.paint(gc1);
            dessinerCible(gc1, cibleX[0], cibleY[0]);
            
            compteur[0]++;
            
            if (compteur[0] < 15) {
                animation.play();
            } else {
                // Arrêter sur la position finale
                plateau1.paint(gc1);
                dessinerCible(gc1, finalX, finalY);
                
                // Pause puis montrer le résultat
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(ev -> {
                    boolean touche = tirSurMoi.tirerMissile(finalX, finalY);
                    
                    if (touche) {
                        System.out.println("L'adversaire vous a touché en [" + finalX + ", " + finalY + "] !");
                    } else {
                        System.out.println("L'adversaire a raté en [" + finalX + ", " + finalY + "]");
                    }
                    
                    plateau1.paint(gc1);
                    
                    // Mon tour
                    monTour[0] = true;
                    labelTour.setText("À votre tour ! Cliquez sur une case du plateau adverse");
                    labelTour.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
                    animationEnCours[0] = false;
                });
                pause.play();
            }
        });
        animation.play();
    }

    // Méthode pour dessiner la cible (sur n'importe quel plateau)
    private void dessinerCible(GraphicsContext gc, int x, int y) {
        Dimension ech = new Dimension();
        ech.width = 40; // Taille approximative d'une case
        ech.height = 40;
        
        double centreX = x * ech.width + ech.width / 2.0;
        double centreY = y * ech.height + ech.height / 2.0;
        
        // Cercle extérieur rouge
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeOval(centreX - 15, centreY - 15, 30, 30);
        
        // Cercle intérieur rouge
        gc.strokeOval(centreX - 8, centreY - 8, 16, 16);
        
        // Croix au centre
        gc.strokeLine(centreX - 20, centreY, centreX + 20, centreY);
        gc.strokeLine(centreX, centreY - 20, centreX, centreY + 20);
        
        // Point central
        gc.setFill(Color.RED);
        gc.fillOval(centreX - 3, centreY - 3, 6, 6);
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
        return plateau1;
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