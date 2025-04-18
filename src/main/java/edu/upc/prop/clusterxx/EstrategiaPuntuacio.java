package edu.upc.prop.clusterxx;

/**
 * Interfície que defineix una estratègia de puntuació per a una casella del tauler.
 * Les estratègies poden aplicar multiplicadors a lletres o paraules senceres,
 * segons el tipus de casella.
 */
public interface EstrategiaPuntuacio {

    /**
     * Calcula els punts aportats per una fitxa segons l’estratègia de puntuació.
     *
     * @param fitxa Fitxa per a la qual es vol calcular la puntuació
     * @return Puntuació resultant d’aplicar l’estratègia
     */
    int calcularPunts(Fitxa fitxa);

    /**
     * Retorna el valor del multiplicador associat a l’estratègia.
     *
     * @return Valor del multiplicador (per a lletra o paraula)
     */
    int obtenirMultiplicador();

    /**
     * Indica si l’estratègia és de multiplicador de paraula.
     *
     * @return {@code true} si és una estratègia de paraula, {@code false} si és de lletra
     */
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
            if (multiplicador <= 0) {
                throw new IllegalArgumentException("El multiplicador ha de ser positiu.");
            }
            else this.multiplicador = multiplicador;
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
            if (multiplicador <= 0) {
                throw new IllegalArgumentException("El multiplicador ha de ser positiu.");
            }
            else this.multiplicador = multiplicador;
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