/**
 * Escola Politecnica da Universidade de Sao Paulo
 * Departamento de Engenharia de Computacao e Sistemas Digitais
 * Copyright© 2001..2011, todos os direitos reservados.
 * 
 * Este programa e de uso exclusivo das disciplinas de Laboratorio de
 * Fundamentos de Engenharia de Computacao (PCS2024 e PCS2302) e Linguagens
 * e Compiladores (PCS2056 e PCS2508).
 * e vetada a utilizacao e/ou distribuicao deste codigo sem a autorizacao
 * dos docentes responsaveis pela disciplina ou do departamento responsavel.
 */
package mvn.dispositivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import mvn.Bits8;
import mvn.Dispositivo;
import mvn.controle.MVNException;

/**
 * Implementacao do dispositivo DISCO para a MVN, utilizada para ler arquivos
 * de unidades de armazenamento anexas à maquina local.
 * 
 * @author PSMuniz
 * @author FLevy
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public class Disco implements Dispositivo{
	
	/*** Erro de dispositivo: Arquivo nao encontrado */
	private static final String	ERR_FILENOTFOUND		= "Arquivo nao encontrado. Arquivo: %s.";
	
	/*** Erro de dispositivo: Erro de entrada/saida */
	private static final String	ERR_IOERROR					= "Erro ao ler do disco. Arquivo: %s.";
	
	/*** Simbolo que representa o modo de leitura de disco */
	public static final char		MODO_LEITURA				= 'l';
	
	/*** Simbolo que representa o modo de escrita de disco */
	public static final char		MODO_ESCRITA				= 'e';
	
	/*** Simbolo que representa o modo de leitura e escrita de disco */
	public static final char		MODO_LEITURAESCRITA	= 'b';
	
	/*** Modo de operacao: Invalido */
	private static final int		INVALIDO						= 0;
	
	/*** Modo de operacao: Leitura */
	private static final int		LEITURA							= 1;
	
	/*** Modo de operacao: Escrita */
	private static final int		ESCRITA							= 2;
	
	/*** Modo de operacao: Leitura e escrita */
	private static final int		LEITURAESCRITA			= 3;
	
	/*** Modo de operacao: Descricao dos modos de operacao */
	private final String[]			MODOS_OPERACAO			= new String[]{
			"desconhecido", String.valueOf(MODO_LEITURA),
			String.valueOf(MODO_ESCRITA), MODO_LEITURA + "/" + MODO_ESCRITA};
	
	/*** Arquivo anexado ao dispositivo */
	private File								arquivo;
	
	/*** Modo de operacao do objeto */
	private int									modoOperacao;
	
	/*** Fluxo de entrada de dados */
	private FileInputStream			inFile;
	
	/*** Fluxo de saida de dados */
	private FileOutputStream		outFile;
	
	
	/**
	 * Cria um disco obtendo informacoes de um arquivo.<br/>
	 * <b>Pre-condicao</b>: O arquivo deve existir e o modo de operacao
	 * especificado deve estar disponivel.<br/>
	 * <b>Pos-condicao</b>: O dispositivo e inicializado.
	 * 
	 * @param arquivo
	 *          O arquivo a ser usado como disco
	 * @param modoOperacao
	 *          O mode de operacao (leitura, escrita e leitura e escrita).
	 * @throws MVNException
	 *           Caso ocorra algum erro na inicializacao do dispositivo.
	 */
	public Disco(String arquivo, char modoOperacao) throws MVNException{
		switch(modoOperacao){
			case MODO_LEITURA:
				this.modoOperacao = LEITURA;
				break;
			case MODO_ESCRITA:
				this.modoOperacao = ESCRITA;
				break;
			case MODO_LEITURAESCRITA:
				this.modoOperacao = LEITURAESCRITA;
				break;
			default:
				this.modoOperacao = INVALIDO;
		}
		
		this.arquivo = new File(arquivo);
		outFile = null;
		inFile = null;
		
		initializeDevice();
	}
	
	
	/**
	 * Inicializa o dispositivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Caso o modo de operacao seja somente leitura, entao o
	 * arquivo deve existir.<br/>
	 * <b>Pos-condicao</b>: O arquivo e aberto. Caso este nao exista e o modo de
	 * operacao
	 * permita gravacao, entao o arquivo e criado.
	 * 
	 * @throws MVNException
	 *           Caso o arquivo nao exista e o modo de operacao seja somente
	 *           leitura
	 *           ou ocorra algum erro no acesso ao disco.
	 */
	private void initializeDevice() throws MVNException{
		try{
			if(!arquivo.exists()){
				if(podeEscrever()){
					arquivo.createNewFile();
				}else{
					throw new MVNException(ERR_FILENOTFOUND, arquivo.getName());
				}
			}
			
			inFile = podeLer() ? new FileInputStream(arquivo) : null;
			outFile = podeEscrever() ? new FileOutputStream(arquivo, true) : null;
			
		}catch(IOException e){
			throw new MVNException(ERR_IOERROR, arquivo.getName());
		}
	}
	
	
	/**
	 * Finaliza o dispositivo.<br/>
	 * Encerra o acesso à todos os recursos inicializados.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo esta inicializado.<br/>
	 * <b>Pos-condicao</b>: O dispositivo e finalizado.
	 * 
	 * @throws MVNException
	 *           Caso ocorra algum erro.
	 */
	private void finalizeDevice() throws MVNException{
		try{
			if(podeLer()){
				inFile.close();
			}
			if(podeEscrever()){
				outFile.close();
			}
		}catch(IOException e){
			throw new MVNException(ERR_IOERROR, arquivo.getName());
		}
	}
	
	
	/**
	 * Verifica se e possivel ler do arquivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: A partir do modo de operacao, verifica se e possivel
	 * ler.
	 * 
	 * @return True caso seja possivel ler, False caso contrario.
	 */
	@Override
	public boolean podeLer(){
		return modoOperacao == LEITURA || modoOperacao == LEITURAESCRITA;
	}
	
	
	/**
	 * Verifica se e possivel escrever no arquivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: A partir do modo de operacao, verifica se e possivel
	 * escrever.
	 * 
	 * @return True caso seja possivel escrever, False caso contrario.
	 */
	@Override
	public boolean podeEscrever(){
		return modoOperacao == ESCRITA || modoOperacao == LEITURAESCRITA;
	}
	
	
	/**
	 * Escreve um Bits8 no disco.<br/>
	 * Implementacao ineficiente, mas condizente com o ponto de vista conceitual.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Deve ser possivel escrever no arquivo.<br/>
	 * <b>Pos-condicao</b>: A informacao e salva no arquivo.
	 * 
	 * @param out
	 *          O Bits8 a ser escrito.
	 * @throws MVNException
	 *           Caso o arquivo nao possa ser escrito ou caso o modo de
	 *           operacao do disco nao permita escrita.
	 */
	@Override
	public void escrever(Bits8 out) throws MVNException{
		try{
			if(podeEscrever()){
				outFile.write(out.toInt());
				outFile.flush();
			}else{
				// modo de operacao inadequado
				throw new MVNException(ERR_READONLYDEVICE, this);
			}
		}catch(IOException e){
			throw new MVNException(ERR_IOERROR);
		}
	}
	
	
	/**
	 * Le um Bits8 do disco.<br/>
	 * <b>Pre-condicao</b>: Deve ser possivel ler do arquivo.<br/>
	 * <b>Pos-condicao</b>: A informacao e lida do arquivo.
	 * 
	 * @throws MVNException
	 *           Caso o arquivo nao possa ser lido ou caso o modo de
	 *           operacao do disco nao permita leitura.
	 */
	@Override
	public Bits8 ler() throws MVNException{
		try{
			if(podeLer()){
				int deviceData = inFile.read();
				return new Bits8(deviceData);
			}else{
				// modo de operacao inadequado
				throw new MVNException(ERR_WRITEONLYDEVICE, this);
			}
		}catch(IOException e){
			throw new MVNException(ERR_IOERROR, arquivo.getName());
		}
	}
	
	
	/**
	 * Retorna o nome do arquivo que o dispositivo esta acessando.<br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Retorna o nome do arquivo anexado ao dispositivo.
	 * 
	 * @return Uma String contendo o nome do arquivo.
	 */
	public String getFilename(){
		return arquivo.getName();
	}
	
	
	/**
	 * Destrutor do objeto.
	 * Libera os recursos em uso (fecha os fluxos de entrada e saida).<br/>
	 * <b>Pre-condicao</b>: O dispositivo deve ter sido inicializado.<br/>
	 * <b>Pos-condicao</b>: O dispositivo e finalizado.
	 * 
	 * @throws Throwable
	 */
	@Override
	protected void finalize() throws Throwable{
		// Remover essa funcao e pensar num metodo melhor
		// de gerenciar os arquivos. O GarbageCollector nao da garantias do
		// momento em que o objeto sera liberado e a chamada desse
		// metodo pode demorar mais que o esperado.
		try{
			finalizeDevice();
		}finally{
			super.finalize();
		}
	}
	
	
	/**
	 * Retorna o modo de operacao e o Path do arquivo.<br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Retorna uma String contendo dados sobre o dispositivo.
	 * 
	 * @return Uma String contendo o modo de operacao e o path do arquivo.
	 */
	@Override
	public String toString(){
		String operationMode = MODOS_OPERACAO[modoOperacao];
		String filePath = arquivo.getAbsolutePath();
		
		return String.format(" (%s - %s)", operationMode, filePath);
	}
	
	
	@Override
	public Bits8 position() throws MVNException{
		return null;
	}
	
	
	@Override
	public void reset() throws MVNException{
	}
	
	
	@Override
	public Bits8 size() throws MVNException{
		return null;
	}
	
	
	@Override
	public Bits8 skip(Bits8 val) throws MVNException{
		return null;
	}
} // Disco