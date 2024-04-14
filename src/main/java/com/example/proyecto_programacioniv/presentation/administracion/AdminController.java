package com.example.proyecto_programacioniv.presentation.administracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("admin")
public class AdminController {
    @Autowired
    private com.example.proyecto_programacioniv.logic.Service service;

    @GetMapping("/administracion")
    public String show(Model model){
        model.addAttribute("provRegistrados",service.proveedorRegistrados());
        return "presentation/administracion/ViewAdmin";
    }

    @GetMapping("/administracion/proveedor")
    public String showProveedor(@RequestParam("id") String id, Model model){
        model.addAttribute("proveedor",service.proveedorFindById(id).get());
        return "presentation/administracion/ViewProvData";
    }

    @GetMapping("/administracion/solicitudes")
    public String showSolicitudes(Model model){
        model.addAttribute("provNuevos",service.proveedoresNuevos());
        return "presentation/administracion/ViewSolicitudes";
    }


    @GetMapping("/administracion/solicitudes/validacion")
    public String validarProveedor(@RequestParam("id") String id,@RequestParam("accion") String accion, Model model){
        if(accion.equals("denegar")){
            service.proveedorDelete(id);
        }
        else if(accion.equals("aceptar")){
            service.aceptarProveedor(id);
        }
        model.addAttribute("proveedores",service.proveedorFindAll());

        return "presentation/administracion/ViewAdmin";
    }

    @GetMapping("/administracion/manejo-prov")
    public String manejarProveedor(@RequestParam("id") String id,@RequestParam("accion") String accion, Model model){
        if(accion.equals("desactivar")){
            service.desactivarProveedor(id);
        }
        else if(accion.equals("activar")){
            service.activarProveedor(id);
        }
        model.addAttribute("proveedor",service.proveedorFindById(id).get());

        return "presentation/administracion/ViewProvData";
    }

    @PostMapping("/administracion/buscar")
    public String buscar(@RequestParam("prov") String searchTerm, Model model){

        model.addAttribute("provRegistrados", service.buscarProveedorPor(searchTerm));

        return "presentation/administracion/ViewAdmin";
    }

    @PostMapping("/administracion/solicitudes/buscar")
    public String buscarSolic(@RequestParam("prov") String searchTerm, Model model){

        model.addAttribute("provNuevos", service.buscarProveedorNuevoPor(searchTerm));

        return "presentation/administracion/ViewSolicitudes";
    }

}
