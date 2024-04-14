package com.example.proyecto_programacioniv.logic;

import com.example.proyecto_programacioniv.data.ClienteRepository;
import com.example.proyecto_programacioniv.data.FacturaRepository;
import com.example.proyecto_programacioniv.data.ProductoRepository;
import com.example.proyecto_programacioniv.data.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FacturaRepository facturaRepository;

    public Iterable<ClienteEntity> clienteFindAll(){
        return clienteRepository.findAll();
    }

    public Iterable<ProductoEntity> productoFindAll(){
        return productoRepository.findAll();
    }
    public Optional<ProveedorEntity> proveedorFindById(String id){
        return proveedorRepository.findById(id);
    }

    public Optional<ClienteEntity> clienteFindById(String id) {return clienteRepository.findById(id);}

    public Optional<ProductoEntity> productoFindByCod(String cod){return productoRepository.findById(cod);}

    private ClienteEntity returnCliente(String id){return clienteRepository.findById(id).orElse(null);}

    private ProveedorEntity returnProveedor(String id){return proveedorRepository.findById(id).orElse(null);}
    public void facturaSave(String idProveedor, String idCliente, Collection<LineaServicioEntity> lineas){
       FacturasEntity factura = new FacturasEntity();
        factura.setClienteByIdCliente(returnCliente(idCliente));
        factura.setProveedorByIdProveedor(returnProveedor(idProveedor));
        factura.setLineaServiciosByNumFactura(lineas);
        facturaRepository.save(factura);
    }
}
