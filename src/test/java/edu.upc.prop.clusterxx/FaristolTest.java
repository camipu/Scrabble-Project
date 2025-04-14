package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolNoConteLaFitxa;
import edu.upc.prop.clusterxx.exceptions.ExcepcioFaristolPle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class FaristolTest {

    private Faristol faristol;
    private Sac sacMock;
    private Fitxa fitxaMockA;
    private Fitxa fitxaMockB;
    private Fitxa fitxaMockC;
    private Fitxa fitxaMockD;
    private Fitxa fitxaMockE;
    private Fitxa fitxaMockF;
    private Fitxa fitxaMockG;
    private Fitxa fitxaMockH;
    private Fitxa fitxaMockJ;
    private Fitxa fitxaMockI;

    @Before
    public void setUp() {
        // Crear mocks de fitxes
        fitxaMockA = mock(Fitxa.class);
        fitxaMockB = mock(Fitxa.class);
        fitxaMockC = mock(Fitxa.class);
        fitxaMockD = mock(Fitxa.class);
        fitxaMockE = mock(Fitxa.class);
        fitxaMockF = mock(Fitxa.class);
        fitxaMockG = mock(Fitxa.class);
        fitxaMockH = mock(Fitxa.class);
        fitxaMockI = mock(Fitxa.class);
        fitxaMockJ = mock(Fitxa.class);

        // Simulant els punts de les fitxes
        when(fitxaMockA.obtenirPunts()).thenReturn(1);
        when(fitxaMockB.obtenirPunts()).thenReturn(3);

        // Crear mock de sac
        sacMock = mock(Sac.class);

        // Simulant que el sac té fitxes
        when(sacMock.agafarFitxa(fitxaMockA)).thenReturn(fitxaMockA);
        when(sacMock.agafarFitxa(fitxaMockB)).thenReturn(fitxaMockB);
        when(sacMock.agafarFitxa()).thenReturn(fitxaMockA, fitxaMockB, fitxaMockC, fitxaMockD, fitxaMockE, fitxaMockF, fitxaMockG, fitxaMockH, fitxaMockI, fitxaMockJ);

        // Crear el Faristol amb el mock de Sac
        faristol = new Faristol(sacMock);
    }

    @Test
    public void test1() {
        System.out.println(sacMock.agafarFitxa(fitxaMockA));
    }

//    @Test
//    public void testInicialitzarFaristol() {
//        // Comprovar que el faristol té fitxes al principi
//        assertEquals(2, faristol.obtenirNumFitxes());
//        assertEquals(fitxaMockA, faristol.obtenirFitxa(0));
//        assertEquals(fitxaMockB, faristol.obtenirFitxa(1));
//    }
//
//    @Test
//    public void testAfegirFitxa() {
//        // Afegim una fitxa (després de la inicialització)
//        faristol.afegirFitxa(fitxaMockA);
//
//        // Comprovar que la nova fitxa s'ha afegit
//        assertEquals(3, faristol.obtenirNumFitxes());
//        assertEquals(fitxaMockA, faristol.obtenirFitxa(2));
//    }
//
//    @Test(expected = ExcepcioFaristolPle.class)
//    public void testAfegirFitxaSenseEspai() {
//        // Afegim més fitxes fins que el faristol estigui ple
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//
//        // Intentem afegir una altra fitxa, hauria de llançar una ExcepcioFaristolPle
//        faristol.afegirFitxa(fitxaMockA);
//    }
//
//    @Test(expected = ExcepcioFaristolNoConteLaFitxa.class)
//    public void testEliminarFitxaNoExistent() {
//        // Intentem eliminar una fitxa que no existeix en el faristol
//        faristol.eliminarFitxa(fitxaMockA); // La fitxa no està
//    }
//
//    @Test
//    public void testReposarFitxes() {
//        // Comprovem que reposar les fitxes funcionin
//        // Si es treu una fitxa, es reposarà automàticament
//        faristol.eliminarFitxa(fitxaMockA);
//        assertEquals(2, faristol.obtenirNumFitxes());
//    }
//
//    @Test
//    public void testBarrejarFitxes() {
//        // Barrejar les fitxes i comprovar que l'ordre canvia
//        ArrayList<Fitxa> fitxesAbans = new ArrayList<>(faristol.obtenirFitxes());
//        faristol.barrejarFitxes();
//        ArrayList<Fitxa> fitxesDespres = new ArrayList<>(faristol.obtenirFitxes());
//        assertNotEquals(fitxesAbans, fitxesDespres); // L'ordre hauria de ser diferent
//    }
//
//    @Test
//    public void testImprimirFaristol() {
//        // Comprovar que la impressió es fa correctament
//        faristol.imprimirFaristol(); // No podem verificar directament a causa de la impressió, però ens assegurem que no es llança cap excepció
//    }
//
//    // Verificacions d'excepcions
//    @Test(expected = ExcepcioFaristolPle.class)
//    public void testExcepcionsAfegirFitxa() {
//        // Comprovem excepcions per afegir fitxes a un faristol ple
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA);
//        faristol.afegirFitxa(fitxaMockA); // Aquí hauria d'explotar
//    }
//
//    @Test(expected = ExcepcioFaristolNoConteLaFitxa.class)
//    public void testExcepcionsEliminarFitxa() {
//        // Comprovem excepció quan es vol eliminar una fitxa que no hi és
//        faristol.eliminarFitxa(fitxaMockA); // La fitxa no està
//    }
}
