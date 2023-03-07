package br.com.alura.leilao.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {
        lanceNaoValido(lance);

        Double valorLance = defineMaiorEMenorLanceParaOPrimeiroLance(lance);
        if (valorLance == null) return;

        Collections.sort(lances);

        calculaMaiorLance(valorLance);
    }

    @Nullable
    private Double defineMaiorEMenorLanceParaOPrimeiroLance(Lance lance) {
        double valorLance = lance.getValor();
        lances.add(lance);
        if (lances.size() == 1) {
            maiorLance = valorLance;
            menorLance = valorLance;
            return null;
        }
        return valorLance;
    }

    private void lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();

        if (lanceForMenorQueUltimoLance(valorLance))
            throw new LanceMenorQueUltimoLanceException();

        if (!lances.isEmpty()) {
            Usuario usuario = lance.getUsuario();
            if (usuarioForOMesmoDoUltimoLance(usuario))
                throw new LanceSeguidoDoMesmoUsuarioException();

            usuarioDeuCincoLances(usuario);
        }
    }

    private void usuarioDeuCincoLances(Usuario usuario) {
        int lancesDoUsuario = 0;

        for (Lance l: lances) {
            Usuario usuarioExistente = l.getUsuario();

            if (usuarioExistente.equals(usuario)) {
                lancesDoUsuario++;

                if (lancesDoUsuario == 5) {
                    throw new UsuarioJaDeuCincoLancesException();
                }
            }
        }
    }

    private boolean usuarioForOMesmoDoUltimoLance(Usuario usuario) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();

        return usuario.equals(ultimoUsuario);
    }

    private boolean lanceForMenorQueUltimoLance(double valorLance) {
        return maiorLance > valorLance;
    }

    private void calculaMaiorLance(double valorLance) {
        if (valorLance > maiorLance) {
            maiorLance = valorLance;
        }
    }

    public double getMaiorLance() {
        return maiorLance;
    }

    public double getMenorLance() {
        return menorLance;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> tresMaioresLances() {
        int qtdMaxLances = lances.size();

        if (qtdMaxLances > 3) {
            qtdMaxLances = 3;
        }

        return lances.subList(0, qtdMaxLances);
    }

}
