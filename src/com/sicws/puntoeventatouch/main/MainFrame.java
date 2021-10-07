
package com.sicws.puntoeventatouch.main;

import com.sicws.puntodeventatouch.consultas.SaldoDiasVencidos;
import java.awt.Image;
import java.awt.Toolkit;


public class MainFrame extends javax.swing.JFrame {
    
    
    
    public MainFrame() {
        initComponents();
        SaldoDiasVencidos saldos = new SaldoDiasVencidos();
        saldos.calcularSaldo();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnLiquidar = new rscomponentshade.RSButtonShade();
        btnLiquidar1 = new rscomponentshade.RSButtonShade();
        btnLiquidar2 = new rscomponentshade.RSButtonShade();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Principal");
        setIconImage(getIconImage());
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnLiquidar.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar.setText("ORDENES");
        btnLiquidar.setBgHover(new java.awt.Color(0, 155, 219));
        btnLiquidar.setBgShade(new java.awt.Color(255, 255, 255));
        btnLiquidar.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnLiquidar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLiquidar.setPixels(0);
        btnLiquidar.setRound(0);
        btnLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidarActionPerformed(evt);
            }
        });

        btnLiquidar1.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar1.setText("NUEVA VENTA");
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

        btnLiquidar2.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar2.setText("NUEVA ORDEN");
        btnLiquidar2.setBgHover(new java.awt.Color(0, 155, 219));
        btnLiquidar2.setBgShade(new java.awt.Color(255, 255, 255));
        btnLiquidar2.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnLiquidar2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLiquidar2.setPixels(0);
        btnLiquidar2.setRound(0);
        btnLiquidar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLiquidar, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                    .addComponent(btnLiquidar1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                    .addComponent(btnLiquidar2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(btnLiquidar2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiquidar1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLiquidar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar1ActionPerformed
        VerificarClienteVenta ver = new VerificarClienteVenta();
        ver.setVisible(true);
    }//GEN-LAST:event_btnLiquidar1ActionPerformed

    private void btnLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidarActionPerformed
       ListaOrdenes lista = new ListaOrdenes();
       lista.setVisible(true);
    }//GEN-LAST:event_btnLiquidarActionPerformed

    private void btnLiquidar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar2ActionPerformed
       Verificar ver = new Verificar();
       ver.setVisible(true);
    }//GEN-LAST:event_btnLiquidar2ActionPerformed

   
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnLiquidar;
    private rscomponentshade.RSButtonShade btnLiquidar1;
    private rscomponentshade.RSButtonShade btnLiquidar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
