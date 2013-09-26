/**
 *
 * @author Diego
 */
public class Principal {

    public static void main(String[] args) {

        Triangulo x1 = new Triangulo(new Ponto(0,0), new Ponto(0,1) , new Ponto(0,2));  //Não é triângulo
        x1.imprime();

        Triangulo x2 = new Triangulo(new Ponto(1,1), new Ponto(2,2) , new Ponto(-3,-3));   //Não é triângulo
        x2.imprime();

        TrianguloEscaleno x3 = new TrianguloEscaleno(new Ponto(0,2), new Ponto(1,0) , new Ponto(-1,2));   //Não é triângulo
        x3.imprime();

        TrianguloIsosceles x4 = new TrianguloIsosceles(new Ponto(0,1), new Ponto(1,0) , new Ponto(-1,-1));   //Não é triângulo
        x4.imprime();


        TrianguloEquilatero x6 = new TrianguloEquilatero(new Ponto(4,0), new Ponto(2,  2*((float) Math.sqrt(3))) , new Ponto(2, (float) -2*((float) Math.sqrt(3))));  //Não é triângulo
        x6.imprime();


    }
}
