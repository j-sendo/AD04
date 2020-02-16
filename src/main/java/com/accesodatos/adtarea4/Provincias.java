/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author José Rosendo
 */
public class Provincias {
    ArrayList<Provincia> provincias;
    void insertarBd(SessionFactory sF){
            Session sesion=sF.openSession();
            Transaction transaccion=sesion.beginTransaction();
            Query consulta=sesion.createQuery("SELECT x FROM Provincia x");
            if (consulta.list().size()<52){
                    for (int i=0;i<provincias.size();i++){
                        Provincia tmp=provincias.get(i);
                        if (sesion.get(Provincia.class, tmp.getId())==null) sesion.save(tmp);
                        
                    }
            }
            transaccion.commit();
            sesion.close();

    }
}
