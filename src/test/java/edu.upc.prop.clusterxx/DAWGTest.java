package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DAWGTest {
    private DAWG dawg;

        @Before
        public void setUp() {
            List<String> tokensCatalan = [];
            List<String> tokensCastellano = [];
            List<String> tokensEnglish = [];
        }

        @Test
        public void inicialitzacióTokens() {
            assertEquals(dawg.inicialitzacióTokens("catalan").tokens(), tokensCatalan);
        }

        @Override
        protected void carregar(String idioma) {
            this.tokens.addAll(Arrays.asList("ch", "ll", "ny", "a", "b", "c", "s")); // tokens comuns
            this.tokens.sort((a, b) -> Integer.compare(b.length(), a.length())); // ordre descendent

            afegirParaula("casa", "");  // paraula 1
            afegirParaula("casament", "casa");  // paraula 2
            afegirParaula("castell", "casament");  // paraula 3
            minimitzarFinal();
        }

        @Override
        protected void inicialitzarTokens(String idioma) {
            // ja afegits manualment
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        dawg = new DAWGProva();
    }

    @Test
    void testConteParaula() {
        assertTrue(dawg.conteParaula("casa"));
        assertTrue(dawg.conteParaula("casament"));
        assertFalse(dawg.conteParaula("caseta"));
    }

    @Test
    void testEsPrefix() {
        assertTrue(dawg.esPrefix("cas"));
        assertTrue(dawg.esPrefix("casa"));
        assertFalse(dawg.esPrefix("cab"));
    }
}
