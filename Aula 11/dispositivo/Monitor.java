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
package mvn.dispositivo;

import mvn.Bits8;
import mvn.Dispositivo;
import mvn.controle.MVNException;

/**
 * Implementação do dispositivo MONITOR para a MVN.
 * 
 * @author FLevy
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public class Monitor implements Dispositivo{
	
	/**
	 * Método que sempre retorna uma exceção. Não se pode ler o monitor.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Erro. Não é possível ler o monitor.
	 * 
	 * @return Nada. Uma exceção sempre será gerada.
	 * @throws MVNException
	 *           Caso o método seja invocado.
	 */
	@Override
	public Bits8 ler() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	/**
	 * Escreve a informação no dispositivo de saída padrão.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: <i>in</i> é não nulo.<br/>
	 * <b>Pós-condição</b>: O valor é escrito na saída padrão.
	 * 
	 * @param in
	 *          Valor a ser escrito imediatamente.
	 * @throws MVNException
	 *           Caso ocorra algum erro.
	 */
	@Override
	public void escrever(Bits8 in) throws MVNException{
		byte saida = (byte) in.toInt();
		System.out.write(saida);
		System.out.flush();
	}
	
	
	/**
	 * Retorna FALSE.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Retorna falso.
	 * 
	 * @return False.
	 */
	@Override
	public boolean podeLer(){
		return false;
	}
	
	
	/**
	 * Retorna TRUE.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Retorna verdadeiro.
	 * 
	 * @return True.
	 */
	@Override
	public boolean podeEscrever(){
		return true;
	}
	
	
	@Override
	public Bits8 position() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	@Override
	public void reset() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public Bits8 size() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	@Override
	public Bits8 skip(Bits8 val) throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
} // Monitor