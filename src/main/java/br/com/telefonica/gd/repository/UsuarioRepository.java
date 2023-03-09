package br.com.telefonica.gd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.telefonica.gd.model.UsuarioModel;

public interface UsuarioRepository extends MongoRepository<UsuarioModel, String> {

	Optional<UsuarioModel> findByEmail(String email);
	
	List<UsuarioModel> findByPerfil(String perfil);
	
	List<UsuarioModel> findByPerfilIn(List<String> perfis);
	
	List<UsuarioModel> findByClienteId(String idCliente);
	
	List<UsuarioModel> findByClienteIdAndPerfilIn(String idCliente, List<String> perfis);
	
}
