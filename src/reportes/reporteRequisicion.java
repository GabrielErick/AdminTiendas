/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Erick
 */
public abstract class reporteRequisicion {
    private static JasperReport jsr;
    private static JasperPrint jsp;
    private static JasperViewer jsv;
    
    public static void crearReporte(Connection con ,File ruta, String cveSae){
        try{
            Map docSae = new HashMap();
            
            docSae.put("cveDocSae", cveSae); 
            jsr =(JasperReport) JRLoader.loadObjectFromFile(ruta.getAbsolutePath());
//            System.out.println(docSae);
            jsp = JasperFillManager.fillReport(jsr,docSae,con);
        }catch(JRException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public static void mostrarReporte(){
        jsv = new JasperViewer(jsp,false);
        jsv.setVisible(true);
    }
}
