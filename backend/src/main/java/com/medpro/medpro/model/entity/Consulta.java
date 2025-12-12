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

    // Relacionamento com Paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    // Relacionamento com Médico
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    // Horário da consulta
    @Column(name = "data_hora_consulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    private boolean cancelada;

    // Valida regras de horário no momento do agendamento
    public void validarDataInicial() {
        if (this.dataHoraConsulta.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("Consulta deve ser agendada com 30 minutos de antecedência.");
        }

        int hora = dataHoraConsulta.getHour();
        int diaSemana = dataHoraConsulta.getDayOfWeek().getValue();

        if (diaSemana == 7) {
            throw new IllegalArgumentException("A clínica não funciona aos domingos.");
        }

        if (hora < 7 || hora >= 19) {
            throw new IllegalArgumentException("Horário fora do expediente (07:00 às 19:00).");
        }
    }

    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;

    // Realiza o cancelamento aplicando regras de negócio
    public void cancelar(MotivoCancelamento motivo) {

        if (this.cancelada)
            throw new IllegalArgumentException("A consulta já foi cancelada.");

        // Regra das 24h
        var agora = LocalDateTime.now();
        var diferencaHoras = Duration.between(agora, this.dataHoraConsulta).toHours();

        if (diferencaHoras < 24) {
            throw new IllegalArgumentException("Consulta só pode ser cancelada com 24 horas de antecedência.");
        }

        this.cancelada = true;
        this.motivoCancelamento = motivo;
    }
}