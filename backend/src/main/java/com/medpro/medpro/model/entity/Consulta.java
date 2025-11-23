package com.medpro.medpro.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consulta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    private LocalDateTime dataHoraConsulta;

    // --- Regra de negócio na própria entidade ---
    public void validarData() {
        if (this.dataHoraConsulta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A consulta não pode ser marcada no passado.");
        }
    }
}