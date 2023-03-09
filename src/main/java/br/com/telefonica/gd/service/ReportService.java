package br.com.telefonica.gd.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import br.com.telefonica.gd.dto.DocumentoPorMesDto;
import br.com.telefonica.gd.enums.SituacaoArquivoEnum;
import br.com.telefonica.gd.model.NotificacaoModel;
import br.com.telefonica.gd.model.ProjetoDocumentoModel;
import br.com.telefonica.gd.model.ProjetoModel;
import br.com.telefonica.gd.repository.ProjetoRepository;
import br.com.telefonica.gd.request.ReportRequest;
import br.com.telefonica.gd.response.DocPorMesResponse;
import br.com.telefonica.gd.response.DocumentoStatusResponse;
import br.com.telefonica.gd.response.ProjetoPorClienteResponse;
import br.com.telefonica.gd.response.ProjetoPorLocalidadeResponse;
import br.com.telefonica.gd.util.DataUtil;

@Service
public class ReportService {
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private DataUtil dataUtil;
	
	private static final Logger logger = LogManager.getLogger(ReportService.class);

	
	public InputStreamResource export( ReportRequest reportRequest) throws Exception{
		
		try {
			
			if(Strings.isNullOrEmpty(reportRequest.getIdCliente()) || 
					reportRequest.getDataPesquisa() == null || reportRequest.getDataPesquisa().length > 2) {
				throw new Exception("Erro falta dos campos de filtro");
			}
			
			LocalDateTime _dataInicial = dataUtil.formataData(reportRequest.getDataPesquisa()[0] + " 00:00:00");
			LocalDateTime _dataFinal = dataUtil.formataData(reportRequest.getDataPesquisa()[1] + " 23:59:59");
			
			
			List<ProjetoModel> listProjetoModel = projetoRepository.findByClienteIdAndDataCadastroBetween(
					reportRequest.getIdCliente(), _dataInicial, _dataFinal);

			return new InputStreamResource(this.geraExcel(listProjetoModel,
					reportRequest.getDataPesquisa()[0], 
					reportRequest.getDataPesquisa()[1]));

		}catch (Exception e) {
			logger.error( String.format("Erro no report %s", e) );
			throw e;
		}
		
	}
	
