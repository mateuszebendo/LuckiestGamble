package org.cefet.models;

import org.cefet.models.base.BaseModel;

import java.util.HashMap;

public class JogoModel extends BaseModel {
    public String Nome;
    public HashMap<String, Double> Odds;

    public JogoModel() {
    }

    public JogoModel(String nome, HashMap<String, Double> odds) {
        Nome = nome;
        Odds = odds;
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
