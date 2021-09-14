
package com.sicws.puntodeventatouch.conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class frame extends javax.swing.JFrame {

    Connection cn;
    JFileChooser chooser = new JFileChooser();
    conexionBD conec = new conexionBD();
    public frame(){
        
        initComponents();
        cargar();
        this.setTitle("Conexion a Base de Datos");
    }
    
    public void cargar(){
        try {
            File archivo = new File ("config.properties");
            
       
            FileInputStream fis;
            fis = new FileInputStream(archivo);
            Properties prop = new Properties();
            prop.load(fis);
            String nombreConexion = prop.getProperty("nombreConexion");
            String ubicacionBD = prop.getProperty("ubicacionBD");
            String usuario = prop.getProperty("usuario");
            String contraseña = prop.getProperty("contraseña");
            String tipo = prop.getProperty("tipo");
            
            if (tipo.equals("localhost")) {
                rbLocal.setSelected(true);
            } else {
                rbRemota.setSelected(true);
                txtIP.setEditable(true);
                txtIP.setText(tipo);
            }
            
            txtdireccion.setText(ubicacionBD);
            txtusuario.setText(nombreConexion);
            txtusuario.setText(usuario);
            txtcontraseña.setText(contraseña);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public Connection conexion(){
        File archivo = new File ("config.properties");
            
        try {
            FileInputStream fis;
            fis = new FileInputStream(archivo);
            Properties prop = new Properties();
            prop.load(fis);
            String nombre = prop.getProperty("nombre");
//            txtusuario.setText(nombre);
            System.out.println(nombre);
            String contra = prop.getProperty("contraseña");
            String base = prop.getProperty("direccion");
            String direccionBD = "jdbc:firebirdsql://localhost/"+base;
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            cn=DriverManager.getConnection(direccionBD, nombre, contra);
            System.out.println("conexion");
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(conexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
           
        }
      
     return cn;
}
public void guardarConfig(){
try {
            File archivo = new File ("config.properties");
            FileOutputStream fis = new FileOutputStream(archivo);
            Properties prop = new Properties();
            String nombreConexion = txtusuario.getText();
            String ubicacionBD = txtdireccion.getText();
            String nombre = txtusuario.getText();
            String contraseña = txtcontraseña.getText();
            String tipoConexion = "";
            if (rbRemota.isSelected()==true) {
                tipoConexion = txtIP.getText();
            } else {
                tipoConexion = "localhost";
            }
            prop.setProperty("nombreConexion", nombreConexion);
            prop.setProperty("ubicacionBD", ubicacionBD);
            prop.setProperty("usuario", nombre);
            prop.setProperty("contraseña", contraseña);
            prop.setProperty("tipo", tipoConexion);
            prop.store(fis,"arhcivo de configuracion"); 
            JOptionPane.showMessageDialog(null, "Configuracion guardada");
        } catch (IOException ex) {
//            Logger.getLogger(escritura.class.getName()).log(Level.SEVERE, null, ex);
        }


}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtusuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtdireccion = new javax.swing.JTextField();
        txtcontraseña = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        rbLocal = new javax.swing.JRadioButton();
        rbRemota = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Probar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtcontraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcontraseñaActionPerformed(evt);
            }
        });

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Direccion de Base de Datos");

        jLabel2.setText("Usuario");

        jLabel3.setText("Contraseña");

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        rbLocal.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbLocal);
        rbLocal.setText("Local");
        rbLocal.setFocusable(false);
        rbLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbLocalMouseClicked(evt);
            }
        });

        rbRemota.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbRemota);
        rbRemota.setText("Remota");
        rbRemota.setFocusable(false);
        rbRemota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbRemotaMouseClicked(evt);
            }
        });

        jLabel4.setText("Conexion");

        txtIP.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtdireccion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbLocal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(rbRemota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIP)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbLocal)
                    .addComponent(rbRemota)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        conec.conexion();
        JOptionPane.showMessageDialog(null, "Conexion");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtcontraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcontraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcontraseñaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String cad;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("FDB", "fdb");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
        System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        System.out.print("directorio:" +chooser.getCurrentDirectory());
        cad=chooser.getCurrentDirectory()+"/"+chooser.getSelectedFile().getName();
        txtdireccion.setText(cad);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        guardarConfig();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void rbRemotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbRemotaMouseClicked
        if (rbRemota.isSelected()==true) {
            txtIP.setEditable(true);
        } else {
            txtIP.setEditable(false);
        }
    }//GEN-LAST:event_rbRemotaMouseClicked

    private void rbLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbLocalMouseClicked
        if (rbRemota.isSelected()==true) {
            txtIP.setEditable(true);
        } else {
            txtIP.setEditable(false);
        }
    }//GEN-LAST:event_rbLocalMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    new frame().setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton rbLocal;
    private javax.swing.JRadioButton rbRemota;
    private javax.swing.JTextField txtIP;
    private javax.swing.JPasswordField txtcontraseña;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
