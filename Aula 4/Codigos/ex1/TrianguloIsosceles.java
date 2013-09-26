/**
 *
 * @author Diego
 */
public class TrianguloIsosceles extends Triangulo {

    public TrianguloIsosceles(Ponto pontoA, Ponto pontoB, Ponto pontoC){
        super(pontoA, pontoB, pontoC);
    }

    @Override
    public boolean validar() {
        boolean isTriangulo = super.validar();

        if (isTriangulo) {
          return (lado1 == lado2) || (lado2 == lado3) || (lado3 == lado1);
        }

        else return false;
    }


    @Override
    public void imprime(){
        System.out.println(getClass().getName());

        System.out.print("Pontos: ");

        StringBuilder txt = new StringBuilder();
        String sep = "";
        for ( Ponto i : pontos ){
            txt.append(sep).append(i.getX() + " , " + i.getY());
            sep = " - ";
        }
        System.out.println(txt);

        System.out.print("Perímetro: ");
        System.out.println(perimetro());

        if (validar()){
            System.out.println("É triangulo isoceles!");
        } else {
            System.out.println("Não é triangulo isoceles!");
        }
        System.out.println();
    }
}
