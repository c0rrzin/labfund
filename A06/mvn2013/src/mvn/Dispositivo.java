/**
 * Escola Politecnica da Universidade de Sao Paulo
 * Departamento de Engenharia de Computacao e Sistemas Digitais
 * CopyrightÂ© 2001..2011, todos os direitos reservados.
 * 
 * Este programa e de uso exclusivo das disciplinas de Laboratorio de
 * Fundamentos de Engenharia de Computacao (PCS2024 e PCS2302) e Linguagens
 * e Compiladores (PCS2056 e PCS2508).
 * Eh vetada a utilizacao e/ou distribuicao deste codigo sem a autorizacao
 * dos docentes responsaveis pela disciplina ou do departamento responsavel.
 */
package mvn;

import mvn.controle.MVNException;

/**
 * Interface que representa um dispositivo da MVN.
 * 
 * @author PSMuniz
 * @author FLevy
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public interface Dispositivo{
	
	/*** Mensagem de erro: Dispositivo somente escrita. */
	public static final String	ERR_WRITEONLYDEVICE	= "Dispositivo \"%s\" disponÃ­vel somente para escrita.";
	
	/*** Mensagem de erro: Dispositivo somente leitura. */
	public static final String	ERR_READONLYDEVICE	= "Dispositivo \"%s\" disponÃ­vel somente para leitura.";
	
	
	/**
	 * Escreve um Bits8 no dispositivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>in</i> e nao nulo e o dispositivo permite gravacao.<br/>
	 * <b>Pos-condicao</b>: O valor e escrito no dispositivo.
	 * 
	 * @param in
	 *          O Bits8 a ser escrito.
	 * @throws MVNException
	 *           Caso haja um problema de entrada/saida ao escrever no
	 *           dispositivo.
	 */
	public void escrever(Bits8 in) throws MVNException;
	
	
	/**
	 * LÃª um Bits8 do dispositivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: Um Bits8 e lido do dispositivo.
	 * 
	 * @return O Bits8 lido.
	 * @throws MVNException
	 *           Caso haja um problema de entrada e saida ao escrever no
	 *           dispositivo.
	 */
	public Bits8 ler() throws MVNException;
	
	
	/**
	 * Verifica se o dispositivo esta habilitado para leitura.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Verifica se a leitura e permitida no dispositivo.
	 * 
	 * @return True caso seja possÃ­vel ler do dispositivo, False caso contrario.
	 */
	public boolean podeLer();
	
	
	/**
	 * Verifica se o dispositivo esta habilitado para escrita.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Verifica se a gravacao e permitida no dispositivo.
	 * 
	 * @return True caso seja possÃ­vel escrever no dispositivo, False caso
	 *         contrario.
	 */
	public boolean podeEscrever();
	
	
	/***************************************************************/
	/**
	 * Posiciona o cursor de leitura do dispositivo que pode ser lido em seu
	 * inicio.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: o buffer de leitura encontra-se na posição inicial.
	 * 
	 * @throws MVNException
	 *           Caso haja um problema de entrada e saida ao executar comando.
	 */
	public void reset() throws MVNException;
	
	
	/**
	 * Avança o cursor de leitura do dispositivo que pode ser lido em uma
	 * quantidade especifica de bytes.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: o cursor de leitura do dispositivo de leitura
	 * na posição indicada.
	 * 
	 * @param val
	 *          O numero de posiçoes a ser avançadas.
	 * @return numero de posiçoes avançadas.
	 * @throws MVNException
	 *           Caso haja um problema de entrada e saida ao executar comando.
	 */
	public Bits8 skip(Bits8 val) throws MVNException;
	
	
	/**
	 * Obtem o numero total de bytes lidos da unidade logica ate o momento.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: verifica número de bytes ja lidos.
	 * 
	 * @return numero de bytes ja lidos
	 * @throws MVNException
	 *           Caso haja um problema de entrada e saida ao executar comando.
	 */
	public Bits8 position() throws MVNException;
	
	
	/**
	 * Obtém o tamanho do arquivo associado a unidade logica, em bytes.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: verifica o tamanho do arquivo associado a unidade
	 * logica.
	 * 
	 * @return tamanho do arquivo associado a unidade logica, em bytes.
	 * @throws MVNException
	 *           Caso haja um problema de entrada e saida ao executar comando.
	 */
	public Bits8 size() throws MVNException;
}