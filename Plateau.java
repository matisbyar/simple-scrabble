public class Plateau {
    private Case[][] g; // g pour grille

    public Plateau() {
        int[][] plateau = { { 5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 },
                { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 },
                { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 },
                { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 },
                { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 },
                { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 },
                { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 },
                { 5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5 },
                { 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1 },
                { 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1 },
                { 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1 },
                { 2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2 },
                { 1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1 },
                { 1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1 },
                { 5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5 } };
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
        String affichage = "     1   2   3   4   5   6   7   8   9   10  11  12  13  14  15\n";
        for (int i = 0; i < g.length; i++) {
            affichage += i + 1 + "\t";
            affichage += " || ";
            for (int j = 0; j < g.length; j++) {
                affichage += g[i][j];
                affichage += " | ";
            }
            affichage += "\n";
            affichage += "    -------------------------------------------------------------- \n";
        }
        return affichage;
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
        int moitie = (g.length + 1) / 2;

        // Dans le cas où le plateau est vide
        if (this.g[moitie][moitie].getCouleur() == 4) {
            while (verification) {
                // Le mot proposé a au moins 2 lettres
                if (mot.length() < 2) {
                    verification = false;
                }
                // La zone de placement du mot contient la case centrale du plateau
                else if (numLig != moitie && numCol != moitie) {
                    verification = false;
                } else if (numLig == moitie && (sens == 'v' || numCol > moitie || numCol + mot.length() < moitie)) {
                    verification = false;
                } else if (numCol == moitie && (sens == 'h' || numLig > moitie || numLig + mot.length() < moitie)) {
                    verification = false;
                }
                // Le chevalet du joueur proposant le mot contient les lettres permettant de
                // former le mot
                MEE chevalet = new MEE(e);
                for (int i = 0; i < mot.length(); i++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(i)));
                }

            }
        }
        // Dans le cas où le plateau n'est pas vide
        else {
            while (verification) {
                // La zone de placement du mot ne dépasse pas de la grille
                if ((sens == 'h' && numCol + mot.length() >= g.length)
                        || (sens == 'v' && numLig + mot.length() >= g.length)) {
                    verification = false;
                }
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
        int nbPointsPlacement = 0;
        for (int i = 0; i < mot.length(); i++) {
            // Dans le cas où le sens est horizontal et que la case est grise (pas de
            // valorisation)
            if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))];
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleu clair (lettre
            // compte double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 2;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleue (lettre compte
            // triple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 3;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est rose (mot compte
            // double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; i < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 2;
            }
            // Dans le cas où le sens est horizontal et que la case est rouge (mot compte
            // triple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; i < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 3;
            }

            // Dans le cas où le sens est vertical et que la case est grise (pas de
            // valorisation)
            if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))];
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est bleu clair (lettre
            // compte double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 2;
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est bleue (lettre compte
            // triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 3;
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est rose (mot compte
            // double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 2;
            }
            // Dans le cas où le sens est vertical et que la case est rouge (mot compte
            // triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 3;
            }
        }
        return nbPointsPlacement;
    }

    public int place(String mot, int numLig, int numCol, char sens, MEE e){
        /* pré-requis : le placement de mot sur this à partir de la case (numLig, numCol) dans le sens donné par sens à l'aide des jetons de e est valide.
           action/résultat : effectue ce placement et retourne le nombre de jetons retirés de e. */
        int nbJetonsRetire = 0;
        if(placementValide(mot, numLig, numCol, sens, e) && sens == 'h'){
            for(int i = 0; i < mot.length(); i++){
                this.g[numLig][numCol + i].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)));
            }
            nbJetonsRetire = 7 - mot.length();
        }else if(placementValide(mot, numLig, numCol, sens, e) && sens == 'v'){
            for(int i = 0; i < mot.length(); i++){
                this.g[numLig + i][numCol].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)));
            }
            nbJetonsRetire = 7 - mot.length();
        }
        return nbJetonsRetire;
    }
}
