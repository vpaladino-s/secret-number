package Sistema.CapaGrafica;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

import Sistema.CapaLogica.ICapaLogica;
import Sistema.CapaLogica.ResultadoIntento;
import Sistema.CapaLogica.Exceptions.JugadorYaExisteException;
import Sistema.CapaLogica.Exceptions.PersistenciaException;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOJugadorRanking;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;
import Sistema.CapaLogica.ValueObjects.VOPartidaJugador;

public class Cliente {


	public static void main(String[] args) {
		
		System.out.println("Empiezo pruebas\n");

		try
		{ 
			
			//Levanto las configuraciones
			Properties prop = new Properties();
			String nomArch = "config/configuracion.properties";
			prop.load (new FileInputStream (nomArch));
			String ip = prop.getProperty("SERVIDOR");
			String puerto = prop.getProperty("PUERTO");
			String ruta = "//" + ip + ":" + puerto + "/fachada";
			
			//Hago el lookup
			ICapaLogica fachada = (ICapaLogica) Naming.lookup(ruta);
			
			//PRUEBAS DE LAS EXEPCIONES PARA LISTAR JUGADORES REGISTRADOS (Se ejecuta la primera vez que levanto el servidor)
			System.out.println("\n---------PRUEBO ERROR LISTAR JUGADORES ------------- ");
			try {
				fachada.listarJugadoresRegistrados();
			
			}catch(Exception e) {
				System.out.println(e);
			}
			
			//PRUEBAS DE LAS EXEPCIONES PARA RANKING JUGADORES (Se ejecuta la primera vez que levanto el servidor)
			System.out.println("\n---------PRUEBO ERROR RANKING JUGADORES ------------- ");
			try {
				fachada.rankingGlobal();
			
			}catch(Exception e) {
				System.out.println(e);
			}
			
			
			
			try {
	    		System.out.println("\n\nVoy a restaurar los datos");
				fachada.restaurar();
				System.out.println("Cargue el archivo de datos");
			}catch (Exception e){
				System.out.println("No hay archivo de respaldo, no se cargan datos");
			}
			
			
			//------INGRESAR JUGADOR

			Random rand = new Random();
			
			//Creo un jugador X.Pepexxx
			int num = rand.nextInt(5000);
			char c = (char) (rand.nextInt(26) + 'a');			
			VOJugador nuevo = new VOJugador(String.valueOf(c).toUpperCase() + ".Pepe"+num, "pepe");
			System.out.println("\n------Registro jugador "+ nuevo.getNombre() +" ----Contrase�a--"+nuevo.getCodigo());
			fachada.registrarJugador(nuevo);
			
			//PRUEBAS DE LAS EXEPCIONES PARA REGISTRAR JUGADOR
			System.out.println("\n---------PRUEBO REGISTRAR JUGADOR QUE YA ESTA REGISTRADO ");
			
			try {
				System.out.println(" Quiero registrar a: "+nuevo.getNombre());
				fachada.registrarJugador(nuevo);
			
			}catch(Exception e) {
				System.out.println(e);
			}
			
			LinkedList<VOJugadorRegistrado> r = fachada.listarJugadoresRegistrados();
			
			System.out.println("\n--------Imprimo registrados--------\n");
			for(VOJugadorRegistrado re : r) {
				System.out.println("--"+re.getNombre());
			}
			
			//Inicia una partida
			System.out.println("--\nVa a iniciar partida para "+nuevo.getNombre()+":");
			boolean inicia = fachada.iniciarPartida(nuevo);
			System.out.println("  ---->Resultado: " + inicia);
			
			//Creo un jugador X.Tomxxx
			int num4 = rand.nextInt(5000);
			c = (char) (rand.nextInt(26) + 'a');
			VOJugador nuevo3 = new VOJugador(String.valueOf(c).toUpperCase() + ".Tom"+num4, "tom");
			System.out.println("\n------Registro jugador "+ nuevo3.getNombre() +" ------");
			fachada.registrarJugador(nuevo3);
			
			r = fachada.listarJugadoresRegistrados();
			
			System.out.println("\n--------Imprimo registrados--------\n");
			for(VOJugadorRegistrado re : r) {
				System.out.println("--"+re.getNombre());
			}
			
			//Inicia una partida
			System.out.println("\n--Va a iniciar partida para "+nuevo3.getNombre()+":");
			inicia = fachada.iniciarPartida(nuevo3);
			System.out.println("  ---->Resultado: " + inicia);

			//Creo un jugador X.Johnxxx
			int num5 = rand.nextInt(5000);
			c = (char) (rand.nextInt(26) + 'a');
			VOJugador nuevo4 = new VOJugador(String.valueOf(c).toUpperCase() + ".John"+num5, "john");
			System.out.println("\n------Registro jugador "+ nuevo4.getNombre() +" ------");
			fachada.registrarJugador(nuevo4);
			
			r = fachada.listarJugadoresRegistrados();
			
			System.out.println("\n--------Imprimo registrados--------\n");
			for(VOJugadorRegistrado re : r) {
				System.out.println("--"+re.getNombre());
			}
			
			//Inicia una partida
			System.out.println("\n--Va a iniciar partida para "+nuevo4.getNombre()+":");
			inicia = fachada.iniciarPartida(nuevo4);
			System.out.println("  ---->Resultado: " + inicia);
			

			//Jugadores se loguean
			System.out.println("\n---------------JUGADORES LOGUEADOS-------------\n");
			boolean logueado = false;
			
			System.out.println("---------LOGUEADO CORRECTO:----\n");
			logueado = fachada.loguearJugador(nuevo);
			System.out.println("Jugador: "+nuevo.getNombre()+"----logueado: "+logueado);
			
			logueado = fachada.loguearJugador(nuevo3);
			System.out.println("Jugador: "+nuevo3.getNombre()+"----logueado: "+logueado);
			
			logueado = fachada.loguearJugador(nuevo4);
			System.out.println("Jugador: "+nuevo4.getNombre()+"----logueado: "+logueado);
			
			//PRUEBAS DE LAS EXEPCIONES ES LOGUEADO
			
			System.out.println("\n--------- PRUEBA LOGUEADO INCORRECTO:----\n");
			
			System.out.println("Prueba nombre mal:");
			try {
				VOJugador usuariomal = new VOJugador("Prueba", "pepe");
				logueado = fachada.loguearJugador(usuariomal);
			}catch (Exception e) {
				System.out.println(e);
			}
			
			System.out.println("Prueba Codigo mal:");
			try {
				VOJugador usuarioContraseniaMal = new VOJugador(nuevo.getNombre(), "pruebaMal");
				logueado = fachada.loguearJugador(usuarioContraseniaMal);
			}catch (Exception e) {
				System.out.println(e);
			}
				
			
//			//------Listar partidas de JUGADOR
			
			LinkedList<VOPartidaJugador> p = fachada.listarJugadoresPartidas(nuevo3.getNombre());
			
			System.out.println("\n--------Imprimo Partidas de jugador "+ nuevo3.getNombre() +" con solo un intento--------\n");
			System.out.println("Partidas de: " + nuevo3.getNombre());
			for(VOPartidaJugador par : p) 
			{
				System.out.println("-Finalizada-"+par.getFinalizada());
				System.out.println("-Cant Intentos-"+par.getCantIntentos());
				System.out.println("-NroPartidas-"+par.getNroPartida());
				System.out.println("-Nro secreto-"+par.getNroSecreto());
				System.out.println("-Puntaje final-"+par.getPuntajeFinal());
			}

			
			//------Listar partidas de JUGADOR
			System.out.println("---------------REALIZO MAS INTENTOS PARA "+nuevo3.getNombre()+" hasta que adivine-------------\n");
			ResultadoIntento res;
			int i=0;
			do {
				res = fachada.realizarIntento(nuevo3, i);
				//System.out.println("  ---->Resultado: " + res);
				i++;
			}while (res !=ResultadoIntento.IGUAL);
			System.out.println("---->Adivino------");
			
			System.out.println("---------------IMPRIMO PARTIDA "+nuevo3.getNombre()+" LUEGO DE ACERTAR-------------");
			LinkedList<VOPartidaJugador> pt = fachada.listarJugadoresPartidas(nuevo3.getNombre());
			
			for(VOPartidaJugador par : pt) 
			{
				System.out.println("-Finalizada-"+par.getFinalizada());
				System.out.println("-Cant Intentos-"+par.getCantIntentos());
				System.out.println("-NroPartida-"+par.getNroPartida());
				System.out.println("-Nro secreto-"+par.getNroSecreto());
				System.out.println("-Puntaje final-"+par.getPuntajeFinal());
			}
			
			System.out.println("---------------NUEVA PARTIDA PARA "+nuevo3.getNombre()+"-------------\n");
			System.out.println("--Va a iniciar partida para "+nuevo3.getNombre()+":");
			inicia = fachada.iniciarPartida(nuevo3);
			System.out.println("  ---->Resultado: " + inicia);
			
			
			System.out.println("\n---------------PRUEBA PARTIDA EN CURSO PARA " + nuevo3.getNombre()+"-------------\n");
			VOPartidaEnCurso partidaCurso = fachada.partidaEnCurso(nuevo3);
			//Imprimo partida en curso
			System.out.println("-Finalizada-"+partidaCurso.getFinalizada());
			System.out.println("-Cant Intentos-"+partidaCurso.getCantIntentos());
			System.out.println("-NroPartida-"+partidaCurso.getNroPartida());
			System.out.println("-Puntaje final-"+partidaCurso.getPuntajeFinal());
			
			
			System.out.println("---------------REALIZO MAS INTENTOS PARA TOM EN SU SEGUNDA PARTIDA-------------\n");
			 i=0;
			do {
				res = fachada.realizarIntento(nuevo3, i);
				i++;
			}while (res !=ResultadoIntento.IGUAL);
			System.out.println("---->Adivino------");
			
			System.out.println("\n---------------PRUEBA ERROR PARTIDA EN CURSO PARA " + nuevo3.getNombre()+"-------------\n");
			try {
				partidaCurso = fachada.partidaEnCurso(nuevo3);
				//Imprimo partida en curso
				System.out.println("-Finalizada-"+partidaCurso.getFinalizada());
				System.out.println("-Cant Intentos-"+partidaCurso.getCantIntentos());
				System.out.println("-NroPartida-"+partidaCurso.getNroPartida());
				System.out.println("-Puntaje final-"+partidaCurso.getPuntajeFinal());
			}catch(Exception e) {
				System.out.println(e);
			}
			
			System.out.println("\n---------------IMPRIMO PARTIDAS de " + nuevo3.getNombre() + " -------------");
			LinkedList<VOPartidaJugador> pt2 = fachada.listarJugadoresPartidas(nuevo3.getNombre());
			
			System.out.println("Partidas de: " + nuevo3.getNombre());
			for(VOPartidaJugador par : pt2) 
			{
				System.out.println("-Cant Intentos-"+par.getCantIntentos());
				System.out.println("-NroPartidas-"+par.getNroPartida());
				System.out.println("-Nro secreto-"+par.getNroSecreto());
				System.out.println("-Puntaje final-"+par.getPuntajeFinal());
				System.out.println("-------");
			}
			
			System.out.println("---REALIZO MAS INTENTOS PARA " + nuevo.getNombre() + " hasta que adivine---");
			
			i=0;
			do {
				res = fachada.realizarIntento(nuevo, i);
				//System.out.println("  ---->Resultado: " + res);
				i++;
			}while (res !=ResultadoIntento.IGUAL);
			
			//PRUEBAS DE LAS EXEPCIONES PARA REALIZAR INTENTO
			
			System.out.println("\n---------PRUEBO REALIZAR INTENTO INCORRECTO PARA "+nuevo3.getNombre()+":----\n");
			
			System.out.println("Prueba no tiene partidas en curso:");
			try {
				System.out.println("-"+nuevo3.getNombre()+" va a realizar un intento");
				res = fachada.realizarIntento(nuevo3, 3);
				System.out.print(" ----> Resultado: " + res);
			}catch(Exception e) {
				System.out.println(e);
			}
	
			//------------ IMPRIMO RANKING GLOBAL--------
			System.out.println("\n------IMPRIMO RANKING------\n");
			
			LinkedList<VOJugadorRanking> ranking = fachada.rankingGlobal();
			
			for(VOJugadorRanking ran : ranking) {
				System.out.println("--"+ran.getNombre()+"-"+ran.getCociente());
			}
		
			//------------ PRUEBA ABANDONO PARTIDA -------------
			System.out.println("\n------PRUEBA ABANDONO PARTIDA------\n");

			//Pruebo un intento fallido porque no tiene partida en curso
			try {
				System.out.println("-El jugador " + nuevo.getNombre() + " va a abandonar la partida pero no tiene partidas en curso");
				fachada.abandonarPartida(nuevo);
			}catch(Exception e) {
				System.out.println(e);
			}
			//Pruebo un intento fallido porque no tiene partida en curso
			System.out.println("\n-El jugador " + nuevo4.getNombre() + " va a abandonar la partida");
			fachada.abandonarPartida(nuevo4);
			System.out.println("-El jugador " + nuevo4.getNombre() + " abandonó la partida");
			
			
			//Inicia una partida
			System.out.print("\n-"+nuevo.getNombre()+" va a iniciar una partida nueva");
			inicia = fachada.iniciarPartida(nuevo);
			System.out.println(" ----> Resultado: " + inicia);
			
			System.out.println("-Ahora " + nuevo.getNombre() + " va a abandonar la partida sin realizar intento");
			fachada.abandonarPartida(nuevo);
			System.out.println("-El jugador " + nuevo.getNombre() + " abandonó la partida");

			
			//------------ IMPRIMO RANKING GLOBAL--------
			System.out.println("\n------IMPRIMO RANKING para ver donde quedó al abandonar partida "+nuevo.getNombre()+"------\n");
			
			ranking = fachada.rankingGlobal();
			
			
			for(VOJugadorRanking ran : ranking) {
				System.out.println("--"+ran.getNombre()+"-"+ran.getCociente());
			}
			
			
			System.out.println("\n------Guardo Datos------\n");
			fachada.guardarCambios();
			
		
		
		}catch (Exception e) {
			System.out.println(e);
		}		
		
		

	}

}
