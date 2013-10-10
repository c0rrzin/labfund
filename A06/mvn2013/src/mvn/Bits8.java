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

import java.util.BitSet;

/**
 * Representação de um Byte para a MVN.
 * 
 * @author PSMuniz
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
@SuppressWarnings("serial")
public class Bits8 extends BitSet implements Comparable<Bits8>{
	
	/*** Quantidade de bits em um byte */
	public static final int	BYTE_SIZE			= 8;
	
	/*** Quantidade de bits em um nibble */
	public static final int	NIBBLE_SIZE		= 4;
	
	/*** Quantidade de dígitos de um byte representado na forma hexadecimal */
	public static final int	HEXBYTE_SIZE	= BYTE_SIZE / NIBBLE_SIZE;
	
	
	/**
	 * Instancia um conjunto de <i>BYTE_SIZE</i> bits,
	 * inicialmente setados com FALSE.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O Bits8 é instanciado.
	 */
	public Bits8(){
		super(BYTE_SIZE);
	}
	
	
	/**
	 * Instancia um Bits8 e define o valor inicial com base em um valor inteiro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O Bits8 é instanciano e inicializado com o valor
	 * especificado.
	 * 
	 * @param initialValue
	 *          Valor inicial.
	 */
	public Bits8(int initialValue){
		// Instancia o objeto
		this();
		
		// Define o conteúdo inicial do Bits8
		setValue(initialValue);
	}
	
	
	/**
	 * Instancia um Bits8 e define o valor inicial com base em um valor inteiro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O Bits8 é instanciado e inicializado com o valor
	 * especificado.
	 * 
	 * @param initialValue
	 *          Valor inicial.
	 */
	public Bits8(byte initialValue){
		// Instancia o objeto
		this();
		
		// Define o conteúdo inicial do Bits8
		setValue(initialValue);
	}
	
	
	/**
	 * Instancia um Bits8 e define o valor inicial com base no valor de um
	 * Bits8 passado como parâmetro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: O Bits8 é instanciado e inicializado com o valor
	 * especificado.
	 * 
	 * @param initialValue
	 *          Objeto Bits8 contendo o valor inicial.
	 */
	public Bits8(Bits8 initialValue){
		// Instancia o objeto
		this();
		
		// Define o conteúdo inicial do Bits8
		setValue(initialValue);
	}
	
	
	/**
	 * Define o conteúdo do Bits8 com base no valor especificado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O Bits8 contém o valor especificado.
	 * 
	 * @param value
	 *          Valor a ser definido.
	 */
	private void setValue(byte value){
		// Define o valor do Bits8 bit-a-bit
		for(int i = 0; i < BYTE_SIZE; i++){
			this.set(i, (1 & (value >> i)) != 0);
		}
	}
	
	
	/**
	 * Define o conteúdo do Bits8 com base no valor especificado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O Bits8 contém o valor especificado.
	 * 
	 * @param value
	 *          Valor a ser definido.
	 */
	private void setValue(int value){
		setValue((byte) value);
	}
	
	
	/**
	 * Define o conteúdo do Bits8 com base no valor de um objeto Bits8
	 * passado como parâmetro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor e <i>value</i> é não
	 * nulo.<br/>
	 * <b>Pós-condição</b>: O Bits8 contém o valor especificado.
	 * 
	 * @param value
	 *          Objeto Bits8 contendo o valor a ser definido.
	 */
	private void setValue(Bits8 value){
		setValue(value.toByte());
	}
	
	
	/**
	 * Soma o valor de <i>p</i> ao objeto Bits8.<br/>
	 * <br/>
	 * <b>Pré-condição </b>: O Bits8 contém qualquer valor e <i>p</i> não deve ser
	 * nulo.<br/>
	 * <b>Pós-condição </b>: o objeto tem seu valor somado a <i>p</i>.
	 * 
	 * @param p
	 *          O objeto a ser somado.
	 */
	public void add(Bits8 p){
		boolean carryFlag = false;
		for(int i = 0; i < BYTE_SIZE; i++){
			boolean bit0 = get(i);
			boolean bit1 = p.get(i);
			
			boolean newValue = bit0 ^ bit1 ^ carryFlag;
			carryFlag = (bit1 && bit0) || (bit0 && carryFlag) || (bit1 && carryFlag);
			
			set(i, newValue);
		}
	}
	
	
	/**
	 * Realiza um deslocamento <i>times</i> vezes à direita.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é deslocado à direita <i>times</i> vezes.
	 * 
	 * @param times
	 *          Número de vezes que o valor deve ser deslocado à direita.
	 */
	public final void shiftRight(int times){
		times %= BYTE_SIZE;
		for(int i = 0; i < times; i++){
			shiftRight();
		}
	}
	
	
	/**
	 * Realiza um deslocamento <i>times</i> vezes à esquerda.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é deslocado à esquerda <i>times</i> vezes.
	 * 
	 * @param times
	 *          Número de vezes que o valor deve ser deslocado à esquerda.
	 */
	public final void shiftLeft(int times){
		for(int i = 0; i < times; i++){
			shiftLeft();
		}
	}
	
	
	/**
	 * Desloca os bits um passo para a direita.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é deslocado à direita.
	 */
	public void shiftRight(){
		for(int i = 0; i < BYTE_SIZE - 1; i++){
			set(i, get(i + 1));
		}
		clear(BYTE_SIZE - 1);
	}
	
	
	/**
	 * Desloca os bits um passo para a esquerda.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é deslocado à esquerda.
	 */
	public void shiftLeft(){
		for(int i = BYTE_SIZE - 1; i > 0; i++){
			set(i, get(i - 1));
		}
		clear(0);
	}
	
	
	/**
	 * Realiza uma rotação <i>times</i> vezes à direita.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é rotacionado à direita <i>times</i> vezes.
	 * 
	 * @param times
	 *          Número de vezes que o valor deve ser rotacionado à direita.
	 */
	public final void rotateRight(int times){
		times %= BYTE_SIZE; // coloca o valor entre 0 e BYTE_SIZE
		for(int i = 0; i < times; i++){
			this.rotateRight();
		}
	}
	
	
	/**
	 * Realiza uma rotação <i>times</i> vezes à esquerda.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é rotacionado à esquerda <i>times</i> vezes.
	 * 
	 * @param times
	 *          Número de vezes que o valor deve ser rotacionado à esquerda.
	 */
	public final void rotateLeft(int times){
		times %= BYTE_SIZE; // coloca o valor entre 0 e BYTE_SIZE
		for(int i = 0; i < times; i++){
			this.rotateLeft();
		}
	}
	
	
	/**
	 * Rotaciona os bits um passo para a direita.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é rotacionado à direita.
	 */
	public void rotateRight(){
		boolean firstBit = get(0);
		shiftRight();
		set(BYTE_SIZE - 1, firstBit);
	}
	
	
	/**
	 * Rotaciona os bits um passo para a esquerda.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: o valor é rotacionado à esquerda.
	 */
	public void rotateLeft(){
		boolean lastBit = get(BYTE_SIZE - 1);
		shiftLeft();
		set(0, lastBit);
	}
	
	
	/**
	 * Converte o Bits8 em um valor inteiro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O Bits8 é convertido para sua representação no formato
	 * "int" (0..255).
	 * 
	 * @return O valor inteiro correspondente.
	 */
	public int toInt(){
		int result = 0;
		for(int i = 0; i < BYTE_SIZE; i++){
			result |= (get(i) ? 1 : 0) << i;
		}
		return result;
	}
	
	
	/**
	 * Converte o Bits8 a um valor inteiro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O Bits8 é convertido para sua representação no formato
	 * "byte" (-128..127).
	 * 
	 * @return O valor inteiro correspondente.
	 */
	public byte toByte(){
		return (byte) toInt();
	}
	
	
	/**
	 * Converte o Bits8 para a sua representação Binária.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A representação binária do Bits8 é retornada na forma
	 * de uma String.
	 * 
	 * @return Uma string contendo o valor binário correspondente.
	 */
	public String toBinaryString(){
		return toBinaryString(BYTE_SIZE);
	}
	
	
	/**
	 * Converte o Bits8 para a sua representação Binária.
	 * A saída é formatada para conter, no mínimo, <i>minDigits</i> dígitos.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A representação binária do Bits8 é retornada na forma
	 * de uma String.
	 * 
	 * @param minDigits
	 *          Número mínimo de dígitos da saída.
	 * @return Uma string contendo o valor binário correspondente.
	 */
	public String toBinaryString(int minDigits){
		StringBuilder result = new StringBuilder(Integer.toBinaryString(toInt()));
		while(result.length() < minDigits){
			result.insert(0, '0');
		}
		return result.toString();
	}
	
	
	/**
	 * Converte o Bits8 para a sua representação Hexadecimal.
	 * A saída é formatada para conter, no mínimo, <i>minDigits</i> dígitos.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A representação hexadecimal do Bits8 é retornada na
	 * forma de uma String.
	 * 
	 * @param minDigits
	 *          Número mínimo de dígitos da saída.
	 * @return Uma string contendo o valor hexadecimal correspondente.
	 */
	public String toHexString(int minDigits){
		StringBuilder result = new StringBuilder(Integer.toHexString(toInt()));
		while(result.length() < minDigits){
			result.insert(0, '0');
		}
		return result.toString().toUpperCase();
	}
	
	
	/**
	 * Converte o Bits8 para a sua representação Hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A representação hexadecimal do Bits8 é retornada na
	 * forma de uma String.
	 * 
	 * @return Uma String contendo o valor hexadecimal correspondente.
	 */
	public String toHexString(){
		return toHexString(HEXBYTE_SIZE);
	}
	
	
	/**
	 * Converte o Bits8 para uma String informando o seu conteúdo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A repsentação binária e hexadecimal do Bits8 é
	 * retornada na forma de uma String.
	 * 
	 * @return Uma String com o conteúdo do Bits8.
	 */
	@Override
	public String toString(){
		return String.format("%s (Hex: %s)", toBinaryString(BYTE_SIZE),
				toHexString(HEXBYTE_SIZE));
	}
	
	
	/**
	 * Compara o Bits8 com outro Bits8 numericamente.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: O Bits8 contém qualquer valor e <i>value</i> é não
	 * nulo.<br/>
	 * <b>Pós-condição</b>: O Bits8 é comparado com o objeto especificado.
	 * 
	 * @param value
	 *          O Bits8 a ser comparado
	 * @return o valor 0 se o argumento for igual; um valor menor que 0 se o Bits8
	 *         for menor que argumento passado; e um valor maior do que 0 se este
	 *         Bits8 for
	 *         maior que o argumento passado (<b>comparação sem sinal</b>).
	 */
	@Override
	public int compareTo(Bits8 value){
		return new Integer(this.toInt()).compareTo(value.toInt());
	}
}