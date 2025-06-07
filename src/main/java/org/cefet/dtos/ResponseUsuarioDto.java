package org.cefet.dtos;

import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;

import java.util.Date;

public class ResponseUsuarioDto {
    private Long UsuarioId;
    private String Nome;
    private String Email;
    private String Senha;
    private Date DataNascimento;
    private TipoUsuario TipoUsuario = org.cefet.enums.TipoUsuario.COMUM;
    private double Saldo;

    public ResponseUsuarioDto() {
    }

    public ResponseUsuarioDto(UsuarioModel usuario) {
        UsuarioId = usuario.getUsuarioId();
        Nome = usuario.getNome();
        Email = usuario.getEmail();
        Senha = usuario.getSenha();
        DataNascimento = usuario.getDataNascimento();
        TipoUsuario = usuario.getTipoUsuario();
        Saldo = usuario.getSaldo();
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
