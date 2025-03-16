package edu.upc.prop.clusterxx;

// Estratègia per a multiplicador de lletra
public class EstrategiaMultiplicadorLletra implements EstrategiaPuntuacio {
    private final int multiplicador;

    public EstrategiaMultiplicadorLletra(int multiplicador) {
        this.multiplicador = multiplicador;
    }

    @Override
    public int calcularPunts(Fitxa fitxa) {
        return fitxa != null ? fitxa.getPunts() * multiplicador : 0;
    }

    public int getMultiplicador() {
        return multiplicador;
    }
}
