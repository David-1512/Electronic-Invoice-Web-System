package com.example.proyecto_programacioniv.data;
import com.example.proyecto_programacioniv.logic.ProductoEntity;
import com.example.proyecto_programacioniv.logic.ProveedorEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProveedorRepository extends CrudRepository<ProveedorEntity,String> {

}
