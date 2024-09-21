package com.proyfinaldyasoft.inmobiliariosystem.bd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropiedadJPA extends JpaRepository<PropiedadORM, Long> {
    List<PropiedadORM> findAllByCiudadEquals(String ciudad);

    List<PropiedadORM> findAllBytipoPropiedadEquals(String tipoPropiedad);

    List<PropiedadORM> findByPrecioBetween(Double minPrecio, Double maxPrecio);

    List<PropiedadORM> findAllByNombreEquals(String nombre);
}
