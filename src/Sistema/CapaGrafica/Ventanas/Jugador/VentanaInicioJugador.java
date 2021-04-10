package Sistema.CapaGrafica.Ventanas.Jugador;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Sistema.CapaGrafica.Ventanas.InfoLogin;
import Sistema.CapaLogica.CapaLogica;
import Sistema.CapaLogica.ICapaLogica;

public class VentanaInicioJugador extends JFrame{

	private CardLayout card;
	private Container c;
	private InfoLogin jugador;
	private PanelLogin PLogin ;
	private PanelJugar PNIntento ;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {	
		VentanaInicioJugador cl=new VentanaInicioJugador();
		cl.setSize(500,350);
		cl.setVisible(true);
		cl.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
	}
		

	/**
	 * Create the application.
	 */	
	public VentanaInicioJugador() {
		this.setTitle("Apofenia - Login de Jugador");
		c=getContentPane();
		card=new CardLayout(0,0);
		c.setLayout(card);
		
		PLogin = new PanelLogin(this);
		PNIntento = new PanelJugar(this);
		c.add("PLogin",PLogin);
		c.add("PNIntento",PNIntento);
	}

	public void ingresoOk() {
		this.setTitle("Apofenia - Jugar");
		PNIntento.validarPartida();
		card.show(getContentPane(), "PNIntento");
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
