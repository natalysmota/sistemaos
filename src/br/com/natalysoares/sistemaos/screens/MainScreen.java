package br.com.natalysoares.sistemaos.screens;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import br.com.natalysoares.sistemaos.dao.ConnectionModule;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class MainScreen extends JFrame {

	//===== DATABASE =====//
	Connection connection = null;
	PreparedStatement prepared = null;
	ResultSet result = null;
	
	private static final long serialVersionUID = 1L;
	
	//===== ATTRIBUTES =====//
	private JMenu mnRelatorio;
	private JMenuItem mnItemUsuarios;
	private JLabel lblUsuarioLogado;
	private JLabel lblData;
	private JLabel lblTipoUsuario;
	private Panel panelDesktop;

	//===== COMPONENTS =====//
	public MainScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 610);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		URL url = this.getClass().getResource("/br/com/natalysoares/sistemaos/icons/logo.png");
		Image logoTitulo = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(logoTitulo);
		
		panelDesktop = new Panel();
		panelDesktop.setBackground(Color.WHITE);
		panelDesktop.setBounds(10, 10, 660, 540);
		getContentPane().add(panelDesktop);
		panelDesktop.setLayout(null);
		
		Panel panelIndentificador = new Panel();
		panelIndentificador.setBounds(678, 10, 246, 540);
		getContentPane().add(panelIndentificador);
		panelIndentificador.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(40, 354, 169, 140);
		ImageIcon logo = new ImageIcon(MainScreen.class.getResource("/br/com/natalysoares/sistemaos/icons/logo.png"));
		Image imagemLogo = logo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(imagemLogo));
		panelIndentificador.add(lblLogo);
		
		lblUsuarioLogado = new JLabel("Usuario");
		lblUsuarioLogado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuarioLogado.setBounds(40, 47, 169, 28);
		panelIndentificador.add(lblUsuarioLogado);
		
		lblTipoUsuario = new JLabel("Tipo de usuario");
		lblTipoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTipoUsuario.setBounds(40, 24, 95, 24);
		panelIndentificador.add(lblTipoUsuario);
		
		lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblData.setBounds(40, 85, 169, 28);
		panelIndentificador.add(lblData);
		
		JMenuBar menuPrincipal = new JMenuBar();
		setJMenuBar(menuPrincipal);
		
		//== OPCAO - CADASTRO ==//
		JMenu mnCadastro = new JMenu("Cadastro");
		menuPrincipal.add(mnCadastro);
		
		//== OPCAO - CADASTRO DE CLIENTE ==//
		JMenuItem mnItemCliente = new JMenuItem("Cliente");
		mnItemCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScreenCliente screenCliente = new ScreenCliente();
				screenCliente.setVisible(true);
				panelDesktop.add(screenCliente);
			}
		});
		mnItemCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnCadastro.add(mnItemCliente);
		
		//== OPCAO - CADASTRO DE OS ==//
		JMenuItem mnItemOs = new JMenuItem("OS");
		mnItemOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScreenOs screenOs = new ScreenOs();
				screenOs.setVisible(true);
				panelDesktop.add(screenOs);
			}
		});
		mnItemOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		mnCadastro.add(mnItemOs);
		
		//== OPCAO - CADASTRO DE USUARIO ==//
		mnItemUsuarios = new JMenuItem("Usuários");
		mnItemUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScreenUsuario screenUsuario = new ScreenUsuario();
				screenUsuario.setVisible(true);
				panelDesktop.add(screenUsuario);
			}
		});
		mnItemUsuarios.setEnabled(false);
		mnItemUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK));
		mnCadastro.add(mnItemUsuarios);
		
		//== OPCAO - RELATÓRIO ==//
		mnRelatorio = new JMenu("Relatório");
		mnRelatorio.setEnabled(false);
		menuPrincipal.add(mnRelatorio);
		
		//== OPCAO - RELATÓRIOS - CLIENTES ==//
		JMenuItem mnRelatorioClientes = new JMenuItem("Clientes");
		mnRelatorioClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir o relatório de clientes?","Atenção", JOptionPane.YES_NO_OPTION);
				
				if(confirma == JOptionPane.YES_OPTION) {
					
					//JasperReport
					try {
						// Classe JasperPrint
						JasperPrint print = JasperFillManager.fillReport("C:/Users/natal/Desktop/reports/Clientes.jasper", null, connection);
						
						// Exibe relatório - JasperViewer
						JasperViewer.viewReport(print, false);
					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnRelatorioClientes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnRelatorio.add(mnRelatorioClientes);
		
		//== OPCAO - RELATÓRIOS - CLIENTES ==//
		JMenuItem mnItemServicos = new JMenuItem("Serviços");
		mnItemServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir o relatório de serviços?", "Atenção", JOptionPane.YES_NO_OPTION);
				
				if(confirma == JOptionPane.YES_OPTION) {
					try {
						JasperPrint print = JasperFillManager.fillReport("C:/Users/natal/Desktop/reports/servicos.jasper", null, connection);
						
						JasperViewer.viewReport(print, false);
					} catch(Exception e) {
						JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnItemServicos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnRelatorio.add(mnItemServicos);
		
		//== OPCAO - AJUDA ==//
		JMenu mnAjuda = new JMenu("Ajuda");
		menuPrincipal.add(mnAjuda);
		
		//== OPCAO - AJUDA - SOBRE ==//
		JMenuItem mnItemSobre = new JMenuItem("Sobre");
		mnItemSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScreenSobre sobre = new ScreenSobre();
				sobre.setVisible(true);
			}
		});
		mnItemSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.ALT_MASK));
		mnAjuda.add(mnItemSobre);
		
		//== OPCAO - OPÇÕES ==//
		JMenu mnOpcoes = new JMenu("Opções");
		menuPrincipal.add(mnOpcoes);
		
		//== OPCAO - OPCOES - SAIR ==//
		JMenuItem mnItemSair = new JMenuItem("Sair");
		mnItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
				
				if(sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mnItemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnOpcoes.add(mnItemSair);
		
		//==================== FUNCTIONS ====================//
		
		// Quando a janela principal for ativada
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				// Troca data pela atual
				Date data  = new Date();
				DateFormat dataFormat = DateFormat.getDateInstance(DateFormat.SHORT);
				lblData.setText(dataFormat.format(data));
			}
		});
		
		//==================== DATABASE ====================//

		//Retorna informações do banco de dados
		connection = ConnectionModule.connector();
	}
	
	//==================== GETTERS ====================//
	public JMenu getMnRelatorio() {
		return mnRelatorio;
	}
	public JMenuItem getMnItemUsuarios() {
		return mnItemUsuarios;
	}
	public JLabel getLblUsuarioLogado() {
		return lblUsuarioLogado;
	}
	public JLabel getLblData() {
		return lblData;
	}
	public JLabel getLblTipoUsuario() {
		return lblTipoUsuario;
	}
}
