
package puntodeventatouch;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class Renderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       
        
//        if(row % 2 ==0){
//            setBackground(new Color(248,248,248) );
//            setForeground(Color.black);
//        
//        }else{
//            setBackground(Color.white);
//            setForeground(Color.black);
//        }
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //To change body of generated methods, choose Tools | Templates.
        
        setBorder(noFocusBorder);
        return this;
    }
    
    
    
}
