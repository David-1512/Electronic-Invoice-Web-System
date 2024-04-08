package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "linea_servicio", schema = "proyecto_prograiv", catalog = "")
public class LineaServicioEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cod")
    private int cod;
    @Basic
    @Column(name = "cantidad")
    private int cantidad;
    @Basic
    @Column(name = "subtotal")
    private int subtotal;
    @ManyToOne
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod", nullable = false)
    private ProductoEntity productoByCodProducto;
    @ManyToOne
    @JoinColumn(name = "num_factura", referencedColumnName = "num_factura", nullable = false)
    private FacturasEntity facturasByNumFactura;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaServicioEntity that = (LineaServicioEntity) o;
        return cod == that.cod && cantidad == that.cantidad && subtotal == that.subtotal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cod, cantidad, subtotal);
    }

    public ProductoEntity getProductoByCodProducto() {
        return productoByCodProducto;
    }

    public void setProductoByCodProducto(ProductoEntity productoByCodProducto) {
        this.productoByCodProducto = productoByCodProducto;
    }

    public FacturasEntity getFacturasByNumFactura() {
        return facturasByNumFactura;
    }

    public void setFacturasByNumFactura(FacturasEntity facturasByNumFactura) {
        this.facturasByNumFactura = facturasByNumFactura;
    }
}
