public class Scrabble {
    private static int[] nbPointsJeton = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10 }; // Nombre de points que rapportent un jeton
    private Joueur[] joueurs;   // tableau contenant les noms des joueurs
    private int numJoueur;      // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau;
    private MEE sac;

    public Scrabble(String[] nomsJoueurs) {
        for (int i = 0; i < nomsJoueurs.length; i++) {
            this.joueurs[i] = new Joueur(nomsJoueurs[i]);
        }
        numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
    }

    public String toString() {
        String scores = "";
        for (int i = 0; i < joueurs.length; i++) {
            scores += "Le joueur " + joueurs[i] + " a pour score " + joueurs[i].getScore() + "\n";
        }
        return plateau.toString() + "\nLe joueur " + joueurs[numJoueur] + " possède la main.\nVoici les scores : " + scores;
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
            toString();                                                                     // L'affichage du plateau
            sortieJoue = joueurs[numJoueur].joue(plateau, sac, nbPointsJeton);              // On fait jouer le joueur courant
            if(sortieJoue == 1){                                                            // Si le chevalet du joueur est vide, on le remplit à nouveau
                joueurs[numJoueur].prendJetons(sac, 7);
            }
            if(numJoueur + 1 > joueurs.length - 1){                                         // On passe ensuite au prochain joueur
                numJoueur = 0;
            } else {
                numJoueur += 1;
            }
            if (sortieJoue == joueurs.length * -1){                                         // Si tous les joueurs ont passé leur tour, le jeu s'arrête.
                jeuArrete = true;
            }
        }
        // Enfin; si le sac est vide ou que l'un des joueurs se retrouve avec 0 jeton, on finit la partie avec ceci :
        if(sac.estVide() || joueurs[numJoueur].nbPointsChevalet(nbPointsJeton) == 0){
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
                }
            }
            int vainqueur = 0;
            int scoreVainqueur = 0;
            String scores = "";
            for(int i = 0; i < joueurs.length; i++){                                        // Pour chaque joueur, on créé une ligne dans la variable scores, qui indique son nom et son score.
                scores += "Le joueur " + joueurs[i] + " a pour score " + joueurs[i].getScore() + "\n";
            }
            for(int i = 0; i < joueurs.length; i++){                                        // On cherche ensuite le vainqueur, c'est-à-dire celui qui a le plus de points
                if(joueurs[i].getScore() > scoreVainqueur){
                    vainqueur = i;
                } else if (joueurs[i].getScore() == scoreVainqueur){

                }
            }                                                                               // On affiche enfin la phrase de fin
            Ut.afficher("Le jeu est terminé ! Voyons les scores.\nLe nom du gagnant est " + joueurs[vainqueur] + " avec un score de " + joueurs[vainqueur].getScore());
        }
    }
}
