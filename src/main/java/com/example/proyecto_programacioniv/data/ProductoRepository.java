package com.example.proyecto_programacioniv.data;

import com.example.proyecto_programacioniv.logic.ProductoEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<ProductoEntity,String> {
}
