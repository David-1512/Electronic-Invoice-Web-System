package com.example.proyecto_programacioniv.logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;

public class XML {
    public void crearXML(FacturasEntity factura) throws TransformerConfigurationException, TransformerException{
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Node facturaNode;
            facturaNode = doc.createElement("FacturaElectronica");
            doc.appendChild(facturaNode);

            Element numFactura = doc.createElement("Clave");
            numFactura.appendChild(doc.createTextNode(factura.getNumFactura()));
            facturaNode.appendChild(numFactura);

            Element actComercialFactura = doc.createElement("ActividadComercial");
            actComercialFactura.appendChild(doc.createTextNode(factura.getProveedorByIdProveedor().getHaciendaByNif().getActEconomica()));
            facturaNode.appendChild(actComercialFactura);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaEmision = dateFormat.format(factura.getFechEmision());
            Element fechFactura = doc.createElement("FechaEmision");
            fechFactura.appendChild(doc.createTextNode(fechaEmision));
            facturaNode.appendChild(fechFactura);

            Element proveedor = doc.createElement("Emisor");

            Element nomElement = doc.createElement("Nombre");
            nomElement.appendChild(doc.createTextNode(factura.getProveedorByIdProveedor().getNombre()));
            proveedor.appendChild(nomElement);

            Element idElement = doc.createElement("Identificacion");
            idElement.appendChild(doc.createTextNode(factura.getProveedorByIdProveedor().getId()));
            proveedor.appendChild(idElement);

            Element telElement = doc.createElement("Telefono");
            telElement.appendChild(doc.createTextNode(factura.getProveedorByIdProveedor().getTelefono()));
            proveedor.appendChild(telElement);

            Element correoElement = doc.createElement("CorreoElectronico");
            correoElement.appendChild(doc.createTextNode(factura.getProveedorByIdProveedor().getCorreo()));
            proveedor.appendChild(correoElement);

            facturaNode.appendChild(proveedor);

            Element cliente = doc.createElement("Receptor");

            Element nomClienteElement = doc.createElement("Nombre");
            nomClienteElement.appendChild(doc.createTextNode(factura.getClienteByIdCliente().getNombre()));
            cliente.appendChild(nomClienteElement);

            Element idClienteElement = doc.createElement("Identificacion");
            idClienteElement.appendChild(doc.createTextNode(factura.getClienteByIdCliente().getId()));
            cliente.appendChild(idClienteElement);

            Element telClienteElement = doc.createElement("Telefono");
            telClienteElement.appendChild(doc.createTextNode(factura.getClienteByIdCliente().getTelefono()));
            cliente.appendChild(telClienteElement);

            Element correoClienteElement = doc.createElement("CorreoElectronico");
            correoClienteElement.appendChild(doc.createTextNode(factura.getClienteByIdCliente().getCorreo()));
            cliente.appendChild(correoClienteElement);

            facturaNode.appendChild(cliente);

            Element detalleServicio = doc.createElement("DetalleServicio");

            for (LineaServicioEntity lineaServicio : factura.getLineaServiciosByNumFactura()) {
                Element lineaServicioElement = doc.createElement("LineaDetalle");

                Element numLineaElement = doc.createElement("NumeroLinea");
                numLineaElement.appendChild(doc.createTextNode(String.valueOf(lineaServicio.getCod())));
                lineaServicioElement.appendChild(numLineaElement);

                Element cantElement = doc.createElement("Cantidad");
                cantElement.appendChild(doc.createTextNode(String.valueOf(lineaServicio.getCantidad())));
                lineaServicioElement.appendChild(cantElement);

                Element detalleElement = doc.createElement("Detalle");
                detalleElement.appendChild(doc.createTextNode(lineaServicio.getProductoByCodProducto().getNombre()));
                cliente.appendChild(detalleElement);

                Element preElement = doc.createElement("PrecioUnitario");
                preElement.appendChild(doc.createTextNode(String.valueOf(lineaServicio.getProductoByCodProducto().getPrecio())));
                lineaServicioElement.appendChild(preElement);

                Element totalLineaElement = doc.createElement("MontoTotalLinea");
                totalLineaElement.appendChild(doc.createTextNode(String.valueOf(lineaServicio.getSubtotal())));
                lineaServicioElement.appendChild(totalLineaElement);

                detalleServicio.appendChild(lineaServicioElement);
            }

           Element totalFacturaElement = doc.createElement("MontoTotalLinea");
            totalFacturaElement.appendChild(doc.createTextNode(String.valueOf(factura.getTotal())));
            detalleServicio.appendChild(totalFacturaElement);

            facturaNode.appendChild(detalleServicio);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(factura.getClienteByIdCliente().getId()+"_"+
                    factura.getNumFactura()+".xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("Documento XML Request creado correctamente");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
