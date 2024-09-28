package com.proyfinaldyasoft.inmobiliariosystem.integration;

import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadORM;
import com.proyfinaldyasoft.inmobiliariosystem.controller.PropiedadDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PruebaDeIntegracion {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void PruebaIntegracion(){
        // Guardar Propiedad
        PropiedadDTO nuevaPropiedad = new PropiedadDTO("MiCasa","Venta", "Madrid", "Via San Felipe #24", "Casa", 300f, 1000000f, 4,3,true);
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/guardarPropiedad",nuevaPropiedad,String.class);
        Assertions.assertEquals("Propiedad guardada", respuestaInsercion.getBody());

        // Filtros
        String ciudadPrueba = "Madrid";
        String url1 = UriComponentsBuilder.fromPath("/filtrarPropiedadesCiudad")
                .queryParam("ciudad", ciudadPrueba)
                .toUriString();
        ResponseEntity<List> resultadoGet1 = testRestTemplate.getForEntity(url1, List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultadoGet1.getBody()).isEmpty());

        String tipoPropiedadPrueba = "Casa";
        String url2 = UriComponentsBuilder.fromPath("/filtrarPropiedadesTipoPropiedad")
                .queryParam("tipoPropiedad", tipoPropiedadPrueba)
                .toUriString();
        ResponseEntity<List> resultadoGet2 = testRestTemplate.getForEntity(url2, List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultadoGet2.getBody()).isEmpty());

        Double minPrecio = 100000.0;
        Double maxPrecio = 3000000.0;
        String url3 = UriComponentsBuilder.fromPath("/filtrarPropiedadesPrecio")
                .queryParam("minPrecio", minPrecio)
                .queryParam("maxPrecio", maxPrecio)
                .toUriString();
        ResponseEntity<List> resultadoGet3 = testRestTemplate.getForEntity(url3, List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultadoGet3.getBody()).isEmpty());

        ResponseEntity<List> resultadoGet4 = testRestTemplate.getForEntity("/propiedad/MiCasa", List.class);
        Assertions.assertFalse(Objects.requireNonNull(resultadoGet4.getBody()).isEmpty());

        // Actualizar Propiedad
        Long idPropiedad = 1L;
        Boolean nuevoEstado = false;
        String url4 = UriComponentsBuilder.fromPath("/actualizarPropiedadEstado/{id}")
                .queryParam("estado", nuevoEstado)
                .buildAndExpand(idPropiedad)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> resultadoPut = testRestTemplate.exchange(url4, HttpMethod.PUT, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, resultadoPut.getStatusCode());
        Assertions.assertTrue(resultadoPut.getBody().contains("Estado de la propiedad actualizado correctamente"));

        // Eliminar Propiedad
        String url = "/eliminarPropiedad/" + idPropiedad;
        ResponseEntity<String> resultadoDelete = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, resultadoDelete.getStatusCode());
        Assertions.assertTrue(resultadoDelete.getBody().contains("Propiedad eliminada"));
    }

}
