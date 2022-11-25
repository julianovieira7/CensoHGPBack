package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Incidente;
import br.com.unitins.censohgp.model.Precaucao;

public interface PrecaucaoRepository extends JpaRepository<Precaucao, Long>{
	Precaucao findById(long id);
    Precaucao findByNome(String nome);

    @Query(" select p from Precaucao p order by p.nome asc" )
    List<Precaucao> findAll();
}
