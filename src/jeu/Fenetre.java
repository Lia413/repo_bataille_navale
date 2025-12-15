
package jeu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author olivier
 */
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
    TextField tftxt;
    Label lmess;
    
      
    public Fenetre(Stage st, Jeu jeu){
        this.st=st;
        this.jeu=jeu;
        jeu.setFenetre(this);
        
        st.setTitle("Jeu");
        st.setOnCloseRequest(eh -> Platform.exit());
        VBox root = new VBox();
        root.setSpacing(10);
        Scene scene = new Scene(root);
        st.setScene(scene);
        root.setPadding(new Insets(10));

        // zone de connexion
        gpcnx = new GridPane();
        gpcnx.setVgap(10);
        gpcnx.setHgap(20);
        gpcnx.setPadding(new Insets(10));
        // identifiants
        gpcnx.add(new Label("Pseudo"), 0, 0);
        tfnom = new TextField();
        tfnom.setPrefWidth(130);
        gpcnx.add(tfnom, 1, 0);
        // mode serveur ou connexion
        bserv = new Button("Mode serveur");
        bserv.setPrefWidth(100);
        bserv.setOnAction(eh -> {
            lmess.setText("");
            String ps = tfnom.getText();
            if (ps == null || ps.isBlank() || ps.isEmpty()) {
                lmess.setText("Définissez votre pseudo");
            } else {
                if (interdit(ps)) {
                    lmess.setText("Seuls les caractères alphanum sans accent et _ sont autorisés.");
                } else {
                    lmess.setText("");
                    jeu.setPseudo(ps);
                    jeu.lancerServeur();
                }
            }
        });
        gpcnx.add(bserv, 2, 0);
        gpcnx.add(new Label("IP machine locale"), 3, 0);
        iploc = new Label("");
        iploc.setPrefWidth(100);
        gpcnx.add(iploc, 4, 0);
        // bouton de connexion
        bcnx = new Button("Connexion");
        bcnx.setPrefWidth(100);
        bcnx.setOnAction(eh -> {
            lmess.setText("");
            String ps = tfnom.getText();
            if (ps == null || ps.isBlank() || ps.isEmpty()) {
                lmess.setText("Définissez votre pseudo");
            } else {
                String ip = tfipdist.getText();
                if (ip == null || ip.isEmpty() || ip.isBlank()) {
                    lmess.setText("Indiquez l'adresse IP du serveur distant");
                } else {
                    if (interdit(ps)) {
                        lmess.setText("Seuls les caractères alphanum sans accent et _ sont autorisés.");
                    } else {
                        jeu.connexion(ip, ps);
                    }
                }
            }
        });
        gpcnx.add(bcnx, 2, 1);
        Label lts = new Label("IP serveur distant");
        lts.setPrefWidth(115);
        gpcnx.add(lts, 3, 1);
        tfipdist = new TextField();
        tfipdist.setPrefWidth(100);
        gpcnx.add(tfipdist, 4, 1);
        
        //Affiche le pseudo de la personne en face
        gpcnx.add(new Label("Distant"),0,2);
        lnom=new Label();
        gpcnx.add(lnom,1,2);        
        
        //Discussion
        gpcnx.add(new Label("texte"),0,3);
        tftxt=new TextField();
        tftxt.setPrefWidth(470);
        gpcnx.add(tftxt, 1,3,  4,1);
        Button benv=new Button("Envoi");
        gpcnx.add(benv,1,4);
        benv.setOnAction(eh->{
            String txt=tftxt.getText();
            if(!txt.isEmpty())jeu.envoyer(txt);
        });
        
        //Message qui s'actualise même quand le code est lancé
        lmess = new Label();
        lmess.setPrefWidth(570);
        lmess.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        
        //Derniers trucs à faire
        root.getChildren().addAll(gpcnx,lmess);
        st.show();
    }
    
        public void setIP(String ip) {
        iploc.setText(ip);
    }

        /**
     * Affichage d'un message.
     *
     * @param mess le message.
     */
    public void setMess(String mess) {
        lmess.setText(mess);
    }
    
    public void setNom(String pseudo){
        lnom.setText(pseudo);
    }
    
    
    private boolean interdit(String txt) {
        for (char c : txt.toCharArray()) {
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || c == '_')) {
                return true;
            }
        }
        return false;
    }
}
