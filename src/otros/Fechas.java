/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Erick
 */
public class Fechas {
         //metodo para obtener el año anterior    
    public String anio (Integer NumeroMes){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");  
        //Calendar date = null;
        Calendar fecha;
        fecha = Calendar.getInstance();
        if(NumeroMes>10){
            fecha.add(Calendar.YEAR, 0);
        }else{
            fecha.add(Calendar.YEAR, -1);
        }
//        System.out.println("Año: "+formatter.format(fecha.getTime()));
        return formatter.format(fecha.getTime());
    }
    //metodo para obtener el mes actual
    public String mes (int NumMes){
        SimpleDateFormat formatter = new SimpleDateFormat("MM");  
        //Calendar date = null;
        Calendar fecha;
        fecha = Calendar.getInstance();
        switch (NumMes) {
            case 0:
                fecha.add(Calendar.MONTH, 0);
                break;
            case 1:
                fecha.add(Calendar.MONTH, +1);
                break;
            case 2:
                fecha.add(Calendar.MONTH, +2);
                break;
            default:
                break;
        }
//        System.out.println("Mes: "+formatter.format(fecha.getTime()));
        return formatter.format(fecha.getTime());
    }
    //metodo para obtener el primner dia del mes
    public String primerDia(String anio, String mes){
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        int a,m;
        a = Integer.parseInt(anio);
        m = Integer.parseInt(mes);
        Calendar fecha;
        fecha = Calendar.getInstance();
        fecha.set(a, m, 1);
        fecha.getActualMinimum(Calendar.DAY_OF_MONTH);
//        System.out.println("Dia de inicio: "+formatter.format(fecha.getTime()));
        return formatter.format(fecha.getTime());
    }
    
    //metodo para obtener el dia ultimo dia del mes
    public String ultimoDia(String anio, String mes){
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        int a,m;
        a = Integer.parseInt(anio);
        m = Integer.parseInt(mes);
        Calendar fecha;
        fecha = Calendar.getInstance();
        fecha.set(a, m, 0);
        fecha.getActualMinimum(Calendar.DAY_OF_MONTH);
//        System.out.println("Dia de fin: "+formatter.format(fecha.getTime()));
        return formatter.format(fecha.getTime());
    }
}
