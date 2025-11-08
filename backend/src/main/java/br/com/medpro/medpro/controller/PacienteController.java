package br.com.medpro.medpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.medpro.medpro.model.dto.DadosCadastroPaciente;
import br.com.medpro.medpro.model.dto.DadosListagemPaciente;
import br.com.medpro.medpro.model.entity.Paciente;
import br.com.medpro.medpro.repository.PacienteRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    
    
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados){
       pacienteRepository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(Pageable paginacao){
        return pacienteRepository.findAll(paginacao).map(DadosListagemPaciente::new);
    }
}
