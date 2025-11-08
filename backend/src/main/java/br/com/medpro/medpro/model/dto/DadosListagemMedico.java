package br.com.medpro.medpro.model.dto;

import br.com.medpro.medpro.model.entity.Medico;
import br.com.medpro.medpro.model.enums.Especialidade;

public record DadosListagemMedico(
    Long id,
    String nome, 
    String email, 
    String crm, 
    Especialidade especialidade) {

    public DadosListagemMedico(Medico medico){
        this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getCrm(),
            medico.getEspecialidade()
        );
    }
}
