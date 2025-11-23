package com.medpro.medpro.controller;

import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.model.entity.Paciente;
import com.medpro.medpro.repository.ConsultaRepository;
import com.medpro.medpro.repository.MedicoRepository;
import com.medpro.medpro.repository.PacienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    //AGENDAR CONSULTA ----------------------------------------->
    @PostMapping
    @Transactional
    public ResponseEntity<?> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {

        // Verifica se paciente existe
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));

        // Verifica se médico existe
        Medico medico = medicoRepository.findById(dados.idMedico())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));

        /*
            - Verifica conflito de agenda do paciente
            - Verificar conflito de agenda do médico
        */
        if (consultaRepository.existsByPacienteAndDataHoraConsulta(paciente, dados.dataHora())) {
            return ResponseEntity.badRequest()
                    .body("O paciente já possui consulta marcada neste horário.");
        }

        if (consultaRepository.existsByMedicoAndDataHoraConsulta(medico, dados.dataHora())) {
            return ResponseEntity.badRequest()
                    .body("O médico já possui consulta marcada neste horário.");
        }

        // Criar objeto Consulta
        var consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHoraConsulta(dados.dataHora());

        // Regra de negócio na entidade
        consulta.validarData();

        // Salvar
        consultaRepository.save(consulta);

        return ResponseEntity.ok(consulta);
    }

    //LISTAR CONSULTAS ------------------------------------------>
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(consultaRepository.findAll());
    }

    //DETALHAR UMA CONSULTA ------------------------------------->
    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        var consulta = consultaRepository.findById(id);

        if (consulta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(consulta.get());
    }

    //EXCLUIR CONSULTA-------------------------------------------->
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!consultaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        consultaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}