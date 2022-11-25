package br.com.unitins.censohgp.resource;

import java.util.List;

import javax.validation.Valid;

import br.com.unitins.censohgp.model.enums.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.unitins.censohgp.repository.UsuarioRepository;
import br.com.unitins.censohgp.model.dto.UsuarioDTO;
import br.com.unitins.censohgp.exception.NegocioException;
import br.com.unitins.censohgp.model.Usuario;

@RestController
@RequestMapping(value = "/apicensohgp")
public class UsuarioResource {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	UsuarioRepository usuarioRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios")
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/administradores-ativos")
	public List<Usuario> findAllAdministradorAtivo(){
		return usuarioRepository.findAllAdministradorAtivo();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/administradores-inativos")
	public List<Usuario> findAllAdministradorInativos(){
		return usuarioRepository.findAllAdministradorInativo();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/administradores")
	public List<Usuario> findAllAdministrador(){
		return usuarioRepository.findAllAdministrador();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/enfermeiros-ativos")
	public List<Usuario> findAllEnfermeiroAtivo(){
		return usuarioRepository.findAllEnfermeiroAtivo();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/enfermeiros-inativos")
	public List<Usuario> findAllEnfermeiroInativos(){
		return usuarioRepository.findAllEnfermeiroInativo();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/enfermeiros")
	public List<Usuario> findAllEnfermeiro(){
		return usuarioRepository.findAllEnfermeiro();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuario/{idUsuario}")
	public Usuario findById(@PathVariable(value = "idUsuario") long id) {
		return usuarioRepository.findById(id);
	}


	//Aplicando padrão DTO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/usuario")
	public ResponseEntity<Usuario> salvar(@RequestBody UsuarioDTO usuarioDto) {
		Usuario usuarioExistente = usuarioRepository.findByMatricula(usuarioDto.getMatricula());
		if (usuarioExistente != null) {
			throw new NegocioException("Esta matrícula já existe no sistema!");
		}
		Usuario usu = new Usuario();
		usu.setMatricula(usuarioDto.getMatricula());
		usu.setNome(usuarioDto.getNome());
		usu.setEmail(usuarioDto.getEmail());
		usu.setAtivo(true);
		usu.setSenha(pe.encode(usuarioDto.getSenha()));
		if(usuarioDto.getPerfil() == 1) {
			usu.addPerfil(Perfil.Administrador);
		}else if(usuarioDto.getPerfil() == 2){
			usu.addPerfil(Perfil.Enfermeiro);
		}else {
			throw new NegocioException("Esse perfil não existe no sistema!");
		}
		return new ResponseEntity<Usuario>(usuarioRepository.save(usu), HttpStatus.CREATED);
	}
	//Aplicando padrão DTO
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/usuario")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody UsuarioDTO usuarioDto){
		Usuario usuario = usuarioRepository.findByMatricula(usuarioDto.getMatricula());
		if (usuario != null && usuario.getIdUsuario() != usuarioDto.getIdUsuario() ) {
			throw new NegocioException("Esta matrícula já existe no sistema!");
		} else if(usuario != null ) {
			try {
				Usuario usuUpdate = new Usuario();
				usuUpdate.setIdUsuario(usuarioDto.getIdUsuario());
				usuUpdate.setMatricula(usuarioDto.getMatricula());
				usuUpdate.setNome(usuarioDto.getNome());
				usuUpdate.setEmail(usuarioDto.getEmail());
				usuUpdate.setAtivo(usuarioDto.isAtivo());
				usuUpdate.setSenha(pe.encode(usuarioDto.getSenha()));
				if(usuarioDto.getPerfil() == 1) {
					usuUpdate.addPerfil(Perfil.Administrador);
				}else if(usuarioDto.getPerfil() == 2){
					usuUpdate.addPerfil(Perfil.Enfermeiro);
				}else {
					throw new NegocioException("Esse perfil não existe no sistema!");
				}
				return new ResponseEntity<Usuario>(usuarioRepository.save(usuUpdate), HttpStatus.CREATED);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}

		}else {
			//throw new NegocioException("Usuário não encontrado no sistema!");
			//return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário informado não existe!");
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/usuario/mudar-status")
	public ResponseEntity<Usuario> updateStatusUsuario(@Valid @RequestBody UsuarioDTO usuarioDto){
		Usuario usu = usuarioRepository.findById(usuarioDto.getIdUsuario());
		if(usu != null ) {
			try {
					if(usu.isAtivo()) {
						usu.setAtivo(false);
					} else {
						usu.setAtivo(true);
					}
				return new ResponseEntity<Usuario>(usuarioRepository.save(usu), HttpStatus.CREATED);
			}
			catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno, contacte o administrador do sistema!");
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário informado não existe!");
		}
	}
}