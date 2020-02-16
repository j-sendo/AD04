/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author José Rosendo
 */
@Entity
public class EmpregadoTenda implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name="id_Emp")
    Empregado empregado;
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_Tenda")     
    Tenda tenda_;
    
    int horas;

    public EmpregadoTenda(Empregado empregado, Tenda tenda, int horas) {
        this.empregado = empregado;
        this.tenda_ = tenda;
        this.horas = horas;
    }

    public EmpregadoTenda(Empregado empregado, Tenda tenda) {
        this.empregado = empregado;
        this.tenda_ = tenda;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final EmpregadoTenda other = (EmpregadoTenda) obj;
        if (!Objects.equals(this.empregado, other.empregado)) {
            return false;
        }
        if (!Objects.equals(this.tenda_, other.tenda_)) {
            return false;
        }
        return true;
    }

    


    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    


    public EmpregadoTenda() {
    }

}
