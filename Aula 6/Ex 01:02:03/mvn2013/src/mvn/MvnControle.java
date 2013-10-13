/**
 * Escola Politécnica da Universidade de São Paulo
 * Departamento de Engenharia de Computação e Sistemas Digitais
 * Copyright© 2001..2011, todos os direitos reservados.
 * 
 * Este programa é de uso exclusivo das disciplinas de Laboratório de
 * Fundamentos de Engenharia de Computação (PCS2024 e PCS2302) e Linguagens
 * e Compiladores (PCS2056 e PCS2508).
 * É vetada a utilização e/ou distribuição deste código sem a autorização
 * dos docentes responsáveis pela disciplina ou do departamento responsável.
 */
package mvn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import mvn.controle.MVNException;

/**
 * Contém a classe MVN que define uma interface,
 * constantes e outros literais utilizados na
 * maquina.
 * 
 * @author PSMuniz
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - Humberto Sandmann - Refactory - PSI/EPUSP
 * @version 3.0 - PCS/EPUSP (MVN 4.0)
 */

public class MvnControle{
	
	/*** Menor endereço de memória permitido */
	private static final int				MIN_ADDRESS															= 0x0000;
	
	/*** Maior endereço de memória permitido */
	private static final int				MAX_ADDRESS															= 0x0FFF;
	
	/*** Nome do dispositivo: Teclado */
	private static final String			DEV_NAME_TECLADO												= "Teclado";														// Este
																																																									// nome
																																																									// deve
																																																									// ser
																																																									// igual
																																																									// ao
																																																									// da
																																																									// classe
																																																									
	/*** Nome do dispositivo: Monitor */
	private static final String			DEV_NAME_MONITOR												= "Monitor";														// Este
																																																									// nome
																																																									// deve
																																																									// ser
																																																									// igual
																																																									// ao
																																																									// da
																																																									// classe
																																																									
	/*** Nome do dispositivo: Impressora */
	private static final String			DEV_NAME_IMPRESSORA											= "Impressora";												// Este
																																																									// nome
																																																									// deve
																																																									// ser
																																																									// igual
																																																									// ao
																																																									// da
																																																									// classe
																																																									
	/*** Nome do dispositivo: Disco */
	private static final String			DEV_NAME_DISCO													= "Disco";															// Este
																																																									// nome
																																																									// deve
																																																									// ser
																																																									// igual
																																																									// ao
																																																									// da
																																																									// classe
																																																									
	/*** Dispositivos da MVN */
	public static final String[][]	DEVICES																	= new String[][]{
			new String[]{DEV_NAME_TECLADO},
			new String[]{DEV_NAME_MONITOR},
			new String[]{DEV_NAME_IMPRESSORA},
			new String[]{
			DEV_NAME_DISCO,
			"nome do arquivo",
			"modo de operação -> Leitura(" + mvn.dispositivo.Disco.MODO_LEITURA
					+ "), Escrita(" + mvn.dispositivo.Disco.MODO_ESCRITA
					+ ") ou Leitura e Escrita("
					+ mvn.dispositivo.Disco.MODO_LEITURAESCRITA + ")"}							};
	
	/*** Mensagem: Cabeçalho - Tipos de dispotivo disponíveis */
	private static final String			MSG_HEADER_TIPOSDISPOSITIVOSDISPONIVEIS	= "Tipos de dispositivos disponíveis:";
	
	/*** Mensagem de erro: Erro ao abrir o arquivo */
	private static final String			ERR_READ_FILE														= "Erro ao ler o arquivo (%s).";
	
	/*** Mensagem de erro: Erro na instrução */
	private static final String			ERR_PARSE_ERROR													= "Linha %d: Erro na instrução.";
	
	/*** Unidade de Controle da MVN */
	private UnidadeControle					cpu																			= null;
	
	/*** Memória da MVN */
	private Memoria									memoria																	= null;
	
	/*** Gerenciador de Dispositivos da MVN */
	private GerenciadorDispositivos	io																			= null;
	
	/*** Painel de Controle da MVN */
	// private PainelControle painel = null;
	
	/***
	 * Variável que indica se o conteúdo dos registradores deve ser adicionado à
	 * saída a cada ciclo de execução da MVN.
	 */
	private boolean									showRegs;
	
