package br.com.unitins.censohgp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.unitins.censohgp.model.enums.Genero;
import br.com.unitins.censohgp.model.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_USUARIO")
@Getter
@Setter()

public class Usuario implements Serializable {

	private static final long serialVersionUID = 4910606617343821908L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;


	@NotNull
	@Column(name="matricula", nullable = false)
	private String matricula;


	@Column(name="nome", nullable = false)
	private String nome;


	@Column(name="email", nullable = false)
	private String email;



	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="TB_USUARIO_PERFIL")
	private Set<Integer> perfil = new HashSet<>();

	@JsonIgnore
	@Column(name="senha", nullable = false)
	private String senha;

	@NotNull
	private boolean ativo;

	public Usuario() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return idUsuario == usuario.idUsuario;
	}

	@Override
	public int hashCode() {

		return Objects.hash(idUsuario);
	}

	public Set<Perfil> getPerfil() {

		return perfil.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil addPerfil) {

		perfil.add(addPerfil.getCod());
	}


	public Usuario(@NotBlank @NotNull String matricula, @NotBlank String nome, @NotBlank String email,
				   @NotBlank String senha, @NotNull boolean ativo) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.ativo = true;
	}
}
