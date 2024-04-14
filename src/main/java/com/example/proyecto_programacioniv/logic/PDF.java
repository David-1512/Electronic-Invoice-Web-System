package com.example.proyecto_programacioniv.logic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

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

        PdfContentByte cb = writer.getDirectContent();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(0, 0, 0));
// Título de la factura
        drawText(cb, "FACTURACION S.A", 50, 750, font);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// Fecha de emisión
        drawText(cb, "Fecha emisión: " + dateFormat.format(factura.getFechEmision()), 50, 730, font);

// Datos del proveedor
        drawText(cb, "Proveedor", 50, 700, font);
        drawText(cb, "Nombre: " + factura.getProveedorByIdProveedor().getNombre(), 50, 680, font);
        drawText(cb, "Identificación: " + factura.getProveedorByIdProveedor().getId(), 50, 660, font);
        drawText(cb, "Telefono: " + factura.getProveedorByIdProveedor().getTelefono(), 50, 640, font);
        drawText(cb, "Correo electrónico: " + factura.getProveedorByIdProveedor().getCorreo(), 50, 620, font);

// Datos del cliente
        drawText(cb, "Cliente", 350, 700, font);
        drawText(cb, "Nombre: " + factura.getClienteByIdCliente().getNombre(), 350, 680, font);
        drawText(cb, "Identificación: " + factura.getClienteByIdCliente().getId(), 350, 660, font);
        drawText(cb, "Teléfono: " + factura.getClienteByIdCliente().getTelefono(), 350, 640, font);
        drawText(cb, "Correo Electrónico: " + factura.getClienteByIdCliente().getCorreo(), 350, 620, font);


// Detalles de la factura
        PdfPTable table = new PdfPTable(5);
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

        drawText(cb,"Monto total: "+factura.getTotal(),50,50,font);

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
