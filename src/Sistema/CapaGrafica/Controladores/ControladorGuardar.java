package Sistema.CapaGrafica.Controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import Sistema.CapaGrafica.Ventanas.Administrador.PanelInicioAdministrador;
import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.Exceptions.PersistenciaException;

public class ControladorGuardar {
	
	private ICapaLogica fachada;
	private PanelInicioAdministrador panelAdmin;
	
	
	/* aqu� me guardo la referencia a mi ventana y adem�s hago el lookup
	para acceder remotamente a la fachada */
	public ControladorGuardar (PanelInicioAdministrador l) {
		
		panelAdmin = l;
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
		} catch (IOException e1) {
			panelAdmin.MensajeError("Error al conectarse con el servidor");
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			panelAdmin.MensajeError("Error al conectarse con el servidor");
		}
	}



	public void guardar()  throws  RemoteException{
		
		try {
			fachada.guardarCambios();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			panelAdmin.MensajeError("Error al conectarse con el servidor");
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			panelAdmin.popupMensaje(e.getMessage());
		}
			
		}
}
