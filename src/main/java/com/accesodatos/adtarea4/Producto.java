/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String nome;
    private String descripcion;
    private double prezo;

    @OneToMany(mappedBy="producto",cascade=CascadeType.ALL)
    List<ProductoTenda> Ptendas;
    /**@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="ProductosTenda",
            joinColumns={@JoinColumn(name="id_Producto")},
            inverseJoinColumns={@JoinColumn(name="id_Tenda")})
    private List<Tenda> tendas;
*//**
    public List<Tenda> getTendas(){
    ArrayList<Tenda> tendasP = new ArrayList<>();
    for (ProductoTenda pT : Ptendas){
        tendasP.add(pT.getTenda());
    }
    return tendasP;
}*/
    public Producto() {
    }
    
    public Producto(String nome, String descripcion, double prezo) throws Exception {
        if (nome.isEmpty()) throw new Exception("Erro. O campo nome é obrigatorio.");
        else if (prezo<=0) throw new Exception("Erro. O prezo debe ser unha cantidade positiva.");
        this.nome = nome;
        this.descripcion = descripcion;
        this.prezo = prezo;
    }

    public Producto(int id, String nome, String descripcion, double prezo) {
        this.id = id;
        this.nome = nome;
        this.descripcion = descripcion;
        this.prezo = prezo;
    }
    
    public boolean insertarBd(Connection conex) throws SQLException{
        PreparedStatement tmpPrep=conex.prepareStatement("INSERT INTO Producto (nome,descripcion,prezo) VALUES (?,?,?);");
        boolean resultado;
        tmpPrep.setString(1, nome);
        tmpPrep.setString(2, descripcion);
        tmpPrep.setDouble(3, prezo);
        if (tmpPrep.executeUpdate()!=0) resultado=true;
        else resultado=false;
        tmpPrep.close();
        return resultado;
    }

    @Override
    public String toString() {
        return String.format("%-3s %-14s %-40s %6.2f€\n",id,nome,descripcion,prezo);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrezo() {
        return prezo;
    }

    public void setPrezo(double prezo) {
        this.prezo = prezo;
    }

    public List<ProductoTenda> getPtendas() {
        return Ptendas;
    }

    public void setPTendas(List<ProductoTenda> tendas) {
        this.Ptendas = tendas;
    }
    
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
        final Producto other = (Producto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
