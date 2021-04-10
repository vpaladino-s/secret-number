package Sistema.CapaLogica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Properties;

import Sistema.CapaLogica.Exceptions.*;
import Sistema.CapaLogica.ValueObjects.*;
import Sistema.CapaLogica.Monitor;
import Sistema.Persistencia.*;
import Sistema.CapaLogica.Jugadores;



public class CapaLogica extends UnicastRemoteObject implements ICapaLogica{
	
	private static CapaLogica instancia;
	private Jugadores ju;
	private Monitor monit;
	
	private CapaLogica() throws RemoteException{
		 ju = new Jugadores();
		 monit = new Monitor();
	 }
	
	public static CapaLogica getInstancia() throws RemoteException { 
		if(instancia== null) 
			instancia = new CapaLogica();
		return instancia;
	}
	

	
	
	//----------ADMINISTRADOR-------------
	/** MÃ©todo registrarJugador que registra un nuevo jugador
	* @param nuevo recibe VOJugador con los datos del jugador
	* @throws JugadorYaExisteException No se puede crear el usuario porque ya existe*/
	public void registrarJugador(VOJugador nuevo) throws JugadorYaExisteException,RemoteException {
		
		monit.comienzoEscritura();
		if(this.ju.member(nuevo.getNombre())){
			monit.terminoEscritura();
	        throw new JugadorYaExisteException("El jugador "+nuevo.getNombre()+" ya está registrado");
		}else {
			
			Jugador jug = new Jugador(nuevo.getNombre(),nuevo.getCodigo());
			this.ju.insert(nuevo.getNombre(), jug);
			monit.terminoEscritura();
		}
	}
	
	/** MÃ©todo listarJugadoresRegistrados que devuelve todos los jugadores registrados
	* @return retorna una LinkedList de VOJugadorRegistrado
	* @throws NoHayJugadoresException*/
	public LinkedList<VOJugadorRegistrado> listarJugadoresRegistrados() throws NoHayJugadoresException,RemoteException {
		
		monit.comienzoLectura();
		if(this.ju.HayJugadores()) {
			LinkedList<VOJugadorRegistrado> listado = this.ju.listarJugadores();
			monit.terminoLectura( );
			return listado; 

		}else {			
			monit.terminoLectura( );
	        throw new NoHayJugadoresException("No hay jugadores registrados");
		}
	}
	
	/** MÃ©todo listarJugadoresPartidas que devuelve las partidas de un Jugador
	* @param nom recibe el nombre de un jugador
	* @return retorna una LinkedList de VOPartidaJugador 
	* @throws JugadorNoExisteException 
	* @throws JugadorNoTienePartidasException*/
	public LinkedList<VOPartidaJugador> listarJugadoresPartidas(String nom) throws JugadorNoExisteException, JugadorNoTienePartidasException,RemoteException {
		
		LinkedList<VOPartidaJugador> listadoPartidas;
		monit.comienzoLectura();
		if(this.ju.member(nom)){
			Jugador jgr = this.ju.find(nom);
			if(jgr.hayPartidas()) {
				
				listadoPartidas =  jgr.listarPartidasJugador();
				monit.terminoLectura( );
				return listadoPartidas;

			}else {
				
				monit.terminoLectura( );
		        throw new JugadorNoTienePartidasException("El jugador " + nom + " no tiene partidas");
			}
		}else {
			monit.terminoLectura( );
	        throw new JugadorNoExisteException("El jugador " + nom + " no existe");
		}
		
	}
	
	/** MÃ©todo guardarCambios persiste los jugadores en un archivo
	 * @throws PersistenciaException*/
	public void guardarCambios() throws PersistenciaException,RemoteException {
		
		monit.comienzoLectura();
		try (InputStream input = new FileInputStream("config/configuracion.properties")) {
	            Properties prop = new Properties();
	            prop.load(input);
	    		Respaldo persistencia = new Respaldo();
	    		persistencia.respaldar(prop.getProperty("RESPALDO"), this.ju);	
	    		monit.terminoLectura();
	    		
        } catch (Exception ex) {
    		monit.terminoLectura();
            throw new PersistenciaException("Error al guardar cambios");
        }

	}
	
