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
        String affichage = "     1   2   3   4   5   6   7   8   9   10  11  12  13  14  15\n   = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = \n";
        for(int i = 0; i < g.length; i++){
            affichage += Ut.indexToMaj(i);
            affichage += " || ";
            for (int j = 0; j < g.length; j++) {
                affichage += g[i][j];
                affichage += " | ";
            }
            affichage += "\n";
            affichage += "    ------------------------------------------------------------ \n"; 
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
        boolean bouclesortie = true;
        int moitie = (g.length - 1) / 2;
        boolean aucuneOccupee = true;
        int i = 0;
        boolean aucuneRecouverte = true;
        int j = 0;


        // Dans le cas où le plateau est vide
        if (!this.g[moitie][moitie].estRecouverte()) {
            while (verification && bouclesortie) {
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
                
                // Le chevalet du joueur proposant le mot contient les lettres permettant de former le mot
                MEE chevalet = new MEE(e);
                for (int a = 0; a < mot.length(); a++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(a)));
                }
                // Toutes les vérifications sont valides, donc on sort du while
                bouclesortie = false;
            }
        }
        // Dans le cas où le plateau n'est pas vide
        else {
            while (verification && bouclesortie) {
                // La zone de placement du mot ne dépasse pas de la grille
                if ((sens == 'h' && numCol + 1 + mot.length() >= g.length) || (sens == 'v' && numLig + 1 + mot.length() >= g.length)) {
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
                        }
                        else {
                            aucuneOccupee = false;
                        }
                    }
                }
                else if (sens == 'v') {
                    while (aucuneOccupee && i < mot.length()) {
                        if (this.g[numLig + i][numCol].estRecouverte()) {
                            i++;
                        }
                        else {
                            aucuneOccupee = false;
                        }
                    }
                }
                else if (aucuneOccupee) {
                    verification = false;
                }
                // La zone de placement contient au moins une case recouverte
                else if (sens == 'h') {
                    while (aucuneRecouverte && j < mot.length()) {
                        if (this.g[numLig][numCol + j].estRecouverte()) {
                           aucuneRecouverte = false;
                        }
                        else {
                             j++;
                        }
                    }
                }
                else if (sens == 'v') {
                    while (aucuneRecouverte && j < mot.length()) {
                        if (this.g[numLig + j][numCol].estRecouverte()) {
                            aucuneRecouverte = false;
                        }
                        else {
                            j++;
                        }
                    }
                }
                else if (aucuneRecouverte) {
                    verification = false;
                }
                // Pour chaque case recouverte de la zone de placement du mot, la lettre du jeton est la même que celle de la case
                else if (sens == 'h') {
                    for (int k = 0; k < mot.length(); k++) {
                        if (this.g[numLig][numCol + k].estRecouverte() && this.g[numLig][numCol + k].getLettre() != mot.charAt(i)) {
                            verification = false;
                        }
                    }
                }
                else if (sens == 'v') {
                    for (int k = 0; k < mot.length(); k++) {
                        if (this.g[numLig + k][numCol].estRecouverte() && this.g[numLig + k][numCol].getLettre() != mot.charAt(i)) {
                            verification = false;
                        }
                    }
                }
                // Le chevalet du joueur proposant le mot contient les lettres permettant de former le mot
                MEE chevalet = new MEE(e);
                for (int l = 0; l < mot.length(); l++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(l)));
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
            // Dans le cas où le sens est horizontal et que la case est grise (pas de
            // valorisation)
            // Dans le cas où le sens est horizontal et que la case est grise (pas de valorisation)
            if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))];
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleu clair (lettre
            // compte double)
            // Dans le cas où le sens est horizontal et que la case est bleu clair (lettre compte double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 2;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est bleue (lettre compte
            // triple)
            // Dans le cas où le sens est horizontal et que la case est bleue (lettre compte triple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 3;
                numCol++;
            }
            // Dans le cas où le sens est horizontal et que la case est rose (mot compte
            // double)
            // Dans le cas où le sens est horizontal et que la case est rose (mot compte double)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; i < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 2;
                coefCumul *= 2;
            }
            // Dans le cas où le sens est horizontal et que la case est rouge (mot compte
            // triple)
            // Dans le cas où le sens est horizontal et que la case est rouge (mot compte triple)
            else if (sens == 'h' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; i < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 3;
                coefCumul *= 3;
            }

            // Dans le cas où le sens est vertical et que la case est grise (pas de
            // valorisation)
            // Dans le cas où le sens est vertical et que la case est grise (pas de valorisation)
            if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 1) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))];
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est bleu clair (lettre
            // compte double)
            // Dans le cas où le sens est vertical et que la case est bleu clair (lettre compte double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 2) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 2;
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est bleue (lettre compte
            // triple)
            // Dans le cas où le sens est vertical et que la case est bleue (lettre compte triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 3) {
                nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(i))] * 3;
                numCol++;
            }
            // Dans le cas où le sens est vertical et que la case est rose (mot compte
            // double)
            // Dans le cas où le sens est vertical et que la case est rose (mot compte double)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 4) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 2;
                coefCumul *= 2;
            }
            // Dans le cas où le sens est vertical et que la case est rouge (mot compte
            // triple)
            // Dans le cas où le sens est vertical et que la case est rouge (mot compte triple)
            else if (sens == 'v' && this.g[numLig][numCol].getCouleur() == 5) {
                for (int j = 0; j < mot.length(); j++) {
                    nbPointsPlacement += nbPointsJet[Ut.majToIndex(mot.charAt(j))];
                    numCol++;
                }
                nbPointsPlacement *= 3;
                coefCumul *= 3;
            }
        }
        nbPointsPlacement *= coefCumul; // on multiplie le nombre de points aux "mot compte double" et "mot compte triple" rencontrés
        return nbPointsPlacement;
    }
    
    public int place(String mot, int numLig, int numCol, char sens, MEE e){
        /* pré-requis : le placement de mot sur this à partir de la case (numLig, numCol) dans le sens donné par sens à l'aide des jetons de e est valide.
           action/résultat : effectue ce placement et retourne le nombre de jetons retirés de e. */
        int nbJetonsRetire = 0;
        if (placementValide(mot, numLig, numCol, sens, e) && sens == 'h'){
            for(int i = 0; i < mot.length(); i++){
                this.g[numLig][numCol + i].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)));
            }
            nbJetonsRetire = 7 - mot.length();
        } else if(placementValide(mot, numLig, numCol, sens, e) && sens == 'v'){
            for(int i = 0; i < mot.length(); i++){
                this.g[numLig + i][numCol].setLettre(mot.charAt(i));
                e.retire(Ut.majToIndex(mot.charAt(i)));
            }
            nbJetonsRetire = 7 - mot.length();
        }
        return nbJetonsRetire;
    }
}
