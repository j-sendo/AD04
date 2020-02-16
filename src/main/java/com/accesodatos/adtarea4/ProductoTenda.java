/*22
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 *
 * @author José Rosendo
 */
@Entity
public class ProductoTenda implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="id_Prod")
    private Producto producto;
    @Id
    @ManyToOne
    @JoinColumn(name="id_Tenda")
    private Tenda tenda;
    private int stock;

    public ProductoTenda() {
    }

    public ProductoTenda(Producto producto, Tenda tenda) {
        this.producto = producto;
        this.tenda = tenda;
    }

    
    public ProductoTenda(Producto producto, Tenda tenda, int stock) throws Exception {
        this.producto = producto;
        this.tenda = tenda;
        if (stock<=0) throw new Exception("Debe introducirse un valor positivo.");
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Tenda getTenda() {
        return tenda;
    }

    @Override
    public String toString() {
        return String.format("%3d   %3d   %20s   %4.2f   %4d",tenda.id,producto.getId(),producto.getNome(),producto.getPrezo(), stock);
    }
    public String toStringProducto() {
        return String.format("%-4s %-14s %6.2f€ %5s\n",producto.getId(),producto.getNome(),producto.getPrezo(), stock);
    }
    public String toStringTenda() {
        return tenda.toString();
    }

    public void setTenda(Tenda tenda) {
        this.tenda = tenda;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws Exception {
        if (stock<=0) throw new Exception("Erro. O stock non pode ser negativo.");
        this.stock = stock;
    }
}
