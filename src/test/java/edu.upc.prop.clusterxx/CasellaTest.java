//package edu.upc.prop.clusterxx;
//
//import edu.upc.prop.clusterxx.exceptions.ExcepcioCasellaOcupada;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class CasellaTest {
//
//    private Casella casellaNormal;
//    private Casella casellaMultiplicadorLletra;
//    private Casella casellaMultiplicadorParaula;
//
//    private Fitxa fitxaAMock;
//    private Fitxa fitxaBMock;
//    private Fitxa fitxaCHMock;
//
//    @Before
//    public void setUp() {
//        fitxaAMock = mock(Fitxa.class);
//        fitxaBMock = mock(Fitxa.class);
//        fitxaCHMock = mock(Fitxa.class);
//
//        when(fitxaAMock.obtenirPunts()).thenReturn(1);
//        when(fitxaBMock.obtenirPunts()).thenReturn(3);
//
//        when(fitxaAMock.esDigraf()).thenReturn(false);
//        when(fitxaAMock.toString()).thenReturn("A");
//
//        when(fitxaCHMock.esDigraf()).thenReturn(true);
//        when(fitxaCHMock.toString()).thenReturn("CH");
//
//        casellaNormal = new Casella(0, 0, new EstrategiaNormal());
//        casellaMultiplicadorLletra = new Casella(1, 3, new EstrategiaMultiplicadorLletra(2));
//        casellaMultiplicadorParaula = new Casella(2, 5, new EstrategiaMultiplicadorParaula(3));
//    }
//
//    @Test
//    public void testColocarFitxa() {
//        casellaNormal.colocarFitxa(fitxaAMock);
//        assertEquals(fitxaAMock, casellaNormal.obtenirFitxa());
//    }
//
//    @Test
//    public void testCasellaBuidaInicialment() {
//        assertTrue(casellaNormal.esBuida());
//    }
//
//    @Test
//    public void testObtenirPosicio() {
//        assertEquals(0, casellaNormal.obtenirX());
//        assertEquals(0, casellaNormal.obtenirY());
//
//        assertEquals(1, casellaMultiplicadorLletra.obtenirX());
//        assertEquals(3, casellaMultiplicadorLletra.obtenirY());
//
//        assertEquals(2, casellaMultiplicadorParaula.obtenirX());
//        assertEquals(5, casellaMultiplicadorParaula.obtenirY());
//    }
//
//    @Test
//    public void testObtenirEstrategia() {
//        assertEquals(EstrategiaNormal.class, casellaNormal.obtenirEstrategia().getClass());
//        assertEquals(EstrategiaMultiplicadorLletra.class, casellaMultiplicadorLletra.obtenirEstrategia().getClass());
//        assertEquals(EstrategiaMultiplicadorParaula.class, casellaMultiplicadorParaula.obtenirEstrategia().getClass());
//    }
//
//    @Test
//    public void testCasellaJugada() {
//        assertFalse(casellaNormal.esJugada());
//        casellaNormal.jugarCasella();
//        assertTrue(casellaNormal.esJugada());
//    }
//
//    @Test
//    public void testCalcularPuntsNormal() {
//        casellaNormal.colocarFitxa(fitxaAMock);
//        assertEquals(1, casellaNormal.calcularPunts());
//    }
//
//    @Test
//    public void testCalcularPuntsMultiplicadorLletra() {
//        casellaMultiplicadorLletra.colocarFitxa(fitxaBMock);
//        assertEquals(6, casellaMultiplicadorLletra.calcularPunts());
//    }
//
//    @Test
//    public void testCalcularPuntsMultiplicadorParaula() {
//        casellaMultiplicadorParaula.colocarFitxa(fitxaAMock);
//        assertEquals(1, casellaMultiplicadorParaula.calcularPunts());
//    }
//
//    @Test
//    public void testObtenirMultiplicadorParaula() {
//        assertEquals(1, casellaNormal.obtenirMultiplicador());
//        assertEquals(2, casellaMultiplicadorLletra.obtenirMultiplicador());
//        assertEquals(3, casellaMultiplicadorParaula.obtenirMultiplicador());
//    }
//
//    @Test
//    public void testCalcularPuntsCasellaBuida() {
//        assertTrue(casellaNormal.esBuida());
//        assertEquals(0, casellaNormal.calcularPunts());
//    }
//
//    @Test
//    public void testToStringCasellaBuida() {
//        assertEquals("  ", casellaNormal.toString());
//    }
//
//    @Test
//    public void testToStringCasellaAmbFitxaNoDigraf() {
//        casellaNormal.colocarFitxa(fitxaAMock);
//        assertEquals(" A", casellaNormal.toString());
//    }
//
//    @Test
//    public void testToStringCasellaAmbFitxaDigraf() {
//        casellaNormal.colocarFitxa(fitxaCHMock);
//        assertEquals("CH", casellaNormal.toString());
//    }
//
//    @Test(expected = Exception.class)
//    public void testSobreescriureFitxa() throws ExcepcioCasellaOcupada {
//        casellaNormal.colocarFitxa(fitxaAMock);
//        casellaNormal.colocarFitxa(fitxaBMock);
//        assertEquals(fitxaAMock, casellaNormal.obtenirFitxa());
//    }
//}
