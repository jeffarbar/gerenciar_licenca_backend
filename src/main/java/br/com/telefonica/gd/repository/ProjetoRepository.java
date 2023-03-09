package br.com.telefonica.gd.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.telefonica.gd.model.ProjetoModel;


public interface ProjetoRepository extends MongoRepository<ProjetoModel, String> {

	//Optional<ProjetoModel> findByIdAndTipoLicenciamentoDocumentosId(String idProjeto, String idDocumento);
	
	List<ProjetoModel> findByClienteIdAndDataCadastroBetween(String idCliente, LocalDateTime fromDate, LocalDateTime toDate);
	
	List<ProjetoModel> findByClienteIdOrderByDataCadastroDesc(String idCliente);	
	
	List<ProjetoModel> findByClienteSharingIdOrderByDataCadastroDesc(String idCliente);
	
}
