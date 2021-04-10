package Sistema.CapaLogica.ValueObjects;
import java.io.Serializable;

public class VOJugador implements Serializable {

	 private String nombre;
	 private String codigo;
	 
	 
	 public VOJugador(String nom,String cod){
		 nombre = nom;
		 codigo =cod;
				 
	 }
	 
	 public String getNombre(){ 
		 return nombre;
	 }
	 
	 
	 public String getCodigo(){ 
		 return codigo;
	 }
}
