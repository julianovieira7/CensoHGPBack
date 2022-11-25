package br.com.unitins.censohgp.model.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	private long idUsuario;
	private String matricula;
	private String nome;
	private String email;
	private String senha;
	private long perfil;
	private boolean ativo;

	public UsuarioDTO() {

	}
}
