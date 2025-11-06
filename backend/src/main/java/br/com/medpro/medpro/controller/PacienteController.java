package br.com.medpro.medpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.medpro.medpro.model.dto.DadosCadastroPaciente;
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
}
