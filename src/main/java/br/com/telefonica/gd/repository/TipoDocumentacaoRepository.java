package br.com.telefonica.gd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.telefonica.gd.model.TipoDocumentacaoModel;

public interface TipoDocumentacaoRepository extends MongoRepository<TipoDocumentacaoModel, String> {

	List<TipoDocumentacaoModel> findAllByOrderByNomeDesc();
}
