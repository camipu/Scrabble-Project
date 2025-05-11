package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests per a la classe ValidadorJugada.
 * Comprova que les jugades compleixin les regles de Scrabble en diferents tamanys de taulell.
 */
public class ValidadorJugadaTest {

    private DAWG dawg;

    @Before
    public void init() {
        // Diccionari amb algunes paraules per a les proves
        List<String> paraulesCurtes = List.of(
            "NY", "L·L", "Ç", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", 
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        );

        List<String> paraules = List.of(
            "AB", "ABCD", "CASA", "MARC", "SAC", "CEBA", "CAFE"
        );
        dawg = new DAWG(paraulesCurtes, paraules);
    }

    @Test
    public void testPositioValida_PrimeraJugadaCentre_TaulellPetit() {
        // Taulell petit de 5x5
        Taulell taulell = new Taulell(5);
        
        // La primera jugada ha de passar pel centre (2,2)
        Casella c = new Casella(2, 2, 5);
        c.colocarFitxa(new Fitxa("A", 1));
        
        assertTrue("La primera jugada al centre hauria de ser vàlida en un taulell petit",
            ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void testPositioValida_PrimeraJugadaSenseCentre_TaulellPetit() {
        // Taulell petit de 5x5
        Taulell taulell = new Taulell(5);
        
        // Intent de primera jugada sense passar pel centre
        Casella c = new Casella(0, 0, 5);
        c.colocarFitxa(new Fitxa("A", 1));
        
        assertFalse("La primera jugada fora del centre hauria de ser invàlida",
            ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void testPositioValida_PrimeraJugadaCentre_TaulellGran() {
        // Taulell gran de 21x21
        Taulell taulell = new Taulell(21);
        
        // La primera jugada ha de passar pel centre (10,10)
        Casella c = new Casella(10, 10, 21);
        c.colocarFitxa(new Fitxa("A", 1));
        
        assertTrue("La primera jugada al centre hauria de ser vàlida en un taulell gran",
            ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void testPositioValida_CasellesNoAlineades() {
        // Taulell estàndard
        Taulell taulell = new Taulell(15);
        
        // Posem una fitxa al centre per evitar la comprovació de primera jugada
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        
        // Caselles no alineades (files i columnes diferents)
        Casella c1 = new Casella(7, 8, 15);
        c1.colocarFitxa(new Fitxa("B", 3));
        Casella c2 = new Casella(8, 7, 15);
        c2.colocarFitxa(new Fitxa("C", 3));
        
        assertFalse("Les caselles no alineades haurien de ser invàlides",
            ValidadorJugada.posicioValida(List.of(c1, c2), taulell));
    }

    @Test
    public void testJugadaHaDeTocarFitxaExistent() {
        // Inicialitzem un taulell amb una fitxa al centre
        Taulell taulell = new Taulell(15);
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);

        // Intent de jugada separada (no adjacent)
        Casella c = new Casella(0, 0, 15);
        c.colocarFitxa(new Fitxa("B", 3));

        assertFalse("Una jugada que no toca cap fitxa existent hauria de ser invàlida",
            ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void testJugadaValidaAdjacent() {
        // Inicialitzem un taulell amb una fitxa al centre
        Taulell taulell = new Taulell(15);
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);

        // Jugada adjacent
        Casella c = new Casella(7, 8, 15);
        c.colocarFitxa(new Fitxa("B", 3));

        assertTrue("Una jugada adjacent a una fitxa existent hauria de ser vàlida",
            ValidadorJugada.posicioValida(List.of(c), taulell));
    }

    @Test
    public void testConstruirParaulaHoritzontal() {
        Taulell taulell = new Taulell(15);
        
        Casella c1 = new Casella(7, 6, 15);
        c1.colocarFitxa(new Fitxa("A", 1));
        Casella c2 = new Casella(7, 7, 15);
        c2.colocarFitxa(new Fitxa("B", 3));
        Casella c3 = new Casella(7, 8, 15);
        c3.colocarFitxa(new Fitxa("C", 3));

        String paraula = ValidadorJugada.construirParaula(
                List.of(c1, c2, c3), true, taulell);

        assertEquals("Hauria de construir correctament la paraula horitzontal",
                "ABC", paraula);
    }

    @Test
    public void testConstruirParaulaVertical() {
        Taulell taulell = new Taulell(15);
        
        Casella c1 = new Casella(6, 7, 15);
        c1.colocarFitxa(new Fitxa("A", 1));
        Casella c2 = new Casella(7, 7, 15);
        c2.colocarFitxa(new Fitxa("B", 3));
        Casella c3 = new Casella(8, 7, 15);
        c3.colocarFitxa(new Fitxa("C", 3));

        String paraula = ValidadorJugada.construirParaula(
                List.of(c1, c2, c3), false, taulell);

        assertEquals("Hauria de construir correctament la paraula vertical",
                "ABC", paraula);
    }
    
    @Test
    public void testParaulaIncorrecta() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "A" al centre
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        
        // Intentem formar la paraula "AZ" que no està al diccionari
        Casella c = new Casella(7, 8, 15);
        c.colocarFitxa(new Fitxa("Z", 10));
        
        assertFalse("Hauria de rebutjar una paraula que no existeix al diccionari",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
    }
    
    @Test
    public void testParaulaPerpendicularIncorrecta() {
        Taulell taulell = new Taulell(15);
        
        // Creem paraula "AB" horitzontal
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        taulell.colocarFitxa(new Fitxa("B", 3), 7, 8);
        
        // Intentem col·locar "Z" per formar "BZ" vertical (no existeix)
        Casella c = new Casella(8, 8, 15);
        c.colocarFitxa(new Fitxa("Z", 10));
        
        assertFalse("Hauria de rebutjar una jugada que forma una paraula perpendicular incorrecta",
                ValidadorJugada.validarParaulesPerpendiculars(List.of(c), dawg, true, taulell));
    }
    
    @Test
    public void testConnexioDuesParaulesCorrecte() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "AB" i "CD" amb un espai entre elles
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 5);
        taulell.colocarFitxa(new Fitxa("B", 3), 7, 6);
        
        taulell.colocarFitxa(new Fitxa("D", 2), 7, 8);
        
        // Connectem amb "C" per formar "ABCD"
        Casella c = new Casella(7, 7, 15);
        c.colocarFitxa(new Fitxa("C", 3));
        
        assertTrue("Hauria de validar la connexió de dues paraules existents",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
        
        String paraulaResultant = ValidadorJugada.construirParaula(List.of(c), true, taulell);
        assertEquals("ABCD", paraulaResultant);
    }
    
    @Test
    public void testConnexioDuesParaulesIncorrecte() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "AB" i "CD" amb un espai entre elles
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 5);
        taulell.colocarFitxa(new Fitxa("B", 3), 7, 6);
        
        taulell.colocarFitxa(new Fitxa("D", 2), 7, 8);
        
        // Intentem connectar amb "Z" per formar "ABZD" (no existeix)
        Casella c = new Casella(7, 7, 15);
        c.colocarFitxa(new Fitxa("Z", 10));
        
        assertFalse("Hauria de rebutjar la connexió de dues paraules si forma una paraula invàlida",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
    }
    
    @Test
    public void testJugadaFormantDuesParaulesValides() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "CASA" vertical
        taulell.colocarFitxa(new Fitxa("C", 3), 6, 7);
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        taulell.colocarFitxa(new Fitxa("S", 1), 8, 7);
        taulell.colocarFitxa(new Fitxa("A", 1), 9, 7);

        // Col·loquem "MARC" vertical
        taulell.colocarFitxa(new Fitxa("M", 3), 5, 9);
        taulell.colocarFitxa(new Fitxa("A", 1), 6, 9);
        taulell.colocarFitxa(new Fitxa("R", 1), 7, 9);
        taulell.colocarFitxa(new Fitxa("C", 3), 8, 9);
        
        // Col·loquem "A" per formar "SAC"
        Casella c = new Casella(8, 8, 15);
        c.colocarFitxa(new Fitxa("A", 1));

        assertTrue("Hauria de validar una jugada que forma múltiples paraules vàlides",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
    }
    
    @Test
    public void testParaulaAmbCasesPremocades() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "C_BA" amb un espai buit
        taulell.colocarFitxa(new Fitxa("C", 3), 7, 6);
        taulell.colocarFitxa(new Fitxa("B", 3), 7, 8);
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 9);
        
        // Posem "E" per formar "CEBA"
        Casella c = new Casella(7, 7, 15);
        c.colocarFitxa(new Fitxa("E", 1));
        
        assertTrue("Hauria de validar una jugada que omple espais en una paraula existent",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
        
        String paraulaFormada = ValidadorJugada.construirParaula(List.of(c), true, taulell);
        assertEquals("CEBA", paraulaFormada);
    }
    
    @Test
    public void testJugadaAmbAccents() {
        Taulell taulell = new Taulell(15);
        
        // Col·loquem "CAF_" amb un espai buit al final
        taulell.colocarFitxa(new Fitxa("C", 3), 7, 6);
        taulell.colocarFitxa(new Fitxa("A", 1), 7, 7);
        taulell.colocarFitxa(new Fitxa("F", 4), 7, 8);
        
        // Posem "È" per formar "CAFÈ"
        Casella c = new Casella(7, 9, 15);
        c.colocarFitxa(new Fitxa("E", 1));
        
        assertTrue("Hauria de validar una jugada que forma paraules amb accents",
                ValidadorJugada.validarJugada(List.of(c), dawg, taulell));
    }
}