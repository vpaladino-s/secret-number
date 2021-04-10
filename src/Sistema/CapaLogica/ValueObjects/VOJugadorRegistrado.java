package Sistema.CapaLogica.ValueObjects;

import Sistema.CapaLogica.ValueObjects.VOJugador;
import java.io.Serializable;

public class VOJugadorRegistrado extends VOJugador implements Serializable{
	
	 private int puntaje_total;
	 private int cant_partidas;
	 private int cociente;
	 
	 public VOJugadorRegistrado(String nom,String cod,int punt,int cant,int coc){
		 
		 super(nom,cod);
		 puntaje_total = punt;
		 cant_partidas = cant;
		 cociente =coc;
	 }
	 
	 
	 public int getPuntajeTotal(){ 
		 return puntaje_total;
	 }
	 
	 public int getCantPartidas(){ 
		 return cant_partidas;
	 }
	 
	 public int getCociente(){ 
		 return cociente;
	 }

}
