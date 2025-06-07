package org.cefet.dtos;

public class LoginUsuarioDto {
    private String Usuario;
    private String Senha;

    public LoginUsuarioDto() {
    }

    public LoginUsuarioDto(String usuario, String senha) {
        Usuario = usuario;
        Senha = senha;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
}
