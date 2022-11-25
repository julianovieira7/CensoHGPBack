package br.com.unitins.censohgp.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
public class PacienteDTO {
	private long idPaciente;
	private String nome;
	private String nomeMae;
	private String cpf;
	private String rg;
	private long genero;
	private Date dataNascimento;
	private List<Long> precaucao;
	private long departamento;

	public PacienteDTO() {
	}

	@Override
	public String toString() {
		return "PacienteDTO{" +

				", nome='" + nome + '\'' +
				", nomeMae='" + nomeMae + '\'' +
				", dataNascimento=" + dataNascimento +
				", precaucao=" + precaucao +
				", departamento=" + departamento +

				'}';
	}
}
