/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 *
 * @author José Rosendo
 */
@Entity
public class Tenda implements Serializable {
    
    String nome,cidade;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    
    @ManyToOne
    @JoinColumn(name="id_prov")
    Provincia prov;

    @OneToMany(mappedBy="tenda",cascade=CascadeType.ALL)
    List<ProductoTenda> Tproductos;


    @OneToMany(mappedBy="tenda_",cascade=CascadeType.ALL)
    List<EmpregadoTenda> empregadosTendas;
    
    List<Empregado> getEmpregados(){
        ArrayList<Empregado> empregados=new ArrayList<>();
        for (EmpregadoTenda e:empregadosTendas){
            empregados.add(e.empregado);
            
        }
        return empregados;
    }

    public Provincia getProv() {
        return prov;
    }

    public void setProv(Provincia prov) {
        this.prov = prov;
    }

    public List<EmpregadoTenda> getEmpregadosTendas() {
        return empregadosTendas;
    }

    public void setEmpregadosTendas(List<EmpregadoTenda> empregadosTendas) {
        this.empregadosTendas = empregadosTendas;
    }
    /**
    @ManyToMany(mappedBy="tendas",cascade=CascadeType.ALL)
    List<Producto> productos;
    
    @ManyToMany(mappedBy="tendas",cascade=CascadeType.ALL)
    List<Empregado> empregados;*/
    /**
    public List<Tenda> getProductos(){
    ArrayList<Tenda> tendasP = new ArrayList<>();
    for (ProductoTenda pT : Tproductos){
        tendasP.add(pT.getTenda());
    }
    return tendasP;
}*/
    public Tenda() {
    }

    
    public Tenda (String nome, String cidade, Provincia prov) throws Exception{
        if (nome.isEmpty()||cidade.isEmpty()) throw new Exception("Erro. Nome e cidade son campos obrigatorios.");
        else if (prov==null) throw new Exception("Erro. Non existe a provincia.");
        this.nome=nome;
        this.cidade=cidade;
        this.prov=prov;
    }
    public Tenda (int id,String nome, String cidade, Provincia prov) {
        this.id=id;
        this.nome=nome;
        this.cidade=cidade;
        this.prov=prov;
    }

    @Override
    public boolean equals(Object a){
        if (this.id==((Tenda)a).id) return true;
        else return false;
        
    }

    @Override
    public String toString(){
        return String.format("%-3s %-10s %-18s %-18s\n",id,nome,cidade,prov.nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public List<ProductoTenda> getTproductos() {
        return Tproductos;
    }

    public void setTproductos(List<ProductoTenda> Tproductos) {
        this.Tproductos = Tproductos;
    }
}
