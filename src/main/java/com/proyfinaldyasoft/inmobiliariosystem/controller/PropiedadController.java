package com.proyfinaldyasoft.inmobiliariosystem.controller;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadJPA;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadORM;
import com.proyfinaldyasoft.inmobiliariosystem.logica.PropiedadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
// Prueba
@RestController
@AllArgsConstructor
@CrossOrigin
public class PropiedadController {

    private final PropiedadService propiedadService;

    //GET Read, POST Create, PUT Update, DELETE Delete -> CRUD
    @GetMapping(path = "/propiedades/todos")
    public List<PropiedadORM> obtenerPropiedades() {
        return propiedadService.obtenerTodasPropiedades();
    }

    // Filtrar
    @GetMapping(path = "/filtrarPropiedadesCiudad")
    public List<PropiedadORM> filtrarPropiedadesCiudad(@RequestParam String ciudad) {
        return propiedadService.filtrarPropiedadesPorCiudad(ciudad);
    }

    @GetMapping(path = "/filtrarPropiedadesTipoPropiedad")
    public List<PropiedadORM> filtrarPropiedadesTipoPropiedad(@RequestParam String tipoPropiedad) {
        return propiedadService.filtrarPropiedadesPorTipo(tipoPropiedad);
    }

    @GetMapping(path = "/filtrarPropiedadesPrecio")
    public List<PropiedadORM> filtrarPropiedadesPrecio(
            @RequestParam(name = "minPrecio", required = false) Double minPrecio,
            @RequestParam(name = "maxPrecio", required = false) Double maxPrecio) {
        return propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
    }

    @GetMapping(path = "/propiedad/{nombre}")
    public List<PropiedadORM> obtenerPropiedad(@PathVariable String nombre) {
        return propiedadService.obtenerPropiedadPorNombre(nombre);
    }

    @PostMapping(path = "/guardarPropiedad")
    public String guardarPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
        return propiedadService.guardarPropiedad(propiedadDTO);
    }

    @DeleteMapping(path = "/eliminarPropiedad/{id}")
    public String eliminarPropiedad(@PathVariable Long id) {
        return propiedadService.eliminarPropiedad(id);
    }

    @PutMapping(path = "/actualizarPropiedadEstado/{id}")
    public String actualizarEstadoPropiedad(@PathVariable Long id, @RequestParam Boolean estado) {
        return propiedadService.actualizarEstadoPropiedad(id, estado);
    }
}
