package org.cefet.models;

import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;
import org.cefet.models.base.BaseModel;

import java.util.Date;

public class TransacaoModel extends BaseModel {
    private long TransacaoId;
    private TipoTransacao TipoTransacao;
    private Double Valor;
    private Date DataHora;
    private String Descricao;
    private StatusTransacao Status;
    private Date DataCriacao;
    private Date DataAtualizacao;
    private long UsuarioId;

    private UsuarioModel Usuario;

    public TransacaoModel() {
    }

    public TransacaoModel(long transacaoId, TipoTransacao tipoTransacao, Double valor, Date dataHora, String descricao, StatusTransacao status, Date dataCriacao, Date dataAtualizacao, long usuarioId, UsuarioModel usuario) {
        TransacaoId = transacaoId;
        TipoTransacao = tipoTransacao;
        Valor = valor;
        DataHora = dataHora;
        Descricao = descricao;
        Status = status;
        DataCriacao = dataCriacao;
        DataAtualizacao = dataAtualizacao;
        UsuarioId = usuarioId;
        Usuario = usuario;
    }

    public long getTransacaoId() {
        return TransacaoId;
    }

    public void setTransacaoId(long transacaoId) {
        TransacaoId = transacaoId;
    }

    public TipoTransacao getTipoTransacao() {
        return TipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        TipoTransacao = tipoTransacao;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double valor) {
        Valor = valor;
    }

    public Date getDataHora() {
        return DataHora;
    }

    public void setDataHora(Date dataHora) {
        DataHora = dataHora;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public StatusTransacao getStatus() {
        return Status;
    }

    public void setStatus(StatusTransacao status) {
        Status = status;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return DataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        DataAtualizacao = dataAtualizacao;
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }

    public UsuarioModel getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        Usuario = usuario;
    }
}
