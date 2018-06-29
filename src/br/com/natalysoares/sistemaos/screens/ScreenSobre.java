package br.com.natalysoares.sistemaos.screens;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ScreenSobre extends JFrame {

	private static final long serialVersionUID = 1L;

	//===== COMPONENTS =====//
	public ScreenSobre() {
		setResizable(false);
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 235);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		URL url = this.getClass().getResource("/br/com/natalysoares/sistemaos/icons/logo.png");
		Image logoTitulo = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(logoTitulo);
		
		JLabel lblNewLabel = new JLabel("<html>Sistema para controle de ordem de serviços. Organizador de pedidos e clientes.</html>");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 63, 414, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Desenvolvido por Nataly S. Mota.");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 126, 326, 30);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sob a licen\u00E7a GPL.");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 150, 114, 30);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblGnu = new JLabel("");
		lblGnu.setBounds(346, 91, 78, 89);
		ImageIcon logognu = new ImageIcon(MainScreen.class.getResource("/br/com/natalysoares/sistemaos/icons/gnu.png"));
		Image imagemGnu = logognu.getImage().getScaledInstance(lblGnu.getWidth(), lblGnu.getHeight(), Image.SCALE_SMOOTH);
		lblGnu.setIcon(new ImageIcon(imagemGnu));
		getContentPane().add(lblGnu);
		
		JLabel lblLogo = new JLabel("InfoX");
		lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
		lblLogo.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLogo.setBounds(10, 24, 60, 28);
		getContentPane().add(lblLogo);
	}

}
