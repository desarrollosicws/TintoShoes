
package com.sicws.puntoeventatouch.main;

import com.mxrck.autocompleter.TextAutoCompleter;
import com.sicws.puntodeventatouch.conexion.conexionBD;
import com.sicws.puntodeventatouch.ticket.reimprimirTicket;
import com.sicws.puntodeventatouch.utileria.Rendercheck;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


public class ListaOrdenes extends javax.swing.JFrame {

    static conexionBD con = new conexionBD();
    static Connection cn = con.conexion();
    
    private TextAutoCompleter ac;
    public static String nombreCliente; 
    Rendercheck claserender = new Rendercheck();
    JCheckBox check = new JCheckBox();
    DefaultCellEditor defaultCelle = new DefaultCellEditor(check);
    
    public ListaOrdenes() {
        initComponents();
        cargarTabla();
        cargarOrdenes();
        cargarAutoCompletar();
        tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }
    
    private void cargarAutoCompletar(){
        TextAutoCompleter textAutoC = new TextAutoCompleter(txtNombreCliente);
        try {
            Statement sent = cn.createStatement();
            ResultSet rs = sent.executeQuery("SELECT DISTINCT A.NOMBRE FROM CLIENTES A\n" +
            "LEFT JOIN DOCTOS_PV B ON B.cliente_id = A.CLIENTE_ID\n" +
            "WHERE A.ESTATUS='A' AND B.TIPO_DOCTO='O'");
            while (rs.next()) {
                textAutoC.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    private void cargarTabla(){
    DefaultTableModel modelo = (DefaultTableModel) tablaOrdenes.getModel();
        modelo.addColumn("ID");
        modelo.addColumn("FOLIO");
        modelo.addColumn("FECHA");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("TELEFONO");
        modelo.addColumn("ENTREGA");
        modelo.addColumn("FINAL");
        modelo.addColumn("VENCIDOS");
        modelo.addColumn("SALDO");
        tablaOrdenes.setModel(modelo);
        TableColumnModel columnModel = tablaOrdenes.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(7).setPreferredWidth(120);
        columnModel.getColumn(8).setPreferredWidth(120);
        JTableHeader header = tablaOrdenes.getTableHeader();
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 1, new java.awt.Color(232, 232, 232)));
        header.setOpaque(false);
        header.setBackground(new Color(55,55,55));
        header.setForeground(new Color(255,255,255));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        header.setFont(new Font ("Segoe UI", Font.BOLD, 14));
        header.setToolTipText("Tabla Articulos");
        tablaOrdenes.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        tablaOrdenes.setForeground(new Color(0,0,0));
        tablaOrdenes.getTableHeader().setOpaque(false);
        tablaOrdenes.setRowHeight(25);
    
    }
    
    
    public  void cargarOrdenesCliente(){
        
        DefaultTableModel modelo = (DefaultTableModel) tablaOrdenes.getModel();
        for (int i = tablaOrdenes.getRowCount()-1; i>=0; i--) {
                modelo.removeRow(i);
            }
           
        try {
            
            
            
            
            Object datos [] = new Object[5];
             Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.DOCTO_PV_ID,A.FOLIO,A.FECHA,B.NOMBRE,B.CONTACTO1\n" +
            "FROM doctos_pv A\n" +
            "LEFT JOIN CLIENTES B ON B.CLIENTE_ID=A.CLIENTE_ID\n" +
            "WHERE A.TIPO_DOCTO='O' AND B.CLIENTE_ID=(SELECT CLIENTE_ID FROM clientes WHERE NOMBRE='"+txtNombreCliente.getText()+"') ORDER BY A.FOLIO DESC");
            
            while (rs.next()) {                
                datos[0]=rs.getInt(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                modelo.addRow(datos);
            }
            //tablaOrdenes.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void cargarOrdenes(){
    DefaultTableModel modelo = (DefaultTableModel) tablaOrdenes.getModel();
        for (int i = tablaOrdenes.getRowCount()-1; i>=0; i--) {
                modelo.removeRow(i);
            }
           
        try {
            
            Object datos [] = new Object[9];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.DOCTO_PV_ID,A.FOLIO,A.FECHA,B.NOMBRE,B.CONTACTO1,C.FECHA_ENTREGA,C.fecha_final,C.dias_vencidos,C.SALDO\n" +
            "FROM doctos_pv A\n" +
            "LEFT JOIN CLIENTES B ON B.CLIENTE_ID=A.CLIENTE_ID\n" +
            "LEFT JOIN dias_vencidos C ON C.id_docto=A.docto_pv_id\n" +
            "WHERE A.TIPO_DOCTO='O' ORDER BY A.FOLIO DESC");
            
            while (rs.next()) {                
                datos[0]=rs.getInt(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                modelo.addRow(datos);
            }
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOrdenes = new javax.swing.JTable();
        txtNombreCliente = new rscomponentshade.RSTextFieldShade();
        btnBuscar = new rscomponentshade.RSButtonShade();
        jPanel1 = new javax.swing.JPanel();
        btnLiquidar2 = new rscomponentshade.RSButtonShade();
        btnLiquidar3 = new rscomponentshade.RSButtonShade();
        btnLiquidar4 = new rscomponentshade.RSButtonShade();
        btnBuscar1 = new rscomponentshade.RSButtonShade();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Ordenes");
        setIconImage(getIconImage());
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tablaOrdenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        }
    );
    jScrollPane1.setViewportView(tablaOrdenes);

    txtNombreCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
    txtNombreCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    txtNombreCliente.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
    txtNombreCliente.setOpaque(false);
    txtNombreCliente.setPixels(2);
    txtNombreCliente.setPlaceholder("NOMBRE");
    txtNombreCliente.setSelectionEnd(17);
    txtNombreCliente.setSelectionStart(17);
    txtNombreCliente.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            txtNombreClienteMouseClicked(evt);
        }
    });

    btnBuscar.setBackground(new java.awt.Color(0, 149, 126));
    btnBuscar.setText("BUSCAR");
    btnBuscar.setBgHover(new java.awt.Color(0, 155, 219));
    btnBuscar.setBgShade(new java.awt.Color(255, 255, 255));
    btnBuscar.setBgShadeHover(new java.awt.Color(255, 255, 255));
    btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnBuscar.setPixels(0);
    btnBuscar.setRound(0);
    btnBuscar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnBuscarActionPerformed(evt);
        }
    });

    jPanel1.setLayout(new java.awt.GridLayout(1, 0, 2, 2));

    btnLiquidar2.setBackground(new java.awt.Color(0, 168, 235));
    btnLiquidar2.setText("LIQUIDAR");
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
    jPanel1.add(btnLiquidar2);

    btnLiquidar3.setBackground(new java.awt.Color(0, 149, 126));
    btnLiquidar3.setText("MODIFICAR");
    btnLiquidar3.setBgHover(new java.awt.Color(85, 184, 169));
    btnLiquidar3.setBgShade(new java.awt.Color(255, 255, 255));
    btnLiquidar3.setBgShadeHover(new java.awt.Color(255, 255, 255));
    btnLiquidar3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnLiquidar3.setPixels(0);
    btnLiquidar3.setRound(0);
    btnLiquidar3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLiquidar3ActionPerformed(evt);
        }
    });
    jPanel1.add(btnLiquidar3);

    btnLiquidar4.setBackground(new java.awt.Color(248, 148, 6));
    btnLiquidar4.setText("REIMPRIMIR");
    btnLiquidar4.setBgHover(new java.awt.Color(250, 183, 89));
    btnLiquidar4.setBgShade(new java.awt.Color(255, 255, 255));
    btnLiquidar4.setBgShadeHover(new java.awt.Color(255, 255, 255));
    btnLiquidar4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnLiquidar4.setPixels(0);
    btnLiquidar4.setRound(0);
    btnLiquidar4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLiquidar4ActionPerformed(evt);
        }
    });
    jPanel1.add(btnLiquidar4);

    btnBuscar1.setBackground(new java.awt.Color(0, 149, 126));
    btnBuscar1.setText("ACTUALIZAR");
    btnBuscar1.setBgHover(new java.awt.Color(0, 155, 219));
    btnBuscar1.setBgShade(new java.awt.Color(255, 255, 255));
    btnBuscar1.setBgShadeHover(new java.awt.Color(255, 255, 255));
    btnBuscar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    btnBuscar1.setPixels(0);
    btnBuscar1.setRound(0);
    btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnBuscar1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
    panelPrincipal.setLayout(panelPrincipalLayout);
    panelPrincipalLayout.setHorizontalGroup(
        panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelPrincipalLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelPrincipalLayout.createSequentialGroup()
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
            .addContainerGap())
    );
    panelPrincipalLayout.setVerticalGroup(
        panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
            .addGap(17, 17, 17)
            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     private static DecimalFormat df = new DecimalFormat("0.00");
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (txtNombreCliente.getText().isEmpty()){
            cargarOrdenes();
        } else {
            cargarOrdenesCliente();
        }
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreClienteMouseClicked

    }//GEN-LAST:event_txtNombreClienteMouseClicked
    
    private void btnLiquidar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar2ActionPerformed
        NuevaVenta.nombre = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 3);
        NuevaVenta ven = new NuevaVenta();
        DefaultTableModel modelo = (DefaultTableModel) ven.tablaProductos.getModel();
        try {
          
            int row = tablaOrdenes.getSelectedRow();
            int idDocumento = (int) tablaOrdenes.getValueAt(row, 0);
            
            System.out.println(idDocumento);
            ven.idDocumento = idDocumento;
            String anticipo = "";
            String unidades = "";
            String cliente = "";
            int diasVencidos = 0;
            double saldoVencidos =0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.IMPORTE_NETO,A.PERSONA,B.NOTAS,B.POSICION,C.NOMBRE,B.UNIDADES,C.UNIDAD_VENTA,B.PRECIO_UNITARIO,B.PRECIO_TOTAL_NETO,d.nombre as cliente,E.DIAS_VENCIDOS,E.SALDO FROM doctos_pv A\n" +
"            LEFT JOIN doctos_pv_det B ON B.DOCTO_PV_ID = A.DOCTO_PV_ID\n" +
"            LEFT JOIN articulos C ON C.ARTICULO_ID = B.ARTICULO_ID\n" +
"            left join clientes d on d.cliente_id = a.cliente_id\n" +
"            LEFT JOIN DIAS_VENCIDOS E ON E.ID_DOCTO=A.DOCTO_PV_ID\n" +
"            WHERE A.DOCTO_PV_ID = '"+idDocumento+"'");
                        Object [] fila = new Object[6];
            while (rs.next()) {                
                anticipo = rs.getString("PERSONA");
                cliente = rs.getString(10);
                diasVencidos = rs.getInt("DIAS_VENCIDOS");
                saldoVencidos = rs.getDouble("SALDO");
                fila[0] = rs.getInt("POSICION");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getDouble("UNIDADES");
                
                fila[3] = rs.getString("NOTAS");
                fila[4] = rs.getDouble("PRECIO_UNITARIO"); 
                fila[5] = rs.getDouble("PRECIO_TOTAL_NETO");
                modelo.addRow(fila);
                ven.tablaProductos.setModel(modelo);
            }
            
            double sumatoria1=0.0;
        int totalRow= ven.tablaProductos.getRowCount();
        if (totalRow!=0) {
            totalRow-=1;
        
        for(int i=0;i<=(totalRow);i++)
        {
           double sumatoria= Double.parseDouble(String.valueOf(ven.tablaProductos.getValueAt(i, 5)));
            sumatoria1+=sumatoria;
 
          System.out.println("LA SUMA TOTAL ES:"+sumatoria1);
          double anticipoVen = Double.parseDouble(anticipo);
          ven.lblTotal.setText(""+(df.format(sumatoria1-anticipoVen)));
            
           }
        } else if(totalRow==0){
        ven.lblTotal.setText("0.0");
        
        }
            System.out.println("DIAS"+diasVencidos);
            ven.txtAnticipo.setText(anticipo);
            
            if (diasVencidos==0 && saldoVencidos==0) {
                diasVencidos = 0;
                saldoVencidos = 0;
            }
            ven.lblDias.setText(String.valueOf(diasVencidos));
            ven.lblSaldo.setText(String.valueOf(saldoVencidos));
            
            int lastRow = NuevaVenta.tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
            NuevaVenta.tablaProductos.setRowSelectionInterval(lastRow, lastRow);
            NuevaVenta.tablaProductos.setColumnSelectionInterval(1, 1);
            NuevaVenta.tablaProductos.requestFocus();
            NuevaVenta.selectAllAfterCreate();
            NuevaVenta.calcularTotal();
            ven.setVisible(true);
            ven.verificarDiasSaldo();
            this.dispose();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnLiquidar2ActionPerformed

    private void btnLiquidar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar3ActionPerformed
        
        try {
            ModificarOrdenes.nombre = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 3);
            ModificarOrdenes or = new ModificarOrdenes();
            DefaultTableModel modelo = (DefaultTableModel) or.tablaProductos.getModel();
            int row = tablaOrdenes.getSelectedRow();
            or.lblFolio.setText((String) tablaOrdenes.getValueAt(row, 1));
            ModificarOrdenes.nombre = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 3);
            int idDocumento = (int) tablaOrdenes.getValueAt(row, 0);
            String anticipo = "";
            String cliente = "";
            String desc = "";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.IMPORTE_NETO,A.PERSONA,B.NOTAS,B.POSICION,C.NOMBRE,B.UNIDADES,C.UNIDAD_VENTA,B.PRECIO_UNITARIO,B.PRECIO_TOTAL_NETO,d.nombre as cliente,E.DIAS_VENCIDOS,E.SALDO,B.PCTJE_DSCTO FROM doctos_pv A\n" +
            "LEFT JOIN doctos_pv_det B ON B.DOCTO_PV_ID = A.DOCTO_PV_ID\n" +
            "LEFT JOIN articulos C ON C.ARTICULO_ID = B.ARTICULO_ID\n" +
            "left join clientes d on d.cliente_id = a.cliente_id\n" +
            "LEFT JOIN DIAS_VENCIDOS E ON E.ID_DOCTO=A.DOCTO_PV_ID\n" +
            "WHERE A.DOCTO_PV_ID = '"+idDocumento+"'");
            Object [] fila = new Object[8];
            while (rs.next()) {                
                anticipo = rs.getString("PERSONA");
                cliente = rs.getString(10);
                String var = rs.getString("NOTAS");
                int extraer = var.indexOf("-");
                String descripcion = var.substring(0, extraer);
                String extras = var.substring(extraer+1,var.length());
                
                fila[0] = rs.getInt("POSICION");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getDouble("UNIDADES");
                
                fila[3] = descripcion;
                fila[4] = extras;
                fila[5] = rs.getDouble("PRECIO_UNITARIO");
                fila[6] = rs.getDouble("PCTJE_DSCTO");        
                fila[7] = rs.getDouble("PRECIO_TOTAL_NETO");
                modelo.addRow(fila);
                or.tablaProductos.setModel(modelo);
            }
            double sumatoria1=0.0;
            int totalRow= or.tablaProductos.getRowCount();
            if (totalRow!=0) {
                totalRow-=1;
        
            for(int i=0;i<=(totalRow);i++){
                double sumatoria= Double.parseDouble(String.valueOf(or.tablaProductos.getValueAt(i, 5)));
                sumatoria1+=sumatoria;
                System.out.println("LA SUMA TOTAL ES:"+sumatoria1);
                double anticipoVen = Double.parseDouble(anticipo);
                or.lblTotal.setText(""+(df.format(sumatoria1-anticipoVen)));
            }
            } else if(totalRow==0){
                or.lblTotal.setText("0.0");
            }
            ModificarOrdenes.fecha = (String) tablaOrdenes.getValueAt(row, 5);
            int lastRow = ModificarOrdenes.tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
            ModificarOrdenes.tablaProductos.setRowSelectionInterval(lastRow, lastRow);
            ModificarOrdenes.tablaProductos.setColumnSelectionInterval(1, 1);
            ModificarOrdenes.tablaProductos.requestFocus();
            ModificarOrdenes.selectAllAfterCreate();
            ModificarOrdenes.calcularTotal();
            or.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLiquidar3ActionPerformed

    private void btnLiquidar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar4ActionPerformed
        int numero=Integer.parseInt(JOptionPane.showInputDialog("INGRESE LA CANTIDAD DE TICKETS A REIMPRIMIR"));
        
        String folio = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 1);
        reimprimirTicket reimprimir = new reimprimirTicket();
        reimprimir.impresion(cn, folio, numero);
        
        System.out.println(folio);
    }//GEN-LAST:event_btnLiquidar4ActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        cargarOrdenes();
    }//GEN-LAST:event_btnBuscar1ActionPerformed

   
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaOrdenes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnBuscar;
    private rscomponentshade.RSButtonShade btnBuscar1;
    private rscomponentshade.RSButtonShade btnLiquidar2;
    private rscomponentshade.RSButtonShade btnLiquidar3;
    private rscomponentshade.RSButtonShade btnLiquidar4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tablaOrdenes;
    public static rscomponentshade.RSTextFieldShade txtNombreCliente;
    // End of variables declaration//GEN-END:variables
}
