
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Descuento {
    
    static conexionBD con = new conexionBD();
    static Connection cn = con.conexion();
    
    public static double getDescuento(String nombre,String cliente){
    
        double descuento = 0;
        int articuloID = 0;
        int clienteID = 0;
        
        try {
            
            /*Statement stCl = cn.createStatement();
            ResultSet rsCl = stCl.executeQuery("SELECT CLIENTE_ID FROM CLIENTES WHERE NOMBRE='"+cliente+"'");
            if (rsCl.next()) {
                clienteID = rsCl.getInt("CLIENTE_ID");
            } else {
                System.out.println("NO SE ENCONTRO EL CLIENTE");
            }
            
            Statement stClPol = cn.createStatement();
            ResultSet rsClPol = stClPol.executeQuery("SELECT POLITICA_DSCTO_ART_CLI_ID FROM PRECIOS_CLI_CLI WHERE CLIENTE_ID='"+clienteID+"'");
            int politica = 0;
            if (rsClPol.next()) {
                politica = rsClPol.getInt("POLITICA_DSCTO_ART_CLI_ID");
            } else {
                System.out.println("ESTE CLIENTE NO TIENE DESCUENTO");
                descuento = 0;
                return descuento;
            }*/
            
            //if (politica!=0) {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT ARTICULO_ID FROM ARTICULOS WHERE NOMBRE='"+nombre+"'");
                if (rs.next()) {
                    articuloID = rs.getInt("ARTICULO_ID");
                } else {
                    System.out.println("NO SE ENCONTRO EL ARTICULO "+nombre);
                }

                Statement stD = cn.createStatement();
                ResultSet rsD = stD.executeQuery("SELECT DESCUENTO FROM GET_POLS_DSCTO_PROMO ('"+articuloID+"',DATE 'NOW',null,0 )");
                if (rsD.next()) {
                    descuento = rsD.getInt("DESCUENTO");
                } else {
                    System.out.println("NO SE ENCONTRO DESCUENTO "+descuento);
                    descuento = 0;
                    return descuento;
                }
            //} 
             
        } catch (SQLException ex) {
            Logger.getLogger(Descuento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return descuento;
        
    }
    
}
