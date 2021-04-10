package Sistema.CapaLogica.ValueObjects;
import java.io.Serializable;

import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

public class VOPartidaJugador extends VOPartidaEnCurso implements Serializable{
	
	private int nro_secreto;
	
	 public VOPartidaJugador(int nro_sec,int nro,boolean f,int cant,int g) {
		 
		 super(nro,f,cant,g);
		 nro_secreto = nro_sec;
		
		
	 }
	 
	 public int getNroSecreto(){ 
		 return nro_secreto;
	 }
	 
	 
}
