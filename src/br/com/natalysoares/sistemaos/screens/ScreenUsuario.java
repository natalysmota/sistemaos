package br.com.natalysoares.sistemaos.screens;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.natalysoares.sistemaos.dao.ConnectionModule;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ScreenUsuario extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	
	//===== DATABASE =====//
	Connection connection = null;
	PreparedStatement prepared = null;
	ResultSet result = null;
	
	//===== ATTRIBUTES =====//
	private JTextField txtIdUsuario;
	private JTextField txtNomeUsuario;
	private JTextField txtFoneUsuario;
	private JTextField txtLoginUsuario;
	@SuppressWarnings("rawtypes")
	private JComboBox comboPerfilUsuario;
	private JPasswordField txtSenhaUsuario;

	//===== COMPONENTS =====//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ScreenUsuario() {
		setClosable(true);
		setEnabled(false);
		setTitle("Usuários");
		setBounds(0, 0, 660, 540);
		getContentPane().setLayout(null);
		
		JPanel panelUsuarios = new JPanel();
		panelUsuarios.setBounds(0, 0, 660, 540);
		getContentPane().add(panelUsuarios);
		panelUsuarios.setLayout(null);
		
		JLabel lblIdUsuario = new JLabel("* ID:");
		lblIdUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblIdUsuario.setBounds(88, 83, 23, 14);
		panelUsuarios.add(lblIdUsuario);
		
		JLabel lblNomeUsuario = new JLabel("* Nome:");
		lblNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNomeUsuario.setBounds(69, 123, 51, 14);
		panelUsuarios.add(lblNomeUsuario);
		
		JLabel lblFoneUsuario = new JLabel("Telefone:");
		lblFoneUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFoneUsuario.setBounds(61, 163, 51, 14);
		panelUsuarios.add(lblFoneUsuario);
		
		JLabel lblPefilUsuario = new JLabel("* Tipo de perfil:");
		lblPefilUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPefilUsuario.setBounds(34, 285, 89, 14);
		panelUsuarios.add(lblPefilUsuario);
		
		JLabel lblLoginUsuario = new JLabel("* Login:");
		lblLoginUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLoginUsuario.setBounds(69, 203, 48, 14);
		panelUsuarios.add(lblLoginUsuario);
		
		JLabel lblSenhaUsuario = new JLabel("* Senha:");
		lblSenhaUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSenhaUsuario.setBounds(69, 245, 51, 14);
		panelUsuarios.add(lblSenhaUsuario);
		
		txtIdUsuario = new JTextField();
		txtIdUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtIdUsuario.setBounds(140, 77, 97, 29);
		panelUsuarios.add(txtIdUsuario);
		txtIdUsuario.setColumns(10);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtNomeUsuario.setBounds(140, 117, 482, 29);
		panelUsuarios.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);
		
		txtFoneUsuario = new JTextField();
		txtFoneUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFoneUsuario.setBounds(140, 157, 179, 29);
		panelUsuarios.add(txtFoneUsuario);
		txtFoneUsuario.setColumns(10);
		
		txtLoginUsuario = new JTextField();
		txtLoginUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtLoginUsuario.setBounds(140, 197, 197, 29);
		panelUsuarios.add(txtLoginUsuario);
		txtLoginUsuario.setColumns(10);
		
		comboPerfilUsuario = new JComboBox();
		comboPerfilUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		comboPerfilUsuario.setModel(new DefaultComboBoxModel(new String[] {"Usuário Comum", "Administrador"}));
		comboPerfilUsuario.setBounds(141, 278, 140, 29);
		panelUsuarios.add(comboPerfilUsuario);
		
		//===== BOTÃO ADICIONA =====//
		JButton btnAddUsuario = new JButton("");
		btnAddUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAddUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddUsuario.setToolTipText("Adicionar novo usuário");
		btnAddUsuario.setIcon(new ImageIcon(ScreenUsuario.class.getResource("/br/com/natalysoares/sistemaos/icons/add-user.png")));
		btnAddUsuario.setBounds(128, 360, 89, 73);
		panelUsuarios.add(btnAddUsuario);
		
		//===== BOTÃO ALTERAR =====//
		JButton btnEditUsuario = new JButton("");
		btnEditUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		btnEditUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditUsuario.setToolTipText("Editar usuário");
		btnEditUsuario.setIcon(new ImageIcon(ScreenUsuario.class.getResource("/br/com/natalysoares/sistemaos/icons/edit-user.png")));
		btnEditUsuario.setBounds(229, 360, 89, 73);
		panelUsuarios.add(btnEditUsuario);
		
		//===== BOTÃO REMOVER =====//
		JButton btnDeleteUsuario = new JButton("");
		btnDeleteUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnDeleteUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeleteUsuario.setToolTipText("Excluir usuário");
		btnDeleteUsuario.setIcon(new ImageIcon(ScreenUsuario.class.getResource("/br/com/natalysoares/sistemaos/icons/delete-user.png")));
		btnDeleteUsuario.setBounds(330, 361, 89, 73);
		panelUsuarios.add(btnDeleteUsuario);
		
		//===== BOTÃO PESQUISA =====//
		JButton btnSearchUsuario = new JButton("");
		btnSearchUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultar();
			}
		});
		btnSearchUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearchUsuario.setToolTipText("Buscar usuário");
		btnSearchUsuario.setIcon(new ImageIcon(ScreenUsuario.class.getResource("/br/com/natalysoares/sistemaos/icons/search-user.png")));
		btnSearchUsuario.setBounds(432, 361, 89, 73);
		panelUsuarios.add(btnSearchUsuario);
		
		JLabel lblCamposObrigatorios = new JLabel("Campos com (*) são obrigatórios");
		lblCamposObrigatorios.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCamposObrigatorios.setBounds(436, 83, 186, 14);
		panelUsuarios.add(lblCamposObrigatorios);
		
		txtSenhaUsuario = new JPasswordField();
		txtSenhaUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		txtSenhaUsuario.setBounds(140, 239, 197, 29);
		panelUsuarios.add(txtSenhaUsuario);
		
		//==================== DATABASE ====================//

		//Retorna informações do banco de dados
		connection = ConnectionModule.connector();

	}
	
	//==================== METHODS ====================//
	
	//===== CONSULTA USUARIOS =====//
	public void consultar() {
		String sql =  "SELECT * FROM usuarios WHERE iduser = ?";
		
		try {
			prepared = connection.prepareStatement(sql);
			
			//Obtem o valor digitado e substitui no ?
			prepared.setString(1, txtIdUsuario.getText());
			result = prepared.executeQuery();
			
			// Caso tenha um usuario correspondente
			if (result.next()) {
				txtNomeUsuario.setText(result.getString(2));
				txtFoneUsuario.setText(result.getString(3));
				txtLoginUsuario.setText(result.getString(4));
				txtSenhaUsuario.setText(result.getString(5));
				comboPerfilUsuario.setSelectedItem(result.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não encontrado.", "Não encontrado", JOptionPane.ERROR_MESSAGE);
				
				limpaCampos();
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== ADICIONA USUARIOS =====//
	@SuppressWarnings("deprecation")
	private void adicionar() {
		String sql = "INSERT INTO usuarios (iduser, usuario, fone, login, senha, perfil) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			prepared = connection.prepareStatement(sql);
			
			prepared.setString(1, txtIdUsuario.getText());
			prepared.setString(2, txtNomeUsuario.getText());
			prepared.setString(3, txtFoneUsuario.getText());
			prepared.setString(4, txtLoginUsuario.getText());
			prepared.setString(5, txtSenhaUsuario.getText());
			prepared.setString(6, comboPerfilUsuario.getSelectedItem().toString());
			
			// Validação dos campos obrigatorios
			if ((txtIdUsuario.getText().isEmpty()) || (txtNomeUsuario.getText().isEmpty()) || (txtLoginUsuario.getText().isEmpty())
					|| (txtSenhaUsuario.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "ERROR - Campos em branco", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// Converte ComboBox para String
				prepared.setString(6, comboPerfilUsuario.getSelectedItem().toString());
				
				// Atualiza tabela de usuario com dados do formulario
				int adicionado = prepared.executeUpdate();
				
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!", "Usuário adicionado", JOptionPane.INFORMATION_MESSAGE);
					
					limpaCampos();
				}
			}
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	//===== EDITA USUARIOS =====//
	@SuppressWarnings("deprecation")
	private void alterar() {
		String sql = "UPDATE usuarios SET usuario = ?, fone = ?, login = ?, senha = ?, perfil = ? WHERE iduser = ?";
		
		try {
			prepared = connection.prepareStatement(sql);

			prepared.setString(1, txtNomeUsuario.getText());
			prepared.setString(2, txtFoneUsuario.getText());
			prepared.setString(3, txtLoginUsuario.getText());
			prepared.setString(4, txtSenhaUsuario.getText());
			prepared.setString(5, comboPerfilUsuario.getSelectedItem().toString());
			prepared.setString(6, txtIdUsuario.getText());
			
			if ((txtIdUsuario.getText().isEmpty()) || (txtNomeUsuario.getText().isEmpty()) || (txtLoginUsuario.getText().isEmpty())
				|| (txtSenhaUsuario.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "ERROR - Campos em branco", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int alterado = prepared.executeUpdate();
	
				if (alterado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do usuário alterado com sucesso!", "Usuário Alterado", JOptionPane.INFORMATION_MESSAGE);

					limpaCampos();
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== REMOVE USUARIOS =====//
	private void excluir() {
		
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
		
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "DELETE FROM usuarios WHERE iduser = ?";
			
			try {
				prepared = connection.prepareStatement(sql);
				prepared.setString(1, txtIdUsuario.getText());
				int deletado = prepared.executeUpdate();

				if(deletado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!", "Usuário excluído", JOptionPane.INFORMATION_MESSAGE);
					limpaCampos();
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
	}
	
	//===== LIMPA CAMPOS =====//
	private void limpaCampos() {
		txtIdUsuario.setText(null);
		txtNomeUsuario.setText(null);
		txtFoneUsuario.setText(null);
		txtLoginUsuario.setText(null);
		txtSenhaUsuario.setText(null);
	}
	
	//==================== GETTERS ====================//
	@SuppressWarnings("rawtypes")
	public JComboBox getComboPerfilUsuario() {
		return comboPerfilUsuario;
	}
}
