package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.request.CrearHabitacionEstandarRequest;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import com.hotel.Hotel.mapper.HabitacionMapper;
import com.hotel.Hotel.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    public HabitacionService(HabitacionRepository habitacionRepository,
                              HabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper     = habitacionMapper;
    }

    @Transactional
    public HabitacionEstandarResponse crearEstandar(CrearHabitacionEstandarRequest request) {
        HabitacionEstandar nueva = new HabitacionEstandar(
                request.numero(),
                request.capacidadMaxima(),
                request.precioPorNoche(),
                request.camasIndividuales()
        );
        HabitacionEstandar guardada = (HabitacionEstandar) habitacionRepository.save(nueva);
        return habitacionMapper.toEstandarResponse(guardada);
    }

    @Transactional
    public SuitePresidencialResponse crearSuite(CrearSuitePresidencialRequest request) {
        SuitePresidencial nueva = new SuitePresidencial(
                request.numero(),
                request.capacidadMaxima(),
                request.precioPorNoche(),
                request.incluyeMayordomo(),
                request.jacuzziPrivado()
        );
        SuitePresidencial guardada = (SuitePresidencial) habitacionRepository.save(nueva);
        return habitacionMapper.toSuiteResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<HabitacionResponse> listarTodas() {
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }
}
