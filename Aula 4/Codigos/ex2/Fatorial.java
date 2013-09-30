/**
 * Implementa a Operação de fatorial
 *
 */
public class Fatorial extends Operacoes {

  /**
   * Implementa o fatorial
   */
  public float calculaOperacao(float operando1, float operando2) {
    float resultadoErro = -1; //Variável que retornará o resultado.


    if (operando1 < 0) {
      System.out.println("Numeros negativos nao possuem fatorial");
      return resultadoErro;
    }

    if (operando1 == 1) {
      return operando1;
    }

    return operando1 * calculaOperacao( operando1 - 1, operando2);
  }

}
