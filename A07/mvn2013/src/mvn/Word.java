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
 * Esta classe implementa a abstraçao "Palavra" que consiste
 * na concatenação de 2 Bits8 (2 bytes = 16 bits).
 * 
 * @author PSMuniz
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public final class Word implements Comparable<Word>{
	
	/*** Quantidade de bits de um dado do tipo WORD */
	public static final int	WORD_SIZE			= Bits8.BYTE_SIZE * 2;
	
	/*** Quantidade de dígitos de uma WORD representada na forma hexadecimal */
	public static final int	HEXWORD_SIZE	= WORD_SIZE / Bits8.NIBBLE_SIZE;
	
	/*** Byte menos significativo da Word */
	private Bits8						LoWord;
	
	/*** Byte mais significativo da Word */
	private Bits8						HiWord;
	
	
	/**
	 * Instancia uma WORD com todos os bits inicialmente setados como FALSE.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma<br/>
	 * <b>Pós-condição</b>: Uma Word é instanciada.
	 */
	public Word(){
		this(0);
	}
	
	
	/**
	 * Instancia uma WORD com o valor inteiro especificado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Uma Word é instanciada.
	 * 
	 * @param initialValue
	 *          Valor da WORD.
	 */
	public Word(int initialValue){
		setValue(initialValue);
	}
	
	
	/**
	 * Instancia uma WORD com o valor especificado nos parâmetros que
	 * correspondem às partes menos e mais significativa da palavra.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma<br/>
	 * <b>Pós-condição</b>: Uma Word é instanciada.
	 * 
	 * @param LoWord
	 *          Byte menos significativo da palavra.
	 * @param HiWord
	 *          Byte mais significativo da palavra.
	 */
	public Word(int LoWord, int HiWord){
		this.LoWord = new Bits8(LoWord);
		this.HiWord = new Bits8(HiWord);
	}
	
	
	/**
	 * Instancia uma palavra da MVN.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>initialValue</i> é não nulo.<br/>
	 * <b>Pós-condição</b>: A palavra é instanciada.
	 * 
	 * @param initialValue
	 */
	public Word(Word initialValue){
		this(initialValue.getLoWord(), initialValue.getHiWord());
	}
	
	
	/**
	 * Instancia uma WORD com o valor especificado nos parâmetros que
	 * correspondem às partes menos e mais significativa da palavra.
	 * 
	 * @param LoWord
	 *          Byte menos significativo da palavra.
	 * @param HiWord
	 *          Byte mais significativo da palavra.
	 */
	public Word(Bits8 LoWord, Bits8 HiWord){
		this(LoWord.toInt(), HiWord.toInt());
	}
	
	
	/**
	 * Define o valor da WORD de acordo com o número hexadecimal passado
	 * como parâmetro. Caso possua mais de 2 bytes, o valor será truncado para
	 * 2 bytes.<br/>
	 * <b>Pré-condição</b>: a String <i>hexa</i> não seja nula e esteja em
	 * formato hexadecimal.<br/>
	 * <b>Pós-condição</b>: a palavra será o valor da String hexadecimal.
	 * 
	 * @param hexValue
	 *          Uma String em formato hexadecimal.
	 */
	public void setValue(String hexValue){
		int value = Integer.parseInt(hexValue, 16);
		setValue(value);
	}
	
	
	/**
	 * Define o valor da WORD de acordo com o número inteiro passado como
	 * parâmetro. Caso a representação binária do número possua mais do
	 * que <i>WORD_SIZE</i> bits, o valor será truncado.<br/>
	 * <b>Pré-condição: A WORD contém qualquer valor.</b> <br/>
	 * <b>Pós-condição: A WORD contém o valor especificado no parâmetro.</b>
	 * 
	 * @param value
	 *          Um valor inteiro.
	 */
	public void setValue(int value){
		LoWord = new Bits8(value & 0xFF);
		HiWord = new Bits8((value >> Bits8.BYTE_SIZE) & 0xFF);
	}
	
	
	/**
	 * Define o valor da WORD com base no valor de outra Word passada como
	 * parâmetro.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A WORD contém qualquer valor e <i>value</i> é não
	 * nulo.<br/>
	 * <b>Pós-condição</b>: A WORD contém uma cópia do valor da Word especificada.
	 * 
	 * @param value
	 *          Word de onde o valor deve ser lido
	 */
	public void setValue(Word value){
		setValue(value.LoWord, value.HiWord);
	}
	
	
	/**
	 * Define o valor da WORD com base nos valores dos bytes da parte alta e baixa
	 * da mesma.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A WORD contém qualquer valor e os parâmetros são não
	 * nulos.<br/>
	 * <b>Pós-condição</b>: A WORD contém o valor formado pela composição dos
	 * bytes na sua parte alta e baixa.
	 * 
	 * @param LoWord
	 *          Byte da parte baixa da palavra
	 * @param HiWord
	 *          Byte da parte alta da palavra
	 */
	public void setValue(Bits8 LoWord, Bits8 HiWord){
		this.LoWord = new Bits8(LoWord);
		this.HiWord = new Bits8(HiWord);
	}
	
	
	/**
	 * "Limpa" a WORD, isto é, define o estado lógico de todos seus bits para
	 * nível lógico baixo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A palavra contém o valor 0x0000.
	 */
	public void clear(){
		LoWord.clear();
		HiWord.clear();
	}
	
	
	/**
	 * Converte a WORD para a sua representação na forma binária.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A palavra é transcrita, bit-a-bit em uma String.
	 * 
	 * @param minDigits
	 *          Número mínimo de dígitos da saída
	 * @return Uma String contendo a representação binária da palavra
	 */
	public String toBinaryString(int minDigits){
		StringBuilder result = new StringBuilder(Integer.toBinaryString(toInt()));
		while(result.length() < minDigits){
			result.insert(0, '0');
		}
		return result.toString();
	}
	
	
	/**
	 * Converte a WORD para a sua representação na forma binária, utilizando
	 * WORD_SIZE
	 * dígitos.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A palavra é transcrita, bit-a-bit em uma String.
	 * 
	 * @return Uma String contendo a representação binária da palavra
	 */
	public String toBinaryString(){
		return toBinaryString(WORD_SIZE);
	}
	
	
	/**
	 * Converte a WORD para a sua representação no formato int.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A WORD contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A WORD é convertida para um número inteiro.
	 * 
	 * @return A WORD em formato int.
	 */
	public int toInt(){
		return (HiWord.toInt() << Bits8.BYTE_SIZE) | LoWord.toInt();
	}
	
	
	/**
	 * Converte a WORD para a sua representação no formato short (16 bits com
	 * sinal).<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A WORD contém qualquer valor.
	 * <b>Pós-condição</b>: A WORD é convertida para um número inteiro.
	 * 
	 * @return A WORD em formato short.
	 */
	public short toShort(){
		return (short) toInt();
	}
	
	
	/**
	 * Obtém o valor do byte alto em formato hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O byte alto da Word é retornado na forma hexadecimal.
	 * 
	 * @return O byte alto da Word em Hexadecimal.
	 */
	public String getHiWordHex(){
		return HiWord.toHexString();
	}
	
	
	/**
	 * Obtém o valor do byte baixo em formato hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: O byte baixo da Word é retornado na forma hexadecimal.
	 * 
	 * @return O byte baixo da Word em Hexadecimal.
	 */
	public String getLoWordHex(){
		return LoWord.toHexString();
	}
	
	
	/**
	 * Obtém uma <u>referência</u> para o byte baixo da palavra.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor<br/>
	 * <b>Pós-condição</b>: Uma referência para a parte baixa da palavra é
	 * retornada.
	 * 
	 * @return O byte baixo da palavra.
	 */
	public Bits8 getLoWord(){
		return this.LoWord;
	}
	
	
	/**
	 * Obtém uma <u>referência</u> para o byte alto da palavra.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor<br/>
	 * <b>Pós-condição</b>: Uma referência para a parte alta da palavra é
	 * retornada.
	 * 
	 * @return O byte alto da palavra.
	 */
	public Bits8 getHiWord(){
		return this.HiWord;
	}
	
	
	/**
	 * Obtém o valor em inteiro de um determinado nibble [0..3].<br/>
	 * <br/>
	 * <b>Pré-condição</b>: o valor do nibble deve estar entre 0 e 3, sendo zero o
	 * nibble menos significativo.
	 * <b>Pós-condição</b>: O nibble indicado é retornado.
	 * 
	 * @param nibble
	 *          Nibble a ser retornado.
	 * @return o valor inteiro do nibble.
	 */
	public int getNibbleInt(int nibble){
		return (toInt() >> (Bits8.NIBBLE_SIZE * nibble)) & 0xF;
	}
	
	
	/**
	 * Obtém o valor de um determinado nibble [0..3] em hexadecimal.
	 * O nibble é lido do menos significativo ao mais significativo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: o valor do nibble deve ser de 0 a 3, sendo zero o
	 * nibble menos significativo.
	 * <b>Pós-condição</b>: O nibble indicado é retornado em formato hexadecimal.
	 * 
	 * @param nibble
	 *          Nibble a ser retornado.
	 * @return o valor hexadecimal do nibble indicado.
	 */
	public String getNibbleHex(int nibble){
		return Integer.toHexString(getNibbleInt(nibble));
	}
	
	
	/**
	 * Converte a WORD para a sua representação Hexadecimal.
	 * A saída é formatada para conter, no mínimo, <i>minDigits</i> dígitos.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor<br/>
	 * <b>Pós-condição</b>: A palavra é convertida para o formato hexadecimal.
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
	 * Converte a WORD para a sua representação Hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A palavra contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: A palavra é convertida para o formato hexadecimal.
	 * 
	 * @return Uma String contendo o valor hexadecimal correspondente.
	 */
	public String toHexString(){
		return toHexString(HEXWORD_SIZE);
	}
	
	
	/**
	 * Compara a WORD com outra WORD numericamente.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A WORD contém qualquer valor e <i>value</i> é não
	 * nulo.<br/>
	 * <b>Pós-condição</b>: A WORD é comparado com o objeto especificado.
	 * 
	 * @param value
	 *          A WORD a ser comparado
	 * @return o valor 0 se o argumento for igual; um valor menor que 0 se a Word
	 *         for menor que argumento passado; e um valor maior do que 0 se esta
	 *         Word for
	 *         maior que o argumento passado (<b>comparação sem sinal</b>).
	 */
	@Override
	public int compareTo(Word value){
		return new Integer(this.toInt()).compareTo(value.toInt());
	}
	
	
	/**
	 * Retorna uma string com o conteúdo da Word no formato decimal e hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A Word contém qualquer valor.<br/>
	 * <b>Pós-condição</b>: Uma String que descreve o conteúdo da Word é
	 * retornada.
	 * 
	 * @return O conteúdo da Word.
	 */
	@Override
	public String toString(){
		return String.format("%d (Hex: %s)", toInt(), toHexString(HEXWORD_SIZE));
	}
	
}// Word.java