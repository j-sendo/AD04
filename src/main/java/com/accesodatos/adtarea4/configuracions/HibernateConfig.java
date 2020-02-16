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
public class HibernateConfig {
    String driver,dialect,HBM2DDL_AUTO;
    boolean SHOW_SQL;

    public boolean isSHOW_SQL() {
        return SHOW_SQL;
    }

    public void setSHOW_SQL(boolean SHOW_SQL) {
        this.SHOW_SQL = SHOW_SQL;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getHBM2DDL_AUTO() {
        return HBM2DDL_AUTO;
    }

    public void setHBM2DDL_AUTO(String HBM2DDL_AUTO) {
        this.HBM2DDL_AUTO = HBM2DDL_AUTO;
    }



    public HibernateConfig() {
    }
}
