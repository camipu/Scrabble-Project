package edu.upc.prop.clusterxx;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CasellaTest {

    private Casella casellaNormal;
    private Casella casellaMultiplicadorLletra;
    private Casella casellaMultiplicadorParaula;
    private Fitxa fitxaA;
    private Fitxa fitxaB;

    @Before
    public void setUp() {
        // Inicialitzar les fitxes per als tests
        fitxaA = new Fitxa("A", 1);
        fitxaB = new Fitxa("B", 3);

        // Inicialitzar les caselles amb diferents estratègies
        casellaNormal = new Casella(0, 0, new EstrategiaNormal());
        casellaMultiplicadorLletra = new Casella(1, 1, new EstrategiaMultiplicadorLletra(2)); // Multiplicador de lletra per 2
        casellaMultiplicadorParaula = new Casella(2, 2, new EstrategiaMultiplicadorParaula(3)); // Multiplicador de paraula per 3
    }

    @Test
    public void testCalcularPuntsCasellaNormal() {
        // Col·locar la fitxa "A" a la casella normal
        casellaNormal.colocarFitxa(fitxaA);
        assertEquals("Els punts de la casella normal han de ser 1", 1, casellaNormal.calcularPunts());
    }

    @Test
    public void testCalcularPuntsMultiplicadorLletra() {
        // Col·locar la fitxa "B" a la casella amb multiplicador de lletra
        casellaMultiplicadorLletra.colocarFitxa(fitxaB);
        assertEquals("Els punts de la casella amb multiplicador de lletra han de ser 6", 6, casellaMultiplicadorLletra.calcularPunts());
    }

    @Test
    public void testCalcularPuntsMultiplicadorParaula() {
        // Col·locar la fitxa "A" a la casella amb multiplicador de paraula
        casellaMultiplicadorParaula.colocarFitxa(fitxaA);
        assertEquals("Els punts de la casella amb multiplicador de paraula han de ser 1", 1, casellaMultiplicadorParaula.calcularPunts());
    }

    @Test
    public void testMultiplicadorParaula() {
        // Comprovem que una casella amb multiplicador de paraula retorna el multiplicador correcte
        assertEquals("El multiplicador de paraula ha de ser 3", 3, casellaMultiplicadorParaula.obtenirMultiplicador());
    }

    @Test
    public void testMultiplicadorLletra() {
        // Comprovem que una casella amb multiplicador de lletra retorna el multiplicador correcte
        assertEquals("El multiplicador de lletra ha de ser 2", 2, casellaMultiplicadorLletra.obtenirMultiplicador());
    }

    @Test
    public void testCasellaBuida() {
        // Comprovem que una casella buida retorna 0 punts
        Casella casellaBuida = new Casella(3, 3, new EstrategiaNormal());
        assertTrue("La casella ha d'estar buida", casellaBuida.esBuida());
        assertEquals("Els punts de la casella buida han de ser 0", 0, casellaBuida.calcularPunts());
    }

    @Test
    public void testColocarFitxa() {
        // Col·locar una fitxa a una casella buida
        assertTrue("S'ha de poder col·locar una fitxa en una casella buida", casellaNormal.colocarFitxa(fitxaA));
    }

    @Test
    public void testNoColocarFitxaSiJaEstàOcupada() {
        // Intentar col·locar una fitxa en una casella ja ocupada
        casellaNormal.colocarFitxa(fitxaA);
        assertFalse("No s'ha de poder col·locar una fitxa en una casella ja ocupada", casellaNormal.colocarFitxa(fitxaB));
    }

    @Test
    public void testCasellaJugada() {
        // Comprovem si la casella es marca com a jugada
        casellaNormal.jugarCasella();
        assertTrue("La casella hauria de ser marcada com a jugada", casellaNormal.casellaJugada());
    }

    @Test
    public void testCasellaNoJugadaInicialment() {
        // Comprovem que la casella inicialment no ha estat jugada
        assertFalse("La casella no hauria de ser jugada inicialment", casellaNormal.casellaJugada());
    }
}
