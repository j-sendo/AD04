/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import com.accesodatos.adtarea4.configuracions.ConfiguracionApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;



/**
 *
 * @author José Rosendo
 */
public class HibernateUtil {
    static Gson gson=new GsonBuilder().setPrettyPrinting().create();

    private static SessionFactory sesion;
    public static SessionFactory getSessionFactory() throws FileNotFoundException {
        if (sesion==null){
            File fichConfig=new File("config.json");
            ConfiguracionApp config=gson.fromJson(new FileReader(fichConfig), ConfiguracionApp.class);
                    
            Configuration conf=new Configuration();
            
            Properties propiedades=new Properties();
            
            propiedades.put(Environment.DIALECT, config.getHibernate().getDialect());
            propiedades.put(Environment.DRIVER, config.getHibernate().getDriver());
            propiedades.put(Environment.URL, config.getDbConnection().getUrl());
            propiedades.put(Environment.USER,config.getDbConnection().getUser());
            propiedades.put(Environment.PASS, config.getDbConnection().getPassword());
            propiedades.put(Environment.SHOW_SQL,false);
            propiedades.put(Environment.HBM2DDL_AUTO, config.getHibernate().getHBM2DDL_AUTO());
            propiedades.put(Environment.HBM2DDL_AUTO, "update");
 
            conf.setProperties(propiedades);
            conf.addAnnotatedClass(Cliente.class);
            conf.addAnnotatedClass(Tenda.class);
            conf.addAnnotatedClass(Provincia.class);
            conf.addAnnotatedClass(Producto.class);
            conf.addAnnotatedClass(Empregado.class);
            conf.addAnnotatedClass(ProductoTenda.class);
            conf.addAnnotatedClass(EmpregadoTenda.class);
            
            sesion=conf.buildSessionFactory();
            
            
        }
        return sesion;
    }
}



            /**
            propiedades.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            propiedades.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            propiedades.put(Environment.URL, "jdbc:mysql://192.168.56.101:3306/franquicia");
            propiedades.put(Environment.USER,"userhibernate");
            propiedades.put(Environment.PASS, "abc123.");
            propiedades.put(Environment.SHOW_SQL,"true");
            */