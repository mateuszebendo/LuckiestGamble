package org.cefet.dtos;

import org.cefet.enums.TipoUsuario;

import java.util.Date;

public class FindUsuarioDto {
    private String Nome = null;
    private String Email = null;
    private TipoUsuario TipoUsuario = org.cefet.enums.TipoUsuario.COMUM;
    private Date DataNascimento = null;

    public FindUsuarioDto() {
    }

    public FindUsuarioDto(String nome, String email, TipoUsuario tipoUsuario, Date dataNascimento) {
        Nome = nome;
        Email = email;
        TipoUsuario = tipoUsuario;
        DataNascimento = dataNascimento;
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

    public TipoUsuario getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
    }
}
