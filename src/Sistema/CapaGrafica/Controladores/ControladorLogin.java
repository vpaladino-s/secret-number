package Sistema.CapaGrafica.Controladores;

import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.Jugador;
import Sistema.CapaLogica.Exceptions.CodigoNoValidoException;
import Sistema.CapaLogica.Exceptions.JugadorNoExisteException;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidaEnCursoException;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidasException;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;

import Sistema.CapaGrafica.Ventanas.Administrador.VentanaAdministrador;
import Sistema.CapaGrafica.Ventanas.Jugador.PanelLogin;

public class ControladorLogin {
	
	/* tengo como atributo remoto a la fachada y como atributo local a mi ventana */
	private ICapaLogica fachada;
	private PanelLogin login;
	
	
	public ControladorLogin (PanelLogin l) 
	{
		/* aqu� me guardo la referencia a mi ventana y adem�s hago el lookup
		para acceder remotamente a la fachada */

		login = l;
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
			login.MensajeError("Error al conectarse con el servidor");
		} catch (NotBoundException e) {
			login.MensajeError("Error al conectarse con el servidor");
		}

		
	}
	
	public void login (String nombre, char[] codigo) throws  RemoteException
	{
		try
			{ 
			
			/* desde aqui accedo a la Fachada y hago los chequeos
			necesarios, luego decido si emito al usuario un mensaje de
			exito o uno de error */
			
				VOJugador ju = new VOJugador(nombre, String.valueOf(codigo));
				fachada.loguearJugador(ju);
				login.ingresar();		

			}catch ( CodigoNoValidoException e) {
				
				login.setearError(e.getMessage());
					
			}catch ( JugadorNoExisteException e) {
			
				login.setearError(e.getMessage());
			}catch ( RemoteException e) {
			
				login.MensajeError("Error al conectarse con el servidor");
			}
	}
	
	
}
	
