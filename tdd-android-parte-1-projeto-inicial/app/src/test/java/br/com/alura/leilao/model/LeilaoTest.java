package br.com.alura.leilao.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeilaoTest {

    @Test
    public void getDescricao_QuandoRecebeDescricao_DevolveDescricao() {
        // criar cenário de teste
        Leilao console = new Leilao("console");

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
        Leilao console = new Leilao("console");
        console.propoe(new Lance(new Usuario("Alex"), 200.0));

        double lanceConsole = console.getMaiorLance();

        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, lanceConsole, 0.0001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Leilao computador = new Leilao("computador");
        computador.propoe(new Lance(new Usuario("Alex"), 100.0));
        computador.propoe(new Lance(new Usuario("Fran"), 200.0));
        double lanceComputador = computador.getMaiorLance();
        assertEquals(200.0, lanceComputador, 0.0001);
    }

    @Test
    public void deve_DevolveMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Leilao carro = new Leilao("carro");
        carro.propoe(new Lance(new Usuario("Alex"), 10000.0));
        carro.propoe(new Lance(new Usuario("Fran"), 90000.0));
        double lanceCarro = carro.getMaiorLance();
        assertEquals(10000.0, lanceCarro, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeApenasUmLance() {
        Leilao console = new Leilao("console");
        console.propoe(new Lance(new Usuario("Alex"), 200.0));

        double lanceConsole = console.getMenorLance();

        // delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        assertEquals(200.0, lanceConsole, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        Leilao computador = new Leilao("computador");
        computador.propoe(new Lance(new Usuario("Alex"), 100.0));
        computador.propoe(new Lance(new Usuario("Fran"), 200.0));
        double lanceComputador = computador.getMenorLance();
        assertEquals(100.0, lanceComputador, 0.0001);
    }

    @Test
    public void deve_DevolveMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        Leilao carro = new Leilao("carro");
        carro.propoe(new Lance(new Usuario("Alex"), 10000.0));
        carro.propoe(new Lance(new Usuario("Fran"), 90000.0));
        double lanceCarro = carro.getMenorLance();
        assertEquals(90000.0, lanceCarro, 0.0001);
    }
}