/**
 *
 * @author Diego
 */
public class Triangulo extends Poligono {

    protected float lado1, lado2, lado3;

    public Triangulo(Ponto pontoA, Ponto pontoB, Ponto pontoC){
        super(new Ponto[]{pontoA, pontoB, pontoC});




        lado1 = pontos[0].getDistancia(pontos[1]);
        lado2 = pontos[1].getDistancia(pontos[2]);
        lado3 = pontos[2].getDistancia(pontos[0]);
    }


    @Override
    public boolean validar() {
        boolean isTriangulo = super.validar();

        if (isTriangulo == true ) {
            if (pontos.length != 3 ) {
                return false;
            }
            else {
                return ( lado1 + lado2 > lado3 ) && (lado2 + lado3 > lado1) && (lado3 + lado1 > lado2);
            }
        }
        else return false;
    }

    @Override
    public float perimetro() {
        return lado1 + lado2 + lado3;
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
            System.out.println("É triangulo!");
        } else {
            System.out.println("Não é triangulo!");
        }
        System.out.println();
    }
}
