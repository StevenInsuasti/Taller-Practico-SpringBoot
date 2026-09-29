package com.hotel.Hotel.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

/**
 * DTO de salida base para habitaciones.
 * @JsonTypeInfo inyecta el campo "tipo" en el JSON con el valor del discriminador
 * declarado en @JsonSubTypes, sin necesidad de setearlo manualmente en el mapper.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HabitacionEstandarResponse.class, name = "ESTANDAR"),
        @JsonSubTypes.Type(value = SuitePresidencialResponse.class,  name = "SUITE")
})
public abstract class HabitacionResponse {

    private final UUID    id;
    private final String  numero;
    private final int     capacidadMaxima;
    private final double  precioPorNoche;
    private final String  estado;

    protected HabitacionResponse(UUID id, String numero, int capacidadMaxima,
                                  double precioPorNoche, String estado) {
        this.id             = id;
        this.numero         = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.estado         = estado;
    }

    public UUID   getId()             { return id; }
    public String getNumero()         { return numero; }
    public int    getCapacidadMaxima(){ return capacidadMaxima; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public String getEstado()         { return estado; }
}
