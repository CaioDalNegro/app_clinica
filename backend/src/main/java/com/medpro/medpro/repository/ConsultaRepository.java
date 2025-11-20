package com.medpro.medpro.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.model.entity.Paciente;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long>{
    
    boolean existsByPacienteAndDataHoraConsulta(LocalDateTime dataHoraConsulta, Paciente paciente);  // Verifica se o paciente já tem consulta
    boolean existsByMedicoAndDataHoraConsulta(LocalDateTime dataHoraConsulta, Medico medico);  // Verifica se o médico já tem consulta

    //List<Consulta> findByDataHoraConsulta(LocalDateTime dataHoraConsulta);
}
