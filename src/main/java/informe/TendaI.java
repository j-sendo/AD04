/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import com.accesodatos.adtarea4.Tenda;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author José Rosendo
 */
public class TendaI {
    int id_Tenda;
    String nome,cidade,provincia; 
    List<ProductoI> productos=new ArrayList<>();

    public TendaI(Session sesion,int idTenda) {
        Tenda tmp=sesion.get(Tenda.class, idTenda);
        id_Tenda=tmp.getId();
        nome=tmp.getNome();
        cidade=tmp.getCidade();
        provincia=tmp.getProv().getNome();        
        Query consulta=sesion.createQuery("SELECT p.id,p.nome,pT.stock FROM Producto p,ProductoTenda pT WHERE p=pT.producto AND pT.tenda=:t");
        consulta.setEntity("t", tmp);
        List<Object[]> resul=consulta.list();

        
        for (Object[] o:resul){

            productos.add(new ProductoI(Integer.parseInt(o[0].toString()),Integer.parseInt(o[2].toString()),(String)o[1]));
        }
    }
    
    
}
