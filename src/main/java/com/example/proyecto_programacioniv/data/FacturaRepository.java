package com.example.proyecto_programacioniv.data;

import com.example.proyecto_programacioniv.logic.FacturasEntity;
import org.springframework.data.repository.CrudRepository;

public interface FacturaRepository extends CrudRepository<FacturasEntity,String> {
}