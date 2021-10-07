
package com.sicws.puntoeventatouch.main;

import puntodeventatouch.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import rojeru_san.RSButton;
import com.sicws.puntodeventatouch.conexion.*;
import com.sicws.puntodeventatouch.consultas.*;
import com.sicws.puntodeventatouch.ticket.imprimirTicket;
import com.sicws.puntodeventatouch.ticket.imprimirTicketVenta;
import com.sicws.puntodeventatouch.utileria.Rendercheck;
import com.sicws.puntoeventatouch.main.Main;
import java.awt.Image;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import rscomponentshade.RSToggleButtonShade;


public class NuevaVenta extends javax.swing.JFrame {

    conexionBD con = new conexionBD();
    Connection cn = con.conexion();
    Renderer r = new Renderer();
    Statement stm;
    ResultSet rs;
    boolean suspender;
    JTextField text;
    private static DecimalFormat df = new DecimalFormat("0.00");
    public static int idDocumento;
    public static String nombre;
    
   // public static int dias;
    //public static double saldo;
    
    cobrarCredito cobro = new cobrarCredito();
    
    JCheckBox check = new JCheckBox();
    DefaultCellEditor defaultCelle = new DefaultCellEditor(check);
    Rendercheck claserender = new Rendercheck();
    
    entradaInventario inventario = new entradaInventario();

