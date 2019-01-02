/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import forms.Tienda;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelos.tablas;
import variables.var;

/**
 *
 * @author Erick
 */
public class utiles {   
    
    public String columnasPivot(ArrayList<String> almacenes){
        String almacenesPivot="["+var.getAlmacenP()+"],";
        
        for(int i=0; i<almacenes.size(); i++){
            if(!var.getAlmacenP().equals(almacenes.get(i))){           
                almacenesPivot=almacenesPivot+"["+almacenes.get(i)+"],";
            }
        }
        almacenesPivot = almacenesPivot.substring(0,almacenesPivot.length()-1);
        System.out.println(almacenesPivot);
        return almacenesPivot;
    }
    
    
     public void LimpiarJtable(JTable tab){         
          try{
            DefaultTableModel tb = (DefaultTableModel) tab.getModel();            
            for (int i=tab.getRowCount()-1; i>=0; i--) {          
                tb.removeRow(tb.getRowCount()-1);
            }               
            tb.setColumnCount(0);
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }    
    }
     
     
     public void ajusteColumnas(JTable tb){
        final TableColumnModel columnModel = tb.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(300);         
     }
    
    public String formatoDecinal(Float num){
       DecimalFormat formato = new DecimalFormat("######.####");
       return formato.format(num);
    }
    
    public String Fecha(Date f){               
        String Fecha;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
        if(f == null){
            Date date = new Date(); 
            Fecha = formatoFecha.format(date);
        }else{
            Fecha = formatoFecha.format(f);
        }        
        System.out.println(Fecha);
        return Fecha;
    }
    
    
    public void ActualizarTablas() throws SQLException{
        tablas tb = new tablas();
        LimpiarJtable(Tienda.jtable_existencia);
        LimpiarJtable(Tienda.jTable2_Requi);
        LimpiarJtable(Tienda.jTable3_Pedido);
        tb.tablaStock();
        tb.tablaProveedor(false);
        tb.tablaVerPeidos();
    }
}
