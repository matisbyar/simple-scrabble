public class Plateau {
    private Case[][] g = new Case[15][15]; // g pour grille

    public Plateau() {
        int[][] plateau = {{5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
                {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
                {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
                {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
                {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
                {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                {5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5},
                {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
                {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
                {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
                {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
                {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5}};
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau.length; j++) {
                this.g[i][j] = new Case(plateau[i][j]);
            }
        }
    }

    public Plateau(Case[][] plateau) {
        this.g = plateau;
    }

    public String toString() {
        StringBuilder affichage = new StringBuilder("     1   2   3   4   5   6   7   8   9   10  11  12  13  14  15\n   = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = \n");
        for (int i = 0; i < g.length; i++) {
            affichage.append(Ut.indexToMaj(i + 1));
            affichage.append(" || ");
            for (int j = 0; j < g.length; j++) {
                if (g[i][j].getCouleur() == 1 && !g[i][j].estRecouverte()) {
                    affichage.append(' ');
                } else if ((g[i][j].getCouleur() == 2 && !g[i][j].estRecouverte()) || (g[i][j].getCouleur() == 3 && !g[i][j].estRecouverte()) || (g[i][j].getCouleur() == 4 && !g[i][j].estRecouverte()) || (g[i][j].getCouleur() == 5 && !g[i][j].estRecouverte())) {
                    affichage.append(g[i][j].getCouleur());
                } else if (g[i][j].estRecouverte()) {
                    affichage.append(g[i][j].getLettre());

                }
                affichage.append(" | ");
            }
            affichage.append("\n");
            affichage.append("    ------------------------------------------------------------ \n");
        }
        return affichage.toString();
    }

    public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) {
        /*
         * pré-requis : mot est un mot accepté par CapeloDico, 0 <= numLig <= 14,
         * 0 <= numCol <= 14, sens est un élément de {'h', 'v'}
         * et l'entier maximum prévu pour e est au moins 25
         * résultat : retourne vrai ssi le placement de mot sur this à partir de la case
         * (numLig, numCol) dans le sens donné par sens
         * à l'aide des jetons de e est valide.
         */
        boolean verification = true;
        boolean bouclesortie = true;
        boolean aucuneOccupee = true;
        int i = 0;
        boolean aucuneRecouverte = true;
        int j = 0;

        // Dans le cas où le plateau est vide
        if (!this.g[7][7].estRecouverte()) {
            while (verification && bouclesortie) {
                // Le mot proposé a au moins 2 lettres
                if (mot.length() < 2) {
                    verification = false;
                }
                // La zone de placement du mot contient la case centrale du plateau
                else if (numLig != 8 && numCol != 8) {
                    verification = false;
                } else if (numLig == 8 && (sens == 'v' || numCol > 8 || numCol + mot.length() < 8)) {
                    verification = false;
                } else if (numCol == 8 && (sens == 'h' || numLig > 8 || numLig + mot.length() < 8)) {
                    verification = false;
                }

                // Le chevalet du joueur proposant le mot contient les lettres permettant de former le mot
                MEE chevalet = new MEE(e);
                for (int a = 0; a < mot.length(); a++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(a)) - 1);
                }
                // Toutes les vérifications sont valides, donc on sort du while
                bouclesortie = false;
            }
        }
        // Dans le cas où le plateau n'est pas vide
        else {
            while (verification && bouclesortie) {
                // La zone de placement du mot ne dépasse pas de la grille
                if ((sens == 'h' && numCol + 1 + mot.length() > g.length) || (sens == 'v' && numLig + 1 + mot.length() > g.length)) {
                    verification = false;
                }
                // La zone de placement n'est pas précédée d'une case recouverte par un jeton
                else if ((sens == 'h' && numCol != 0 && this.g[numLig][numCol - 1].estRecouverte()) || (sens == 'v' && numLig != 0 && this.g[numLig - 1][numCol].estRecouverte())) {
                    verification = false;
                }
                // La zone de placement n'est pas suivie d'une case recouverte par un jeton
                else if ((sens == 'h' && numCol + mot.length() != 15 && this.g[numLig][numCol + 1].estRecouverte()) || (sens == 'v' && numLig + mot.length() != 15 && this.g[numLig + 1][numCol].estRecouverte())) {
                    verification = false;
                }
                // La zone de placement contient au moins une case non recouverte
                else if (sens == 'h') {
                    while (aucuneOccupee && i < mot.length()) {
                        if (this.g[numLig][numCol + i].estRecouverte()) {
                            i++;
                        } else {
                            aucuneOccupee = false;
                        }
                    }
                } else if (sens == 'v') {
                    while (aucuneOccupee && i < mot.length()) {
                        if (this.g[numLig + i][numCol].estRecouverte()) {
                            i++;
                        } else {
                            aucuneOccupee = false;
                        }
                    }
                } else {
                    verification = false;
                }
                // Le chevalet du joueur proposant le mot contient les lettres permettant de former le mot
                MEE chevalet = new MEE(e);
                for (int l = 0; l < mot.length(); l++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(l)) - 1);
                }
                // Toutes les vérifications sont valides, donc on sort du while
                bouclesortie = false;
            }
        }
        return verification;
    }

    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointsJet) {
        /*
         * pré-requis : le placement de mot sur this à partir de la case
         * n(numLig, numCol) dans le sens donné par sens est valide
         * résultat : retourne le nombre de points rapportés par ce placement, le nombre
         * de points de chaque jeton étant donné par le tableau nbPointsJet.
         */
        /* pré-requis : le placement de mot sur this à partir de la case
                        (numLig, numCol) dans le sens donné par sens est valide
          résultat : retourne le nombre de points rapportés par ce placement, le nombre
                        de points de chaque jeton étant donné par le tableau nbPointsJet. */
        int nbPointsPlacement = 0;
        int coefCumul = 1; // on initialise une variable qui compte les "mot compte double" ou "mot compte triple".
        for (int i = 0; i < mot.length(); i++) {
            // Dans le cas où le sens est horizontal et que la case est grise (pas de valorisation)
            if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1];
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleu clair (lettre compte double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1] * 2;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleue (lettre compte riple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1] * 3;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est rose (mot compte double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; j < mot.length() - 1; j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j)) - 1];
                    numCol++;
                }
                nbPointsPlacement *= 2;
                coefCumul *= 2;
            }
            // Dans le cas où le sens est horizontal et que la case est rouge (mot compte triple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; j < mot.length() - 1; j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j)) - 1];
                    numCol++;
                }
                nbPointsPlacement *= 3;
                coefCumul *= 3;
            }

            // Dans le cas où le sens est vertical et que la case est grise (pas de valorisation)
            if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1];
                numLig++;
            }
            // Dans le cas où le sens est vertical et que la case est bleu clair (lettre compte double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1] * 2;
                numLig++;
            }
            // Dans le cas où le sens est vertical et que la case est bleue (lettre compte triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i)) - 1] * 3;
                numLig++;
            }
            // Dans le cas où le sens est vertical et que la case est rose (mot compte double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j)) - 1];
                    numLig++;
                }
                nbPointsPlacement *= 2;
                coefCumul *= 2;
            }
            // Dans le cas où le sens est vertical et que la case est rouge (mot compte triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j)) - 1];
                    numLig++;
                }
                nbPointsPlacement *= 3;
                coefCumul *= 3;
            }
        }
        nbPointsPlacement *= coefCumul; // on multiplie le nombre de points aux "mot compte double" et "mot compte triple" rencontrés
        if (mot.length() == 7) {
            nbPointsPlacement += 50;
        }
        return nbPointsPlacement;
    }

    public int place(String mot, int numLig, int numCol, char sens, MEE e) {
        /* pré-requis : le placement de mot sur this à partir de la case (numLig, numCol) dans le sens donné par sens à l'aide des jetons de e est valide.
           action/résultat : effectue ce placement et retourne le nombre de jetons retirés de e. */
        int nbJetonsRetire = 0;
        if (placementValide(mot, numLig, numCol, sens, e) && sens == 'h') {
            for (int i = 0; i < mot.length(); i++) {
                this.g[numLig][numCol + i].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)) - 1);
            }
            nbJetonsRetire = mot.length();
        } else if (placementValide(mot, numLig, numCol, sens, e) && sens == 'v') {
            for (int i = 0; i < mot.length(); i++) {
                this.g[numLig + i][numCol].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)) - 1);
            }
            nbJetonsRetire = mot.length();
        }
        return nbJetonsRetire;
    }
}
