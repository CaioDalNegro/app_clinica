package br.com.medpro.medpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.medpro.medpro.model.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
}
