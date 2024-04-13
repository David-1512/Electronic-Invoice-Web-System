package com.example.proyecto_programacioniv.presentation.clientes;

import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import com.example.proyecto_programacioniv.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@org.springframework.stereotype.Controller("clientes")
public class ClienteController {

    @Autowired
    private Service service;

    @GetMapping("/clientes")
    public String show (@RequestParam("idProv") String proveedorID,
                        Model model){
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("clientes",service.mostrarClientesDeProveedor(proveedorID));
        return "/presentation/clientes/View";
    }
    @PostMapping("/clientes/buscar")
    public String buscarCliente(@RequestParam("idProv")String proveedorID,
                                @RequestParam("busq") String busqueda,
                                Model model){
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("clientes",service.buscarClientesProveedor(proveedorID,busqueda));
        return "/presentation/clientes/View";
    }

    @GetMapping("/clientes/seleccionar")
    public String seleccionarCliente(@RequestParam("idClien") String clienteID,
                                     @RequestParam("idProv") String proveedorID,
                                     Model model){
        model.addAttribute("cliente", service.seleccionarCliente(proveedorID,clienteID));
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("clientes",service.mostrarClientesDeProveedor(proveedorID));
        return "/presentation/clientes/View";
    }

    @GetMapping("/clientes/eliminarCliente")
    public String eliminarCliente(@RequestParam("idClien") String clienteID,
                                  @RequestParam("idProv") String proveedorID,
                                  Model model){
        service.eliminarCliente(clienteID,proveedorID);
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("clientes",service.mostrarClientesDeProveedor(proveedorID));
        return "/presentation/clientes/View";
    }

    @PostMapping("/clientes/agregar")
    public String agregarClientes(@RequestParam("identificacion") String clienteID,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("correo") String correo,
                                  @RequestParam("telefono") String telefono,
                                  @RequestParam("esCliente") boolean esCliente,
                                  @RequestParam("idProv") String proveedorID,
                                  Model model){
        try {
            service.agregarCliente(clienteID, nombre, correo, telefono, esCliente, proveedorID);
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
        }
        //model.addAttribute("error",error);
        model.addAttribute("proveedor", service.proveedorFindById(proveedorID));
        model.addAttribute("clientes",service.mostrarClientesDeProveedor(proveedorID));
        return "/presentation/clientes/View";

    }
}
