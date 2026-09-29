package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    // --- Mapeos concretos por subtipo ---

    @Mapping(target = "estado", expression = "java(h.getEstado().name())")
    HabitacionEstandarResponse toEstandarResponse(HabitacionEstandar h);

    @Mapping(target = "estado", expression = "java(s.getEstado().name())")
    SuitePresidencialResponse toSuiteResponse(SuitePresidencial s);

    // --- Dispatch polimórfico en tiempo de ejecución ---

    /**
     * Determina la subclase concreta de Habitacion y delega al método
     * de mapeo específico. MapStruct no puede hacer esto automáticamente
     * con herencia, por eso se usa un método default manual.
     */
    default HabitacionResponse toResponse(Habitacion habitacion) {
        if (habitacion instanceof HabitacionEstandar estandar) {
            return toEstandarResponse(estandar);
        } else if (habitacion instanceof SuitePresidencial suite) {
            return toSuiteResponse(suite);
        }
        throw new IllegalArgumentException(
                "Tipo de habitación desconocido: " + habitacion.getClass().getSimpleName());
    }

    // --- Lista polimórfica ---

    default List<HabitacionResponse> toResponseList(List<Habitacion> habitaciones) {
        return habitaciones.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
