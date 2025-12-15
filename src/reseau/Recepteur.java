package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Récepteur d'un client. Gère la détection des messages entrants.
 *
 * @author olivier
 */
public class Recepteur implements Runnable {

    // le client associé.
    private Client client;
    // le socket de ce client.
    private Socket socket;

    /**
     * Constructeur.
     *
     * @param client le client d'appartenance.
     * @param socket le socket de communication.
     */
    Recepteur(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    /**
     * Lancement du récepteur. Les messages entrants sont transmis au client.
     * Détecte la rupture éventuelle de la connexion.
     */
    @Override
    public void run() {
        try {
            BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            while (true) {
                String texte = entree.readLine();
                if (texte != null) {
                    client.reception(texte);
                }
            }
        } catch (SocketException ex) {
            client.deconnexion();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
