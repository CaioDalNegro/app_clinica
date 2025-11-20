package com.medpro.medpro.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @NotNull Long idPaciente,
        @NotNull Long idMedico,
        @NotNull @Future LocalDateTime dataHora) {

}
