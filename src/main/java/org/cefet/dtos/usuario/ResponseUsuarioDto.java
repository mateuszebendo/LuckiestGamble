package org.cefet.dtos.usuario;

import org.cefet.models.UsuarioModel;

import java.util.Date;

public class ResponseUsuarioDto {
    private String Nome;
    private String Email;
    private String Senha;
    private Date DataNascimento;

    public ResponseUsuarioDto() {
    }

    public ResponseUsuarioDto(UsuarioModel usuarioModel) {
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
}
