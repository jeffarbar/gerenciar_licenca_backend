package br.com.telefonica.gd.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.telefonica.gd.dto.JwtUsuarioDto;
import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.enums.SituacaoArquivoEnum;
import br.com.telefonica.gd.enums.SituacaoDocumentacaoEnum;
import br.com.telefonica.gd.response.ListaProjetoAgrupadoResponse;
import br.com.telefonica.gd.response.ListaProjetoResponse;
import br.com.telefonica.gd.response.PainelResponse;
import br.com.telefonica.gd.response.ProjetoAgrupadoResponse;
import br.com.telefonica.gd.response.ProjetoDocumentoResponse;
import br.com.telefonica.gd.response.ProjetoResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.util.JwtTokenUtil;

@Service
public class PainelService {

	private static final Logger logger = LogManager.getLogger(PainelService.class);
	
	@Autowired
	private ProjetoService projetoService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public Response listAgrupado(String requestToken) throws Exception{
		
		try {
			
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
				
			List<ProjetoResponse> listaProjetoResponse = null;
				
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
				listaProjetoResponse = ((ListaProjetoResponse) projetoService.list()).getLista();
			}else {
				listaProjetoResponse = ((ListaProjetoResponse) projetoService.listSharing( jwtUsuarioDto.getIdCliente() )).getLista();
			}
			
			
			HashSet<ProjetoAgrupadoResponse> listaProjetosAgrupado = new HashSet<>();
			
			listaProjetoResponse.parallelStream().forEach( p->{
				
				ProjetoAgrupadoResponse projetoAgrupadoResponse = new ProjetoAgrupadoResponse();
				
				//projetoAgrupadoResponse.setId(p.getId());
				projetoAgrupadoResponse.setProjetoCliente(p.getCliente());

				listaProjetosAgrupado.add(projetoAgrupadoResponse);
				
			});
			
			ListaProjetoAgrupadoResponse listaProjetoAgrupadoResponse = new ListaProjetoAgrupadoResponse();
			
			listaProjetoAgrupadoResponse.setLista( new ArrayList<ProjetoAgrupadoResponse>(listaProjetosAgrupado) );
			
			//listaProjetoAgrupadoResponse.setLista(listaProjetosAgrupado.stream().toList());
			
			return listaProjetoAgrupadoResponse;
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos agrupado por cliente %s", e) );
			throw e;
		}
	}
	
	public Response list(String idCliente, String requestToken) throws Exception{
		
		try {
		
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
			
			List<ProjetoResponse> listaProjetoResponse = null;
			
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
				listaProjetoResponse = ((ListaProjetoResponse) projetoService.list(idCliente)).getLista();
			}else if(RoleEnum.ROLE_SHARING.getNome().equals( jwtUsuarioDto.getPerfil() )) {
				listaProjetoResponse = ((ListaProjetoResponse) projetoService.listSharing(jwtUsuarioDto.getIdCliente())).getLista();
			}else {
				listaProjetoResponse = ((ListaProjetoResponse) projetoService.list(jwtUsuarioDto.getIdCliente())).getLista();
			}
			
			PainelResponse painelResponse = new PainelResponse();
			
			painelResponse.setLista(listaProjetoResponse);
			
			int[] quantidade = recuperaQuantidade(listaProjetoResponse);
			
			painelResponse.setQuantidadeTotal( quantidade[0] );
			painelResponse.setQuantidadeDocumentoOk( quantidade[1] );
			
			return painelResponse;
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos %s", e) );
			throw e;
		}
	}
	
	private int[] recuperaQuantidade(List<ProjetoResponse> listaProjetoResponse) {
		
		try {
			AtomicInteger quantidadeTotalDocumento = new AtomicInteger(0);
			AtomicInteger quantidadeTotalDocumentoOK = new AtomicInteger(0);
			
			listaProjetoResponse
				.stream().forEach( p -> {
					
					if( p.getTipoDocumentacao() != null && p.getTipoDocumentacao().getDocumentos() != null ) {
						
						List<ProjetoDocumentoResponse> doc = p.getTipoDocumentacao().getDocumentos();
						
						quantidadeTotalDocumento.addAndGet( doc.size());
						
						quantidadeTotalDocumentoOK.addAndGet( 
							(int)doc.stream().filter( d->  SituacaoArquivoEnum.APROVADO.name().equals(d.getStatusArquito() ) ).count()
						);
					}
				} );
			
			return new int[] { quantidadeTotalDocumento.get(), quantidadeTotalDocumentoOK.get()};
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao setar quantidade documentos do projeto %s", e) );
			return  new int[] {0,0};
		}
		
	}

}
