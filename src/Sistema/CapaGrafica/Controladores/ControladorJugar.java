package Sistema.CapaGrafica.Controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import Sistema.CapaGrafica.Ventanas.InfoLogin;
import Sistema.CapaGrafica.Ventanas.Jugador.PanelLogin;
import Sistema.CapaGrafica.Ventanas.Jugador.PanelJugar;
import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.ResultadoIntento;
import Sistema.CapaLogica.Exceptions.CodigoNoValidoException;
import Sistema.CapaLogica.Exceptions.JugadorNoExisteException;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidaEnCursoException;
import Sistema.CapaLogica.Exceptions.JugadorNoTienePartidasException;
import Sistema.CapaLogica.Exceptions.PartidaNoFinalizadaException;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

public class ControladorJugar {

	
	private ICapaLogica fachada;
	private PanelJugar pjugar;
	
	
	
	public ControladorJugar (PanelJugar i) 
	{
		/* aqu� me guardo la referencia a mi ventana y adem�s hago el lookup
		para acceder remotamente a la fachada */

		pjugar = i;
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
			pjugar.MensajeError("Error al conectarse con el servidor");
			
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			pjugar.MensajeError("Error al conectarse con el servidor");
		
		}

	}
	
	
	public ResultadoIntento realizarIntento ( int nro)
	{
		
		try {
			InfoLogin i = InfoLogin.getInstancia();
			String nom = i.getUsuario();
			String cod = i.getCodigo();
			VOJugador j = new VOJugador(nom,cod);
			return fachada.realizarIntento(j, nro);
			
		} catch (JugadorNoTienePartidasException | CodigoNoValidoException | JugadorNoExisteException
				| JugadorNoTienePartidaEnCursoException e) {
			// TODO Auto-generated catch block
			pjugar.setMensaje(e.getMessage());
			return ResultadoIntento.ERROR ;
		}catch (RemoteException e) {
			pjugar.MensajeError("Error al conectarse con el servidor");
			return ResultadoIntento.ERROR ;
		}
		
	}
	
	public int devolverpuntajePartida ( int intentos) {
		int resu = 1000;
		if(intentos>0) {
			resu = 1000/intentos;
		}
		return resu;
		
	}
		
	public void abandonarPartidaEnCurso(VOJugador ju){
		
		try {
			fachada.abandonarPartida(ju);
		} catch ( JugadorNoTienePartidasException | CodigoNoValidoException
				| JugadorNoExisteException | JugadorNoTienePartidaEnCursoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (RemoteException e) {
			pjugar.MensajeError("Error al conectarse con el servidor");
		}
	}
		
	public void nuevaPartida(VOJugador jugador) {
		
		try {
			fachada.iniciarPartida(jugador);
			pjugar.setMensaje("Comienza nueva partida");

		} catch (PartidaNoFinalizadaException | CodigoNoValidoException | JugadorNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (RemoteException e) {
			pjugar.MensajeError("Error al conectarse con el servidor");
		}
	}
	
	public void partidaEnCurso(VOJugador jugador) {
		
		try {
			VOPartidaEnCurso partida =fachada.partidaEnCurso(jugador);
			pjugar.tienePartida(partida);
		}catch(JugadorNoTienePartidaEnCursoException | JugadorNoTienePartidasException e) {
			pjugar.noTienePartida();
		}catch (CodigoNoValidoException | JugadorNoExisteException  e) {
			pjugar.setMensaje("Error en el usuario!");
		}catch (RemoteException e) {
			pjugar.MensajeError("Error al conectarse con el servidor");
		}
	}

		
}