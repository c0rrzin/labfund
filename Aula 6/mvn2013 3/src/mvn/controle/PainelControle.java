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
package mvn.controle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import mvn.MvnControle;

/**
 * Classe responsável pela interface monitor/teclado com o usuário
 * 
 * @author Humberto Sandmann
 * @author Diego Queiroz
 * @author Luis Gustavo Nardin
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 * @version 2.0 - PCS/EPUSP (MVN 4.5)
 */
public class PainelControle{
	
	/*** Cabeçalho do Painel de Controle: Versão da MVN */
	private static final String			HEADER_TXTVERSION									= "MVN versão 4.5 (Agosto/2011)";
	
	/*** Cabeçalho do Painel de Controle: Universidade */
	private static final String			HEADER_TXTPOLI										= "Escola Politécnica da Universidade de São Paulo";
	
	/*** Cabeçalho do Painel de Controle: Descrição MVN */
	private static final String			HEADER_TXTMVN											= "PCS2302/PCS2024 - Simulador da Máquina de von Neumann";
	
	/*** Cabeçalho do Painel de Controle: Copyright */
	private static final String			HEADER_TXTCOPYRIGHT								= HEADER_TXTVERSION
																																				+ " - Todos os direitos reservados";
	
	/*** Ajuda do painel: Cabeçalho da tabela */
	private static final String			AJUDA_CABECALHO										= " COMANDO  PARÂMETROS           OPERAÇÃO";
	
	/*** Ajuda do painel: Separador */
	private static final String			AJUDA_SEPARADOR										= "---------------------------------------------------------------------------";
	
	/*** Ajuda do painel: Linha formatada */
	private static final String			AJUDA_LINHA												= "    %-4c  %-20s %s";
	
	/*** Comando do painel: inicializa */
	private static final char				CMD_INICIALIZA										= 'i';
	
	/*** Comando do painel: ascii */
	private static final char				CMD_ASCII													= 'p';
	
	/*** Comando do painel: executa */
	private static final char				CMD_EXECUTA												= 'r';
	
	/*** Comando do painel: debug */
	private static final char				CMD_DEBUG													= 'b';
	
	/*** Comando do painel: dispositivos */
	private static final char				CMD_DISPOSITIVOS									= 's';
	
	/*** Comando do painel: registradores */
	private static final char				CMD_REGISTRADORES									= 'g';
	
	/*** Comando do painel: memória */
	private static final char				CMD_MEMORIA												= 'm';
	
	/** Comando do painel: gabarito */
	private static final char 				CMD_GABARITO											= 't';
	
	/*** Comando do painel: ajuda */
	private static final char				CMD_AJUDA													= 'h';
	
	/*** Comando do painel: finaliza */
	private static final char				CMD_FINALIZA											= 'x';
	
	/*** Ajuda do painel: inicializa */
	private static final String[]		INICIALIZA												= new String[]{
			"", "Re-inicializa MVN"																				};
	
	/*** Ajuda do painel: ascii */
	private static final String[]		ASCII															= new String[]{
			"[arq]", "Carrega programa para a memória"										};
	
	/*** Ajuda do painel: executa */
	private static final String[]		EXECUTA														= new String[]{
			"[addr] [regs]", "Executa programa"														};
	
	/*** Ajuda do painel: debug */
	private static final String[]		DEBUG															= new String[]{
			"", "Ativa/Desativa modo Debug"																};
	
	/*** Ajuda do painel: dispositivos */
	private static final String[]		DISPOSITIVOS											= new String[]{
			"", "Manipula dispositivos de I/O"														};
	
	/*** Ajuda do painel: registradores */
	private static final String[]		REGISTRADORES											= new String[]{
			"", "Lista conteúdo dos registradores"												};
	
	/*** Ajuda do painel: memória */
	private static final String[]		MEMORIA														= new String[]{
			"[ini] [fim] [arq]", "Lista conteúdo da memória"							};
	
