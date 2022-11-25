package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Incidente;

public interface IncidenteRepository extends JpaRepository<Incidente, Long>{
	Incidente findById(long id);
    Incidente findByNome(String nome);
    
    @Query(" select i from Incidente i order by i.nome asc" )
    List<Incidente> findAll();
}
