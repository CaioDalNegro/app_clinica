package br.com.medpro.medpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.medpro.medpro.model.entity.Paciente;

public interface PacienteRepository extends JpaRepository <Paciente, Long>{
    
}
