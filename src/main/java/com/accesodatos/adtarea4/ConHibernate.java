/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.adtarea4;

import static com.accesodatos.adtarea4.AdTarea4.ENTRADA_TECLADO;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author José Rosendo
 */
public class ConHibernate {
    private SessionFactory sF;

    public SessionFactory getsF() {
        return sF;
    }

    public ConHibernate(SessionFactory sF) {
        this.sF = sF;
    }
//PROVINCIAS    
    public  List<Provincia> getProvincias(){
        Session sesion=sF.openSession();
        Query consulta=sesion.createQuery("SELECT x FROM Provincia x");
        List<Provincia> provincias=consulta.list();
        sesion.close();
        return provincias;     
    }
    private Provincia getProvincia(int idProv) {
        Session sesion=sF.openSession();
        Provincia tmp=sesion.get(Provincia.class, idProv);
        sesion.close();
        return tmp;
        
    }
    public  void listaProvincias(){
        List<Provincia> provincias=getProvincias();
        for (Provincia p:provincias){
            System.out.print(p);
        }
    }
//CLIENTES
    public  void listaClientes() throws SQLException, Exception {
        List<Cliente> clientes=this.getClientes();
        if (clientes.isEmpty()) throw new Exception("Non existen clientes na base de datos.");
        System.out.println("Listado de clientes: "); 
        System.out.printf("%-3s %-14s %-28s %-35s\n","ID","Nome","Apelidos","e-mail");
        for (Cliente cliente:clientes){
            System.out.print(cliente);
        }
    }
    public  void engadirCliente() throws SQLException, Exception {

        System.out.print("Nome do cliente: ");
        String nome=ENTRADA_TECLADO.nextLine();

        System.out.print("Apelidos do cliente: ");
        String apelidos=ENTRADA_TECLADO.nextLine();

        System.out.print("email do cliente: ");
        String email=ENTRADA_TECLADO.nextLine();

        Cliente clienteTmp=new Cliente(nome,apelidos,email);
        
        Session sesion=sF.openSession();
        Transaction transaccion=sesion.beginTransaction();
        try{
            sesion.save(clienteTmp);
            transaccion.commit();
            System.out.println("Cliente engadido correctamente.");

        }catch (ConstraintViolationException e){
                if (e.getConstraintName().equals("Cemail")) System.err.println("\nErro, xa existe un cliente co email indicado.");
                transaccion.rollback();
        }catch (Exception e){
                transaccion.rollback();
        } finally{
                sesion.close();
        }
    }
    public  void eliminarCliente() throws Exception{
        this.listaClientes();
        System.out.print("Introduza o identificador do cliente a eliminar: ");
        int idCliente=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        Cliente tmp=this.getClientePorId(idCliente);
        if (tmp==null) System.out.println("Non se eliminou ningún cliente. Comprobe o id introducido.");
        else {
            Session sesion=sF.openSession();
            Transaction transaccion=sesion.beginTransaction();
            try{
                sesion.delete(tmp);
                transaccion.commit();
                System.out.println("Cliente eliminado correctamente.");
            }catch (Exception e){
                transaccion.rollback();
            } finally{
                sesion.close();
            }
            
        }   
    }
    public  Cliente getClientePorId(int id){
        Session sesion=sF.openSession();
        Cliente tmp;
        tmp=sesion.get(Cliente.class, id);
        sesion.close();
        return tmp;
    }
    public  List<Cliente> getClientes() throws SQLException, FileNotFoundException{
        Session sesion=sF.openSession();
        Query consultaClientes=sesion.createQuery("FROM Cliente");
        List<Cliente> clientes = consultaClientes.list();
        sesion.close();
        return clientes;
    }
//TENDAS
    public List<Tenda> getTendas(){
        Session sesion=sF.openSession();
        Query consulta=sesion.createQuery("FROM Tenda t");
        List<Tenda> tendas=consulta.list();
        sesion.close();
        return tendas;
    }

