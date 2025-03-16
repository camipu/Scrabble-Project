package edu.upc.prop.clusterxx;

// Estratègia per a una casella normal
public class EstrategiaNormal implements EstrategiaPuntuacio {
    @Override
    public int calcularPunts(Fitxa fitxa) {
        return fitxa != null ? fitxa.getPunts() : 0;
    }

    public int getMultiplicador() {
        return 1;
    }
}
