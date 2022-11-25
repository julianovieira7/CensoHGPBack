package br.com.unitins.censohgp.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.model.dto.ChecklistDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Checklist;
import br.com.unitins.censohgp.model.FatorRisco;
import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Procedimento;
import br.com.unitins.censohgp.repository.ChecklistRepository;
import br.com.unitins.censohgp.repository.FatorRiscoRepository;
import br.com.unitins.censohgp.repository.IncidenteRepository;
import br.com.unitins.censohgp.repository.PacienteRepository;
import br.com.unitins.censohgp.repository.ProcedimentoRepository;
import br.com.unitins.censohgp.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class ChecklistResource {
	LocalDate localDate = LocalDate.now();

	@Autowired
	ChecklistRepository checklistRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PacienteRepository pacienteRepository;

	@Autowired
	FatorRiscoRepository fatorRepository;

	@Autowired
	ProcedimentoRepository procedimentoRepository;

	@Autowired
	IncidenteRepository incidenteRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/checklists")
	public List<Checklist> findAll() {
		return checklistRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/checklist/{idChecklist}")
	public Checklist findById(@PathVariable(value = "idChecklist") long id) {
		Checklist check = new Checklist();
		check = checklistRepository.findById(id);
		System.out.println(" ta aqui: " + check.getDataCadastroFormatada());
		return checklistRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/checklists/paciente/{idPaciente}")
	public List<Checklist> findByPaciente(@PathVariable(value = "idPaciente") long idPaciente) {

		return checklistRepository.findByPaciente(idPaciente);
	}

	@PostMapping("/checklist")
	public ResponseEntity<Checklist> salvar(@RequestBody ChecklistDTO checklistDto) {
		Checklist checklist = new Checklist();

		try {

			if (checklistDto.getMatriculaUsuario() != null) {
				checklist.setUsuario(usuarioRepository.findByMatricula(checklistDto.getMatriculaUsuario()));
				checklist.setPaciente(pacienteRepository.findById(checklistDto.getIdPaciente()));
				checklist.setObservacao(checklistDto.getObservacao());

				List<FatorRisco> listaFatores = new ArrayList();
				long j = 0;
				FatorRisco fator = new FatorRisco();
				if (checklistDto.getFatorRisco() != null) {
					for (int i = 0; i < checklistDto.getFatorRisco().size(); i++) {
						j = checklistDto.getFatorRisco().get(i);
						fator = fatorRepository.findById(j);
						listaFatores.add(fator);

					}
				}

				List<Incidente> listaIncidentes = new ArrayList();
				long jj = 0;
				Incidente incidente = new Incidente();
				if (checklistDto.getIncidente() != null) {
					for (int i = 0; i < checklistDto.getIncidente().size(); i++) {
						jj = checklistDto.getIncidente().get(i);
						incidente = incidenteRepository.findById(jj);
						listaIncidentes.add(incidente);

					}
				}

				List<Procedimento> listaProcedimentos = new ArrayList();
				long jjj = 0;
				Procedimento procedimento = new Procedimento();
				if (checklistDto.getProcedimento() != null) {
					for (int i = 0; i < checklistDto.getProcedimento().size(); i++) {
						jjj = checklistDto.getProcedimento().get(i);
						procedimento = procedimentoRepository.findById(jjj);
						listaProcedimentos.add(procedimento);

					}
				}

				checklist.setFatorRisco(listaFatores);
				checklist.setProcedimento(listaProcedimentos);
				checklist.setIncidente(listaIncidentes);

			} else {
				throw new NegocioException("O checklist necessita de um usuário!");
			}

			return new ResponseEntity<Checklist>(checklistRepository.save(checklist), HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Erro interno, contacte o administrador do sistema!");
		}
	}

}