	private static final String[]       GABARITO													= new String[]{
		"[arq]", "Gabarito com enderecos e respectivos dados de saida esperados"
	};
	
	/*** Ajuda do painel: ajuda */
	private static final String[]		AJUDA															= new String[]{
			"", "Ajuda"																										};
	
	/*** Ajuda do painel: finaliza */
	private static final String[]		FINALIZA													= new String[]{
			"", "Finaliza MVN e terminal"																	};
	
	/*** Comando do painel: Lista de comandos */
	// private static final char[] MENU_CMD = new char[]{
	// CMD_INICIALIZA, CMD_ASCII, CMD_EXECUTA, CMD_DEBUG, CMD_LOADER,
	// CMD_DUMPER, CMD_DISPOSITIVOS, CMD_REGISTRADORES, CMD_MEMORIA, CMD_AJUDA,
	// CMD_FINALIZA };
	private static final char[]			MENU_CMD													= new char[]{
			CMD_INICIALIZA, CMD_ASCII, CMD_EXECUTA, CMD_DEBUG, CMD_DISPOSITIVOS,
			CMD_REGISTRADORES, CMD_MEMORIA, CMD_GABARITO, CMD_AJUDA, CMD_FINALIZA				};
	
	/*** Ajuda do painel: Lista das descrições */
	// private static final String[][] MENU_DESC = new String[][]{
	// INICIALIZA, ASCII, EXECUTA, DEBUG, LOADER, DUMPER, DISPOSITIVOS,
	// REGISTRADORES, MEMORIA, AJUDA, FINALIZA };
	private static final String[][]	MENU_DESC													= new String[][]{
			INICIALIZA, ASCII, EXECUTA, DEBUG, DISPOSITIVOS, REGISTRADORES, MEMORIA,
			GABARITO, AJUDA, FINALIZA																								};
	
	/*** Símbolo representativo da resposta afirmativa para uma questão */
	private static final char				LETTER_YES												= 's';
	
	/*** Símbolo representativo da resposta negativa para uma questão */
	private static final char				LETTER_NO													= 'n';
	
	/*** Símbolo representativo a opção ADICIONAR utilizado pelo painel */
	private static final char				ADICIONAR													= 'a';
	
	/*** Símbolo representativo a opção REMOVER utilizado pelo painel */
	private static final char				REMOVER														= 'r';
	
	/*** Mensagem: Programa carregado */
	private static final String			MSG_PROGRAMA_CARREGADO						= "Programa %s carregado";
	
	/*** Mensagem: Informe o endereço inicial */
	private static final String			MSG_INFORME_ENDERECO_INI					= "Informe o endereço inicial [%4H]: ";
	
	/*** Mensagem: Informe o endereço final */
	private static final String			MSG_INFORME_ENDERECO_FIM					= "Informe o endereço final [%4H]: ";
	
	/*** Mensagem: MVN Inicializada */
	private static final String			MSG_MVN_STARTED										= "MVN Inicializada";
	
	/*** Mensagem: Informe o endereço do IC */
	private static final String			MSG_PROMPT_ENDERECO_IC						= "Informe o endereco do IC [%s]: ";
	
	/*** Mensagem: Exibir valores dos registradores a cada passo */
	private static final String			MSG_PROMPT_EXIBIRREGS							= "Exibir valores dos registradores a cada passo do ciclo FDE ("
																																				+ LETTER_YES
																																				+ "/"
																																				+ LETTER_NO
																																				+ ")[%s]: ";
	
	/*** Mensagem: Executar passo a passo */
	private static final String			MSG_PROMPT_EXECUTA_PASSO_A_PASSO	= "Executar MVN passo a passo ("
																																				+ LETTER_YES
																																				+ "/"
																																				+ LETTER_NO
																																				+ ")[%s]: ";
	
