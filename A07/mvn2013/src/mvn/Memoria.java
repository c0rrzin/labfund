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

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import mvn.controle.MVNException;

/**
 * Representa a memória da MVN.
 * 
 * @author PSMuniz
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public class Memoria{
	
	/*** Quantidade de bytes por linha ao efetuar o dump */
	private static final int		DUMP_BYTES_PER_LINE	= 16;
	
	/*** Mensagem de erro: Endereço de memória inválido */
	private static final String	ERR_INVALID_ADDRESS	= "Endereço inválido para acesso à memoria [0x%h].";
	
	/*** Mensagem de erro: Erro ao acessar a memória */
	private static final String	ERR_MEMORY_ERROR		= "Erro ao acessar a memória.";
	
	/*** Mensagem de erro: Erro no parser */
	private static final String	ERR_PARSE_ERROR			= "Erro no parser.";
	
	/*** Parser: Símbolo do comentário de linha */
	private static final char		PARSE_LINE_COMMENT	= ';';
	
	/*** Capacidade total da memória (em bytes) */
	private int									capability;
	
	/*** Menor endereço válido da memória */
	private int									minAddress;
	
	/*** Maior endereço válida da memória */
	private int									maxAddress;
	
	/** Estrutura de dados da memória */
	private Bits8[]							store;
	
	
	/**
	 * Construtor: cria um arranjo de memória de tamanho MAX_ADDRESS e inicia as
	 * posiçoes com valor 0.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: a memória contém MAX_ADDRESS Bits8 com valor zerado.
	 * 
	 * @param minAddress
	 *          Menor endereço da memória.
	 * @param maxAddress
	 *          Maior endereço da memória.
	 */
	public Memoria(int minAddress, int maxAddress){
		
		this.minAddress = minAddress;
		this.maxAddress = maxAddress;
		this.capability = maxAddress - minAddress + 1;
		
		store = new Bits8[capability];
		initializeMemory();
	}
	
	
	/**
	 * Inicializa a memória com dados aleatórios.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: A memória é inicializada.
	 */
	private void initializeMemory(){
		Random randomizer = new Random();
		for(int i = 0; i < capability; i++){
			store[i] = new Bits8(randomizer.nextInt());
		}
	}
	
	
	/**
	 * "Limpa" a memória, isto é, preenche-a com zeros.<br>
	 * <b>Pós-condição</b>: Os bits da memória estão em qualquer nível lógico.<br/>
	 * <b>Pós-condição</b>: Todos os bits da memória estão em nível lógico zero.
	 */
	public void clear(){
		for(int i = 0; i < capability; i++){
			store[i].clear();
		}
	}
	
	
	/**
	 * Escreve um byte em um endereço da memória. <br>
	 * <b>Pré-condição</b>: o Bits8 a ser escrito não pode ser nulo. <br>
	 * <b>Pós-condição</b>: a posição de memória <i>addr</i> contém o valor
	 * <i>p</i>.
	 * 
	 * @param p
	 *          O Bits8 a ser escrito.
	 * @param addr
	 *          O endereço o qual o Bits8 será escrito (entre MIN_ADDRESS e
	 *          MAX_ADDRESS).
	 * @throws MVNException
	 *           Caso o endereço de memória especificado seja inválido.
	 */
	public void write(Bits8 p, int addr) throws MVNException{
		// Verificar se o endereço é válido.
		if(!isValidAddress(addr)){
			throw new MVNException(ERR_INVALID_ADDRESS, addr);
		}
		store[addr - minAddress] = p;
		return;
	}
	
	
	/**
	 * Retorna um Byte8 armazenado em um endereço da memória.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>addr</i> deve ser um endereço válido.<br/>
	 * <b>Pós-condição</b>: O endereço é lido.
	 * 
	 * @param addr
	 *          O endereço de memória do Bits8 a ser retornado.
	 * @return O byte lido da memória.
	 * @throws MVNException
	 *           Caso o endereço especificado seja inválido.
	 */
	public Bits8 read(int addr) throws MVNException{
		if(!isValidAddress(addr)){
			throw new MVNException(ERR_INVALID_ADDRESS, addr);
		}
		return store[addr - minAddress];
	}
	
	
	/**
	 * Analisa a linha especificada, gravando na memória o seu conteúdo.<br/>
	 * O conteúdo da linha deve estar no seguinte padrão:<br/>
	 * <b><i>[endereço] [dado_1] [dado_2] ... [dado_n] [;] [comentários]</i></b><br/>
	 * Sendo que qualquer um dos elementos é opcional.<br/>
	 * O primeiro elemento da linha, caso não seja um ";" sempre será um endereço.
	 * Deste modo, até que seja encontrado um ";" ou o final da linha, os demais
	 * valores serão tratados como dados a serem gravados na memória
	 * sequencialmente
	 * a partir do endereço especificado.<br/>
	 * Os dados são gravados byte a byte. Caso um dado com mais de um byte seja
	 * especificado, é utilizanda a ordenação <i>big-endian</i>, isto é,
	 * o byte mais significativo é gravado na posição de endereço menor.<br/>
	 * A informação é lida na forma de pares de números hexadecimal, portanto,
	 * caso uma quantidade ímpar de dígitos for informada, o analisador completará
	 * a informação com o dígito "0" a esquerda.<br/>
	 * <br/>
	 * <b>Exemplo:</b><br/>
	 * <i>0F5 1234 567 89 1 0 ; comentário</i><br/>
	 * <b>Resultado:</b><br/>
	 * Estado da memória após a análise desta linha<br/>
	 * [0F5 - 0FB]: 12 34 05 67 89 01 00<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A linha deve estar no formato adequado para leitura.<br/>
	 * <b>Pós-condição</b>: O conteúdo da linha é processado.
	 * 
	 * @param line
	 *          Linha a ser analisada
	 * @return O endereço de memória inicial onde os dados foram escritos ou -1
	 *         caso
	 *         o conteúdo da linha não tenha alterado dados na memória.
	 * @throws MVNException
	 *           Caso haja algum erro na memória (escrita em posição inválida, por
	 *           exemplo),
	 *           ou caso haja algum erro de sintaxe.
	 */
	public int parseLine(String line) throws MVNException{
		StringTokenizer dataLine = new StringTokenizer(line);
		// Por padrão os tokens são: " \t\n\r\f"
		// Um token é separado por espaço, tab, linefeed, carriage return e formfeed
		
		ArrayList<String> parsedCode = new ArrayList<String>();
		boolean ignoreRemainingData = false;
		while(dataLine.hasMoreTokens() && !ignoreRemainingData){
			StringTokenizer dataToken = new StringTokenizer(dataLine.nextToken(),
					String.valueOf(PARSE_LINE_COMMENT), true);
			while(dataToken.hasMoreTokens()){
				String thisToken = dataToken.nextToken();
				if(thisToken.charAt(0) == PARSE_LINE_COMMENT){
					ignoreRemainingData = true;
					break;
				}
				parsedCode.add(thisToken);
			}
		}
		
		// se houver apenas 1 token, significa que o usuário especificou um
		// endereço mas não especificou nenhum dado, portanto, pode-se ignorar a
		// linha
		if(parsedCode.size() > 1){
			// primeira informação sempre é o endereço
			int returnAddress = Integer.parseInt(parsedCode.get(0), 16);
			
			// restante dos dados são informações a serem gravadas
			int address = returnAddress;
			for(int i = 1; i < parsedCode.size(); i++){
				StringBuilder code = new StringBuilder(parsedCode.get(i));
				if(code.length() % 2 == 1){
					code.insert(0, "0");
				}
				
				while(code.length() > 0){
					try{
						String strByte = code.substring(0, 2);
						code.delete(0, 2);
						Integer intByte = Integer.parseInt(strByte, 16);
						// grava byte na memória
						Bits8 thisByte = new Bits8(intByte);
						write(thisByte, address++);
					}catch(NumberFormatException e){
						throw new MVNException(ERR_PARSE_ERROR);
					}
				}
			}
			return returnAddress;
		}
		return -1;
	}
	
	
	/**
	 * Retorna o conteúdo de um trecho da memória em formato texto e hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Os endereços devem ser válidos.<br/>
	 * <b>Pós-condição</b>: Um dump do intervalo especificado é retornado.
	 * 
	 * @param enderecoInicial
	 *          endereço do início do trecho
	 * @param enderecoFinal
	 *          endereço do fim do trecho
	 * @return conteúdo da memória em formato texto e hexadecimal
	 * @throws MVNException
	 *           Caso o intervalo de memória passado seja inválido.
	 */
	public String dump(int enderecoInicial, int enderecoFinal)
			throws MVNException{
		
		if(!isValidAddress(enderecoInicial)){
			throw new MVNException(ERR_INVALID_ADDRESS, enderecoInicial);
		}
		
		if(!isValidAddress(enderecoFinal)){
			throw new MVNException(ERR_INVALID_ADDRESS, enderecoFinal);
		}
		
		// Inverte os endereços de memória caso necessário
		if(enderecoInicial > enderecoFinal){
			int swap = enderecoInicial;
			enderecoInicial = enderecoFinal;
			enderecoFinal = swap;
		}
		
		StringBuilder out = new StringBuilder();
		
		int inicio = enderecoInicial - (enderecoInicial % DUMP_BYTES_PER_LINE);
		for(int i = inicio; i <= enderecoFinal; i++){
			if(i % DUMP_BYTES_PER_LINE == 0){
				if(i > enderecoInicial){
					out.append(System.getProperty("line.separator"));
				}
				out.append(String.format("[%2h]:", i));
			}
			out.append(' ');
			if(i < enderecoInicial){
				out.append("  ");
				// out.append(String.format("%2s",Bits8.HEXBYTE_SIZE));
			}else{
				out.append(store[i - minAddress].toHexString(Bits8.HEXBYTE_SIZE));
			}
		}
		out.append(System.getProperty("line.separator"));
		out.append("Final do dump.");
		
		return out.toString();
	}
	
	
	/**
	 * Converte o conteúdo da memória em uma string formatada e base hexadecimal.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: A memória deve estar inicializada.<br/>
	 * <b>Pós-condição</b>: Um dump completo da memória é retornado.
	 * 
	 * @return O conteúdo da memória em uma String formatada.
	 */
	@Override
	public String toString(){
		try{
			return dump(minAddress, maxAddress);
		}catch(MVNException e){
			return ERR_MEMORY_ERROR;
		}
	}
	
	
	/**
	 * Verifica se o endereço especificado é um endereço válido.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Verifica se o endereço especificado é válido.
	 * 
	 * @param address
	 *          O endereço a ser validado.
	 * @return Verdadeiro se o endereço for válido e falso caso contrário.
	 */
	private boolean isValidAddress(int address){
		boolean retVal = true;
		
		if((address > maxAddress) || (address < minAddress)){
			retVal = false;
		}
		
		return retVal;
	}
	
}// Memoria.java