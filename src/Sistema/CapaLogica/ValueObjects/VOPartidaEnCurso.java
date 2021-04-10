package Sistema.CapaLogica.ValueObjects;
import java.io.Serializable;



public class VOPartidaEnCurso implements Serializable {

	 private int nro_partida;
	 private boolean finalizada;
	 private int cant_intentos;
	 private int punt_final;
	 
	 public VOPartidaEnCurso(int nro,boolean f,int cant,int g) {
		 nro_partida = nro;
		 cant_intentos = cant;
		 punt_final = g;
		 finalizada =f;
	 }
	 
	 
	 
	 public int getNroPartida(){ 
		 return nro_partida;
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
}
