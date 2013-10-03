/**
 *
 * @author Diego
 */
public class Triangulo extends Poligono {
    public Triangulo(Ponto pontoA, Ponto pontoB, Ponto pontoC){
        super(new Ponto[]{pontoA, pontoB, pontoC});
    }

    @Override
    public boolean validar() {
        boolean isTriangulo = super.validar();
                
        /* TO DO : Validar tri�ngulo. Tri�ngulos devem ter 3 pontos e a soma de 2 lados deve ser sempre MENOR que o terceiro
         */
        
        return isTriangulo;
    }
    
    
}