	/*** Mensagem: Continua? */
	private static final String			MSG_PROMPT_CONTINUAR_EXECUCAO			= "Continua ("
																																				+ LETTER_YES
																																				+ "/"
																																				+ LETTER_NO
																																				+ ")[%s]: ";
	
	/*** Mensagem: Modo de depuração ativado */
	private static final String			MSG_DEBUG_ENABLED									= "Modo de depuração está ativado.";
	
	/*** Mensagem: Modo de depuração desativado */
	private static final String			MSG_DEBUG_DISABLED								= "Modo de depuração está desativado.";
	
	/*** Mensagem: Dispositivo removido. */
	private static final String			MSG_DISPOSITIVO_REMOVIDO					= "Dispositivo removido. [Tipo: %d - Unidade lógica: %d]";
	
	/*** Mensagem: Informe arquivo de entrada */
	private static final String			MSG_INFORME_NOME_ARQUIVO_ENTRADA	= "Informe o nome do arquivo de entrada: ";
	
	/*** Mensagem: Informe arquivo de saída */
	private static final String			MSG_INFORME_NOME_ARQUIVO_SAIDA		= "Informe o nome do arquivo de saida: ";
	
	/*** Mensagem: Persistir em arquivo */
	private static final String			MSG_GRAVA_ARQUIVO									= "Persistir em arquivo ("
																																				+ LETTER_YES
																																				+ "/"
																																				+ LETTER_NO
																																				+ ")[%s]: ";
	
	/*** Mensagem: Erro ao abrir arquivo */
	private static final String			MSG_ERRO_ABRIR_ARQUIVO						= "Erro ao abrir arquivo";
	
	/*** Mensagem: Erro ao gravar arquivo */
	private static final String			MSG_ERRO_GRAVAR_ARQUIVO						= "Erro ao gravar arquivo (%s)";
	
	/*** Mensagem: Terminal finalizado */
	private static final String			MSG_FINALIZED											= "Terminal encerrado.";
	
	/*** Mensagem: Adicionar ou remover dispositivo */
	private static final String			MSG_PROMPT_ALTERARDISPOSITIVO			= "Adicionar("
																																				+ ADICIONAR
																																				+ ") ou remover("
																																				+ REMOVER
																																				+ ") (ENTER para cancelar): ";
	
	/*** Mensagem: Entrar com tipo de dispositivo */
	private static final String			MSG_PROMPT_TIPODISPOSITIVO				= "Entrar com o tipo de dispositivo (ou ENTER para cancelar): ";
	
	/*** Mensagem: Entrar com a unidade lógica */
	private static final String			MSG_PROMPT_UNIDADELOGICA					= "Entrar com a unidade logica (ou ENTER para cancelar): ";
	
	/*** Mensagem do dispositivo: Digite o parâmetro X */
	private static final String			MSG_PROMPT_DEVICEPARAM						= "[%d] Digite o %s: ";
	
	/*** Mensagem do dispositivo: Dispositivo adicionado */
	private static final String			MSG_DISPOSITIVO_ADICIONADO				= "Dispositivo adicionado (Tipo: %d - Unidade lógica: %d)";
	
	/*** Erro do painel: Comando inválido */
	private static final String			ERR_INVALID_COMMAND								= "Comando inválido (%s). Digite \""
																																				+ CMD_AJUDA
																																				+ "\" para obter uma lista de comandos válidos.";
	
	/*** Erro do painel: Erro inesperado durante a execução do terminal */
	private static final String			ERR_EXECUCAO_TERMINAL							= "Erro inesperado durante a execução do terminal.";
	
	/*** Mensagem de erro: MVN não inicializada */
	private static final String			ERR_MVN_NOT_STARTED								= "ERRO - MVN NÃO inicializada";
	
	/*** Mensagem de erro: Tipo de dispositivo inválido */
	private static final String			ERR_TIPODISPOSITIVOINVALIDO				= "O tipo de dispositivo especificado é inválido (especifique um valor numérico).";
	
