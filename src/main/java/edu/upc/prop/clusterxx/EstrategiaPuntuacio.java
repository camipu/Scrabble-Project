package edu.upc.prop.clusterxx;

// Interfície Estratègia per calcular els punts
public interface EstrategiaPuntuacio {
    int calcularPunts(Fitxa fitxa);
    int obtenirMultiplicador();
    boolean esMultiplicadorParaula();

    /**
     * Estratègia normal (sense multiplicadors)
     */
    class EstrategiaNormal implements EstrategiaPuntuacio {
        @Override
        public int calcularPunts(Fitxa fitxa) {
            return fitxa != null ? fitxa.obtenirPunts() : 0;
        }

        public int obtenirMultiplicador() {
            return 1;
        }

        public boolean esMultiplicadorParaula() { return false; }
    }

    /**
     * Multiplicador de lletra (ex: DL, TL)
     */
    class EstrategiaMultiplicadorLletra implements EstrategiaPuntuacio {
        private final int multiplicador;

        public EstrategiaMultiplicadorLletra(int multiplicador) {
            this.multiplicador = multiplicador;
        }

        @Override
        public int calcularPunts(Fitxa fitxa) {
            return fitxa != null ? fitxa.obtenirPunts() * multiplicador : 0;
        }

        public int obtenirMultiplicador() {
            return multiplicador;
        }

        public boolean esMultiplicadorParaula() { return false; }
    }

    /**
     * Multiplicador de paraula (ex: DW, TW)
     */
    class EstrategiaMultiplicadorParaula implements EstrategiaPuntuacio {
        private final int multiplicador;

        public EstrategiaMultiplicadorParaula(int multiplicador) {
            this.multiplicador = multiplicador;
        }

        @Override
        public int calcularPunts(Fitxa fitxa) {
            return fitxa != null ? fitxa.obtenirPunts() : 0;
        }

        public int obtenirMultiplicador() {
            return multiplicador;
        }

        public boolean esMultiplicadorParaula() { return true; }
    }
}