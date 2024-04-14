package com.example.proyecto_programacioniv.presentation.proveedores;

import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("proveedor")
public class ProveedorController {
    @Autowired
    private com.example.proyecto_programacioniv.logic.Service service;

    @PostMapping("/proveedor/registro-datos")
    public String procesarFormulario(@RequestParam("id") String id,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("correo") String correo,
                                     @RequestParam("telefono") String telefono,
                                     @RequestParam("nif") String nif,
                                     @RequestParam("actividad") String actividad,
                                     Model model) {
        try{
            service.proveedorUpdate(id, nombre, correo, telefono, nif, actividad);
            model.addAttribute("proveedor", service.proveedorFindById(id).get());
            return "/presentation/home/home";
        }catch (Exception e){
            model.addAttribute("proveedor", service.proveedorFindById(id).get());
            model.addAttribute("error", e.getMessage());
            return "/presentation/proveedores/ViewDatos";
        }

    }

}
