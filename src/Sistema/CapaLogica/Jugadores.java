package Sistema.CapaLogica;

import java.util.LinkedList;
import java.io.Serializable;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import Sistema.CapaLogica.Jugador;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;

import Sistema.CapaLogica.ValueObjects.VOJugadorRanking;

//ABB
public class Jugadores implements Serializable{
	
		private TreeMap<String,Jugador> jugadores;

	    
	    public Jugadores(){
	    	this.jugadores = new TreeMap<String,Jugador>();
	    }
	    
	    public boolean member (String nom){
	    	  return jugadores.containsKey(nom);
	    }

	    public void insert (String nom,Jugador ju)
	    {
	    	this.jugadores.put(nom,ju);
	    }

	    
	    public Jugador find (String nom)
	    {
	        return jugadores.get(nom);
	    }
	    
	    public void ImprimirJugadores()
	    {
	    	
	      System.out.println(jugadores.values());
	    }
	    
	    //---------------------------------------------
	    
	    
	    public boolean HayJugadores() {
	    	return !jugadores.isEmpty();
	    }
	    
	    public LinkedList<VOJugadorRegistrado> listarJugadores() {
	    	
	    	LinkedList<VOJugadorRegistrado> listado = new LinkedList<VOJugadorRegistrado>();
	    	
	    	for(Entry<String, Jugador> entry : jugadores.entrySet()) {	    		  
	    	    Jugador jug = entry.getValue();
	    	    VOJugadorRegistrado VOjug = new VOJugadorRegistrado(jug.getNombre(), jug.getCodigo(), jug.getPuntajeTotal(), jug.getCantPartidas(), jug.getCociente());
	    	    listado.add(VOjug);
    		}
	    	return listado;
	    }
	    

	    public LinkedList<VOJugadorRanking> listarRanking() {	
	    	
	    	LinkedList<Jugador> listaFinal =  new LinkedList<Jugador>();
	    	int i = 0;
	    	  
	    	//Recorro arbol y voy copiando en el arreglo
	    	for(Entry<String, Jugador> entry : jugadores.entrySet()) {	
	    		
	    	    Jugador ju = entry.getValue();
	    	    int num = ju.getCociente();	    	
		    	int tamanio= listaFinal.size();
	    	    boolean agregado =false;

		    	if (tamanio>0) {
		    	    
		    	    i = 0;
		    	    while ((i < tamanio) && (agregado== false)){
		    	    	//Si el cociente es mayor o igual al del jugador en la posicion de la lista
		    	    	if (listaFinal.get(i).getCociente() <= num) {
		    	    		
		    	    		 listaFinal.add(i,ju);
		    	    		 agregado = true;	
		    	    	}
		    	    		
		    	    	i++;
		    	    }
		    	
		    	}else {
		    		listaFinal.add(ju);
		    		agregado = true;
		    	}
		    	if(!agregado)
		    		listaFinal.add(ju);

	    	} //Termina for de recorrida de arbol
	    	  
	    	//Recorro el arreglo ordenado y lo paso a VO
	    	LinkedList<VOJugadorRanking> listado = new LinkedList<VOJugadorRanking>();
	    	int contador = 0;
	    	for (int j = 0; j < listaFinal.size(); j++){
	    		contador++;
	    	    Jugador jug = listaFinal.get(j);
	    	    VOJugadorRanking VOjug = new VOJugadorRanking(jug.getNombre(), jug.getPuntajeTotal(), jug.getCantPartidas(), jug.getCociente(),contador);
	    	    listado.add(VOjug);
	    	}
	    	
	    	return listado;
	    
	    }
}
    
   

