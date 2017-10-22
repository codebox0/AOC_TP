package Metier;

import Metier.Algorithmes.AlgoDiffusion;
import Metier.Algorithmes.DiffusionAtomique;
import Metier.Algorithmes.DiffusionSequentielle;

import java.util.LinkedList;
import java.util.List;

public class GenerateurImpl implements Generateur {

    private AlgoDiffusion algo;

    private Integer value;

    // Liste des canaux (Observeur) Observe la modification du générateur
    private List<ObservateurGenerateur> canauxObservers = new LinkedList<>();

    public GenerateurImpl(AlgoDiffusion algo){
        this.algo = algo;
    }

    @Override
    public void attach(ObservateurGenerateur o) {
        this.canauxObservers.add(o);
    }

    @Override
    public void detach(ObservateurGenerateur o) {
        this.canauxObservers.remove(o);
    }

    // Retourne une copie de la liste des cannaux
    public LinkedList<ObservateurGenerateur> getCanauxObservers(){ return new LinkedList<>(this.canauxObservers); }

    @Override
    public int getValue(ObservateurGenerateur oCanal) {

        // Diffusion atomique on supprime le canaux qui a lu
        if(algo instanceof DiffusionAtomique){
            ((DiffusionAtomique) algo).removeCanaux(oCanal);
            return this.value;
        }

        // Diffusion séquentielle on supprime le canaux qui a lu et on recupere la copie de la valeur
        else if(algo instanceof DiffusionSequentielle){
            ((DiffusionSequentielle) algo).removeCanaux(oCanal);
            return ((DiffusionSequentielle) algo).getCopyValue();
        }

        // Diffusion Epoque
        else {
            return this.value;
        }
    }

    public int getValue(){
        return this.value;
    }

    // Simule le changement de valeur
    public void setValue(int value){
        this.value = value;
        algo.execute();
    }

    public void start(){
        algo.configure(this);
        for(int i = 0 ; i < 10 ; i++){
            // DiffusionAtomique - Set value que si tout le monde a lu
            if(algo instanceof DiffusionAtomique && ((DiffusionAtomique) algo).allReading()){
                this.setValue(i);
            }

            // DiffusionSequentielle - On set la value tout le temps
            if(algo instanceof DiffusionSequentielle){
                this.setValue(i);
            }
        }
    }

    public void stop(){}
}
