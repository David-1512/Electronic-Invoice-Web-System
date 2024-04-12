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

    public void agregarProducto(String cod, String nom, int precio, String proveedorID) {
        if (productoRepository.findById(cod).isPresent()) {
            ProductoEntity producto = productoRepository.findById(cod).get();
            producto.setPrecio(precio);
            producto.setNombre(nom);
            productoRepository.save(producto);
        }
        else{
            ProveedorEntity prov= proveedorRepository.findById(proveedorID).get();
            ProductoEntity pE = new ProductoEntity(cod,nom,precio,prov);

            productoRepository.save(pE);
        }
    }
}
