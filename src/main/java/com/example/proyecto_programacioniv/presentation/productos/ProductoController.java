package com.example.proyecto_programacioniv.presentation.productos;

import com.example.proyecto_programacioniv.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/producto")
public class ProductoController {

    @Autowired
    private Service service;

    @GetMapping("/productos")
    public String mostrarProductos(@RequestParam("idProv") String proveedorID, Model model){
        model.addAttribute("listaProductos",service.mostrarProductosProveedor(proveedorID));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        return "presentation/productos/producto";
    }
    @GetMapping("/productos/eliminarProducto")
    public String eliminarProducto(@RequestParam("idProd") String productoID,@RequestParam("idProv") String proveedorID, Model model){
        service.eliminarProducto(proveedorID, productoID);

        model.addAttribute("listaProductos",service.mostrarProductosProveedor(proveedorID));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        return "presentation/productos/producto";
    }

    @PostMapping("/productos/buscar")
    public String buscarProducto(@RequestParam("prod") String producto, @RequestParam("idProv") String proveedorID,Model model){
        model.addAttribute("listaProductos",service.buscadorProductos(proveedorID, producto));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        return "presentation/productos/producto";
    }

    @GetMapping("/productos/seleccionar")
    public String seleccionarProducto(@RequestParam("idProd") String productoID, @RequestParam("idProv") String proveedorID, Model model){
        model.addAttribute("listaProductos",service.mostrarProductosProveedor(proveedorID));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("producto", service.buscarProductoPorCodigo(proveedorID,productoID));

        return "presentation/productos/producto";
    }

    @PostMapping("/productos/agregar")
    public String agregarProducto(@RequestParam("codigo") String cod,
                                  @RequestParam("nombre") String nom,
                                  @RequestParam("precio") double precio,
                                  @RequestParam("idProv") String proveedorID,
                                  @RequestParam("esProducto") boolean esProducto,
                                  Model model){
        try {
            service.agregarProducto(cod, nom, precio, proveedorID, esProducto);
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
        }
        model.addAttribute("listaProductos",service.mostrarProductosProveedor(proveedorID));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));

        return "presentation/productos/producto";
    }
}
