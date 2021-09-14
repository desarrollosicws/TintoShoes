
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import static com.sicws.puntodeventatouch.consultas.finalizarVenta.getNumeros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class cobrarCredito {
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
     Statement st;
    DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
    private static DecimalFormat df = new DecimalFormat("0.00");
    public void cobrarAnticipo(JLabel lblcambio,JTextField txtimporte,JTable tabla,JComboBox cbxCliente,JTextField txtcobro){
        Formatter fmt = new Formatter();
        Formatter fmt2 = new Formatter();
        DefaultTableModel model= (DefaultTableModel) tabla.getModel();
        int posicion = 1;
        Double cambio = Double.parseDouble(lblcambio.getText());
        
        Double importe = Double.parseDouble(txtimporte.getText());
        System.out.println("txtCobro: "+txtcobro.getText());
        Double cobro = Double.parseDouble(txtcobro.getText());
        
//        Double resultado = total / 1.16;
//        Double impuesto = total-resultado;
        DecimalFormat formato1 = new DecimalFormat("#.00", separadoresPersonalizados);
        DecimalFormat formato2 = new DecimalFormat("#.000000", separadoresPersonalizados);
        
        try {
            Statement folioST = cn.createStatement();
            ResultSet rs = folioST.executeQuery("SELECT MAX(folio) FROM doctos_pv WHERE tipo_docto='P'");
            String cadena = null;
            while (rs.next()) {                
                cadena = (rs.getString("MAX"));
            }
            int numeros = Integer.parseInt(getNumeros(cadena));
            int suma = numeros+1;
            fmt.format("A%08d", suma);
            Statement folioDCC = cn.createStatement();
            ResultSet rsDCC = folioDCC.executeQuery("SELECT MAX(FOLIO) FROM DOCTOS_CC WHERE CONCEPTO_CC_ID='155'");
            String cadena2 = null; 
            while (rsDCC.next()) {                
                cadena2 = (rsDCC.getString("MAX"));
            }
            
            int numeros2 = Integer.parseInt(getNumeros(cadena2));
            int suma2 = numeros2+1;
            fmt2.format("A%08d", suma2);
            System.out.println("FOLIO"+fmt);
             System.out.println("FOLIO2"+fmt2);
            System.out.println("AQUI ES EL IMPORTE"+importe);
            System.out.println("AQUI ES EL IMPORTE"+cambio);
//            PreparedStatement primeraConsulta = cn.prepareStatement("execute block as begin\n" +
//            "insert into doctos_pv (docto_pv_id, caja_id, tipo_docto, folio, fecha, hora, cajero_id,clave_cliente, cliente_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
//            "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen, es_fac_global,\n" +
//            "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
//            "values (GEN_ID(ID_DOCTOS, 1), 753, 'P', '"+fmt+"', date 'now', time 'now', 770,(SELECT CLAVE_CLIENTE FROM claves_clientes WHERE CLIENTE_ID = (SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"'), 19,1,'N', 1,'P' ,0, 0, 'N','10', 0, 0, 0,\n" +
//            "'PV', 'N', 'CAJA', 'N', '0.00', 'CAJA');\n" +
//            ""+ 
//            "INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
//            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'C',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = 'Efectivo'),'"+df.format(importe)+"','1.000000','"+df.format(importe)+"' );\n"+
//            "end");
//            primeraConsulta.executeUpdate();
//            if (cambio!=0) {
//            PreparedStatement segundaConsulta = cn.prepareStatement("execute block as begin\n"
//            +"INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
//            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'A',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = 'Efectivo'),'"+df.format(cambio)+"','1.000000','"+df.format(cambio)+"' );\n"
//             + "end");
//            segundaConsulta.executeUpdate();
//            }
            System.out.println("cobro: "+cobro);
            PreparedStatement primeraConsulta = cn.prepareStatement("execute block as begin\n" +
            "insert into doctos_pv (docto_pv_id, caja_id, tipo_docto, folio, fecha, hora, cajero_id,clave_cliente, cliente_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
            "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen, es_fac_global,\n" +
            "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
            "values (GEN_ID(ID_DOCTOS, 1), 753, 'P', '"+fmt+"', date 'now', time 'now', 770,(SELECT CLAVE_CLIENTE FROM claves_clientes WHERE CLIENTE_ID = (SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"'), 19,1,'N', 1,'P' ,0, 0, 'N', '"+cobro+"', 0, 0, 0,\n" +
            "'PV', 'N', 'CAJA', 'N', '0.00', 'CAJA');\n" +
            ""+ 
            "INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'C',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = 'Efectivo'),'"+df.format(importe)+"','1.000000','"+df.format(importe)+"' );\n"+
            "end");
            primeraConsulta.executeUpdate();
            if (cambio!=0) {
            PreparedStatement segundaConsulta = cn.prepareStatement("execute block as begin\n"
            +"INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'A',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = '"+cbxCliente.getSelectedItem()+"'),'"+df.format(cambio)+"','1.000000','"+df.format(cambio)+"' );\n"
             + "end");
            segundaConsulta.executeUpdate();
            }
            
            PreparedStatement terceraConsulta = cn.prepareStatement("insert INTO DOCTOS_CC (DOCTO_CC_ID,CONCEPTO_CC_ID,FOLIO,NATURALEZA_CONCEPTO,FECHA,HORA,CLAVE_CLIENTE,CLIENTE_ID,IMPORTE_COBRO,TIPO_CAMBIO,CANCELADO,APLICADO,DESCRIPCION,FORMA_EMITIDA,CONTABILIZADO,CONTABILIZADO_GYP,PCTJE_DSCTO_PPAG,SISTEMA_ORIGEN,ESTATUS,ESTATUS_ANT,ES_CFD,TIENE_ANTICIPO,MODALIDAD_FACTURACION,ENVIADO,FECHA_HORA_ENVIO,CFDI_CERTIFICADO,INTEG_BA,CONTABILIZADO_BA,USUARIO_CREADOR,FECHA_HORA_CREACION,USUARIO_ULT_MODIF,FECHA_HORA_ULT_MODIF)\n" +
            "VALUES(gen_id(id_doctos ,1),155,'"+fmt2+"','R',DATE'NOW',TIME'NOW',(SELECT CLAVE_CLIENTE FROM claves_clientes where CLIENTE_ID=(SELECT CLIENTE_ID FROM CLIENTES WHERE nombre='"+cbxCliente.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM CLIENTES WHERE nombre='"+cbxCliente.getSelectedItem()+"'),'0.00','1.00','N','S','CAJA:CAJEROForma cobro: Efectivo','N','N','N','0.00','PV','N','N','N','N','PREIMP','N',current_timestamp,'N','N','N','CAJA',current_timestamp,'CAJA',current_timestamp)");
            terceraConsulta.executeUpdate();
            for (int i = 0; i < tabla.getRowCount(); i++) {
                Double abono = (Double) tabla.getValueAt(i, 4);
                    while (abono>0) {                
                        String folio = (String) tabla.getValueAt(i, 1);
                        System.out.println("FOLIO FOR :" +folio);
                        PreparedStatement cuartaConsulta = cn.prepareStatement("INSERT into importes_doctos_cc (IMPTE_DOCTO_CC_ID,DOCTO_CC_ID,fecha,CANCELADO,APLICADO,ESTATUS,TIPO_IMPTE,DOCTO_CC_ACR_ID,importe, impuesto, IVA_RETENIDO,ISR_RETENIDO,DSCTO_PPAG,PCTJE_COMIS_COB)\n" +
                        "VALUES(gen_id(id_doctos, 1),(SELECT MAX(DOCTO_CC_ID) FROM DOCTOS_CC WHERE CONCEPTO_CC_ID = '155'),DATE'NOW','N','S','N','R',(SELECT DOCTO_CC_ID FROM DOCTOS_CC WHERE FOLIO = '"+folio+"'),'"+df.format(cobro)+"','0.00','0.00','0.00','0.00','0.00')");
                        cuartaConsulta.executeUpdate();
                        abono = 0.0;
                    }
            }
            
            PreparedStatement ejecutaAplicadocto = cn.prepareStatement("execute procedure aplica_docto_pv ((select max(docto_pv_id) from doctos_pv));");
            ejecutaAplicadocto.executeUpdate();
            PreparedStatement ejecutaCalculatotal = cn.prepareStatement("execute procedure calc_totales_docto_pv ((select max(docto_pv_id) from doctos_pv),'N','N','1');");
            PreparedStatement ejecutarCCPV = cn.prepareStatement("execute procedure aplica_cobro_cc_pv ((select max(docto_cc_id) from doctos_cc where concepto_cc_id='155') ); ");
            ejecutarCCPV.executeUpdate();
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
            lblcambio.setText("0.0");
            txtimporte.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(finalizarVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
