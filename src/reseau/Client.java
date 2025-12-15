package reseau;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gestionnaire d'une connexion avec un client distant.
 *
 * @author olivier
 */
public class Client {

    // le gestionnaire de réseau.
    private Reseau reseau;
    // le socket de connexion.
    private Socket socket;
    // le récepteur associé.
    private Recepteur recept;
    // le flux d'émission.
    private PrintWriter sortie;
     
    /**
     * Création d'un client. Demarre son récepteur dans un thread séparé.
     *
     * @param reseau le réseau associé.
     * @param socket le socket du client.
     */
    public Client(Reseau reseau, Socket socket) {
        this.reseau = reseau;
        try {
            this.socket = socket;
            sortie = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            recept = new Recepteur(this, socket);
            // lancement dans un thread spécifique
            Thread threc = new Thread(recept);
            threc.setDaemon(true);
            threc.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Emission d'un message vers le socket distant.
     *
     * @param texte le message à envoyer.
     */
    public void emettre(String texte) {
        sortie.println(texte);
    }

    /**
     * Réception d'un message, transmis au réseau.
     *
     * @param texte le message reçu.
     */
    public void reception(String texte) {
        reseau.reception(texte);
    }

    /**
     * Demande de fin de connexion.
     */
    public void terminer() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Signalement d'une rupture de la connexion.
     */
    public void deconnexion() {
        reseau.deconnexion();
    }
}
