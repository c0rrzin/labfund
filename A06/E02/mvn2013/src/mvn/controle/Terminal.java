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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Classe base que implementa o Terminal de saída da MVN.
 * 
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP (MVN 4.0)
 */
public abstract class Terminal{
	
	/*** String a ser exibida no prompt de comando */
	private static final String	PROMPT_STRING					= "> ";
	
	/*** Tamanho do terminal (utilizado para centralizar as mensagens) */
	private static final int		TERMINAL_WIDTH				= 80;
	
	/*** Mensagem de erro: Erro não especificado */
	private static final String	ERR_NAO_ESPECIFICADO	= "Erro não especificado.";
	
	/*** Mensagem de erro: Erro na entrada do terminal */
	private static final String	ERR_ENTRADA_TECLADO		= "Erro na entrada do terminal";
	
	/*** Buffer de entrada do terminal */
	private BufferedReader			in;
	
	/*** Buffer de saída do terminal */
	private PrintStream					out;
	
	/*** Buffer de saída de erro do terminal */
	private PrintStream					err;
	
	/*** Flag de ativação do terminal */
	private boolean							ativado;
	
	/*** Variável que define o estado do modo de depuração */
	private boolean							debug;
	
	
	/**
	 * Instancia o terminal com o modo de depuração no estado especificado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O terminal é instanciado.
	 * 
	 * @param debug
	 *          Estado inicial do modo de depuração.
	 */
	public Terminal(boolean debug){
		this.debug = debug;
		this.ativado = true;
		
		in = setIn();
		out = setOut();
		err = setErr();
	}
	
	
	/**
	 * Instancia o terminal com o modo de depuração desativado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O terminal é instanciado.
	 */
	public Terminal(){
		this(false);
	}
	
	
	/**
	 * Define o modo de depuração.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O modo de depuração está em qualquer estado.<br/>
	 * <b>Pós-condição</b>: O estado do modo de depuração é ativo caso o valor do
	 * parâmetro seja True, ou desativado caso contrário.
	 * 
	 * @param value
	 *          Novo estado do modo de depuração
	 */
	public void setDebug(boolean value){
		debug = value;
	}
	
	
	/**
	 * Retorna o estado do modo de depuração.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Retorna o modo de depuração.
	 * 
	 * @return O estado do modo de depuração.
	 */
	public boolean getDebug(){
		return debug;
	}
	
	
	/**
	 * Escreve uma quebra de linha no saída do terminal.<br/>
	 * O formato da quebra de linha depende do sistema operacional.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma quebra de linha é escrita no terminal.
	 */
	public void pulaLinha(){
		if(ativado)
			out.println();
	}
	
	
	/**
	 * Escreve uma mensagem formatada no terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é escrita no terminal.
	 * 
	 * @param mensagem
	 *          A mensagem a ser escrita.
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 */
	public void exibe(String mensagem, Object... args){
		if(ativado)
			out.print(String.format(mensagem, args));
	}
	
	
	/**
	 * Escreve uma mensagem formatada no terminal, seguida de uma quebra de linha.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é escrita no terminal seguida de uma quebra
	 * de linha.
	 * 
	 * @param mensagem
	 *          A mensagem a ser escrita.
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 */
	public void exibeLinha(String mensagem, Object... args){
		if(ativado){
			exibe(mensagem, args);
			pulaLinha();
		}
	}
	
	
	/**
	 * Escreve uma mensagem formatada no terminal, seguida de uma quebra de linha.<br/>
	 * O conteúdo é ajustado para ser exibido de forma centralizada na tela.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é escrita no terminal seguida de uma quebra
	 * de linha.
	 * 
	 * @param mensagem
	 *          A mensagem a ser escrita.
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 */
	public void exibeLinhaCentralizada(String mensagem, Object... args){
		if(ativado){
			String newMsg = String.format(mensagem, args);
			int leftSpaces = (TERMINAL_WIDTH - newMsg.length()) / 2;
			exibeLinha("%" + leftSpaces + "s%s", "", newMsg);
		}
	}
	
	
	/**
	 * Escreve uma mensagem formatada no terminal, através de saída de erro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é escrita no terminal através da saída de
	 * erro.
	 * 
	 * @param mensagem
	 *          A mensagem a ser escrita.
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 */
	public void erro(String mensagem, Object... args){
		if(ativado)
			err.println(String.format(mensagem, args));
	}
	
	
	/**
	 * Transcreve a mensagem da exceção passado como parâmetro para a saída de
	 * erro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem da exceção é transcrita no terminal através
	 * da saída de erro.
	 * 
	 * @param e
	 *          Exceção de onde a mensagem deve ser lida
	 */
	public void erro(Throwable e){
		if(ativado){
			if(e != null){
				if(debug){
					e.printStackTrace(err);
				}else{
					erro(e.getMessage());
				}
			}else{
				erro(ERR_NAO_ESPECIFICADO);
			}
		}
	}
	
	
	/**
	 * Escreve uma mensagem formatada no terminal seguida da transcrição da
	 * mensagem da exceção passado como parâmetro, através de saída de erro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem é escrita no terminal através da saída de
	 * erro.
	 * 
	 * @param mensagem
	 *          A mensagem a ser escrita.
	 * @param e
	 *          Exceção de onde a mensagem deve ser lida.
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 */
	public void erro(String mensagem, Throwable e, Object... args){
		if(ativado){
			erro(mensagem, args);
			erro(e);
		}
	}
	
	
	/**
	 * Lê uma mensagem do terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um valor é lido através do terminal.
	 * 
	 * @return O conteúdo lido.
	 */
	public synchronized String ler(){
		if(ativado){
			String s = "";
			try{
				s = in.readLine();
			}catch(IOException e){
				erro(ERR_ENTRADA_TECLADO, e);
			}
			return s;
		}
		return null;
	}
	
	
	/**
	 * Exibe uma mensagem formatada e aguarda uma entrada pelo terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A mensagem digitada é retornada, ou o valor
	 * especificado
	 * como padrão, caso nada tenha sido digitado.
	 * 
	 * @param mensagem
	 *          Mensagem a ser exibida.
	 * @param padrao
	 *          Valor padrão (caso nada tenha sido digitado)
	 * @param args
	 *          Parâmetros de formatação da mensagem.
	 * @return O valor digitado pelo usuário ou o valor padrão especificado.
	 */
	public String obtem(String mensagem, String padrao, Object... args){
		if(ativado){
			exibe(mensagem, args);
			String s = ler();
			if(s.length() == 0){
				s = padrao;
			}
			return s;
		}
		return null;
	}
	
	
	/**
	 * Exibe o prompt do terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O prompt é exibido.
	 * 
	 * @return A linha de comando digitada no prompt
	 */
	public String prompt(){
		if(ativado){
			String conteudo;
			
			do{
				pulaLinha();
				conteudo = obtem(PROMPT_STRING, "");
			}while(conteudo.length() == 0);
			
			return conteudo;
		}
		return null;
	}
	
	
	public void desativa(){
		ativado = false;
	}
	
	
	public void ativa(){
		ativado = true;
	}
	
	
	/**
	 * Método abstrato. Define a entrada do terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um buffer de entrada é atribuído a este terminal.
	 * 
	 * @return Uma referência para um buffer de entrada a ser utilizado pelo
	 *         terminal.
	 */
	protected abstract BufferedReader setIn();
	
	
	/**
	 * Método abstrato. Define a saída do terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um buffer de saída é atribuído a este terminal.
	 * 
	 * @return Uma referência para um buffer de saída a ser utilizado pelo
	 *         terminal.
	 */
	protected abstract PrintStream setOut();
	
	
	/**
	 * Método abstrato. Define a saída de erro do terminal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um buffer de saída de erro é atribuído a este
	 * terminal.
	 * 
	 * @return Uma referência para um buffer de saída de erro a ser utilizado pelo
	 *         terminal.
	 */
	protected abstract PrintStream setErr();
}