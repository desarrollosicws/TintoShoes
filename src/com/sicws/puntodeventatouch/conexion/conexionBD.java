
package com.sicws.puntodeventatouch.conexion;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.swing.JOptionPane;


public class conexionBD {
    Connection conectar = null;
    
    public Connection conexion(){
        
         File archivo = new File ("config.properties");
        try {
            FileInputStream fis;
            fis = new FileInputStream(archivo);
            Properties prop = new Properties();
            prop.load(fis);
            String nombre = prop.getProperty("usuario");
//            txtusuario.setText(nombre);
            System.out.println(nombre);
            String contra = prop.getProperty("contraseña");
            String base = prop.getProperty("ubicacionBD");
            String tipo = prop.getProperty("tipo");
            
            Properties props = new Properties();
            
            props.setProperty("user", nombre);
            props.setProperty("password", contra);
            props.setProperty("encoding", "UTF8");
            String direccionBD = "jdbc:firebirdsql://"+tipo+":3050/"+base;
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            conectar=DriverManager.getConnection(direccionBD,props);
            System.out.println("conexion");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    return conectar;
    }
}
