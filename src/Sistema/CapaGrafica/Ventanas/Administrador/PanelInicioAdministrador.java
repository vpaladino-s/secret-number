package Sistema.CapaGrafica.Ventanas.Administrador;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import Sistema.CapaGrafica.Controladores.ControladorGuardar;
import Sistema.CapaGrafica.Controladores.ControladorListarJugadores;
import Sistema.CapaGrafica.Controladores.ControladorLogin;
import Sistema.CapaGrafica.Ventanas.Jugador.VentanaInicioJugador;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelInicioAdministrador extends JPanel {
	
	private ControladorGuardar controlador;
	private VentanaAdministrador vInicio;

	public PanelInicioAdministrador(VentanaAdministrador v) {
		setBorder(new MatteBorder(2, 2, 2, 2, (Color) SystemColor.inactiveCaption));
		controlador = new ControladorGuardar(this);
		vInicio=v;
		initialize();
	}
	
	/**
	 * Create the panel.
	 * @return 
	 */
	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{42, 152, 66, 180, 0};
		gridBagLayout.rowHeights = new int[]{44, 44, 0, 25, 10, 0, 10, 25, 10, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 50.0, 1.0, 50.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		JButton btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.guardar();
					popupMensaje("Guardado correctamante");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		JButton btnRegistrarJugador = new JButton("Registrar Jugador");
		btnRegistrarJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vInicio.registrarJugador();
			}
		});
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		JLabel lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);
		GridBagConstraints gbc_btnRegistrarJugador = new GridBagConstraints();
		gbc_btnRegistrarJugador.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegistrarJugador.anchor = GridBagConstraints.NORTH;
		gbc_btnRegistrarJugador.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarJugador.gridx = 2;
		gbc_btnRegistrarJugador.gridy = 3;
		add(btnRegistrarJugador, gbc_btnRegistrarJugador);
		
		JButton btnListarJugadores = new JButton("Ver Jugadores");
		btnListarJugadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vInicio.listarJugadores();
			}
		});
		
		JButton btnNewButton = new JButton("Ranking Global");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				vInicio.rankingGlobal();

			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		add(btnNewButton, gbc_btnNewButton);
		GridBagConstraints gbc_btnListarJugadores = new GridBagConstraints();
		gbc_btnListarJugadores.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListarJugadores.anchor = GridBagConstraints.NORTH;
		gbc_btnListarJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_btnListarJugadores.gridx = 2;
		gbc_btnListarJugadores.gridy = 7;
		add(btnListarJugadores, gbc_btnListarJugadores);
		GridBagConstraints gbc_btnGuardarCambios = new GridBagConstraints();
		gbc_btnGuardarCambios.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGuardarCambios.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardarCambios.anchor = GridBagConstraints.NORTH;
		gbc_btnGuardarCambios.gridx = 2;
		gbc_btnGuardarCambios.gridy = 9;
		add(btnGuardarCambios, gbc_btnGuardarCambios);

	}
	
	public void MensajeError(String e) {
		JOptionPane.showMessageDialog(this, e);
		System.exit(0); 
	}
	
	public void popupMensaje(String e) {
		vInicio.popupMensajeFrame(e);
	}
	

}
