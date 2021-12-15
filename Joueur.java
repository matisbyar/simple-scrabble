public class Joueur {
    private String nom;     // le nom du joueur
    private MEE chevalet;   // le chevalet du joueur
    private int score;      // le score du joueur

    public Joueur(String unNom){
        this.nom = unNom;
    }

    public String toString(){
        /* affiche les caractéristiques du joueur */
        return "Le joueur " + nom + " possède un score de " + score + ".";
    }

    public int getScore(){
        return this.score;
    }

    public void ajouteScore(int nb){
        this.score += nb;
    }

    public int nbPointsChevalet(int[] nbPointsJet){
        /* pré-requis : nbPointsJet indique le nombre de points rapportés par chaque jeton/lettre
           résultat : le nombre de points total sur le chevalet de ce joueur */
       return this.chevalet.sommeValeurs(nbPointsJet);
       // La fonction "sommeValeurs" appartient à la classe MEE
    }
}
