package edu.upc.prop.clusterxx;

// Estratègia per a multiplicador de paraula
public class EstrategiaMultiplicadorParaula implements EstrategiaPuntuacio {
    private final int multiplicador;

    public EstrategiaMultiplicadorParaula(int multiplicador) {
        this.multiplicador = multiplicador;
    }

    @Override
    public int calcularPunts(Fitxa fitxa) {
        return fitxa != null ? fitxa.obtenirPunts() : 0; // Es multiplica després en el càlcul global
    }

    public int getMultiplicador() {
        return multiplicador;
    }
}
