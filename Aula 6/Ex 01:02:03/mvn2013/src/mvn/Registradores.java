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

/**
 * Classe que implementa os Registradores.
 * 
 * @author Jonathan Riddell
 * @author PSMuniz
 * @author Diego Queiroz
 * @version 1.0 - 2001
 * @version 2.0 - junho/2005
 *          Versao inicial. Existem vários metodos que não são utilizados.
 * @version 3.0 - PCS/EPUSP (MVN 4.0)
 */

public class Registradores{
	
	/*** Vetor que armazena o conteúdo dos registradores */
	private Word[]	regs;
	
	
	/**
	 * Instancia os registradores.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Os registradores são instanciados.
	 * 
	 * @param numRegisters
	 *          Quantidade de registradores a serem criados.
	 */
	public Registradores(int numRegisters){
		createRegs(numRegisters);
	}
	
	
	/**
	 * Aloca na memória a quantidade de registradores especificada.<br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A quantidade de registradores especificados são
	 * alocados na memória.
	 * 
	 * @param numRegs
	 *          A quantidade de registradores a serem alocados.
	 */
	private void createRegs(int numRegs){
		regs = new Word[numRegs];
		for(int i = 0; i < numRegs; i++){
			regs[i] = new Word();
		}
	}
	
	
	/**
	 * Zera os registradores.<br/>
	 * <b>Pré-condição</b>: Os registradores contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: Todos os registradores estáo com o valor 0.
	 */
	public void clear(){
		for(int i = 0; i < regs.length; i++){
			regs[i].clear();
		}
	}
	
	
	/**
	 * Retorna uma referência para o registrador especificado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O registrador deve existir.<br/>
	 * <b>Pós-condição</b>: Uma referência ao registrador é retornada.
	 * 
	 * @param reg
	 *          O registrador o qual se quer obter o valor.
	 * @return O registrador.
	 */
	public Word getRegister(int reg){
		return regs[reg];
	}
	
	
	/**
	 * Exibe o valor de todos os registradores em formato hexadecimal separados
	 * por um espaço.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma String contendo o valor dos registradores é
	 * retornada.
	 * 
	 * @return Uma String contendo os valores dos registradores.
	 */
	@Override
	public String toString(){
		StringBuilder out = new StringBuilder((Word.HEXWORD_SIZE + 1) * regs.length);
		for(int i = 0; i < regs.length; i++){
			out.append(regs[i].toHexString());
			out.append(' ');
		}
		return out.toString();
	}
	
	
	/**
	 * Copia o conteúdo de um registrador para outro registrador.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Os registradores devem existir.<br/>
	 * <b>Pós-condição</b>: O conteúdo do registrador <i>regSource</i> é copiado
	 * no registrador <i>reg</i>.
	 * 
	 * @param reg
	 *          Registrador que receberá o valor.
	 * @param regSource
	 *          Registrador de onde o valor deve ser lido.
	 */
	public void setValue(int reg, int regSource){
		regs[reg].setValue(regs[regSource]);
	}
}// Registradores.java