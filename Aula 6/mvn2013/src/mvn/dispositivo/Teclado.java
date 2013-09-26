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
 * Implementação do dispositivo TECLADO para a MVN.
 * 
 * @author FLevy
 * @author Diego Queiroz
 * @version 1.0 - PCS/EPUSP
 * @version 2.0 - PCS/EPUSP (MVN 4.0)
 */
public class Teclado implements Dispositivo{
	
	/**
	 * Método que sempre retorna uma exceção. Não se pode escrever no teclado.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Nenhuma.<br/>
	 * <b>Pós-condição</b>: Erro. Não é possível escrever no teclado.
	 * 
	 * @param in
	 *          Não utilizado.
	 * @throws MVNException
	 *           Caso o método seja invocado.
	 */
	@Override
	public void escrever(Bits8 in) throws MVNException{
		throw new MVNException(ERR_READONLYDEVICE, this);
	}
	
	
	/**
	 * Lê um byte do teclado. Deve se pressionar a tecla ENTER após a entrada de
	 * dados e a quebra de linha deve ser tratada pelo programa.<br/>
	 * <br/>
	 * <b>Obs.:</b> Esse comportamento é inevitável devido a uma carência da API
	 * do
	 * Java no que diz respeito à recursos para o manuseio de aplicativos
	 * de Console. Sabe-se que seria possível implementar isso através da
	 * implementação de um <i>KeyListener</i>, no entanto, seria necessário que a
	 * aplicação possuísse uma interface gráfica. Existe também uma
	 * Biblioteca chamada <a
	 * href="http://sourceforge.net/projects/javacurses/">Java Curses</a>
	 * que disponibiliza os recursos necessários, no entanto, foi optado
	 * por não utilizá-la devido ao fato desta possuir código nativo
	 * (dependente de plataforma) e por tornar o conteúdo do programa
	 * demasiadamente complexo.<br/>
	 * <br/>
	 * <b>Pré-condição</b>: Deve ser possível ler da entrada padrão.<br/>
	 * <b>Pós-condição</b>: A entrada padrão é lida.
	 * 
	 * @return Um Bits8 com o conteúdo lido.
	 * @throws MVNException
	 *           Caso ocorra algum erro.
	 */
	@Override
	public Bits8 ler() throws MVNException{
		try{
			return(new Bits8(System.in.read()));
		}catch(Exception e){
			throw new MVNException(e);
		}
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
	public boolean podeLer(){
		return true;
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
	public boolean podeEscrever(){
		return false;
	}
	
	
	@Override
	public Bits8 position() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	@Override
	public void reset() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	@Override
	public Bits8 size() throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
	
	
	@Override
	public Bits8 skip(Bits8 val) throws MVNException{
		throw new MVNException(ERR_WRITEONLYDEVICE, this);
	}
}