    public NuevaVenta() {
        initComponents();
        verificar();
        cargarCombo();
        cargarComboFormas();
        this.setTitle("Venta");
        txtCantidad.setName("txtCantidad");
        txtImporte.setName("txtImporte");
        txtAnticipo.setName("txtAnticipo");
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("ARTICULO");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("NOTAS");
        modelo.addColumn("PRECIO");
        modelo.addColumn("TOTAL");
       
        tablaProductos.setModel(modelo);
        TableColumnModel columnModel = tablaProductos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(280);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);
        JTableHeader header = tablaProductos.getTableHeader();
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 1, new java.awt.Color(232, 232, 232)));
        header.setOpaque(false);
        header.setBackground(new Color(60,119,147));
        header.setForeground(new Color(255,255,255));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        header.setFont(new Font ("Segoe UI", Font.BOLD, 14));
        header.setToolTipText("Tabla Articulos");
        tablaProductos.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        tablaProductos.setForeground(new Color(0,0,0));
        tablaProductos.getTableHeader().setOpaque(false);
        tablaProductos.setRowHeight(30);
        tablaProductos.setRowHeight(30);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);


        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");


        mostrarLineas();
        panelLineas.setMaximumSize(new Dimension(400, 400));
        suspender=false;
        tablaProductos.setDefaultRenderer(Object.class, r);
        
        this.setExtendedState(6);
        
        botonesTeclado(text);
        
        txtCantidad.addFocusListener(new FocusListener(){
        

   @Override
   public void focusGained(FocusEvent arg0) {

       panelTeclado.removeAll();
       panelTeclado.updateUI();
       botonesTeclado(txtCantidad);
       
       if (txtCantidad.getText().isEmpty()) {
           
       } else {
           RSButton boton = (RSButton) panelTeclado.getComponent(9);
           try {
               Statement st;
               st=cn.createStatement();
               ResultSet rs = st.executeQuery("select es_peso_variable from articulos where nombre = '"+txtArticulo.getText()+"'");
               String var = "";
               while (rs.next()) {
                   var = rs.getString("es_peso_variable");
               }
               
               if (var.equals("S")) {
                   boton.setEnabled(true);
               } else {
                   boton.setEnabled(false);
               }
           } catch (SQLException ex) {
               Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }


   @Override
   public void focusLost(FocusEvent arg0) {
//    pop.setVisible(false);
//       panel.removeAll();
//       panel.updateUI();
       
   }
   
  });
        txtImporte.addFocusListener(new FocusListener(){


   @Override
   public void focusGained(FocusEvent arg0) {
       panelTeclado.removeAll();
       panelTeclado.updateUI();
       botonesTeclado(txtImporte);
   }


   @Override
   public void focusLost(FocusEvent arg0) {
//    pop.setVisible(false);
//       panel.removeAll();
//       panel.updateUI();
   }
   
  });
        
        txtAnticipo.addFocusListener(new FocusListener(){


   @Override
   public void focusGained(FocusEvent arg0) {
       panelTeclado.removeAll();
       panelTeclado.updateUI();
       botonesTeclado(txtAnticipo);
   }


   @Override
   public void focusLost(FocusEvent arg0) {
//    pop.setVisible(false);
//       panel.removeAll();
//       panel.updateUI();
   }
   
  });
       
       
    }
    
    public static void verificarDiasSaldo(){
       int dias = Integer.valueOf(lblDias.getText());
       double saldo = Double.parseDouble(lblSaldo.getText());
       DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
       System.out.println("DDDDDIAS"+dias);
        if (dias>0&&saldo>0) {
            JOptionPane.showMessageDialog(null, "ESTA ORDEN DE VENTA CUENTA CON:\n DIAS VENCIDOS: "+dias+" Y UN MONTO EXTRA DE: $"+saldo+"\n LA CANTIDAD SE AGREGARA AL TOTAL");
            Object [] fila = new Object[6];
            fila[0] = tablaProductos.getRowCount()+1;
            fila[1] = "MULTA";
            fila[2] = 1.0;
            fila[3] = dias+" DIAS VENCIDOS";
            fila[4] = saldo; 
            fila[5] = saldo;
            modelo.addRow(fila);
            tablaProductos.setModel(modelo);
            System.out.println("NO ES PESABLE");
            int lastRow = tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
            tablaProductos.setRowSelectionInterval(lastRow, lastRow);
            tablaProductos.setColumnSelectionInterval(1, 1);
            tablaProductos.requestFocus();
            calcularTotal();
        } else {
            
        }
    }
    
    public static void selectAllAfterCreate(){
        txtSubtotal.setText(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 5)));
        txtPrecio.setText(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 4)));
        txtArticulo.setText(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 1)));
        txtCantidad.setText(String.valueOf((double) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 2)));
        txtAreaNotas.setText(String.valueOf(tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 3)));
        setFocusFieldValidate();
    }
    
    public static void setFocusFieldValidate(){
    
        if (tgbCantidad.isSelected()) {
            txtCantidad.requestFocus();
            txtCantidad.selectAll();    
        } else if (tgbImporte.isSelected()){
            txtImporte.requestFocus();
            txtImporte.selectAll();
        }
    
    }
    public void cargarCombo(){
    
        if (nombre!=null) {
            cbxClientes.addItem(nombre);
        }  else {
            try {
                Statement cliente = cn.createStatement();
                ResultSet rs = cliente.executeQuery("SELECT NOMBRE FROM CLIENTES WHERE ESTATUS='A'");
                while (rs.next()) {                
                    cbxClientes.addItem(rs.getString("nombre"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
     public void cargarComboFormas(){
    
        try {
            Statement cliente = cn.createStatement();
            ResultSet rs = cliente.executeQuery("SELECT NOMBRE FROM FORMAS_COBRO WHERE FORMA_COBRO_ID!=68");
            while (rs.next()) {                
                cbxFormas.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    
    }
    /*@Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("com/sicws/puntodeventatouch/iconos/SIC MargenesICO.png"));
        return retValue;
    }*/
    public void verificar(){
            try {
            Statement estatusCaja = cn.createStatement();
            ResultSet rs = estatusCaja.executeQuery("select tipo_movto from movtos_cajas where movto_caja_id = (select max(movto_caja_id) from movtos_cajas)");
            String cadena = null;
            while (rs.next()) {                
                cadena = (rs.getString("tipo_movto"));
            }
            if (cadena.equals("C")) {
                System.out.println("CAJA CERRADA");
                JOptionPane.showMessageDialog(null, "CAJA CERRADA");
                System.exit(0);
            } else {
                System.out.println("CAJA ABIERTA");
                
//                ventana ven = new ventana();
//                ven.setVisible(true);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        }
    public void calcularCambio(){
        
        if (txtImporte.getText().equals("")) {
            txtImporte.setText("0.0");
        } else {
            double importe = Double.parseDouble(txtImporte.getText());
            double total = Double.parseDouble(lblTotal.getText());
            double resultado = 0;
            //double anticipo = Double.parseDouble(txtAnticipo.getText());
            resultado = importe-total;
            lblCambio.setText(String.valueOf(df.format(resultado)));
        }
        
    
    }
    void mostrarLineas(){
        double uno = 1.0;
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
        separadoresPersonalizados.setDecimalSeparator('.');
        DecimalFormat formato1 = new DecimalFormat("#0.00", separadoresPersonalizados);
        int columna = 0;
        int fila = 0;
        int x = 1;
        int total = 8;
        int componentes = 20;
       Font font = new Font("Segoe UI", Font.BOLD, 12);
       Font font2 = new Font("Segoe UI", Font.BOLD, 9);
//       Color colorV = new Color(83,90,90);
       Color colorV = new Color(40,79,98);
       Color colorP = new Color(60,119,147);
       Color selected = new Color(67,150,209);
//       Color nullo = new Color(149,165,166);
        Color nullo = new Color(232,232,232);
        ButtonGroup grupo = new ButtonGroup();

         try {
        
        
        String sql = "select nombre from lineas_articulos";     
        Statement st;
        try {
            st=cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                RSToggleButtonShade botonFProducto = new RSToggleButtonShade();
                
                botonFProducto.setText(rs.getString("NOMBRE"));
                botonFProducto.setBgShade(Color.white);
                botonFProducto.setBgHover(selected);
                botonFProducto.setBgShadeHover(Color.white);
                botonFProducto.setBackground(colorP);
                botonFProducto.setFont(font);
                botonFProducto.setPixels(0);
                botonFProducto.setRound(0);
                grupo.add(botonFProducto);
                botonFProducto.setPreferredSize(new Dimension(100,40));
//                botonFProducto.setBounds(columna, fila, 125, 80);
                panelLineas.add(botonFProducto);
                panelLineas.updateUI();
                
                x++;
                
               
                
                
//                columna += 130;
                botonFProducto.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent arg0) {
                        
                        Object ValorBoton = botonFProducto.getText();
                        
                        
         try {
        
        
        String sql = "select nombre from articulos WHERE linea_articulo_id=(select linea_articulo_id from lineas_articulos where nombre='"+ValorBoton+"') and estatus = 'A'";
//            
        Statement st;
        panelArticulos.removeAll();
        try {
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
                botonFProducto.setPreferredSize(new Dimension (100,80));
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
//                                 txtPrecio.setText(rs.getString("precio"));
                                 datoPrecio = rs.getString("precio");
                                 
                             }
                             System.out.println("PREEEECIO"+datoPrecio);
                            
                        } catch (Exception e) {
                        }
                         
                         //String sqlPeso = "select es_peso_variable from articulos where nombre = '"+ValorBoton+"'";
                         try {
                            /*st=cn.createStatement();
                            ResultSet rs = st.executeQuery(sqlPeso);
                             String dato = "";
                             while (rs.next()) {    
                                 dato = rs.getString("es_peso_variable");
                             }*/
                             double precio = Double.parseDouble(datoPrecio);
                             
                             /*if (dato.equals("S")) {
                                 RSButton bot = (RSButton) panelTeclado.getComponent(9);
                                 bot.setEnabled(false);
                                try {
                                    SerialPort serialPort = new SerialPort();
                                    List<String> lstFreeSerialPort = serialPort.getFreeSerialPort();//Gets a list of serial ports free 
                                        if(lstFreeSerialPort.size()>0){//if there are free ports
                                            Parameters parameters = new Parameters();//Create a parameter object
                                            parameters.setPort(lstFreeSerialPort.get(0));//assigns the first port found
                                            parameters.setBaudRate(Baud._9600);//assigns baud rate
                                            parameters.setByteSize("8");// assigns byte size
                                            parameters.setParity("N");// assigns parity
                                            parameters.setStopBits("1");
//            while (true) {                
                                            Com com  = new  Com(parameters);// With the "parameters" creates a "Com"
                                            int j = 0;
                                            char [] cadena = new char[11];
                                                for (int i = 0; i < 11; i++) {//Send and receive data every 800 milliseconds
                                                    com.sendSingleData("p");
                                                    Thread.sleep(50);//Wait 0.8 seconds
                                                    int dataReceived =com.receiveSingleCharAsInteger();
                                                    char ch = (char) dataReceived;
                                                    cadena[j]=ch;
                                                    j++;   
                                                }          
                                            String str = String.valueOf(cadena);
                                            String subCadenaOperacion = str.substring(3,8);
                                            double conversion = Double.parseDouble(subCadenaOperacion);
                                            String subCadenaTxt = str.substring(3,9);
                                            double multiplicacion = conversion * (precio);
                                            String resultado = String.valueOf(formato1.format(multiplicacion));
                                            //AGREGAR CON BASCULA
                                            txtCantidad.setText(subCadenaTxt);
                                            txtPrecio.setText(""+df.format(precio));
                                            txtSubtotal.setText(resultado);
                                            Object [] fila = new Object[6];
                                           fila[0] = tablaProductos.getRowCount()+1;
                                           fila[1] = ValorBoton;
                                           fila[2] = conversion;
                                           fila[3] = "KG";
                                           fila[4] = df.format(precio); 
                                           fila[5] = multiplicacion;
                                           modelo.addRow(fila);
                                           tablaProductos.setModel(modelo);
                                            com.close();
                                            
                                            
//            }        
                                        }else{
                                            //AGREGAR SIN BASCULA
//                                           txtCantidad.setText("1");
//                                           txtPrecio.setText(""+df.format(precio));
//                                           txtTotalp.setText(""+df.format(precio));
                                           lblMessage.setText("No se a detectado conexion a bascula");
                                           Object [] fila = new Object[6];
                                           fila[0] = tablaProductos.getRowCount()+1;
                                           fila[1] = ValorBoton;
                                           fila[2] = uno;
                                           fila[3] = "KG";
                                           fila[4] = df.format(precio); 
                                           fila[5] = df.format(precio);
                                           modelo.addRow(fila);
                                           tablaProductos.setModel(modelo);
                                           
//        }                                 
                                        }

                                } catch (Exception e) {
                                    System.out.println(e);
                                    JOptionPane.showMessageDialog(null, e);
                                }    
                                 System.out.println("ES PESABLE");
                                } else {*/
                                 //AGREGAR NO PESABLE
//                                 txtCantidad.setText("1");
                                 String cantidad = txtCantidad.getText();
                                 //double cantidadD = Double.parseDouble(cantidad);
//                               double precio = Double.parseDouble(datoPrecio);
                                 String resultado = String.valueOf(formato1.format(precio));
                    //           txtCantidad.setText(subCadenaTxt);
//                                txtPrecio.setText(resultado);
//                                txtTotalp.setText(resultado);
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
                             //}
                             
                            int lastRow = tablaProductos.convertRowIndexToView(modelo.getRowCount() - 1);
                            tablaProductos.setRowSelectionInterval(lastRow, lastRow);
                            tablaProductos.setColumnSelectionInterval(1, 1);
                            tablaProductos.requestFocus();
        double canti =      (double) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 2);
        
        selectAllAfterCreate();
        calcularTotal();
                             
                             
                        } catch (Exception e) {
                           Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, e);
                            JOptionPane.showMessageDialog(null, e);
                        }
                        System.out.println(ValorBoton);
                    }
                });
            
            }
            int existentesOtro = panelArticulos.getComponentCount();
        
        int resultadoExistente = componentes-existentesOtro;
            for (int i = 0; i < resultadoExistente; i++) {
                    RSButton botonNullo = new RSButton();
//                    botonNullo.setBgShade(Color.white);
//                    botonNullo.setBgHover(nullo);
//                    botonNullo.setBgShadeHover(Color.white);
                    botonNullo.setColorHover(nullo);
                    botonNullo.setBackground(nullo);
                    botonNullo.setPreferredSize(new Dimension(100,80));
//                    botonNullo.set
                    panelArticulos.add(botonNullo);
                    
//                    i++;
                }
        
        
        } 
        catch (SQLException ex) {
            Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
                
            }
         catch (Exception e) {
           Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, e);
             JOptionPane.showMessageDialog(null, e);
        }
                        System.out.println(ValorBoton);
                        
                    }
                });
            
            } 
            
             int existentes = panelLineas.getComponentCount();
             int resultadoTotal = total-existentes;
             for (int i = 0; i < resultadoTotal; i++) {
                    RSButton botonNullo = new RSButton();
//                    botonNullo.setBgShade(Color.white);
                    botonNullo.setColorHover(nullo);
//                    botonNullo.setBgShadeHover(Color.white);
                    botonNullo.setBackground(nullo);
                    botonNullo.setPreferredSize(new Dimension(100,40));
                    panelLineas.add(botonNullo);
                    
//                    i++;
                }
        
        
        }
       
        catch (SQLException ex) {
            Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
                
            }
         catch (Exception e) {
           System.out.println(e);
             JOptionPane.showMessageDialog(null, e);
        }
    
    
     
     RSToggleButtonShade bot = (RSToggleButtonShade) panelLineas.getComponent(0);
     bot.doClick();
     
    }
    public void botonesTeclado(JTextField t){
        Font font = new Font("Segoe UI", Font.BOLD, 18);
        Color color = new Color(0,149,126);
        text = t;
        String array[]= {"7","8","9","4","5","6","1","2","3",".","0","<-"};
        
        for (int i = 0; i < array.length; i++) {
            RSButton botonFProducto = new RSButton();
                botonFProducto.setText(array[i]);
//                botonFProducto.setBgShade(Color.white);
                botonFProducto.setFont(font);
//                botonFProducto.setBgHover(Color.white);
//                botonFProducto.setBgShadeHover(Color.white);
                botonFProducto.setBackground(color);
//                botonFProducto.setFont(font);
                panelTeclado.add(botonFProducto);
                
                 botonFProducto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        Object ValorBoton = botonFProducto.getText();
                          
                        if (t.getName().equals("txtCantidad")) {
                                if (t.getSelectedText()!=null) {
                            System.out.println("tiene texto");
                            t.setText("");
                            if (botonFProducto.getText()!="<-") {
                            String anterior = t.getText();
                            t.setText(anterior+botonFProducto.getText());
                            sobreEscribir();
                        } else {
                            if(t.getText().length()!=0){
                            t.setText("");
                            sobreEscribir();
                            
                        }
                        }
                        } else {
                            System.out.println("no tiene");
                            if (botonFProducto.getText()!="<-") {
                            String anterior = t.getText();
                            t.setText(anterior+botonFProducto.getText());
                            sobreEscribir();
                        } else {
                            if(t.getText().length()!=0){
                            t.setText("");
                            sobreEscribir();
                        }
                        }
                        }
                        
                                System.out.println("true");
                                calcularSubTotal();
                        } else {
                            if (t.getSelectedText()!=null) {
                            System.out.println("tiene texto");
                            t.setText("");
                            if (botonFProducto.getText()!="<-") {
                            String anterior = t.getText();
                            t.setText(anterior+botonFProducto.getText());
                            calcularCambio();
                        } else {
                            if(t.getText().length()!=0){
                            t.setText("");
                            calcularCambio();
                        }
                        }
                        } else {
                            System.out.println("no tiene");
                            if (botonFProducto.getText()!="<-") {
                            String anterior = t.getText();
                            t.setText(anterior+botonFProducto.getText());
                            calcularCambio();
                        } else {
                            if(t.getText().length()!=0){
                            t.setText("");
                            calcularCambio();
                        }
                        }
                        }
                            System.out.println("false");
                        }
                        calcularSubTotal();
                        
                        System.out.println(ValorBoton);
                    }
                });
        }
    
    
    }
    
    public void cargarTablaCobros(){
    
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("CONCEPTO");
            modelo.addColumn("FOLIO");
            modelo.addColumn("FECHA");
            modelo.addColumn("SALDO");
             modelo.addColumn("ACREDITAR");
            modelo.addColumn("");
            tablaProductos.setModel(modelo);
            TableColumnModel columnModel = tablaProductos.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(230);
            columnModel.getColumn(1).setPreferredWidth(120);
            columnModel.getColumn(2).setPreferredWidth(120);
            columnModel.getColumn(3).setPreferredWidth(120);
            columnModel.getColumn(4).setPreferredWidth(160);
            columnModel.getColumn(5).setPreferredWidth(80);
            JTableHeader header = tablaProductos.getTableHeader();
            header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 1, new java.awt.Color(232, 232, 232)));
            header.setOpaque(false);
            header.setBackground(new Color(60,119,147));
            header.setForeground(new Color(255,255,255));
            header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            header.setFont(new Font ("Segoe UI", Font.BOLD, 14));
            header.setToolTipText("Tabla Articulos");
            tablaProductos.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
            tablaProductos.setForeground(new Color(0,0,0));
            tablaProductos.getTableHeader().setOpaque(false);
            tablaProductos.setRowHeight(30);
            Double acreditar = 0.0;
            Object datos[]=new Object[11];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOMBRE_ABREV_CONCEPTO,FOLIO,FECHA,saldo_cargo FROM get_cargos_cc(DATE'NOW','P',0,'S',null, 'S') WHERE CLIENTE_ID=(SELECT CLIENTE_ID FROM CLIENTES WHERE NOMBRE='"+cbxClientes.getSelectedItem()+"')");
            
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getDouble(4);
                datos[4]=acreditar;
                datos[5]=false;
                modelo.addRow(datos);
            }
