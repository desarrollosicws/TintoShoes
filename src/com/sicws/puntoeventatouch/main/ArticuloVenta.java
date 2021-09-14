
package com.sicws.puntoeventatouch.main;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import static com.sicws.puntoeventatouch.main.Ordenes.tablaProductos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import rojeru_san.RSButton;


public class ArticuloVenta extends javax.swing.JFrame {

    
    conexionBD con = new conexionBD();
     Connection cn = con.conexion();
     private static DecimalFormat df = new DecimalFormat("0.00");
     
     public static double precioActual = 0;
    
    public ArticuloVenta() {
        initComponents();
        cargarPanelBotones();
        
        //precioActual = Double.parseDouble(txtPrecio.getText());
    }
    
    /*@Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("com/sicws/puntodeventatouch/iconos/SIC MargenesICO.png"));
        return retValue;
    }*/
    
    
    private void limpiarCampos(){
    
        txtAreaExtras.setText("");
    
    }
    
    public static void calcularCantidad(){
    
        double precio = Double.parseDouble(txtPrecio.getText());
        double cantidad = Double.parseDouble(txtCantidad.getText());
        double descuento = Double.parseDouble(txtDescuento.getText());
        descuento = descuento/100;
        precio = precio - (precio*descuento);
        double resultado = precio*cantidad;
        txtSubtotal.setText(String.valueOf(resultado));
    
    }
    
    private void calcularSubtotal(){
       txtPrecio.setText(String.valueOf(precioActual));
       txtSubtotal.setText(String.valueOf(precioActual));
    }
    
