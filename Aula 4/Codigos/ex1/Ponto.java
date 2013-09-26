/**
 * 
 */

/**
 * Classe que representa um ponto
 * @author Leno
 *
 */
public class Ponto {
	private float x;
	private float y;
	/**
	 * Construtor de um ponto
	 * @param x coordenada x do ponto
	 * @param y coordenada y do ponto
	 */
	public Ponto(float x,float y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Retorna a coordenada x do ponto
	 * @return coordenada x
	 */
	public float getX() {
		return x;
	}
	/**
	 * Define a coordenada x do ponto
	 * @param x coordenada x
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * retorna a coordenada y do ponto
	 * @return coordenada y
	 */
	public float getY() {
		return y;
	}
	/**
	 * Define a coordenada y do ponto
	 * @param y coordenada y
	 */
	public void setY(float y) {
		this.y = y;
	}
        
        /**
	 * Calcula a distancia deste ponto ao ponto passado como parametro
	 * @param P o Ponto cuja distancia se quer calcular
	 */
        public float getDistancia(Ponto p){
            float distX = this.x - p.getX();
            float distY = this.y - p.getY();
            
            return (float) (Math.sqrt(distX*distX + distY*distY));
            
        }
	
	
	
}
