package Metier;

public class Canal implements ObservateurGenerateur, Generateur{

    // Proxy
    // private Afficheur afficheur;

    // Afficheur
    private ObservateurGenerateur observerAfficheur;

    //Proxy
    private GenerateurImpl generateur;

//    public Canal(Afficheur afficheur){
//        this.afficheur = afficheur;
//    }

    public void update(Generateur generateur){
        System.out.println("Canal update");
        // TODO spécification
        this.generateur = (GenerateurImpl) generateur;

        // TODO appeler la méthode update(subject:Generateur) de la classe Afficheur
        this.observerAfficheur.update(this);
    }

    public int getValue(ObservateurGenerateur oAfficheur){
        return this.generateur.getValue(this);
    }

    public void attach(ObservateurGenerateur o){
        this.observerAfficheur = o;
    }

    public void detach(ObservateurGenerateur o){
        this.observerAfficheur = null;
    }
}