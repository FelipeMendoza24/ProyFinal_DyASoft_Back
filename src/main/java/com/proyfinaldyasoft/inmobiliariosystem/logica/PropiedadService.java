package com.proyfinaldyasoft.inmobiliariosystem.logica;

import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadJPA;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadORM;
import com.proyfinaldyasoft.inmobiliariosystem.controller.PropiedadDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropiedadService {

    private final PropiedadJPA propiedadJPA;

    public List<PropiedadORM> obtenerTodasPropiedades() {
        return propiedadJPA.findAll();
    }

    public List<PropiedadORM> filtrarPropiedadesPorCiudad(String ciudad) {
        List<PropiedadORM> propiedades = propiedadJPA.findAllByCiudadEquals(ciudad);
        if (propiedades.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna propiedad en la ciudad: " + ciudad);
        }
        return propiedades;
    }

    public List<PropiedadORM> filtrarPropiedadesPorTipo(String tipoPropiedad) {
        List<PropiedadORM> propiedades = propiedadJPA.findAllBytipoPropiedadEquals(tipoPropiedad);
        if (propiedades.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna propiedad de tipo: " + tipoPropiedad);
        }
        return propiedades;
    }

    public List<PropiedadORM> filtrarPropiedadesPorPrecio(Double minPrecio, Double maxPrecio) {
        if (minPrecio == null || maxPrecio == null) {
            throw new IllegalArgumentException("Debe proporcionar tanto minPrecio como maxPrecio para filtrar.");
        }
        if (minPrecio < 0 || maxPrecio < 0) {
            throw new IllegalArgumentException("Los valores de minPrecio y maxPrecio deben ser mayores o iguales a 0.");
        }
        if (minPrecio > maxPrecio) {
            throw new IllegalArgumentException("El minPrecio no puede ser mayor que el maxPrecio.");
        }
        return propiedadJPA.findByPrecioBetween(minPrecio, maxPrecio);

    }

    public List<PropiedadORM> obtenerPropiedadPorNombre(String nombre) {
        List<PropiedadORM> propiedades = propiedadJPA.findAllByNombreEquals(nombre);
        if (propiedades.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna propiedad con el nombre: " + nombre);
        }
        return propiedades;
    }

    public String guardarPropiedad(PropiedadDTO propiedadDTO) {
        if (propiedadDTO.nombre() == null || propiedadDTO.nombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la propiedad es obligatorio.");
        }
        if (propiedadDTO.tipoOferta() == null || propiedadDTO.tipoOferta().isEmpty()) {
            throw new IllegalArgumentException("El tipo de oferta es obligatorio.");
        }
        if (propiedadDTO.ciudad() == null || propiedadDTO.ciudad().isEmpty()) {
            throw new IllegalArgumentException("La ciudad es obligatoria.");
        }
        if (propiedadDTO.direccion() == null || propiedadDTO.direccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (propiedadDTO.tipoPropiedad() == null || propiedadDTO.tipoPropiedad().isEmpty()) {
            throw new IllegalArgumentException("El tipo de propiedad es obligatorio.");
        }
        if (propiedadDTO.tamano() <= 0.0f) {
            throw new IllegalArgumentException("El tamaño de la propiedad debe ser mayor que 0.");
        }
        if (propiedadDTO.precio() <= 0.0f) {
            throw new IllegalArgumentException("El precio de la propiedad debe ser mayor que 0.");
        }
        propiedadJPA.save(new PropiedadORM(
                propiedadDTO.nombre(),
                propiedadDTO.tipoOferta(),
                propiedadDTO.ciudad(),
                propiedadDTO.direccion(),
                propiedadDTO.tipoPropiedad(),
                propiedadDTO.tamano(),
                propiedadDTO.precio(),
                propiedadDTO.habitaciones(),
                propiedadDTO.banos(),
                propiedadDTO.estado()
        ));
        return "Propiedad guardada";
    }

    public String eliminarPropiedad(Long id) {
        if (!propiedadJPA.existsById(id)) {
            throw new RuntimeException("Propiedad no encontrada con el ID: " + id);
        }
        propiedadJPA.deleteById(id);
        return "Propiedad eliminada";
    }

    public String actualizarEstadoPropiedad(Long id, Boolean estado) {
        PropiedadORM propiedad = propiedadJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con el ID: " + id));
        propiedad.setEstado(estado);
        propiedadJPA.save(propiedad);
        return "Estado de la propiedad actualizado correctamente";
    }
}

