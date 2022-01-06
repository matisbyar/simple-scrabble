public class Scrabble {
    private static int[] nbPointsJeton = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};    // Nombre de points que rapportent un jeton
    private Joueur[] joueurs;
    private int numJoueur;      // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau;
    private MEE sac;

    public Scrabble(String[] nomsJoueurs){
        for(int i = 0; i < nomsJoueurs.length; i++){
            this.joueurs[i] = new Joueur(nomsJoueurs[i]);
        }
        numJoueur = Ut.randomMinMax(0, joueurs.length - 1);
    }

    public String toString(){
        return plateau.toString() + "\nLe joueur " + joueurs[numJoueur] + " possède la main.";
    }

    public void partie(){
        for(int i = 0; i < joueurs.length; i++){
            joueurs[i].prendJetons(sac, 7);
        }
    }
}