    private void cargarPanelBotones(){
        Color colorP = new Color(60,119,147);
        Font font2 = new Font("Segoe UI", Font.BOLD, 9);
       try {
           String sql = "select nombre from articulos WHERE linea_articulo_id IN (3206,3210) and estatus = 'A'";
//
           Statement st;
           panelArticulos.removeAll();
           
           
           st=cn.createStatement();
           ResultSet rs = st.executeQuery(sql);
           while (rs.next()){
               
               RSButton botonFProducto = new RSButton();
               botonFProducto.setText(rs.getString("NOMBRE"));
               
//                botonFProducto.setBgShade(Color.white);
//                botonFProducto.setBgHover(Color.white);
//                botonFProducto.setBgShadeHover(Color.white);
               botonFProducto.setBackground(colorP);
               botonFProducto.setFont(font2);
               botonFProducto.setPreferredSize(new Dimension (100,40));
//                botonFProducto.setMaximumSize(new Dimension(200, 100));
//                botonFProducto.setBounds(columna, fila, 125, 80);
               panelArticulos.add(botonFProducto);
               panelArticulos.updateUI();
               
//                x++;
//                columna += 130;
               
               
               botonFProducto.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent arg0) {
                       Object ValorBoton = botonFProducto.getText();
//                        txtArticulo.setText(botonFProducto.getText());
                       
                       String sql = "select precio from precios_articulos where articulo_id = (select articulo_id from articulos where nombre = '"+ValorBoton+"')";
                       Statement st;
                       String datoPrecio = "";
                       try {
                           st=cn.createStatement();
                           ResultSet rs = st.executeQuery(sql);
                           while (rs.next()) {
                               datoPrecio = rs.getString("precio");
                           }
                           System.out.println("PREEEECIO"+datoPrecio);
                           
                       } catch (SQLException ex) {
                           Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
                           JOptionPane.showMessageDialog(null, ex);
                       }
                       
                       
                       try {
                           double precio = Double.parseDouble(datoPrecio);
                           txtAreaExtras.append(String.valueOf(ValorBoton+" "));
                           double precioServicio = Double.parseDouble(txtPrecio.getText());
                           double cantidadServicio = Double.parseDouble(txtCantidad.getText());
                           double nuevoPrecio = precioServicio+precio;
                           double nuevoSubTotal = nuevoPrecio*cantidadServicio;
                           txtPrecio.setText(String.valueOf(nuevoPrecio));
                           txtSubtotal.setText(String.valueOf(nuevoSubTotal));
                           calcularCantidad();
                           /*
                           
                           
                           
                           Object [] fila = new Object[6];
                           fila[0] = tablaProductos.getRowCount()+1;
                           fila[1] = ValorBoton;
                           fila[2] = uno;
                           fila[3] = "";
                           fila[4] = df.format(precio);
                           fila[5] = df.format(precio);
                           modelo.addRow(fila);
                           tablaProductos.setModel(modelo);
                           System.out.println("NO ES PESABLE");
                           
                           int lastRow = tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
                           tablaProductos.setRowSelectionInterval(lastRow, lastRow);
                           tablaProductos.setColumnSelectionInterval(1, 1);
                           tablaProductos.requestFocus();
                           selectAllAfterCreate();
                           calcularTotal();
                           
                           */
                       } catch (Exception e) {
                           Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, e);
                           JOptionPane.showMessageDialog(null, e);
                       }
                       System.out.println("VALOR "+ValorBoton);
                   }
               });
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(ArticuloVenta.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaExtras = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelArticulos = new javax.swing.JPanel();
        btnEliminar = new rscomponentshade.RSButtonShade();
        btnEliminar1 = new rscomponentshade.RSButtonShade();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaDescripcion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtArticulo = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnEliminar2 = new rscomponentshade.RSButtonShade();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalle Servicio");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaExtras.setEditable(false);
        txtAreaExtras.setColumns(20);
        txtAreaExtras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAreaExtras.setLineWrap(true);
        txtAreaExtras.setRows(5);
        txtAreaExtras.setAutoscrolls(false);
        txtAreaExtras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaExtrasKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(txtAreaExtras);

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelArticulos.setBackground(new java.awt.Color(255, 255, 255));
        panelArticulos.setMaximumSize(new java.awt.Dimension(400, 32767));
        panelArticulos.setLayout(new java.awt.GridLayout(0, 4, 2, 2));
        jScrollPane3.setViewportView(panelArticulos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        btnEliminar.setBackground(new java.awt.Color(0, 168, 235));
        btnEliminar.setText("ACEPTAR");
        btnEliminar.setBgHover(new java.awt.Color(0, 155, 219));
        btnEliminar.setBgShade(new java.awt.Color(255, 255, 255));
        btnEliminar.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setPixels(0);
        btnEliminar.setRound(0);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEliminar1.setBackground(new java.awt.Color(240, 52, 52));
        btnEliminar1.setText("ELIMINAR");
        btnEliminar1.setBgHover(new java.awt.Color(255, 51, 51));
        btnEliminar1.setBgShade(new java.awt.Color(255, 255, 255));
        btnEliminar1.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnEliminar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar1.setPixels(0);
        btnEliminar1.setRound(0);
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("SERVICIOS EXTRAS");

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaDescripcion.setColumns(20);
        txtAreaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAreaDescripcion.setLineWrap(true);
        txtAreaDescripcion.setRows(5);
        txtAreaDescripcion.setAutoscrolls(false);
        txtAreaDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaDescripcionKeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(txtAreaDescripcion);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("DESCRIPCION");

        txtArticulo.setEditable(false);
        txtArticulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtPrecio.setEditable(false);
        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtSubtotal.setEditable(false);
        txtSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtCantidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCantidadMouseClicked(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("CANTIDAD");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("SERVICIO");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("PRECIO");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("SUBTOTAL");

        txtDescuento.setEditable(false);
        txtDescuento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("DESCUENTO");

        btnEliminar2.setBackground(new java.awt.Color(51, 51, 255));
        btnEliminar2.setText("REPARACION");
        btnEliminar2.setBgHover(new java.awt.Color(255, 51, 51));
        btnEliminar2.setBgShade(new java.awt.Color(255, 255, 255));
        btnEliminar2.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnEliminar2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar2.setPixels(0);
        btnEliminar2.setRound(0);
        btnEliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidad)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtAreaExtrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaExtrasKeyTyped
       
    }//GEN-LAST:event_txtAreaExtrasKeyTyped

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) Ventas.tablaProductos.getModel();
        double cantidad = Double.parseDouble(txtCantidad.getText());
        Object [] fila = new Object[8];
                                fila[0] = Ventas.tablaProductos.getRowCount()+1;
                                fila[1] = txtArticulo.getText();
                                fila[2] = cantidad;
                                fila[3] = txtAreaDescripcion.getText();
                                fila[4] = txtAreaExtras.getText();
                                fila[5] = txtPrecio.getText(); 
                                fila[6] = txtDescuento.getText();
                                fila[7] = txtSubtotal.getText();
                                modelo.addRow(fila);
                                Ventas.tablaProductos.setModel(modelo);
                                System.out.println("NO ES PESABLE");

                            int lastRow = Ventas.tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
                            Ventas.tablaProductos.setRowSelectionInterval(lastRow, lastRow);
                            Ventas.tablaProductos.setColumnSelectionInterval(1, 1);
                            Ventas.tablaProductos.requestFocus();
                            Ventas.selectAllAfterCreate();
                            Ventas.calcularTotal();
                            this.dispose();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
       limpiarCampos();
       calcularSubtotal();
       calcularCantidad();
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void txtAreaDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAreaDescripcionKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
       calcularCantidad();
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCantidadMouseClicked
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtCantidadMouseClicked

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        String name = JOptionPane.showInputDialog("MONTO",0);
        double precio = Double.parseDouble(name);
        if (precio>0) {
            txtAreaExtras.append(String.valueOf("REPARACION"+" "));
            double precioServicio = Double.parseDouble(txtPrecio.getText());
            double cantidadServicio = Double.parseDouble(txtCantidad.getText());
            double nuevoPrecio = precioServicio+precio;
            double nuevoSubTotal = nuevoPrecio*cantidadServicio;
            txtPrecio.setText(String.valueOf(nuevoPrecio));
            txtSubtotal.setText(String.valueOf(nuevoSubTotal));
            calcularCantidad();
        } else {
            JOptionPane.showMessageDialog(null, "VERIFIQUE LA CANTIDAD");
        }
        
        
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArticuloVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArticuloVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArticuloVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArticuloVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArticuloVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnEliminar;
    private rscomponentshade.RSButtonShade btnEliminar1;
    private rscomponentshade.RSButtonShade btnEliminar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel panelArticulos;
    public static javax.swing.JTextArea txtAreaDescripcion;
    public static javax.swing.JTextArea txtAreaExtras;
    public static javax.swing.JTextField txtArticulo;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtDescuento;
    public static javax.swing.JTextField txtPrecio;
    public static javax.swing.JTextField txtSubtotal;
    // End of variables declaration//GEN-END:variables
}
