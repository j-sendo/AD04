/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author José Rosendo
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(name="Cemail",columnNames=("email"))})
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    @Column
    String nome,apelidos;
    
    @Column(length=80)
    String email;
/**   
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
*/
    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Cliente (String nome, String apelidos, String email) throws Exception{
        if (nome.isEmpty()||email.isEmpty()) throw new Exception("Erro. Non se introduciron os datos requeridos.");
        if (!email.matches("[\\p{Alnum}-_+.]+@([\\p{Alnum}-]+\\.)+\\p{Alpha}+")) throw new Exception("Erro. Formato email incorrecto.");
        this.nome=nome;
        this.apelidos=apelidos;
        this.email=email;
    }
    public Cliente (int id, String nome, String apelidos, String email) {
        this.id=id;
        this.nome=nome;
        this.apelidos=apelidos;
        this.email=email;
    }

    public Cliente(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return String.format("%-3s %-14s %-28s %-35s\n",id,nome,apelidos,email);
    }

}

