//package puntodeventatouch;
//import com.sicws.puntodeventatouch.conexion.conexionBD;
//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.view.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;
//
//
//public class IniciarReporte {
//    conexionBD con = new conexionBD();
//    Connection cn = con.conexion();
//public static final String DRIVER="com.mysql.jdbc.Driver";
//public static final String RUTA="jdbc:mysql://localhost/Factura";
//public static final String USER="root";
//public static final String PASSWORD="root";
//public static Connection CONEXION;
//public void EjecutarReporte(int Factura) throws ClassNotFoundException, SQLException
//{
//
//try
//{
//
//String template="C:\\Users\\ASUS\\Documents\\NetBeansProjects\\PuntodeVentaTouch\\src\\puntodeventatouch\\newReport.jrxml";
//
//JasperReport reporte=(JasperReport) JRLoader.loadObject(template);
//
//
//
//JasperPrint jasperprint= JasperFillManager.fillReport(reporte,null,cn);
//JasperViewer visor=new JasperViewer(jasperprint,false);
//visor.setTitle("Geniz Reportes - GSF");
//visor.setVisible(true);
//}
//catch(Exception j)
//{
//JOptionPane.showMessageDialog(null, "Problemas al generar el reporte. \n Detalles: " + j);
//
//}
//}
//}