package br.com.alura.leilao.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

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
        assertEquals("console", descricaoDevolvida);
    }

    // Como nomear testes:
    // [nome do método] [estado do teste] [resultado esperado]
    // [deve] [resultado esperado] [estado de teste]
    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));

        double lanceConsole = console.getMaiorLance();

        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, lanceConsole, DELTA);
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
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 10000.0));
        console.propoe(new Lance(fran, 90000.0));
        double maiorLance = console.getMaiorLance();
        assertEquals(10000.0, maiorLance, DELTA);
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

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 10000.0));
        console.propoe(new Lance(fran, 90000.0));
        double menorLance = console.getMenorLance();
        assertEquals(90000.0, menorLance, DELTA);
    }

    // TDD: criar um padrão no qual primeiro implementamos o teste e depois desenvolvemos o código
    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        console.propoe(new Lance(alex, 200.0));
        console.propoe(new Lance(new Usuario("fran"), 300.0));
        console.propoe(new Lance(alex, 400.0));

        List<Lance> tresMaioresLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLances.size());
        assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);
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
        console.propoe(new Lance(new Usuario("fran"), 504.0));
        console.propoe(new Lance(new Usuario("fran"), 340.0));

        List<Lance> tresMaioresLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLances.size());
        assertEquals(504.0, tresMaioresLances.get(0).getValor(), DELTA);
        assertEquals(340.0, tresMaioresLances.get(1).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLances.get(2).getValor(), DELTA);

        console.propoe(new Lance(alex, 700.0));
        List<Lance> lancesDevolvidos = console.tresMaioresLances();

        assertEquals(3, lancesDevolvidos.size());
        assertEquals(700.0, lancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(504.0, lancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(340.0, lancesDevolvidos.get(2).getValor(), DELTA);
    }
}
