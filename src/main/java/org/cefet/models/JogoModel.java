package org.cefet.models;

import org.cefet.models.base.BaseModel;

import java.util.HashMap;

public class JogoModel extends BaseModel {
    private long JogoId;
    private String Nome;
    private HashMap<String, Double> Odds;

    public JogoModel() {
    }

    public JogoModel(long jogoId, String nome, HashMap<String, Double> odds) {
        JogoId = jogoId;
        Nome = nome;
        Odds = odds;
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
