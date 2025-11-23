package com.medpro.medpro.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.model.entity.Paciente;

@Repository
public interface ConsultaRepository extends JpaRepository <Consulta, Long>{
    
    boolean existsByPacienteAndDataHoraConsulta(Paciente paciente, LocalDateTime dataHoraConsulta); // Verifica se o paciente já tem consulta na mesma data/hora
    boolean existsByMedicoAndDataHoraConsulta(Medico medico, LocalDateTime dataHoraConsulta); // Verifica se o médico já tem consulta na mesma data/hora

    //List<Consulta> findByDataHoraConsulta(LocalDateTime dataHoraConsulta);
}
