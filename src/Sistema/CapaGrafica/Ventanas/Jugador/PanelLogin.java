package Sistema.CapaGrafica.Ventanas.Jugador;

import java.awt.EventQueue;

import Sistema.CapaGrafica.Controladores.ControladorLogin;
import Sistema.CapaGrafica.Ventanas.InfoLogin;
import Sistema.CapaLogica.Exceptions.CodigoNoValidoException;
import Sistema.CapaLogica.Exceptions.JugadorNoExisteException;
import Sistema.CapaLogica.ValueObjects.VOPartidaEnCurso;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class PanelLogin extends JPanel{

	private JTextField textFieldNombre;
	private JPasswordField passwordCodigo;
	private ControladorLogin controlador;
	private JLabel lblMensaje;
	private VentanaInicioJugador vInicio;
	private JLabel lblTitulo;

	/**
	 * Create the application.
	 */
	public PanelLogin(VentanaInicioJugador v) {
		controlador = new ControladorLogin(this);
		vInicio=v;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 500, 350);		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 63, 81, 178, 0, 76, 0};
		gridBagLayout.rowHeights = new int[]{70, 90, 22, 22, 24, -11, 81, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = textFieldNombre.getText();
				char[] codigo = passwordCodigo.getPassword();
				try {
					controlador.login(nombre, codigo);
					
				}catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBackground(Color.WHITE);
		lblNombre.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 2;
		gbc_lblNombre.gridy = 2;
		add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(12);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.anchor = GridBagConstraints.NORTH;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 2;
		add(textFieldNombre, gbc_textFieldNombre);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.WEST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 2;
		gbc_lblCodigo.gridy = 3;
		add(lblCodigo, gbc_lblCodigo);
		
		passwordCodigo = new JPasswordField();
		passwordCodigo.setColumns(12);
		GridBagConstraints gbc_passwordCodigo = new GridBagConstraints();
		gbc_passwordCodigo.fill = GridBagConstraints.BOTH;
		gbc_passwordCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_passwordCodigo.gridx = 3;
		gbc_passwordCodigo.gridy = 3;
		add(passwordCodigo, gbc_passwordCodigo);
		
		lblMensaje = new JLabel("");
		lblMensaje.setBackground(new Color(255, 255, 255));
		lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMensaje.setForeground(Color.BLACK);
		
				lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
				gbc_lblMensaje.fill = GridBagConstraints.VERTICAL;
				gbc_lblMensaje.insets = new Insets(0, 0, 5, 5);
				gbc_lblMensaje.gridwidth = 2;
				gbc_lblMensaje.gridx = 2;
				gbc_lblMensaje.gridy = 4;
				add(lblMensaje, gbc_lblMensaje);
		GridBagConstraints gbc_btnIngresar = new GridBagConstraints();
		gbc_btnIngresar.insets = new Insets(0, 0, 5, 5);
		gbc_btnIngresar.anchor = GridBagConstraints.NORTH;
		gbc_btnIngresar.gridx = 3;
		gbc_btnIngresar.gridy = 5;
		add(btnIngresar, gbc_btnIngresar);
	}
	
	public void MensajeError(String e) {
		JOptionPane.showMessageDialog(this, e);
		System.exit(0); 
	}
	
	public void setearError(String e) {
		lblMensaje.setText(e);
	}
	
	public void ingresar() {
		InfoLogin j = InfoLogin.getInstancia();
		j.setCodigo(String.valueOf( passwordCodigo.getPassword()));
		j.setUsuario( textFieldNombre.getText());
		vInicio.setJugador(j);
		vInicio.ingresoOk();
	}
	

}