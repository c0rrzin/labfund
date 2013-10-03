/**
 * Implementa a Operação de Potencia
 *
 */
public class Potencia extends Operacoes {

	/**
	 * Implementa a Potencia
	 */
	public float calculaOperacao(float operando1, float operando2) {
		float resultado; //Variável que guardará o resultado.
		
		resultado= Math.pow(operando1, operando2); //realiza a conta
		
		return resultado;
	}

}
