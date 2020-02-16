/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informe;

import com.accesodatos.adtarea4.ConHibernate;
import com.accesodatos.adtarea4.Tenda;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author José Rosendo
 */
public class Informe {
    List<TendaI> tendas=new ArrayList<>();

    public Informe(List<TendaI> tendas) {
        this.tendas = tendas;
    }

    public Informe() {
    }
    public Informe(ConHibernate cH) {
        List<Tenda> tendasTmp=cH.getTendas();
        Session sesion=cH.getsF().openSession();
        for (Tenda t:tendasTmp){
             tendas.add(new TendaI(sesion,t.getId()));
        }
        sesion.close();
       
    }
}
