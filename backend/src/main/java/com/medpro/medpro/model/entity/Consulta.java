package com.medpro.medpro.model.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import com.medpro.medpro.enums.MotivoCancelamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "data_hora_consulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    // --- Regra de negócio na própria entidade ---
    public void validarData() {
        if (this.dataHoraConsulta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A consulta não pode ser marcada no passado.");
        }
    }
    // indica se a consulta já foi cancelada
    private boolean cancelada;

    // guarda o motivo (obrigatório)
@Enumerated(EnumType.STRING)
private MotivoCancelamento motivoCancelamento;

 public void cancelar(MotivoCancelamento motivo) {

        // valida se já está cancelada
        if (this.cancelada) {
            throw new IllegalArgumentException("A consulta já foi cancelada.");
        }

        // Regra: cancelamento só pode ocorrer 24 horas antes
        var agora = LocalDateTime.now();
        var diferencaHoras = Duration.between(agora, this.dataHoraConsulta).toHours();

        if (diferencaHoras < 24) {
            throw new IllegalArgumentException("Consulta só pode ser cancelada com 24 horas de antecedência.");
        }

        // Marca como cancelada
        this.cancelada = true;

        // Armazena o motivo
        this.motivoCancelamento = motivo;
    }
}