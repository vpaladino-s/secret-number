package Sistema.CapaGrafica.Controladores;

import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidaEnCursoException;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidasException;
import Sistema.CapaLogica.Exceptions.JugadorYaExisteException;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import Sistema.CapaGrafica.Ventanas.*;
import Sistema.CapaGrafica.Ventanas.Administrador.VentanaAdministrador;
import Sistema.CapaGrafica.Ventanas.Administrador.PanelRegistrarJugador;

public class ControladorRegistrarJugador {
	
	/* tengo como atributo remoto a la fachada y como atributo local a mi ventana */
	private ICapaLogica fachada;
	private PanelRegistrarJugador ven;
	
	
	public ControladorRegistrarJugador (PanelRegistrarJugador v)
	{
		/* aqu� me guardo la referencia a mi ventana y adem�s hago el lookup
		para acceder remotamente a la fachada */

		ven = v;
		try {
			//Levanto las configuraciones
			Properties prop = new Properties();
			String nomArch = "config/configuracion.properties";			
			prop.load (new FileInputStream (nomArch));
			String ip = prop.getProperty("SERVIDOR");
			String puerto = prop.getProperty("PUERTO");
			String ruta = "//" + ip + ":" + puerto + "/fachada";
			//Hago el lookup
			fachada = (ICapaLogica) Naming.lookup(ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ven.MensajeError("Error al conectarse con el servidor");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			ven.MensajeError("Error al conectarse con el servidor");
		}
	}
	
	public void altaJugador (String nombre, String codigo) throws  RemoteException
	{
			
			try {
				VOJugador ju = new VOJugador(nombre, codigo);
				fachada.registrarJugador(ju);
				ven.setearMensaje(nombre + " registrado correctamente !");
				
			}catch ( JugadorYaExisteException e) {
				ven.setearMensaje(e.getMessage());
			}catch (RemoteException e) {
				ven.MensajeError("Error al conectarse con el servidor");
			
			}
			
	
			
	}
	
	
}