	/*** Objeto que armazena a saída da MVN, a ser encaminhada ao terminal. */
	private Appendable							MVNoutput;
	
	
	/**
	 * Construtor da Classe MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A MVN é instancializada.
	 */
	public MvnControle(){
		this.memoria = new Memoria(MIN_ADDRESS, MAX_ADDRESS);
		this.io = new GerenciadorDispositivos();
		this.cpu = new UnidadeControle(io, memoria);
	}
	
	
	/**
	 * Responsável por inicializar a MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A MVN é inicializada.
	 * 
	 * @param output
	 *          Classe descendente de Appendable onde a saída da inicialização da
	 *          MVN deve ser escrita.
	 * @throws MVNException
	 *           caso ocorra algum erro na inicialização.
	 */
	public void initialize(Appendable output) throws MVNException{
		// memoria.clear(); // Limpa a memória
		cpu.clearRegs(); // Limpa os registradores
		io.inicializa(output); // Inicia entrada/saida
	}
	
	
	/**
	 * Exportar em formato texto o conteúdo dos registradores
	 * da MVN, com cabeçalho.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um dump dos registradores é feito.
	 * 
	 * @return conteúdo dos registradores com cabeçalhos
	 */
	public String dumpRegistradores(){
		StringBuilder output = new StringBuilder(UnidadeControle.regsHeader());
		output.append(cpu.obterRegs().toString());
		return output.toString();
	}
	
	
	/**
	 * Retorna um trecho da memória em formato texto e hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O intervalo de início e fim da memória deve ser
	 * válido.<br/>
	 * <b>Pós-condição</b>: Um dump do intervalo especificado da memória é feito.
	 * 
	 * @param ini_address
	 *          endereço de início do trecho a ser retornado
	 * @param end_address
	 *          endereço de término do trecho a ser retornado
	 * @return conteúdo da memória em formato texto e hexadecimal
	 * @throws MVNException
	 *           Caso o intervalo de memória seja inválido.
	 */
	public String dumpMemoria(int ini_address, int end_address)
			throws MVNException{
		return memoria.dump(ini_address, end_address);
	}
	
	
	/**
	 * Carrega um arquivo em formato texto para a memória.<br/>
	 * Caso a memória seja alterada, o primeiro endereço alterado pelo arquivo
	 * é passado para o registrador de endereço de instrução (IC).<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O arquivo deve existir e estar no formato correto.<br/>
	 * <b>Pós-condição</b>: O conteúdo do arquivo é processado para a memória.
	 * 
	 * @param filename
	 *          nome do arquivo
	 * @throws MVNException
	 *           caso ocorra algum erro de execução
	 */
	public void loadFileToMemory(String filename) throws MVNException{
		try{
			// Cria um buffer de leitura para o arquivo texto
			BufferedReader inFile = new BufferedReader(new FileReader(filename));
			String line;
			
			int firstAddr = -1;
			
			// Itera sobre as linhas do arquivo, obtendo os endereços e executando o
			// parser
			int linhaAtual = 1;
			try{
				while((line = inFile.readLine()) != null){
					int addr = memoria.parseLine(line);
					if(firstAddr == -1){
						firstAddr = addr;
					}
					linhaAtual++;
				}
			}catch(MVNException e){
				throw new MVNException(ERR_PARSE_ERROR, linhaAtual);
			}
			
			if(firstAddr >= 0){
				cpu.obterRegs().getRegister(UnidadeControle.IC).setValue(firstAddr);
			}
		}catch(IOException e){
			throw new MVNException(ERR_READ_FILE, filename);
		}
	}
	
	
	/**
	 * Carrega um arquivo em formato texto para a memória.<br/>
	 * Caso a memória seja alterada, o primeiro endereço alterado pelo arquivo
	 * é passado para o registrador de endereço de instrução (IC).<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O arquivo deve existir e estar no formato correto.<br/>
	 * <b>Pós-condição</b>: O conteúdo do arquivo é processado para a memória.
	 * 
	 * @param filename
	 *          nome do arquivo
	 * @throws MVNException
	 *           caso ocorra algum erro de execução
	 */
	public void loadFileToMap(String filename) throws MVNException{
		try{
			// Cria um buffer de leitura para o arquivo texto
			BufferedReader inFile = new BufferedReader(new FileReader(filename));
			String line;
			
			int firstAddr = -1;
			
			// Itera sobre as linhas do arquivo, obtendo os endereços e executando o
			// parser
			Map<Word,Word> hash = new HashMap<Word, Word>();
			int linhaAtual = 1;
//			try{
				while((line = inFile.readLine()) != null){
					StringTokenizer dataLine = new StringTokenizer(line);
					while(dataLine.hasMoreTokens()) {
						System.out.printf("%s", dataLine.nextToken());
					}
					linhaAtual++;
				}
//			}catch(MVNException e){
//				throw new MVNException(ERR_PARSE_ERROR, linhaAtual);
//			}
			
			if(firstAddr >= 0){
				cpu.obterRegs().getRegister(UnidadeControle.IC).setValue(firstAddr);
			}
		}catch(IOException e){
			throw new MVNException(ERR_READ_FILE, filename);
		}
	}
	
	
	/**
	 * Adquire a lista de dispositivos disponível para MVN em formato texto.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Os dispositivos são listados pela MVN.
	 * 
	 * @return lista de dispositivos disponíveis para a MVN
	 */
	public String listDispositivos(){
		return io.toString();
	}
	
	
	/**
	 * Inicializa a MVN deixando-a pronta para execução.<br/>
	 * Este métodos apenas prepara a MVN, para que o código seja executado,
	 * chamados subsequentes ao método "resume" devem ser feitas.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>addr</i> deve ser um endereço válido.<br/>
	 * <b>Pós-condição</b>: A MVN é inicializada.
	 * 
	 * @param addr
	 *          Endereço da memória contendo o ponto de entrada do programa.
	 * @param showRegs
	 *          Indica se o estado dos registradores deve ser exibido a cada passo
	 *          da MVN.
	 * @param output
	 *          Instância do objeto que armazenará a saída da MVN.
	 * @throws MVNException
	 *           Caso não seja possível iniciar a MVN.
	 */
	public void start(int addr, boolean showRegs, Appendable output)
			throws MVNException{
		this.showRegs = showRegs;
		this.MVNoutput = output;
		cpu.start(addr);
		
		if(this.showRegs){
			outputInfo(UnidadeControle.regsHeader());
		}
	}
	
	
	/**
	 * Executa a instrução no endereço de memória atual da MVN (indicado pelo
	 * Registrador
	 * de Endereço de Instrução - IC).<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A MVN deve estar inicializada (método start()).<br/>
	 * <b>Pós-condição</b>: A instrução é processada e executada.
	 * 
	 * @return TRUE caso a MVN não tenha encontrado uma instrução de parada, FALSE
	 *         caso contrário.
	 * @throws MVNException
	 *           Caso ocorra algum problema no processamento da instrução.
	 */
	public boolean resume() throws MVNException{
		boolean stillrunning = cpu.resume();
		
		if(showRegs){
			outputInfo(cpu.obterRegs().toString());
		}
		
		return stillrunning;
	}
	
	
	/**
	 * Remove um dispositivo de I/O na MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O dispositivo indicado deve existir.<br/>
	 * <b>Pós-condição</b>: O dispositivo é removido.
	 * 
	 * @param tipo
	 *          especifica o tipo de dispositivo a ser removido
	 * @param unidadeLogica
	 *          unidade lógica do dispositivo
	 * @throws MVNException
	 *           Caso o dispositivo não exista.
	 */
	public void removeDispositivo(int tipo, int unidadeLogica)
			throws MVNException{
		io.removeDispositivo(tipo, unidadeLogica);
	}
	
	
	/**
	 * Retorna o menor endereço da memória.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O menor endereço da memória é retornado.
	 * 
	 * @return O menor endereço de memória.
	 */
	public int getMinAddress(){
		return MIN_ADDRESS;
	}
	
	
	/**
	 * Retorna o maior endereço da memória.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O maior endereço da memória é retornado.
	 * 
	 * @return O maior endereço da memória.
	 */
	public int getMaxAddress(){
		return MAX_ADDRESS;
	}
	
	
	/**
	 * Adiciona um dispositivo de I/O na MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O tipo e unidade lógica especificados são válidos.<br/>
	 * <b>Pós-condição</b>: O dispositivo indicado é adicionado.
	 * 
	 * @param tipo
	 *          especifica o tipo de dispositivo a ser adicinado
	 * @param unidadeLogica
	 *          unidade lógica do dispositivo
	 * @param params
	 *          Parâmetros do dispositivo que está sendo adicionado
	 * @throws MVNException
	 *           Caso não seja possível adicionar o dispositivo.
	 */
	public void addDispositivo(int tipo, int unidadeLogica, String[] params)
			throws MVNException{
		io.addDispositivo(tipo, unidadeLogica, "mvn.dispositivo."
				+ DEVICES[tipo][0], params);
	}
	
	
	/**
	 * Retorna uma lista dos dispositivos disponíveis.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma lista contendo os dispositivos disponíveis é
	 * retornada.
	 * 
	 * @return Uma String contendo os dispositivos disponíveis.
	 */
	public static String availableDevices(){
		StringBuilder out = new StringBuilder(
				MSG_HEADER_TIPOSDISPOSITIVOSDISPONIVEIS);
		out.append(System.getProperty("line.separator"));
		for(int i = 0; i < DEVICES.length; i++){
			out.append(String.format("   %-10s -> %d", DEVICES[i][0], i));
			out.append(System.getProperty("line.separator"));
		}
		return out.toString();
	}
	
	
	/**
	 * Retorna o endereço atual da MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A MVN deve ter sido inicializada.<br/>
	 * <b>Pós-condição</b>: O endereço atual da MVN, especificado pelo Registrador
	 * de Endereço de Instrução - IC.
	 * 
	 * @return O endereço atual da MVN.
	 */
	public String getCurrentAddress(){
		return cpu.obterRegs().getRegister(UnidadeControle.IC).toHexString();
	}
	
	
	/**
	 * Adiciona uma mensagem à saída da MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é adicionada.
	 * 
	 * @param txt
	 *          A mensagem a ser adicionada.
	 */
	private void outputInfo(String txt){
		if(MVNoutput != null){
			try{
				MVNoutput.append(txt);
			}catch(IOException e){
				// ignora esta exceção
			}
		}
	}
	
} // Fim da classe MVN