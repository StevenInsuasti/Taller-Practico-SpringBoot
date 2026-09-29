package com.hotel.Hotel.dto.response;

import java.util.List;
import java.util.UUID;

/**
 * DTO compuesto para la vista ejecutiva del cliente.
 * Los campos calculados (totalReservasRealizadas, montoTotalGastado)
 * son poblados por ClienteMapper usando @Named + Streams.
 */
public record ClienteResumenResponse(
        UUID                    id,
        String                  nombre,
        String                  email,
        boolean                 activo,
        int                     penalizaciones,
        int                     totalReservasRealizadas,
        double                  montoTotalGastado,
        List<ReservaItemResponse> reservasRecientes) {
}
