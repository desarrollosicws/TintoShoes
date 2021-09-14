
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
        tablaOrdenes.setModel(modelo);
        TableColumnModel columnModel = tablaOrdenes.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(250);
        columnModel.getColumn(4).setPreferredWidth(120);
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
            
            Object datos [] = new Object[5];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.DOCTO_PV_ID,A.FOLIO,A.FECHA,B.NOMBRE,B.CONTACTO1\n" +
            "FROM doctos_pv A\n" +
            "LEFT JOIN CLIENTES B ON B.CLIENTE_ID=A.CLIENTE_ID\n" +
            "WHERE A.TIPO_DOCTO='O' ORDER BY A.FOLIO DESC");
            
            while (rs.next()) {                
                datos[0]=rs.getInt(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
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
        btnLiquidar2 = new rscomponentshade.RSButtonShade();
        btnLiquidar3 = new rscomponentshade.RSButtonShade();
        btnLiquidar4 = new rscomponentshade.RSButtonShade();

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
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createSequentialGroup()
                    .addComponent(btnLiquidar2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnLiquidar3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnLiquidar4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    panelPrincipalLayout.setVerticalGroup(
        panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
            .addGap(17, 17, 17)
            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnLiquidar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLiquidar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLiquidar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT A.IMPORTE_NETO,A.PERSONA,B.NOTAS,B.POSICION,C.NOMBRE,B.UNIDADES,C.UNIDAD_VENTA,B.PRECIO_UNITARIO,B.PRECIO_TOTAL_NETO,d.nombre as cliente FROM doctos_pv A\n" +
            "LEFT JOIN doctos_pv_det B ON B.DOCTO_PV_ID = A.DOCTO_PV_ID\n" +
            "LEFT JOIN articulos C ON C.ARTICULO_ID = B.ARTICULO_ID\n" +
            "left join clientes d on d.cliente_id = a.cliente_id\n" +
            "WHERE A.DOCTO_PV_ID = '"+idDocumento+"'");
                        Object [] fila = new Object[6];
            while (rs.next()) {                
                anticipo = rs.getString("PERSONA");
                cliente = rs.getString(10);
                
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
       
            ven.txtAnticipo.setText(anticipo);
            
            ven.setVisible(true);
            this.dispose();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnLiquidar2ActionPerformed

    private void btnLiquidar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar3ActionPerformed
       /*SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        JDateChooser jd = new JDateChooser();
       
String message ="FECHA DE ENTREGA:\n";
Object[] params = {message,jd};
String fecha = String.valueOf(JOptionPane.showConfirmDialog(null,params,"FECHA", JOptionPane.PLAIN_MESSAGE));
Calendar dd = jd.getCalendar();
        System.out.println(jd.getDate());
        System.out.println(dd.getTime());
Calendar fechat = Calendar.getInstance();
System.out.println(fechat.getTime());
System.out.println(jd.getDateFormatString());

        try {
            Date d1 = sdf.parse(jd.getDate().toString());
            Date d2 = sdf.parse(fechat.getTime().toString());
            int result = d1.compareTo(d2);
            System.out.println("result: " + result);

        if (result == 0) {
            System.out.println("Date1 is equal to Date2");
        } else if (result > 0) {
            System.out.println("Date1 is after Date2");
        } else if (result < 0) {
            System.out.println("Date1 is before Date2");
        } else {
            System.out.println("How to get here?");
        }
        } catch (ParseException ex) {
            Logger.getLogger(ListaOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (jd.getDate()==fechat.getTime()) {
            System.out.println("gggg");
        }
        
*/ModificarOrdenes.nombre = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 3);
       ModificarOrdenes or = new ModificarOrdenes();
       int row = tablaOrdenes.getSelectedRow();
       or.lblFolio.setText((String) tablaOrdenes.getValueAt(row, 1));
       ModificarOrdenes.nombre = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 3);
       
       or.setVisible(true);
    }//GEN-LAST:event_btnLiquidar3ActionPerformed

    private void btnLiquidar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidar4ActionPerformed
        int numero=Integer.parseInt(JOptionPane.showInputDialog("INGRESE LA CANTIDAD DE TICKETS A REIMPRIMIR"));
        
        String folio = (String) tablaOrdenes.getValueAt(tablaOrdenes.getSelectedRow(), 1);
        reimprimirTicket reimprimir = new reimprimirTicket();
        reimprimir.impresion(cn, folio, numero);
        
        System.out.println(folio);
    }//GEN-LAST:event_btnLiquidar4ActionPerformed

   
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
    private rscomponentshade.RSButtonShade btnLiquidar2;
    private rscomponentshade.RSButtonShade btnLiquidar3;
    private rscomponentshade.RSButtonShade btnLiquidar4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tablaOrdenes;
    public static rscomponentshade.RSTextFieldShade txtNombreCliente;
    // End of variables declaration//GEN-END:variables
}
