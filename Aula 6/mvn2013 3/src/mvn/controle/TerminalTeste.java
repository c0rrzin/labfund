package mvn.controle;

public class TerminalTeste extends TerminalPadrao {
	
	public TerminalTeste(boolean debug){
		super(debug);
	}
	
	@Override 
	public void exibeLinha(String mensagem, Object... args){
		mensagem = "Teste: " + mensagem;
		super.exibeLinha(mensagem, args);
	}
	
}