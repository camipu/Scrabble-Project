package edu.upc.prop.clusterxx;

// Interfície Estratègia per calcular els punts
public interface EstrategiaPuntuacio {
    int calcularPunts(Fitxa fitxa);
    int getMultiplicador();
}
