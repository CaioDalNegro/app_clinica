package com.medpro.medpro.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.model.entity.Paciente;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteAndDataHoraConsulta(Paciente paciente, LocalDateTime data);
    boolean existsByMedicoAndDataHoraConsulta(Medico medico, LocalDateTime data);
    boolean existsByPacienteAndDataHoraConsultaBetween(Paciente paciente, LocalDateTime inicio, LocalDateTime fim);
}
