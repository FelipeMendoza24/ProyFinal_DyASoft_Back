package com.proyfinaldyasoft.inmobiliariosystem.logica;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadORM;
import com.proyfinaldyasoft.inmobiliariosystem.bd.PropiedadJPA;
import com.proyfinaldyasoft.inmobiliariosystem.controller.PropiedadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropiedadServiceTest {

    @Mock
    PropiedadJPA propiedadJPA;

    @InjectMocks
    PropiedadService propiedadService;
    List<PropiedadORM> mockPropiedades;

    @Test
    void WhenObtenerTodasPropiedades_ThenReturnAllPropiedades() {
        PropiedadORM propiedad1 = new PropiedadORM();
        propiedad1.setNombre("Propiedad 1");
        propiedad1.setCiudad("Ciudad A");

        PropiedadORM propiedad2 = new PropiedadORM();
        propiedad2.setNombre("Propiedad 2");
        propiedad2.setCiudad("Ciudad B");

        mockPropiedades = Arrays.asList(propiedad1, propiedad2);
        when(propiedadJPA.findAll()).thenReturn(mockPropiedades);
        List<PropiedadORM> propiedades = propiedadService.obtenerTodasPropiedades();
        assertEquals(2, propiedades.size());
        assertEquals("Propiedad 1", propiedades.get(0).getNombre());
        assertEquals("Propiedad 2", propiedades.get(1).getNombre());
    }

    @Test
    void GivenNoExisteCiudad_WhenFiltrarPropiedadesPorCiudad_Then_returnException() {
        String ciudad = "Ciudad A";
        when(propiedadJPA.findAllByCiudadEquals(ciudad)).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorCiudad(ciudad);
        });
    }

    @Test
    void GivenExisteCiudad_WhenFiltrarPropiedadesPorCiudad_Then_returnlista() {
        ArrayList<PropiedadORM> propiedadesSimuladas = new ArrayList<>();
        propiedadesSimuladas.add(new PropiedadORM());
        String ciudad = "Ciudad A";
        when(propiedadJPA.findAllByCiudadEquals("Ciudad A")).thenReturn(propiedadesSimuladas);
        List<PropiedadORM> propiedades = propiedadService.filtrarPropiedadesPorCiudad(ciudad);
        assertEquals(1, propiedades.size());
        Mockito.verify(propiedadJPA).findAllByCiudadEquals("Ciudad A");
    }

    @Test
    void GivenNoExisteTipoPropiedad_WhenFiltrarPropiedadesPorCiudad_Then_returnException() {
        String tipoPropiedad = "Casa";
        when(propiedadJPA.findAllBytipoPropiedadEquals(tipoPropiedad)).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorTipo(tipoPropiedad);
        });
    }

    @Test
    void GivenExisteTipoPropiedad_WhenFiltrarPropiedadesPorTipo_Then_returnlista() {
        ArrayList<PropiedadORM> propiedadesSimuladas = new ArrayList<>();
        propiedadesSimuladas.add(new PropiedadORM());
        String tipoPropiedad = "Casa";
        when(propiedadJPA.findAllBytipoPropiedadEquals(tipoPropiedad)).thenReturn(propiedadesSimuladas);
        List<PropiedadORM> propiedades = propiedadService.filtrarPropiedadesPorTipo(tipoPropiedad);
        assertEquals(1, propiedades.size());
        Mockito.verify(propiedadJPA).findAllBytipoPropiedadEquals(tipoPropiedad);
    }

    @Test
    void GivenExistenPropiedadesEnRango_WhenFiltrarPropiedadesPorPrecio_Then_returnList() {
        Double minPrecio = 100000.0;
        Double maxPrecio = 300000.0;

        PropiedadORM propiedad1 = new PropiedadORM();
        propiedad1.setNombre("Casa Económica");
        propiedad1.setPrecio(150000.0f);

        PropiedadORM propiedad2 = new PropiedadORM();
        propiedad2.setNombre("Casa Mediana");
        propiedad2.setPrecio(300000.0f);

        List<PropiedadORM> mockPropiedades = Arrays.asList(propiedad1, propiedad2);
        when(propiedadJPA.findByPrecioBetween(minPrecio, maxPrecio)).thenReturn(mockPropiedades);
        List<PropiedadORM> propiedades = propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        assertEquals(2, propiedades.size());

    }

    @Test
    void GivenNoExistenPropiedadesEnRango_WhenFiltrarPropiedadesPorPrecio_Then_returnList() {
        Double minPrecio = 100000.0;
        Double maxPrecio = 200000.0;

        when(propiedadJPA.findByPrecioBetween(minPrecio, maxPrecio)).thenReturn(new ArrayList<>());
        List<PropiedadORM> propiedades = propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        assertEquals(0, propiedades.size());
    }

    @Test
    void GivenMinPrecioEsMayorQueMaxPrecio_WhenFiltrarPropiedadesPorPrecio_Then_returnException() {
        // Arrange
        Double minPrecio = 500000.0;
        Double maxPrecio = 100000.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        });

        assertEquals("El minPrecio no puede ser mayor que el maxPrecio.", exception.getMessage());
    }

    @Test
    void GivenMinPrecioEsNulo_WhenFiltrarPropiedadesPorPrecio_Then_returnException() {
        Double minPrecio = null;
        Double maxPrecio = 500000.0;

        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        });
    }

    @Test
    void GivenMaxPrecioEsNulo_WhenFiltrarPropiedadesPorPrecio_Then_returnException() {
        Double minPrecio = 100000.0;
        Double maxPrecio = null;
        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        });
    }

    @Test
    void GivenPreciosSonNegativos_WhenFiltrarPropiedadesPorPrecio_Then_returnException() {
        Double minPrecio = -100000.0;
        Double maxPrecio = -500000.0;
        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.filtrarPropiedadesPorPrecio(minPrecio, maxPrecio);
        });
    }

    @Test
    void GivenNoExisteNombre_WhenFiltrarPropiedadesPorNombre_Then_returnException() {
        String nombre = "Nueva Casa";
        when(propiedadJPA.findAllByNombreEquals(nombre)).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.obtenerPropiedadPorNombre(nombre);
        });
    }

    @Test
    void GivenExisteNombre_WhenFiltrarPropiedadesPorNombre_Then_returnlista() {
        ArrayList<PropiedadORM> propiedadesSimuladas = new ArrayList<>();
        propiedadesSimuladas.add(new PropiedadORM());
        String nombre = "Nueva Casa";
        when(propiedadJPA.findAllByNombreEquals(nombre)).thenReturn(propiedadesSimuladas);
        List<PropiedadORM> propiedades = propiedadService.obtenerPropiedadPorNombre(nombre);
        assertEquals(1, propiedades.size());
        Mockito.verify(propiedadJPA).findAllByNombreEquals(nombre);
    }


    @Test
    void When_guardarPropiedades_Then_ReturnPropiedadGuardada() {
        PropiedadDTO propiedadDTO = new PropiedadDTO(
                "Casa Bonita",
                "Venta",
                "Ciudad A",
                "Calle 123",
                "Casa",
                120.0f,
                300000.0f,
                4,
                2,
                true
        );
        String resultado = propiedadService.guardarPropiedad(propiedadDTO);
        assertEquals("Propiedad guardada", resultado);
    }
    @Test
    void GivenTamanoEsInvalido_WhenGuardarPropiedad_Then_returnException() {
        PropiedadDTO propiedadDTO = mock(PropiedadDTO.class);
        when(propiedadDTO.tamano()).thenReturn(0.0f); // Invalid size

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.guardarPropiedad(propiedadDTO);
        });

        assertEquals("El tamaño de la propiedad debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void GivenPrecioEsInvalido_WhenGuardarPropiedad_Then_returnException() {
        PropiedadDTO propiedadDTO = mock(PropiedadDTO.class);
        when(propiedadDTO.tamano()).thenReturn(100.0f);
        when(propiedadDTO.precio()).thenReturn(0.0f);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.guardarPropiedad(propiedadDTO);
        });

        assertEquals("El precio de la propiedad debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void GivenPropiedadExiste_WhenEliminarPropiedad_Then_returnString() {
        Long idPropiedad = 1L;
        when(propiedadJPA.existsById(idPropiedad)).thenReturn(true); // Mock existsById to return true
        doNothing().when(propiedadJPA).deleteById(idPropiedad);      // Mock deleteById to do nothing
        String result = propiedadService.eliminarPropiedad(idPropiedad);

        assertEquals("Propiedad eliminada", result);
        verify(propiedadJPA).deleteById(idPropiedad); // Verify deleteById was called
    }

    @Test
    void GivenPropiedadNoExiste_WhenEliminarPropiedad_Then_returnException() {
        Long idPropiedad = 1L;
        when(propiedadJPA.existsById(idPropiedad)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            propiedadService.eliminarPropiedad(idPropiedad);
        });
    }

    @Test
    void GivenPropiedadExiste_WhenActualizarEstadoPropiedad_Then_returnPropiedad() {
        Long idPropiedad = 1L;
        Boolean nuevoEstado = false;
        PropiedadORM propiedadORM = new PropiedadORM();
        propiedadORM.setEstado(true); // Current state is true

        when(propiedadJPA.findById(idPropiedad)).thenReturn(Optional.of(propiedadORM)); // Mock findById
        when(propiedadJPA.save(propiedadORM)).thenReturn(propiedadORM);                 // Mock save

        String result = propiedadService.actualizarEstadoPropiedad(idPropiedad, nuevoEstado);
        assertEquals("Estado de la propiedad actualizado correctamente", result);
        verify(propiedadJPA).save(propiedadORM);
    }

    @Test
    void GivenPropiedadNoExiste_WhenActualizarEstadoPropiedad_Then_returnException() {
        Long idPropiedad = 1L;
        Boolean nuevoEstado = false;

        when(propiedadJPA.findById(idPropiedad)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            propiedadService.actualizarEstadoPropiedad(idPropiedad, nuevoEstado);
        });

    }
}