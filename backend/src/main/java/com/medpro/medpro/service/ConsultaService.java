package com.medpro.medpro.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medpro.medpro.model.dto.DadosAgendamentoConsulta;
import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.model.entity.Paciente;
import com.medpro.medpro.repository.ConsultaRepository;
import com.medpro.medpro.repository.MedicoRepository;
import com.medpro.medpro.repository.PacienteRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public Consulta agendar(DadosAgendamentoConsulta dados) {

        // Buscar paciente ativo
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));

        if (!paciente.isAtivo())
            throw new IllegalArgumentException("Paciente inativo.");

        // Buscar médico ativo
        Medico medico = null;

        if (dados.idMedico() != null) {
            medico = medicoRepository.findById(dados.idMedico())
                    .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));

            if (!medico.isAtivo())
                throw new IllegalArgumentException("Médico inativo.");
        }

        // <---------------------- Regras de horário ---------------------->
        LocalDateTime dataHora = dados.dataHora();

        int hora = dataHora.getHour();

        if (hora < 7 || hora > 18)
            throw new IllegalArgumentException("A clínica funciona das 07:00 às 19:00.");

        if (dataHora.isBefore(LocalDateTime.now().plusMinutes(30)))
            throw new IllegalArgumentException("Consulta deve ser marcada com 30 minutos de antecedência.");

        /*
            - Verifica se o paciente já possui consulta neste horário.
            - Verifica se o médico já possui consulta neste horário.
        */
        LocalDateTime inicioDia = dataHora.toLocalDate().atStartOfDay();
        LocalDateTime fimDia = dataHora.toLocalDate().atTime(23, 59);

        if (consultaRepository.existsByPacienteAndDataHoraConsultaBetween(
                paciente, inicioDia, fimDia)) {
            throw new IllegalArgumentException("Paciente já possui consulta neste dia.");
        }

        if (medico != null) {
            if (consultaRepository.existsByMedicoAndDataHoraConsulta(medico, dataHora)) {
                throw new IllegalArgumentException("Médico já possui consulta neste horário.");
            }
        }

        // Escolhe médico disponível
        if (medico == null) {

            List<Medico> medicosAtivos = medicoRepository.findByAtivoTrue();

            // Filtra médicos que tenham vaga nesse horário
            medicosAtivos.removeIf(m ->
                    consultaRepository.existsByMedicoAndDataHoraConsulta(m, dataHora)
            );

            if (medicosAtivos.isEmpty()) {
                throw new IllegalArgumentException("Nenhum médico disponível neste horário.");
            }

            // Puxa um médico aleatório
            medico = medicosAtivos.get((int) (Math.random() * medicosAtivos.size()));
        }

        // Cria consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHoraConsulta(dataHora);

        consulta.validarDataInicial();

        return consultaRepository.save(consulta);
    }
}