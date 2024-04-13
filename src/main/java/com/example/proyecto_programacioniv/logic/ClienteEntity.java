package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "proyecto_prograiv", catalog = "") //tabla a la que hace referencia
public class ClienteEntity {//implementar serializable luego

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id//llave primaria
    @Column(name = "id")// se pone cuando no se llama igual en la base de datos
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
    @OneToMany(mappedBy = "clienteByIdCliente")
    private Collection<FacturasEntity> facturasById;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    private ProveedorEntity proveedorByIdProveedor;

    public ClienteEntity(String clienteID, String nombre, String correo, String telefono, ProveedorEntity prov) {
        this.id=clienteID;
        this.nombre=nombre;
        this.correo=correo;
        this.telefono=telefono;
        this.proveedorByIdProveedor=prov;
    }

    public ClienteEntity() {

    }

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

    public ProveedorEntity getProveedorByIdProveedor() {
        return proveedorByIdProveedor;
    }

    public void setProveedorByIdProveedor(ProveedorEntity proveedorByIdProveedor) {
        this.proveedorByIdProveedor = proveedorByIdProveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(correo, that.correo) && Objects.equals(telefono, that.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, correo, telefono);
    }

    public Collection<FacturasEntity> getFacturasById() {
        return facturasById;
    }

    public void setFacturasById(Collection<FacturasEntity> facturasById) {
        this.facturasById = facturasById;
    }
}
