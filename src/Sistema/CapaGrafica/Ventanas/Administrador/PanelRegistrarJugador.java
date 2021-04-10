package Sistema.CapaGrafica.Ventanas.Administrador;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import Sistema.CapaGrafica.Controladores.ControladorRegistrarJugador;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelRegistrarJugador extends JPanel {
	private JTextField textFieldNombre;
	private JTextField textCodigo;
	private VentanaAdministrador vAdmin;
	private ControladorRegistrarJugador controlador;
	private JLabel lblMensaje;

	
	/**
	 * Create the application.
	 */
	public PanelRegistrarJugador(VentanaAdministrador v) {
		vAdmin=v;
		controlador = new ControladorRegistrarJugador(this);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{42, 71, 68, 169, 49, 47, 0};
		gridBagLayout.rowHeights = new int[]{44, 44, 0, 22, 22, 39, 25, 1, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAtras = new JLabel("");
		

		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldNombre.setText("");
				textCodigo.setText("");
				lblMensaje.setText("");
				vAdmin.volverInicio();
			}
		});
		lblAtras.setIcon(new ImageIcon(PanelRegistrarJugador.class.getResource("/javax/swing/plaf/metal/icons/ocean/close-pressed.gif")));
		GridBagConstraints gbc_lblAtras = new GridBagConstraints();
		gbc_lblAtras.insets = new Insets(0, 0, 5, 0);
		gbc_lblAtras.gridx = 5;
		gbc_lblAtras.gridy = 0;
		add(lblAtras, gbc_lblAtras);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		JLabel lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		panel.add(lblTitulo);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 2;
		gbc_lblNombre.gridy = 3;
		add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.anchor = GridBagConstraints.NORTH;
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 3;
		add(textFieldNombre, gbc_textFieldNombre);
		JButton btnRegistrarJugador = new JButton("Registrar Jugador");
		btnRegistrarJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String nombre = textFieldNombre.getText();
				String codigo = textCodigo.getText() ;
				try {
					if(!nombre.equals("")) {
						if(!codigo.equals("")) {
							controlador.altaJugador(nombre, codigo);
							textFieldNombre.setText("");
							textCodigo.setText("");
						}else {
							setearMensaje("El código no puede estar vacío");
						}
					}else {
						setearMensaje("El nombre no puede estar vacío");
					}
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
			}
		});
		
		JLabel lblCodigo = new JLabel("Codigo");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.WEST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 2;
		gbc_lblCodigo.gridy = 4;
		add(lblCodigo, gbc_lblCodigo);
		
		textCodigo = new JTextField();
		textCodigo.setColumns(10);
		GridBagConstraints gbc_textCodigo = new GridBagConstraints();
		gbc_textCodigo.anchor = GridBagConstraints.NORTH;
		gbc_textCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_textCodigo.gridx = 3;
		gbc_textCodigo.gridy = 4;
		add(textCodigo, gbc_textCodigo);
		GridBagConstraints gbc_btnRegistrarJugador = new GridBagConstraints();
		gbc_btnRegistrarJugador.anchor = GridBagConstraints.NORTH;
		gbc_btnRegistrarJugador.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarJugador.gridx = 3;
		gbc_btnRegistrarJugador.gridy = 6;
		add(btnRegistrarJugador, gbc_btnRegistrarJugador);
		
		lblMensaje = new JLabel("");
		GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
		gbc_lblMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_lblMensaje.anchor = GridBagConstraints.NORTH;
		gbc_lblMensaje.gridx = 3;
		gbc_lblMensaje.gridy = 5;
		add(lblMensaje, gbc_lblMensaje);

	}
	
	public void setearMensaje(String e) {
		lblMensaje.setText(e);
	}

	public void MensajeError(String e) {
		JOptionPane.showMessageDialog(this, e);
		System.exit(0);
	}
	
	public void popupMensaje(String e) {
		vAdmin.popupMensajeFrame(e);
	}
}
