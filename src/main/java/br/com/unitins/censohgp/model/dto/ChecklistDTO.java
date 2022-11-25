package br.com.unitins.censohgp.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChecklistDTO {

	private long idChecklist;
	private long idPaciente;
	private String matriculaUsuario;
	private String observacao;
	List<Long> fatorRisco;
	List<Long> incidente;
	List<Long> procedimento;

	public ChecklistDTO(long idChecklist, long idPaciente, String matriculaUsuario, String observacao,
			List<Long> fatorRisco, List<Long> incidente, List<Long> procedimento) {
		super();
		this.idChecklist = idChecklist;
		this.idPaciente = idPaciente;
		this.matriculaUsuario = matriculaUsuario;
		this.observacao = observacao;
		this.fatorRisco = fatorRisco;
		this.incidente = incidente;
		this.procedimento = procedimento;
	}

}
