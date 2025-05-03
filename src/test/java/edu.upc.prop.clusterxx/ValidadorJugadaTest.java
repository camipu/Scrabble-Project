// src/test/java/edu/upc/prop/clusterxx/ValidadorJugadaTest.java
package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class ValidadorJugadaTest {

    private Taulell taulell;
    private DAWG dawg;

    @Before
    public void init() {
        taulell = new Taulell(15);
        dawg    = new DAWG(List.of("A","B","C"), List.of("ABC","AB","BC"));
    }

    @Test
    public void primeraJugadaHaDePassarPelCentre() {
        Casella c = new Casella(7,7,15);
        c.colocarFitxa(new Fitxa("A",1));

        assertTrue(ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void primeraJugadaSenseCentre_esInvalida() {
        Casella c = new Casella(0,0,15);
        c.colocarFitxa(new Fitxa("A",1));

        assertFalse(ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void jugadaHaDeTocarFitxaExistent() {
        // Primer posem “A” al centre
        taulell.colocarFitxa(new Fitxa("A",1),7,7);

        // Intent de jugada separada
        Casella c = new Casella(0,0,15);
        c.colocarFitxa(new Fitxa("B",3));

        assertFalse(ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void construirParaulaHoritzontalFunciona() {
        Casella c1 = new Casella(7,6,15); c1.colocarFitxa(new Fitxa("A",1));
        Casella c2 = new Casella(7,7,15); c2.colocarFitxa(new Fitxa("B",3));
        Casella c3 = new Casella(7,8,15); c3.colocarFitxa(new Fitxa("C",3));

        String paraula = ValidadorJugada.construirParaula(
                List.of(c1,c2,c3), true, taulell);

        assertEquals("ABC", paraula);
    }
}
