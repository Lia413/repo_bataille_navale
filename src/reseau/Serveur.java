package reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author olivier
 */
public class Serveur implements Runnable {

    // gestionnaire des communications 
    private Reseau reseau;
    // socket de réception des connexions distantes
    private ServerSocket socketServeur;

    /**
     * Constructeur du Serveur.
     *
     * @param reseau le gestionnaire du réseau.
     * @param socketServeur le socketServer.
     */
    public Serveur(Reseau reseau, ServerSocket socketServeur) {
        this.reseau = reseau;
        this.socketServeur = socketServeur;
    }

    /**
     * Exécution de l'écoute.
     */
    @Override
    public void run() {
        try {
            while (true) {
                //Attente de la connexion en provenance du client
                Socket socket = socketServeur.accept();
                System.out.println(socket);
                // transmission du nouveau client au réseau
                reseau.accepteClient(new Client(reseau, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
