public class Plateau {
    private Case[][] g;     // g pour grille

    public Plateau(){
        int[][] plateau = { {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
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
    }

    public Plateau(Case[][] plateau){
        this.g = plateau;
    }

    public String toString(){
        String affichage = "     1   2   3   4   5   6   7   8   9   10  11  12  13  14  15\n";
        for(int i = 0; i < g.length; i++){
            affichage += i + 1 + "\t";
            affichage += " || ";
            for(int j = 0; j < g.length; j++){
                affichage += g[i][j];
                affichage += " | ";
            }
            affichage += "\n";
            affichage += "    -------------------------------------------------------------- \n";
        }
        return affichage;
    }

    public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e){
        /* pré-requis : mot est un mot accepté par CapeloDico,
                       0 <= numLig <= 14, 0 <= numCol <= 14, sens est un élément de {'h', 'v'}
                       et l'entier maximum prévu pour e est au moins 25 
           résultat : retourne vrai ssi le placement de mot sur this à partir de la case (numLig, numCol) dans le sens donné par sens
                      à l'aide des jetons de e est valide. */
        // VOIR LE SUJET POUR TOUTES LES VÉRIFICATIONS
        boolean verification = true;
        int finVerification = 0;
        int moitie = (g.length+1)/2;
        
        // Dans le cas où le plateau est vide
        if (plateau[moitie][moitie] == 4) {
            while (verification || finVerification == 0) {
                // Le mot proposé a au moins 2 lettres
                if (mot.length < 2) {
                    verification = false;
                }
                // La zone de placement du mot contient la case centrale du plateau
                else if (numLig != moitie && numCol != moitie) {
                    verification = false;
                }
                else if (numLig == moitie && (sens = 'v' || numCol > moitie || numCol+mot.length < moitie)) {
                    verification = false;
                }
                else if (numCol == moitie && (sens = 'h' || numLig > moitie || numLig+mot.length < moitie)) {
                    verification = false;
                }
                // Le chevalet du joueur proposant le mot contient les lettres permettant de former le mot
                MEE chevalet = MEE(e);
                for (int i = 0; i < mot.length; i++) {
                    verification = chevalet.retire(Ut.majToIndex(mot.charAt(i)));
                }
                finVerification = 1;
            }
        }
        // Dans le cas où le plateau n'est pas vide
        else {
            while (verification || finVerification == 0) {
                // La zone de placement du mot ne dépasse pas de la grille
                if ((sens == 'h' && numCol+mot.length >= g.length) || (sens == 'v' && numLig+mot.length >= g.length)) {
                    verification = false; 
                }
    }
}
