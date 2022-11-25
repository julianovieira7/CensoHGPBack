package br.com.unitins.censohgp.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.unitins.censohgp.model.dto.DepartamentoDTO;
import br.com.unitins.censohgp.model.Departamento;
import br.com.unitins.censohgp.repository.DepartamentoRepository;

@RestController
@RequestMapping(value = "/apicensohgp")

public class DepartamentoResource {

	@Autowired
	DepartamentoRepository departamentoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos")
	public List<Departamento> findAll() {
		return departamentoRepository.findAll();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamento/{idDepartamento}")
	public Departamento findById(@PathVariable(value = "idDepartamento") long id) {
		return departamentoRepository.findById(id);
	}

	//	METODOS DE FILTROS
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos")
	public List<Departamento> findAllAtivos() {
		return departamentoRepository.findAllAtivos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos")
	public List<Departamento> findAllInativos() {
		return departamentoRepository.findAllInativos();
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos-internos")//ativos internos
	public List<Departamento> findAllAtivosInternos() {
		return departamentoRepository.findAllAtivosInternos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/ativos-externos")//ativos externos
	public List<Departamento> findAllAtivosExternos() {
		return departamentoRepository.findAllAtivosExternos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos-internos")//inativos internos
	public List<Departamento> findAllInativosInternos() {
		return departamentoRepository.findAllInativosInternos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/inativos-externos")//inativos externos
	public List<Departamento> findAllInativosExternos() {
		return departamentoRepository.findAllInativosExternos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/externos")//inativos externos
	public List<Departamento> findAllExternos() {
		return departamentoRepository.findAllExternos();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/departamentos/internos")//inativos externos
	public List<Departamento> findAllInternos() {
		return departamentoRepository.findAllInternos();
	}

	// Aplicando padrão DTO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/departamento")
	public ResponseEntity<String> salvar(@RequestBody @Valid DepartamentoDTO departamentoDto) {
		if (departamentoRepository.findByNomeUpperCase(departamentoDto.getNome()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		if (departamentoDto.getNumero_leitos() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de leitos deve ser maior que zero!");
		}
		Departamento dep = new Departamento();
		dep.setNome(departamentoDto.getNome());
		dep.setNumero_leitos(departamentoDto.getNumero_leitos());
		dep.setDescricao(departamentoDto.getDescricao());
		dep.setAtivo(true);
		dep.setInterno(departamentoDto.isInterno());
		try {
			departamentoRepository.save(dep);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
		}

		// return ResponseEntity<Departamento>(departamentoRepository.save(dep),
		// HttpStatus.CREATED);
	}

	// Aplicando padrão DTO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/departamento")
	public ResponseEntity<String> updateDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDto) {
		Departamento departamentoExiste = departamentoRepository.findById(departamentoDto.getIdDepartamento());
		Departamento departamentoMesmoNome = departamentoRepository.findByNomeUpperCase(departamentoDto.getNome());
		// pega o nome e o id que o usuario enviou
		// se existir o nome informado com id diferente signfica que já existe
		// departamento cadastrado com o nome
		if (departamentoMesmoNome != null && departamentoMesmoNome.getIdDepartamento() != departamentoDto.getIdDepartamento()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este departamento já existe no sistema!");
			// return ResponseEntity.noContent().build();
		}
		if (departamentoDto.getNumero_leitos() < 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de leitos deve ser maior que zero!");
		}
		if (departamentoExiste != null) {
			try {
				Departamento dep = new Departamento();
				dep.setIdDepartamento(departamentoDto.getIdDepartamento());
				dep.setNome(departamentoDto.getNome());
				dep.setNumero_leitos(departamentoDto.getNumero_leitos());
				dep.setDescricao(departamentoDto.getDescricao());
				dep.setAtivo(departamentoDto.isAtivo());
				dep.setInterno(departamentoDto.isInterno());
				departamentoRepository.save(dep);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Houve algum problema durante o processamento, tente novamente ou contacte o administrador do sistema!");
			}

		} else {
			// throw new NegocioException("Usuário não encontrado no sistema!");
			// return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível encontrar o departamento informado!");
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/departamento/mudar-status")
	public ResponseEntity<String> updateStatusDepartamento(@Valid @RequestBody Departamento departamento) {
		Departamento departamentoDisable = departamentoRepository.findById(departamento.getIdDepartamento());
		// System.out.println("departamento: " + departamento + " DepartamentoDTO: " +
		// departamento);
		if (departamentoDisable != null) {
			try {

				if (departamentoDisable.isAtivo()) {
					departamentoDisable.setAtivo(false);
				} else {
					departamentoDisable.setAtivo(true);
				}
				departamentoRepository.save(departamentoDisable);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Erro interno, contacte o administrador do sistema!");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departamento não existe!");
		}
	}
}
