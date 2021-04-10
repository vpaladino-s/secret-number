package Sistema.CapaGrafica.Ventanas.Jugador;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Sistema.CapaGrafica.Controladores.ControladorJugar;
import Sistema.CapaGrafica.Ventanas.InfoLogin;
import Sistema.CapaGrafica.Ventanas.Administrador.PanelRegistrarJugador;
import Sistema.CapaLogica.ResultadoIntento;
import Sistema.CapaLogica.ValueObjects.VOJugador;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.SwingConstants;;

public class PanelJugar extends JPanel {

	private ControladorJugar controlador;
	private VentanaInicioJugador vInicio;
	private JTextField textIngreso;
	private int contador;
	private JButton btnNuevaPartida;
	private JButton btnAbandonar;
	private JButton btnIntento;
	private JLabel lblTitulo;
	private JLabel lblNroPuntaje;
	private JLabel lblNroIntentos;
	
	
	/**
	 * Create the application.
	 */
	public PanelJugar(VentanaInicioJugador v) {
	
		controlador = new ControladorJugar(this);
		vInicio=v;
		contador =0;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 500, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{71, 132, 111, 0, 101, 79, 0};
		gridBagLayout.rowHeights = new int[]{-5, 49, 38, 28, 25, 0, 1, 37, 44, 8, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);
		
		
		JLabel lblNewLabel = new JLabel("Ingrese el numero");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		textIngreso = new JTextField();
		textIngreso.setEnabled(false);
		textIngreso.setColumns(10);
		GridBagConstraints gbc_textIngreso = new GridBagConstraints();
		gbc_textIngreso.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIngreso.insets = new Insets(0, 0, 5, 5);
		gbc_textIngreso.gridx = 2;
		gbc_textIngreso.gridy = 2;
		add(textIngreso, gbc_textIngreso);
		
		
		
		btnIntento = new JButton("Intentar");
		GridBagConstraints gbc_btnIntento = new GridBagConstraints();
		gbc_btnIntento.insets = new Insets(0, 0, 5, 5);
		gbc_btnIntento.gridx = 3;
		gbc_btnIntento.gridy = 2;
		add(btnIntento, gbc_btnIntento);
		btnIntento.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				
				try {
					int num = Integer.parseInt(textIngreso.getText());
					
					if(num>0 && num<=1000) {
						
						ResultadoIntento res = controlador.realizarIntento(num);
				
						//Registro la cantidad de intentos
						contador+=1;
						lblNroIntentos.setText(Integer.toString(contador));
						
						//Registro el puntaje de la partida
						InfoLogin j = vInicio.getJugador();
						String codigo = j.getCodigo();
						String nombre = j.getUsuario();
						VOJugador jugador= new VOJugador(nombre, codigo);
						int puntaje = controlador.devolverpuntajePartida(contador);
						lblNroPuntaje.setText(Integer.toString(puntaje));
						btnNuevaPartida.setEnabled(false);
						if (res== ResultadoIntento.IGUAL) {
							
							btnNuevaPartida.setEnabled(true);
							btnIntento.setEnabled(false);
							btnAbandonar.setEnabled(false);
							textIngreso.setEnabled(false);
							setMensaje("Acertó !!");

						}else {
							//Despliego JSOptionPane
							if(res!=ResultadoIntento.ERROR)
								setMensaje("El numero secreto es "+res + " al ingresado");
							else
								setMensaje("Hubo un error al ingresar el intento!");
						}
								
						
					}else {
						setMensaje("El numero debe ser entre 1 y 1000");
					}
				}catch(NumberFormatException e) {
					setMensaje("Debe ingresar un número");
				}catch(Exception e) {
					System.out.print(e);
				}
				
			}
		});
		
		JLabel lblPuntaje = new JLabel("Puntaje");
		GridBagConstraints gbc_lblPuntaje = new GridBagConstraints();
		gbc_lblPuntaje.anchor = GridBagConstraints.EAST;
		gbc_lblPuntaje.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuntaje.gridx = 1;
		gbc_lblPuntaje.gridy = 4;
		add(lblPuntaje, gbc_lblPuntaje);
		
		lblNroPuntaje = new JLabel("");
		lblNroPuntaje.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNroPuntaje = new GridBagConstraints();
		gbc_lblNroPuntaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNroPuntaje.insets = new Insets(0, 0, 5, 5);
		gbc_lblNroPuntaje.gridx = 2;
		gbc_lblNroPuntaje.gridy = 4;
		add(lblNroPuntaje, gbc_lblNroPuntaje);
		
		JLabel lblCantIntentos = new JLabel("#Intentos");
		GridBagConstraints gbc_lblCantIntentos = new GridBagConstraints();
		gbc_lblCantIntentos.anchor = GridBagConstraints.EAST;
		gbc_lblCantIntentos.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantIntentos.gridx = 1;
		gbc_lblCantIntentos.gridy = 5;
		add(lblCantIntentos, gbc_lblCantIntentos);
		
		btnNuevaPartida = new JButton("Nueva Partida");
		btnNuevaPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				InfoLogin j = vInicio.getJugador();
				String codigo = j.getCodigo();
				String nombre = j.getUsuario();
				VOJugador jugador= new VOJugador(nombre, codigo);

				controlador.nuevaPartida(jugador);
				btnNuevaPartida.setEnabled(false);
				btnAbandonar.setEnabled(true);
				btnIntento.setEnabled(true);
				textIngreso.setEnabled(true);
				textIngreso.setText("");
				lblNroPuntaje.setText("1000");
				lblNroIntentos.setText("0");
				contador = 0;			
			}
		});
		
		lblNroIntentos = new JLabel("");
		lblNroIntentos.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNroIntentos = new GridBagConstraints();
		gbc_lblNroIntentos.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNroIntentos.anchor = GridBagConstraints.EAST;
		gbc_lblNroIntentos.insets = new Insets(0, 0, 5, 5);
		gbc_lblNroIntentos.gridx = 2;
		gbc_lblNroIntentos.gridy = 5;
		add(lblNroIntentos, gbc_lblNroIntentos);
		GridBagConstraints gbc_btnNuevaPartida = new GridBagConstraints();
		gbc_btnNuevaPartida.anchor = GridBagConstraints.EAST;
		gbc_btnNuevaPartida.insets = new Insets(0, 0, 5, 5);
		gbc_btnNuevaPartida.gridx = 1;
		gbc_btnNuevaPartida.gridy = 8;
		add(btnNuevaPartida, gbc_btnNuevaPartida);
		
		btnAbandonar = new JButton("Abandonar");
		btnAbandonar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoLogin j = vInicio.getJugador();
				String codigo = j.getCodigo();
				String nombre = j.getUsuario();
				VOJugador jugador= new VOJugador(nombre, codigo);
				controlador.abandonarPartidaEnCurso(jugador);
				
				btnAbandonar.setEnabled(false);
				btnIntento.setEnabled(false);

				btnNuevaPartida.setEnabled(true);
				textIngreso.setEnabled(false);

				textIngreso.setText(null);
				lblNroIntentos.setText(null);
				lblNroPuntaje.setText(null);
				
			}
		});
		GridBagConstraints gbc_btnAbandonar = new GridBagConstraints();
		gbc_btnAbandonar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbandonar.anchor = GridBagConstraints.WEST;
		gbc_btnAbandonar.gridx = 4;
		gbc_btnAbandonar.gridy = 8;
		add(btnAbandonar, gbc_btnAbandonar);
	}
	
	public void setMensaje(String e) {
		
		vInicio.popupMensajeFrame(e);
	}
	
	public void validarPartida() {
	
		String codigo = vInicio.getJugador().getCodigo();
		String nombre = vInicio.getJugador().getUsuario();
		VOJugador jugador= new VOJugador(nombre, codigo);
	    controlador.partidaEnCurso(jugador);
	}
	
	public void tienePartida(VOPartidaEnCurso partida) {
		lblNroIntentos.setEnabled(true);
		lblNroIntentos.setText(String.valueOf(partida.getCantIntentos()));
		lblNroPuntaje.setEnabled(true);
		textIngreso.setEnabled(true);
		lblNroPuntaje.setText(String.valueOf(controlador.devolverpuntajePartida(partida.getCantIntentos())));
		btnNuevaPartida.setEnabled(false);
		
	}
	
	public void noTienePartida() {
		btnIntento.setEnabled(false);
		btnAbandonar.setEnabled(false);
		btnNuevaPartida.setEnabled(true);
		vInicio.popupMensajeFrame(new String("No tiene partidas en curso, inicie una nueva!"));
	}
	
	public void MensajeError(String e) {
		JOptionPane.showMessageDialog(this, e);
		vInicio.setVisible(false);
	}
	
	
}
