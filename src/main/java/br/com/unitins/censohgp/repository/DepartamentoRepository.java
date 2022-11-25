package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{

	Departamento findById(long id);

    @Query(" select d from Departamento d where upper( d.nome ) = upper( :nome )" )
    Departamento findByNomeUpperCase(@Param("nome") String nome);

//    METODOS DE FILTROS
    
	@Query(value = "SELECT *FROM tb_departamento p WHERE p.ativo = 'true'", nativeQuery=true)
	List<Departamento> findAllAtivos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.ativo = 'false'", nativeQuery=true)
	List<Departamento> findAllInativos();

	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= :interno",  nativeQuery=true)
	List<Departamento> findByTipo(@Param("interno") boolean interno);
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= true and p.ativo = true",  nativeQuery=true)
	List<Departamento> findAllAtivosInternos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= true and p.ativo = false",  nativeQuery=true)
	List<Departamento> findAllInativosInternos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= false and p.ativo = true",  nativeQuery=true)
	List<Departamento> findAllAtivosExternos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= false and p.ativo = false",  nativeQuery=true)
	List<Departamento> findAllInativosExternos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= false",  nativeQuery=true)
	List<Departamento> findAllExternos();
	
	@Query(value = "SELECT  *FROM tb_departamento p WHERE p.interno= true",  nativeQuery=true)
	List<Departamento> findAllInternos();

	 @Query(" select d from Departamento d order by d.nome asc" )
	    List<Departamento> findAll();
}

