package Sistema.CapaLogica;

import java.util.LinkedList;
import java.io.Serializable;

import Sistema.CapaLogica.Partida;
import Sistema.CapaLogica.ValueObjects.VOPartidaJugador;


public class Partidas implements Serializable{
	
	private LinkedList<Partida> partidas;
	
	public Partidas() {
		partidas =  new LinkedList<Partida>();
	}
	
	 
	 public boolean EsVacia ()
	 {
		 return partidas.isEmpty();
	 }
	 
	 //Precondici�n: !Vacia(l)
	 public Partida Ultimo (){
		 
		 return partidas.getLast();
	 }
	 
	 
	 public void Insback (Partida j)
	 {
		 partidas.add(j);
	 }
	 
	 //---------------------------------------
	 
	 public LinkedList<VOPartidaJugador> listarPartidas() {
		 LinkedList<VOPartidaJugador> listadoPartidas = new LinkedList<VOPartidaJugador>();
	     for(Partida par: partidas)
	     {
	    	 VOPartidaJugador VOpar = new VOPartidaJugador(par.getNroSecreto(),par.getNroPartida(), par.getFinalizada(), par.getCantIntentos(), par.getPuntajeFinal());
	    	 listadoPartidas.add(VOpar);
	     }
	     return listadoPartidas;
	 }
	 
}
