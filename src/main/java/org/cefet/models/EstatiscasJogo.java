package org.cefet.models;

import org.cefet.models.base.BaseModel;

public class EstatiscasJogo extends BaseModel {
    private long EstatiscasJogoId;
    private long TotalApostas;
    private long JogadoresAtivos;
    private long JogoId;

    private JogoModel Jogo;

    public EstatiscasJogo() {
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
}
