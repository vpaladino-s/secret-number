package Sistema.CapaGrafica.Ventanas.Administrador;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import Sistema.CapaGrafica.Controladores.ControladorListarPartidas;
import Sistema.CapaLogica.ValueObjects.VOPartidaJugador;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class PanelListarPartidas extends JPanel {

	private ControladorListarPartidas controlador;
	private JLabel lblError;
	private JTable table;
	private VentanaAdministrador vAdmin;
	
	
	/**
	 * Create the panel.
	 */
	public PanelListarPartidas(VentanaAdministrador v) {
		System.out.print("\npanel listarPartidas!!!!!");
		controlador = new ControladorListarPartidas(this);
		vAdmin=v;
		initialize();
	}
	
	
	private void initialize() {
		System.out.println("\nInitialize!!!!");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 363, 36, 0};
		gridBagLayout.rowHeights = new int[]{45, 0, 39, 244, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblError =new JLabel("");
		
		JLabel lblAtras = new JLabel("");
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				vAdmin.listarJugadores();
			}
		});
		lblAtras.setIcon(new ImageIcon(PanelListarPartidas.class.getResource("/javax/swing/plaf/metal/icons/ocean/close-pressed.gif")));
		
		GridBagConstraints gbc_lblAtras = new GridBagConstraints();
		gbc_lblAtras.insets = new Insets(0, 0, 5, 0);
		gbc_lblAtras.gridx = 2;
		gbc_lblAtras.gridy = 0;
		add(lblAtras, gbc_lblAtras);
		
		ImageIcon icon = new ImageIcon("Img/Logo80x80.png", "Pródigo");
		JLabel lblTitulo = new JLabel("Apofenia", icon, JLabel.RIGHT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		add(lblTitulo, gbc_label);
		
		JLabel lblListadoDeJugadores = new JLabel("Listado de Partidas");
		GridBagConstraints gbc_lblListadoDeJugadores = new GridBagConstraints();
		gbc_lblListadoDeJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_lblListadoDeJugadores.gridx = 1;
		gbc_lblListadoDeJugadores.gridy = 2;
		add(lblListadoDeJugadores, gbc_lblListadoDeJugadores);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
	}
	
	
	public void LlenarTabla(String nom)
	{
		
		DefaultTableModel modeloT = (new javax.swing.table.DefaultTableModel(
				
            new Object [][] { },new String [] { "Nro de Partida", "Nro Secreto", "Finalizada", "Puntaje Final", "Cantidad Intentos"}) {
			
			Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
			
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };


            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
		Object[] columna = new Object[6];
		try {
			
			LinkedList<VOPartidaJugador> listado = controlador.ListarPartidas(nom);
			
			int tamanio = listado.size();
			
			for (int j1 = 0; j1 < tamanio; j1++) {
				
				columna[0] = listado.get(j1).getNroPartida();
				columna[1] = listado.get(j1).getNroSecreto();
				columna[2] = listado.get(j1).getFinalizada();
				columna[3] = listado.get(j1).getPuntajeFinal();
				columna[4] = listado.get(j1).getCantIntentos();
				
				
				modeloT.addRow(columna);
			}
			
			table.setModel(modeloT);

		} catch (RemoteException e) {
			this.setearError(e.getMessage());
			
		}
				
	}


	public void setearError(String e) {
		lblError.setText(e);
	}

	public void popupMensaje(String e) {
		vAdmin.popupMensajeFrame(e);
	}
	public void MensajeError(String e) {
		vAdmin.popupMensajeFrame(e);
		System.exit(0); 
	}
}
