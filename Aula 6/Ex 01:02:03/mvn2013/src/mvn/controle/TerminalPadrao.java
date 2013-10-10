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
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Classe que implementa o terminal de entrada e saída padrão.
 * 
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP (MVN 4.0)
 */
public class TerminalPadrao extends Terminal{
	
	/**
	 * Instancia um novo Terminal Padrão.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Um terminal é instanciado.
	 * 
	 * @param debug
	 */
	public TerminalPadrao(boolean debug){
		super(debug);
	}
	
	
	/**
	 * Define a entrada do terminal como sendo a entrada padrão.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Deve haver uma entrada padrão.<br/>
	 * <b>Pós-condição</b>: A entrada padrão é atribuída a este terminal.
	 * 
	 * @return Uma referência para o buffer de entrada do terminal.
	 */
	@Override
	protected BufferedReader setIn(){
		return new BufferedReader(new InputStreamReader(System.in));
	}
	
	
	/**
	 * Define a saída do terminal como sendo a saída padrão.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Deve haver uma saída padrão.<br/>
	 * <b>Pós-condição</b>: A saída padrão é atribuída a este terminal.
	 * 
	 * @return Uma referência para o buffer de saída do terminal.
	 */
	@Override
	protected PrintStream setOut(){
		return new PrintStream(System.out);
	}
	
	
	/**
	 * Define a saída de erro do terminal como sendo a saída de erro padrão.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Deve haver uma saída de erro padrão.<br/>
	 * <b>Pós-condição</b>: A saída de erro padrão é atribuída a este terminal.
	 * 
	 * @return Uma referência para o buffer de saída de erro do terminal.
	 */
	@Override
	protected PrintStream setErr(){
		return new PrintStream(System.err);
	}
	
}