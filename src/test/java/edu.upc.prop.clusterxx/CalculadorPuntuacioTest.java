package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculadorPuntuacioTest {
    
    private Taulell taulellPetit;   // Taulell 5x5
    private Taulell taulellMitja;   // Taulell 11x11
    private Taulell taulellGran;    // Taulell 15x15
    
    @Before
    public void inicialitzar() {
        taulellPetit = new Taulell(5);
        taulellMitja = new Taulell(11);
        taulellGran = new Taulell(15);
    }

    @Test
    public void testPuntuacioSenseMultiplicadors() {
        // Creem una casella amb una fitxa sense multiplicadors
        Casella c1 = new Casella(2, 2, 5); 
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c1.calcularPunts()).thenReturn(1);
        c1.colocarFitxa(new Fitxa("A", 1));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(c1), true, taulellPetit);
        assertEquals("Puntuació incorrecta per una fitxa sense multiplicadors", 1, punts);
    }

    @Test
    public void testPuntuacioAmbMultiplicadorLletra() {
        // Creem una casella amb un multiplicador de lletra doble
        Casella c1 = new Casella(1, 1, 5);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(2));
        when(c1.calcularPunts()).thenReturn(4);  // Fitxa de 2 punts x2
        c1.colocarFitxa(new Fitxa("E", 2));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(c1), true, taulellPetit);
        assertEquals("La puntuació no aplica correctament el multiplicador de lletra", 4, punts);
    }

    @Test
    public void testPuntuacioAmbMultiplicadorParaula() {
        // Creem una casella amb un multiplicador de paraula triple
        Casella c1 = new Casella(0, 0, 5);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(3));
        when(c1.calcularPunts()).thenReturn(2);  // Valor original de la fitxa
        c1.colocarFitxa(new Fitxa("D", 2));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(c1), true, taulellPetit);
        assertEquals("La puntuació no aplica correctament el multiplicador de paraula", 6, punts);
    }

    @Test
    public void testPuntuacioMultiplesMultiplicadorsParaula() {
        // Paraula amb dos multiplicadors de paraula 
        Casella c1 = new Casella(0, 0, 11);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(2));
        when(c1.calcularPunts()).thenReturn(1);
        c1.colocarFitxa(new Fitxa("C", 1));

        Casella c2 = new Casella(0, 1, 11);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c2.calcularPunts()).thenReturn(1);
        c2.colocarFitxa(new Fitxa("A", 1));

        Casella c3 = new Casella(0, 2, 11);
        c3 = spy(c3);
        when(c3.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(3));
        when(c3.calcularPunts()).thenReturn(3);
        c3.colocarFitxa(new Fitxa("S", 3));

        // (1+1+3) * 2 * 3 = 30
        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(Arrays.asList(c1, c2, c3), true, taulellMitja);
        assertEquals("No s'han aplicat correctament múltiples multiplicadors de paraula", 30, punts);
    }

    @Test
    public void testPuntuacioAmbMultiplicadorsLletraIParaula() {
        // Casella amb DL (doble lletra)
        Casella c1 = new Casella(0, 0, 5);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(2));
        when(c1.calcularPunts()).thenReturn(8);  // 4 punts x2
        c1.colocarFitxa(new Fitxa("H", 4));

        // Casella amb TW (triple paraula)
        Casella c2 = new Casella(0, 1, 5);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(3));
        when(c2.calcularPunts()).thenReturn(1);
        c2.colocarFitxa(new Fitxa("O", 1));

        // Casella normal
        Casella c3 = new Casella(0, 2, 5);
        c3 = spy(c3);
        when(c3.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c3.calcularPunts()).thenReturn(1);
        c3.colocarFitxa(new Fitxa("L", 1));

        // (8+1+1) * 3 = 30
        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(Arrays.asList(c1, c2, c3), true, taulellPetit);
        assertEquals("No s'han aplicat correctament els multiplicadors de lletra i paraula", 30, punts);
    }

    @Test
    public void testPuntuacioParaulaPrincipalIPerpendicular() {
        // Configurem taulell amb una paraula existent "AT"
        // Primer, configurem les fitxes al taulell
        Fitxa fitxaA = new Fitxa("A", 1);
        taulellGran.colocarFitxa(fitxaA, 7, 7);
        taulellGran.getTaulell()[7][7] = spy(taulellGran.getTaulell()[7][7]);
        when(taulellGran.getTaulell()[7][7].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaT = new Fitxa("T", 1);
        taulellGran.colocarFitxa(fitxaT, 7, 8);
        taulellGran.getTaulell()[7][8] = spy(taulellGran.getTaulell()[7][8]);
        when(taulellGran.getTaulell()[7][8].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][8].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());

        // Afegim nova fitxa "E" per formar "ATE" horitzontalment i "E" verticalment
        Casella nova = new Casella(7, 9, 15);
        nova = spy(nova);
        when(nova.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(nova.calcularPunts()).thenReturn(1);
        nova.colocarFitxa(new Fitxa("E", 1));
        
        // També la posem al taulell per assegurar-nos
        taulellGran.getTaulell()[7][9] = spy(taulellGran.getTaulell()[7][9]);
        when(taulellGran.getTaulell()[7][9].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][9].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(taulellGran.getTaulell()[7][9].esBuida()).thenReturn(false);  // Important: la casella no està buida
        
        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(nova), true, taulellGran);
        // Paraula principal "ATE" = 1+1+1 = 3
        // No hi ha paraula perpendicular completa (només "E")
        assertEquals("No s'ha calculat correctament la puntuació de la paraula principal", 3, punts);
    }

    @Test
    public void testPuntuacioAmbParaulaPerpendicularCompleta() {
        // Configurem taulell amb diverses fitxes ja col·locades
        Fitxa fitxaC = new Fitxa("C", 3);
        taulellGran.colocarFitxa(fitxaC, 5, 7);
        taulellGran.getTaulell()[5][7] = spy(taulellGran.getTaulell()[5][7]);
        when(taulellGran.getTaulell()[5][7].calcularPunts()).thenReturn(3);
        when(taulellGran.getTaulell()[5][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaA = new Fitxa("A", 1);
        taulellGran.colocarFitxa(fitxaA, 6, 7);
        taulellGran.getTaulell()[6][7] = spy(taulellGran.getTaulell()[6][7]);
        when(taulellGran.getTaulell()[6][7].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[6][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaT = new Fitxa("T", 1);
        taulellGran.colocarFitxa(fitxaT, 7, 6);
        taulellGran.getTaulell()[7][6] = spy(taulellGran.getTaulell()[7][6]);
        when(taulellGran.getTaulell()[7][6].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][6].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());

        // Afegim nova fitxa "S" per formar "CAT" verticalment i "TS" horitzontalment
        Casella nova = new Casella(7, 7, 15);
        nova = spy(nova);
        when(nova.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(nova.calcularPunts()).thenReturn(1);
        nova.colocarFitxa(new Fitxa("S", 1));
        
        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(nova), false, taulellGran);
        // Paraula principal vertical "CAT" = 3+1+1 = 5
        // Paraula perpendicular "TS" = 1+1 = 2
        assertEquals("No s'ha calculat correctament la puntuació amb paraula perpendicular", 7, punts);
    }

    @Test
    public void testPuntuacioAmbMultiplesParaulesPerpendiculars() {
        // Configurem el taulell amb una estructura existent
        Fitxa fitxaC = new Fitxa("C", 3);
        taulellGran.colocarFitxa(fitxaC, 5, 6);
        taulellGran.getTaulell()[5][6] = spy(taulellGran.getTaulell()[5][6]);
        when(taulellGran.getTaulell()[5][6].calcularPunts()).thenReturn(3);
        when(taulellGran.getTaulell()[5][6].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaA = new Fitxa("A", 1);
        taulellGran.colocarFitxa(fitxaA, 5, 8);
        taulellGran.getTaulell()[5][8] = spy(taulellGran.getTaulell()[5][8]);
        when(taulellGran.getTaulell()[5][8].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[5][8].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaS = new Fitxa("S", 1);
        taulellGran.colocarFitxa(fitxaS, 6, 6);
        taulellGran.getTaulell()[6][6] = spy(taulellGran.getTaulell()[6][6]);
        when(taulellGran.getTaulell()[6][6].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[6][6].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaO = new Fitxa("O", 1);
        taulellGran.colocarFitxa(fitxaO, 6, 8);
        taulellGran.getTaulell()[6][8] = spy(taulellGran.getTaulell()[6][8]);
        when(taulellGran.getTaulell()[6][8].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[6][8].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());

        // Juguem tres fitxes "P", "T", "L" per formar "PTL" verticalment i paraules perpendiculars
        Casella c1 = new Casella(5, 7, 15);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c1.calcularPunts()).thenReturn(3);
        c1.colocarFitxa(new Fitxa("P", 3));
        
        taulellGran.getTaulell()[5][7] = spy(taulellGran.getTaulell()[5][7]);
        when(taulellGran.getTaulell()[5][7].calcularPunts()).thenReturn(3);
        when(taulellGran.getTaulell()[5][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(taulellGran.getTaulell()[5][7].esBuida()).thenReturn(false);

        Casella c2 = new Casella(6, 7, 15);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c2.calcularPunts()).thenReturn(1);
        c2.colocarFitxa(new Fitxa("T", 1));
        
        taulellGran.getTaulell()[6][7] = spy(taulellGran.getTaulell()[6][7]);
        when(taulellGran.getTaulell()[6][7].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[6][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(taulellGran.getTaulell()[6][7].esBuida()).thenReturn(false);

        Casella c3 = new Casella(7, 7, 15);
        c3 = spy(c3);
        when(c3.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c3.calcularPunts()).thenReturn(1);
        c3.colocarFitxa(new Fitxa("L", 1));
        
        taulellGran.getTaulell()[7][7] = spy(taulellGran.getTaulell()[7][7]);
        when(taulellGran.getTaulell()[7][7].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(taulellGran.getTaulell()[7][7].esBuida()).thenReturn(false);

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(Arrays.asList(c1, c2, c3), false, taulellGran);
        System.out.println("Punts: " + punts);
        // Paraula principal vertical: "PTL" = 3+1+1 = 5
        // Paraules perpendiculars: "CP" = 3+3 = 6, "AT" = 1+1 = 2, "LO" = 1+1 = 2
        // Total = 5 + 6 + 2 + 2 = 15
        assertEquals("No s'han calculat correctament múltiples paraules perpendiculars", 15, punts);
    }

    @Test
    public void testPuntuacioAmbMultiplicadorsEnParaulaPrincipalIPerpendicular() {
        // Configurem el taulell amb la paraula "AT" horitzontal
        Fitxa fitxaA = new Fitxa("A", 1);
        taulellGran.colocarFitxa(fitxaA, 7, 7);
        taulellGran.getTaulell()[7][7] = spy(taulellGran.getTaulell()[7][7]);
        when(taulellGran.getTaulell()[7][7].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][7].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        
        Fitxa fitxaT = new Fitxa("T", 1);
        taulellGran.colocarFitxa(fitxaT, 7, 8);
        taulellGran.getTaulell()[7][8] = spy(taulellGran.getTaulell()[7][8]);
        when(taulellGran.getTaulell()[7][8].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[7][8].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());

        // Afegim nova fitxa "E" amb multiplicador triple de lletra
        Casella nova = new Casella(7, 9, 15);
        nova = spy(nova);
        when(nova.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(3));
        when(nova.calcularPunts()).thenReturn(3);  // 1 punt x3
        nova.colocarFitxa(new Fitxa("E", 1));
        
        taulellGran.getTaulell()[7][9] = spy(taulellGran.getTaulell()[7][9]);
        when(taulellGran.getTaulell()[7][9].calcularPunts()).thenReturn(3);
        when(taulellGran.getTaulell()[7][9].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(3));
        when(taulellGran.getTaulell()[7][9].esBuida()).thenReturn(false);

        // Primer calculem una jugada
        int punts1 = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(nova), true, taulellGran);
        // Paraula principal "ATE" = 1+1+3 = 5

        // Afegim una fitxa "R" damunt de la "E" per formar "RE" verticalment
        Casella nova2 = new Casella(6, 9, 15);
        nova2 = spy(nova2);
        when(nova2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(2));
        when(nova2.calcularPunts()).thenReturn(1);
        nova2.colocarFitxa(new Fitxa("R", 1));
        
        taulellGran.getTaulell()[6][9] = spy(taulellGran.getTaulell()[6][9]);
        when(taulellGran.getTaulell()[6][9].calcularPunts()).thenReturn(1);
        when(taulellGran.getTaulell()[6][9].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(2));
        when(taulellGran.getTaulell()[6][9].esBuida()).thenReturn(false);

        // Actualitzem la fitxa E després d'haver-la jugat (ja no té multiplicador actiu)
        when(taulellGran.getTaulell()[7][9].obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(taulellGran.getTaulell()[7][9].calcularPunts()).thenReturn(1);  // Ara retorna el valor normal

        // Calculem la segona jugada
        int punts2 = CalculadorPuntuacio.calcularPuntuacioJugada(List.of(nova2), false, taulellGran);
        // Paraula principal vertical "RE" = 1+1 (sense aplicar el multiplicador de lletra ja usat) = 2 * 2 = 4

        assertEquals("No s'ha calculat correctament la paraula amb multiplicador de lletra", 5, punts1);
        assertEquals("No s'ha calculat correctament la paraula perpendicular amb multiplicador de paraula", 4, punts2);
    }

    @Test
    public void testPuntuacioTaulellPetit() {
        // Test específic per taulell petit (5x5)
        // Mig del taulell és (2,2)
        
        // Configurem una jugada al centre del taulell
        Casella c1 = new Casella(2, 0, 5);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c1.calcularPunts()).thenReturn(3);
        c1.colocarFitxa(new Fitxa("P", 3));

        Casella c2 = new Casella(2, 1, 5);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorLletra(2));
        when(c2.calcularPunts()).thenReturn(2);  // 1 punt x2
        c2.colocarFitxa(new Fitxa("I", 1));

        Casella c3 = new Casella(2, 2, 5);
        c3 = spy(c3);
        when(c3.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaMultiplicadorParaula(2));
        when(c3.calcularPunts()).thenReturn(1);
        c3.colocarFitxa(new Fitxa("L", 1));

        // (3+2+1) * 2 = 12
        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(Arrays.asList(c1, c2, c3), true, taulellPetit);
        assertEquals("No s'han calculat correctament els punts en un taulell petit", 12, punts);
    }

    @Test
    public void testPuntuacioTaulellMitja() {
        // Test específic per taulell mitjà (11x11)
        // Mig del taulell és (5,5)
        
        Casella c1 = new Casella(5, 4, 11);
        c1 = spy(c1);
        when(c1.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c1.calcularPunts()).thenReturn(3);
        c1.colocarFitxa(new Fitxa("S", 3));

        Casella c2 = new Casella(5, 5, 11);
        c2 = spy(c2);
        when(c2.obtenirEstrategia()).thenReturn(new EstrategiaPuntuacio.EstrategiaNormal());
        when(c2.calcularPunts()).thenReturn(1);
        c2.colocarFitxa(new Fitxa("I", 1));

        int punts = CalculadorPuntuacio.calcularPuntuacioJugada(Arrays.asList(c1, c2), true, taulellMitja);
        assertEquals("No s'han calculat correctament els punts en un taulell mitjà", 4, punts);
    }
}