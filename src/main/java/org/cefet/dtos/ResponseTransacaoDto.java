package org.cefet.dtos;

import org.cefet.enums.StatusTransacao;
import org.cefet.enums.TipoTransacao;
import org.cefet.models.TransacaoModel;

import java.util.Date;

public class ResponseTransacaoDto {
    private Long TransacaoId;
    private TipoTransacao TipoTransacao;
    private Double Valor;
    private Date DataHora;
    private String Descricao;
    private StatusTransacao Status;
    private Date DataCriacao;
    private Date DataAtualizacao;
    private long UsuarioId;

    private ResponseUsuarioDto ResponseUsuario;

    public ResponseTransacaoDto() {
    }

    public ResponseTransacaoDto(TransacaoModel transacaoModel) {
        TransacaoId = transacaoModel.getTransacaoId();
        TipoTransacao = transacaoModel.getTipoTransacao();
        Valor = transacaoModel.getValor();
        DataHora = transacaoModel.getDataHora();
        Descricao = transacaoModel.getDescricao();
        Status = transacaoModel.getStatus();
        DataCriacao = transacaoModel.getDataCriacao();
        DataAtualizacao = transacaoModel.getDataAtualizacao();
        UsuarioId = transacaoModel.getUsuarioId();
        if (transacaoModel.getUsuario() != null) {
            this.ResponseUsuario = new ResponseUsuarioDto(transacaoModel.getUsuario());
        } else {
            this.ResponseUsuario = null;
        }
    }

    public Long getTransacaoId() {
        return TransacaoId;
    }

    public void setTransacaoId(Long transacaoId) {
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

    public ResponseUsuarioDto getResponseUsuario() {
        return ResponseUsuario;
    }

    public void setResponseUsuario(ResponseUsuarioDto responseUsuario) {
        ResponseUsuario = responseUsuario;
    }
}
