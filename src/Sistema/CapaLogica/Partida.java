package Sistema.CapaLogica;

import java.io.Serializable;
import java.lang.Math;

public class Partida implements Serializable{
	
	 private int nro_partida;
	 private int nro_secreto;
	 private boolean finalizada;
	 private int cant_intentos;
	 private int punt_final;
	 
	 public Partida() {
		 nro_partida = 0;
		 nro_secreto = 0;
		 cant_intentos = 0;
		 punt_final = 0;
		 finalizada =false;
	 }
	 
	 public Partida(int nro, int sec){
		 nro_partida = nro;
		 nro_secreto = sec;
		 cant_intentos = 0;
		 punt_final = 0;
		 finalizada =false;
				 
	 }
	 
	 public int getNroPartida(){ 
		 return nro_partida;
	 }
	 
	 public int getNroSecreto(){ 
		 return nro_secreto;
	 }
	 
	 public boolean getFinalizada(){ 
		 return finalizada;
	 }
	 
	 public int getCantIntentos(){ 
		 return cant_intentos;
	 }
	 
	 public int getPuntajeFinal(){ 
		 return punt_final;
	 }
	 
	  public void setNroPartida(int nro){ 
		 this.nro_partida = nro;
	  }
	  
	  public void setNroSecreto(int nro){ 
		 this.nro_secreto = nro;
	  }
	  
	  public void setCantNroIntentos(int nro){ 
		 this.cant_intentos = nro;
	  }
	  
	  public void setFinalizada(boolean f){ 
		 this.finalizada= f;
	  }
	  
	  public void setPuntajeFinal(int f){ 
		 this.punt_final= f;
	  }
	  
	  //-----------------------
	  
      public void calcularPuntaje() {
    	  
    	  int punt = 1000 /cant_intentos;
    	  
    	  punt_final= Math.round(punt);
      }


}


