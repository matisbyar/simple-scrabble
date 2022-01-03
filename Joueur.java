public class Joueur {
    private String nom;     // le nom du joueur
    private MEE chevalet;   // le chevalet du joueur
    private int score;      // le score du joueur

    public Joueur(String unNom){
        /* Constructeur */
        this.nom = unNom;
    }

    public String toString(){
        /* affiche les caractéristiques du joueur */
        return "Le joueur " + nom + " possède un score de " + score + ".";
    }

    public int getScore(){
        /* résultat : renvoie le score de this */
        return this.score;
    }

    public void ajouteScore(int nb){
        /* résultat : ajoute nb au score de this */
        this.score += nb;
    }

    public int nbPointsChevalet(int[] nbPointsJet){
        /* pré-requis : nbPointsJet indique le nombre de points rapportés par chaque jeton/lettre
           résultat : le nombre de points total sur le chevalet de ce joueur */
       return this.chevalet.sommeValeurs(nbPointsJet);
       // La fonction "sommeValeurs" appartient à la classe MEE
    }

    public void prendJetons(MEE s, int nbJetons){
        /* pré-requis : les éléments de s sont inférieurs à 26
           action : simule la prise de nbJetons par this dans le sac s, dans la limite de son contenu */
        s.transfereAleat(this.chevalet, nbJetons);
    }

    public int joue(Plateau p, MEE s, int[] nbPointsJet){
        /* pré-requis : 
    }
}
