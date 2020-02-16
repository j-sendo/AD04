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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author José Rosendo
 */
@Entity
public class Empregado implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String nome;
    private String apelidos;

    @OneToMany(mappedBy="empregado",cascade=CascadeType.ALL)
    List<EmpregadoTenda> empregadosTendas;

    public List<EmpregadoTenda> getEmpregadosTendas() {
        return empregadosTendas;
    }

    public void setEmpregadosTendas(List<EmpregadoTenda> empregadosTendas) {
        this.empregadosTendas = empregadosTendas;
    }
    /**@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Traballa",
            joinColumns={@JoinColumn(name="id_Empre")},
            inverseJoinColumns={@JoinColumn(name="id_Tenda")
            })
    private List<Tenda> tendas;*/
    public Empregado(int id, String nome, String apelidos) {
        this.id = id;
        this.nome = nome;
        this.apelidos = apelidos;
    }

    public Empregado(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public Empregado() {
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
        final Empregado other = (Empregado) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public Empregado(String nome, String apelidos) throws Exception {
        if (nome.isEmpty()||apelidos.isEmpty()) throw new Exception("Erro. Non se introduciron os datos requeridos.");
        this.nome = nome;
        this.apelidos = apelidos;
    }

    @Override
    public String toString() {
        return String.format("%-3s %-14s %-28s\n",id,nome,apelidos);
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


    
}
