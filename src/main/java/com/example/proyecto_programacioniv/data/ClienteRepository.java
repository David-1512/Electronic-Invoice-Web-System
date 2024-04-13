package com.example.proyecto_programacioniv.data;

import com.example.proyecto_programacioniv.logic.ClienteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface  ClienteRepository extends CrudRepository<ClienteEntity,String> {

}
