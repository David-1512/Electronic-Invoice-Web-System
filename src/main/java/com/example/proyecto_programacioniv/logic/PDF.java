package com.example.proyecto_programacioniv.logic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class PDF {
    public void createPDF(FacturasEntity factura)throws IOException, DocumentException {
        try {

            Document document = new Document(PageSize.LETTER);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(factura.getClienteByIdCliente().getId()+"_"+
                    factura.getNumFactura()+".pdf"));
            document.open();

            Paragraph titulo = new Paragraph("FACTURACION S.A", FontFactory.getFont(FontFactory.HELVETICA, 12));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Paragraph fechaEmision = new Paragraph("FECHA: "+ dateFormat.format(factura.getFechEmision()), FontFactory.getFont(FontFactory.HELVETICA, 12));
            fechaEmision.setAlignment(Element.ALIGN_LEFT);
            document.add(fechaEmision);

            Paragraph numFactura = new Paragraph( "FACTURA: "+factura.getNumFactura(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            numFactura.setAlignment(Element.ALIGN_LEFT);
            document.add(numFactura);

            Paragraph Proveedor = new Paragraph( ": "+factura.getNumFactura(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            numFactura.setAlignment(Element.ALIGN_LEFT);
            document.add(numFactura);


            /*PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Paragraph("Código", font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Detalle", font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Cantidad", font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Precio Unitario", font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph("Subtotal", font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            for (LineaServicioEntity lineaServicio : factura.getLineaServiciosByNumFactura()) {
                table.addCell(new Paragraph(lineaServicio.getProductoByCodProducto().getCod(), font));
                table.addCell(new Paragraph(lineaServicio.getProductoByCodProducto().getNombre(), font));
                table.addCell(new Paragraph(String.valueOf(lineaServicio.getCantidad()), font));
                table.addCell(new Paragraph(String.valueOf(lineaServicio.getProductoByCodProducto().getPrecio()), font));
                table.addCell(new Paragraph(String.valueOf(lineaServicio.getSubtotal()), font));
            }
            document.add(table);

            drawText(cb,"Monto total: "+factura.getTotal(),50,50,font);*/

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void drawText(PdfContentByte cb, String text, float x, float y, Font font) {
        cb.beginText();
        cb.setFontAndSize(font.getBaseFont(), font.getSize());
        cb.setColorFill(new BaseColor(0, 0, 0));
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, x, y, 0);
        cb.endText();
    }
}