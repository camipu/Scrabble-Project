//package edu.upc.prop.clusterxx;
//
//import edu.upc.prop.clusterxx.exceptions.ExcepcioSacBuit;
//import edu.upc.prop.clusterxx.exceptions.ExcepcioSacNoConteLaFitxa;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
//public class SacTest {
//    private Sac sac;
//    private Fitxa fitxaA;
//    private Fitxa fitxaA2;
//    private Fitxa fitxaB;
//    private Fitxa fitxaC;
//
//    @Before
//    public void setUp() {
//        sac = new Sac();  // Inicialitza un sac buit
//        fitxaA = new Fitxa("A", 1);
//        fitxaA2 = new Fitxa("A", 1);
//        fitxaB = new Fitxa("B", 3);
//        fitxaC = new Fitxa("C", 1);
//    }
//
//    @Test
//    public void testObtenirNumFitxesDelSac() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaB);
//
//        assertEquals(2, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testObtenirNumFitxesEspecifica() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaA2);
//        sac.afegirFitxa(fitxaB);
//
//        assertEquals(2, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(1, sac.obtenirNumFitxes(fitxaB));
//    }
//
//    @Test
//    public void testAfegirUnaFitxa() {
//        sac.afegirFitxa(fitxaA);
//        assertEquals(1, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(1, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testAfegirFitxaRepetida() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaA2);
//        assertEquals(2, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(2, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testAfegirDiversesFitxes() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaA2);
//        sac.afegirFitxa(fitxaB);
//        sac.afegirFitxa(fitxaC);
//
//        assertEquals(2, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(1, sac.obtenirNumFitxes(fitxaB));
//        assertEquals(1, sac.obtenirNumFitxes(fitxaC));
//        assertEquals(4, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testQuantitatZeroSiNoExisteix() {
//        assertEquals(0, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(0, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testAgafarFitxaRedueixQuantitat() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaA2);
//        sac.afegirFitxa(fitxaB);
//        assertEquals(2, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(3, sac.obtenirNumFitxes());
//
//        sac.agafarFitxa(fitxaA2);
//        assertEquals(1, sac.obtenirNumFitxes(fitxaA));
//        assertEquals(2, sac.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testAgafarFitxaAleatoria() {
//        sac.afegirFitxa(fitxaA);
//        sac.afegirFitxa(fitxaB);
//
//        Fitxa fitxaSeleccionada = sac.agafarFitxa();
//
//        assertTrue(fitxaSeleccionada.equals(fitxaA) || fitxaSeleccionada.equals(fitxaB));
//    }
//
//    @Test
//    public void testSacBuit() {
//        sac.afegirFitxa(fitxaA);
//        assertEquals(1, sac.obtenirNumFitxes(fitxaA));
//        sac.agafarFitxa(fitxaA);
//
//        assertTrue(sac.esBuit());
//    }
//
//    @Test
//    public void testEsBuitInicialment() {
//        assertTrue(sac.esBuit());
//    }
//
//    // Proves afegides per a excepcions i errors
//
//    @Test(expected = ExcepcioSacBuit.class)
//    public void testAgafarFitxaSacBuit() {
//        sac.agafarFitxa();  // Ha de llançar una ExcepcioSacBuit
//    }
//
//    @Test(expected = ExcepcioSacNoConteLaFitxa.class)
//    public void testAgafarFitxaNoDisponible() {
//        sac.agafarFitxa(fitxaA);  // Ha de llançar una ExcepcioSacNoConteLaFitxa
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testInicialitzacioSacFormatIncorrecte() throws IOException {
//        String arxiuErroni = "eslovas  co"; // Aquest format és incorrecte
//        Sac sac = new Sac(arxiuErroni);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testInicialitzacioSacIdiomaNoExistent() throws IOException {
//        String arxiuErroni = "eslovasco"; // Aquest format és incorrecte
//        Sac sac = new Sac(arxiuErroni);
//    }
//}
