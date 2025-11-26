package com.medpro.medpro.model.dto;

import com.medpro.medpro.enums.MotivoCancelamento;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(

    // ID da consulta a ser cancelada
        @NotNull Long idConsulta,

        // Motivo obrigatório conforme regras de negócio
        @NotNull MotivoCancelamento motivo
) {
    
}
