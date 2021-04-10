package Sistema.Persistencia;
import java.io.*;

import Sistema.CapaLogica.*;
import Sistema.CapaLogica.Exceptions.PersistenciaException;


public class Respaldo {

	public void respaldar (String nomArch, Jugadores j)throws PersistenciaException{ 
		
		try
			{ 
				// Abro el archivo y creo un flujo de comunicaci�n hacia �l
				FileOutputStream f = new FileOutputStream(nomArch);
				ObjectOutputStream o = new ObjectOutputStream(f);
				
				// Escribo el arreglo de Jugadores en el archivo a trav�s del flujo
				o.writeObject (j);
				o.close();
				f.close();
			}
		
			catch (IOException e){ 
				e.printStackTrace();

				throw new PersistenciaException("error respaldar");
			}
		}
	
	
	 public Jugadores recuperar (String nomArch) throws PersistenciaException, ClassNotFoundException{
		 
		 try
			{ // Abro el archivo y creo un flujo de comunicacion
				FileInputStream f = new FileInputStream(nomArch);
				ObjectInputStream o = new ObjectInputStream(f);
				// Leo el arreglo de veh�culos desde el archivo a trav�s del flujo
				Jugadores j = (Jugadores) o.readObject();
				o.close();
				f.close();
				return j;
			}catch (IOException e ){ 
				throw new PersistenciaException("Error al recuperar - Hubo problemas de IO");
			}catch(ClassNotFoundException e2){
				throw new ClassNotFoundException("Error al recuperar - No se encontro la clase");
			}
	
		}
	 
}
