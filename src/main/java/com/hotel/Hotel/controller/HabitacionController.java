package com.hotel.Hotel.controller;

import com.hotel.Hotel.dto.request.CrearHabitacionEstandarRequest;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import com.hotel.Hotel.service.HabitacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    /**
     * POST /api/habitaciones/estandar
     * Crea una habitación estándar y devuelve 201 Created con Location header.
     */
    @PostMapping("/estandar")
    public ResponseEntity<HabitacionEstandarResponse> crearEstandar(
            @RequestBody CrearHabitacionEstandarRequest request,
            UriComponentsBuilder uriBuilder) {

        HabitacionEstandarResponse creada = habitacionService.crearEstandar(request);
        URI uri = uriBuilder.path("/api/habitaciones/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(uri).body(creada);
    }

    /**
     * POST /api/habitaciones/suites
     * Crea una suite presidencial y devuelve 201 Created con Location header.
     */
    @PostMapping("/suites")
    public ResponseEntity<SuitePresidencialResponse> crearSuite(
            @RequestBody CrearSuitePresidencialRequest request,
            UriComponentsBuilder uriBuilder) {

        SuitePresidencialResponse creada = habitacionService.crearSuite(request);
        URI uri = uriBuilder.path("/api/habitaciones/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(uri).body(creada);
    }

    /**
     * GET /api/habitaciones
     * Retorna lista polimórfica: cada elemento incluye "tipo": "ESTANDAR" o "SUITE"
     * gracias a @JsonTypeInfo en HabitacionResponse.
     */
    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> listar() {
        return ResponseEntity.ok(habitacionService.listarTodas());
    }
}
