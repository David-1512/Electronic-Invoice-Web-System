package com.example.proyecto_programacioniv.data;

import com.example.proyecto_programacioniv.logic.HaciendaEntity;
import org.springframework.data.repository.CrudRepository;

public interface HaciendaRepository extends CrudRepository<HaciendaEntity,String> {
    HaciendaEntity findByNif(String Nif);
}
