package org.cefet.dtos;

import org.cefet.models.JogoModel;

import java.util.HashMap;

public class ResponseJogoDto {
    private long JogoId;
    private String Nome;
    private HashMap<String, Double> Odds;

    public ResponseJogoDto() {}

    public ResponseJogoDto(JogoModel jogo) {
        JogoId = jogo.getJogoId();
        Nome = jogo.getNome();
        Odds = jogo.getOdds();
    }

    public long getJogoId() {
        return JogoId;
    }

    public void setJogoId(long jogoId) {
        JogoId = jogoId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public HashMap<String, Double> getOdds() {
        return Odds;
    }

    public void setOdds(HashMap<String, Double> odds) {
        Odds = odds;
    }
}
