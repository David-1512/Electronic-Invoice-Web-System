package com.example.proyecto_programacioniv.logic;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

            document.add(new Paragraph(" "));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Paragraph fechaEmision = new Paragraph("FECHA: "+ dateFormat.format(factura.getFechEmision()), FontFactory.getFont(FontFactory.HELVETICA, 12));
            fechaEmision.setAlignment(Element.ALIGN_RIGHT);
            document.add(fechaEmision);

            Paragraph numFactura = new Paragraph( "FACTURA: "+factura.getNumFactura(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            numFactura.setAlignment(Element.ALIGN_RIGHT);
            document.add(numFactura);

            document.add(new Paragraph(" "));

            Paragraph proveedor = new Paragraph( "PROVEEDOR: ", FontFactory.getFont(FontFactory.HELVETICA, 12));
            proveedor.setAlignment(Element.ALIGN_LEFT);
            document.add(proveedor);

            Paragraph nomP = new Paragraph( "NOMBRE: "+factura.getProveedorByIdProveedor().getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            nomP.setAlignment(Element.ALIGN_LEFT);
            document.add(nomP);

            Paragraph idP = new Paragraph( "IDENTIFICACION: "+factura.getProveedorByIdProveedor().getId(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            idP.setAlignment(Element.ALIGN_LEFT);
            document.add(idP);

            Paragraph  telP = new Paragraph( "TELEFONO: "+factura.getProveedorByIdProveedor().getTelefono(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            telP.setAlignment(Element.ALIGN_LEFT);
            document.add(telP);

            Paragraph correoP = new Paragraph( "CORREO: "+factura.getProveedorByIdProveedor().getCorreo(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            correoP.setAlignment(Element.ALIGN_LEFT);
            document.add(correoP);

            Paragraph cliente = new Paragraph( "CLIENTE:", FontFactory.getFont(FontFactory.HELVETICA, 12));
            cliente.setAlignment(Element.ALIGN_RIGHT);
            document.add(cliente);

            Paragraph nomC = new Paragraph( "NOMBRE: "+factura.getClienteByIdCliente().getNombre(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            nomC.setAlignment(Element.ALIGN_RIGHT);
            document.add(nomC);

            Paragraph idC = new Paragraph( "IDENTIFICACION: "+factura.getClienteByIdCliente().getId(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            idC.setAlignment(Element.ALIGN_RIGHT);
            document.add(idC);

            Paragraph telC = new Paragraph( "TELEFONO: "+factura.getClienteByIdCliente().getTelefono(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            telC.setAlignment(Element.ALIGN_RIGHT);
            document.add(telC);

            Paragraph correoC = new Paragraph( "CORREO: "+factura.getClienteByIdCliente().getCorreo(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            correoC.setAlignment(Element.ALIGN_RIGHT);
            document.add(correoC);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph tituloTabla = new Paragraph("PRODUCTOS", FontFactory.getFont(FontFactory.HELVETICA, 12));
            tituloTabla.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloTabla);
            document.add(new Paragraph(" "));

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(0, 0, 0));

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
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph montoT = new Paragraph( "MONTO TOTAL: "+factura.getTotal(), FontFactory.getFont(FontFactory.HELVETICA, 12));
            montoT.setAlignment(Element.ALIGN_LEFT);
            document.add(montoT);
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}