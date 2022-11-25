package br.com.unitins.censohgp.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import br.com.unitins.censohgp.model.dto.PacienteDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Usuario;
import br.com.unitins.censohgp.model.enums.Genero;
import br.com.unitins.censohgp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.model.dto.NewPacienteDTO;
import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.model.Paciente;
import br.com.unitins.censohgp.model.Precaucao;
import br.com.unitins.censohgp.repository.DepartamentoRepository;
import br.com.unitins.censohgp.repository.PacienteRepository;
import br.com.unitins.censohgp.repository.PrecaucaoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")
public class PacienteResource {

	@Autowired
	PacienteRepository pacienteRepository;
	@Autowired
	DepartamentoRepository departamentoRepository;
	@Autowired
	PrecaucaoRepository precaucaoRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pacientes")
	public List<Paciente> findAll() {
		return pacienteRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/paciente/{idPaciente}")
	public Paciente findById(@PathVariable(value = "idPaciente") long id) {
		return pacienteRepository.findById(id);
	}

	// Aplicando padrão DTO
	@PostMapping("/paciente")
	public ResponseEntity<String> salvar(@RequestBody @Valid NewPacienteDTO newPacienteDto,
										 @RequestParam(value = "matriculaUsuario", required = false, defaultValue = "") String matriculaUsuario) {
		if (pacienteRepository.findByProntuario(newPacienteDto.getProntuario()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este paciente já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		Departamento dep = departamentoRepository.findById(newPacienteDto.getDepartamento());
		Usuario usu = usuarioRepository.findByMatricula(matriculaUsuario);
		if (dep == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento informado nao existe no sistema!");
		}
		List<Precaucao> listaPrecaucao = new ArrayList();
		long j = 0;
		Precaucao prec = new Precaucao();
		if (newPacienteDto.getPrecaucao() != null) {
			for (int i = 0; i < newPacienteDto.getPrecaucao().size(); i++) {
				j = newPacienteDto.getPrecaucao().get(i);
				prec = precaucaoRepository.findById(j);
				listaPrecaucao.add(prec);
			}
		}

		System.out.println("paciente: " + newPacienteDto);
		Paciente pac = new Paciente();
		pac.setNome(newPacienteDto.getNome());
		pac.setProntuario(newPacienteDto.getProntuario());
		pac.setNomeMae(newPacienteDto.getNomeMae());
		pac.setCpf(newPacienteDto.getCpf());
		pac.setRg(newPacienteDto.getRg());
		pac.setUsuario(usu);
		if(newPacienteDto.getGenero() == 1) {
			pac.addGenero(Genero.Masculino);
		}else if(newPacienteDto.getGenero() == 2){
			pac.addGenero(Genero.Feminino);
		}else {
			throw new NegocioException("Esse genero não existe no sistema!");
		}
		pac.setDataNascimento(newPacienteDto.getDataNascimento());
		pac.setDepartamento(dep);
		pac.setPrecaucao(listaPrecaucao);
		try {
			pacienteRepository.save(pac);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar!");
		}
		// return ResponseEntity<Paciente>(pacienteRepository.save(dep),
		// HttpStatus.CREATED);
	}

	//
	// Aplicando padrão DTO
	@PutMapping("/paciente")
	public ResponseEntity<Paciente> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDto,
												   @RequestParam(value = "matriculaUsuario", required = false, defaultValue = "") String matriculaUsuario) {
		Paciente paciente = pacienteRepository.findById(pacienteDto.getIdPaciente());
		Departamento dep = departamentoRepository.findById(pacienteDto.getDepartamento());
		Usuario usu = usuarioRepository.findByMatricula(matriculaUsuario);
		if (dep == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "É necesario informar um departamento!");
		}
		List<Precaucao> listaPrecaucao = new ArrayList();
		long j = 0;
		Precaucao prec = new Precaucao();
		if (pacienteDto.getPrecaucao() != null) {
			for (int i = 0; i < pacienteDto.getPrecaucao().size(); i++) {
				j = pacienteDto.getPrecaucao().get(i);
				prec = precaucaoRepository.findById(j);
				listaPrecaucao.add(prec);
			}
		}
		if(pacienteDto.getRg() != null){
		paciente.setRg(pacienteDto.getRg());
		}
		if(pacienteDto.getCpf() != null){
		paciente.setCpf(pacienteDto.getCpf());
		}
		if(pacienteDto.getNome() != null){
		paciente.setNome(pacienteDto.getNome());
		}
		if(pacienteDto.getNomeMae() != null){
		paciente.setNomeMae(pacienteDto.getNomeMae());
		}
		paciente.setUsuario(usu);
		if (!paciente.getGenero().isEmpty()){
			paciente.removeGenero(Genero.Masculino);
			paciente.removeGenero(Genero.Feminino);
		}
		if(pacienteDto.getGenero() == 1) {
			paciente.addGenero(Genero.Masculino);
		}else if(pacienteDto.getGenero() == 2){
			paciente.addGenero(Genero.Feminino);
		}else {
			throw new NegocioException("Esse genero não existe no sistema!");
		}
		if(pacienteDto.getDataNascimento() != null){
		paciente.setDataNascimento(pacienteDto.getDataNascimento());
		}
		paciente.setDepartamento(dep);
		paciente.setPrecaucao(listaPrecaucao);
		try {
			pacienteRepository.save(paciente);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar!");
		}
	}
}
