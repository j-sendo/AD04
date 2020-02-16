/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4.configuracions;

/**
 *
 * @author José Rosendo
 */
public class ConfiguracionApp {
    ConexionBd dbConnection;
    HibernateConfig hibernate;

    public ConfiguracionApp() {
    }

    public ConexionBd getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(ConexionBd dbConnection) {
        this.dbConnection = dbConnection;
    }

    public HibernateConfig getHibernate() {
        return hibernate;
    }

    public void setHibernate(HibernateConfig hibernate) {
        this.hibernate = hibernate;
    }
}