	private void cabecalho(Workbook workbook, Row _rowCabecalho ) {
		
		CellStyle cs = cabecalhoCellStyle(workbook);
			
		Cell _cellCabecalhoProjeto = _rowCabecalho.createCell(0);
		_cellCabecalhoProjeto.setCellStyle(cs);
        _cellCabecalhoProjeto.setCellValue("Projeto");
        
        Cell _cellCabecalhoProjCid = _rowCabecalho.createCell(1);
        _cellCabecalhoProjCid.setCellStyle(cs);
        _cellCabecalhoProjCid.setCellValue("Cidade Projeto");
        
        
        Cell _cellCabecalhoProjEst = _rowCabecalho.createCell(2);
        _cellCabecalhoProjEst.setCellStyle(cs);
        _cellCabecalhoProjEst.setCellValue("Estado Projeto");
        
        Cell _cellCabecalhoProjEnde = _rowCabecalho.createCell(3);
        _cellCabecalhoProjEnde.setCellStyle(cs);
        _cellCabecalhoProjEnde.setCellValue("Endereço Projeto");
        
        Cell _cellCabecalhoProjData = _rowCabecalho.createCell(4);
        _cellCabecalhoProjData.setCellStyle(cs);
        _cellCabecalhoProjData.setCellValue("Data Cadastro Projeto");
        
        
        Cell _cellCabecalhoCli = _rowCabecalho.createCell(5);
        _cellCabecalhoCli.setCellStyle(cs);
        _cellCabecalhoCli.setCellValue("Cliente");
        
        Cell _cellCabecalhoCliCid = _rowCabecalho.createCell(6);
        _cellCabecalhoCliCid.setCellStyle(cs);
        _cellCabecalhoCliCid.setCellValue("Cidade Cliente");
        
        Cell _cellCabecalhoCliEst = _rowCabecalho.createCell(7);
        _cellCabecalhoCliEst.setCellStyle(cs);
        _cellCabecalhoCliEst.setCellValue("Estado Cliente");
        
        Cell _cellCabecalhoCliSeg = _rowCabecalho.createCell(8);
        _cellCabecalhoCliSeg.setCellStyle(cs);
        _cellCabecalhoCliSeg.setCellValue("Segmento Cliente");
        
        
        Cell _cellCabecalhoTpDoc = _rowCabecalho.createCell(9);
        _cellCabecalhoTpDoc.setCellStyle(cs);
        _cellCabecalhoTpDoc.setCellValue("Tipo Documento");

        Cell _cellCabecalhoDocNome = _rowCabecalho.createCell(10);
        _cellCabecalhoDocNome.setCellStyle(cs);
        _cellCabecalhoDocNome.setCellValue("Nome Documento");
        
        Cell _cellCabecalhoArquivotatus = _rowCabecalho.createCell(11);
        _cellCabecalhoArquivotatus.setCellStyle(cs);
        _cellCabecalhoArquivotatus.setCellValue("Status Documento");
        
        Cell _cellCabecalhoDocCadastro = _rowCabecalho.createCell(12);
        _cellCabecalhoDocCadastro.setCellStyle(cs);
        _cellCabecalhoDocCadastro.setCellValue("Data Cadastro");
        
        Cell _cellCabecalhoDocUpload = _rowCabecalho.createCell(13);
        _cellCabecalhoDocUpload.setCellStyle(cs);
        _cellCabecalhoDocUpload.setCellValue("Data Upload");
		
        Cell _cellCabecalhoDocAprovacao = _rowCabecalho.createCell(14);
        _cellCabecalhoDocAprovacao.setCellStyle(cs);
        _cellCabecalhoDocAprovacao.setCellValue("Data Aprovação");
		
        Cell _cellCabecalhoDocRejeicao = _rowCabecalho.createCell(15);
        _cellCabecalhoDocRejeicao.setCellStyle(cs);
        _cellCabecalhoDocRejeicao.setCellValue("Data Rejeição");
        
        Cell _cellCabecalhoDocHistorico = _rowCabecalho.createCell(16);
        _cellCabecalhoDocHistorico.setCellStyle(cs);
        _cellCabecalhoDocHistorico.setCellValue("Histórico");
        
        Cell _cellCabecalhoDocNotificacao = _rowCabecalho.createCell(17);
        _cellCabecalhoDocNotificacao.setCellStyle(cs);
        _cellCabecalhoDocNotificacao.setCellValue("Notificações");

	}
	
