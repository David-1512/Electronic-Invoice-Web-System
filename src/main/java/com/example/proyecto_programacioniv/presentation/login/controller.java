package com.example.proyecto_programacioniv.presentation.login;

import com.example.proyecto_programacioniv.logic.HaciendaEntity;
import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import com.example.proyecto_programacioniv.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@org.springframework.stereotype.Controller("login")
public class controller {
    @Autowired
    private Service service;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/login")
    public String show  (Model model){
        ProveedorEntity proveedor = new ProveedorEntity();
        model.addAttribute("proveedor", proveedor);
        return "/presentation/login/View";
    }

    @PostMapping("/presentation/login")
    public String validate(@ModelAttribute ProveedorEntity proveedor, Model model){
        Optional<ProveedorEntity> p = service.proveedorFindByIdandContrasena(proveedor.getId(), proveedor.getContrasena());
        if (p.isPresent()) {
            if (p.get().getEstado() == 'A') {
                httpSession.setAttribute("idProveedor", p.get().getId());
                return "redirect:/";
            } else if (p.get().getEstado() == 'D') {
                //redireccionar a pagina de completar datos
                return "presentation/login/View";
            }
        }
        if (p.isEmpty()) {
            model.addAttribute("error", "Credenciales incorrectas");
            model.addAttribute("proveedor", proveedor);
            return "presentation/login/View";
        }

        return "redirect:/";
    }


    @GetMapping("/registro")
    public String showRegistro  (Model model){
        ProveedorEntity pE = new ProveedorEntity();
        model.addAttribute("prov", pE);
        return "/presentation/login/ViewRegistro";
    }
    @PostMapping("/presentation/login/registro")
    public String register(@ModelAttribute ProveedorEntity prov, Model model, @RequestParam ("nif")String nif){
        try {
            ProveedorEntity proveedor = new ProveedorEntity();
            model.addAttribute("proveedor", proveedor);
            ProveedorEntity pE = new ProveedorEntity();
            pE.setId(prov.getId());
            pE.setContrasena(prov.getContrasena());
            pE.setCorreo(prov.getCorreo());
            pE.setTelefono("");
            pE.setEstado('E');
            pE.setNombre("");
            service.agregarHacienda(nif);
            HaciendaEntity hE = service.findHaciendaByNIF(nif);
            pE.setHaciendaByNif(hE);
            service.agregarProveedor(pE);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "presentation/login/View";
    }




}


//        if (p.isEmpty()){
//            //model.addAttribute("error", "Credenciales inválidas, por favor intente de nuevo");
//            return "/presentation/login/View";
//        }
//        return "redirect:/";