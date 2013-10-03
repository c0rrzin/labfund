/**
 * Implementa a Operação de Fatorial
 *
 */
public class Fatorial extends Operacoes {

	/**
	 * Implementa a Fatorial (operando2 ignorado)
	 */
	public float calculaOperacao(float operando1, float operando2) {
		int   operando1_int = (int) operando1;
		float resultado = 1; //Variável que guardará o resultado.
		for (int i = 2; i <= operando1_int; ++i )
			operando1 *= i;
		return resultado;
	}

}
