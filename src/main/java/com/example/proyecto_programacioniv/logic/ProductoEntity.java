package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "producto", schema = "proyecto_prograiv", catalog = "")
public class ProductoEntity {

    @Id
    @Column(name = "cod")
    private String cod;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "precio")
    private Double precio;
    @OneToMany(mappedBy = "productoByCodProducto")
    private Collection<LineaServicioEntity> lineaServiciosByCod;
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    private ProveedorEntity proveedorByIdProveedor;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEntity that = (ProductoEntity) o;
        return Objects.equals(cod, that.cod) && Objects.equals(nombre, that.nombre) && Objects.equals(precio, that.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod, nombre, precio);
    }

    public Collection<LineaServicioEntity> getLineaServiciosByCod() {
        return lineaServiciosByCod;
    }

    public void setLineaServiciosByCod(Collection<LineaServicioEntity> lineaServiciosByCod) {
        this.lineaServiciosByCod = lineaServiciosByCod;
    }

    public ProveedorEntity getProveedorByIdProveedor() {
        return proveedorByIdProveedor;
    }

    public void setProveedorByIdProveedor(ProveedorEntity proveedorByIdProveedor) {
        this.proveedorByIdProveedor = proveedorByIdProveedor;
    }
}