	public CellStyle inicioCellStyle(Workbook workbook) {
	       
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontName("Courier New");
        font.setBold(true);
        font.setColor(HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short)225);
        style.setFont(font);
        
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        return style;
    }

	public CellStyle cabecalhoCellStyle(Workbook workbook) {
       
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setFontName("Courier New");
        font.setBold(true);
        font.setColor(HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short)170);
        style.setFont(font);
         
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        return style;
    }
	
	public int setValorCelula(XSSFSheet sheet, int linha, ProjetoModel p) {
        
        if( p.getProjetoTipoDocumentacao() != null ) {
  	
        	List<ProjetoDocumentoModel> docs = p.getProjetoTipoDocumentacao().getDocumentos();
        	
        	if( docs != null ) {

        		for(ProjetoDocumentoModel d : docs  ) {
        			
        			if( d != null ) {
        				
        				Row row = sheet.createRow(linha);
        				
        				Cell cellNome = row.createCell(0);
        				cellNome.setCellValue(p.getNome());
        		        
        		        Cell cellCidade = row.createCell(1);
        		        cellCidade.setCellValue(p.getCidade());
        		        
        		        Cell cellEstado = row.createCell(2);
        		        cellEstado.setCellValue(p.getEstado());

        		        Cell cellEndereco = row.createCell(3);
        		        cellEndereco.setCellValue(p.getEndereco());
        		        
        		        Cell cellDtCadastro = row.createCell(4);
        		        cellDtCadastro.setCellValue( dataUtil.formataData( p.getDataCadastro()));
        		        
        		        
        		        if( p.getCliente() != null ) {
        		        	
        		        	Cell cellCli = row.createCell(5);
        		        	cellCli.setCellValue(p.getCliente().getRazaoSocial());
        		        	
        		        	Cell cellCliCid = row.createCell(6);
        		        	cellCliCid.setCellValue(p.getCliente().getCidade());
        		        	
        		        	Cell cellCliEst = row.createCell(7);
        		        	cellCliEst.setCellValue(p.getCliente().getEstado());
        		        	
        		        	Cell cellCliSeg = row.createCell(8);
        		        	cellCliSeg.setCellValue(p.getCliente().getSegmento());
        		        	
        		        }

        		        
        		        Cell cellTpDoc = row.createCell(9);
        	        	cellTpDoc.setCellValue(p.getProjetoTipoDocumentacao().getNome());
        				
	        			Cell cellDoc = row.createCell(10);
	        			cellDoc.setCellValue(d.getNome());
	        			
	        			Cell cellArquivoStatus = row.createCell(11);
	        			cellArquivoStatus.setCellValue(d.getStatusArquito());
	        			
	        			Cell cellDocDat = row.createCell(12);
	        			cellDocDat.setCellValue( dataUtil.formataData( d.getDataCadastro() ));
	        			
	        			Cell cellDocUpload = row.createCell(13);
	        			cellDocUpload.setCellValue( dataUtil.formataData(d.getDataUpload()));
	        			
	        			Cell cellDocAprov = row.createCell(14);
	        			cellDocAprov.setCellValue(dataUtil.formataData( d.getDataAprovacao()));
	        			
	        			Cell cellDocRejei = row.createCell(15);
	        			cellDocRejei.setCellValue(dataUtil.formataData(d.getDataRejeicao()));
	        			
	        			Cell cellDocHist = row.createCell(16);
	        			if( d.getHistorico() != null && !d.getHistorico().isEmpty() ) {
	        				cellDocHist.setCellValue( String.join(", ", d.getHistorico()));
	        			}else {
	        				cellDocHist.setCellValue("");
	        			}
	        				
	        			Cell cellDocNotif = row.createCell(17);
	        			cellDocNotif.setCellValue( converteNotificicacao( d.getNotificacoes()));
	                	
	        			linha++;
        				row = sheet.createRow(linha);
        			}
        		}
        		   	
        	}
        }
        return linha;
	}

	private String converteNotificicacao(List<NotificacaoModel> listaNotificacaoes) {
		
		if( listaNotificacaoes == null || listaNotificacaoes.isEmpty() ) {
			return "";
		}
		
		List<String> list = new ArrayList<>();
		
		listaNotificacaoes.parallelStream().forEach( n->{
			list.add( "Autor: "+n.getAutor() + " Mensagem: "+ n.getMensagem() );
		});
		
		return String.join(", ", list);
	}
	
	public ByteArrayInputStream geraExcel( List<ProjetoModel> listaProjeto , String dataInicio, String dataFinal) throws Exception {
			
		XSSFWorkbook workbook = new XSSFWorkbook();
		  
        XSSFSheet sheet = workbook.createSheet("Projeto");
        
        int firstRow = 0;
        int lastRow = 1;
        int firstCol = 0;
        int lastCol = 17;
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        
       
        int rownum = 0;
              
        Row _row = sheet.createRow(rownum);
        
        Cell _cell = _row.createCell(0);
                
        _cell.setCellStyle(inicioCellStyle(workbook));
        
        String titulo = "Projetos, data do relatório "+ dataInicio + " até "+ dataFinal;
        if(listaProjeto != null && !listaProjeto.isEmpty() ) {
        	titulo = "Projetos do Cliente "+listaProjeto.get(0).getCliente().getRazaoSocial() +" data do relatório "+ dataInicio + " até "+ dataFinal;
        }
        	
    	_cell.setCellValue( titulo );
    
        rownum = rownum + 2;
        
        Row _rowCabecalho = sheet.createRow(rownum++);
        
        cabecalho(workbook, _rowCabecalho);
           
        AtomicInteger _rownum = new AtomicInteger(rownum++);
        
        if(listaProjeto != null) {
        	
        	listaProjeto.stream().forEach(p->{
        			
        		int posicao = setValorCelula(sheet, _rownum.getAndIncrement() , p);
        		
        		_rownum.set( posicao  );
        		
        	});
        }
        
        autoSizeColumn(sheet);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        out.close();
        
        return new ByteArrayInputStream(out.toByteArray());
        
	      
	}
	
	private void autoSizeColumn( XSSFSheet sheet){
		
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        sheet.autoSizeColumn(10);
        sheet.autoSizeColumn(11);
        sheet.autoSizeColumn(12);
        sheet.autoSizeColumn(13);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(15);
        sheet.autoSizeColumn(16);
        sheet.autoSizeColumn(17);
		
	}

	
	public DocumentoStatusResponse list( ReportRequest reportRequest) {
		
		try {
			
			DocumentoStatusResponse response = new DocumentoStatusResponse();

			if(Strings.isNullOrEmpty(reportRequest.getIdCliente()) || 
					reportRequest.getDataPesquisa() == null || reportRequest.getDataPesquisa().length > 2) {
				return response;
			}
			
			LocalDateTime _dataInicial = dataUtil.formataData(reportRequest.getDataPesquisa()[0] + " 00:00:00");
			LocalDateTime _dataFinal = dataUtil.formataData(reportRequest.getDataPesquisa()[1] + " 23:59:59");
			
			
			List<ProjetoModel> listProjetoModel = projetoRepository.findByClienteIdAndDataCadastroBetween(
					reportRequest.getIdCliente(), _dataInicial, _dataFinal);

			if( listProjetoModel == null || listProjetoModel.isEmpty() ) {
				return response;
			}
			
			
			HashMap<String, DocumentoPorMesDto> docMes = new HashMap<>();	
			HashMap<String, Integer> projetoLocal = new HashMap<>();
			HashMap<String, Integer> projetoCliente = new HashMap<>();
			
			
			listProjetoModel.parallelStream()
			.forEach( p ->{
				if( p.getProjetoTipoDocumentacao() != null && p.getProjetoTipoDocumentacao().getDocumentos() != null ) {
					
					if( p.getEstado() != null ) {
						String uf = p.getEstado();
						if( projetoLocal.containsKey( uf ) ) {
							projetoLocal.put( uf,  projetoLocal.get( uf ).intValue() + 1 );
						}else {
							projetoLocal.put( uf , 1);
						}
					}
					
					if( p.getCliente() != null && p.getCliente().getRazaoSocial() != null ) {
						String nomeEmpresa = p.getCliente().getRazaoSocial();
						if( projetoCliente.containsKey( nomeEmpresa ) ) {
							projetoCliente.put( nomeEmpresa,  projetoCliente.get( nomeEmpresa ).intValue() + 1 );
						}else {
							projetoCliente.put( nomeEmpresa , 1);
						}
					}
					
					//System.out.println("QUANTI " + p.getProjetoTipoDocumentacao().getDocumentos().size());
					
					p.getProjetoTipoDocumentacao().getDocumentos().parallelStream()
					.forEach( d ->{
						
						/*
						System.out.println("status "+ d.getStatusArquito());
						System.out.println("dataaprovado "+ d.getDataAprovacao());
						System.out.println("dataanalise "+ d.getDataUpload());
						System.out.println("datapendente "+ d.getDataCadastro());
						*/
						
						if( SituacaoArquivoEnum.APROVADO.name().equals( d.getStatusArquito())) {
							
							String mes = dataUtil.recuperaMesAno(d.getDataAprovacao());
							if( docMes.containsKey(mes) ) {
								
								DocumentoPorMesDto documentoPorMesDto = docMes.get(mes) ;
								documentoPorMesDto.setFinalizado( documentoPorMesDto.getFinalizado() + 1 );
								
								docMes.put(mes,  documentoPorMesDto );
							}else {
								docMes.put(mes, new DocumentoPorMesDto(0,0,1));
							}
								
						}else if( SituacaoArquivoEnum.ANALISE.name().equals( d.getStatusArquito())) {
							
							String mes = dataUtil.recuperaMesAno(d.getDataUpload());
							
							if( docMes.containsKey(mes) ) {
								DocumentoPorMesDto documentoPorMesDto = docMes.get(mes) ;
								documentoPorMesDto.setAnalise( documentoPorMesDto.getAnalise() + 1 );
								
								docMes.put(mes,  documentoPorMesDto );
							}else {
								docMes.put(mes, new DocumentoPorMesDto(0,1,0));
							}
							
						}else if( SituacaoArquivoEnum.PENDENTE.name().equals( d.getStatusArquito())) {
							
							System.out.println( d.getDataCadastro() );
							
							String mes = dataUtil.recuperaMesAno(d.getDataCadastro());
							if( docMes.containsKey(mes) ) {
								
								DocumentoPorMesDto documentoPorMesDto = docMes.get(mes) ;
								documentoPorMesDto.setPendente(documentoPorMesDto.getPendente() + 1);
								
								docMes.put(mes,  documentoPorMesDto );
							}else {
								docMes.put(mes, new DocumentoPorMesDto(1,0,0));
							}
						}
						
					} );
					
				}
				
			} );
			
			
			int qdtDocAnalise = 0;
			int qdtDocFinalizado = 0;
			int qdtDocPendente = 0;
			
			List<DocPorMesResponse> listDocPorMes = new ArrayList<>();
			
			for (String key : docMes.keySet()) {
		        
		        DocumentoPorMesDto docPorMesDto  = docMes.get(key);
		        
		        qdtDocAnalise = qdtDocAnalise + docPorMesDto.getAnalise();
				qdtDocFinalizado = qdtDocFinalizado + docPorMesDto.getFinalizado();
				qdtDocPendente = qdtDocPendente + docPorMesDto.getPendente();
		        
		        listDocPorMes.add( new DocPorMesResponse( key, 
		        		docPorMesDto.getPendente(), docPorMesDto.getAnalise(), docPorMesDto.getFinalizado() ) );
		        
		    }
			
			response.setDocMes(listDocPorMes);	
			
			response.setQuantidadeDocAnalise(qdtDocAnalise);
			response.setQuantidadeDocFinalizado(qdtDocFinalizado);
			response.setQuantidadeDocPendente(qdtDocPendente);
			
			
			List<ProjetoPorClienteResponse> listProjetoPorCliente = new ArrayList<>();
			projetoCliente.forEach((key, value) -> {
				listProjetoPorCliente.add( new ProjetoPorClienteResponse( key, value ) );
			});
			response.setProjetoCliente(listProjetoPorCliente);
			
			
			List<ProjetoPorLocalidadeResponse> listProjetoPorLocalidade = new ArrayList<>();
			projetoLocal.forEach((key, value) -> {
				listProjetoPorLocalidade.add( new ProjetoPorLocalidadeResponse( key, value ) );
			});
			response.setProjetoLocalidade(listProjetoPorLocalidade);
				
			return response;
			
		}catch (Exception e) {
			logger.error( String.format("Erro no report %s", e) );
			throw e;
		}
	}
	
 
	public static void main(String[] args) {
		
	
		System.out.println( Short.MAX_VALUE  );
		
	}
		 
	
}
