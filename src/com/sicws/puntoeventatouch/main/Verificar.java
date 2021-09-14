
package com.sicws.puntoeventatouch.main;

import com.mxrck.autocompleter.TextAutoCompleter;
import com.sicws.puntodeventatouch.conexion.conexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Verificar extends javax.swing.JFrame {

   
    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    private TextAutoCompleter ac;
    
    public Verificar() {
        initComponents();
        cargarAutoCompletar();
    }
    
    private void cargarAutoCompletar(){
        TextAutoCompleter textAutoC = new TextAutoCompleter(txtNombre);
        try {
            Statement sent = cn.createStatement();
            ResultSet rs = sent.executeQuery("SELECT NOMBRE FROM CLIENTES WHERE ESTATUS='A'");
            while (rs.next()) {
                textAutoC.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtNombre = new rscomponentshade.RSTextFieldShade();
        jLabel1 = new javax.swing.JLabel();
        txtTelefono = new rscomponentshade.RSTextFieldShade();
        jLabel2 = new javax.swing.JLabel();
        btnLiquidar1 = new rscomponentshade.RSButtonShade();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtNombre.setPixels(2);
        txtNombre.setPlaceholder("NOMBRE");
        txtNombre.setRound(0);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NOMBRE DEL CLIENTE");

        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setEnabled(false);
        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtTelefono.setPixels(2);
        txtTelefono.setPlaceholder("TELEFONO");
        txtTelefono.setRound(0);
        txtTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTelefonoMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NUMERO DE TELEFONO");

        btnLiquidar1.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar1.setText("ACEPTAR");
        btnLiquidar1.setBgHover(new java.awt.Color(0, 155, 219));
        btnLiquidar1.setBgShade(new java.awt.Color(255, 255, 255));
        btnLiquidar1.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnLiquidar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLiquidar1.setPixels(0);
        btnLiquidar1.setRound(0);
        btnLiquidar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiquidar1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLiquidar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMouseClicked

    }//GEN-LAST:event_txtNombreMouseClicked

    private void txtTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTelefonoMouseClicked

    }//GEN-LAST:event_txtTelefonoMouseClicked

    private void btnLiquidar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar1ActionPerformed
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE UN NOMBRE");
        } else {
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT NOMBRE FROM CLIENTES WHERE NOMBRE = '"+txtNombre.getText()+"'");
                
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "YA EXISTE ESTE CLIENTE");
                   
                    Ordenes.nombre = txtNombre.getText();
                    Ordenes ven = new Ordenes();
                    ven.setVisible(true);
                    this.dispose();
                } else if (!txtTelefono.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "EL CLIENTE NO EXISTE POR FAVOR INGRESE UN NUMERO DE TELEFONO");
                    txtTelefono.setEnabled(true);
                } else {
                    try {
                        PreparedStatement psInsertCliente = cn.prepareStatement("INSERT INTO CLIENTES\n" +
                        "(CLIENTE_ID,NOMBRE,CONTACTO1,ESTATUS,COBRAR_IMPUESTOS,RETIENE_IMPUESTOS,SUJETO_IEPS,GENERAR_INTERESES,EMITIR_EDOCTA,MONEDA_ID,COND_PAGO_ID,USUARIO_CREADOR,FECHA_HORA_CREACION,USUARIO_ULT_MODIF,FECHA_HORA_ULT_MODIF)\n" +
                        "VALUES\n" +
                        "(GEN_ID(id_bitacora,1),'"+txtNombre.getText()+"','"+txtTelefono.getText()+"','A','S','N','N','S','S',1,583,'SYSDBA',DATE 'NOW','SYSDBA',DATE 'NOW')");
                        psInsertCliente.executeUpdate();
                        JOptionPane.showMessageDialog(null, "CLIENTE REGISTRADO EXITOSAMENTE");
                    } catch (SQLException ex) {
                        Logger.getLogger(NuevoClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    Ordenes.nombre = txtNombre.getText();
                    Ordenes ven = new Ordenes();
                    ven.setVisible(true);
                    this.dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(NuevoClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex);
            }
            /*Ordenes.nombre = txtNombre.getText();
            Ordenes ven = new Ordenes();
            ven.setVisible(true);
            this.dispose();*/
        }
    }//GEN-LAST:event_btnLiquidar1ActionPerformed

    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Verificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Verificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Verificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Verificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Verificar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnLiquidar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private rscomponentshade.RSTextFieldShade txtNombre;
    private rscomponentshade.RSTextFieldShade txtTelefono;
    // End of variables declaration//GEN-END:variables
}
