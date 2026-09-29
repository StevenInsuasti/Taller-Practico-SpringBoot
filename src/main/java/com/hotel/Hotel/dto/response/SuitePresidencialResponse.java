package com.hotel.Hotel.dto.response;

import java.util.UUID;

/**
 * DTO de salida para SuitePresidencial.
 * Jackson serializa "tipo": "SUITE" automáticamente por @JsonSubTypes en la clase base.
 */
public class SuitePresidencialResponse extends HabitacionResponse {

    private final boolean incluyeMayordomo;
    private final boolean jacuzziPrivado;

    public SuitePresidencialResponse(UUID id, String numero, int capacidadMaxima,
                                      double precioPorNoche, String estado,
                                      boolean incluyeMayordomo, boolean jacuzziPrivado) {
        super(id, numero, capacidadMaxima, precioPorNoche, estado);
        this.incluyeMayordomo = incluyeMayordomo;
        this.jacuzziPrivado   = jacuzziPrivado;
    }

    public boolean isIncluyeMayordomo() { return incluyeMayordomo; }
    public boolean isJacuzziPrivado()   { return jacuzziPrivado; }
}
