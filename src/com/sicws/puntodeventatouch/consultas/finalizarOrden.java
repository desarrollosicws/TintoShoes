/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import com.sicws.puntodeventatouch.ticket.imprimirTicket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class finalizarOrden {
    
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    private static DecimalFormat df = new DecimalFormat("0.00");
    
    public void generarOrdenVenta(JLabel lblcambio,JLabel lbltotal,JTextField txtimporte,JTextField txtAnticipo,JTable tabla,JComboBox cbxCliente,JComboBox cbxFormas,String not){
        imprimirTicket imp = new imprimirTicket();
        DefaultTableModel model= (DefaultTableModel) tabla.getModel();
        Double importe = Double.parseDouble(txtimporte.getText());
        Double total = Double.parseDouble(lbltotal.getText());
        Double anticipo = Double.parseDouble(txtAnticipo.getText());
        Double cambio = Double.parseDouble(lblcambio.getText());
        Formatter fmt = new Formatter();
        Formatter fmtIngreso = new Formatter();
        String consultaFolio = "SELECT SERIE,CONSECUTIVO from FOLIOS_CAJAS WHERE TIPO_DOCTO='O' AND CAJA_ID=3297";
        String letra = null;
        int num = 0;
        
        try {
            Statement stFolio = cn.createStatement();
            ResultSet rsFolio = stFolio.executeQuery(consultaFolio);
            
            while (rsFolio.next()) {                    
                letra = (rsFolio.getString("SERIE"));
                num = rsFolio.getInt("CONSECUTIVO");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Buscar el folio de la caja"+ex);
            Logger.getLogger(finalizarOrden.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fmt.format(letra+"%08d", num);
        System.out.println(fmt);
        
        try {
            PreparedStatement insertDoctoVE = cn.prepareStatement("INSERT into doctos_pv (docto_pv_id, caja_id, tipo_docto,sucursal_id ,folio, fecha, hora, cajero_id,clave_cliente, cliente_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
                    "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen,persona ,es_fac_global,\n" +
                    "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
                    "values (GEN_ID(ID_DOCTOS, 1), 3297, 'O',384, '"+fmt+"', date 'now', time 'now', 3319,(SELECT CLAVE_CLIENTE FROM claves_clientes WHERE\n" +
                    "CLIENTE_ID = (SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"'), 19,1,'S', 1,'P' ,0, 0, 'P', '"+total+"', 0, 0, 0,\n" +
                    "'PV','"+anticipo+"' ,'N', 'CAJA', 'N', 0, 'CAJA')");
            insertDoctoVE.executeUpdate();
            if (anticipo>0) {
                
                String consultaFolioIngreso = "SELECT SERIE,CONSECUTIVO from FOLIOS_CAJAS WHERE TIPO_DOCTO='I' AND CAJA_ID=3297";
                String letraIngreso = null;
                int numIngreso = 0;
                Statement stFolioIngreso = cn.createStatement();
            ResultSet rsFolioIngreso = stFolioIngreso.executeQuery(consultaFolioIngreso);
            while (rsFolioIngreso.next()) {                    
                letraIngreso = (rsFolioIngreso.getString("SERIE"));
                numIngreso = rsFolioIngreso.getInt("CONSECUTIVO");
            }
            fmtIngreso.format(letraIngreso+"%08d", numIngreso);
            System.out.println("FOLIO INGRESO: "+fmtIngreso);
                
                PreparedStatement insertDoctoVEAnticipo = cn.prepareStatement("INSERT into doctos_pv (docto_pv_id, caja_id, tipo_docto,sucursal_id ,folio, fecha, hora, cajero_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
                    "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen,persona ,es_fac_global,refer_reting,\n" +
                    "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
                    "values (GEN_ID(ID_DOCTOS, 1), 3297, 'I',384, '"+fmtIngreso+"', date 'now', time 'now', 3319, 19,1,'S', 1,'P' ,0, 0, 'P', '"+anticipo+"', 0, 0, 0,\n" +
                    "'PV','"+cbxCliente.getSelectedItem()+"' ,'N','"+fmt+"' ,'CAJA', 'N', 0, 'CAJA')");
                insertDoctoVEAnticipo.executeUpdate();
                
                PreparedStatement insertDoctoPVCobroAnticipo = cn.prepareStatement("INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
                "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv where tipo_docto='I'),'I',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxFormas.getSelectedItem()+"'),'"+df.format(anticipo)+"','1.000000','"+df.format(anticipo)+"' );\n");
                insertDoctoPVCobroAnticipo.executeUpdate();
                
                PreparedStatement ejecutaAplicadocto = cn.prepareStatement("execute procedure aplica_docto_pv ((select max(docto_pv_id) from doctos_pv where tipo_docto='I'));");
                ejecutaAplicadocto.executeUpdate();
                
                PreparedStatement psUpdateFolioIngreso = cn.prepareStatement("update folios_cajas set CONSECUTIVO=CONSECUTIVO+1 WHERE CAJA_ID='3297' AND TIPO_DOCTO='I'");
                psUpdateFolioIngreso.executeUpdate();
                
                /*PreparedStatement insertAnticipoCobro = cn.prepareStatement("INSERT INTO ANTICIPOS_PV_COBROS (ANTICIPO_PV_COBRO_ID,DOCTO_PV_ID,TIPO,FORMA_COBRO_ID,IMPORTE) VALUES (gen_id(id_doctos, 1),(select max(docto_pv_id) from doctos_pv),'C',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxFormas.getSelectedItem()+"'),'"+df.format(importe)+"')");
                insertAnticipoCobro.executeUpdate();*/
                /*if (cambio!=0) {
                    PreparedStatement segundaConsulta = cn.prepareStatement("INSERT INTO ANTICIPOS_PV_COBROS (ANTICIPO_PV_COBRO_ID,DOCTO_PV_ID,TIPO,FORMA_COBRO_ID,IMPORTE) VALUES (gen_id(id_doctos, 1),(select max(docto_pv_id) from doctos_pv),'A',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxFormas.getSelectedItem()+"'),'"+df.format(cambio)+"')");
                    segundaConsulta.executeUpdate();
                }*/
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar la orden de venta"+ex);
            Logger.getLogger(finalizarOrden.class.getName()).log(Level.SEVERE, null, ex);
        }
        int posicion = 1;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String articulo = (String) tabla.getValueAt(i, 1);
            String desc = (String) tabla.getValueAt(i, 3);
            String ext = (String) tabla.getValueAt(i, 4);
            String notas = desc+" "+ext;
            
            try {
                PreparedStatement ps = cn.prepareStatement("INSERT INTO doctos_pv_det (docto_pv_det_id, docto_pv_id, clave_articulo, articulo_id, unidades, unidades_dev,tipo_contab_unid, precio_unitario,\n" +
                        "precio_unitario_impto, impuesto_por_unidad, pctje_dscto, precio_total_neto, precio_modificado, pctje_comis,\n" +
                        "rol, notas,posicion, dscto_art, dscto_extra)\n" +
                        "values (gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv WHERE tipo_docto='O' AND caja_id='3297'),(select clave_articulo from claves_articulos WHERE articulo_id = (select articulo_id from articulos where nombre = '"+articulo+"')), (select articulo_id from articulos WHERE nombre='"+articulo+"'), '"+tabla.getValueAt(i, 2)+"', 0,1, '"+tabla.getValueAt(i, 5)+"', '"+tabla.getValueAt(i, 5)+"',0, 0, '"+tabla.getValueAt(i, 7)+"', 'N',\n" +
                        "0, 'N','"+notas+"' ,'"+posicion+"', 0, 0)");
                ps.executeUpdate();
                posicion++;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al insertar la orden de venta detalle"+ex);
                Logger.getLogger(finalizarOrden.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            PreparedStatement psInsertFolio = cn.prepareStatement("update folios_cajas set CONSECUTIVO=CONSECUTIVO+1 WHERE CAJA_ID='3297' AND TIPO_DOCTO='O'");
            psInsertFolio.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el folio"+ex);
            Logger.getLogger(finalizarOrden.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = tabla.getRowCount()-1; i>=0; i--) {
                model.removeRow(i);
            }
            lbltotal.setText("0.0");
            lblcambio.setText("0.0");
            txtimporte.setText("0.0");
            txtAnticipo.setText("0.0");
            JOptionPane.showMessageDialog(null, "ORDEN FINALIZADA");
            imp.impresion(cn,String.valueOf(fmt));
    
    }
    
}
