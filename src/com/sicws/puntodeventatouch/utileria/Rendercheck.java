/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sicws.puntodeventatouch.utileria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ASUS
 */
public class Rendercheck implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       JLabel label = new JLabel();
        JCheckBox check = new JCheckBox();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 12));
        if(model.getValueAt(row, column).getClass().equals(Boolean.class)){
        
        check.setSelected(Boolean.parseBoolean(model.getValueAt(row, column).toString()));
        check.setBackground(Color.white);
         
        return check;
        }
        
        if(column!=6){
        
        label.setText(model.getValueAt(row, column).toString());
            
        }
        return label;
       
    }
    
}