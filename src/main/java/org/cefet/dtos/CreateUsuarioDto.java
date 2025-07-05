package org.cefet.dtos;

import org.cefet.enums.TipoUsuario;
import org.cefet.models.UsuarioModel;

import java.util.Date;

public class CreateUsuarioDto {
    private String Nome;
    private String Email;
    private TipoUsuario TipoUsuario = org.cefet.enums.TipoUsuario.COMUM;
    private String Senha;
    private Date DataNascimento;

    public CreateUsuarioDto() {
    }

    public CreateUsuarioDto(String nome, String email, String senha, Date dataNascimento) {
        DataNascimento = dataNascimento;
        Senha = senha;
        Email = email;
        Nome = nome;
    }

    public CreateUsuarioDto(String nome, String email, String senha, Date dataNascimento, org.cefet.enums.TipoUsuario tipoUsuario) {
        DataNascimento = dataNascimento;
        Senha = senha;
        Email = email;
        Nome = nome;
        TipoUsuario = tipoUsuario;
    }

    public CreateUsuarioDto(UsuarioModel usuarioModel) {
        Nome = usuarioModel.getNome();
        Email = usuarioModel.getEmail();
        Senha = usuarioModel.getSenha();
        DataNascimento = usuarioModel.getDataNascimento();
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
}
