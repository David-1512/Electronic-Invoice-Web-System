package com.example.proyecto_programacioniv.data;
import com.example.proyecto_programacioniv.logic.AdministradorEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdministradorRepository {
    List<AdministradorEntity> listAdministrado;

    public AdministradorRepository() {
        this.listAdministrado = new ArrayList<>();
        AdministradorEntity admi1=new AdministradorEntity("111","Tatiana","111");
        AdministradorEntity admi2=new AdministradorEntity("222","Juan","111");
        listAdministrado.add(admi1);
        listAdministrado.add(admi2);
    }

    public List<AdministradorEntity> getListAdministrado() {
        return listAdministrado;
    }

    public void setListAdministrado(List<AdministradorEntity> listAdministrado) {
        this.listAdministrado = listAdministrado;
    }
}
