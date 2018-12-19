/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package variables;

import java.util.ArrayList;

/**
 *
 * @author Erick
 */
public final class var {
    private static String cve_doc_Sae,solicita, entregar, prov_desc, cve_prov, fecha, almacenP;
    private static Integer cve_alma;
    private static ArrayList<Object> comboProveedores;
    private static ArrayList<String> almacenes;

    public static ArrayList<String> getAlmacenes() {
        return almacenes;
    }

    public static void setAlmacenes(ArrayList<String> almacenes) {
        var.almacenes = almacenes;
    }
    

    public static String getAlmacenP() {
        return almacenP;
    }

    public static void setAlmacenP(String almacenP) {
        var.almacenP = almacenP;
    }    
    
    public static String getCve_doc_Sae() {
        return cve_doc_Sae;
    }

    public static void setCve_doc_Sae(String cve_doc_Sae) {
        var.cve_doc_Sae = cve_doc_Sae;
    }

    public static String getSolicita() {
        return solicita;
    }

    public static void setSolicita(String solicita) {
        var.solicita = solicita;
    }

    public static String getEntregar() {
        return entregar;
    }

    public static void setEntregar(String entregar) {
        var.entregar = entregar;
    }

    public static String getProv_desc() {
        return prov_desc;
    }

    public static void setProv_desc(String prov_desc) {
        var.prov_desc = prov_desc;
    }

    public static String getCve_prov() {
        return cve_prov;
    }

    public static void setCve_prov(String cve_prov) {
        Integer cvl = Integer.parseInt(cve_prov);
        var.cve_prov = comboProveedores.get(cvl).toString();
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        var.fecha = fecha;
    }

    public static Integer getCve_alma() {
        return cve_alma;
    }

    public static void setCve_alma(Integer cve_alma) {
        var.cve_alma = cve_alma;
    }

    public static ArrayList<Object> getComboProveedores() {
        return comboProveedores;
    }

    public static void setComboProveedores(ArrayList<Object> comboProveedores) {
        var.comboProveedores = comboProveedores;
    }
}
