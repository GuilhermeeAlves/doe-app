package com.doe.models;


public class Organization {

    private String name;
    private String email;
    private String password;
    private String descOrganization;
    private String cnpj;
    private String endereco;
    private String fone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescOrganization() {
        return descOrganization;
    }

    public void setDescOrganization(String descOrganization) {
        this.descOrganization = descOrganization;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    @Override
    public String toString() {
        return "Nome: " + this.getName() + "\nDescOrganization: "+ this.getDescOrganization() + "\nEmail: "+ this.getEmail();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
