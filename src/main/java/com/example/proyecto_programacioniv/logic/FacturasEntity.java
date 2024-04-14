package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "facturas", schema = "proyecto_prograiv", catalog = "")
public class FacturasEntity {

    @Id
    @Column(name = "num_factura")
    private String numFactura;
    @Basic
    @Column(name = "fech_emision")
    private Date fechEmision;
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private ClienteEntity clienteByIdCliente;
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    private ProveedorEntity proveedorByIdProveedor;
    @OneToMany(mappedBy = "facturasByNumFactura")
    private Collection<LineaServicioEntity> lineaServiciosByNumFactura;

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Date getFechEmision() {
        return fechEmision;
    }

    public void setFechEmision(Date fechEmision) {
        this.fechEmision = fechEmision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturasEntity that = (FacturasEntity) o;
        return Objects.equals(numFactura, that.numFactura) && Objects.equals(fechEmision, that.fechEmision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numFactura, fechEmision);
    }

    public ClienteEntity getClienteByIdCliente() {
        return clienteByIdCliente;
    }

    public void setClienteByIdCliente(ClienteEntity clienteByIdCliente) {
        this.clienteByIdCliente = clienteByIdCliente;
    }

    public ProveedorEntity getProveedorByIdProveedor() {
        return proveedorByIdProveedor;
    }

    public void setProveedorByIdProveedor(ProveedorEntity proveedorByIdProveedor) {
        this.proveedorByIdProveedor = proveedorByIdProveedor;
    }

    public Collection<LineaServicioEntity> getLineaServiciosByNumFactura() {
        return lineaServiciosByNumFactura;
    }

    public void setLineaServiciosByNumFactura(Collection<LineaServicioEntity> lineaServiciosByNumFactura) {
        this.lineaServiciosByNumFactura = lineaServiciosByNumFactura;
    }
}
