package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "producto", schema = "proyecto_prograiv", catalog = "")
public class ProductoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cod")
    private String cod;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "precio")
    private int precio;
    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    private ProveedorEntity proveedorByIdProveedor;
    @OneToMany(mappedBy = "productoByCodProducto")
    private Collection<LineaServicioEntity> lineaServiciosByCod;

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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoEntity that = (ProductoEntity) o;
        return precio == that.precio && Objects.equals(cod, that.cod) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod, nombre, precio);
    }

    public ProveedorEntity getProveedorByIdProveedor() {
        return proveedorByIdProveedor;
    }

    public void setProveedorByIdProveedor(ProveedorEntity proveedorByIdProveedor) {
        this.proveedorByIdProveedor = proveedorByIdProveedor;
    }

    public Collection<LineaServicioEntity> getLineaServiciosByCod() {
        return lineaServiciosByCod;
    }

    public void setLineaServiciosByCod(Collection<LineaServicioEntity> lineaServiciosByCod) {
        this.lineaServiciosByCod = lineaServiciosByCod;
    }
}
