package com.example.proyecto_programacioniv.presentation.proveedores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("proveedor")
public class ProveedorController {



    @PostMapping("/proveedor/registro-datos")
    public String datos(){
        return "/presentation/proveedores/ViewDatos";
    }

}
