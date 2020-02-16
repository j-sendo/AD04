/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import informe.Informe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.xml.sax.SAXException;

/**
 *
 * @author José Rosendo
 */
public class AdTarea4 {
    final static Scanner ENTRADA_TECLADO=new Scanner(System.in);
    final static File FICHEIRO=new File("datos.json");
    
    static int opcion;
    
    private static void obtenerTitularesElPais() {
        URL elpais;
        try {
            elpais = new URL("http://ep00.epimg.net/rss/elpais/portada.xml");
            SAXParserFactory spf=SAXParserFactory.newInstance();
            SAXParser sp=spf.newSAXParser();
            ManejadorRss manejador=new ManejadorRss();
            sp.parse(elpais.openStream(), manejador);
                        
        } catch (MalformedURLException | SAXException | ParserConfigurationException ex) {
            Logger.getLogger(AdTarea4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Erro lectura RSS El país, revise a conexión a internet.");
        }
    }
    private static void mostrarMenu(){
        System.out.println("Operacións dispoñibles:");
        System.out.println("1.-Engadir unha tenda");
        System.out.println("2.-Mostrar as tendas");
        System.out.println("3.-Eliminar unha tenda");
        System.out.println("4.-Engadir un producto");
        System.out.println("5.-Mostrar todos os productos");
        System.out.println("6.-Mostrar os productos dunha tenda");
        System.out.println("7.-Engadir un producto a unha tenda");
        System.out.println("8.-Actualizar o stock dun producto nunha tenda");
        System.out.println("9.-Mostrar o stock dun producto dunha tenda");
        System.out.println("10.-Eliminar un producto dunha determinada tenda");
        System.out.println("11.-Eliminar un producto");
        System.out.println("12.-Engadir un cliente");
        System.out.println("13.-Mostrar os clientes");
        System.out.println("14.-Eliminar un cliente");
        System.out.println("15.-Engadir un empregado");
        System.out.println("16.-Engadir un empregado a unha tenda");
        System.out.println("17.-Mostrar lista de empregados.");
        System.out.println("18.-Eliminar un empregado.");
        System.out.println("19.-Eliminar un empregado dunha tenda.");
        System.out.println("20.-Xerar informe json co stock de todos os productos.");
        System.out.println("21.-Titulares do periódico El País");
        
        System.out.println("0.-Saír da aplicación.");
        System.out.print("Indique a operación desexada: ");
    }

    private static boolean cargaProvincias(ConHibernate conHibernate) {
        if (conHibernate.getProvincias().size()<50){
            try {
                Gson GSON=new GsonBuilder().create();
                
                File fichProv=new File("provincias.json");
                InputStreamReader iSR=new InputStreamReader(new FileInputStream(fichProv),Charset.forName("UTF-8"));
                
                Provincias provincias=GSON.fromJson(iSR, Provincias.class);
                iSR.close();
                provincias.insertarBd(conHibernate.getsF());
            } catch (FileNotFoundException ex) {
                System.out.println("Erro. Non se encontrou o ficheiro provincias.json");
                System.exit(0);
                return false;
            } catch (IOException ex) {
                return false;
            }
            return true;
            
        }else return true;
    }

    private static void continuar(){
        System.out.print("\nPulse Enter para continuar....");
        ENTRADA_TECLADO.nextLine();
        System.out.println("\n");
    }
    public static void main(String[] args) throws FileNotFoundException {
        SessionFactory sF=null;
        try{
             sF=HibernateUtil.getSessionFactory();
        }catch (org.hibernate.service.spi.ServiceException e){
            System.err.println("Erro. Non se conseguiu conectar coa base de datos.");
            System.exit(0);
        }
       
        ConHibernate conHibernate=new ConHibernate(sF);
        cargaProvincias(conHibernate);
        while(true){
            try {   
            mostrarMenu();
            opcion=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            switch(opcion){
                case 1:
                    conHibernate.insertarTenda();
                break;
                case 2:
                    conHibernate.listadoTendas();
                break;
                case 3:
                    conHibernate.eliminarTenda();                   
                break;
                case 4:
                    conHibernate.insertarProducto();
                break;
                case 5:
                    conHibernate.listadoProductos();
                break;
                case 6:
                    conHibernate.productosPorTenda();
                break;
                case 7:
                    conHibernate.insertarProductoTenda();
                break;
                case 8:
                    conHibernate.actualizarStockProductoTenda();
                break;
                case 9:
                    conHibernate.mostrarStockProductoTenda();
                break;
                case 10:
                    conHibernate.eliminarProductoTenda();
                break;
                case 11:
                    conHibernate.eliminarProducto();
                break;
                case 12:
                    conHibernate.engadirCliente();
                break;
                case 13:
                    conHibernate.listaClientes();
                break;
                case 14:
                    conHibernate.eliminarCliente();
                break;   
                case 15:
                    conHibernate.engadirEmpregado();
                    break;
                case 16:
                    conHibernate.insertarEmpregadoTenda();
                    break;
                case 17:
                    conHibernate.listaEmpregados();
                    break;
                case 18:
                    conHibernate.eliminarEmpregado();
                break;
                case 19:
                    conHibernate.eliminarEmpregadoTenda();
                break;
                case 20:
                    Informe informe=new Informe(conHibernate);
                    File informeF=new File("informe.json");
                    FileOutputStream fOS=new FileOutputStream(informeF);
                    OutputStreamWriter oSW=new OutputStreamWriter(fOS,Charset.forName("UTF-8"));
                    Gson GSON=new GsonBuilder().setPrettyPrinting().create();
                    GSON.toJson(informe,oSW);
                    oSW.close();
                    fOS.close();
                    System.out.println("Xerouse o arquivo: "+informeF);
                    
                break;
                case 21:
                    obtenerTitularesElPais();
                break;
                case 0:
                    sF.close();
                    System.exit(0);
                break;
                default:
                    System.out.println("Non existe a opción introducida.");
                break;
            }

            }catch (ConstraintViolationException e){
                if (e.getConstraintName().equals("Cemail")) System.err.println("\nErro, xa existe un cliente co email indicado.");

            }catch (java.lang.NumberFormatException e){
                StackTraceElement[] st=e.getStackTrace();
                if (st[2].getClassName().contains("Integer")) System.err.println("Valor introducido incorrecto, esperábase un número enteiro.");   
                else if ((st[2].getClassName().contains("Double")))  System.err.println("Valor introducido incorrecto, esperábase un número real.");  
                else System.err.println("Valor introducido incorrecto."); 

            }catch (java.lang.IndexOutOfBoundsException e){
                System.err.println("Selección incorrecta."); 

            } catch (Exception ex) {
                //ex.printStackTrace();
                System.err.println(ex.getMessage());
            } finally {
                
                continuar();
            }

        }
    }

    
}
