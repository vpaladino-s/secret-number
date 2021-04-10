package Sistema.CapaGrafica.Ventanas.Administrador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Sistema.CapaGrafica.Controladores.ControladorListarJugadores;
import Sistema.CapaLogica.ValueObjects.VOJugadorRegistrado;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PanelListarJugadores extends JPanel {
	
	private ControladorListarJugadores controlador;
	private JLabel lblMensaje;
	private VentanaAdministrador vAdmin;
	private JTable table;



	/**
	 * Create the panel.
	 */
	public PanelListarJugadores(VentanaAdministrador v) {
		controlador = new ControladorListarJugadores(this);
		vAdmin=v;
		initialize();
		
	}
		
	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 373, 36, 0};
		gridBagLayout.rowHeights = new int[]{39, 0, 0, 141, 63, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		lblMensaje = new JLabel();
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				//Me guardo en que fila estoy parada
				int nrofila = table.rowAtPoint(e.getPoint());
				
				//Me guardo el nombre del usuario, fila-columna
				String nombrejugador = String.valueOf(table.getValueAt(nrofila, 0));
				
				//Llamo a la pantalla
				vAdmin.listarPartidas(nombrejugador);
			}
		});
		
		
		JLabel lblAtras = new JLabel("");
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				vAdmin.volverInicio();
			}
		});
		lblAtras.setIcon(new ImageIcon(PanelListarJugadores.class.getResource("/javax/swing/plaf/metal/icons/ocean/close-pressed.gif")));
		GridBagConstraints gbc_lblAtras = new GridBagConstraints();
		gbc_lblAtras.insets = new Insets(0, 0, 5, 0);
		gbc_lblAtras.gridx = 2;
		gbc_lblAtras.gridy = 0;
		add(lblAtras, gbc_lblAtras);
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		JLabel lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);
		
		JLabel lblListadoDeJugadores = new JLabel("Listado de Jugadores");
		GridBagConstraints gbc_lblListadoDeJugadores = new GridBagConstraints();
		gbc_lblListadoDeJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_lblListadoDeJugadores.gridx = 1;
		gbc_lblListadoDeJugadores.gridy = 2;
		add(lblListadoDeJugadores, gbc_lblListadoDeJugadores);
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		JLabel lblNewLabel = new JLabel("Click para ver las partidas del jugador");
		lblNewLabel.setIcon(new ImageIcon(PanelListarJugadores.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		add(lblNewLabel, gbc_lblNewLabel);
	
	}
	
	
	
	public void LlenarTabla()
	{
	
		DefaultTableModel modeloT = (new javax.swing.table.DefaultTableModel(
	            new Object [][] { },new String [] { "Nombre", "Codigo", "Puntaje Total", "Cociente" })
			{
	            Class[] types = new Class [] {
	                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
	            };
	            boolean[] canEdit = new boolean [] {
	                false, false, false, false
	            };

	            public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
        });
		Object[] columna = new Object[5];
		try {
			
			LinkedList<VOJugadorRegistrado> listado = controlador.ListarJugadores();
			
			int tamanio = listado.size();
			
			for (int j = 0; j < tamanio; j++) {
				
				columna[0] = listado.get(j).getNombre();
				columna[1] = listado.get(j).getCodigo();
				columna[2] = listado.get(j).getPuntajeTotal();
				columna[3] = listado.get(j).getCociente();
				modeloT.addRow(columna);
			}
			table.setModel(modeloT);
		} catch (RemoteException e) {
			this.setearError(e.getMessage());
			
		}
				
	}


	public void setearError(String e) {
		lblMensaje.setText(e);
	}
	
	public void popupMensaje(String e) {
		vAdmin.popupMensajeFrame(e);
	}
	
	public void MensajeError(String e) {
		vAdmin.popupMensajeFrame(e);
		System.exit(0); 
	}
}
