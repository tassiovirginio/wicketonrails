package br.wicketonrails.entities;

import br.wicketonrails.wicketonrails.EntityGeneric;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User extends EntityGeneric{

    @NotNull
    private String nome;

    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
