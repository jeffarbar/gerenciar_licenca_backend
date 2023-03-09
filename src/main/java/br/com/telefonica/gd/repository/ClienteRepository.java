package br.com.telefonica.gd.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.telefonica.gd.model.ClienteModel;

public interface ClienteRepository extends MongoRepository<ClienteModel, String> {
	
	Optional<ClienteModel> findByMasterTrue();

	Optional<ClienteModel> findByRazaoSocial(String razaosocial);
	
	Optional<ClienteModel> findByCnpj(String cnpj);
	
	List<ClienteModel> findBySharingTrue();	
	
	List<ClienteModel> findBySharingFalse();	
	
	
	List<ClienteModel> findBySegmento(String segmento);	

    List<ClienteModel> findAllByOrderByRazaoSocialDesc();
    
    List<ClienteModel> findRazaoSocialByOrderByRazaoSocialDesc(String razaoSocial);

}