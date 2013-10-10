package mvn;

import java.util.Map;

public class ValidadorPrograma {

	private Map<Word, Word> gabarito;


	public ValidadorPrograma() {
		
	}
	
	public void loadGabaritoFromFile( Map< Word, Word > gabarito ) {
		this.gabarito = gabarito;
	}
	
	public void setGabarito( Map< Word, Word > gabarito ) {
		this.gabarito = gabarito;
	}
	
	public boolean valida( Memoria mem ) {
		if ( this.gabarito == null )
			return true;
		
		return false;
	}

}
