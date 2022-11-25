package br.com.unitins.censohgp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_INCIDENTE")
@Getter
@Setter()
public class Incidente implements Serializable{

	private static final long serialVersionUID = -4991204829478436046L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idIncidente;
	
	@NotNull
	@NotBlank
	@Column(name="nome", nullable = false)
	private String nome;
	
	private String descricao;

	@NotNull
	private boolean ativo;
	
	public Incidente(@NotNull @NotBlank String nome, String descricao, @NotNull boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public Incidente() {
		super();
	}

	@Override
	public String toString() {
		return "Incidente [idIncidente=" + idIncidente + ", nome=" + nome + ", descricao=" + descricao + ", ativo="
				+ ativo + "]";
	}
	
	
}
