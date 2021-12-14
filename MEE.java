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
        this.tabFreq = e.tabFreq;
        this.nbTotEx = e.nbTotEx;
    }

    public boolean estVide(){
        /* résultat : vrai ssi cet ensemble est vide */
        return (this.tabFreq.length == 0 || this.nbTotEx == 0);
    }

    public void ajoute(int i){
        /* pré-requis : 0 <= i < tabFreq.length
           action : ajoute un exemplaire de i à this */
        this.tabFreq[i]++;
        this.nbTotEx++;
    }
    
    public boolean retire(int i){
        /* pré-requis : 0 <= i < tabFreq.length
           action/résultat : retire un exemplaire de i de this s’il en existe, et retourne vrai ssi cette action a pu être effectuée */
        boolean sortie = false;
        if (this.tabFreq[i] > 0){
            this.tabFreq[i]--;
            this.nbTotEx--;
            sortie = true;
        }
        return sortie;
    }
    
    public int retireAleat(){
        /* pré-requis : this est non vide
           action/résultat : retire de this un exemplaire choisi aléatoirement et le retourne */
        int i = 0;
        int j = 0;
        while (i == 0){
            j = Ut.randomMinMax(0, this.tabFreq.length-1);
            i = this.tabFreq[j];
        }
        this.tabFreq[j]--;
        this.nbTotEx--;
        return i;
    }
    
    public boolean transfere(MEE e, int i){
        /* pré-requis : 0 <= i < tabFreq.length
           action/résultat : transfère un exemplaire de i de this vers e s’il en existe, et retourne vrai ssi cette action a pu être effectuée */
        boolean sortie = false;
        if (this.tabFreq[i] > 0) {
            this.tabFreq[i]--;
            this.nbTotEx--;
            e.tabFreq[i]++;
            e.nbTotEx++;
            sortie = true;
        }
        return sortie;
    }
    
    public int transfereAleat(MEE e, int k){
        /* pré-requis : k >= 0
           action : tranfère k exemplaires choisis aléatoirement de this vers e dans la limite du contenu de this 
           résultat : le nombre d'exemplaires effectivement transférés
           stratégie : utise retraitAleatoire() et ajoute() simultanément. */
        int nbElementsTransferes = 0;
        while(nbElementsTransferes < k && !this.estVide()){
            e.ajoute(this.retireAleat());
            nbElementsTransferes++;
        }
        return nbElementsTransferes;
    }
    
    public int sommeValeurs(int[] v){
        /* pré-requis : tabFreq.length <= v.length
           résultat : retourne la somme des valeurs des exemplaires des éléments de this, la valeur d’un exemplaire d’un élément i de this étant égale à v[i] */
        int somme = 0;
        for (int i = 0; i < this.tabFreq.length; i++){
            somme += this.tabFreq[i] * v[i];
        }
        return somme;
    }
}
