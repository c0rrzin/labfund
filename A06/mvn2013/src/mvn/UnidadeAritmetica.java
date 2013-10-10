package mvn;


public class UnidadeAritmetica{
	
	private int regInRight = 0; /* registrador da esquerda (de entrada) */
	private int regInLeft = 0;	/* registrador da direita (de entrada) */
	private int regOut;			/* registrador de saída */

	/*** coloca o valor da soma dos registradores de entrada no registrador de saída*/
	public void ADD(){
		regOut = regInLeft + regInRight;
	}
	
	/*** coloca o valor da subtração entre o registrador esquerdo e direito (de entrada) no registrador de saída*/
	public void SUB(){
		regOut = regInLeft - regInRight;
	}
	
	/*** coloca o valor da divisão entre o registrador esquerdo e direito (de entrada) no registrador de saída*/
	public void DIV(){
		regOut = regInLeft / regInRight;
	}
	
	/*** coloca o valor da multiplicação dos registradores de entrada no registrador de saída*/
	public void MUL(){
		regOut = regInLeft * regInRight;
	}
	
	/*** registra o valor de entrada no registrador da esquerda */
	public void setLeft(int register) {
		regInLeft = register;
	}
	
	/*** registra o valor de entrada no registrador da direita */
	public void setRight(int register) {
		regInRight = register;
	}
	/*** exibe saída */
	public int getOut() {
		return regOut;
	}
}
