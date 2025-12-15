package reseau;

/**
 * Interface que doit implémenter une application pour que le réseau lui
 * transfert des informations.
 *
 * @author olivier
 */
public interface IAppli {

    /**
     * Affectation du réseau.
     * 
     * @param reseau le réseau.
     */
    
    public void setReseau(Reseau reseau);
    /**
     * Signalement d'une anomalie réseau.
     *
     * @param code code du signalement : 1 lancement serveur impossible, 2
     * erreur sur demande de connexion, 3 déconnexion.
     */
    public void signaler(int code);

    /**
     * Renvoie la phrase à transmettre en cas de refus de connexion (nombre maxi
     * de clients connectés atteints).
     *
     * @return la phrase à transmettre au nouveau client avant fermeture.
     */
    public String getRefusConnexion();

    /**
     * Indique à l'application qu'un nouveau client a été accepté.
     */
    public void connexionAcceptee();

    /**
     * Réception d'un message réseau.
     *
     * @param mess le message.
     */
    public void reception(String mess);
}
