package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long>{
	Procedimento findById(long id);

    Procedimento findByNome( String nome);

    @Query(" select p from Procedimento p order by p.nome asc" )
    List<Procedimento> findAll();
}
