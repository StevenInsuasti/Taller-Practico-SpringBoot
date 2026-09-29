package com.hotel.Hotel.dto.request;

/**
 * DTO para PATCH parcial de cliente.
 * Ambos campos son opcionales: si llegan como null, el mapper los ignora
 * gracias a NullValuePropertyMappingStrategy.IGNORE en ClienteMapper.
 */
public record ActualizarClienteRequest(
        String nombre,
        String email) {
}
