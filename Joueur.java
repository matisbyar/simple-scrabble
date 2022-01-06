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
        /* pré-requis : les éléments de s sont inférieurs à 26 et nbPointsJet.length >= 26
            action : simule le coup de this : this choisit de passer son tour, d'échanger des jetons ou de placer un mot
            résultat : -1 si this a passé son tour, 1 si son chevalet est vide et 0 sinon */
        // On initialise quelques variables qui nous seront primordiales par la suite.
        boolean verificationPlacementMot;
        int sortie = 0;

        Ut.afficher("Que désirez-vous faire ?\n1. Je passe mon tour \n2.J'échange mes jetons \n3. Je place un mot\n Mon choix : ");
        int choix = Ut.saisirEntier();

        if(choix == 1){
            sortie = -1;        // Le joueur a passé son tour.
        } 
        else if ((choix == 2 && this.chevalet.estVide()) || (choix == 3 && this.chevalet.estVide())){
            sortie = 1;         // Le joueur échange ses jetons mais son chevalet est vide OU il place un mot mais son chevalet est vide
        }
        else if (choix == 2 && !this.chevalet.estVide()){
            this.echangeJetons(s);  // Le joueur échange ses jetons ET son plateau n'est pas vide.
        }
        else if (choix == 3 && !this.chevalet.estVide()){
            verificationPlacementMot = joueMot(p, s, nbPointsJet);        // Le joueur entre le mot qu'il veut jouer.
            while(!verificationPlacementMot){                             // Tant que ce mot est invalide (soit CapeloDico ou placement), il doit recommencer
                verificationPlacementMot = joueMot(p, s, nbPointsJet);    
            }
        }
        return sortie;
    }

    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet){
        /** pré-requis : les éléments de s sont inférieurs à 26
                         et nbPointsJet.length >= 26
            action : simule le placement d’un mot de this :
                    a) le mot, sa position sur le plateau et sa direction, sont saisis au clavier
                    b) vérifie si le mot est valide
                    c) si le coup est valide, le mot est placé sur le plateau
            résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
                       CapeloDico et satisfaisant les règles détaillées plus haut
            stratégie : utilise la méthode joueMotAux */
        String mot;
        int numLig;
        int numCol;
        char sens; 
        char valide;

        Ut.afficher("Saisissez le mot que vous désirez entrer : ");
        mot = Ut.saisirChaine(); 
        Ut.afficher("Saisissez la ligne de placement (de A à Z): ");
        numLig = Ut.indexToMaj(Ut.saisirEntier()) - 1;
        Ut.afficher("Saisissez la colone de placement (de 1 à 15) : ");
        numCol = Ut.saisirEntier() - 1;
        Ut.afficher("Saisissez le sens (h pour horizontal, v pour vertical) : ");
        sens = Ut.saisirCaractere();
        Ut.afficher("CapeloDico procède à la vérification du mot. Le mot " + mot + " est-il valide (y/n)? ");
        valide = Ut.saisirCaractere();
        if(p.placementValide(mot, numLig, numCol, sens, this.chevalet) && valide == 'y'){
            this.joueMotAux(p, s, nbPointsJet, mot, numLig, numCol, sens);
            return true;
        }
        else{
            return false;
        }
    }

    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens){
        /** pré-requis : cf. joueMot et le placement de mot à partir de la case (numLig, numCol) dans le sens donné par sens est valide
            action : simule le placement d'un mot de this */
        int nbTransfert = p.place(mot, numLig, numCol, sens, this.chevalet);
        s.transfereAleat(this.chevalet, nbTransfert);
        this.ajouteScore(p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet));
    }
    
    public void echangeJetons(MEE sac) {
        /** pré-requis : sac peut contenir des entiers de 0 à 25
            action : simule l’échange de jetons de ce joueur :
                - saisie de la suite de lettres du chevalet à échanger en vérifiant que la suite soit correcte
                - échange de jetons entre le chevalet du joueur et le sac
            stratégie : appelle les méthodes estCorrectPourEchange et echangeJetonsAux */
        boolean motIncorrect = true;
        while (motIncorrect) {
            Ut.afficher("Veuillez saisir, en majuscule, les lettres que vous souhaitez afficher :\n");
            String mot = Ut.saisirChaine();
            if (this.estCorrectPourEchange(mot)) {
                this.echangeJetonsAux(sac, mot);
                motIncorrect = false;
            }
        }
    }
        
    public boolean estCorrectPourEchange(String mot) {
        /** résultat : vrai ssi les caractères de mot correspondent tous à des lettres majuscules et l’ensemble de ces caractères est un sous-ensemble des jetons du chevalet de this */
        boolean resultat = true;
        int i = 0;
        MEE copie = new MEE(this.chevalet);
        while (resultat && i < mot.length()) {
            if (Ut.estUneMajuscule(mot.charAt(i))) {
                resultat = copie.retire(mot.charAt(i));
                i++;
            }
            else {
                resultat = false;
            }
        }
        return resultat;
    }
    
    public void echangeJetonsAux(MEE sac, String ensJetons) {
        /** pré-requis : sac peut contenir des entiers de 0 à 25 et ensJetons est un ensemble de jetons correct pour l’échange
            action : simule l’échange de jetons de ensJetons avec des jetons du sac tirés aléatoirement. */
        for (int i = 0; i < ensJetons.length(); i++) {
            this.chevalet.transfere(sac, Ut.majToIndex(ensJetons.charAt(i)));
        }
        sac.transfereAleat(this.chevalet, ensJetons.length());
    }
}
