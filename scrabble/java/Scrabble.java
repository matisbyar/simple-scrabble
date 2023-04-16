public class Scrabble {

    private static final int[] nbPointsJeton = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};
    private final Joueur[] joueurs;
    private final Plateau plateau = new Plateau();
    private final int[] freqSac = {9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1};
    private final MEE sac = new MEE(freqSac);
    private int numJoueur;

    public Scrabble(String[] nomsJoueurs) {
        Joueur[] copieJoueur = new Joueur[nomsJoueurs.length];
        for (int i = 0; i < nomsJoueurs.length; i++) {
            copieJoueur[i] = new Joueur(nomsJoueurs[i]);
        }
        joueurs = copieJoueur;

        numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
    }

    public String toString() {
        StringBuilder scores = new StringBuilder();
        for (Joueur joueur : joueurs) {
            scores.append(joueur);
        }
        return plateau.toString() + joueurs[numJoueur] + " Il possède la main.\nVoici les scores : " + scores + " ";
    }

    public void partie() {
        int sortieJoue = 0;         // sortieJoue sert à déterminer les actions des joueurs à l'aide de la sortie de la méthode joue.
        boolean jeuArrete = false;  // jeuArrete sert à savoir si le jeu a été arrêté de force par les joueurs (tours passés successivement)

        // Premièrement, on distribue les jetons à chaque joueur
        for (Joueur joueur : joueurs) {
            joueur.prendJetons(sac, 7);
            Ut.afficher(sac.toString() + "\n");
        }

        // Ensuite, tant que le sac n'est pas vide ou qu'aucun des joueurs se retrouve avec 0 jeton, on lance ceci :
        while (!sac.estVide() || joueurs[numJoueur].nbPointsChevalet(nbPointsJeton) != 0 || !jeuArrete) {
            Ut.clearConsole();
            Ut.afficher(sac.toString() + "\n");
            Ut.afficher(toString());                                                                     // L'affichage du plateau
            sortieJoue += joueurs[numJoueur].joue(plateau, sac, nbPointsJeton);             // On fait jouer le joueur courant
            if (sortieJoue >= 0) {                                                            // Si le joueur ne passe pas son tour, sortieJoue revient à 0
                sortieJoue = 0;
            } else if (sortieJoue <= (joueurs.length * -1)) {                                          // Si tous les joueurs ont passé leur tour, le jeu s'arrête.
                jeuArrete = true;
            }
            if (numJoueur + 1 > joueurs.length - 1) {                                         // On passe ensuite au prochain joueur
                numJoueur = 0;
            } else {
                numJoueur += 1;
            }
        }
        // Enfin, si le sac est vide et que l'un des joueurs se retrouve avec 0 jeton, on finit la partie avec ceci :
        if (sac.estVide() && joueurs[numJoueur].nbPointsChevalet(nbPointsJeton) == 0) {
            // Si le jeu a été arrêté de force
            for (Joueur joueur : joueurs) {                                    // On enlève à chacun le score total des jetons de leur chevalet, de leur score
                joueur.ajouteScore(joueur.nbPointsChevalet(nbPointsJeton) * -1);
                joueur.ajouteScore(sac.sommeValeurs(nbPointsJeton) * -1);
            }
            StringBuilder vainqueur = new StringBuilder();
            int scoreVainqueur = 0;
            for (Joueur joueur : joueurs) {                                        // On cherche ensuite le vainqueur, c'est-à-dire celui qui a le plus de points
                if (joueur.getScore() > scoreVainqueur) {
                    vainqueur = new StringBuilder("le joueur " + joueur);
                    scoreVainqueur = joueur.getScore();
                } else if (joueur.getScore() == scoreVainqueur) {
                    vainqueur.append(" et le joueur ").append(joueur);
                }
            }                                                                               // On affiche enfin la phrase de fin
            Ut.afficher("Le jeu est terminé ! Voyons les scores.\nLe nom du gagnant est " + vainqueur + " avec un score de " + scoreVainqueur);
        }
    }
}
