package com.example.proyecto_programacioniv.logic;
import com.example.proyecto_programacioniv.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private  HaciendaRepository haciendaRepository;

    public Iterable<ClienteEntity> clienteFindAll(){
        return clienteRepository.findAll();
    }

    public Optional<ProveedorEntity> proveedorFindById(String id){
        return proveedorRepository.findById(id);
    }
    public Optional<ProveedorEntity> proveedorFindByIdandContrasena(String id, String contrasena){
        return proveedorRepository.findByIdAndContrasena(id,contrasena);
    }

    public void agregarHacienda(String nif){
        HaciendaEntity hE = new HaciendaEntity(nif, "");
        haciendaRepository.save(hE);
    }
    public void agregarProveedor(ProveedorEntity p) throws Exception {
        proveedorRepository.save(p);
        p.getHaciendaByNif().getProveedorsByNif().add(p);
        haciendaRepository.save(p.getHaciendaByNif());
    }

    public HaciendaEntity findHaciendaByNIF(String Nif){
        return haciendaRepository.findByNif(Nif);
    }

    public Optional<ClienteEntity> clienteFindById(String id) {return clienteRepository.findById(id);}

    public Optional<ProductoEntity> productoFindByCod(String cod){return productoRepository.findById(cod);}

    public Iterable<ProveedorEntity> proveedorFindAll(){
        return proveedorRepository.findAll();
    }


    public void proveedorDelete(String id) {
        proveedorRepository.deleteById(id);
    }

    public Iterable<ProveedorEntity> proveedorRegistrados() {
        List<ProveedorEntity> list = (List<ProveedorEntity>) proveedorRepository.findAll();

        List<ProveedorEntity> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEstado() != 'E') {
                list2.add(list.get(i));
            }
        }
        return  list2;
    }

    public List<ProveedorEntity> proveedoresNuevos() {
        List<ProveedorEntity> list = (List<ProveedorEntity>) proveedorRepository.findAll();

        List<ProveedorEntity> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEstado() == 'E') {
                list2.add(list.get(i));
            }
        }
        return list2;
    }

    public void aceptarProveedor(String id) {
        ProveedorEntity proveedor = proveedorRepository.findById(id).get();
        proveedor.setEstado('A');
        proveedorRepository.save(proveedor);
    }

    public void desactivarProveedor(String id) {
        ProveedorEntity proveedor = proveedorRepository.findById(id).get();
        proveedor.setEstado('I');
        proveedorRepository.save(proveedor);
    }

    public List<ProveedorEntity> buscarProveedorPor(String searchTerm) {
            List<ProveedorEntity> provedores = (List<ProveedorEntity>) proveedorRegistrados();

            return provedores.stream()
                    .filter(prov -> (prov.getNombre().toLowerCase().contains(searchTerm.toLowerCase())
                            || prov.getId().toLowerCase().contains(searchTerm.toLowerCase())))
                    .collect(Collectors.toList());
    }

    public Object buscarProveedorNuevoPor(String searchTerm) {
        List<ProveedorEntity> provedores = proveedoresNuevos();

        return provedores.stream()
                .filter(prov -> (prov.getNombre().toLowerCase().contains(searchTerm.toLowerCase())
                        || prov.getId().toLowerCase().contains(searchTerm.toLowerCase())))
                .collect(Collectors.toList());
    }
}
