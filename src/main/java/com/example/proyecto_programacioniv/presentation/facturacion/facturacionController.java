package com.example.proyecto_programacioniv.presentation.facturacion;

import com.example.proyecto_programacioniv.logic.ClienteEntity;
import com.example.proyecto_programacioniv.logic.ProductoEntity;
import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import com.example.proyecto_programacioniv.logic.Service;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerException;
import java.io.IOException;
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
        ProveedorEntity proveedor = service.returnProveedor("86952");
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("idProveedor",proveedor.getId());
        model.addAttribute("totalFactura", modelfacturacion.getTotalFactura());
        modelfacturacion.limpiarLista();
        return "/presentation/facturacion/View";
    }

    @GetMapping("/searchCliente")
    public String searchCliente(@RequestParam("clienteId") String clienteId,Model model,
                                @RequestParam("idProveedorCliente") String idProveedor) {
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        Optional<ClienteEntity> clienteOptional = service.clienteFindById(clienteId);
        if (clienteOptional.isPresent()) {
            ClienteEntity cliente = clienteOptional.get();
            if(service.verificarCliente(clienteId,idProveedor)) {
                model.addAttribute("nombreCliente", cliente.getNombre());
                model.addAttribute("idCliente",cliente.getId());
            }
            model.addAttribute("nombreProveedor", proveedor.getNombre());
            model.addAttribute("idProveedor",idProveedor);
            model.addAttribute("totalFactura", modelfacturacion.getTotalFactura());
            modelfacturacion.limpiarLista();
        } else {
            model.addAttribute("nombreCliente", "Cliente no encontrado");
            model.addAttribute("nombreProveedor", proveedor.getNombre());
            model.addAttribute("idProveedor",idProveedor);
            model.addAttribute("totalFactura", modelfacturacion.getTotalFactura());
            modelfacturacion.limpiarLista();
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/estadoActual")
    public String estadoActual(@RequestParam("idProveedorProducto") String idProveedor,
                               @RequestParam("idClienteProducto") String idCliente,
                               Model model){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("idCliente",idCliente);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());

        return "presentation/facturacion/View";
    }


    @GetMapping("/verClientes")
    public String verClientes(Model model,
                              @RequestParam("idProveedorCliente") String idProveedor){
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("clientes",service.getClientesPorProveedor(idProveedor));
        return "presentation/facturacion/ViewClientes";
    }

    @GetMapping("/selectCliente/{idCliente}")
    public String addCliente(Model model,@PathVariable String idCliente,
                             @RequestParam("idProveedorCliente") String idProveedor){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("idCliente",cliente.getId());
        model.addAttribute("totalFactura", modelfacturacion.getTotalFactura());
        modelfacturacion.limpiarLista();

        return "presentation/facturacion/View";
    }

    @GetMapping("/searchProducto")
    public String searchProducto(@RequestParam("codProducto") String cod,
                                 @RequestParam("idProveedorProducto") String idProveedor,
                                 @RequestParam("idClienteProducto") String idCliente,
                                 Model model) {
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        Optional<ProductoEntity> productoOptional = service.productoFindByCod(cod);
        if (productoOptional.isPresent()) {
            ProductoEntity producto = productoOptional.get();
            model.addAttribute("cod",cod);
            model.addAttribute("detalleProducto",producto.getNombre());
            model.addAttribute("nombreCliente", cliente.getNombre());
            model.addAttribute("nombreProveedor",proveedor.getNombre());
            model.addAttribute("idProveedor",idProveedor);
            model.addAttribute("idCliente",idCliente);
            model.addAttribute("lineasServicios", modelfacturacion.findAll());
            model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        }
        else{
            model.addAttribute("detalleProducto", "Producto no encontrado");
            model.addAttribute("nombreCliente", cliente.getNombre());
            model.addAttribute("nombreProveedor",proveedor.getNombre());
            model.addAttribute("idProveedor",idProveedor);
            model.addAttribute("idCliente",idCliente);
            model.addAttribute("lineasServicios", modelfacturacion.findAll());
            model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        }
        return "presentation/facturacion/View";
    }

    @GetMapping("/addProducto")
    public String addProducto(@RequestParam("cod") String cod,
                              @RequestParam("cantidad")Integer cantidad,
                              @RequestParam("idProveedorProducto") String idProveedor,
                              @RequestParam("idClienteProducto") String idCliente,
                              Model model){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        ProductoEntity producto = service.returnProducto(cod);
        if(idCliente != null && cantidad != null && cod != null
        && service.verificarProducto(cod,idProveedor)) {
            modelfacturacion.agregarLineaServicio(producto,cantidad);
        }
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("idCliente",idCliente);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/verProductos")
    public String verProductos(Model model,
                               @RequestParam("idProveedorProducto") String idProveedor,
                               @RequestParam("idClienteProducto") String idCliente){
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("idCliente", idCliente);
        model.addAttribute("productos",service.getProductosPorProveedor(idProveedor));
        return "presentation/facturacion/ViewProductos";
    }

    @GetMapping("/selectProducto/{codProducto}")
    public String addProducto(Model model,@PathVariable String codProducto,
                              @RequestParam("idProveedorProducto")String idProveedor,
                              @RequestParam("idClienteProducto") String idCliente){

        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        ProductoEntity producto = service.returnProducto(codProducto);
        model.addAttribute("cod",codProducto);
        model.addAttribute("detalleProducto",producto.getNombre());
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("idProveedor",idProveedor);
        model.addAttribute("idCliente",idCliente);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/disCantidad/{codigoP}")
    public String disCantidad(Model model, @PathVariable int codigoP,
                              @RequestParam("idProveedorLista") String idProveedor,
                              @RequestParam("idClienteLista") String idCliente){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        modelfacturacion.disminuirCantidad(codigoP);
        model.addAttribute("idCliente", idCliente);
        model.addAttribute("idProveedor", idProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/aumCantidad/{codigoP}")
    public String aumCantidad(Model model, @PathVariable int codigoP,
                              @RequestParam("idProveedorLista") String idProveedor,
                              @RequestParam("idClienteLista") String idCliente){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        modelfacturacion.aumentarCantidad(codigoP);
        model.addAttribute("idCliente", idCliente);
        model.addAttribute("idProveedor", idProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @GetMapping("/eliminarProducto/{codigoP}")
    public String eliminarProducto(Model model, @PathVariable int codigoP,
                              @RequestParam("idProveedorLista") String idProveedor,
                              @RequestParam("idClienteLista") String idCliente){
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        ClienteEntity cliente = service.returnCliente(idCliente);
        model.addAttribute("nombreCliente", cliente.getNombre());
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        modelfacturacion.eliminarProducto(codigoP);
        model.addAttribute("idCliente", idCliente);
        model.addAttribute("idProveedor", idProveedor);
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/View";
    }

    @PostMapping("/guardarFactura")
    public String guardarFactura( @RequestParam("idProveedorGuardar") String idProveedor,
                                  @RequestParam("idClienteGuardar") String idCliente,
                                  Model model){
        if(idCliente != null && !modelfacturacion.findAll().isEmpty()) {
            service.facturaSave(idProveedor, idCliente, modelfacturacion.getTotalFactura(), modelfacturacion.findAll());
            return "presentation/facturacion/ViewGuardado";
        }
        ProveedorEntity proveedor = service.returnProveedor(idProveedor);
        if(idCliente != null){
            ClienteEntity cliente = service.returnCliente(idCliente);
            model.addAttribute("nombreCliente", cliente.getNombre());
            model.addAttribute("idCliente", idCliente);
        }
        model.addAttribute("nombreProveedor",proveedor.getNombre());
        model.addAttribute("lineasServicios", modelfacturacion.findAll());
        model.addAttribute("totalFactura",modelfacturacion.getTotalFactura());
        return "presentation/facturacion/ViewFacturas";
    }

    @GetMapping("/verFacturas")
    public String verFacturas(Model model){
        model.addAttribute("facturas",service.facturaFindAll());
        return "presentation/facturacion/ViewFacturas";
    }

    @GetMapping("/generarPDF/{numFactura}")
    public String generarPDF(Model model,
                             @PathVariable String numFactura) throws DocumentException, IOException {
        modelfacturacion.generarPDF(service.returnFactura(numFactura));
        model.addAttribute("facturas",service.facturaFindAll());
        model.addAttribute("mensaje","PDF generado correctamente");
        return "presentation/facturacion/ViewFacturas";
    }

    @GetMapping("/generarXML/{numFactura}")
    public String generarXML(Model model,
                             @PathVariable String numFactura) throws TransformerException {
        modelfacturacion.generarXML(service.returnFactura(numFactura));
        model.addAttribute("facturas",service.facturaFindAll());
        model.addAttribute("mensaje","XML generado correctamente");
        return "presentation/facturacion/ViewFacturas";
    }
}

