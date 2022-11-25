package br.com.unitins.censohgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.censohgp.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findById(long id);

    @Query(" select u from Usuario u order by u.nome asc" )
    List<Usuario> findAll();

    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=1 and u.ativo=true order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllAdministradorAtivo();
    
    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=1 and u.ativo=false order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllAdministradorInativo();
    
    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=1 order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllAdministrador();
    
    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=2 and u.ativo=true order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllEnfermeiroAtivo();
    
    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=2 and u.ativo=false order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllEnfermeiroInativo();
    
    @Query(value =" select * from tb_usuario u, tb_usuario_perfil x where u.id_usuario = x.usuario_id_usuario and x.perfil=2 order by u.nome asc", nativeQuery = true )
    List<Usuario> findAllEnfermeiro();
    
    @Query(" select u from Usuario u where upper( u.matricula ) like upper( :matricula )" )
	Usuario findByMatricula( @Param("matricula") String matricula);

	Usuario findByEmail(String email);
	
	@Modifying
    @Query(value="UPDATE Usuario set senha = :senha where id_usuario = :idUsuario", nativeQuery=true)
    Usuario updateSenhaUsuario(long idUsuario, String senha);
	/*
	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'true'")
	List<Usuario> findAllAtivos();
	@Query(value = "SELECT p FROM Usuario p WHERE p.ativo = 'false'")
	List<Usuario> findAllInativos();
	*/
}
