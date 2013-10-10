/**
 * Terminal Teste que exibe o testo Teste antes das mensagems
 */
package mvn.controle;

/**
 * Classe terminal Teste do exercicio 1
 * @author T5G12
 */
public class TerminalTeste extends TerminalPadrao {

	/**
	 * Instancia um terminal de teste.
	 * 
	 * @param debug
	 */
	public TerminalTeste(boolean debug) {
		super(debug);
	}

	/**
	 * Exibe a linha com o prefixo "Teste: "
	 */
	@Override
	public void exibeLinha(String mensagem, Object... args) {
		super.exibeLinha("Teste: " + mensagem, args);
	}
}
