// src/test/java/edu/upc/prop/clusterxx/CalculadorPuntuacioTest.java
package edu.upc.prop.clusterxx;

import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculadorPuntuacioTest {

    @Test
    public void puntuacioSenseMultiplicadors() {
        Taulell t = new Taulell(15);
        Casella c1 = new Casella(7,7,15); c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        c1.colocarFitxa(new Fitxa("A",1));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(c1), true, t);
        assertEquals(1, punts);
    }

    @Test
    public void puntuacioAmbTripleParaula() {
        Taulell t = new Taulell(15);
        Casella c = t.getCasella(0,0);      // (0,0) és TW a la implementació
        c.colocarFitxa(new Fitxa("D",2));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(c), true, t);
        assertEquals(2*3, punts);           // 2 punts * 3 (paraula)
    }

    @Test
    public void sumaParaulaPrincipalIPerpendicular() {
        Taulell t = new Taulell(15);

        // Paraula existent “AT”
        t.colocarFitxa(new Fitxa("A",1),7,7);
        t.colocarFitxa(new Fitxa("T",1),7,8);

        // Nova fitxa “E” que fa “ATE” i perpendicular “TE”
        Casella nova = new Casella(7,9,15); nova = spy(nova);
        when(nova.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        nova.colocarFitxa(new Fitxa("E",1));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(nova), true, t);
        // Paraula principal “ATE” = 1+1+1 = 3
        // Paraula perpendicular “E”+”T”(existent) = 1+1 =2
        assertEquals(15, punts);
    }
}
