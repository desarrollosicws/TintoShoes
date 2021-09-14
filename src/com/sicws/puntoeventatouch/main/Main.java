
package com.sicws.puntoeventatouch.main;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public final class Main {
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    public Main(){
        
        verificar();
    }
    public void verificar(){
            try {
            Statement estatusCaja = cn.createStatement();
            ResultSet rs = estatusCaja.executeQuery("select max(tipo_movto) from movtos_cajas");
            String cadena = null;
            while (rs.next()) {                
                cadena = (rs.getString("tipo_movto"));
            }
            if (cadena == "c") {
                System.out.println("CAJA CERRADA");
                System.exit(0);
            } else {
                System.out.println("CAJA ABIERTA");
                MainFrame ven = new MainFrame();
                ven.setVisible(true);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        }
    public static void main(String[] args) {
        
    }
}
