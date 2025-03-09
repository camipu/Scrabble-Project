//Classe Sac
import java.util.*;

public class Sac{
    private Map<Fitxa, Integer> fitxes;

    public Sac(){
        this.fitxes = new HashMap<>();
    }

    public Sac(Map<Fitxa, Integer> fitxesini){
        this.fitxes = new HashMap<>(fitxesini);
    }

    public void inicialitzarSac(){
        //Aqui s'inicialitzarà el sac amb les fitxes que toquin i tal
    }

    public Fitxa agafarFitxa(){
        if(esBuit()) return null;

        List<Fitxa> disponibles = new ArrayList<>();
        for(Fitxa fitxa: fitxes.keySet()){
            int quant = fitxes.get(fitxa);
            for(int i = 0; i<quant; i++){
                disponibles.add(fitxa);
            }
        }
        Fitxa seleccionada = disponibles.get(random.nextInt(disponibles.size()));

        fitxes.put(seleccionada, fitxes.get(seleccionada)-1);
        if(fitxes.get(seleccionada)==0){
            fitxes.remove(seleccionada);
        }
        return seleccionada;
    }

    public void afegirFitxa(Fitxa f){
        fitxes.put(f, fitxes.get(f)+1);
    }

    public int getNumFitxes(){
        int total = 0;
        for(int q: fitxes.values()){
            total += q;
        }
        return total;
    }
    
    public boolean esBuit(){
        return fitxes.isEmpty();
    }

}