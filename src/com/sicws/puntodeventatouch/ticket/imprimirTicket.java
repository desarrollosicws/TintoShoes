
package com.sicws.puntodeventatouch.ticket;

import com.sicws.puntodeventatouch.conexion.conexionBD;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.util.HashMap;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class imprimirTicket {
    public void impresion(Connection cn,String folioOrden){
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject("Orden80mm.jasper");
            HashMap param = new HashMap();
            param.put("Folio", folioOrden);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, cn);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printerJob.defaultPage(pageFormat);
            int selectedService = 0;
            AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("Generica", null));
            //AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("EC-PM-80250", null));
            PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);
            try {
                printerJob.setPrintService(printService[selectedService]);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));    
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