    void listadoTendas() throws Exception {
        List<Tenda> tendas=getTendas();
        if (tendas.isEmpty()) throw new Exception("Non existen tendas.");
        System.out.println("Listado de tendas: ");
        System.out.printf("%-3s %-10s %-18s %-18s\n","ID","Nome","Cidade","Provincia");
        for (Tenda t:tendas){
            System.out.print(t);
        }
    }
    void listadoTendas(List<Tenda> tendas) throws Exception {
        System.out.printf("%-3s %-10s %-18s %-18s\n","ID","Nome","Cidade","Provincia");
        for (Tenda t:tendas){
            System.out.print(t);
        }
    }
     void insertarTenda() throws Exception {
        Tenda tenda;
       
        System.out.print("Nome da tenda: ");
        String nome=ENTRADA_TECLADO.nextLine();

        System.out.print("Cidade: ");
        String cidade=ENTRADA_TECLADO.nextLine();

        listaProvincias();
        System.out.print("Id da provincia: ");
        int idProv=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        tenda=new Tenda(nome, cidade, getProvincia(idProv));
        Session sesion=sF.openSession();
        Transaction transaccion=sesion.beginTransaction();
        try{
            sesion.save(tenda);
            transaccion.commit();
            System.out.println("Tenda engadida correctamente.");
        }catch (Exception e){
                transaccion.rollback();
        } finally{
                sesion.close();
        }

        
    }

    void eliminarTenda() throws Exception {
        listadoTendas();
        System.out.print("Introduza o identificador da tenda a eliminar: "); 
        
        Session sesion=sF.openSession();
        Tenda tendaTmp=sesion.get(Tenda.class, Integer.parseInt(ENTRADA_TECLADO.nextLine()));
        
        if (tendaTmp==null) throw new Exception("Non existe unha tenda co id introducido");
        else {
            Transaction transaccion=sesion.beginTransaction();
            try{ 
                sesion.delete(tendaTmp);
                transaccion.commit();
                System.out.println("Tenda eliminada correctamente.");
            }catch (Exception e){
                transaccion.rollback();
            } finally{
                sesion.close();
            }
            
        }         
    }
//PRODUCTOS
    List<Producto> getProductos(){
        Session sesion=sF.openSession();
        Query consulta=sesion.createQuery("FROM Producto p");
        List<Producto> productos=consulta.list();
        sesion.close();
        return productos;
    }
    Producto getProducto(int idProd){
        Producto producto;
        Session sesion=sF.openSession();
        producto=sesion.get(Producto.class, idProd);
        sesion.close();
        return producto;
    }
    void listadoProductos() throws Exception {
        List<Producto> productos=getProductos();
        if (productos.isEmpty()) throw new Exception("Non existen productos na base de datos.");
        System.out.println("Listado de productos: ");
        System.out.printf("%-3s %-14s %-40s %8s\n","ID","Nome","Descripción","Prezo");
        for (Producto p: productos){
            System.out.print(p);
        }
    }
    void listadoProductos(List<Producto> productos)  {
        //System.out.println("Listado de productos: ");
        System.out.printf("%-3s %-14s %-40s %8s\n","ID","Nome","Descripción","Prezo");
        for (Producto p: productos){
            System.out.print(p);
        }
    }
        void insertarProducto() throws Exception {
        System.out.print("Nome do producto: ");
        String nome=ENTRADA_TECLADO.nextLine();

        System.out.print("Descripción: ");
        String descr=ENTRADA_TECLADO.nextLine();
        System.out.print("Prezo: ");
        String prezoString=ENTRADA_TECLADO.nextLine();
        prezoString=prezoString.replace(",", ".");
        double prezo=Double.parseDouble(prezoString);
        if (prezo<=0) throw new Exception("O prezo debe ser un valor positivo.");

        Producto tmpProd=new Producto(nome,descr,prezo);
        Session sesion=sF.openSession();
        Transaction transaccion=sesion.beginTransaction();
        try{
            sesion.save(tmpProd);
            transaccion.commit();
            System.out.println("Producto engadido correctamente.");
        }catch (Exception e){
                transaccion.rollback();
        } finally{
                sesion.close();
        }
        
    }
        
