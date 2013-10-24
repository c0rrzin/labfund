package mvn;

import mvn.controle.*;

import java.io.*;
import java.util.*;

public class ValidadorPrograma {

	private Map<Word, Word> gabarito = null;
	
	public boolean loadGabaritoFromFile( String filename ) {
		try {
			Scanner scanner = new Scanner( new FileReader( filename ) );
			HashMap<Word, Word> new_gabarito = new HashMap<Word, Word>();
			
			while ( scanner.hasNextLine() ) {
				String[] colums = scanner.nextLine().split( " " );
				new_gabarito.put( 
					new Word( Integer.parseInt( colums[0], 16 ) ), 
					new Word( Integer.parseInt( colums[1], 16 ) )
				);
			}
			
			setGabarito( new_gabarito );
			return true;
		} catch ( FileNotFoundException e ) {
			return false;
		}
	}
	
	public void setGabarito( Map< Word, Word > gabarito ) {
		this.gabarito = gabarito;
	}
	
	/**
	 * 
	 * @param mem
	 * @return
	 */
	public boolean valida( Memoria mem ) {
		if ( gabarito == null )
			return true;
		
		try {
			for ( Map.Entry<Word, Word> entry : gabarito.entrySet() ) {
				Word key = entry.getKey();
				Bits8 HiWord = mem.read(key.toInt());
				Bits8 LoWord = mem.read(key.toInt() + 1);
				Word value   = new Word(LoWord, HiWord);
				if ( value.compareTo( entry.getValue() ) != 0 )
					return false;
			}
		}
		catch ( MVNException e )
		{
			return false;
		}
		return true;
	}

}
