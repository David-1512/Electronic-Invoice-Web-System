package com.example.proyecto_programacioniv.logic;
import com.example.proyecto_programacioniv.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    public Iterable<ClienteEntity> clienteFindAll() { return clienteRepository.findAll(); }

    public Optional<ProveedorEntity> proveedorFindById(String id){
        return proveedorRepository.findById(id);
    }

    public Optional<ClienteEntity> clienteFindById(String id) {return clienteRepository.findById(id);}

    public Optional<ProductoEntity> productoFindByCod(String cod){return productoRepository.findById(cod);}


    //---------------------------------------- PRODUCTOS ---------------------------------------------
    public List<ProductoEntity> mostrarProductosProveedor(String proveedorID) {
        return (List<ProductoEntity>) proveedorRepository.findById(proveedorID).get().getProductosById();
    }

    public void eliminarProducto(String proveedorID, String productoID) {
        productoRepository.deleteById(productoID);
    }


    public List<ProductoEntity> buscadorProductos(String proveedorID, String searchTerm) {
        Optional<ProveedorEntity> proveedorOptional = proveedorRepository.findById(proveedorID);
        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            List<ProductoEntity> productos = new ArrayList<>(proveedor.getProductosById());

            return productos.stream()
                    .filter(producto -> producto.getNombre().toLowerCase().contains(searchTerm.toLowerCase())
                            || producto.getCod().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public ProductoEntity buscarProductoPorCodigo(String proveedorID, String productoID) {
        Optional<ProveedorEntity> proveedorOptional = proveedorRepository.findById(proveedorID);
        if (proveedorOptional.isPresent()) {
            ProveedorEntity proveedor = proveedorOptional.get();
            return proveedor.getProductosById().stream()
                    .filter(producto -> producto.getCod().equalsIgnoreCase(productoID))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public void agregarProducto(String cod, String nom, double precio, String proveedorID, boolean esProducto) {
        if (productoRepository.findById(cod).isPresent() ) {
            if(esProducto) {
                ProductoEntity producto = productoRepository.findById(cod).get();
                producto.setPrecio(precio);
                producto.setNombre(nom);
                productoRepository.save(producto);
            }
            else return;// tal vez poner un mensajito que ya el codigo está siendo utilizado
        }
        else{
            ProveedorEntity prov= proveedorRepository.findById(proveedorID).get();
            ProductoEntity pE = new ProductoEntity(cod,nom,precio,prov);
            productoRepository.save(pE);
        }
    }
    //-----------------------------------------------------------------------------------------------------------
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
