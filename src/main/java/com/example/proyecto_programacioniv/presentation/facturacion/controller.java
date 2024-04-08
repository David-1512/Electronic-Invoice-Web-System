package com.example.proyecto_programacioniv.presentation.facturacion;

import ch.qos.logback.core.model.Model;
import com.example.proyecto_programacioniv.logic.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller("facturas")
@RequestMapping("/presentation/facturacion")
public class controller {

    @Autowired
    private Service service;

    @Autowired
    private HttpSession httpSession;

    private Model model;
    private List<LineaServicioEntity> listLinea;

    controller(){
        model = new Model();
        listLinea = new ArrayList<>();
    }

    @GetMapping("/searchProveedor")
    public String searchProveedor() {
        Optional<ProveedorEntity> proveedorOptional = service.proveedorFindById("86952");
        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            httpSession.setAttribute("nombreProveedor", proveedor.getNombre());
        } else {
            httpSession.setAttribute("nombreProveedor", "Proveedor no encontrado");
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/searchCliente")
    public String searchCliente(@RequestParam("clienteId") String clienteId) {
        Optional<ClienteEntity> clienteOptional = service.clienteFindById(clienteId);
        if (clienteOptional.isPresent()) {
            ClienteEntity cliente = clienteOptional.get();
            httpSession.setAttribute("nombreCliente", cliente.getNombre());
        } else {
            httpSession.setAttribute("nombreCliente", "Cliente no encontrado");
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/searchProducto")
    public String searchProducto(@RequestParam("codProducto") String cod,
                                 @RequestParam("cantidad")Integer cantidad) {
        Optional<ProductoEntity> productoOptional = service.productoFindByCod(cod);
        if (productoOptional.isPresent()) {
            ProductoEntity producto = productoOptional.get();
            LineaServicioEntity lineaServicioEntity = new LineaServicioEntity();
            lineaServicioEntity.setCantidad(cantidad);
            lineaServicioEntity.setSubtotal(1500);
            lineaServicioEntity.setProductoByCodProducto(producto);
            listLinea.add(lineaServicioEntity);
            httpSession.setAttribute("lineasServicios", listLinea);
        }
        return "presentation/facturacion/View";
    }
}

