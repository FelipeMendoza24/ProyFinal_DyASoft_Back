package com.proyfinaldyasoft.inmobiliariosystem.bd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "propiedad")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropiedadORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipooferta")
    private String tipoOferta;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "tipopropiedad")
    private String tipoPropiedad;

    @Column(name = "tamano")
    private float tamano;

    @Column(name = "precio")
    private float precio;

    @Column(name = "habitaciones")
    private int habitaciones;

    @Column(name = "banos")
    private int banos;

    @Column(name = "estado")
    private boolean estado;

    public PropiedadORM(String nombre, String tipoOferta, String ciudad, String direccion, String tipoPropiedad, float tamano, float precio, int habitaciones, int banos, boolean estado) {
        this.nombre = nombre;
        this.tipoOferta = tipoOferta;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipoPropiedad = tipoPropiedad;
        this.tamano = tamano;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.banos = banos;
        this.estado = estado;
    }

}