	/** MÃ©todo restaurar recupera los jugadores de un archivo
	 * @throws PersistenciaException 
	 * @throws ClassNotFoundException*/
	public void restaurar() throws PersistenciaException, ClassNotFoundException,RemoteException {
		monit.comienzoEscritura();
		
		try (InputStream input = new FileInputStream("config/configuracion.properties")) {
			
            Properties prop = new Properties();
            prop.load(input);
            
			Respaldo persistencia = new Respaldo();
			this.ju=persistencia.recuperar(prop.getProperty("RESPALDO"));
			
			monit.terminoEscritura();
			
		} catch (Exception ex) {
			
    		monit.terminoEscritura();
            throw new PersistenciaException("Error a leer el archivo");
            
        }
	}
	
	
	
	//----------------------------------------------JUGADOR-------------------------------------------
	
	/** MÃ©todo loguearJugador que devuelve true si los datos del usuario ingresados son vÃ¡lidos
	* @param jugador recibe un VOJugador(nombre y cÃ³digo)
	* @return retorna un boolean 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException*/
	public boolean loguearJugador(VOJugador jugador) throws JugadorNoExisteException, CodigoNoValidoException,RemoteException{
				
		monit.comienzoLectura();
		boolean resultado = false;
		
		if(this.ju.member(jugador.getNombre())){
			Jugador jgr = this.ju.find(jugador.getNombre());
			if(jgr.getCodigo().equals(jugador.getCodigo())) {
				resultado = true;
				monit.terminoLectura( );
				return resultado;
			}else {
				monit.terminoLectura( );
				throw new CodigoNoValidoException("El código no es válido");
			}
		}else {
			monit.terminoLectura( );
	        throw new JugadorNoExisteException("El jugador " + jugador.getNombre() + " no existe");
		}
	}
	
	
	
	/** MÃ©todo iniciarPartida si los datos del usuario ingresados son vÃ¡lidos y no tiene una partida en curso,inicia partida nueva
	* @param jugador recibe un VOJugador(nombre y cÃ³digo)
	* @return retorna un boolean 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException
	* @throws JugadorNoTienePartidaEnCursoException*/
	public boolean iniciarPartida(VOJugador jugador) throws PartidaNoFinalizadaException, CodigoNoValidoException, JugadorNoExisteException,RemoteException{
				
		monit.comienzoEscritura();
	
		if(this.ju.member(jugador.getNombre())){
			
			Jugador jgr = this.ju.find(jugador.getNombre());
			if(jgr.getCodigo().equals(jugador.getCodigo())) {
				
				boolean ultimapartidaestaencurso=jgr.ultimaPartidaEnCurso();
				
				 if (ultimapartidaestaencurso) {
					 
					 monit.terminoEscritura();
					 throw new PartidaNoFinalizadaException("El jugador " + jugador.getNombre() + " tiene una partida en curso");
					 
				 }else {
					 
					 //Este es el que hace toda la logica
					 jgr.crearPartida();
					
					 monit.terminoEscritura();
					 return true;
				 }
				 
			}else {
				monit.terminoEscritura();
				throw new CodigoNoValidoException("El cÃ³digo no es vÃ¡lido");
			}
		}else { 
			monit.terminoEscritura();
			throw new JugadorNoExisteException("El jugador " + jugador.getNombre() + " no existe");
		}
	}
	
	
	
