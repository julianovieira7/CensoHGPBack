package br.com.unitins.censohgp.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.unitins.censohgp.model.enums.Genero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="TB_PACIENTE")
@Getter
@Setter()
public class Paciente implements Serializable {

	private static final long serialVersionUID = 4038057425224115166L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPaciente;

	@NotBlank
	@NotNull
	@Column(name="prontuario", nullable = false, unique = true)
	private String prontuario;

	@Column(name="nome")
	private String nome;

	@Column(name="nomeMae")
	private String nomeMae;

	@Column(name="cpf")
	private String cpf;

	@Column(name="rg")
	private String rg;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="TB_PACIENTE_GENERO")
	private Set<Integer> genero = new HashSet<>();

	@Column(name="dataNascimento", nullable = true)
	private Date dataNascimento;

	@ManyToMany (fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.MERGE)
	@JoinTable(name="tb_Paciente_Precaucao",
			joinColumns={@JoinColumn(name = "id_paciente",referencedColumnName = "idPaciente")},
			inverseJoinColumns={@JoinColumn(name = "id_precaucao",referencedColumnName = "idPrecaucao")})

	private List<Precaucao> precaucao;


	@OneToOne
	@JoinColumn(name = "id_departamento",referencedColumnName = "idDepartamento")
	private Departamento departamento;


	@OneToOne
	@JoinColumn(name = "id_usuario",referencedColumnName = "idUsuario")
	private Usuario usuario;

	public void addGenero(Genero addGenero) {
		genero.add(addGenero.getCod());
	}

	public void removeGenero(Genero removeGenero) {
		genero.remove(removeGenero.getCod());
	}
	public Paciente( String prontuario, String nome, String nomeMae, String cpf, String rg,
					 Date dataNascimento, List<Precaucao> precaucao,Departamento departamento, Usuario usuario) {
		this.prontuario = prontuario;
		this.nome = nome;
		this.nomeMae = nomeMae;
		this.cpf = cpf;
		this.rg = rg;
		this.departamento = departamento;
		this.dataNascimento = dataNascimento;
		this.precaucao = precaucao;
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Paciente paciente = (Paciente) o;
		return idPaciente == paciente.idPaciente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPaciente);
	}

	public Paciente() {
		super();
	}

}
