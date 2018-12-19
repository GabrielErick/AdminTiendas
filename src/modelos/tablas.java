/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dataBase.sql;
import forms.Tienda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import otros.Fechas;
import otros.utiles;
import variables.var;

/**
 *
 * @author Erick
 */
public class tablas{
    ArrayList<String> meses =  new ArrayList<>();
    public static String tienda,anio, mes0, dia0I, dia0F, mes1, dia1I, dia1F, mes2, dia2I, dia2F, F1, F2, F3, F4, F5, F6, TituloColumna1, TituloColumna2, TituloColumna3;
    Fechas fch = new Fechas();
    utiles ut = new utiles();
    DefaultTableModel tbM;
    sql sql = new sql();
    ResultSet rs;        
    
    public void FechasTablas(){
         meses.add("");
         meses.add("Enero");
         meses.add("Febrero");
         meses.add("Marzo");
         meses.add("Abril");
         meses.add("Mayo");
         meses.add("Junio");
         meses.add("Julio");
         meses.add("Agosto");
         meses.add("Septiembre");
         meses.add("Octubre");
         meses.add("Noviembre");
         meses.add("Diciembre");
                 
        mes0 =fch.mes(0);
        anio=fch.anio(Integer.parseInt(mes0));
        TituloColumna1=meses.get(Integer.parseInt(mes0))+"_"+anio;
        dia0I = fch.primerDia(anio, mes0);
        dia0F = fch.ultimoDia(anio, mes0);
        F1 = anio+mes0+dia0I;
        F2 = anio+mes0+dia0F;
        
        mes1 =fch.mes(1);
        anio=fch.anio(Integer.parseInt(mes1));
        TituloColumna2=meses.get(Integer.parseInt(mes1))+"_"+anio;
        dia1I = fch.primerDia(anio, mes1);
        dia1F = fch.ultimoDia(anio, mes1);
        F3 = anio+mes1+dia1I;
        F4 = anio+mes1+dia1F;
        
        mes2 =fch.mes(2);
        anio=fch.anio(Integer.parseInt(mes2));
        TituloColumna3=meses.get(Integer.parseInt(mes2))+"_"+anio;
        dia2I = fch.primerDia(anio, mes2);
        dia2F = fch.ultimoDia(anio, mes2);
        F5 = anio+mes2+dia2I;
        F6 = anio+mes2+dia2F;
         
//         System.out.println(TituloColumna1);
//         System.out.println(TituloColumna2);
//         System.out.println(TituloColumna3);
    }
    
    public void tablaStock() throws SQLException{
        ut.LimpiarJtable(Tienda.jtable_existencia);
        tbM = (DefaultTableModel) Tienda.jtable_existencia.getModel();
        rs = sql.StockTiendas(); 
        for(int i=1; i<=rs.getMetaData().getColumnCount(); i++){
            tbM.addColumn(rs.getMetaData().getColumnName(i));
        }       
        while(rs.next()){
            ArrayList<Object> datos = new ArrayList<>();
            for(int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                if(i<3){
                    datos.add(rs.getString(i));
                }
                                              
                if(i>2){
                    datos.add(rs.getInt(i));
                }
            }
            tbM.addRow(datos.toArray());
        }
        Tienda.jtable_existencia.setDefaultEditor(Object.class, null);  
        ut.ajusteColumnas(Tienda.jtable_existencia);
    }    
    
    public void tablaProveedor(boolean prov)throws SQLException{
        ut.LimpiarJtable(Tienda.jTable2_Requi);
        tbM = (DefaultTableModel)Tienda.jTable2_Requi.getModel();
        FechasTablas();
        rs = sql.StockTiendasProveedor(F1, F2, F3, F4, F5, F6, prov,TituloColumna1,TituloColumna2,TituloColumna3);
        for(int i=1; i<rs.getMetaData().getColumnCount(); i++){            
            tbM.addColumn(rs.getMetaData().getColumnName(i));
        }        
        while(rs.next()){
            ArrayList<Object> datos = new ArrayList<>();
            for(int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                if(i<3){
                    datos.add(rs.getString(i));
                }
                if(i>2){
                    datos.add(rs.getInt(i));
                }
                
            }
            tbM.addRow(datos.toArray());
        }
        tbM.addColumn("Pedido");    
        ut.ajusteColumnas(Tienda.jTable2_Requi);
    }    
    
    public void tablaVerPeidos()throws SQLException{
        ut.LimpiarJtable(Tienda.jTable3_Pedido);        
        tbM = (DefaultTableModel) Tienda.jTable3_Pedido.getModel();
        rs = sql.verPeidos(var.getCve_alma());
        for(int i=1; i<rs.getMetaData().getColumnCount(); i++){
            tbM.addColumn(rs.getMetaData().getColumnName(i));
        }
        while(rs.next()){
            ArrayList<Object> datos = new ArrayList<>();
            for(int i=1; i<=rs.getMetaData().getColumnCount(); i++){
                datos.add(rs.getString(i));
            }
            tbM.addRow(datos.toArray());
        }
        Tienda.jTable3_Pedido.setDefaultEditor(Object.class, null);
    }
    
}