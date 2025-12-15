package jeu;

import reseau.IAppli;
import reseau.Reseau;

/**
 * Démonstration de jeu (appli réseau)
 *
 * @author olivier
 */
public class Jeu implements IAppli {

    // fenêtre principale
    Fenetre fen;
    // gestionnaire de réseau
    Reseau reseau;

    String pseudo;
    boolean estServeur = false;
    String nom;

    /**
     * Création du jeu avec un nombre maxi de joueurs.
     */
    public Jeu() {
    }

    /**
     * Affectation de la fenêtre principale.
     *
     * @param fen la fenêtre.
     */
    public void setFenetre(Fenetre fen) {
        this.fen = fen;
    }

    /**
     * Affectation du gestionnaire de réseau.
     *
     * @param reseau le réseau.
     */
    public void setReseau(Reseau reseau) {
        this.reseau = reseau;
        reseau.setAppli(this);
    }

    /**
     * Signalement envoyé par le réseau suite à un évènement anormal.
     *
     * @param code code du signalement : 1 lancement serveur impossible, 2
     * connexion impossible ou refusée, 3 déconnexion.
     */
    public void signaler(int code) {
        switch (code) {
            case 1: // lancement serveur impossible
                fen.setMess("lancement serveur impossible : port occupé");
                fen.setIP("");
                break;
            case 2: // non réponse du serveur
                fen.setMess("connexion impossible ou refusée");
                break;
            case 3: // déconnexion détectée
                fen.setMess("deconnexion du client distant");
                break;
        }
    }

    /**
     * Demande lancement du serveur. Attente de connexion ultérieure.
     */
    public void lancerServeur() {
        fen.setNom(pseudo);
        fen.setMess("Serveur lancé. Attendez connexion.");
        // lancement serveur.
        if (reseau.startServeur()) {
            estServeur = true;
        }
        // affichage de l'adresse IP de ce serveur
        fen.setIP(reseau.getIP());
    }

    /**
     * Demande de connexion en mode client.
     *
     * @param ip adresse IP du serveur distant.
     * @param pseudo nom de l'usager local.
     */
    public void connexion(String ip, String pseudo) {
        this.pseudo = pseudo;
        reseau.connexion(ip);
    }

    /**
     * Affecte le pseudo du joueur.
     *
     * @param pseudo le pseudo choisi.
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Renvoie la phrase à transemttre en cas de refus de connexion (nombre maxi
     * atteint).
     *
     * @return la phrase à transmettre au nouveau client avant fermeture.
     */
    public String getRefusConnexion() {
        return "NOCNX";
    }

    /**
     * Indique une nouvelle connexion cliente.
     */
    public void connexionAcceptee() {
        fen.setMess("connexion acceptée");
        reseau.emettre("OKCNX;" + pseudo);
    }

    /**
     * Réception d'un message réseau.
     *
     * @param mess le message.
     */
    public void reception(String mess) {
        System.out.println("-> " + mess);
        String[] dec = mess.split(";");
        switch (dec[0]) {
            case "OKCNX":
                fen.setMess("connexion acceptée");
                fen.setNom(dec[1]);
                reseau.emettre("NOM;" + pseudo);
                break;
            case "NOCNX":
                fen.setMess("connexion refusée");
                break;
            case "NOM":
                fen.setNom(dec[1]);
                break;
            case "MESS":
                fen.setMess(dec[1]);
                break;
        }
    }

    public void envoyer(String txt) {
        reseau.emettre("MESS;" + txt);
    }
}
