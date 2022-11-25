package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Checklist;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
	@Query(" select c from Checklist c inner join fetch c.paciente p  where  ( c.idChecklist) like upper ( :id ) " )
	Checklist findById(@Param("id") long id);

//	 @Query(" select c from Checklist c where c.id equal id " )
	@Query(value = "SELECT * from tb_checklist where tb_checklist.id_paciente = ?1", nativeQuery = true)
	List<Checklist> findByPaciente(long idPaciente);

}