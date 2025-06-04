package org.cefet.models;

import org.cefet.enums.TipoUsuario;
import org.cefet.models.base.BaseModel;

import java.util.Date;

public class UsuarioModel extends BaseModel {
    private long UsuarioId;
    private String Nome;
    private String Senha;
    private Date DataNascimento;
    private TipoUsuario TipoUsuario = org.cefet.enums.TipoUsuario.COMUM;
    private double Saldo;

    public UsuarioModel(long usuarioId, String nome, String senha, Date dataNascimento, TipoUsuario tipoUsuario, double saldo) {
        UsuarioId = usuarioId;
        Nome = nome;
        Senha = senha;
        DataNascimento = dataNascimento;
        TipoUsuario = tipoUsuario;
        Saldo = saldo;
        setCreatedAt(new Date());
    }

    public long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public TipoUsuario getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }
}
