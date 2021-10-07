
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import static com.sicws.puntodeventatouch.consultas.Descuento.cn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SaldoDiasVencidos {
    static conexionBD con = new conexionBD();
    static Connection cn = con.conexion();
    
    public void calcularSaldo(){
        int dias = 0;
        int id = 0;
        int unidades = 0;
        ArrayList<Integer> diasVencidos = new ArrayList<>();
        ArrayList<Integer> listaID = new ArrayList<>();
        ArrayList<Integer> listaUnidades = new ArrayList<>();
        try {
            PreparedStatement psUpdate = cn.prepareStatement("update DIAS_VENCIDOS SET dias_vencidos = DATEDIFF(day, FECHA_FINAL,DATE'NOW')");
            psUpdate.executeUpdate();
            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID_DIAS_VENCIDOS,DIAS_VENCIDOS,NUM_UNIDADES FROM DIAS_VENCIDOS");
            while (rs.next()) {
                dias = rs.getInt("DIAS_VENCIDOS");
                id = rs.getInt("ID_DIAS_VENCIDOS");
                unidades = rs.getInt("NUM_UNIDADES");
                System.out.println(id);
                diasVencidos.add(dias);
                listaID.add(id);
                listaUnidades.add(unidades);
            }
            
            for (int i = 0; i < listaID.size(); i++) {
                int nuevoDia = diasVencidos.get(i);
                int nuevoID = listaID.get(i);
                int nuevoUnidades = listaUnidades.get(i);
                
                if (nuevoDia<0) {
                    nuevoDia = 0;
                }
                
                int saldo = 5*nuevoDia*nuevoUnidades;
                
                System.out.println("ID: "+nuevoID+" DIA: "+nuevoDia+" SALDO: "+saldo);
                
                PreparedStatement ps = cn.prepareStatement("UPDATE DIAS_VENCIDOS SET DIAS_VENCIDOS='"+nuevoDia+"',SALDO='"+saldo+"' WHERE ID_DIAS_VENCIDOS='"+nuevoID+"'");
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaldoDiasVencidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
