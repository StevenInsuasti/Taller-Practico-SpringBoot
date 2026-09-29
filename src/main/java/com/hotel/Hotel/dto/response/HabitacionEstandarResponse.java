package com.hotel.Hotel.dto.response;

import java.util.UUID;

/**
 * DTO de salida para HabitacionEstandar.
 * Jackson serializa "tipo": "ESTANDAR" automáticamente por @JsonSubTypes en la clase base.
 */
public class HabitacionEstandarResponse extends HabitacionResponse {

    private final int camasIndividuales;

    public HabitacionEstandarResponse(UUID id, String numero, int capacidadMaxima,
                                       double precioPorNoche, String estado,
                                       int camasIndividuales) {
        super(id, numero, capacidadMaxima, precioPorNoche, estado);
        this.camasIndividuales = camasIndividuales;
    }

    public int getCamasIndividuales() {
        return camasIndividuales;
    }
}
