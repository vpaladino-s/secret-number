package Sistema.CapaGrafica.Ventanas.Administrador;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import Sistema.CapaGrafica.Ventanas.InfoLogin;
import java.awt.Container;


public class VentanaAdministrador extends JFrame{

	private CardLayout card;
	private Container c;
	private InfoLogin jugador;
	private PanelRegistrarJugador PRegistrar;
	private PanelInicioAdministrador PNInicio;
	private PanelListarJugadores PNListJugadores;
	private PanelListarPartidas PNListPartidas;
	private PanelRankingGlobal PNRanking;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		VentanaAdministrador cl=new VentanaAdministrador();
		cl.setSize(450,450);
		cl.setVisible(true);
		cl.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	/**
	 * Create the application.
	 */
	public VentanaAdministrador() {
		this.setTitle("Apofenia - Menú Administración");
		c=getContentPane();
		card=new CardLayout(0,0);
		//create CardLayout object with 40 hor space and 30 ver space
		c.setLayout(card);
		
		PRegistrar = new PanelRegistrarJugador(this);
		PNInicio = new PanelInicioAdministrador(this);
		PNListJugadores = new PanelListarJugadores(this);
		PNListPartidas = new PanelListarPartidas(this);
		PNRanking = new PanelRankingGlobal(this);

		
		c.add("PNInicio",PNInicio);
		c.add("PRegistrar",PRegistrar);
		c.add("PNListJugadores",PNListJugadores);
		c.add("PNListPartidas",PNListPartidas);
		c.add("PNRanking",PNRanking);

	}
	
	public void registrarJugador() {
		setTitle("Apofenia - Registrar jugador");
		card.show(getContentPane(), "PRegistrar");
	}
	
	public void rankingGlobal() {
		PNRanking.LlenarTabla();
		setTitle("Apofenia - Ranking global de Jugadores");
		card.show(getContentPane(), "PNRanking");
	}
	
	public void listarJugadores() {
		PNListJugadores.LlenarTabla();
		setTitle("Apofenia - Listado de Jugadores");
		card.show(getContentPane(), "PNListJugadores");
	}
	
	public void listarPartidas(String nom) {
		setTitle("Apofenia - Listado de partidas de " + nom);
		PNListPartidas.LlenarTabla(nom);
		card.show(getContentPane(), "PNListPartidas");
	}
	
	public void volverInicio() {
		setTitle("Apofenia - Menú Administración");
		card.show(getContentPane(), "PNInicio");
	}
	
	
	public void setJugador(InfoLogin j) {
		jugador = j;
	}
	
	public InfoLogin getJugador() {
		return jugador;
	}
	
	public void popupMensajeFrame(String e) {
		JOptionPane.showMessageDialog(this, e);
	}

}