	/*** Mensagem de erro: Unidade lógica inválida */
	private static final String			ERR_UNIDADELOGICAINVALIDA					= "A unidade lógica especificada é inválida (especifique um valor numérico).";
	
	/** Instância da classe MvnControle **/
	private MvnControle							mvn																= null;
	
	/** Instância do classe Terminal **/
	private Terminal								terminal													= null;
	
	
	/**
	 * Contrutor da classe, recebe uma MVN para gerenciar com modo de depuração
	 * inicialmente inativo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>mvn</i> é não nulo.<br/>
	 * <b>Pós-condição</b>: O Painel de Controle da MVN especificada é
	 * instancializado.
	 * 
	 * @param mvn
	 *          MVN alvo do gerenciador, ao qual o Painel será acoplado.
	 */
	public PainelControle(MvnControle mvn){
		this(mvn, false);
	}
	
	
	/**
	 * Construtor da classe, recebe uma MVN para gerenciar.<br/>
	 * O estado do modo de depuração é dado pelo parâmetro debug.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>mvn</i> é não nulo.<br/>
	 * <b>Pós-condição</b>: O Painel de Controle da MVN especificada é
	 * instancializado.
	 * 
	 * @param mvn
	 *          MVN alvo do gerenciador, ao qual o Painel será acoplado.
	 * @param debug
	 *          Estado inicial do modo de depuração.
	 */
	public PainelControle(MvnControle mvn, boolean debug){
		this.mvn = mvn;
		this.terminal = new TerminalTeste(debug);
		
		initialize();
	}
	
	
	/**
	 * Exibe o controle de terminal para o operador.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O terminal é exibido.
	 */
	public void mostrarTerminal(){
		sobre();
		ajuda();
		
		boolean leTerminal = true;
		do{
			
			StringBuilder linhaComando = new StringBuilder(terminal.prompt().trim());
			char comando = linhaComando.charAt(0);
			linhaComando.delete(0, 1);
			
			try{
				leTerminal = executaComando(comando, linhaComando.toString().trim());
			}catch(MVNException e){
				terminal.erro(e);
			}catch(Exception e){
				terminal.erro(ERR_EXECUCAO_TERMINAL, e);
			}
		}while(leTerminal);
		terminal.exibeLinha(MSG_FINALIZED);
	}
	
	
	/**
	 * Exibe o cabeçalho da MVN no terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O cabeçalho da MVN é exibido.
	 */
	private void sobre(){
		terminal.pulaLinha();
		terminal.exibeLinhaCentralizada(HEADER_TXTPOLI);
		terminal.exibeLinhaCentralizada(HEADER_TXTMVN);
		terminal.exibeLinhaCentralizada(HEADER_TXTCOPYRIGHT);
	}
	
	
	/**
	 * Exibe as opçőes de comando disponíveis no terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A ajuda do painel é exibida no terminal.
	 */
	private void ajuda(){
		StringBuilder out = new StringBuilder();
		out.append(System.getProperty("line.separator"));
		out.append(AJUDA_CABECALHO);
		out.append(System.getProperty("line.separator"));
		out.append(AJUDA_SEPARADOR);
		for(int i = 0; i < MENU_CMD.length; i++){
			String[] descMenu = MENU_DESC[i];
			out.append(System.getProperty("line.separator"));
			out.append(String.format(AJUDA_LINHA, MENU_CMD[i], descMenu[0],
					descMenu[1]));
		}
		
		terminal.exibeLinha(out.toString());
	}
	
	
	/**
	 * Executa o comando de inicialização.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A MVN é inicializada.
	 */
	private void initialize(){
		try{
			StringBuilder initOutput = new StringBuilder();
			mvn.initialize(initOutput);
			terminal.exibeLinha(initOutput.toString());
			terminal.exibeLinha(MSG_MVN_STARTED);
		}catch(MVNException e){
			terminal.erro(ERR_MVN_NOT_STARTED, e);
		}
	}
	
	
	/**
	 * Abre o assistente para gerenciar dispositivos.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A MVN deve estar inicializada.<br/>
	 * <b>Pós-condição</b>: Exibe o assistente para gerenciamento dos
	 * dispositivos.
	 * 
	 * @throws MVNException
	 *           Caso ocorra algum erro na execuçăo do assistente
	 */
	public void dispositivos() throws MVNException{
		terminal.exibeLinha(mvn.listDispositivos());
		terminal.pulaLinha();
		
		char acao = terminal.obtem(MSG_PROMPT_ALTERARDISPOSITIVO, " ").charAt(0);
		if(acao != ADICIONAR && acao != REMOVER)
			return;
		
		terminal.exibe(MvnControle.availableDevices());
		
		String strTipo = terminal.obtem(MSG_PROMPT_TIPODISPOSITIVO, "");
		if(strTipo.isEmpty())
			return;
		
		int tipo;
		int unidadeLogica;
		
		try{
			tipo = Integer.parseInt(strTipo);
		}catch(NumberFormatException e){
			throw new MVNException(ERR_TIPODISPOSITIVOINVALIDO);
		}
		
		String strUnidadeLogica = terminal.obtem(MSG_PROMPT_UNIDADELOGICA, "");
		if(strUnidadeLogica.isEmpty())
			return;
		
		try{
			unidadeLogica = Integer.parseInt(strUnidadeLogica);
		}catch(NumberFormatException e){
			throw new MVNException(ERR_UNIDADELOGICAINVALIDA);
		}
		
		switch(acao){
			case REMOVER:
				removeDispositivo(tipo, unidadeLogica);
				break;
			case ADICIONAR:
				adicionaDispositivo(tipo, unidadeLogica);
				break;
		}
	}
	
	
	/**
	 * Remove o dispositivo especificado, de acordo com o tipo e unidade lógica.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O dispositivo deve existir.<br/>
	 * <b>Pós-condição</b>: O dispositivo é removido.
	 * 
	 * @param tipo
	 *          Tipo do dispositivo a ser removido
	 * @param unidadeLogica
	 *          Unidade lógica do dispositivo a ser removido
	 */
	private void removeDispositivo(int tipo, int unidadeLogica){
		try{
			mvn.removeDispositivo(tipo, unidadeLogica);
			terminal.exibeLinha(MSG_DISPOSITIVO_REMOVIDO, tipo, unidadeLogica);
		}catch(MVNException e){
			terminal.erro(e);
		}
	}
	
	
	/**
	 * Adiciona um dispositivo com base no tipo e unidade lógica especificados.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Não deve existir um dispositivo com o mesmo tipo e
	 * unidade lógica.<br/>
	 * <b>Pós-condição</b>: O dispositivo é adicionado ao gerenciador.
	 * 
	 * @param tipo
	 *          Tipo do dispositivo a ser adicionado
	 * @param unidadeLogica
	 *          Unidade lógica do dispositico a ser adicionado
	 */
	private void adicionaDispositivo(int tipo, int unidadeLogica){
		try{
			// pega os parâmetros necessários para o dispositivo especificado, se
			// houver
			String[] params = new String[MvnControle.DEVICES[tipo].length - 1];
			for(int i = 1; i < MvnControle.DEVICES[tipo].length; i++){
				if(!MvnControle.DEVICES[tipo][i].isEmpty()){
					params[i - 1] = terminal.obtem(MSG_PROMPT_DEVICEPARAM, "", i,
							MvnControle.DEVICES[tipo][i]);
				}
			}
			mvn.addDispositivo(tipo, unidadeLogica, params);
			terminal.exibeLinha(MSG_DISPOSITIVO_ADICIONADO, tipo, unidadeLogica);
		}catch(MVNException e){
			terminal.erro(e);
		}
	}
	
	
	/**
	 * Executa o comando de carregamento de programas em formato texto para
	 * memória.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <br/>
	 * <b>Pós-condição</b>:
	 * 
	 * @param args
	 *          Argumentos da linha de comando
	 * @throws MVNException
	 *           caso ocorra algum erro de execuçăo
	 */
	private void loadTextFiletoMemory(String args) throws MVNException{
		
		if(args == null || args.isEmpty()){
			args = terminal.obtem(MSG_INFORME_NOME_ARQUIVO_ENTRADA, "");
		}
		
		if(!args.isEmpty()){
			StringBuilder filename = new StringBuilder(args);
			
			// Remove quaisquer aspas que estejam delimitando o nome do arquivo
			while(args.charAt(0) == '"'
					&& filename.charAt(filename.length() - 1) == '"'){
				filename.deleteCharAt(0);
				filename.deleteCharAt(filename.length() - 1);
			}
			
			mvn.loadFileToMemory(filename.toString());
			terminal.exibeLinha(MSG_PROGRAMA_CARREGADO, filename.toString());
		}else{
			throw new MVNException(MSG_ERRO_ABRIR_ARQUIVO);
		}
	}
	
