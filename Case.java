public class Case {
    private int couleur;             // la couleur est un entier entre 1 et 5
    private char lettre;             // si la lettre est recouverte, la variable lettre la contient
    
    public Case(int uneCouleur){
        /* pré-requis : uneCouleur est un entier entre 1 et 5
           action : constructeur de Case */
        this.couleur = uneCouleur;
        this.lettre = ' ';
    }

    public int getCouleur(){
        /* résultat : la couleur de this, un nombre entre 1 et 5 */
        return this.couleur;
    }

    public char getLettre(){
        /* pré-requis : cette case est recouverte */
        // JE PENSE QUE CELA EST FAUX
        return this.lettre;
    }

    public void setLettre(char let){
        /* pré-requis : let est une lettre majuscule */
        this.lettre = let;
    }

    public boolean estRecouverte(){
        /* résultat : vrai ssi la case est recouverte par une lettre */
        return lettre != ' ';
    }

    public String toString(){
        return "La case possède une couleur de valorisation " + this.couleur + " et elle possède cette lettre (aucune si vide) : " + this.lettre + "\"";
    }
}
