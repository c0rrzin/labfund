/**
 *
 * @author Diego
 */
public class TrianguloEquilatero extends TrianguloIsosceles {

    public TrianguloEquilatero(Ponto pontoA, Ponto pontoB, Ponto pontoC){
        super(pontoA, pontoB, pontoC);
    }

    @Override
    public boolean validar() {
        boolean isTrianguloIsoceles = super.validar();

        if (isTrianguloIsoceles) {
          return (lado1 == lado2) && (lado2 == lado3);
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
            System.out.println("É triangulo isoceles e equilatero!");
        } else {
            System.out.println("Não é triangulo isoceles e equilatero, pois o a biblioteca math está gerando valores diferentes ao se converter para float, mas deveria ser!");
        }
        System.out.println();
    }
}
