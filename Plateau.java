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
    }
}
