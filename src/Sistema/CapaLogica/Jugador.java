package Sistema.CapaLogica;

import java.io.Serializable;
import java.util.LinkedList;



import Sistema.CapaLogica.Partidas;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidasException;
import Sistema.CapaLogica.ValueObjects.VOPartidaJugador;

public class Jugador implements Serializable{
	
	 private String nombre;
	 private String codigo;
	 private int puntaje_total;
	 private int cant_partidas;
	 private int cociente;
	 private Partidas par;
	 
	 
	 public Jugador(String nom, String codigo2){
		 nombre = nom;
		 codigo = codigo2;
		 puntaje_total = 0;
		 cant_partidas = 0;
		 cociente =0;
		 par = new Partidas();		 
	 }
	 
	 public String getNombre(){ 
		 return nombre;
	 }
	 
	 public String getCodigo(){ 
		 return codigo;
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
	 
	 public void setNombre (String nombre){ 
		  this.nombre = nombre; 
	}
	 
	  public void setCodigo (String codigo){ 
		 this.codigo = codigo;
	  }
	  
	  public void setPuntajeTotal(int f){ 
		 this.puntaje_total = f;
	  }
	  
	  public void SetCantidadPartidas(int cant){ 
		 this.cant_partidas = cant;
	  }
	  
	  public void setCociente(int c){ 
		 this.cociente = c;
	  }
	  
	  public Partidas DevolverPartidas(){ 
		 return par;
	  }
	  
	  //--------------------------------
	  
	  
	  public void crearPartida() {
		  
		 //Obtener numero al azar
		 int numeroAzar = (int)(Math.random()*1000+1);
		 
		 //Obtiene numero de ultima partida para ese jugador
		 int nro_par;
		 if(par.EsVacia()) {
			 nro_par=0;
		 }else {
			Partida ultima = par.Ultimo();
		 	nro_par = ultima.getNroPartida();
		 }
		 nro_par+=1;
		 
		 Partida aux = new Partida(nro_par,numeroAzar);
		 
		 aux.setCantNroIntentos(0);
		 aux.setFinalizada(false);
		 aux.setPuntajeFinal(0);		 
		 
		 par.Insback(aux);
			 
	  }
	  
	  public boolean hayPartidas() {
		  boolean hay = true;
		  if (par.EsVacia())
		  	hay= false;
		  
		  return hay;	  	
	  }
	  
	  public LinkedList<VOPartidaJugador> listarPartidasJugador() {
		  LinkedList<VOPartidaJugador> listadoPartidas = new LinkedList<VOPartidaJugador>();
		  
		  listadoPartidas = par.listarPartidas();
		  return listadoPartidas;
	  }
	  
	  public Partida darUltimaPartida() {
		  return par.Ultimo();
	  }
	  
	  public boolean ultimaPartidaEnCurso() {
	    	boolean tieneEnCurso= false;
	    	boolean estavacia = par.EsVacia();
	    		    	
	    	if (estavacia==false) {
		    	Partida ul= par.Ultimo();
		    	if (ul.getFinalizada()==false)
		    		tieneEnCurso=true;
	    	}
	    	
	    	return tieneEnCurso;
	    	
	    	
	    }
	  

	public void actualizarPuntajeCTE() {
		  
		//Cociente entre puntaje acumulado y cantidad de partidas
		this.cociente = (this.getPuntajeTotal() / this.getCantPartidas());
		
	  }
	
	 
}
