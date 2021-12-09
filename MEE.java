public class MEE {
    private int[] tabFreq;  // tabFreq[i] est le nombre d'exemplaires (fréquence) de l'élément i.
    private int nbTotEx;    // nombre total d'exemplaires

    public MEE(int max){
        /* pré-requis : max >= 0
           action : crée un multi-ensemble vide dont les éléments seront inférieurs à max */
        this.nbTotEx = max;
    }

    public MEE(int[] tab){
        /* pré-requis : les éléments de tab sont positifs ou nuls
           action : crée un multi-ensemble dont le tableau de fréquences est une copie de tab */
           this.tabFreq = tab;
    }

    public MEE(MEE e){
        /* constructeur par copie */
        // À COMPLÉTER
    }

    public boolean estVide(){
        /* résultat : vrai ssi cet ensemble est vide */
        return this.tabFreq.length == 0;
    }

    public void ajoute(int i){
        /* pré-requis : ° <= i < tabFreq.length
           action : ajoute un exemplaire de i à this */
        // À COMPLÉTER
    }
}
