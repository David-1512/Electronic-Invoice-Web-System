package com.example.proyecto_programacioniv.data;
import com.example.proyecto_programacioniv.logic.ProductoEntity;
import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProveedorRepository extends CrudRepository<ProveedorEntity,String> {//nombre, y el tipo que es la PK
}
