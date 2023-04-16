public class Case {
    private int couleur;             // la couleur est un entier entre 1 et 5
    private char lettre;             // si la lettre est recouverte, la variable lettre la contient

    /**
     * @param couleur une couleur est un entier entre 1 et 5
     */
    public Case(int couleur) {
        this.couleur = couleur;
        this.lettre = ' ';
    }

    public int getCouleur() {
        return this.couleur;
    }

    public char getLettre() {
        return this.lettre;
    }

    public void setLettre(char let) {
        this.lettre = let;
    }

    public boolean estRecouverte() {
        return lettre != ' ';
    }

    public String toString() {
        return "La case possède une couleur de valorisation " + this.couleur + " et elle possède cette lettre (aucune si vide) : " + this.lettre + "\"";
    }
}
