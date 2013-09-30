/**
 *
 * @author Thales
 */
public class Principal {

    public static void main(String[] args) {

        float x = 8, y = 4, z = 2;

        Soma x1 = new Soma();

        System.out.println("A soma de " + x + " com " + y + " é: " + x1.calculaOperacao(x,y) + "");

        Subtracao x2 = new Subtracao();

        System.out.println("A subtracao de " + x + " com " + y + " é: " + x2.calculaOperacao(x,y) + "");

        Multiplicacao x3 = new Multiplicacao();

        System.out.println("A Multiplicacao de " + x + " com " + y + " é: " + x3.calculaOperacao(x,y) + "");

        Divisao x4 = new Divisao();

        System.out.println("A Divisao de " + x + " com " + y + " é: " + x4.calculaOperacao(x,y) + "");

        Fatorial x5 = new Fatorial();

        System.out.println("O Fatorial de " + y + " é: " + x5.calculaOperacao(y, y) + "");

        RaizQuadrada x6 = new RaizQuadrada();

        System.out.println("A RaizQuadrada de " + z + " é: " + x6.calculaOperacao(z, y) + "");

    }
}
