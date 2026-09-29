package com.hotel.Hotel.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.dto.response.ReservaItemResponse;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    // ---------------------------------------------------------------
    // Mapeos básicos existentes
    // ---------------------------------------------------------------

    ClienteResponse toResponse(Cliente cliente);

    List<ClienteResponse> toResponseList(List<Cliente> clientes);

    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "activo",        ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas",      ignore = true)
    Cliente toEntity(CrearClienteRequest request);

    // ---------------------------------------------------------------
    // Tarea 2: Resumen ejecutivo con proyecciones anidadas
    // ---------------------------------------------------------------

    /**
     * Mapea Cliente → ClienteResumenResponse.
     * Los campos calculados referencian métodos @Named auxiliares
     * para que MapStruct invoque la lógica de Stream en tiempo de compilación.
     */
    @Mapping(target = "totalReservasRealizadas",
             expression = "java(cliente.getReservas().size())")
    @Mapping(target = "montoTotalGastado",
             expression = "java(calcularMontoTotal(cliente.getReservas()))")
    @Mapping(target = "reservasRecientes",
             expression = "java(toReservaItemList(cliente.getReservas()))")
    ClienteResumenResponse toResumenResponse(Cliente cliente);

    /**
     * Mapea una Reserva al sub-DTO sin referencia al cliente,
     * evitando la recursión infinita en la serialización JSON.
     */
    @Mapping(target = "idReserva",        source = "id")
    @Mapping(target = "numeroHabitacion", source = "habitacion.numero")
    @Mapping(target = "fechaInicio",
             expression = "java(reserva.getPeriodo().fechaInicio().toLocalDate())")
    @Mapping(target = "fechaFin",
             expression = "java(reserva.getPeriodo().fechaFin().toLocalDate())")
    @Mapping(target = "estado",
             expression = "java(reserva.getEstado().name())")
    ReservaItemResponse toReservaItem(Reserva reserva);

    // ---------------------------------------------------------------
    // Métodos auxiliares @Named invocados desde las expresiones
    // ---------------------------------------------------------------

    /**
     * Suma el costoTotal de todas las reservas del cliente.
     */
    @Named("calcularMontoTotal")
    default double calcularMontoTotal(List<Reserva> reservas) {
        return reservas.stream()
                .mapToDouble(Reserva::getCostoTotal)
                .sum();
    }

    /**
     * Convierte la lista de Reserva a List<ReservaItemResponse>
     * delegando en toReservaItem para que MapStruct gestione cada elemento.
     */
    @Named("toReservaItemList")
    default List<ReservaItemResponse> toReservaItemList(List<Reserva> reservas) {
        return reservas.stream()
                .map(this::toReservaItem)
                .toList();
    }

    /**
     * Tarea 3: Actualización parcial segura (PATCH).
     * NullValuePropertyMappingStrategy.IGNORE hace que MapStruct omita
     * cualquier campo del DTO que llegue como null, preservando el valor
     * actual de la entidad sin sobrescribirlo.
     * Los campos id, activo, penalizaciones y reservas se ignoran
     * explícitamente para evitar la vulnerabilidad de sobre-asignación
     * (Mass Assignment).
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",             ignore = true)
    @Mapping(target = "activo",         ignore = true)
    @Mapping(target = "penalizaciones", ignore = true)
    @Mapping(target = "reservas",       ignore = true)
    void updateClienteFromDto(ActualizarClienteRequest dto, @MappingTarget Cliente entity);
}
