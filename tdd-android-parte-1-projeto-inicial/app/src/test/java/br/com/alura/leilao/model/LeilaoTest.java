package br.com.alura.leilao.model;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

// Cada execução é uma nova instância da classe de testes
public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private final Leilao console = new Leilao("console");
    private final Usuario alex = new Usuario("Alex");

    @Test
    public void getDescricao_QuandoRecebeDescricao_DevolveDescricao() {
        // executar ação esperada
        String descricaoDevolvida = console.getDescricao();

        // testar resultado esperado
        assertThat(descricaoDevolvida, is(equalTo("console")));
    }

    // Como nomear testes:
    // [nome do método] [estado do teste] [resultado esperado]
    // [deve] [resultado esperado] [estado de teste]
    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));

        double maiorLanceDevolvido = console.getMaiorLance();

        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
//        assertEquals(200.0, lanceConsole, DELTA);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(fran, 200.0));
        double maiorLance = console.getMaiorLance();
        assertEquals(200.0, maiorLance, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));
        double lanceConsole = console.getMenorLance();
        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, lanceConsole, DELTA);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(fran, 200.0));
        double menorLance = console.getMenorLance();
        assertEquals(100.0, menorLance, DELTA);
    }

    // TDD: criar um padrão no qual primeiro implementamos o teste e depois desenvolvemos o código
    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        console.propoe(new Lance(alex, 200.0));
        console.propoe(new Lance(new Usuario("fran"), 300.0));
        console.propoe(new Lance(alex, 400.0));

        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

//        assertEquals(3, tresMaioresLances.size());
        /** Para verificar tamanho de lista **/
        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));

//        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        /** para verificar se tem o item **/
//        assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(alex, 400.0)));
//        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
//        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
        /** para verificar os itens que devem retornar os 3 mariores lances **/
        /*assertThat(tresMaioresLancesDevolvidos, contains(
                new Lance(alex, 400.0),
                new Lance(new Usuario("fran"), 300.0),
                new Lance(alex, 200.0)
        ));*/

        /** retorna tamanho da lista e itens esperados */
        assertThat(tresMaioresLancesDevolvidos, both(hasSize(3)).and(contains(
                new Lance(alex, 400.0),
                new Lance(new Usuario("fran"), 300.0),
                new Lance(alex, 200.0)
        )));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLances = console.tresMaioresLances();
        assertEquals(0, tresMaioresLances.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));
        List<Lance> tresMaioresLances = console.tresMaioresLances();

        assertEquals(1, tresMaioresLances.size());
        assertEquals(200.0, tresMaioresLances.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLance() {
        console.propoe(new Lance(alex, 200.0));
        console.propoe(new Lance(new Usuario("fran"), 300.0));

        List<Lance> tresMaioresLances = console.tresMaioresLances();

        assertEquals(2, tresMaioresLances.size());
        assertEquals(300.0, tresMaioresLances.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLances.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        console.propoe(new Lance(alex, 200.0));
        console.propoe(new Lance(new Usuario("fran"), 300.0));
        console.propoe(new Lance(new Usuario("fran"), 400.0));
        console.propoe(new Lance(new Usuario("fran"), 500.0));

        List<Lance> tresMaioresLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLances.size());
        assertEquals(300.0, tresMaioresLances.get(0).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLances.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLance = console.getMaiorLance();
        assertEquals(0.0, maiorLance, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLance = console.getMenorLance();
        assertEquals(0.0, menorLance, DELTA);
    }

    /**
     * naoDeve é a forma padrão para trabalhar com negação
     */
    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueMaiorLance() {
        console.propoe(new Lance(alex, 500.0));
        console.propoe(new Lance(alex, 400.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {
        console.propoe(new Lance(alex, 500.00));
        console.propoe(new Lance(alex, 600.00));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances() {
        final Usuario FRAN = new Usuario("Fran");
        console.propoe(new Lance(FRAN, 100.00));
        console.propoe(new Lance(alex, 200.00));
        console.propoe(new Lance(FRAN, 300.00));
        console.propoe(new Lance(alex, 400.00));
        console.propoe(new Lance(FRAN, 500.00));
        console.propoe(new Lance(alex, 600.00));
        console.propoe(new Lance(FRAN, 700.00));
        console.propoe(new Lance(alex, 800.00));
        console.propoe(new Lance(FRAN, 900.00));
        console.propoe(new Lance(alex, 1000.00));
        console.propoe(new Lance(FRAN, 1100.00));
    }
}
