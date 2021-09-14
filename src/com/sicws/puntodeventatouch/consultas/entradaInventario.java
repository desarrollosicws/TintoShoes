
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class entradaInventario {
    
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    
    public void generarEntradaInventario(JTable tabla,JComboBox cbxCliente){
        ArrayList<String> listaAlmacenableCheck = new ArrayList<>();
        ArrayList<String> listaAlmacenable = new ArrayList<>();
        int renglon = tabla.getRowCount();
        String nombreArticulo = "";
        nombreArticulo = (String) tabla.getValueAt(0, 1);
        Formatter fmt = new Formatter();
            try {
                Statement stAlmacenableCheck = cn.createStatement();
                ResultSet rsAlmacenbaleCheck = stAlmacenableCheck.executeQuery("SELECT ES_ALMACENABLE FROM ARTICULOS WHERE NOMBRE='"+nombreArticulo+"'");
                if (rsAlmacenbaleCheck.next()) {
                    listaAlmacenableCheck.add(rsAlmacenbaleCheck.getString("ES_ALMACENABLE"));
                }
        if (renglon==1 && listaAlmacenableCheck.get(0).equals("N")) {
            System.out.println("NO SE REGISTRA ENTRADA EN INVENTARIO");
        } else {
            String consultaConceptoID = "SELECT CONCEPTO_IN_ID FROM CONCEPTOS_IN WHERE NOMBRE = 'RECEPCION MERCANCIA'";
            
            String letra = null;
            int conceptoID = 0;
            int num = 0;
        
            
            Statement stConceptoID = cn.createStatement();
            ResultSet rsConceptoID = stConceptoID.executeQuery(consultaConceptoID);
            
            while (rsConceptoID.next()) {                
                conceptoID = rsConceptoID.getInt("CONCEPTO_IN_ID");
            }
            String consultaFolio = "SELECT SERIE,CONSECUTIVO from FOLIOS_CONCEPTOS WHERE CONCEPTO_ID='"+conceptoID+"'";
            Statement stFolio = cn.createStatement();
            ResultSet rsFolio = stFolio.executeQuery(consultaFolio);
            
            while (rsFolio.next()) {                    
                letra = (rsFolio.getString("SERIE"));
                num = rsFolio.getInt("CONSECUTIVO");
            }
        
        
            fmt.format(letra+"%08d", num);
            System.out.println(fmt);
        
        
        
            PreparedStatement pstA = cn.prepareStatement("insert into doctos_in(docto_in_id, almacen_id, concepto_in_id, folio, naturaleza_concepto, fecha,descripcion ,sistema_origen,sucursal_id)\n" +
            "values(GEN_ID(ID_DOCTOS, 1), 19, '"+conceptoID+"', '"+fmt+"', 'E', date 'now', '"+cbxCliente.getSelectedItem()+"','IN',384);");
            pstA.executeUpdate();
            System.out.println("DONTOS_IN");
           
            System.out.println("rows "+tabla.getRowCount());
            
            for (int i = 0; i < tabla.getRowCount(); i++) {
                String articulo = (String) tabla.getValueAt(i, 1);
                Double unidades =  (Double) tabla.getValueAt(i, 2);
                Double costo =  Double.parseDouble((String) tabla.getValueAt(i, 5));
                Double subtotal =  Double.parseDouble((String) tabla.getValueAt(i, 7));
                System.out.println(articulo+" "+i);
                
                Statement stAlmacenable = cn.createStatement();
                ResultSet rsAlmacenbale = stAlmacenable.executeQuery("SELECT ES_ALMACENABLE FROM ARTICULOS WHERE NOMBRE='"+articulo+"'");
                if (rsAlmacenbale.next()) {
                    listaAlmacenable.add(rsAlmacenbale.getString("ES_ALMACENABLE"));
                }
                        

                if (listaAlmacenable.get(i).equals("S")) {
                    PreparedStatement psInsertDetalle = cn.prepareStatement("insert into doctos_in_det(docto_in_det_id, docto_in_id, almacen_id, concepto_in_id, clave_articulo, articulo_id, tipo_movto, unidades, costo_unitario, costo_total, metodo_costeo, rol, aplicado, fecha)\n" +
                    "values(GEN_ID(ID_DOCTOS, 1), (select max(docto_in_id) from doctos_in), 19, '"+conceptoID+"', (SELECT CLAVE_ARTICULO FROM CLAVES_ARTICULOS WHERE ARTICULO_ID=(SELECT ARTICULO_ID FROM ARTICULOS WHERE NOMBRE='"+articulo+"')), (SELECT ARTICULO_ID FROM ARTICULOS WHERE NOMBRE='"+articulo+"'), 'E', '"+unidades+"', '"+costo+"', '"+subtotal+"', 'C', 'S', 'S', date 'now');");
                    psInsertDetalle.executeUpdate();
                }
                





                /*PreparedStatement psExecuteProcedure = cn.prepareStatement("execute procedure afecta_saldos_in((SELECT ARTICULO_ID FROM ARTICULOS WHERE NOMBRE='"+articulo+"'), 19, date 'now', 'E', '"+unidades+"', '"+costo+"', 1);");
                psExecuteProcedure.executeUpdate();*/
            }
        
        
        
            PreparedStatement ejecutaAplicadocto = cn.prepareStatement("execute procedure aplica_docto_in ((select max(docto_in_id) from doctos_in));");
            ejecutaAplicadocto.executeUpdate();
            
        
            PreparedStatement psInsertFolioInv = cn.prepareStatement("INSERT INTO LIBRES_ENTRADAS_IN (DOCTO_IN_ID,FOLIO_ORDEN) VALUES ((SELECT MAX(DOCTO_IN_ID) FROM DOCTOS_IN),(SELECT MAX(FOLIO) FROM DOCTOS_PV WHERE TIPO_DOCTO='O'))");
            psInsertFolioInv.executeUpdate();
             
        
        
        
            PreparedStatement psInsertFolio = cn.prepareStatement("update folios_conceptos set CONSECUTIVO=CONSECUTIVO+1 WHERE CONCEPTO_ID='"+conceptoID+"'");
            psInsertFolio.executeUpdate();
            
        }
        
        
    
            
            
         
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el folio"+ex);
            Logger.getLogger(finalizarOrden.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
