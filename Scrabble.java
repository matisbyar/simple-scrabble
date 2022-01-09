public class Scrabble {
    private static int[] nbPointsJeton = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10 }; // Nombre de points que rapportent un jeton
    private Joueur[] joueurs;   // tableau contenant les joueurs
    private int numJoueur;      // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau = new Plateau();
    private int[] freqSac = {9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1};
    private MEE sac = new MEE(freqSac);

    public Scrabble(String[] nomsJoueurs) {
        Joueur[] copieJoueur = new Joueur[nomsJoueurs.length];
        for (int i = 0; i < nomsJoueurs.length; i++) {
            copieJoueur[i] = new Joueur(nomsJoueurs[i]);
        }
        joueurs = copieJoueur;

        numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
    }

    public String toString() {
        String scores = "";
        for (int i = 0; i < joueurs.length; i++) {
            scores += joueurs[i];
        }
        return plateau.toString() + joueurs[numJoueur] + " Il possède la main.\nVoici les scores : " + scores;
    }

    public void partie(){
        int sortieJoue = 0;         // sortieJoue sert à déterminer les actions des joueurs à l'aide de la sortie de la méthode joue.
        boolean jeuArrete = false;  // jeuArrete sert à savoir si le jeu a été arrêté de force par les joueurs (tours passés successivement)
        // Premièrement, on distribue les jetons à chaque joueur
        for(int i = 0; i < joueurs.length; i++){
            joueurs[i].prendJetons(sac, 7);
        }
        // Ensuite, tant que le sac n'est pas vide ou qu'aucun des joueurs se retrouve avec 0 jeton, on lance ceci :
        while(!sac.estVide() || joueurs[numJoueur].nbPointsChevalet(nbPointsJeton) != 0 || !jeuArrete){
            Ut.clearConsole();
            Ut.afficher(toString());                                                                     // L'affichage du plateau
            sortieJoue += joueurs[numJoueur].joue(plateau, sac, nbPointsJeton);             // On fait jouer le joueur courant
            if(sortieJoue == 1){                                                            // Si le chevalet du joueur est vide, on le remplit à nouveau
                joueurs[numJoueur].prendJetons(sac, 7);
            }
            if(numJoueur + 1 > joueurs.length - 1){                                         // On passe ensuite au prochain joueur
                numJoueur = 0;
            } else {
                numJoueur += 1;
            }
            if (sortieJoue != -1){                                                          // Si le joueur ne passe pas son tour, sortieJoue revient à 0
                sortieJoue = 0;
            }
            if (sortieJoue == joueurs.length - 1){                                          // Si tous les joueurs ont passé leur tour, le jeu s'arrête.
                jeuArrete = true;
            }
        }
        // Enfin, si le sac est vide et que l'un des joueurs se retrouve avec 0 jeton, on finit la partie avec ceci :
        if(sac.estVide() && joueurs[numJoueur].nbPointsChevalet(nbPointsJeton) == 0){
            if(!jeuArrete){                                                                         // Si le jeu n'a pas été arrêté de force
                for(int j = 0; j < joueurs.length; j ++){                                           // On ajoute le score des chevalets des autres joueurs au joueur courant et on retire cela aux autres joueurs 
                    joueurs[numJoueur].ajouteScore(joueurs[j].nbPointsChevalet(nbPointsJeton));
                    if(numJoueur != j){
                        joueurs[j].ajouteScore(joueurs[j].nbPointsChevalet(nbPointsJeton) * -1);
                    }    
                }    
            } else if (jeuArrete){                                                          // Si le jeu a été arrêté de force
                for(int j = 0; j < joueurs.length; j++){                                    // On enlève à chacun le score total des jetons de leur chevalet, de leur score
                    joueurs[j].ajouteScore(joueurs[j].nbPointsChevalet(nbPointsJeton) * -1);
                    joueurs[j].ajouteScore(sac.sommeValeurs(nbPointsJeton) * -1);
                }
            }
            String vainqueur = "";
            int scoreVainqueur = 0;
            for(int i = 0; i < joueurs.length; i++){                                        // On cherche ensuite le vainqueur, c'est-à-dire celui qui a le plus de points
                if(joueurs[i].getScore() > scoreVainqueur){
                    vainqueur = "le joueur " + joueurs[i];
                    scoreVainqueur = joueurs[i].getScore();
                } else if (joueurs[i].getScore() == scoreVainqueur){
                    vainqueur += " et le joueur " + joueurs[i];
                }
            }                                                                               // On affiche enfin la phrase de fin
            Ut.afficher("Le jeu est terminé ! Voyons les scores.\nLe nom du gagnant est " + vainqueur + " avec un score de " + scoreVainqueur);
        }
    }
}