    void eliminarProducto() throws Exception {
        this.listadoProductos(getProductos());
        System.out.print("Introduza o identificador do producto a eliminar: ");     
        int idProd=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        Producto producto=getProducto(idProd);
        if (producto==null)System.out.println("Non existe un producto co id introducido.");
        else{
            Session sesion=sF.openSession();
            Transaction trans=sesion.beginTransaction();
            try {
                sesion.delete(producto);
                trans.commit();
                System.out.println("Producto eliminado correctamente.");
            }catch (Exception e){
                trans.rollback();
            } finally{
                sesion.close();
            }

            
             
        }
    }
//PRODUCTOTENDA
    void insertarProductoTenda() throws Exception {
        int idTmp;
        listadoTendas();
        System.out.print("Introduza o identificador da tenda: "); 
        idTmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        Session sesion=sF.openSession();
        
        
        Tenda tendaTmp=sesion.get(Tenda.class,idTmp);
        Query consulta=sesion.createQuery("SELECT p FROM Producto p WHERE p NOT IN (SELECT pt.producto FROM ProductoTenda pt WHERE pt.tenda=:t)");
        consulta.setEntity("t", tendaTmp);
        
        List<Producto> productos=consulta.list();
        
        if (productos.isEmpty()) {
            System.out.println("Non existen productos que se podan engadir a tenda indicada.");
            sesion.close();
        }
        else {
            System.out.println("Productos que se poden engadir: ");
            System.out.printf("%-3s %-14s %-40s %8s\n","ID","Nome","Descripción","Prezo");
            listadoProductos(productos);
            System.out.print("Identificador do producto a engadir: ");                        
            idTmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            Producto productoTmp=getProducto(idTmp);
            if (productoTmp==null) throw new Exception("Non existe o producto indicado.");
            
            System.out.print("Stock dese producto na tenda: "); 
            int stock=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            if (stock<0) throw new Exception("O stock non pode ser negativo.");
            
            ProductoTenda pT = new ProductoTenda(productoTmp,tendaTmp,stock);
            
            Transaction transaccion=sesion.beginTransaction();
                try{
                    sesion.save(pT);
                    transaccion.commit();

                    System.out.println("Producto engadido na tenda correctamente.");

                } catch (ConstraintViolationException e){
                    if (e.getConstraintName().equals("PRIMARY")) System.err.println("ERRO. Ese producto xa está asignado a tenda.");
                    transaccion.rollback();
                } catch (Exception e){
                    transaccion.rollback();
                }finally{
                     sesion.close();
            }
            
        }
    }
    
    void productosPorTenda() throws Exception{
        listadoTendas();
        System.out.print("Introduza o identificador da tenda: "); 
        int idTenda=Integer.parseInt(ENTRADA_TECLADO.nextLine()); 
        Session sesion=sF.openSession();
        listadoProductosPorTenda(sesion.get(Tenda.class, idTenda));
        sesion.close();
    }
    void listadoProductosPorTenda(Tenda tenda) throws Exception {
        if (tenda==null) throw new Exception("Non existe a tenda introducida.");
        System.out.printf("%-4s %-14s %-8s %-4s\n","ID","Nome","Prezo","Stock");
        for(ProductoTenda pt:tenda.getTproductos()){
            System.out.print(pt.toStringProducto());
        }
    }

    void eliminarProductoTenda() throws Exception {
        int idTmp;
        listadoProductos();
        System.out.print("Introduza o identificador do producto a eliminar: "); 
        idTmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        Session sesion=sF.openSession();
        Producto productoTmp=sesion.get(Producto.class, idTmp);
        
        System.out.println(productoTmp.toString());
        System.out.println(productoTmp.Ptendas.size());
        List<ProductoTenda> pT=productoTmp.getPtendas();
             
        if (pT.isEmpty()) {
            System.out.println("O producto non está asociado a ningunha tenda ou non existe.");
            sesion.close();
        }
        else {
            System.out.println("Listado de tendas con ese producto: ");
            System.out.printf("%-3s %-10s %-18s %-18s\n","ID","Nome","Cidade","Provincia");
            for(ProductoTenda pt:pT){
                System.out.print(pt.toStringTenda());          
            }
            System.out.print("Introduza o identificador da tenda na que eliminar o producto: "); 
            idTmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            Tenda tendaTmp=sesion.get(Tenda.class, idTmp);
            ProductoTenda pTE=sesion.get(ProductoTenda.class, new ProductoTenda(productoTmp,tendaTmp));
            if (tendaTmp==null) throw new Exception("Non existe unha tenda co id indicado.");
            else if (pTE==null) throw new Exception("A tenda seleccionada non dispón dese producto.");
            
            

            Transaction transaccion=sesion.beginTransaction();
            try{
                Query consulta=sesion.createQuery("DELETE FROM ProductoTenda pT WHERE pT.tenda=:t AND pT.producto=:p");
                consulta.setEntity("t", pTE.getTenda());
                consulta.setEntity("p", pTE.getProducto());
                
                if (consulta.executeUpdate()!=0) System.out.println("Producto eliminado da tenda indicada.");
                transaccion.commit();
            
            } catch (HibernateException e){
                    transaccion.rollback();

            }finally{
                     sesion.close();
            }

            
        }
    }
         
