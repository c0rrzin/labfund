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

/**
 * @author Humberto Sandmann
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
@SuppressWarnings("serial")
public class MVNException extends Throwable{
	
	/**
	 * Cria uma instância da classe MVNException com a mensagem nula.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 */
	public MVNException(){
	}
	
	
	/**
	 * Cria uma instância da classe MVNException com a mensagem especificada.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 * 
	 * @param message
	 *          Mensagem da exceção
	 */
	public MVNException(String message){
		super(message);
	}
	
	
	/**
	 * Cria uma instância da classe MVNException utilizando outra exceção como
	 * causa.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 * 
	 * @param causa
	 *          Exceção que gerou esta exceção
	 */
	public MVNException(Throwable causa){
		super(causa);
	}
	
	
	/**
	 * Cria uma instância da classe MVNException com a mensagem especificada e
	 * utilizando outra exceção como causa.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 * 
	 * @param mensagem
	 *          Mensagem da exceção
	 * @param causa
	 *          Exceção que gerou esta exceção
	 */
	public MVNException(String mensagem, Throwable causa){
		super(mensagem, causa);
	}
	
	
	/**
	 * Cria uma instância da classe MVNException com a mensagem especificada.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 * 
	 * @param mensagem
	 *          Mensagem da exceção
	 * @param args
	 *          Parâmetros para formatação da mensagem.
	 */
	public MVNException(String mensagem, Object... args){
		super(String.format(mensagem, args));
	}
	
	
	/**
	 * Cria uma instância da classe MVNException com a mensagem especificada e
	 * utilizando outra exceção como causa.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma instância da classe é criada.
	 * 
	 * @param mensagem
	 *          Mensagem da exceção
	 * @param causa
	 *          Exceção que gerou esta exceção
	 * @param args
	 *          Parâmetros para formatação da mensagem.
	 */
	public MVNException(String mensagem, Throwable causa, Object... args){
		super(String.format(mensagem, args), causa);
	}
}