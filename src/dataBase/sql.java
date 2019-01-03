/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import variables.var;
import otros.utiles;

/**
 *
 * @author Erick
 */
public class sql {
    CallableStatement cts;
    Statement st;
    ResultSet rs;
    Connection cn;
    utiles ut = new utiles();
    //metodo conectar con SQL
    public Connection conectar (String db, String uss, String pass){
       try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn=DriverManager.getConnection("jdbc:sqlserver://181.189.128.94:1433;databaseName="+db,uss,pass);         
        }catch(ClassNotFoundException | SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, "ERROR DE CONECXION: "+e.getMessage());
        }
        return cn;
    }
    
    public ResultSet SeleccionarTienda(String db, String uss, String pass)throws SQLException{
        String query="SELECT NUMERO_ALMACEN, DESCRIPCION FROM TIENDAS";
        st=this.conectar(db,uss,pass).createStatement();
        rs=st.executeQuery(query);
        return rs;
    }
    
    public ResultSet SeleccionarProveedor()throws SQLException{
        String query = "SELECT CLAVE\n" +
                        "     ,NOMBRE \n" +
                        "FROM [SAE5_GT].[dbo].[PROV01] \n" +
                        "WHERE STATUS LIKE 'A%'  AND LOWER(LEFT(CLASIFIC,1)) <> 'a'";
        st = this.conectar("SAE5_GT", "sa", "Grupo2014").createStatement();
        rs = st.executeQuery(query);
        return rs;
    }
    
    public ResultSet StockTiendas ()throws SQLException{
        cts =  this.conectar("GTUCANAPP", "123", "1").prepareCall("EXEC [dbo].[STOCK_TIENDAS]"
                                                                + "@ALMACENES =N'"+ut.columnasPivot(var.getAlmacenes(), false)+"',"
                                                                + "@TIENDA_P =N'"+var.getAlmacenP()+"'");
        rs = cts.executeQuery();
        return rs;
    }
    
    public ResultSet StockTiendasProveedor(String f1,String f2,String f3,String f4,String f5,String f6, Boolean prov, String T1, String T2, String T3)throws SQLException{
        String condP;
        if(prov){
            condP = " E.CLAVE LIKE (''%"+var.getCve_prov().trim()+"'')";
            
        }else{
            condP = " E.NOMBRE IS NULL";
        }
//        System.out.println(condP);
        cts = this.conectar("GTUCANAPP", "123", "1").prepareCall("EXEC [dbo].[STOCK_TIENDAS_PROVEEDOR]"
                                                                +"@CLAVE_ALMACEN=N'"+var.getCve_alma()+"',"                                                                
                                                                +"@ALMACENES_PIVOT=N'"+ut.columnasPivot(var.getAlmacenes(), true)+"',"
                                                                +"@ALMA =N'"+var.getAlmacenP()+"',"                                                                
                                                                +"@F1=N'"+f1+"',"
                                                                +"@F2=N'"+f2+"',"
                                                                +"@F3=N'"+f3+"',"
                                                                +"@F4=N'"+f4+"',"
                                                                +"@F5=N'"+f5+"',"
                                                                +"@F6=N'"+f6+"',"
                                                                +"@PROV_COND=N'"+condP+"',"
                                                                +"@TITULO1=N'"+T1+"',"
                                                                +"@TITULO2=N'"+T2+"',"
                                                                +"@TITULO3=N'"+T3+"'");
        rs = cts.executeQuery();
        System.out.println(var.getCve_alma()+ut.columnasPivot(var.getAlmacenes(), true)+var.getAlmacenP()+" - "+f1+f2+f3+f4+f5+f6+" - "+prov+T1+T2+T3+" - "+condP);
        return rs;
    }
    
    public ResultSet verPeidos (Integer almacen)throws SQLException{
        String query = "SELECT \n" +
                            " [CVE_DOC_SAE]\n" +
                            ",[SOLICITA]\n" +
                            ",[ENTREGAR_A]\n" +
                            ",B.NOMBRE AS PROVEEDOR\n" +
                            ",[FECHA_DOC]\n" +
                        "FROM [GTUCANAPP].[dbo].[REQUISICIONES_ENCA_APP] AS A\n" +
                        "LEFT JOIN [SAE5_GT].[dbo].[PROV01] AS B ON A.CVE_PROVEEDOR = B.CLAVE\n" +
                        "WHERE  CVE_ALMACEN = "+almacen;
        st = this.conectar("GTUCANAPP", "123", "1").createStatement();
        rs = st.executeQuery(query);
        return rs;
    }
    
    
     public Integer ultimoDocumnto()throws SQLException{
         String query ="SELECT ULT_DOC FROM FOLIOSC01 WHERE TIP_DOC LIKE '%q%'";
         st = this.conectar("SAE5_GT", "sa", "Grupo2014").createStatement();
         rs=st.executeQuery(query); 
         Integer ultDoc = 0, nuevoDoc=0;
         while(rs.next()){
            ultDoc = Integer.parseInt(rs.getString(1));
         }
         nuevoDoc = ultDoc+1;
//         System.out.println(nuevoDoc);
         String queryUltmodoc ="UPDATE FOLIOSC01 SET ULT_DOC = "+nuevoDoc+",  FECH_ULT_DOC = GETDATE() WHERE TIP_DOC LIKE 'q'";
         st.execute(queryUltmodoc);
         this.cerrar();
         return nuevoDoc;
     }
    
     
     public void insertarEncabezadoxProvee(Object[][] detalle, Integer tipCreacion,  Integer NumPartidas)throws SQLException{ 
        if(var.getCve_prov() == null){
            var.setCve_prov("");
//            System.out.println(var.getCve_prov()+"v");
        }
        String ultimoDoc = String.valueOf(this.ultimoDocumnto());
        String ceros="";
//        System.out.println(ultimoDoc.length());
        if(ultimoDoc.length()<10){
            for(int i=ultimoDoc.length()+1;i<=10;i++){
//                System.out.println(i);
                ceros = ceros + "0";
            }
//            System.out.println(ceros+ultimoDoc);
        }
        String cve_DocSae = "          "+ceros+ultimoDoc; 
        String cve_DocApp = cve_DocSae;
        String query = "INSERT INTO REQUISICIONES_ENCA_APP VALUES ('"+cve_DocSae+"','"+cve_DocApp+"','"+var.getSolicita()+"','"+var.getEntregar()+"','"+var.getCve_prov()+"','"+ut.Fecha(null)+"',"+var.getCve_alma()+",'"+tipCreacion+"',0);";
        st = this.conectar("GTUCANAPP", "123", "1").createStatement();
        st.execute(query);
        insertarDetallexProvee(NumPartidas, detalle, tipCreacion, cve_DocApp);
        SincronizarSae();
     }
//     
     public void insertarDetallexProvee(Integer numPartidas,  Object[][] detalle, Integer tipCreacion, String cveDocApp)throws SQLException{
         Integer ii=0, np=0;
         String query;
         st = this.conectar("GTUCANAPP", "123", "1").createStatement();
//         System.out.println(cveDocApp);
         do{                
            np+=1;           
            query ="INSERT INTO REQUISICIONES_DETA_APP VALUES("+np+","+var.getCve_alma()+",'"+var.getCve_prov()+"','"+detalle[0][ii]+"',"+detalle[1][ii]+","+detalle[2][ii]+","+detalle[3][ii]+","+tipCreacion+",'"+cveDocApp+"');";
//             System.out.println(query);
            st.execute(query);
            ii+=1;
         }while(ii<numPartidas);     
     }
     
    public void SincronizarSae()throws SQLException{
        cts = this.conectar("GTUCANAPP", "123", "1").prepareCall("{call APP_RQ_SAE()}");
        cts.execute();
    }
    
    public void cerrar ()throws SQLException{
        cn.close();
    }
}
