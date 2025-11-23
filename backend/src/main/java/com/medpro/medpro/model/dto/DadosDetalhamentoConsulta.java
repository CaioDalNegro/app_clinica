package com.medpro.medpro.model.dto;

import com.medpro.medpro.model.entity.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
    Long id,
    Long idPaciente,
    Long idMedico,
    LocalDateTime dataHora) {

        public DadosDetalhamentoConsulta(Consulta consulta) {
            this(consulta.getId(),
            consulta.getPaciente().getId(),
            consulta.getMedico().getId(),
            consulta.getDataHoraConsulta());
        }
}