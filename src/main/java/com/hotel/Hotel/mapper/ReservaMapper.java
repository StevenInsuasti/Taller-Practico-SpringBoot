package com.hotel.Hotel.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.response.ReservaResponse;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "nombreHuesped",    source = "cliente.nombre")
    @Mapping(target = "habitacionNumero", source = "habitacion.numero")
    @Mapping(target = "fechaInicio",      expression = "java(reserva.getPeriodo().fechaInicio().toLocalDate())")
    @Mapping(target = "fechaFin",         expression = "java(reserva.getPeriodo().fechaFin().toLocalDate())")
    @Mapping(target = "estado",           expression = "java(reserva.getEstado().name())")
    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> reservas);
}