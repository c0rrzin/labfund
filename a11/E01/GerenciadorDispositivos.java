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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import mvn.controle.MVNException;
import mvn.dispositivo.*;

/**
 * Representa o gerenciador de dispositivos para a MVN. O gerenciador e
 * responsável por habilitar e desabilitar dispositivos e intermediar a leitura
 * e escrita (encontrando o dispositivo adequado).<br>
 * Existem MAX_DEVICETYPES tipos de dispositivos a  disposicao do usua¡rio da
 * MVN. Sendo que cada um dos tipos de dispositivos pode ter MAX_LOGICALUNITS
 * unidades logicas. <br>
 * Os dispositivos podem ser adicionados em tempo de execucao.
 * 
 * @author FLevy
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public class GerenciadorDispositivos{
	
	/*** Quantidade ma¡xima de tipos de dispositivos suportados */
	private static final int							MAX_DEVICETYPES									= (int) Math
																																						.pow(
																																								2,
																																								Bits8.NIBBLE_SIZE);																																	// (nibble)
																																																																																			
	/*** Quantidade ma¡xima de unidades logicas suportadas */
	private static final int							MAX_LOGICALUNITS								= (int) Math
																																						.pow(
																																								2,
																																								Bits8.BYTE_SIZE);																																		// (byte)
																																																																																			
	/*** Nome do arquivo padrao de dispositivos */
	private static final String						DEFAULT_DEVICE_FILE							= "disp.lst";
	
	/*** Mensagem de inicializacao dos dispositivos */
	private static final String						MSG_DEFAULT_INITIALIZATION			= "Inicializacao padrao de dispositivos";
	
	/*** Mensagem de inicializacao dos dispositivos via arquivo */
	private static final String						MSG_FILE_INICIALIZATION					= "Inicializacao padrao de dispositivos baseada em arquivo: "
																																						+ DEFAULT_DEVICE_FILE;
	
	/*** Mensagem: Nao ha¡ dispositivos disponiveis */
	private static final String						MSG_NO_DEVICES									= "Nao ha¡ dispositivos disponiveis.";
	
	/*** Mensagem de erro para dispositivo inva¡lido */
	private static final String						ERR_INVALID_DEVICE							= "Dispositivo invalido";
	
	/*** Mensagem de erro para dispositivo nao encontrado */
	private static final String						ERR_DEVICE_NOTFOUND							= "Dispositivo nao encontrado. [Tipo: %h, Unidade logica: %h]";
	
	/*** Mensagem de erro para tipo de dispositivo inva¡lido */
	private static final String						ERR_INVALID_DEVICETYPE					= "Tipo de dispositivo invalido (0.."
																																						+ MAX_DEVICETYPES
																																						+ ")";
	
	/*** Mensagem de erro para unidade logica inva¡lida */
	private static final String						ERR_INVALID_LOGICALUNIT					= "Posicao da unidade logica inva¡lida (0.."
																																						+ MAX_LOGICALUNITS
																																						+ ")";
	
	/*** Mensagem de erro: Erro de entrada/saida */
	public static final String						ERR_IOERROR											= "Erro ao ler o arquivo %s.";
	
	/***
	 * Mensagem de erro da inicializacao por arquivo: tipo de argumento
	 * inva¡lido
	 */
	private static final String						ERR_DEVICEFILE_ARGMUSTBENUMBER	= "Erro na linha %d: O argumento %d deve ser numerico.";
	
	/***
	 * Mensagem de erro da inicializacao por arquivo: impossivel instanciar o
	 * dispositivo com os para¢metros especificados
	 */
	private static final String						ERR_DEVICEFILE_PARAMSETERROR		= "Erro na linha %d: Impossivel instanciar um dispositivo com os para¢metros especificados.";
	
	/*** Mensagem de erro: classe do dispositivo inva¡lida */
	private static final String						ERR_DEVICE_INVALIDCLASS					= "A classe de dispositivo especificada nao existe ou nao pode ser utilizada (%s).";
	
	/*** Mensagem de erro: para¢metro incorreto */
	private static final String						ERR_DEVICE_INCORRECTPARAM				= "Erro ao instanciar o dispositivo %s. Para¢metro incorreto.";
	
	/*** Mensagem de erro: falha ao instanciar o dispositivo */
	private static final String						ERR_DEVICE_DEVICEERROR					= "Dispositivo %s nao pode ser instanciado.";
	
	/*** Tabela hash dos dispositivos adicionados na MVN. */
	private HashMap<String, Dispositivo>	dispositivos;
	
	/** Tipos padrao de dispositivo: teclado */
	public static final int								TYPE_TECLADO										= 0;
	
	/** Tipos padrao de dispositivo: monitor */
	public static final int								TYPE_MONITOR										= 1;
	
	/** Tipos padrao de dispositivo: impressora */
	public static final int								TYPE_IMPRESSORA									= 2;
	
	/** Tipos padrao de dispositivo: disco */
	public static final int								TYPE_DISCO											= 3;
	
	
	/**
	 * Instancia a classe criando uma tabela de dispositivos vazia.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: A classe e instanciada.
	 */
	public GerenciadorDispositivos(){
		dispositivos = new HashMap<String, Dispositivo>();
	}
	
	
	/**
	 * Cria uma chave aºnica para o dispositivo/unidade logica.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Uma chave e criada a partir dos para¢metros
	 * especificados.
	 * 
	 * @param deviceType
	 *          Tipo de dispositivo
	 * @param logicalUnit
	 *          Unidade logica
	 * @return Uma chave aºnica formada com base nos dados fornecidos.
	 */
	private String MakeHashKey(int deviceType, int logicalUnit){
		return String.format("%d.%d", deviceType, logicalUnit);
	}
	
	
	/**
	 * Extrai a tipo de dispositivo de uma chave.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>key</i> deve ser uma chave va¡lida.<br/>
	 * <b>Pos-condicao</b>: O tipo de dispositivo que gerou a chave e
	 * retornado.
	 * 
	 * @param key
	 *          Chave.
	 * @return O tipo de dispositivo que gerou a chave.
	 */
	private int ExtractDeviceTypeFromKey(String key){
		String deviceTypeStr = key.substring(0, key.indexOf('.'));
		return Integer.parseInt(deviceTypeStr);
	}
	
	
	/**
	 * Extrai a unidade logica de uma chave.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>key</i> deve ser uma chave va¡lida.<br/>
	 * <b>Pos-condicao</b>: A unidade logica que gerou a chave e retornada.
	 * 
	 * @param key
	 *          Chave.
	 * @return A unidade logica que gerou a chave.
	 */
	private int ExtractLogicalUnitFromKey(String key){
		String logicalUnitStr = key.substring(key.indexOf('.') + 1);
		return Integer.parseInt(logicalUnitStr);
	}
	
	
	/**
	 * Adiciona um dispositivo no gerenciador.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Os para¢metros devem ser consistentes.<br/>
	 * <b>Pos-condicao</b>: o dispositivo sera¡ inserido na posicao
	 * especificada substituindo o dispositivo que estiver na posicao, se
	 * houver.
	 * 
	 * @param deviceType
	 *          Tipo do dispositivo que sera¡ adicionado.
	 * @param logicalUnit
	 *          Unidade logica do dispositivo que sera¡ adicionado.
	 * @param newDevice
	 *          Dispositivo a ser adicionado.
	 * @throws MVNException
	 *           Caso ocorra algum erro ao remover o dispositivo.
	 */
	private void addDispositivo(int deviceType, int logicalUnit,
			Dispositivo newDevice) throws MVNException{
		if(deviceType < 0 || deviceType >= MAX_DEVICETYPES){
			throw new MVNException(ERR_INVALID_DEVICETYPE);
		}
		if(logicalUnit < 0 || logicalUnit >= MAX_LOGICALUNITS){
			throw new MVNException(ERR_INVALID_LOGICALUNIT);
		}
		if(newDevice == null){
			throw new MVNException(ERR_INVALID_DEVICE);
		}
		
		String key = MakeHashKey(deviceType, logicalUnit);
		dispositivos.put(key, newDevice);
	}
	
	
	/**
	 * Adiciona um dispositivo no gerenciador.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Os para¢metros devem ser consistentes.<br/>
	 * <b>Pos-condicao</b>: O dispositivo especificado e gerado.
	 * 
	 * @param deviceType
	 *          Tipo de dispositivo
	 * @param logicalUnit
	 *          Unidade logica
	 * @param deviceClass
	 *          Classe do dispotivo a ser instanciado
	 * @param params
	 *          Para¢metros a serem passados ao dispotivo que sera¡
	 *          instanciado
	 * @throws MVNException
	 *           Caso ocorra algum erro na instanciacao do dispositivo.
	 */
	public void addDispositivo(int deviceType, int logicalUnit,
			String deviceClass, String[] params) throws MVNException{
		Dispositivo device = CreateDeviceByClass(deviceClass, params);
		addDispositivo(deviceType, logicalUnit, device);
	}
	
	
	/**
	 * Remove um dispositivo do gerenciador.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo indicado deve existir.<br/>
	 * <b>Pos-condicao</b>: O dispositivo e removido.
	 * 
	 * @param deviceType
	 *          Tipo de dispositivo.<br>
	 *          Tipos padrao:<br/>
	 *          0. Teclado<br/>
	 *          1. Monitor<br/>
	 *          2. Impressora<br/>
	 *          3. Disco
	 * @param logicalUnit
	 *          Unidade logica do dispositivo.
	 * @throws MVNException
	 *           Caso o dispositivo nao esteja na tabela do gerenciador.
	 */
	public void removeDispositivo(int deviceType, int logicalUnit)
			throws MVNException{
		String key = MakeHashKey(deviceType, logicalUnit);
		if(!dispositivos.containsKey(key)){
			throw new MVNException(ERR_DEVICE_NOTFOUND, deviceType, logicalUnit);
		}
		dispositivos.remove(key);
	}
	
	
	/**
	 * Retorna uma referencia para a classe especificada caso ela seja
	 * descendente da interface Dispositivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>deviceClassName</i> e uma classe va¡lida
	 * descendente de Dispositivo va¡lida.<br/>
	 * <b>Pos-condicao</b>: Uma referencia a  classe especificada e
	 * retornada.
	 * 
	 * @param deviceClassName
	 *          Nome da classe a ser retornada.
	 * @return Uma referencia para a classe especificada caso ela seja
	 *         descendente da interface Dispositivo, caso contra¡rio retorna
	 *         null.
	 */
	@SuppressWarnings("unchecked")
	private Class<Dispositivo> getDeviceClass(String deviceClassName){
		// aqui pega a classe do device que o usua¡rio especificou
		try{
			Class deviceClass = Class.forName(deviceClassName);
			if(!Dispositivo.class.isAssignableFrom(deviceClass)){
				return null;
			}else{
				return deviceClass;
			}
		}catch(ClassNotFoundException e){
			return null;
		}
	}
	
	
	/**
	 * Converte <i>param</i> mo tipo de dado identificado por <i>paramType</i>.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>paramType</i> contem um tipo de para¢metro
	 * va¡lido.<br/>
	 * <b>Pos-condicao</b>: Um objeto do tipo especificado contendo o valor
	 * passado e retornado.
	 * 
	 * @param param
	 *          Valor a ser convertido.
	 * @param paramType
	 *          Tipo de dado.
	 * @return O valor passado como para¢metro no tipo de dado informado.
	 */
	private Object setValueByType(String param, Class paramType){
		if(paramType == String.class){
			return param;
		}else if(paramType == char.class){
			return Character.valueOf(param.charAt(0));
		}else if(paramType == short.class){
			return Short.valueOf(param);
		}else if(paramType == int.class){
			return Integer.valueOf(param);
		}else if(paramType == int.class){
			return Float.valueOf(param);
		}else if(paramType == int.class){
			return Double.valueOf(param);
		}else if(paramType == int.class){
			return Boolean.valueOf(param);
		}else{
			return null;
		}
	}
	
	
	/**
	 * Remove todos os dispositivos adicionados e adiciona alguns dispositivos
	 * padraµes, caso exista um arquivo de configuracao
	 * <i>DEFAULT_DEVICE_FILE</i>, esta lista sera¡ carregada.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: a lista de dispositivos esta¡ inicializada com os
	 * dispositivos padraµes.
	 * 
	 * @param output
	 *          Objeto onde as mensagens de saida devem ser escritas.
	 * @throws MVNException
	 *           Caso ocorra algum erro durante a leitura do arquivo.
	 */
	public void inicializa(Appendable output) throws MVNException{
		dispositivos.clear();
		// verifica se existe um arquivo com a configuracao dos dispositivos
		File file = new File(DEFAULT_DEVICE_FILE);
		if(file.exists() && file.canRead()){
			// se houver leia
			outputInfo(MSG_FILE_INICIALIZATION, output);
			fileInitialization(file);
		}else{
			// se nao houver entao configure o padrao
			outputInfo(MSG_DEFAULT_INITIALIZATION, output);
			defaultInitialization();
		}
	}
	
	
	/**
	 * Escreve um Bits8 em um dispositivo.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo deve aceitar gravacao e o Bits8 a
	 * ser escrito nao pode ser nulo.<br/>
	 * <b>Pos-condicao</b>: O Bits8 e escrito no dispositivo.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo a ser escrito.
	 * @param logicalUnit
	 *          A unidade de mvn.controle a ser escrita.
	 * @param outData
	 *          O Bits8 a ser escrito.
	 * @throws MVNException
	 *           Caso o dispositivo nao exista ou caso nao seja possivel
	 *           escrever no mesmo.
	 */
	public void escreverDispositivo(int deviceType, int logicalUnit, Bits8 outData)
			throws MVNException{
		Dispositivo dispositivo = getDevice(deviceType, logicalUnit);
		dispositivo.escrever(outData);
	}
	
	
	/**
	 * Le um Bits8 (byte da MVN) de um dispositivo.<br/>
	 * Esse metodo espera ate o dispositivo retornar o Bits8 lido.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo deve aceitar leitura.<br/>
	 * <b>Pos-condicao</b>: Um Bits8 e lido do dispositivo.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo a ser lido.
	 * @param logicalUnit
	 *          A unidade de mvn.controle a ser lida.
	 * @return O valor lido do dispositivo.
	 * @throws MVNException
	 *           Caso nao seja possivel ler no dispositivo.
	 */
	public Bits8 lerDispositivo(int deviceType, int logicalUnit)
			throws MVNException{
		Dispositivo dispositivo = getDevice(deviceType, logicalUnit);
		return dispositivo.ler();
	}
	
	
	/**
	 * Posiciona o cursor de leitura do dispositivo em seu inicio.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo deve aceitar leitura.<br/>
	 * <b>Pos-condicao</b>: O buffer de leitura encontra-se na posicao inicial.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo a ser lido.
	 * @param logicalUnit
	 *          A unidade de mvn.controle a ser lida.
	 * 
	 * @throws MVNException
	 *           Caso o dispositivo nao aceite leitura ou caso haja um problema
	 *           de entrada e saida ao executar comando.
	 * 
	 */
	public void reiniciarLeitura(int deviceType, int logicalUnit)
			throws MVNException{
	}
	
	
	/**
	 * Obtem o numero total de bytes lidos da unidade logica
	 * ate o momento.<br/>
	 * .<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo deve aceitar leitura.<br/>
	 * <b>Pos-condicao</b>: verifica numero de bytes ja lidos do dispositivo.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo alvo.
	 * @param logicalUnit
	 *          A unidade de mvn.controle alvo.
	 * 
	 * @return Numero de bytes ja lidos do dispositivo.
	 * @throws MVNException
	 *           Caso o dispositivo nao aceite leitura ou caso haja um problema
	 *           de entrada e saida ao executar comando.
	 */
	public Bits8 posicaoLeitura(int deviceType, int logicalUnit)
			throws MVNException{
		return null;
	}
	
	
	/**
	 * Avanca o cursor de leitura do dispositivo em uma quantidade especifica de
	 * bytes.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo permite leitura.<br/>
	 * <b>Pos-condicao</b>: o cursor de leitura do dispositivo de leitura
	 * deslocado do numero indicado de bytes.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo alvo.
	 * @param logicalUnit
	 *          A unidade de mvn.controle alvo.
	 * @param val
	 *          O numero de posicoes a serem avancadas.
	 * 
	 * @return numero total de posicoes efetivamente avancadas.
	 * @throws MVNException
	 *           Caso o dispositivo nao aceite leitura ou caso haja um problema
	 *           de entrada e saida ao executar comando.
	 */
	public Bits8 avancarLeitura(int deviceType, int logicalUnit, int val)
			throws MVNException{
		return null;
	}
	
	
	/**
	 * Obtem o tamanho do arquivo associado a unidade logica, em bytes.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O dispositivo deve aceitar leitura.<br/>
	 * <b>Pos-condicao</b>: verifica o tamanho do arquivo associado a unidade
	 * logica.
	 * 
	 * @param deviceType
	 *          O tipo do dispositivo alvo.
	 * @param logicalUnit
	 *          A unidade de mvn.controle alvo.
	 * 
	 * @return tamanho do arquivo associado a unidade logica, em bytes
	 * @throws MVNException
	 *           Caso nao seja possivel executar corretamente o comando
	 */
	public Bits8 tamanhoDispositivo(int deviceType, int logicalUnit)
			throws MVNException{
		return null;
	}
	
	
	/**
	 * Retorna uma referencia para a insta¢ncia do dispositivo indicado de
	 * acordo com o tipo e unidade logica passada como para¢metro.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: O tipo de dispositivo e unidade logica devem
	 * existir.<br/>
	 * <b>Pos-condicao</b>: O dispositivo indicado e retornado.
	 * 
	 * @param deviceType
	 *          Tipo de dispositivo
	 * @param logicalUnit
	 *          Unidade logica
	 * @return Dispositivo indicado pelo tipo e pela unidade logica.
	 * @throws MVNException
	 *           Caso o dispositivo nao esteja na tabela de dispositivos.
	 */
	private Dispositivo getDevice(int deviceType, int logicalUnit)
			throws MVNException{
		String key = MakeHashKey(deviceType, logicalUnit);
		if(dispositivos.containsKey(key)){
			return dispositivos.get(key);
		}else{
			throw new MVNException(ERR_DEVICE_NOTFOUND, deviceType, logicalUnit);
		}
	}
	
	
	/**
	 * Retorna string com a listagem dos dispositivos contidos na tabela Hash.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: Uma lista contendo todos os dispositivos
	 * registrados.
	 * 
	 * @return String com os dispositivos contidos na tabela.
	 */
	@Override
	public String toString(){
		StringBuilder out = new StringBuilder();
		if(dispositivos.size() > 0){
			out.append("Tipo   UC   Dispositivo");
			out.append(System.getProperty("line.separator"));
			out.append("---------------------------------");
			
			Iterator<Entry<String, Dispositivo>> it = dispositivos.entrySet()
					.iterator();
			while(it.hasNext()){
				Entry<String, Dispositivo> entry = it.next();
				String key = entry.getKey();
				Dispositivo dispositivo = entry.getValue();
				
				int tipo = ExtractDeviceTypeFromKey(key);
				int uc = ExtractLogicalUnitFromKey(key);
				String name = dispositivo.getClass().getSimpleName();
				String detail = !(dispositivo instanceof Monitor || dispositivo instanceof Teclado) ? dispositivo
						.toString() : "";
				
				out.append(System.getProperty("line.separator"));
				out.append(String.format(" %2d    %2d   %s%s", tipo, uc, name, detail));
			}
		}else{
			out.append(MSG_NO_DEVICES);
		}
		return out.toString();
	}
	
	
	/**
	 * Faz uma inicializacao por arquivo dos dispositivos.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>file</i> deve existir e estar no formato
	 * apropriado.<br/>
	 * <b>Pos-condicao</b>: Os dispositivos especificados no arquivo sao
	 * registrados no gerenciador.
	 * 
	 * @param file
	 *          Arquivo a ser lido.
	 * @throws MVNException
	 *           Caso nao seja possivel ler o arquivo ou caso ele contenha
	 *           algum erro que impeca a criacao do dispositivo.
	 */
	private void fileInitialization(File file) throws MVNException{
		try{ // try..catch IOException
			BufferedReader reader = new BufferedReader(new FileReader(file));
			try{ // try..finally
				// realiza parsing do arquivo linha-a-linha
				String line;
				int iline = 1;
				while((line = reader.readLine()) != null){
					parseLine(line, iline);
					iline++;
				}
			}finally{
				reader.close();
			}
		}catch(IOException e){
			throw new MVNException(ERR_IOERROR, file.getName());
		}
	}
	
	
	/**
	 * Retorna o construtor da classe com a mema quantidade de para¢metros
	 * passado como para¢metro.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>className</i> deve ser uma classe va¡lida
	 * descendente de Dispositivo.<br/>
	 * <b>Pos-condicao</b>: O construtor e retornado.
	 * 
	 * @param className
	 *          O nome da classe
	 * @param numParams
	 *          A quantidade de para¢metros que o construtor deve ter.
	 * @return O construtor da classe caso ele exista ou "null", caso
	 *         contra¡rio.
	 */
	@SuppressWarnings("unchecked")
	private Constructor<Dispositivo> getDeviceConstrutor(String className,
			int numParams){
		Constructor<Dispositivo> constructor = null;
		
		Class<Dispositivo> deviceClass = getDeviceClass(className);
		if(deviceClass != null){
			for(Constructor thisConstructor : deviceClass.getConstructors()){
				if(thisConstructor.getParameterTypes().length == numParams){
					constructor = thisConstructor;
					break;
					// pode haver um bug aqui caso exista mais do que um
					// construtor
					// com a mesma quantidade de para¢metros
					// Nao ha¡ como saber qual dos construtores sera¡ utilizado
				}
			}
		}
		return constructor;
	}
	
	
	/**
	 * Inicializacao padrao de dispositivos da MVN.<br/>
	 * Por padrao e inicializado o Teclado e o Monitor.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: Nenhuma.<br/>
	 * <b>Pos-condicao</b>: O Teclado e o Monitor sao registrados no
	 * gerenciador.
	 * 
	 * @throws MVNException
	 *           Caso ocorra algum erro durante a inicializacao dos
	 *           dispositivos.
	 */
	private void defaultInitialization() throws MVNException{
		// Trecho hardcoded
		addDispositivo(0, 0, new Teclado()); // Teclado
		addDispositivo(1, 0, new Monitor()); // Monitor
	}
	
	
	/**
	 * Instancia um dispositivo especificado pelo comando.<br/>
	 * Sintaxe:<br/>
	 * <tipo> <unidade_logica> <classe> [<param_1> <param_2> ... <param_n>]<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: A sintaxe da linha esta¡ correta.<br/>
	 * <b>Pos-condicao</b>: O dispositivo e registrado no gerenciador.
	 * 
	 * @param line
	 *          Linha com os dados a serem lidos
	 * @param lineNumber
	 *          Naºmero da linha do arquivo (para mensagem de erro)
	 * @throws MVNException
	 *           Caso a linha contenha algum erro de sintaxe ou nao seja
	 *           possivel instanciar o dispositivo.
	 */
	private void parseLine(String line, int lineNumber) throws MVNException{
		ArrayList<String> newDevice = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(line);
		while(token.hasMoreElements()){
			newDevice.add(token.nextToken());
		}
		
		if(newDevice.size() >= 3){
			int tipo, unidadeLogica;
			Dispositivo dispositivo;
			
			int iarg = 1;
			try{ // Tipo de dispositivo
				tipo = Integer.parseInt(newDevice.remove(0));
				iarg++;
				unidadeLogica = Integer.parseInt(newDevice.remove(0));
				iarg++;
			}catch(NumberFormatException e){
				throw new MVNException(ERR_DEVICEFILE_ARGMUSTBENUMBER, lineNumber, iarg);
			}
			
			String className = newDevice.remove(0);
			iarg++;
			
			dispositivo = CreateDeviceByClass(className,
					newDevice.toArray(new String[newDevice.size()]));
			addDispositivo(tipo, unidadeLogica, dispositivo);
		}else{
			throw new MVNException(ERR_DEVICEFILE_PARAMSETERROR, lineNumber);
		}
	}
	
	
	/**
	 * Cria um dispositivo com da classe especificada e com os para¢metros
	 * informados.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>className</i> deve ser uma classe va¡lida que
	 * contem um construtor que aceite os para¢metros informados.<br/>
	 * <b>Pos-condicao</b>: Uma insta¢ncia da classe especificada e
	 * retornada.
	 * 
	 * @param className
	 *          Nome da classe a ser instanciada.
	 * @param params
	 *          Para¢metros do construtor da classe indicada.
	 * @return Uma referencia para a classe indicada.
	 * @throws MVNException
	 *           Caso a classe nao exista, nao haja construtor que possa
	 *           receber os para¢metros ou caso ocorra algum erro durante a
	 *           instancializacao.
	 */
	private Dispositivo CreateDeviceByClass(String className, String[] params)
			throws MVNException{
		// encontra o construtor com base na quantidade de para¢metros passados
		Constructor<Dispositivo> constructor = getDeviceConstrutor(className,
				params.length);
		if(constructor == null){
			throw new MVNException(ERR_DEVICE_INVALIDCLASS, className);
		}
		
		// cria a estrutura com os para¢metros, caso houver
		Object[] typedParams = new Object[params.length];
		for(int i = 0; i < params.length; i++){
			try{
				Class paramType = constructor.getParameterTypes()[i];
				typedParams[i] = setValueByType(params[i], paramType);
			}catch(Exception e){
				throw new MVNException(ERR_DEVICE_INCORRECTPARAM, className);
			}
		}
		
		try{
			return constructor.newInstance(typedParams);
		}catch(Exception e){
			throw new MVNException(ERR_DEVICE_DEVICEERROR, className);
		}
	}
	
	
	/**
	 * Escreve a mensagem no objeto especificada.<br/>
	 * <br/>
	 * <b>Pre-condicao</b>: <i>output</i> e <i>txt</i> sao nao nulos.<br/>
	 * <b>Pos-condicao</b>: <i>txt</i> e escrito em <i>output</i>
	 * 
	 * @param txt
	 *          Mensagem a ser escrita
	 * @param output
	 *          Objeto onde a mensagem deve ser escrita.
	 */
	private void outputInfo(String txt, Appendable output){
		if(output != null){
			try{
				output.append(txt);
			}catch(IOException e){
				// ignora esta excecao
			}
		}
	}
}