	/** MÃ©todo partidaEnCurso devuelve la partida en curso si los datos del usuario ingresados son vÃ¡lidos
	* @param jugador recibe un VOJugador(nombre y cÃ³digo)
	* @return retorna un VOPartidaEnCurso 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException*/
	public VOPartidaEnCurso partidaEnCurso(VOJugador jugador) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException,JugadorNoTienePartidaEnCursoException,RemoteException{
				
		monit.comienzoLectura();
		
		if(this.ju.member(jugador.getNombre())){
			Jugador jgr = this.ju.find(jugador.getNombre());
			
			if(jgr.getCodigo().equals(jugador.getCodigo())) {
				if (jgr.hayPartidas()) {
					
					 if (jgr.ultimaPartidaEnCurso()) {
						 
						 Partidas p = jgr.DevolverPartidas();
						 Partida ultima = p.Ultimo();
						 VOPartidaEnCurso resu = new VOPartidaEnCurso(ultima.getNroPartida(),ultima.getFinalizada(),ultima.getCantIntentos(),ultima.getPuntajeFinal());
						 monit.terminoLectura();
						 return resu;
						 
					 }else {
						 
						 monit.terminoLectura();
						 throw new JugadorNoTienePartidaEnCursoException("El jugador " + jugador.getNombre() + " no tiene partida en curso");
					 }
				}else {
					 monit.terminoLectura();
			        throw new JugadorNoTienePartidasException("El jugador " + jugador.getNombre() + " no tiene partidas");
				}
			}else {
				monit.terminoLectura();
				throw new CodigoNoValidoException("El cÃ³digo no es vÃ¡lido");
			}	
		}else {
			 monit.terminoLectura();
	        throw new JugadorNoExisteException("El jugador " + jugador.getNombre() + " no existe");		
		}		
}
		
	
	/** MÃ©todo realizarIntento suma uno a la cantidad de intentos y devuelve enumerado
	* @param jugador recibe un VOJugador(nombre y cÃ³digo)
	* @return retorna un VOPartidaEnCurso 
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException*/
	public ResultadoIntento realizarIntento(VOJugador jugador,int nro_ingresado) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException,JugadorNoTienePartidaEnCursoException,RemoteException{
		
		monit.comienzoEscritura();
		ResultadoIntento resu; 
		
		if(this.ju.member(jugador.getNombre())) {
			Jugador jgr = this.ju.find(jugador.getNombre());
			
			if(jgr.getCodigo().equals(jugador.getCodigo())) {
				if (jgr.hayPartidas()) {
					
					 if (jgr.ultimaPartidaEnCurso()) {
						 
						 int nro_secreto = jgr.darUltimaPartida().getNroSecreto();
						 
						 int nro_intento = jgr.darUltimaPartida().getCantIntentos();
						 nro_intento = nro_intento+1;
						 jgr.darUltimaPartida().setCantNroIntentos(nro_intento);
						 						
						 if (nro_ingresado > nro_secreto) {
							 resu =ResultadoIntento.MENOR;
						 }else {
							 if (nro_ingresado < nro_secreto) {
								 resu =ResultadoIntento.MAYOR;
							 }else {
								 
								 //finaliza partida, calcula puntaje final y actualiza cociente
								 //System.out.println("El jugador " + jgr.getNombre() + " acerto al numero " + nro_secreto);
								 resu =ResultadoIntento.IGUAL;
								 
								 jgr.DevolverPartidas().Ultimo().setCantNroIntentos(nro_intento);
								 
								 jgr.DevolverPartidas().Ultimo().setFinalizada(true);
								 
								 jgr.DevolverPartidas().Ultimo().calcularPuntaje();
								 
								 //int nro_intento_par = jgr.DevolverPartidas().Ultimo().getCantIntentos();
								 int puntaje_jugador = jgr.getPuntajeTotal();
								 
								 float aux = jgr.DevolverPartidas().Ultimo().getPuntajeFinal()+puntaje_jugador;
								 int puntaje_total = Math.round(aux);
								 jgr.setPuntajeTotal(puntaje_total);
					
								 
								 //Aumento la cantidad de partidas finalizadas
								 int cant = jgr.getCantPartidas();
								 cant++;
								 jgr.SetCantidadPartidas(cant);
								 
								 
								 //Actualizo el puntaje del jugador
								 jgr.actualizarPuntajeCTE();
							 }
						 }  
					 }else {
						 resu =ResultadoIntento.ERROR;
						
						 monit.terminoEscritura();
						 throw new JugadorNoTienePartidaEnCursoException("El jugador " + jugador.getNombre() + " no tiene partida en curso");
					 }
				}else {
					 resu =ResultadoIntento.ERROR;
					
					 monit.terminoEscritura();
			        throw new JugadorNoTienePartidasException("El jugador " + jugador.getNombre() + " no tiene partidas");
				}
			}else {
				resu =ResultadoIntento.ERROR;
				
				 monit.terminoEscritura();
				throw new CodigoNoValidoException("El codigo no es valido");
			}	
		}else {
			resu =ResultadoIntento.ERROR;
			
			 monit.terminoEscritura();
	        throw new JugadorNoExisteException("El jugador " + jugador.getNombre() + " no existe");		
		}
		monit.terminoEscritura();
		return resu;
	}
						 
	
	/** MÃ©todo partidaEnCurso la partida en curso si los datos del usuario ingresados son vÃ¡lidos
	* @param jugador recibe un VOJugador(nombre y cÃ³digo)
	* @throws JugadorNoExisteException 
	* @throws CodigoNoValidoException
	* @throws JugadorNoTienePartidasException
	 * @throws JugadorNoTienePartidaEnCursoException */
	public void abandonarPartida(VOJugador jugador) throws JugadorNoTienePartidasException, CodigoNoValidoException, JugadorNoExisteException, JugadorNoTienePartidaEnCursoException,RemoteException{
				
		monit.comienzoEscritura();
		if(this.ju.member(jugador.getNombre())){
			Jugador jgr = this.ju.find(jugador.getNombre());
			
			if(jgr.getCodigo().equals(jugador.getCodigo())) {
				if (jgr.hayPartidas()) {
					
					 if (jgr.ultimaPartidaEnCurso()) {
						 
						 //Setea como finalizada
						 Partidas p = jgr.DevolverPartidas();
						 Partida ultima = p.Ultimo();

						 ultima.setFinalizada(true);
						 
						 //Aumenta cantidad de partidas finalizadas
						 int cant = jgr.getCantPartidas();
						 cant++;
						 jgr.SetCantidadPartidas(cant);
						 
						 //Actualiza cociente
						 jgr.actualizarPuntajeCTE();
						 
						 monit.terminoEscritura();
					
					}else {
						monit.terminoEscritura();
						throw new JugadorNoTienePartidaEnCursoException("El jugador " + jugador.getNombre() + " no tiene partida en curso");
					}	 
				}else {
					monit.terminoEscritura();
			        throw new JugadorNoTienePartidasException("El jugador " + jugador.getNombre() + " no tiene partidas");
				}
			}else {
				monit.terminoEscritura();
				throw new CodigoNoValidoException("El cÃ³digo no es vÃ¡lido");
		}	
		}else {
			monit.terminoEscritura();
	        throw new JugadorNoExisteException("El jugador " + jugador.getNombre() + " no existe");
		}
	}

	
	//--------------PUBLICO GENEREAL-------------
	
	/** MÃ©todo rankingGlobal que devuelve el ranking
	* @return retorna una LinkedList de VOJugadorRanking
	* @throws NoHayJugadoresException*/
	public LinkedList<VOJugadorRanking> rankingGlobal() throws NoHayJugadoresException,RemoteException{
		
		monit.comienzoLectura();
				
		if(this.ju.HayJugadores()) {
			
			LinkedList<VOJugadorRanking> listado = this.ju.listarRanking();
			monit.terminoLectura( );
			return listado; 
			
		}else {
			monit.terminoLectura();
	        throw new NoHayJugadoresException("No hay jugadores registrados");
	        
		}
	}
	
}
