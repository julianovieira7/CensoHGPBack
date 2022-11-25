package br.com.unitins.censohgp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CHECKLIST")
@Getter
@Setter()
@NoArgsConstructor
public class Checklist implements Serializable {

	private static final long serialVersionUID = 1406098838611661052L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idChecklist;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne()
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany()
	@JoinColumn(name = "idFatorRisco")
	@JoinTable(name = "tb_checklist_fator_risco", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idfator_risco", referencedColumnName = "idFatorRisco") })
	private List<FatorRisco> fatorRisco;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany()
	@JoinColumn(name = "idIncidente")
	@JoinTable(name = "tb_checklist_incidente", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idincidente", referencedColumnName = "idIncidente") })
	private List<Incidente> incidente;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany()
	@JoinColumn(name = "idProcedimento")
	@JoinTable(name = "tb_checklist_procedimento", joinColumns = {
			@JoinColumn(name = "idchecklist", referencedColumnName = "idChecklist") }, inverseJoinColumns = {
					@JoinColumn(name = "idprocedimento", referencedColumnName = "idProcedimento") })
	private List<Procedimento> procedimento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@PrePersist
	private void atualizarDadosAntesInsert() {
		Date date = new Date();
		LocalDateTime localDate = LocalDateTime.now();
		date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
		this.dataCadastro = date;

	}

	private String Observacao;

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataCadastroFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		LocalDateTime date = dataCadastro.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		String currentTime = date.format(formatter);

		return currentTime;

	}

	@Override
	public String toString() {
		return "Checklist [idChecklist=" + idChecklist + ", usuario=" + usuario + ", paciente=" + paciente
				+ ", fatorRisco=" + fatorRisco + ", incidente=" + incidente + ", procedimento=" + procedimento
				+ ", dataCadastro=" + dataCadastro + ", Observacao=" + Observacao + "]";
	}

}
