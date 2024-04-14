package com.example.proyecto_programacioniv.presentation.facturacion;

import com.example.proyecto_programacioniv.logic.LineaServicioEntity;

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

    public void agregarLineaServicio(LineaServicioEntity linea){
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

    private double subtotal(LineaServicioEntity linea){  //Cambiar a double
        double subtotal = linea.getCantidad() * linea.getProductoByCodProducto().getPrecio();
        return subtotal;
    }

    public double getTotalFactura(){return totalFactura;}
}
