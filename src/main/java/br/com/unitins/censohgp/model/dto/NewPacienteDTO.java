package br.com.unitins.censohgp.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class NewPacienteDTO {

	private long idPaciente;
	private String prontuario;
	private String nome;
	private String nomeMae;
	private String cpf;
	private String rg;
	private long genero;
	private Date dataNascimento;
	private List<Long> precaucao;
	private long departamento;


	public NewPacienteDTO(long idPaciente, String prontuario, String nome, String nomeMae, String cpf,
						  Date dataNascimento, List<Long> precaucao, long departamento, long checklist) {
		super();
		this.idPaciente = idPaciente;
		this.prontuario = prontuario;
		this.nome = nome;
		this.nomeMae = nomeMae;
		this.cpf = cpf;

		this.dataNascimento = dataNascimento;
		this.precaucao = precaucao;
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "PacienteDTO{" +
				"idPaciente=" + idPaciente +
				", prontuario='" + prontuario + '\'' +
				", nome='" + nome + '\'' +
				", nomeMae='" + nomeMae + '\'' +
				", cpf='" + cpf + '\'' +
				", dataNascimento=" + dataNascimento +
				", precaucao=" + precaucao +
				", departamento=" + departamento +

				'}';
	}
}
