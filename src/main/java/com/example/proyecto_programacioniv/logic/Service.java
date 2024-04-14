package com.example.proyecto_programacioniv.logic;

import com.example.proyecto_programacioniv.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    @Autowired
    private LineaServicioRepository lineaServicioRepository;

    public Iterable<ClienteEntity> clienteFindAll(){
        return clienteRepository.findAll();
    }

    public Iterable<ProductoEntity> productoFindAll(){
        return productoRepository.findAll();
    }

    public Iterable<FacturasEntity> facturaFindAll(){return facturaRepository.findAll();}
    public Optional<ProveedorEntity> proveedorFindById(String id){
        return proveedorRepository.findById(id);
    }

    public Optional<ClienteEntity> clienteFindById(String id) {return clienteRepository.findById(id);}

    public Optional<ProductoEntity> productoFindByCod(String cod){return productoRepository.findById(cod);}

    public FacturasEntity  returnFactura(String num){return facturaRepository.findById(num).orElse(null);}
    public ClienteEntity returnCliente(String id){return clienteRepository.findById(id).orElse(null);}

    public ProveedorEntity returnProveedor(String id){return proveedorRepository.findById(id).orElse(null);}

    public ProductoEntity returnProducto(String cod){return productoRepository.findById(cod).orElse(null);}


    public void facturaSave(String idProveedor, String idCliente, double total, Collection<LineaServicioEntity> lineas){
       FacturasEntity factura = new FacturasEntity();
        String numeroFactura = String.format("%010d", facturaRepository.count()+1);
        factura.setNumFactura(numeroFactura);
        Date fechaActual = new Date(System.currentTimeMillis());
        Date fecha = new Date(fechaActual.getTime());
        factura.setFechEmision(fecha);
        factura.setTotal(total);
        factura.setClienteByIdCliente(returnCliente(idCliente));
        factura.setProveedorByIdProveedor(returnProveedor(idProveedor));
        facturaRepository.save(factura);
         for (LineaServicioEntity lineaServicio : lineas){
            lineaServicio.setFacturasByNumFactura(factura);
            lineaServicioRepository.save(lineaServicio);
        }
    }

    public boolean verificarProducto(String cod, String idProveedor) {
        ProductoEntity producto = productoRepository.findById(cod).orElse(null);
        if (producto.getProveedorByIdProveedor().getId().equals(idProveedor)) {
            return true;
        }
        return false;
    }

    public boolean verificarCliente(String id, String idProveedor) {
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        if (cliente.getProveedorByIdProveedor().getId().equals(idProveedor)) {
            return true;
        }
        return false;
    }


    public List<ProductoEntity> getProductosPorProveedor(String idProveedor) {
        List<ProductoEntity> productosConProveedor = new ArrayList<>();
        for (ProductoEntity producto : productoRepository.findAll()) {
            if (producto.getProveedorByIdProveedor().getId().equals(idProveedor)) {
                productosConProveedor.add(producto);
            }
        }
        return productosConProveedor;
    }

    public List<ClienteEntity> getClientesPorProveedor(String idProveedor) {
        List<ClienteEntity> clientesConProveedor = new ArrayList<>();
        for (ClienteEntity cliente : clienteRepository.findAll()) {
            if (cliente.getProveedorByIdProveedor().getId().equals(idProveedor)) {
                clientesConProveedor.add(cliente);
            }
        }
        return clientesConProveedor;
    }
}