//            tablaProductos.setModel(modelo);
            tablaProductos.getColumnModel().getColumn(5).setCellEditor(defaultCelle);
          
        tablaProductos.setDefaultRenderer(tablaProductos.getColumnClass(0), claserender);
        } catch (SQLException ex) {
            Logger.getLogger(NuevaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void calcularTotal()
    {
        double sumatoria1=0.0;
        double anticipo = Double.parseDouble(txtAnticipo.getText());
        //double antic   
                
        int totalRow= tablaProductos.getRowCount();
        if (totalRow!=0) {
            totalRow-=1;
        
        for(int i=0;i<=(totalRow);i++)
        {
//             String total = (String) tablaProductos.getValueAt(i, 3);
             double sumatoria= Double.parseDouble(String.valueOf(tablaProductos.getValueAt(i, 5)));
//en la parte de arriba indica el primer parametro la fila y el segundo la columna la cual estaras //manejando
             sumatoria1+=sumatoria;
 
          System.out.println("LA SUMA TOTAL ES:"+sumatoria1);
          lblTotal.setText(""+(df.format(sumatoria1-anticipo)));
            
           }
        } else if(totalRow==0){
        lblTotal.setText("0.0");
        
        }
        
        
        
        
            
    } 
    
    public void sobreEscribir(){
    calcularSubTotal();
        if (tgbCantidad.isSelected()) {
            
//            txt.requestFocus();
            tablaProductos.setValueAt(Double.parseDouble(txtCantidad.getText()), tablaProductos.getSelectedRow(), 2);
            tablaProductos.setValueAt(Double.parseDouble(txtSubtotal.getText()), tablaProductos.getSelectedRow(), 5);
        } else {
            
        }
        
    calcularTotal();
        
    }
    
    public void calcularSubTotal(){
        if (txtPrecio.getText().isEmpty() || txtCantidad.getText().isEmpty()) {
            System.out.println("NO SE PUEDE CALCULAR SUBTOTAL");
        } else {
            double precio = Double.parseDouble(txtPrecio.getText());
            double cantidad = Double.parseDouble(txtCantidad.getText());
            double subtotal = precio * cantidad;
            txtSubtotal.setText(String.valueOf(df.format(subtotal)));
        }
    }
 public void limpiarCampos(){
 
 txtArticulo.setText("");
 txtCantidad.setText("");
 txtSubtotal.setText("");
 txtPrecio.setText("");
 txtAreaNotas.setText("");
// lblTotal.setText("0.0");
 }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelPrincipal = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        tgbImporte = new rscomponentshade.RSToggleButtonShade();
        tgbCantidad = new rscomponentshade.RSToggleButtonShade();
        btnEliminar = new rscomponentshade.RSButtonShade();
        btnLiquidar = new rscomponentshade.RSButtonShade();
        btnLimpiar = new rscomponentshade.RSButtonShade();
        btnCancelar = new rscomponentshade.RSButtonShade();
        panelTeclado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelLineas = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelArticulos = new javax.swing.JPanel();
        panelVentas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtImporte = new rscomponentshade.RSTextFieldShade();
        lblCambio = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxFormas = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAnticipo = new rscomponentshade.RSTextFieldShade();
        lblDias = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        txtArticulo = new rscomponentshade.RSTextFieldShade();
        txtPrecio = new rscomponentshade.RSTextFieldShade();
        txtCantidad = new rscomponentshade.RSTextFieldShade();
        txtSubtotal = new rscomponentshade.RSTextFieldShade();
        cbxClientes = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaNotas = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        panelPrincipal.setBackground(new java.awt.Color(242, 242, 242));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(4, 2, 2, 2));

        tgbImporte.setBackground(new java.awt.Color(0, 168, 235));
        tgbImporte.setBorder(null);
        buttonGroup1.add(tgbImporte);
        tgbImporte.setText("IMPORTE");
        tgbImporte.setBgHover(new java.awt.Color(0, 149, 126));
        tgbImporte.setBgShade(new java.awt.Color(255, 255, 255));
        tgbImporte.setBgShadeHover(new java.awt.Color(255, 255, 255));
        tgbImporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tgbImporte.setPixels(0);
        tgbImporte.setRound(0);
        tgbImporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbImporteActionPerformed(evt);
            }
        });
        jPanel8.add(tgbImporte);

        tgbCantidad.setBackground(new java.awt.Color(0, 168, 235));
        buttonGroup1.add(tgbCantidad);
        tgbCantidad.setSelected(true);
        tgbCantidad.setText("CANTIDAD");
        tgbCantidad.setBgHover(new java.awt.Color(0, 149, 126));
        tgbCantidad.setBgShade(new java.awt.Color(255, 255, 255));
        tgbCantidad.setBgShadeHover(new java.awt.Color(255, 255, 255));
        tgbCantidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tgbCantidad.setPixels(0);
        tgbCantidad.setRound(0);
        tgbCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbCantidadActionPerformed(evt);
            }
        });
        jPanel8.add(tgbCantidad);

        btnEliminar.setBackground(new java.awt.Color(0, 168, 235));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setBgHover(new java.awt.Color(0, 155, 219));
        btnEliminar.setBgShade(new java.awt.Color(255, 255, 255));
        btnEliminar.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setPixels(0);
        btnEliminar.setRound(0);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel8.add(btnEliminar);

        btnLiquidar.setBackground(new java.awt.Color(0, 168, 235));
        btnLiquidar.setText("LIQUIDAR");
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
        jPanel8.add(btnLiquidar);

        btnLimpiar.setBackground(new java.awt.Color(0, 168, 235));
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.setBgHover(new java.awt.Color(0, 155, 219));
        btnLimpiar.setBgShade(new java.awt.Color(255, 255, 255));
        btnLimpiar.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiar.setPixels(0);
        btnLimpiar.setRound(0);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel8.add(btnLimpiar);

        btnCancelar.setBackground(new java.awt.Color(0, 168, 235));
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBgHover(new java.awt.Color(0, 155, 219));
        btnCancelar.setBgShade(new java.awt.Color(255, 255, 255));
        btnCancelar.setBgShadeHover(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setPixels(0);
        btnCancelar.setRound(0);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel8.add(btnCancelar);

        panelTeclado.setBackground(new java.awt.Color(255, 255, 255));
        panelTeclado.setLayout(new java.awt.GridLayout(4, 3, 2, 2));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBar(null);

        panelLineas.setBackground(new java.awt.Color(255, 255, 255));
        panelLineas.setLayout(new java.awt.GridLayout(0, 4, 2, 2));
        jScrollPane2.setViewportView(panelLineas);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        );

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelArticulos.setBackground(new java.awt.Color(255, 255, 255));
        panelArticulos.setMaximumSize(new java.awt.Dimension(400, 32767));
        panelArticulos.setLayout(new java.awt.GridLayout(0, 3, 2, 2));
        jScrollPane3.setViewportView(panelArticulos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        panelVentas.setBackground(new java.awt.Color(60, 119, 147));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 119, 147), 2));
        jScrollPane1.setForeground(new java.awt.Color(34, 49, 63));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProductos.setGridColor(new java.awt.Color(255, 255, 255));
        tablaProductos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaProductos.setRowHeight(25);
        tablaProductos.setSelectionBackground(new java.awt.Color(67, 150, 209));
        tablaProductos.setShowVerticalLines(false);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        jPanel4.setBackground(new java.awt.Color(40, 79, 98));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TOTAL");

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("0.0");
        lblTotal.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("FORMA DE COBRO");

        txtImporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporte.setText("0.0");
        txtImporte.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtImporte.setPixels(0);
        txtImporte.setPlaceholder("");
        txtImporte.setRound(0);
        txtImporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtImporteMouseClicked(evt);
            }
        });
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        lblCambio.setBackground(new java.awt.Color(255, 255, 255));
        lblCambio.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambio.setText("0.0");
        lblCambio.setOpaque(true);

        lblMessage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 51, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CAMBIO");

        cbxFormas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbxFormas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFormasActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("IMPORTE");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ANTICIPO");

        txtAnticipo.setEditable(false);
        txtAnticipo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAnticipo.setText("0.0");
        txtAnticipo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAnticipo.setPixels(0);
        txtAnticipo.setPlaceholder("");
        txtAnticipo.setRound(0);
        txtAnticipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAnticipoMouseClicked(evt);
            }
        });

        lblDias.setForeground(new java.awt.Color(40, 79, 98));
        lblDias.setText("0");

        lblSaldo.setForeground(new java.awt.Color(40, 79, 98));
        lblSaldo.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblDias, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxFormas, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxFormas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAnticipo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDias, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        txtArticulo.setEditable(false);
        txtArticulo.setFocusable(false);
        txtArticulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtArticulo.setPixels(0);
        txtArticulo.setPlaceholder("ARTICULO");
        txtArticulo.setRound(0);

        txtPrecio.setEditable(false);
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setFocusable(false);
        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtPrecio.setPixels(0);
        txtPrecio.setPlaceholder("PRECIO");
        txtPrecio.setRound(0);

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtCantidad.setPixels(0);
        txtCantidad.setPlaceholder("CANTIDAD");
        txtCantidad.setRound(0);
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

        txtSubtotal.setEditable(false);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotal.setFocusable(false);
        txtSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtSubtotal.setPixels(0);
        txtSubtotal.setPlaceholder("SUBTOTAL");
        txtSubtotal.setRound(0);

        cbxClientes.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        cbxClientes.setFocusable(false);
        cbxClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxClientesActionPerformed(evt);
            }
        });

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAreaNotas.setColumns(20);
        txtAreaNotas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAreaNotas.setLineWrap(true);
        txtAreaNotas.setRows(5);
        txtAreaNotas.setAutoscrolls(false);
        txtAreaNotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaNotasKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(txtAreaNotas);

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addComponent(txtArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addComponent(cbxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(panelTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 510, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelTeclado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
         DefaultTableModel model= (DefaultTableModel) tablaProductos.getModel();
        
        int i=tablaProductos.getSelectedRow();
        System.out.println(i);
//        for (int j = 0; j < tablaProductos.getSelectedRow(); j++) {
//            model.removeRow(i);
//        }
        if (i>=0){
            model.removeRow(i);
            lblTotal.setText("");
            calcularTotal();
        } else{
            JOptionPane.showMessageDialog(null, "No hay datos que elimnar");
        }
        calcularTotal();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        DefaultTableModel model= (DefaultTableModel) tablaProductos.getModel();
        if (JOptionPane.showConfirmDialog(rootPane, "¿DESEA CANCELAR LA VENTA?",
        "Traspasar Venta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            for (int i = tablaProductos.getRowCount() -1; i >= 0; i--){
                        model.removeRow(i);
                        limpiarCampos();
                        lblTotal.setText("0.0");
                        lblCambio.setText("0.0");
                        txtImporte.setText("0.0");
                        txtAnticipo.setText("0.0");
                }
        }
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        //        Res = new Resizable();
//        this.panelLineas.set
//        ComponentResizer cr = new ComponentResizer();
//        cr.setSnapSize(new Dimension(600, 300));
//cr.registerComponent(panelLineas);
    }//GEN-LAST:event_formWindowStateChanged

    
    private void btnLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidarActionPerformed
        finalizarVenta venta = new finalizarVenta();
        Double anticipo = Double.parseDouble(txtAnticipo.getText());
        Double importe = Double.parseDouble(txtImporte.getText());
        Double total = Double.parseDouble(lblTotal.getText());
        if (txtImporte.getText().equals("0.0") && lblCambio.getText().startsWith("-")) {
            JOptionPane.showMessageDialog(null, "EL IMPORTE O EL CAMBIO ES INCORRECTO");
        } else if(total==0 && importe>0){
            JOptionPane.showMessageDialog(null, "ESTA ORDEN YA FUE PAGADA CON EL ANTICIPO");
            txtImporte.setText("0.0");
            calcularCambio();
        }else {
            if (JOptionPane.showConfirmDialog(rootPane, "¿DESEA LIQUIDAR ESTA VENTA?",
                "Traspasar Venta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                System.out.println("Documento a Eliminar: "+idDocumento);
                venta.liquidarVenta(lblCambio, lblTotal, txtImporte, tablaProductos,cbxClientes,cbxFormas,txtAnticipo,idDocumento);
                limpiarCampos();
                this.dispose();
                ListaOrdenes list = new ListaOrdenes();
                list.setVisible(true);
            } 
        }
    }//GEN-LAST:event_btnLiquidarActionPerformed

    public static int getDiasHabiles(Date startDate, Date endDate){
    
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }
        
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        
        do {            
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        
    return workDays;
    }
    
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cbxClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxClientesActionPerformed
        
    }//GEN-LAST:event_cbxClientesActionPerformed

    private void txtCantidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCantidadMouseClicked
        //        txtCantidad.setSelectionStart(0);
        //        txtCantidad.se
        //        //        int j = 0;
        //        for (int i = 0; i < txtCantidad.getText().length(); i++) {
            //
            //            //            System.out.println(i+i);
            //            txtCantidad.setSelectionEnd(i+1);
            //        }
        txtAnticipo.requestFocus();
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtCantidadMouseClicked

    private void txtImporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtImporteMouseClicked
        txtImporte.setSelectionStart(0);
        //        int j = 0;
        for (int i = 0; i < txtImporte.getText().length(); i++) {

            //            System.out.println(i+i);
            txtImporte.setSelectionEnd(i+1);
        }
    }//GEN-LAST:event_txtImporteMouseClicked

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
      selectAllAfterCreate();
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void tgbCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbCantidadActionPerformed
       txtCantidad.requestFocus();
       txtCantidad.selectAll();
    }//GEN-LAST:event_tgbCantidadActionPerformed

    private void tgbImporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbImporteActionPerformed
       txtImporte.requestFocus();
       txtImporte.selectAll();
    }//GEN-LAST:event_tgbImporteActionPerformed

    private void cbxFormasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFormasActionPerformed
        System.out.println(cbxFormas.getSelectedItem());
    }//GEN-LAST:event_cbxFormasActionPerformed

    private void txtAreaNotasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaNotasKeyTyped
        tablaProductos.setValueAt(txtAreaNotas.getText(), tablaProductos.getSelectedRow(), 3);
        
        
    }//GEN-LAST:event_txtAreaNotasKeyTyped

    private void txtAnticipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAnticipoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnticipoMouseClicked

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        calcularCambio();
    }//GEN-LAST:event_txtImporteKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
       sobreEscribir();
    }//GEN-LAST:event_txtCantidadKeyTyped

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
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NuevaVenta().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rscomponentshade.RSButtonShade btnCancelar;
    private rscomponentshade.RSButtonShade btnEliminar;
    private rscomponentshade.RSButtonShade btnLimpiar;
    private rscomponentshade.RSButtonShade btnLiquidar;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox cbxClientes;
    private javax.swing.JComboBox cbxFormas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCambio;
    public static javax.swing.JLabel lblDias;
    private javax.swing.JLabel lblMessage;
    public static javax.swing.JLabel lblSaldo;
    public static javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelLineas;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTeclado;
    private javax.swing.JPanel panelVentas;
    public static javax.swing.JTable tablaProductos;
    public static rscomponentshade.RSToggleButtonShade tgbCantidad;
    public static rscomponentshade.RSToggleButtonShade tgbImporte;
    public static rscomponentshade.RSTextFieldShade txtAnticipo;
    public static javax.swing.JTextArea txtAreaNotas;
    public static rscomponentshade.RSTextFieldShade txtArticulo;
    public static rscomponentshade.RSTextFieldShade txtCantidad;
    public static rscomponentshade.RSTextFieldShade txtImporte;
    public static rscomponentshade.RSTextFieldShade txtPrecio;
    public static rscomponentshade.RSTextFieldShade txtSubtotal;
    // End of variables declaration//GEN-END:variables

}