    void mostrarStockProductoTenda() throws Exception {
        listadoTendas();
        System.out.print("Introduza o identificador da tenda: "); 
        int idtmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        Session sesion=sF.openSession();
        Tenda tenda=sesion.get(Tenda.class, idtmp);
        if (tenda==null) throw new Exception("Non existe a tenda indicado.");
        
        
        
        
        if (tenda.getTproductos().isEmpty()) {
            System.out.println("A tenda indicada non dispón de productos.");
        } else {
            System.out.println("Productos: ");
            listadoProductosPorTenda(tenda);

            System.out.print("Identificador do producto: ");     
            idtmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            
            
            ProductoTenda pT=sesion.get(ProductoTenda.class, new ProductoTenda(getProducto(idtmp),tenda));
            if (pT==null) System.out.println("Identificador de producto incorrecto, non existe na tenda indicada.");
            else System.out.printf("Stock dese producto na tenda: %2s\n",pT.getStock());

            sesion.close();
    }
    }
    
    void actualizarStockProductoTenda() throws Exception {
        listadoTendas();
        System.out.print("Introduza o identificador da tenda: "); 
        int idtmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        Session sesion=sF.openSession();
        Tenda tenda=sesion.get(Tenda.class, idtmp);
        if (tenda==null) throw new Exception("Non existe a tenda indicado.");
        
        
        
        
        if (tenda.getTproductos().isEmpty()) {
            System.out.println("A tenda indicada non dispón de productos.");
        } else {
            System.out.println("Productos: ");
            listadoProductosPorTenda(tenda);

            System.out.print("Identificador do producto: ");     
            idtmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            
            
            ProductoTenda pT=sesion.get(ProductoTenda.class, new ProductoTenda(getProducto(idtmp),tenda));
            if (pT==null) System.out.println("Identificador de producto incorrecto, non existe na tenda indicada.");
            else {
                System.out.print("Novo valor de stock: "); 
                idtmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
                Transaction transaccion=sesion.beginTransaction();
                try{
                    pT.setStock(idtmp);
                    transaccion.commit();
                    System.out.println("Stock do producto na tenda actualizado.");
                }catch(Exception e) {
                    System.err.println(e);
                    transaccion.rollback();
                }

                
            }

            sesion.close();
    }
    }


     
//EMPREGADOS
    List<Empregado> getEmpregados(){
        Session sesion=sF.openSession();
        Query consulta=sesion.createQuery("FROM Empregado e");
        List<Empregado> empregados=consulta.list();
        sesion.close();
        return empregados;
    }
    Empregado getEmpregado(int idEmp){
        Empregado empregado;
        Session sesion=sF.openSession();
        empregado=sesion.get(Empregado.class, idEmp);
        sesion.close();
        return empregado;
    }
    void engadirEmpregado() throws Exception {
        Empregado empregado;
        System.out.print("Nome do empregado: ");
        String nome=ENTRADA_TECLADO.nextLine();
        System.out.print("Apelidos do empregado: ");
        String apelido=ENTRADA_TECLADO.nextLine();
        empregado=new Empregado(nome,apelido);
        
        Session sesion=sF.openSession();
        Transaction transaccion=sesion.beginTransaction();
        try{
        sesion.save(empregado);
        transaccion.commit();
        System.out.println("Empregado engadido correctamente.");
        } catch (Exception e){
            transaccion.rollback();
        }
        sesion.close();
        
    }

    void listaEmpregados() throws Exception {
        List<Empregado> empregados=getEmpregados();
        if (empregados.isEmpty()) throw new Exception("Non existen empregados na base de datos.");
        System.out.println("Lista de empregados: ");
        System.out.printf("%-3s %-14s %-28s\n","ID","Nome","Apelidos");
        for (Empregado e:empregados){
            System.out.print(e);
        }
    }

