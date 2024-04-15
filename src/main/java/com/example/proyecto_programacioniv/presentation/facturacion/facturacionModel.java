package com.example.proyecto_programacioniv.presentation.facturacion;

import com.example.proyecto_programacioniv.logic.*;
import com.itextpdf.text.DocumentException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class facturacionModel {

    private List<LineaServicioEntity> listLinea;

    private int cont;

    private double totalFactura;

    public facturacionModel(){
        listLinea = new ArrayList<>();
        cont = 1;
        totalFactura = 0.0;
    }

    public void agregarLineaServicio(ProductoEntity producto, int cant){
        LineaServicioEntity linea = new LineaServicioEntity();
        linea.setCantidad(cant);
        linea.setProductoByCodProducto(producto);
        linea.setCod(cont);
        linea.setSubtotal(subtotal(linea));
        totalFactura += subtotal(linea);
        listLinea.add(linea);
        cont++;
    }

    public List<LineaServicioEntity> findAll(){return listLinea;}

    public void disminuirCantidad(int cod){
        LineaServicioEntity result = listLinea.stream()
                .filter(i->i.getCod() == cod).findFirst().orElse(null);
        if(result.getCantidad() != 1) {
            result.setCantidad(result.getCantidad() - 1);
            result.setSubtotal(subtotal(result));
            totalFactura -= result.getProductoByCodProducto().getPrecio();
        }
    }

    public void aumentarCantidad(int cod){
        LineaServicioEntity result = listLinea.stream()
                .filter(i->i.getCod() == cod).findFirst().orElse(null);
        result.setCantidad(result.getCantidad() + 1);
        result.setSubtotal(subtotal(result));
        totalFactura += result.getProductoByCodProducto().getPrecio();
    }

    public void eliminarProducto(int cod){
        LineaServicioEntity result = listLinea.stream()
                .filter(i->i.getCod() == cod).findFirst().orElse(null);
        listLinea.remove(result);
        totalFactura -= subtotal(result);
        cont--;

    }

    private double subtotal(LineaServicioEntity linea){
        double subtotal = linea.getCantidad() * linea.getProductoByCodProducto().getPrecio();
        return subtotal;
    }

    public double getTotalFactura(){return totalFactura;}

    public void limpiarLista(){
        listLinea.clear();
        cont = 1;
        totalFactura = 0.0;
    }

    public void generarPDF(FacturasEntity factura) throws DocumentException, IOException {
        PDF pdf = new PDF();
        pdf.createPDF(factura);
    }

    public void generarXML(FacturasEntity factura) throws TransformerException {
        XML xml = new XML();
        xml.crearXML(factura);

    }
}
