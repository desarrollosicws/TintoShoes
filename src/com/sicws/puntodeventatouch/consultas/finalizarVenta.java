
package com.sicws.puntodeventatouch.consultas;

import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.sicws.puntodeventatouch.conexion.*;
import com.sicws.puntodeventatouch.ticket.imprimirTicketVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class finalizarVenta {
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    Statement st;
    DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
    
    private static DecimalFormat df = new DecimalFormat("0.00");
    public void liquidarVenta(JLabel lblcambio,JLabel lbltotal,JTextField txtimporte,JTable tabla,JComboBox cbxCliente,JComboBox cbxFormas,JTextField txtAnticipo,int id){
        imprimirTicketVenta imp = new imprimirTicketVenta();
        Formatter fmt = new Formatter();
        DefaultTableModel model= (DefaultTableModel) tabla.getModel();
        int posicion = 1;
        Double cambio = Double.parseDouble(lblcambio.getText());
        Double total = Double.parseDouble(lbltotal.getText());
        Double importe = Double.parseDouble(txtimporte.getText());
        Double anticipo = Double.parseDouble(txtAnticipo.getText());
        Double porcentajeDescuento = 0.0;
        String tipoDescuento = "P";
        if (anticipo!=0) {
            porcentajeDescuento = 100*anticipo/anticipo+total;
        }
        //Double porcentajeDescuento = 100*anticipo/anticipo+total;
        
        
        if (anticipo==0 && porcentajeDescuento ==0) {
            tipoDescuento = "I";
        }
        Double resultado = total / 1.16;
        Double impuesto = total-resultado;
        DecimalFormat formato1 = new DecimalFormat("#.00", separadoresPersonalizados);
        DecimalFormat formato2 = new DecimalFormat("#.000000", separadoresPersonalizados);
        
        try {
            Statement folioST = cn.createStatement();
            ResultSet rs = folioST.executeQuery("SELECT MAX(folio) FROM doctos_pv WHERE tipo_docto='V'");
            String cadena = null;
            while (rs.next()) {                
                cadena = (rs.getString("MAX"));
            }
            int numeros = Integer.parseInt(getNumeros(cadena));
            int suma = numeros+1;
            fmt.format("A%08d", suma);
            System.out.println("FOLIO "+fmt);
            System.out.println("AQUI ES EL IMPORTE"+importe);
            System.out.println("AQUI ES EL CAMBIO"+cambio);
            System.out.println("AQUI ES EL TOTAL"+total);
            System.out.println("AQUI ES EL ANTICIPO"+anticipo);
            PreparedStatement primeraConsulta = cn.prepareStatement("execute block as begin\n" +
            "insert into doctos_pv (docto_pv_id, caja_id, tipo_docto,sucursal_id, folio, fecha, hora, cajero_id,clave_cliente, cliente_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
            "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen, es_fac_global,\n" +
            "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
            "values (GEN_ID(ID_DOCTOS, 1), 3297, 'V',384 ,'"+fmt+"', date 'now', time 'now', 3319,(SELECT CLAVE_CLIENTE FROM claves_clientes WHERE CLIENTE_ID = (SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"'), 19,1,'N', 1,'"+tipoDescuento+"' ,'"+porcentajeDescuento+"', '"+anticipo+"', 'N', '"+formato1.format(total)+"', 0, 0, 0,\n" +
            "'PV', 'N', 'CAJA', 'N', '"+formato2.format(resultado)+"', 'CAJA');\n" +
            "end");
            primeraConsulta.executeUpdate();
            if (total>0 && anticipo==0 || total>0 && anticipo>0){
                PreparedStatement insertCobro = cn.prepareStatement("INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'C',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxFormas.getSelectedItem()+"'),'"+df.format(importe)+"','1.000000','"+df.format(importe)+"' );");
                insertCobro.executeUpdate();
            }
            if (cambio!=0) {
            PreparedStatement segundaConsulta = cn.prepareStatement("execute block as begin\n"
            +"INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'A',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxFormas.getSelectedItem()+"'),'"+df.format(cambio)+"','1.000000','"+df.format(cambio)+"' );\n"
             + "end");
            segundaConsulta.executeUpdate();
            }
            
            for (int i = 0; i < tabla.getRowCount(); i++) {
                String articulo = (String) tabla.getValueAt(i, 1);
                Double unidades = (Double) tabla.getValueAt(i, 2);
                String notas = (String) tabla.getValueAt(i, 3);
                Double precio = (Double) tabla.getValueAt(i, 4);
                Double subtotal = (Double) tabla.getValueAt(i, 5);
                
                
                PreparedStatement insertarPVdet = cn.prepareStatement("INSERT INTO doctos_pv_det (docto_pv_det_id, docto_pv_id, clave_articulo, articulo_id, unidades, unidades_dev,tipo_contab_unid,precio_unitario,\n" +
                "precio_unitario_impto, impuesto_por_unidad, pctje_dscto, precio_total_neto, precio_modificado, pctje_comis,\n" +
                "rol,notas ,posicion, dscto_art, dscto_extra)\n" +
                "values (gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv WHERE tipo_docto='V' AND caja_id='3297'),(select clave_articulo from claves_articulos WHERE articulo_id = (select articulo_id from articulos where nombre = '"+articulo+"')), (select articulo_id from articulos WHERE nombre='"+articulo+"'), '"+unidades+"', 0,1, '"+precio+"', '"+precio+"',0, 0, '"+subtotal+"', 'N',\n" +
                "0, 'N','"+notas+"' ,'"+posicion+"', 0, 0);");
                posicion++;
                insertarPVdet.executeUpdate();
            }
            PreparedStatement ejecutaAplicadocto = cn.prepareStatement("execute procedure aplica_docto_pv ((select max(docto_pv_id) from doctos_pv));");
            ejecutaAplicadocto.executeUpdate();
            PreparedStatement ejecutaCalculatotal = cn.prepareStatement("execute procedure calc_totales_docto_pv ((select max(docto_pv_id) from doctos_pv),'N','N','1');");
            boolean resultados = ejecutaCalculatotal.execute();
            while (resultados) {                
                ResultSet resultS = ejecutaCalculatotal.getResultSet();
                while (resultS.next()) {                     
                }
                resultS.close();
                resultados = ejecutaCalculatotal.getMoreResults();
            }
            ejecutaCalculatotal.close();
            for (int i = tabla.getRowCount()-1; i>=0; i--) {
                model.removeRow(i);
            }
            
            PreparedStatement deleteOrdenPV = cn.prepareStatement("DELETE FROM DOCTOS_PV WHERE DOCTO_PV_ID='"+id+"'");
            deleteOrdenPV.executeUpdate();
            
            PreparedStatement deleteOrdenPVDetalle = cn.prepareStatement("DELETE FROM DOCTOS_PV_DET WHERE DOCTO_PV_ID='"+id+"'");
            deleteOrdenPVDetalle.executeUpdate();
            lbltotal.setText("0.0");
            lblcambio.setText("0.0");
            txtimporte.setText("0.0");
            JOptionPane.showMessageDialog(null, "VENTA FINALIZADA");
            System.out.println(fmt);
            imp.impresion(cn,String.valueOf(fmt));
            
        } catch (SQLException ex) {
            Logger.getLogger(finalizarVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static String getNumeros(String cadena){
    char[] cadena_div = cadena.toCharArray();
                            String n="";
                            for (int j = 0; j < cadena_div.length; j++) {
                                if(Character.isDigit(cadena_div[j])){
                                n+=cadena_div[j];
                                } 
                            }
            return n;
    }
    
}