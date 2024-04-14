package com.example.proyecto_programacioniv.presentation.facturacion;

import com.example.proyecto_programacioniv.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@org.springframework.stereotype.Controller("facturas")
@RequestMapping("/presentation/facturacion") //Aqui deberia entrar con el idProveedor
public class facturacionController {

    @Autowired
    private Service service;

    private facturacionModel modelfacturacion;

    public facturacionController(){
        modelfacturacion = new facturacionModel();
    }

    @GetMapping("/")
    public String show(Model model){
        Optional<ProveedorEntity> proveedorOptional = service.proveedorFindById("86952");
        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            model.addAttribute("nombreProveedor", proveedor.getNombre());
            model.addAttribute(modelfacturacion.getTotalFactura());
        } else {
            model.addAttribute("nombreProveedor", "Proveedor no encontrado");
        }
        return "/presentation/facturacion/View";
    }

    @GetMapping("/searchCliente")
    public String searchCliente(@RequestParam("clienteId") String clienteId,Model model,
                                @RequestParam("nomProveedorCliente")String nomProveedor) {
        Optional<ClienteEntity> clienteOptional = service.clienteFindById(clienteId);
        if (clienteOptional.isPresent()) {
            ClienteEntity cliente = clienteOptional.get();
            model.addAttribute("nombreCliente", cliente.getNombre());
            model.addAttribute("nombreProveedor", nomProveedor);
        } else {
            model.addAttribute("nombreCliente", "Cliente no encontrado");
            model.addAttribute("nombreProveedor", nomProveedor);
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/searchProducto")
    public String searchProducto(@RequestParam("codProducto") String cod,
                                 @RequestParam("cantidad")Integer cantidad,
                                 @RequestParam("nomProveedorProducto") String nomProveedor,
                                 @RequestParam("nomClienteProducto") String nomCliente,
                                 Model model) {
        Optional<ProductoEntity> productoOptional = service.productoFindByCod(cod);
        if (productoOptional.isPresent()) {
            ProductoEntity producto = productoOptional.get();
            LineaServicioEntity lineaServicioEntity = new LineaServicioEntity();
            lineaServicioEntity.setCantidad(cantidad);
            lineaServicioEntity.setProductoByCodProducto(producto);
            model.addAttribute("nombreCliente", nomCliente);
            model.addAttribute("nombreProveedor", nomProveedor);
            modelfacturacion.agregarLineaServicio(lineaServicioEntity);
            model.addAttribute("lineasServicios", modelfacturacion.findAll());
            model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/disCantidad/{codigoP}")
    public String disCantidad(Model model, @PathVariable int codigoP,
                              @RequestParam("nomProveedorLista") String nomProveedor,
                              @RequestParam("nomClienteLista") String nomCliente){
        modelfacturacion.disminuirCantidad(codigoP);
        model.addAttribute("nombreCliente", nomCliente);
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/aumCantidad/{codigoP}")
    public String aumCantidad(Model model, @PathVariable int codigoP,
                              @RequestParam("nomProveedorLista") String nomProveedor,
                              @RequestParam("nomClienteLista") String nomCliente){
        modelfacturacion.aumentarCantidad(codigoP);
        model.addAttribute("nombreCliente", nomCliente);
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/eliminarProducto/{codigoP}")
    public String eliminarProducto(Model model, @PathVariable int codigoP,
                              @RequestParam("nomProveedorLista") String nomProveedor,
                              @RequestParam("nomClienteLista") String nomCliente){
        modelfacturacion.eliminarProducto(codigoP);
        model.addAttribute("nombreCliente", nomCliente);
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/verClientes")
    public String verClientes(Model model,
                              @RequestParam("nomProveedorCliente")String nomProveedor){
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("clientes",service.clienteFindAll());
        return "presentation/facturacion/ViewClientes";
    }

    @GetMapping("/selectCliente/{idCliente}")
    public String addCliente(Model model,@PathVariable String idCliente,
                             @RequestParam("nomProveedorCliente")String nomProveedor){
        Optional<ClienteEntity> clienteOptional = service.clienteFindById(idCliente);
            ClienteEntity cliente = clienteOptional.get();
            model.addAttribute("nombreCliente", cliente.getNombre());
            model.addAttribute("nombreProveedor", nomProveedor);
            return "presentation/facturacion/View";
    }

    @GetMapping("/verProductos")
    public String verProductos(Model model,
                               @RequestParam("nomProveedorProducto") String nomProveedor,
                               @RequestParam("nomClienteProducto") String nomCliente){
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("nombreCliente", nomCliente);
        model.addAttribute("productos",service.productoFindAll());
        return "presentation/facturacion/ViewProductos";
    }

    @GetMapping("/selectProducto/{codProducto}")
    public String addProducto(Model model,@PathVariable String codProducto,
                             @RequestParam("nomProveedorCliente")String nomProveedor,
                              @RequestParam("nomClienteProducto") String nomCliente ){
        model.addAttribute("codigoProducto", codProducto);
        model.addAttribute("nombreProveedor", nomProveedor);
        model.addAttribute("nombreCliente", nomCliente);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }


}

