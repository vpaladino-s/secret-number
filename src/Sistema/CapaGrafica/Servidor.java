package Sistema.CapaGrafica;
import java.rmi.Naming;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.LinkedList;
import java.util.Properties;

import Sistema.CapaLogica.CapaLogica;
import Sistema.CapaLogica.Exceptions.JugadorYaExisteException;
import Sistema.CapaLogica.Exceptions.NoHayJugadoresException;
import Sistema.CapaLogica.Exceptions.PersistenciaException;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;
import Sistema.Persistencia.Respaldo;

import java.util.Random;


public class Servidor {

	public static void main(String[] args) throws JugadorYaExisteException, NoHayJugadoresException, PersistenciaException, RemoteException {

		
		//System.setProperty("java.rmi.server.hostname","192.168.1.103");
		try
		{ 
			InputStream input = new FileInputStream("config/configuracion.properties");
		    Properties prop = new Properties();
            prop.load(input);
            
			// pongo a correr el rmiregistry
			LocateRegistry.createRegistry(Integer.parseInt(prop.getProperty("PUERTO")));
			
			// instancio mi Objeto Remoto y lo publico
			CapaLogica fachada = CapaLogica.getInstancia();
			
			System.out.println ("Publicando Servidor");
			Naming.rebind("//"+prop.getProperty("SERVIDOR")+":"+prop.getProperty("PUERTO")+"/fachada", fachada);
			System.out.println ("Servidor levantado en "+prop.getProperty("SERVIDOR")+":"+prop.getProperty("PUERTO"));
			try {
	    		System.out.println("\n\nVoy a restaurar los datos");
				fachada.restaurar();
				System.out.println("Cargue el archivo de datos");
			}catch (Exception e){
				System.out.println("No hay archivo de respaldo, no se cargan datos");
			}
		}
		catch (RemoteException e){ 
			e.printStackTrace(); 
		}catch (MalformedURLException e){ 
			e.printStackTrace(); 
		} catch (IOException ex) {
			throw new PersistenciaException("Error a leer el properties");
			
		}
		
		

	}

}
