package org.cefet.models;

import org.cefet.models.base.BaseModel;

public class EstatiscasJogoModel extends BaseModel {
    private long EstatiscasJogoId;
    private long TotalApostas;
    private double LucroCasa;
    private long JogadoresAtivos;
    private long JogoId;

    private JogoModel Jogo;

    public EstatiscasJogoModel() {
    }

    public EstatiscasJogoModel(long estatiscasJogoId, long totalApostas, double lucroCasa, long jogadoresAtivos, long jogoId, JogoModel jogo) {
        EstatiscasJogoId = estatiscasJogoId;
        TotalApostas = totalApostas;
        LucroCasa = lucroCasa;
        JogadoresAtivos = jogadoresAtivos;
        JogoId = jogoId;
        Jogo = jogo;
    }

    public long getEstatiscasJogoId() {
        return EstatiscasJogoId;
    }

    public void setEstatiscasJogoId(long estatiscasJogoId) {
        EstatiscasJogoId = estatiscasJogoId;
    }

    public long getTotalApostas() {
        return TotalApostas;
    }

    public void setTotalApostas(long totalApostas) {
        TotalApostas = totalApostas;
    }

    public double getLucroCasa() {
        return LucroCasa;
    }

    public void setLucroCasa(double lucroCasa) {
        LucroCasa = lucroCasa;
    }

    public long getJogadoresAtivos() {
        return JogadoresAtivos;
    }

    public void setJogadoresAtivos(long jogadoresAtivos) {
        JogadoresAtivos = jogadoresAtivos;
    }

    public long getJogoId() {
        return JogoId;
    }

    public void setJogoId(long jogoId) {
        JogoId = jogoId;
    }

    public JogoModel getJogo() {
        return Jogo;
    }

    public void setJogo(JogoModel jogo) {
        Jogo = jogo;
    }

    public void registrarNovaAposta(double valorApostado, double ganhoCasa, boolean jogadorNovo) {
        this.TotalApostas++;
        this.LucroCasa += ganhoCasa;


        if (jogadorNovo) {
            this.JogadoresAtivos++;
        }
    }

    public double calcularLucroMedioPorAposta() {
        if (this.TotalApostas == 0) {
            return 0.0;
        }
        return this.LucroCasa / this.TotalApostas;
    }

    public void registrarNovoJogador() {
        this.JogadoresAtivos++;
    }
}