    void eliminarEmpregado() throws Exception {
        this.listaEmpregados();
        System.out.print("Introduza o identificador do empregado a eliminar: ");
        int idEmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        Session sesion=sF.openSession();
        Empregado empregado=sesion.get(Empregado.class, idEmp);
        if (empregado==null)System.out.println("Non existe un empregado co id introducido.");
        else{
           Transaction transaccion=sesion.beginTransaction();
           try{
               sesion.delete(empregado);
                transaccion.commit();
                System.out.println("Empregado eliminado correctamente.");
           }catch (Exception e){
               transaccion.rollback();
           }         
            sesion.close();          
        }
    }

    void insertarEmpregadoTenda() throws Exception {
        listaEmpregados();
        
        System.out.print("ID do empregado a incluir como traballador nunha tenda: ");
        int idEmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());     
        
        Session sesion=sF.openSession();
        Empregado empregado=sesion.get(Empregado.class, idEmp);
        
        if (empregado==null) throw new Exception("Non existe un empregado co id indicado.");
        
        Query consulta=sesion.createQuery("SELECT t FROM Tenda t WHERE t NOT IN (SELECT eT.tenda_ FROM EmpregadoTenda eT WHERE eT.empregado=:e)");
        consulta.setEntity("e", empregado);
        List<Tenda> tendas=consulta.list();
        
        if (tendas.isEmpty()) {
            System.out.println("Non existen tendas nas que poder incluir o empregado.");
        } else {
            System.out.println("Tendas nas que se pode incluir o traballador: ");
            listadoTendas(tendas);

            System.out.print("Identificador da tenda na que quere incluir o traballador: ");  
            int idTenda=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            Tenda tenda=sesion.get(Tenda.class, idTenda);
            
            if (tenda==null) throw new Exception("Erro. A tenda indicada non existe.");
            else if (!tendas.contains(tenda)) throw new Exception("Erro. O empregado xa traballa na tenda indicada.");
            
            System.out.print("Número de horas de traballo semanais: "); 
            int horas=Integer.parseInt(ENTRADA_TECLADO.nextLine());
            if (horas<=0) throw new Exception("Erro. O número de horas debe ser un enteiro positivo.");
            
            EmpregadoTenda eT=new EmpregadoTenda(empregado,tenda,horas);
            Transaction transaccion=sesion.beginTransaction();
            try{
                sesion.save(eT);
                transaccion.commit();
                System.out.println("Empregado incluido na tenda correctamente.");
            }catch (Exception e){
               transaccion.rollback();
            }        
            sesion.close();
        } 
    }

    void eliminarEmpregadoTenda() throws Exception {
        listadoTendas();
        System.out.print("Introduza o identificador da tenda: "); 
        int idTenda=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        
        Session sesion=sF.openSession();
        Tenda tenda=sesion.get(Tenda.class, idTenda);
        
        if (tenda==null) throw new Exception("Erro. A tenda indicada non existe.");
        
        ArrayList<Empregado> empregados=(ArrayList<Empregado>)tenda.getEmpregados();
       
        if(empregados.isEmpty()) System.out.println("A tenda indicada non ten ningún empregado asignado.");     
        else {
            System.out.printf("%-3s %-14s %-28s\n","ID","Nome","Apelidos");
            for(Empregado e:empregados){
                System.out.print(e);
            }
        }
        System.out.print("Identificador do empregado a eliminar: ");
        int idEmp=Integer.parseInt(ENTRADA_TECLADO.nextLine());
        Empregado empregado=sesion.get(Empregado.class, idEmp);
        
        if(!empregados.contains(empregado)) System.out.println("Erro. O empregado non traballa na tenda indicada ou non existe.");
        else {
            Transaction transaccion=sesion.beginTransaction();
            try{
                EmpregadoTenda eT=sesion.get(EmpregadoTenda.class,new EmpregadoTenda(empregado,tenda));
                empregado.getEmpregadosTendas().remove(eT);
                tenda.getEmpregadosTendas().remove(eT); 
                sesion.delete(eT);
                transaccion.commit();
                System.out.println("Empregado eliminado.");
            }catch (Exception e){
               transaccion.rollback();
            }        

        }
            
        sesion.close();
        
         
    }

        
        


}







