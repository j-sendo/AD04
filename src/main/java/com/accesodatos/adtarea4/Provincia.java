/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author José Rosendo
 */
@Entity
public class Provincia implements Serializable {
    @Id         
    int id;

    public Provincia() {
    }

    @OneToMany(mappedBy="prov", cascade=CascadeType.ALL)
    List<Tenda> tendas;
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    String nome;

    @Override
    public String toString() {
        return String.format("%-3s %-18s\n",id,nome);
    }

    public Provincia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
