package Sistema.CapaLogica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import Sistema.CapaLogica.Exceptions.*;
import Sistema.CapaLogica.ValueObjects.*;

public interface ICapaLogica extends Remote{
	
	//----------ADMINISTRADOR-------------
	/** Método registrarJugador que registra un nuevo jugador
	* @param nuevo recibe VOJugador con los datos del jugador
	* @throws JugadorYaExisteException No se puede crear el usuario porque ya existe*/
	public void registrarJugador(VOJugador nuevo) throws JugadorYaExisteException, RemoteException ;
	
	/** Método listarJugadoresRegistrados que devuelve todos los jugadores registrados
	* @return retorna una LinkedList de VOJugadorRegistrado
	* @throws NoHayJugadoresException*/
	public LinkedList<VOJugadorRegistrado> listarJugadoresRegistrados() throws NoHayJugadoresException, RemoteException  ;
	
	/** Método listarJugadoresPartidas que devuelve las partidas de un Jugador
	* @param nom recibe el nombre de un jugador
	* @return retorna una LinkedList de VOPartidaJugador 
	* @throws JugadorNoExisteException 
	* @throws JugadorNoTienePartidasException*/
	public LinkedList<VOPartidaJugador> listarJugadoresPartidas(String nom) throws JugadorNoExisteException, JugadorNoTienePartidasException, RemoteException  ;
	
	/** Método guardarCambios persiste los jugadores en un archivo
	 * @throws PersistenciaException*/
	public void guardarCambios() throws PersistenciaException,RemoteException ;
	
	/** Método restaurar recupera los jugadores de un archivo
	 * @throws PersistenciaException 
	 * @throws ClassNotFoundException*/
	public void restaurar() throws PersistenciaException, ClassNotFoundException, RemoteException  ;
	
	//----------------------------------------------JUGADOR-------------------------------------------
	
	/** Método loguearJugador que devuelve true si los datos del usuario ingresados son válidos
	* @param nuevo recibe un VOJugador(nombre y código)
	* @return retorna un boolean 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException*/
	public boolean loguearJugador(VOJugador nuevo) throws JugadorNoExisteException, CodigoNoValidoException, RemoteException ;
	
	
	/** Método iniciarPartida si los datos del usuario ingresados son válidos y no tiene una partida en curso,inicia partida nueva
	* @param jugador recibe un VOJugador(nombre y código)
	* @return retorna un boolean 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException
	* @throws JugadorNoTienePartidaEnCursoException*/
	public boolean iniciarPartida(VOJugador jugador) throws PartidaNoFinalizadaException, CodigoNoValidoException, JugadorNoExisteException, RemoteException ;
	
	/** Método partidaEnCurso devuelve la partida en curso si los datos del usuario ingresados son válidos
	* @param jugador recibe un VOJugador(nombre y código)
	* @return retorna un VOPartidaEnCurso 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException*/
	public VOPartidaEnCurso partidaEnCurso(VOJugador jugador) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException,JugadorNoTienePartidaEnCursoException, RemoteException ;
		
	
	/** Método realizarIntento suma uno a la cantidad de intentos y devuelve enumerado
	* @param jugador recibe un VOJugador(nombre y código)
	* @return retorna un VOPartidaEnCurso 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException*/
	public ResultadoIntento realizarIntento(VOJugador jugador,int nro_ingresado) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException,JugadorNoTienePartidaEnCursoException, RemoteException ;
						 
	/** Método partidaEnCurso la partida en curso si los datos del usuario ingresados son válidos
	* @param jugador recibe un VOJugador(nombre y código)
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException
	 * @throws JugadorNoTienePartidaEnCursoException */
	public void abandonarPartida(VOJugador jugador) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException, JugadorNoTienePartidaEnCursoException, RemoteException ;
	
	//--------------PUBLICO GENEREAL-------------
	
	/** Método rankingGlobal que devuelve el ranking
	* @return retorna una LinkedList de VOJugadorRanking
	* @throws NoHayJugadoresException*/
	public LinkedList<VOJugadorRanking> rankingGlobal() throws NoHayJugadoresException, RemoteException ;
	
}
