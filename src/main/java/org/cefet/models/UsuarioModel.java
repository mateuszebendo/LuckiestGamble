package org.cefet.models;

import org.cefet.enums.TipoUsuario;
import org.cefet.models.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class UsuarioModel extends BaseModel {
    private Long UsuarioId;
    private String Nome;
    private String Email;
    private String Senha;
    private Date DataNascimento;
    private TipoUsuario TipoUsuario = org.cefet.enums.TipoUsuario.COMUM;
    private double Saldo;

    public UsuarioModel(Long usuarioId, String nome, String email, String senha, Date dataNascimento, TipoUsuario tipoUsuario, double saldo) {
        UsuarioId = usuarioId;
        Nome = nome;
        Email = email;
        Senha = senha;
        DataNascimento = dataNascimento;
        TipoUsuario = tipoUsuario;
        Saldo = saldo;
    }

    public UsuarioModel() {
    }

    public Long getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
