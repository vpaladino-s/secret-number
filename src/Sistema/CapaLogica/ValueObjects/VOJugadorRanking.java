package Sistema.CapaLogica.ValueObjects;

import java.io.Serializable;

public class VOJugadorRanking implements Serializable {

	 private String nombre;
	 private int puntaje_total;
	 private int cant_partidas;
	 private int cociente;
	 private int posicion;
	 
	 
	 public VOJugadorRanking(String nom,int punt,int cant,int coc, int c){
		 nombre = nom;
		 puntaje_total = punt;
		 cant_partidas = cant;
		 cociente =coc;
		 posicion = c;
		 
		 
	 }
	 
	 public String getNombre(){ 
		 return nombre;
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
	 
	 public int getPosicion(){ 
		 return posicion;
	 }
}
