package com.medpro.medpro.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.medpro.medpro.model.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
    // List<Medico> findAllByAtivoTrue();

    // lista médicos ativos
    List<Medico> findByAtivoTrue();
}