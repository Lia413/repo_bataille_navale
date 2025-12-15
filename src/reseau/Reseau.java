package reseau;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform;

/**
 * Gestionnaire de réseau (TCP) : gère un serveur (avec plusieurs clients) ou un
 * client (vers un serveur).
 *
 * @author olivier
 */
public class Reseau {

    private IAppli appli;
    // adresse IP locale
    private InetAddress iploc;
    // port d'écoute du serveur
    private int port;

    // SI SERVEUR ------------------------------------------------------
    // le serveur
    private Serveur serveur;
    // message de refus de nouveau client
    private String refus;

    // SI CLIENT ------------------------------------------------------
    // le client local
    private Client client;

    /**
     * Constructeur de Réseau. Détermine son adresse IP.
     *
     * @param port le numéro de port du serveur.
     */
    public Reseau(int port) {
        this.port = port;
        try {
            // adresse IP locale
            iploc = InetAddress.getLocalHost();
            String nom = iploc.getHostName();
            System.out.println("nom " + nom);
            InetAddress[] lst = InetAddress.getAllByName(nom);
            for (int i = 0; i < lst.length; i++) {
                if (lst[i].isSiteLocalAddress()) {
                    iploc = lst[i];
                }
                break;
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Affectation du jeu.
     *
     * @param appli l'application nécessitant le réseau.
     */
    public void setAppli(IAppli appli) {
        this.appli = appli;
        // récupère le message de refus de nouveau client.
        refus = appli.getRefusConnexion();
    }

    //-----------------------------------------------------------------------------
    //
    // Méthodes si serveur
    //
    //-----------------------------------------------------------------------------
    /**
     * Renvoie l'adresse IP locale.
     *
     * @return l'adresse IP.
     */
    public String getIP() {
        return iploc.getHostAddress();
    }

    /**
     * Lancement d'un serveur. Signale une erreur si le port par défaut est déjà
     * en service.
     *
     * @return true si lancement ok.
     */
    public boolean startServeur() {
        try {
            ServerSocket socketServeur = new ServerSocket(port, 50, iploc);
            serveur = new Serveur(this, socketServeur);
            // lancement dans un thread spécifique
            Thread thsrv = new Thread(serveur);
            thsrv.setDaemon(true);
            thsrv.start();
            return true;
        } catch (BindException ex) {
            // le port d'écoute est déjà utilisé
            signaler(1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Client créé par le serveur sur demande de connexion externe.
     *
     * @param client le client.
     */
    public void accepteClient(Client client) {
        System.out.println("nouveau client");
        if (this.client != null) {
            // le client est déjà connecté : refus et terminaison du client
            client.emettre(refus);
            client.terminer();
        } else {
            // acceptation du nouveau client
            this.client = client;
            //informe le jeu d'un nouveau client
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    appli.connexionAcceptee();
                }
            });
        }
    }

    //-----------------------------------------------------------------------------
    //
    // Méthodes si client
    //
    //-----------------------------------------------------------------------------
    /**
     * Demande de création d'un client pour connexion à un serveur distant.
     *
     * @param ip l'adresse du serveur distant.
     */
    public void connexion(String ip) {
        try {
            Socket socket = new Socket(ip, port);
            if (socket.isConnected()) {
                client = new Client(this, socket);
            } else {
                signaler(2);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            signaler(2);
        }
    }

    //----------------------------------------------------------------------------
    //
    // Méthodes générales
    //
    //----------------------------------------------------------------------------
    /**
     * Poste client : envoi d'un message au client distant.
     *
     * @param mess le message.
     */
    public void emettre(String mess) {
        client.emettre(mess);
    }

    /**
     * Envoi d'un message reçu au jeu.
     *
     * @param mess le message à transférer au jeu.
     */
    public void reception(String mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                appli.reception(mess);
            }
        });
    }

    /**
     * Demande de fin de connexion.
     */
    public void terminer() {
        if (client != null) {
            client.terminer();
        }
        client = null;
    }

    /**
     * Remontée de deconnexion de la connexion en cours.
     */
    public void deconnexion() {
        signaler(3);
    }

    /**
     * Envoi d'un signalement au jeu.
     *
     * @param code code du signalement.
     */
    public void signaler(int code) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                appli.signaler(code);
            }
        });
    }

}
