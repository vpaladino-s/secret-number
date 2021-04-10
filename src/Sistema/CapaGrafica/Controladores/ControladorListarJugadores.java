package Sistema.CapaGrafica.Controladores;

import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.Exceptions.NoHayJugadoresException;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;
import Sistema.CapaLogica.ValueObjects.VOPartidaJugador;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;

import Sistema.CapaGrafica.Ventanas.Administrador.PanelListarJugadores;
import Sistema.CapaGrafica.Ventanas.Administrador.PanelListarPartidas;


public class ControladorListarJugadores {
	
	/* tengo como atributo remoto a la fachada y como atributo local a mi ventana */
	private ICapaLogica fachada;
	private PanelListarJugadores vlistar;
	
	
	public ControladorListarJugadores (PanelListarJugadores v) 
	{
		/* aqu� me guardo la referencia a mi ventana y adem�s hago el lookup
		para acceder remotamente a la fachada */

		vlistar = v;
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
			vlistar.MensajeError("Error al conectarse con el servidor");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			vlistar.MensajeError("Error al conectarse con el servidor");
		}
		
		
	}
	
	public LinkedList<VOJugadorRegistrado> ListarJugadores() throws RemoteException
	{	
		LinkedList<VOJugadorRegistrado> listado = new LinkedList<>();
		try
			{ 
			
				listado=fachada.listarJugadoresRegistrados();

			}catch ( NoHayJugadoresException e) {
				
					vlistar.popupMensaje(e.getMessage());
					
			}catch (RemoteException e) {
				vlistar.MensajeError("Error al conectarse con el servidor");
			
			}
		return listado;	
		
	}

	

}
	