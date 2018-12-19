/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dataBase.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import variables.var;

/**
 *
 * @author Erick
 */
public class combo {
    sql db = new sql();
    ResultSet rs;
    ArrayList<Object>cveProveedores = new ArrayList<>();
    
    public combo(JComboBox jcbx, Integer i)throws SQLException{//i para ver que caso toca 
        switch(i){
            case (1):
                rs = db.SeleccionarTienda("GTUCANAPP", "123", "1");
                while(rs.next()){   
                        //var.setAlmacenes((ArrayList<Object>) (Object) rs.getString(2));
                        jcbx.addItem(rs.getString(1).trim()+"-"+rs.getString(2));
                    } 
            break;
            case(2):
                try{
                    jcbx.addItem("Sin proveedor");
                    cveProveedores.add("");
                    rs = db.SeleccionarProveedor();
                    while(rs.next()){
                        jcbx.addItem(rs.getString(2));
                        cveProveedores.add(rs.getString(1));
                    }
                    var.setComboProveedores(cveProveedores);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Error Combo Proveedores: "+e.getMessage());
                }
            break;
        }
    }
}
