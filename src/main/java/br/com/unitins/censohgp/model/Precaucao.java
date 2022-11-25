package br.com.unitins.censohgp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Entity()
@Table(name="TB_PRECAUCAO")
@Getter
@Setter()
public class Precaucao implements Serializable{

	private static final long serialVersionUID = 8245332206209363985L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPrecaucao;

	@NotNull
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;

	@ManyToOne
	@JoinColumn(name="id_paciente", referencedColumnName = "idPaciente")
	private Paciente paciente;

	private String descricao;

	@NotNull
	private boolean ativo;

	public Precaucao(@NotNull @NotBlank String nome, String descricao,  @NotNull boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Precaucao() {
		super();
	}

	@Override
	public String toString() {
		return "Precaucao [idPrecaucao=" + idPrecaucao + ", nome=" + nome + ", descricao=" + descricao + ", ativo="
				+ ativo + "]";
	}
}
