package br.com.alura.leilao.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if (lanceNaoValido(lance)) return;

        Double valorLance = defineMaiorEMenorLanceParaOPrimeiroLance(lance);
        if (valorLance == null) return;

        Collections.sort(lances);

        calculaMaiorLance(valorLance);

        calculaMenorLance(valorLance);
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

    private boolean lanceNaoValido(Lance lance) {
        double valorLance = lance.getValor();

        if (lanceForMenorQueUltimoLance(valorLance)) return true;

        if (!lances.isEmpty()) {
            Usuario usuario = lance.getUsuario();
            if (usuarioForOMesmoDoUltimoLance(usuario)) return true;

            return usuarioDeuCincoLances(usuario);
        }
        return false;
    }

    private boolean usuarioDeuCincoLances(Usuario usuario) {
        int lancesDoUsuario = 0;

        for (Lance l: lances) {
            Usuario usuarioExistente = l.getUsuario();

            if (usuarioExistente.equals(usuario)) {
                lancesDoUsuario++;

                if (lancesDoUsuario == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForOMesmoDoUltimoLance(Usuario usuario) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();

        return usuario.equals(ultimoUsuario);
    }

    private boolean lanceForMenorQueUltimoLance(double valorLance) {
        return maiorLance > valorLance;
    }

    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance) {
            menorLance = valorLance;
        }
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

    public int quantidadeLances() {
        return lances.size();
    }
}
