
package com.sicws.puntodeventatouch.consultas;

import com.sicws.puntodeventatouch.conexion.conexionBD;
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
import javax.swing.JTable;
import javax.swing.JTextField;


public class finalizarVentaCredito {
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    private static DecimalFormat df = new DecimalFormat("0.00");
    public void liquidarVentaCredito(JLabel lblcambio,JTextField txtimporte,JTextField txtcobro,JComboBox cbxClientes,JTable tabla){
        try {
            Formatter fmt = new Formatter();
            Formatter fmt2 = new Formatter();
            double cambio = Double.parseDouble(lblcambio.getText());
            double importe = Double.parseDouble(txtimporte.getText());
            double cobro = Double.parseDouble(txtcobro.getText());
            Statement stFolio = cn.createStatement();
            ResultSet rsFolio = stFolio.executeQuery("SELECT MAX(folio) FROM doctos_pv WHERE tipo_docto='P'");
            String cadena = null;
            while (rsFolio.next()) {
                cadena = (rsFolio.getString("MAX"));
            }
            int numeros = Integer.parseInt(getNumeros(cadena));
            int suma = numeros + 1;
            fmt.format("A%08d", suma);
            Statement stFolioDCC = cn.createStatement();
            ResultSet rsFolioDCC = stFolioDCC.executeQuery("SELECT MAX(FOLIO) FROM DOCTOS_CC WHERE CONCEPTO_CC_ID='155'");
            String cadenaCC = null;
            while (rsFolioDCC.next()) {                
                cadenaCC=(rsFolioDCC.getString("MAX"));
            }
            int numerosCC = Integer.parseInt(getNumeros(cadenaCC));
            int sumaCC = numerosCC + 1;
            fmt2.format("A%08d", sumaCC);
            PreparedStatement insertDoctosPV = cn.prepareStatement("insert into doctos_pv (docto_pv_id, caja_id, tipo_docto, folio, fecha, hora, cajero_id,clave_cliente, cliente_id, almacen_id,moneda_id,impuesto_incluido ,tipo_cambio,tipo_dscto, dscto_pctje,\n" +
            "dscto_importe, estatus,importe_neto, total_impuestos, importe_donativo, total_fpgc, sistema_origen, es_fac_global,\n" +
            "usuario_creador, cfdi_certificado, precio_orig_partida_ajuste, usuario_ult_modif)\n" +
            "values (GEN_ID(ID_DOCTOS, 1), 753, 'P', '"+fmt+"', date 'now', time 'now', 770,(SELECT CLAVE_CLIENTE FROM claves_clientes WHERE CLIENTE_ID = (SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxClientes.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE = '"+cbxClientes.getSelectedItem()+"'), 19,1,'N', 1,'P' ,0, 0, 'N','"+df.format(cobro)+"', 0, 0, 0,\n" +
            "'PV', 'N', 'CAJA', 'N', '0.00', 'CAJA');");
            insertDoctosPV.executeUpdate();
            PreparedStatement insertDoctosPVC = cn.prepareStatement("INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'C',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = 'Efectivo'),'"+df.format(importe)+"','1.000000','"+df.format(importe)+"' );");
            insertDoctosPVC.executeUpdate();
            if (cambio!=0) {
                PreparedStatement insertDoctosPVCCambio = cn.prepareStatement("INSERT INTO doctos_pv_cobros (docto_pv_cobro_id,docto_pv_id, tipo, forma_cobro_id, importe, tipo_cambio, importe_mon_doc)"+ 
            "values(gen_id(id_doctos, 1), (select max(docto_pv_id) from doctos_pv),'A',(SELECT FORMA_COBRO_ID from FORMAS_COBRO WHERE NOMBRE = 'Efectivo'),'"+df.format(cambio)+"','1.000000','"+df.format(cambio)+"' );");
                insertDoctosPVCCambio.executeUpdate();
            }
            PreparedStatement insertDoctosCC = cn.prepareStatement("insert INTO DOCTOS_CC (DOCTO_CC_ID,CONCEPTO_CC_ID,FOLIO,NATURALEZA_CONCEPTO,FECHA,HORA,CLAVE_CLIENTE,CLIENTE_ID,IMPORTE_COBRO,TIPO_CAMBIO,CANCELADO,APLICADO,DESCRIPCION,FORMA_EMITIDA,CONTABILIZADO,CONTABILIZADO_GYP,PCTJE_DSCTO_PPAG,SISTEMA_ORIGEN,ESTATUS,ESTATUS_ANT,ES_CFD,TIENE_ANTICIPO,MODALIDAD_FACTURACION,ENVIADO,FECHA_HORA_ENVIO,CFDI_CERTIFICADO,INTEG_BA,CONTABILIZADO_BA,USUARIO_CREADOR,FECHA_HORA_CREACION,USUARIO_ULT_MODIF,FECHA_HORA_ULT_MODIF)\n" +
            "VALUES(gen_id(id_doctos ,1),155,'"+fmt2+"','R',DATE'NOW',TIME'NOW',(SELECT CLAVE_CLIENTE FROM claves_clientes where CLIENTE_ID=(SELECT CLIENTE_ID FROM CLIENTES WHERE nombre='"+cbxClientes.getSelectedItem()+"')),(SELECT CLIENTE_ID FROM CLIENTES WHERE nombre='"+cbxClientes.getSelectedItem()+"'),'0.00','1.00','N','S','CAJA:CAJEROForma cobro: Efectivo','N','N','N','0.00','PV','N','N','N','N','PREIMP','N',current_timestamp,'N','N','N','CAJA',current_timestamp,'CAJA',current_timestamp)");
            insertDoctosCC.executeUpdate();
            for (int i = 0; i < tabla.getRowCount(); i++) {
                double abono = (Double) tabla.getValueAt(i, 4);
                while (abono>0) {
                    String folio = (String) tabla.getValueAt(i, 1);
                    PreparedStatement insertImportesDCC = cn.prepareStatement("INSERT into importes_doctos_cc (IMPTE_DOCTO_CC_ID,DOCTO_CC_ID,fecha,CANCELADO,APLICADO,ESTATUS,TIPO_IMPTE,DOCTO_CC_ACR_ID,importe, impuesto, IVA_RETENIDO,ISR_RETENIDO,DSCTO_PPAG,PCTJE_COMIS_COB)\n" +
                    "VALUES(gen_id(id_doctos, 1),(SELECT MAX(DOCTO_CC_ID) FROM DOCTOS_CC WHERE CONCEPTO_CC_ID = '155'),DATE'NOW','N','S','N','R',(SELECT DOCTO_CC_ID FROM DOCTOS_CC WHERE FOLIO = '"+folio+"'),'"+df.format(cobro)+"','0.00','0.00','0.00','0.00','0.00')");
                    insertImportesDCC.executeUpdate();
                    abono = 0.0;
                }
            }
            PreparedStatement insertDoctosES = cn.prepareStatement("INSERT INTO DOCTOS_ENTRE_SIS VALUES ((SELECT max(DOCTO_CC_ID) FROM DOCTOS_CC WHERE CONCEPTO_CC_ID='155'),'CC','PV',(SELECT max (DOCTO_PV_ID) FROM DOCTOS_PV WHERE TIPO_DOCTO='P'),'R') ");
            insertDoctosES.executeUpdate();
            PreparedStatement executeAplicaDPV = cn.prepareStatement("execute procedure aplica_docto_pv ((select max(docto_pv_id) from doctos_pv));");
            executeAplicaDPV.executeUpdate();
//            PreparedStatement generaDoctoCCPV = cn.prepareStatement("execute procedure genera_docto_cc_pv ((select max(docto_pv_id) from doctos_pv where tipo_docto='P'))");
//            generaDoctoCCPV.executeUpdate();
            PreparedStatement executeAplicaCCCPV = cn.prepareStatement("execute procedure aplica_cobro_cc_pv ((select max(docto_cc_id) from doctos_cc where concepto_cc_id='155') ); ");
            executeAplicaCCCPV.executeUpdate();
            
//            PreparedStatement executeCalculaTDPV = cn.prepareStatement("execute procedure calc_totales_docto_pv ((select max(docto_pv_id) from doctos_pv),'N','N','1');");
//            boolean resultados = executeCalculaTDPV.execute();
//            while (resultados) {                
//                ResultSet rsCalculaTDPV = executeCalculaTDPV.getResultSet();
//                while (rsCalculaTDPV.next()) {                    
//                    
//                }
//                rsCalculaTDPV.close();
//                resultados = executeCalculaTDPV.getMoreResults();
//            }
//            executeCalculaTDPV.close();
            lblcambio.setText("0.0");
            txtimporte.setText("");
            txtcobro.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(finalizarVentaCredito.class.getName()).log(Level.SEVERE, null, ex);
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
