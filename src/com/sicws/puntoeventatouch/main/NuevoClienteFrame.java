
package com.sicws.puntoeventatouch.main;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import static com.sicws.puntoeventatouch.main.ClienteExistenteFrame.nombreCliente;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



 

public class NuevoClienteFrame extends javax.swing.JFrame {

    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    
   
    //AnticipoFrame frame = new AnticipoFrame();
    
    public NuevoClienteFrame() {
        initComponents();
    }
    
    /*@Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("com/sicws/puntodeventatouch/iconos/SIC MargenesICO.png"));
        return retValue;
    }*/
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnLiquidar1 = new rscomponentshade.RSButtonShade();
        txtTelefono = new rscomponentshade.RSTextFieldShade();
        txtNombre = new rscomponentshade.RSTextFieldShade();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo Cliente");
        setIconImage(getIconImage());
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnLiquidar1.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar1.setText("GUARDAR");
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

        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtTelefono.setPixels(2);
        txtTelefono.setPlaceholder("TELEFONO");
        txtTelefono.setRound(0);
        txtTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTelefonoMouseClicked(evt);
            }
        });

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NUMERO DE TELEFONO");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLiquidar1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLiquidar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTelefonoMouseClicked

    }//GEN-LAST:event_txtTelefonoMouseClicked

    private void txtNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMouseClicked
      
    }//GEN-LAST:event_txtNombreMouseClicked

    private void btnLiquidar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar1ActionPerformed
        
        /*AnticipoFrame.nombreCliente = txtNombre.getText();
       AnticipoFrame.nuevoNumeroCliente = Integer.parseInt(txtTelefono.getText());
       frame.setVisible(true);
       this.dispose();*/
        
        if (txtNombre.getText().equals("") || txtTelefono.getText().equals("") ) {
            
            JOptionPane.showMessageDialog(null, "CAMPOS VACIOS");
            
        } else {
            
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT NOMBRE FROM CLIENTES WHERE NOMBRE = '"+txtNombre.getText()+"'");
                
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "YA EXISTE ESTE CLIENTE");
                   
                    Ordenes ven = new Ordenes();
                    ven.setVisible(true);
                    this.dispose();
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
            
            
            
            
        }
    }//GEN-LAST:event_btnLiquidar1ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NuevoClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevoClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevoClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuevoClienteFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnLiquidar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panelPrincipal;
    private rscomponentshade.RSTextFieldShade txtNombre;
    private rscomponentshade.RSTextFieldShade txtTelefono;
    // End of variables declaration//GEN-END:variables
}
