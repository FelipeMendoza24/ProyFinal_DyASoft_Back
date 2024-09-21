package com.proyfinaldyasoft.inmobiliariosystem.controller;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadJPA;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadORM;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class PropiedadController {

    private PropiedadJPA propiedadJPA;
    List<PropiedadDTO> propiedades = new ArrayList<>();


    //GET Read, POST Create, PUT Update, DELETE Delete -> CRUD
    @GetMapping(path = "/propiedades/todos")
    public List<PropiedadORM> obtenerEstudiantes(){
        return propiedadJPA.findAll();
    }

    //Filtrar
    @GetMapping(path = "/filtrarPropiedadesCiudad")
    public List<PropiedadORM> filtrarPropiedadesCiudad(@RequestParam String ciudad) {
        return propiedadJPA.findAllByCiudadEquals(ciudad);
    }
    @GetMapping(path = "/filtrarPropiedadesTipoPropiedad")
    public List<PropiedadORM> filtrarPropiedadesTipoPropiedad(@RequestParam String tipoPropiedad) {
        return propiedadJPA.findAllBytipoPropiedadEquals(tipoPropiedad);
    }
    @GetMapping(path = "/filtrarPropiedadesPrecio")
    public List<PropiedadORM> filtrarPropiedadesPrecio(
            @RequestParam(name = "minPrecio", required = false) Double minPrecio,
            @RequestParam(name = "maxPrecio", required = false) Double maxPrecio) {

        minPrecio = minPrecio != null ? minPrecio : 0.0;
        maxPrecio = maxPrecio != null ? maxPrecio : Double.MAX_VALUE;

        return propiedadJPA.findByPrecioBetween(minPrecio, maxPrecio);
    }
    @GetMapping(path = "/propiedad/{nombre}")
    public List<PropiedadORM> obtenerPropiedad(@PathVariable String nombre) {
        return propiedadJPA.findAllByNombreEquals(nombre);
    }


    @PostMapping(path = "/guardarPropiedad")
    public String guardarPropiedad(@RequestBody PropiedadDTO propiedad){
        propiedades.add(propiedad);
        propiedadJPA.save(new PropiedadORM(propiedad.nombre(),propiedad.tipoOferta(),propiedad.ciudad(), propiedad.direccion(), propiedad.tipoPropiedad(), propiedad.tamano(), propiedad.precio(),propiedad.habitaciones(),propiedad.banos(),propiedad.estado()));
        return "Propiedad guardada";
    }

    @DeleteMapping(path = "/eliminarPropiedad/{id}")
    public String eliminarPropiedad(@PathVariable Long id) {
        propiedadJPA.deleteById(id);
        return "Propiedad eliminado";
    }

    @PutMapping(path = "/actualizarPropiedadEstado/{id}")
    public String actualizarEstadoPropiedad(@PathVariable Long id, @RequestParam Boolean estado) {
        PropiedadORM propiedad = propiedadJPA.findById(id).orElseThrow(() -> new RuntimeException("Propiedad no encontrada con el ID: " + id));
        propiedad.setEstado(estado);
        propiedadJPA.save(propiedad);
        return "Estado de la propiedad actualizado correctamente";
    }


}
