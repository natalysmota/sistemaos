package br.com.natalysoares.sistemaos.screens;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;

import br.com.natalysoares.sistemaos.dao.ConnectionModule;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.sql.*;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class ScreenLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//===== DATABASE =====//
	Connection connection = null;
	PreparedStatement prepared = null;
	ResultSet result = null;
	
	//===== LOGIN =====//
	@SuppressWarnings("deprecation")
	public void login() {
		String sql = "SELECT * FROM usuarios WHERE login = ? and senha = ?";
		
		try {
			prepared = connection.prepareStatement(sql);

			prepared.setString(1, txtUsuario.getText());
			prepared.setString(2, pwdSenha.getText());

			result = prepared.executeQuery();
			
			if(result.next()) {
				//Obtem o conteúdo do campo perfil da tabela
				String perfil = result.getString(6);

				// Faz o tratamento do perfil do usuário
				if(perfil.equals("Administrador")) {
					MainScreen mainScreen = new MainScreen();
					mainScreen.setVisible(true);
					
					mainScreen.getMnRelatorio().setEnabled(true);
					mainScreen.getMnItemUsuarios().setEnabled(true);
					mainScreen.getLblUsuarioLogado().setText(result.getString(2));
					mainScreen.getLblUsuarioLogado().setForeground(Color.red);
					mainScreen.getLblTipoUsuario().setText(result.getString(6));

					this.dispose(); //Fecha tela de login
				} else {
					MainScreen mainScreen = new MainScreen();
					mainScreen.setVisible(true);

					mainScreen.getLblUsuarioLogado().setText(result.getString(2));
					mainScreen.getLblTipoUsuario().setText(result.getString(6));

					this.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválida.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== ATTRIBUTES =====//
	private JTextField txtUsuario;
	private JPasswordField pwdSenha;

	//===== MAIN =====//
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenLogin frame = new ScreenLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//===== COMPONENTS =====//
	public ScreenLogin() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Sistema OS - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 253);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		URL url = this.getClass().getResource("/br/com/natalysoares/sistemaos/icons/logo.png");
		Image logoTitulo = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(logoTitulo);
		
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUsuario.setBounds(81, 86, 50, 14);
		getContentPane().add(lblUsuario);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSenha.setBounds(88, 122, 41, 14);
		getContentPane().add(lblSenha);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtUsuario.setBounds(141, 80, 203, 27);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		pwdSenha = new JPasswordField();
		pwdSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		pwdSenha.setBounds(141, 116, 203, 27);
		getContentPane().add(pwdSenha);
		
		JLabel lblSubtitle = new JLabel("Informe seu nome de usuário e senha nos campos abaixo:");
		lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSubtitle.setBounds(20, 44, 347, 14);
		getContentPane().add(lblSubtitle);
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(ScreenLogin.class.getResource("/br/com/natalysoares/sistemaos/icons/database-ok.png")));
		lblStatus.setBounds(20, 165, 32, 32);
		getContentPane().add(lblStatus);
		
		//==================== EVENT LOGIN ====================//
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
		btnLogin.setBounds(252, 164, 92, 32);
		getContentPane().add(btnLogin);
		
		//==================== DATABASE ====================//

		//Retorna informações do banco de dados
		connection = ConnectionModule.connector();
		
		if(connection != null) {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/natalysoares/sistemaos/icons/database-ok.png")));
		} else {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/natalysoares/sistemaos/icons/database-error.png")));
		}
	}
}
