package br.com.natalysoares.sistemaos.screens;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.natalysoares.sistemaos.dao.ConnectionModule;

import java.sql.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScreenCliente extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	//===== DATABASE =====//
	Connection connection = null;
	PreparedStatement prepared = null;
	ResultSet result = null;
	
	//===== ATTRIBUTES =====//
	private JTextField txtNomeCliente;
	private JTextField txtEnderecoCliente;
	private JTextField txtFoneCliente;
	private JTextField txtEmailCliente;
	private JTextField txtBuscaCliente;
	private JTable tblClientes;
	private JTextField txtIdCliente;
	private JButton btnAddCliente;

	//===== COMPONENTS =====//
	public ScreenCliente() {
		setClosable(true);
		setTitle("Clientes");
		setBounds(0, 0, 660, 540);
		getContentPane().setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBounds(0, 0, 660, 540);
		getContentPane().add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblNomeCliente = new JLabel("* Nome:");
		lblNomeCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNomeCliente.setBounds(60, 271, 46, 14);
		panelCliente.add(lblNomeCliente);
		
		JLabel lblEnderecoCliente = new JLabel("Endereço:");
		lblEnderecoCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEnderecoCliente.setBounds(50, 307, 56, 14);
		panelCliente.add(lblEnderecoCliente);
		
		JLabel lblFoneCliente = new JLabel("* Telefone:");
		lblFoneCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFoneCliente.setBounds(48, 341, 64, 14);
		panelCliente.add(lblFoneCliente);
		
		JLabel lblEmailCliente = new JLabel("Email:");
		lblEmailCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmailCliente.setBounds(70, 376, 35, 14);
		panelCliente.add(lblEmailCliente);
		
		txtNomeCliente = new JTextField();
		txtNomeCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtNomeCliente.setBounds(116, 265, 514, 27);
		panelCliente.add(txtNomeCliente);
		txtNomeCliente.setColumns(10);
		
		txtEnderecoCliente = new JTextField();
		txtEnderecoCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtEnderecoCliente.setBounds(116, 301, 513, 27);
		panelCliente.add(txtEnderecoCliente);
		txtEnderecoCliente.setColumns(10);
		
		txtFoneCliente = new JTextField();
		txtFoneCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFoneCliente.setBounds(116, 336, 195, 27);
		panelCliente.add(txtFoneCliente);
		txtFoneCliente.setColumns(10);
		
		txtEmailCliente = new JTextField();
		txtEmailCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtEmailCliente.setBounds(116, 370, 364, 27);
		panelCliente.add(txtEmailCliente);
		txtEmailCliente.setColumns(10);
		
		//===== BOTÃO ADICIONA =====//
		btnAddCliente = new JButton("");
		btnAddCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionar();
			}
		});
		btnAddCliente.setToolTipText("Adicionar novo cliente");
		btnAddCliente.setIcon(new ImageIcon(ScreenCliente.class.getResource("/br/com/natalysoares/sistemaos/icons/add-user.png")));
		btnAddCliente.setBounds(186, 415, 80, 76);
		panelCliente.add(btnAddCliente);
		
		//===== BOTÃO ALTERAR =====//
		JButton btnEditCliente = new JButton("");
		btnEditCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		btnEditCliente.setToolTipText("Editar cliente");
		btnEditCliente.setIcon(new ImageIcon(ScreenCliente.class.getResource("/br/com/natalysoares/sistemaos/icons/edit-user.png")));
		btnEditCliente.setBounds(290, 415, 80, 76);
		panelCliente.add(btnEditCliente);
		
		//===== BOTÃO DELETAR =====//
		JButton btnDeleteCliente = new JButton("");
		btnDeleteCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excluir();
			}
		});
		btnDeleteCliente.setToolTipText("Excluir cliente");
		btnDeleteCliente.setIcon(new ImageIcon(ScreenCliente.class.getResource("/br/com/natalysoares/sistemaos/icons/delete-user.png")));
		btnDeleteCliente.setBounds(391, 415, 80, 76);
		panelCliente.add(btnDeleteCliente);
		
		JLabel lblCamposObrigatorios = new JLabel("Campos com (*) sãoo obrigatórios.");
		lblCamposObrigatorios.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCamposObrigatorios.setBounds(435, 237, 196, 14);
		panelCliente.add(lblCamposObrigatorios);
		
		//===== CAMPO BUSCA =====//
		txtBuscaCliente = new JTextField();
		txtBuscaCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				busca_cliente();
			}
		});
		txtBuscaCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtBuscaCliente.setBounds(10, 21, 581, 27);
		panelCliente.add(txtBuscaCliente);
		txtBuscaCliente.setColumns(10);
		
		JLabel lblBuscaCliente = new JLabel("");
		lblBuscaCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblBuscaCliente.setIcon(new ImageIcon(ScreenCliente.class.getResource("/br/com/natalysoares/sistemaos/icons/search-icon.png")));
		lblBuscaCliente.setBounds(604, 21, 29, 27);
		panelCliente.add(lblBuscaCliente);
		
		JScrollPane scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setBounds(10, 59, 624, 148);
		panelCliente.add(scrollPaneClientes);
		
		tblClientes = new JTable();

		//===== EVENT MOUSE CLICK - Setar campos =====//
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				set_campos();
			}
		});
		tblClientes.setFont(new Font("Arial", Font.PLAIN, 12));
		tblClientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Endereço", "Telefone", "Email"
			}
		));
		scrollPaneClientes.setViewportView(tblClientes);
		
		JLabel lblIdCliente = new JLabel("ID:");
		lblIdCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblIdCliente.setBounds(91, 234, 15, 14);
		panelCliente.add(lblIdCliente);
		
		txtIdCliente = new JTextField();
		txtIdCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtIdCliente.setEnabled(false);
		txtIdCliente.setBounds(116, 229, 86, 27);
		panelCliente.add(txtIdCliente);
		txtIdCliente.setColumns(10);
		
		//==================== DATABASE ====================//

		//Retorna informações do banco de dados
		connection = ConnectionModule.connector();

	}
	
	//==================== METHODS ====================//

	//===== ADICIONA CLIENTES =====//
	private void adicionar() {
		String sql = "INSERT INTO clientes (nomecliente, endcliente, fonecliente, emailcliente) VALUES (?, ?, ?, ?)";
				
		try {
			prepared = connection.prepareStatement(sql);
					
			prepared.setString(1, txtNomeCliente.getText());
			prepared.setString(2, txtEnderecoCliente.getText());
			prepared.setString(3, txtFoneCliente.getText());
			prepared.setString(4, txtEmailCliente.getText());
					
			// Validação dos campos obrigatorios
			if ((txtNomeCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "ERROR - Campos em branco", JOptionPane.INFORMATION_MESSAGE);
			} else {
						
				// Atualiza tabela de usuario com dados do formulario
				int adicionado = prepared.executeUpdate();
						
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!", "Cliente adicionado", JOptionPane.INFORMATION_MESSAGE);
						
					limpaCampos();
					}
				}
					
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== BUSCA CLIENTE =====//
	private void busca_cliente() {
		String sql = "SELECT idcliente AS ID, nomecliente AS Nome, endcliente AS Endereço, fonecliente AS Fone, emailcliente AS Email FROM clientes WHERE idcliente LIKE ?";
		
		try {
			prepared = connection.prepareStatement(sql);
			
			// Concatena com a sql
			prepared.setString(1,txtBuscaCliente.getText() + "%");
			result = prepared.executeQuery();
			
			tblClientes.setModel(DbUtils.resultSetToTableModel(result));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== SETAR CAMPOS DO FORMULÁRIO =====//
	public void set_campos() {
		// Pega linha que está selecionada
		int setar = tblClientes.getSelectedRow();

		txtIdCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		txtNomeCliente.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtEnderecoCliente.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		txtFoneCliente.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
		txtEmailCliente.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
		
		// Desabilita Botão Add
		btnAddCliente.setEnabled(false);
	}
	
	//===== EDITA CLIENTES =====//
	private void alterar() {
		String sql = "UPDATE clientes SET nomecliente = ?, endcliente = ?, fonecliente = ?, emailcliente = ? WHERE idcliente = ?";

		try {
			prepared = connection.prepareStatement(sql);

			prepared.setString(1, txtNomeCliente.getText());
			prepared.setString(2, txtEnderecoCliente.getText());
			prepared.setString(3, txtFoneCliente.getText());
			prepared.setString(4, txtEmailCliente.getText());
			prepared.setString(5, txtIdCliente.getText());
				
			if ((txtNomeCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "ERROR - Campos em branco", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int alterado = prepared.executeUpdate();
	
				if (alterado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do usuário alterado com sucesso!", "Cliente Alterado", JOptionPane.INFORMATION_MESSAGE);

					limpaCampos();
					
					// Habilita Botão Add
					btnAddCliente.setEnabled(true);
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//===== REMOVE CLIENTES =====//
	private void excluir() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
			
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "DELETE FROM clientes WHERE idcliente = ?";
				
			try {
				prepared = connection.prepareStatement(sql);
				prepared.setString(1, txtIdCliente.getText());
				int deletado = prepared.executeUpdate();

				if(deletado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Usuário excluído", JOptionPane.INFORMATION_MESSAGE);
					limpaCampos();
					
					// Habilita Botão Add
					btnAddCliente.setEnabled(true);
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	//===== LIMPA CAMPOS =====//
	private void limpaCampos() {
		txtIdCliente.setText(null);
		txtNomeCliente.setText(null);
		txtEnderecoCliente.setText(null);
		txtFoneCliente.setText(null);
		txtEmailCliente.setText(null);
	}
}