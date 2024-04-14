package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "proveedor", schema = "proyecto_prograiv", catalog = "")
public class ProveedorEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "proveedorByIdProveedor")
    private Collection<ClienteEntity> clientesById;
    @OneToMany(mappedBy = "proveedorByIdProveedor")
    private Collection<FacturasEntity> facturasById;
    @OneToMany(mappedBy = "proveedorByIdProveedor")
    private Collection<ProductoEntity> productosById;
    @ManyToOne
    @JoinColumn(name = "nif", referencedColumnName = "nif", nullable = false)
    private HaciendaEntity haciendaByNif;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorEntity proveedor = (ProveedorEntity) o;
        return Objects.equals(id, proveedor.id) && Objects.equals(nombre, proveedor.nombre) && Objects.equals(correo, proveedor.correo) && Objects.equals(telefono, proveedor.telefono) && Objects.equals(contrasena, proveedor.contrasena) && Objects.equals(estado, proveedor.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, correo, telefono, contrasena, estado);
    }

    public Collection<ClienteEntity> getClientesById() {
        return clientesById;
    }

    public void setClientesById(Collection<ClienteEntity> clientesById) {
        this.clientesById = clientesById;
    }

    public Collection<FacturasEntity> getFacturasById() {
        return facturasById;
    }

    public void setFacturasById(Collection<FacturasEntity> facturasById) {
        this.facturasById = facturasById;
    }

    public Collection<ProductoEntity> getProductosById() {
        return productosById;
    }

    public void setProductosById(Collection<ProductoEntity> productosById) {
        this.productosById = productosById;
    }

    public HaciendaEntity getHaciendaByNif() {
        return haciendaByNif;
    }

    public void setHaciendaByNif(HaciendaEntity haciendaByNif) {
        this.haciendaByNif = haciendaByNif;
    }
}
