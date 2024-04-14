package com.example.proyecto_programacioniv.logic;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.BaseColor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class PDF {
    public void createPDF(FacturasEntity factura)throws IOException, DocumentException {
    try {

        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(factura.getClienteByIdCliente().getId()+"_"+
                factura.getNumFactura()));
        document.open();

        PdfContentByte cb = writer.getDirectContent();

        Font font = FontFactory.getFont("Times New Roman", 12, Font.BOLDITALIC, new BaseColor(0,0,0));
        drawText(cb,"FACTURACION S.A",894,57,font);
        drawText(cb,"Clave de factura: ",847,91,font);
        drawText(cb,factura.getNumFactura(),951,87,font);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        drawText(cb,"Fecha emisión: ",828,106,font);
        drawText(cb,dateFormat.format(factura.getFechEmision()),923,100,font);

        drawText(cb,"Proveedor",486,171,font);
        drawText(cb,"Nombre: ",486,214,font);
       drawText(cb,factura.getProveedorByIdProveedor().getNombre(),570,214,font);
        drawText(cb,"Identificación: ",486,230,font);
        drawText(cb,factura.getProveedorByIdProveedor().getId(),570,230,font);
        drawText(cb,"Telefono: ",486,241,font);
        drawText(cb,factura.getProveedorByIdProveedor().getTelefono(),570,241,font);
        drawText(cb,"Correo electrónico: ",486,253,font);
        drawText(cb,factura.getProveedorByIdProveedor().getCorreo(),570,253,font);

        drawText(cb,"Cliente",900,171,font);
        drawText(cb,"Nombre: ",900,214,font);
        drawText(cb,factura.getClienteByIdCliente().getNombre(),924,214,font);
        drawText(cb,"Identificación: ",900,230,font);
        drawText(cb,factura.getClienteByIdCliente().getId(),924,230,font);
        drawText(cb,"Teléfono: ",900,241,font);
        drawText(cb,factura.getClienteByIdCliente().getTelefono(),924,241,font);
        drawText(cb,"Correo Electrónico: ",900,253,font);
        drawText(cb,factura.getClienteByIdCliente().getCorreo(),924,253,font);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD,new BaseColor(0,0,0));
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell(new Paragraph("Código", headerFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(221, 221, 221));
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Detalle", headerFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(221, 221, 221));
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Cantidad", headerFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(221, 221, 221));
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Precio Unitario", headerFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(221, 221, 221));
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Subtotal", headerFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(221, 221, 221));
        table.addCell(cell);

        for (LineaServicioEntity lineaServicio : factura.getLineaServiciosByNumFactura()){
            table.addCell(new PdfPCell(new Paragraph(lineaServicio.getProductoByCodProducto().getCod(), cellFont)));
            table.addCell(new PdfPCell(new Paragraph(lineaServicio.getProductoByCodProducto().getNombre(), cellFont)));
            table.addCell(new Paragraph(String.valueOf(lineaServicio.getCantidad()), cellFont));
            table.addCell(new Paragraph(String.valueOf(lineaServicio.getProductoByCodProducto().getPrecio()), cellFont));
            table.addCell(new Paragraph(String.valueOf(lineaServicio.getSubtotal()), cellFont));
        }
        document.add(table);

        drawText(cb,"Monto total: ",668,810,font);
       // drawText(cb,factura.getTotal(),668,923,font);

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
