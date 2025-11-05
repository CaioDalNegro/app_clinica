package br.com.medpro.medpro.model.dto;

import br.com.medpro.medpro.model.enums.Especialidade;

public record DadosCadastroMedico(String nome, String email, String cmr, Especialidade especialidade, DadosEndereco endereco) {
}