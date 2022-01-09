public class MainScrabble {
    public static void main(String [] args){
        
        Ut.afficher("Combien de joueurs êtes-vous ? ");
        int nbJoueurs = Ut.saisirEntier();
        while((nbJoueurs < 1 && nbJoueurs > 14)){
            Ut.afficher("Le nombre minimum de joueurs est de 1 et maximum, de 14. Combien êtes-vous ? ");
            nbJoueurs = Ut.saisirEntier();
        }
        String[] joueurs = new String[nbJoueurs];
        for(int i = 0; i < nbJoueurs; i++){
            Ut.afficher("Saisissez le nom du joueur " + (i+1) + " : ");
            joueurs[i] = Ut.saisirChaine();
        }

        Scrabble scrabble = new Scrabble(joueurs);

        scrabble.partie();
    }
}