	/**
	 * Realiza comparacao entro o dado esperado e o dado real no respectivo(os) endereco(os) escritos no arquivo.<br/>
	 * <br/>
	 * <b>Pr�-condi��o</b>: <br/>
	 * <b>P�s-condi��o</b>:
	 * 
	 * @param args
	 *          Argumentos da linha de comando
	 * @throws MVNException
	 *           caso ocorra algum erro de execu�ao
	 */
	private void loadTextFileToCompare(String args) throws MVNException{
		
		if(args == null || args.isEmpty()){
			args = terminal.obtem(MSG_INFORME_NOME_ARQUIVO_ENTRADA, "");
		}
		
		if(!args.isEmpty()){
			StringBuilder filename = new StringBuilder(args);
			
			// Remove quaisquer aspas que estejam delimitando o nome do arquivo
			while(args.charAt(0) == '"'
					&& filename.charAt(filename.length() - 1) == '"'){
				filename.deleteCharAt(0);
				filename.deleteCharAt(filename.length() - 1);
			}
			
//			mvn.loadFileToMemory(filename.toString());t
			terminal.exibeLinha(MSG_PROGRAMA_CARREGADO, filename.toString());
		}else{
			throw new MVNException(MSG_ERRO_ABRIR_ARQUIVO);
		}
	}
	
	
	/**
	 * Realiza dump em tela ou arquivo da memória, em formato texto.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Faz um dump da memória.
	 * 
	 * @param args
	 *          [endereço de inicio] - Endereço da posiçăo inicial
	 *          [endereço de fim] - Endereço da posiçăo final
	 *          [nome do arquivo] - Arquivo para gravar conteúdo de memória em
	 *          formato texto
	 * @throws MVNException
	 *           caso ocorra algum erro de execuçăo
	 */
	private void dumpMemory(String[] args) throws MVNException{
		
		// Trata endereço inicial da memória
		String strAddrIni = parseParam(args, 0, MSG_INFORME_ENDERECO_INI,
				String.format("%4H", mvn.getMinAddress()));
		int add_ini = convertHex(strAddrIni, mvn.getMinAddress());
		
		String strAddrFim = parseParam(args, 1, MSG_INFORME_ENDERECO_FIM,
				String.format("%4H", mvn.getMaxAddress()));
		int add_fim = convertHex(strAddrFim, mvn.getMaxAddress());
		
		// Obtém o dumpping de memória em formato texto
		String dump = mvn.dumpMemoria(add_ini, add_fim);
		
		// Tratar persistência em arquivo
		String opcao = "";
		String arq = "";
		if(args.length > 2){
			arq = args[2];
		}else if(args.length == 0){
			opcao = terminal.obtem(MSG_GRAVA_ARQUIVO, String.valueOf(LETTER_NO),
					String.valueOf(LETTER_NO));
			if(opcao.length() > 0 && opcao.toLowerCase().charAt(0) == LETTER_YES){
				arq = terminal.obtem(MSG_INFORME_NOME_ARQUIVO_SAIDA, "");
			}
		}
		
		if(arq.length() > 0){
			try{
				gravaArquivoTexto(arq, dump);
			}catch(IOException e){
				throw new MVNException(MSG_ERRO_GRAVAR_ARQUIVO, e, arq);
			}
		}else{
			terminal.exibeLinha(dump);
		}
	}
	
	
	/**
	 * Grava um conteúdo de String em um arquivo externo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Deve-se ter permissões de gravação no disco.<br/>
	 * <b>Pós-condição</b>: O arquivo com o dump da memória é salvo no local
	 * indicado.
	 * 
	 * @param filename
	 *          nome do arquivo onde ser persistido o conteúdo
	 * @param conteudo
	 *          String com o valor a ser gravado
	 * @throws IOException
	 *           caso ocorra algum erro de execuçăo
	 */
	public void gravaArquivoTexto(String filename, String conteudo)
			throws IOException{
		// Cria um buffer de escrita, associando-o a um arquivo.
		PrintWriter dumpFile = new PrintWriter(new FileWriter(filename));
		// Converte o conteúdo do buffer em string e escreve no arquivo, no
		// formato especificado.
		dumpFile.print(conteudo);
		dumpFile.close();
	}
	
	
	/**
	 * Executa o comando de execuçăo de programas na memória.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Existe um programa carregado na memória.<br/>
	 * <b>Pós-condição</b>: O programa é executado a partir do ponto indicado.
	 * 
	 * @param args
	 *          argumentos da linha de comando
	 * @throws MVNException
	 *           caso ocorra algum erro de execuçăo
	 */
	private void executa(String[] args) throws MVNException{
		int addr = Integer.parseInt(
				parseParam(args, 0, MSG_PROMPT_ENDERECO_IC, mvn.getCurrentAddress()),
				16);
		boolean showRegs = Character.toLowerCase(parseParam(args, 1,
				MSG_PROMPT_EXIBIRREGS, String.valueOf(LETTER_YES)).charAt(0)) == Character
				.toLowerCase(LETTER_YES);
		boolean passByPass = showRegs ? Character.toLowerCase(parseParam(args, 2,
				MSG_PROMPT_EXECUTA_PASSO_A_PASSO, String.valueOf(LETTER_NO)).charAt(0)) == Character
				.toLowerCase(LETTER_YES) : false;
		
		StringBuilder outputBuffer = new StringBuilder();
		
		mvn.start(addr, showRegs, outputBuffer);
		
		boolean continueRunning;
		do{
			continueRunning = mvn.resume();
			
			if(outputBuffer.length() > 0){
				terminal.exibe(outputBuffer.toString());
				outputBuffer.delete(0, outputBuffer.length());
			}
			
			if(showRegs){
				if(passByPass){
					char opcao = terminal.obtem(MSG_PROMPT_CONTINUAR_EXECUCAO,
							String.valueOf(LETTER_YES), String.valueOf(LETTER_YES)).charAt(0);
					if(Character.toLowerCase(opcao) == Character.toLowerCase(LETTER_NO)){
						break;
					}
				}else{
					terminal.pulaLinha();
				}
			}
		}while(continueRunning);
		
	}
	
	
	/**
	 * Método auxiliar para transformaçăo de um valor de entrada em hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>input</i> é um número em formato hexadecimal.<br/>
	 * <b>Pós-condição</b>: O valor é convertido para o formato inteiro.
	 * 
	 * @param input
	 *          valor a transformar
	 * @param defaultValue
	 *          valor padrăo caso năo consigo realizar a operaçăo
	 * @return valor transformado
	 */
	private int convertHex(String input, int defaultValue){
		try{
			return Integer.parseInt(input, 16);
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	
	/**
	 * Extrai os argumentos de uma linha de comando.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>parametros</i> é não nulo.<br/>
	 * <b>Pós-condição</b>: Um vetor de Strings contendo os argumentos da linha de
	 * comando é retornado.
	 * 
	 * @param parametros
	 *          Conteúdo da linha de comando
	 * @return Um vetor de Strings contendo os argumentos passados na linha de
	 *         comando.
	 */
	private String[] ExtractArguments(String parametros){
		// Definindo elementos da linha de comando
		StringTokenizer token = new StringTokenizer(parametros);
		String[] args = new String[token.countTokens()];
		for(int i = 0; token.hasMoreElements(); i++){
			args[i] = (String) token.nextElement();
		}
		return args;
	}
	
	
	/**
	 * Executa o comando especificado com os parâmetros dados.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O comando é executado.
	 * 
	 * @param comando
	 *          Comando a ser executado (de acordo com a ajuda do painel)
	 * @param parametros
	 *          Parâmetros a serem passados ao comando (os parâmetros são
	 *          ignorados caso o comando não possua parâmetros.
	 * @return True caso o terminal não seja sido encerrado, False caso contrário.
	 * @throws MVNException
	 *           Caso seja passado um comando inválido.
	 */
	private boolean executaComando(char comando, String parametros)
			throws MVNException{
		switch(comando){
			case CMD_INICIALIZA:
				initialize();
				break;
			case CMD_ASCII:
				loadTextFiletoMemory(parametros);
				break;
			case CMD_EXECUTA:
				executa(ExtractArguments(parametros));
				break;
			case CMD_DEBUG:
				switchDebugMode();
				break;
			case CMD_DISPOSITIVOS:
				dispositivos();
				break;
			case CMD_REGISTRADORES:
				showRegisters();
				break;
			case CMD_MEMORIA:
				dumpMemory(ExtractArguments(parametros));
				break;
			case CMD_GABARITO:
				loadTextFileToCompare(parametros);
				break;
			case CMD_AJUDA:
				ajuda();
				break;
			case CMD_FINALIZA:
				return false;
			default:
				throw new MVNException(ERR_INVALID_COMMAND, comando);
		}
		return true;
	}
	
	
	/**
	 * Alterna o modo de depuração entre ativado e desativado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O modo de depuração está em qualquer estado.<br/>
	 * <b>Pós-condição</b>: O modo de depuração está no modo inverso ao que estava
	 * antes da invocação ao método.
	 */
	private void switchDebugMode(){
		terminal.setDebug(!terminal.getDebug());
		terminal.exibeLinha(terminal.getDebug() ? MSG_DEBUG_ENABLED
				: MSG_DEBUG_DISABLED);
	}
	
	
	/**
	 * Mostra os registradores da MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Os registradores são exibidos no terminal.
	 */
	private void showRegisters(){
		terminal.exibeLinha(mvn.dumpRegistradores());
	}
	
	
	/**
	 * Analisa os parâmetros da linha de comando e retorna o valor desejado
	 * caso o parâmetro procurado exista.<br/>
	 * Caso não exista, solicita o valor ao usuário.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>args</i> é não nulo e <i>paramIndex</i> >= 0<br/>
	 * <b>Pós-condição</b>: O valor do parâmetro, seja este lido da linha de
	 * comando ou digitado pelo usuário.
	 * 
	 * @param args
	 *          Argumentos da linha de comando
	 * @param paramIndex
	 *          Índice do argumento desejado (iniciado por zero)
	 * @param promptMsg
	 *          Mensagem a ser exibida caso seja necessário solicitar o valor ao
	 *          usuário.
	 * @param defaultValue
	 *          Valor padrão, caso o usuário não digite nenhum valor.
	 * @return O valor do parâmetro.
	 */
	private String parseParam(String[] args, int paramIndex, String promptMsg,
			String defaultValue){
		if(args.length > paramIndex){
			return args[paramIndex];
		}
		
		return terminal.obtem(promptMsg, defaultValue, defaultValue);
	}
}