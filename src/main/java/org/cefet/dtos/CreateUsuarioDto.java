package org.cefet.dtos;

import java.util.Date;

public class CreateUsuarioDto {
    private String Nome;
    private String Senha;
    private Date DataNascimento;

    public CreateUsuarioDto() {
    }

    public CreateUsuarioDto(String nome, String senha, Date dataNascimento) {
        Nome = nome;
        Senha = senha;
        DataNascimento = dataNascimento;
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
}
