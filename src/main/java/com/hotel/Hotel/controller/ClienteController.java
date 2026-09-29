package com.hotel.Hotel.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody CrearClienteRequest request,
            UriComponentsBuilder uriBuilder) {
        ClienteResponse response = clienteService.crear(request);
        URI uri = uriBuilder.path("/api/clientes/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    /**
     * PATCH /api/clientes/{id}
     * Actualización parcial: solo los campos presentes (no-null) se modifican.
     * email, activo y penalizaciones permanecen inalterados si no se envían.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody ActualizarClienteRequest request) {
        return ResponseEntity.ok(clienteService.actualizar(id, request));
    }

    /**
     * GET /api/clientes/{id}/resumen
     * Vista ejecutiva del cliente: datos personales + historial de reservas
     * con montos calculados, sin recursión infinita en la serialización.
     */
    @GetMapping("/{id}/resumen")
    public ResponseEntity<ClienteResumenResponse> obtenerResumen(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.obtenerResumen(id));
    }
}
