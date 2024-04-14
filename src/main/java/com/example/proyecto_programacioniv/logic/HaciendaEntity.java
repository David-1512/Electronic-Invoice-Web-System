package com.example.proyecto_programacioniv.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "hacienda", schema = "proyecto_prograiv", catalog = "")
public class HaciendaEntity {

    @Id
    @Column(name = "nif")
    private String nif;
    @Basic
    @Column(name = "act_economica")
    private String actEconomica;
    @OneToMany(mappedBy = "haciendaByNif")
    private Collection<ProveedorEntity> proveedorsByNif;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getActEconomica() {
        return actEconomica;
    }

    public void setActEconomica(String actEconomica) {
        this.actEconomica = actEconomica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HaciendaEntity that = (HaciendaEntity) o;
        return Objects.equals(nif, that.nif) && Objects.equals(actEconomica, that.actEconomica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, actEconomica);
    }

    public Collection<ProveedorEntity> getProveedorsByNif() {
        return proveedorsByNif;
    }

    public void setProveedorsByNif(Collection<ProveedorEntity> proveedorsByNif) {
        this.proveedorsByNif = proveedorsByNif;
    }
}
