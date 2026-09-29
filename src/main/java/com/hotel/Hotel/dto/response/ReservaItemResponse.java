package com.hotel.Hotel.dto.response;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Sub-DTO de reserva para la vista de resumen del cliente.
 * No contiene referencia al cliente — corta el ciclo de serialización
 * que ocurriría si se usara ReservaResponse (que incluye nombreHuesped).
 */
public record ReservaItemResponse(
        UUID      idReserva,
        String    numeroHabitacion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String    estado,
        double    costoTotal) {
}
