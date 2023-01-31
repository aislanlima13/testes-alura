package br.com.alura.leilao.model;

import static org.junit.Assert.*;

import org.junit.Test;

// Cada execução é uma nova instância da classe de testes
public class LeilaoTest {

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
        assertEquals(200.0, lanceConsole, 0.0001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(fran, 200.0));
        double maiorLance = console.getMaiorLance();
        assertEquals(200.0, maiorLance, 0.0001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 10000.0));
        console.propoe(new Lance(fran, 90000.0));
        double maiorLance = console.getMaiorLance();
        assertEquals(10000.0, maiorLance, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeApenasUmLance() {
        console.propoe(new Lance(alex, 200.0));
        double lanceConsole = console.getMenorLance();
        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, lanceConsole, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 100.0));
        console.propoe(new Lance(fran, 200.0));
        double menorLance = console.getMenorLance();
        assertEquals(100.0, menorLance, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Usuario fran = new Usuario("Fran");
        console.propoe(new Lance(alex, 10000.0));
        console.propoe(new Lance(fran, 90000.0));
        double menorLance = console.getMenorLance();
        assertEquals(90000.0, menorLance, 0.0001);
    }
}