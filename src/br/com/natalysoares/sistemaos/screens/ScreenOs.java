package br.com.natalysoares.sistemaos.screens;

import javax.swing.ButtonGroup;
import javax.swing.JInternalFrame;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.natalysoares.sistemaos.dao.ConnectionModule;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.sql.*;
import java.util.HashMap;

import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class ScreenOs extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	//===== DATABASE =====//
	Connection connection = null;
	PreparedStatement prepared = null;
	ResultSet result = null;
	
	//===== ATTRIBUTES =====//
	private JTextField txtNumOs;
	private JTextField txtDataOs;
	private JTextField txtBuscaCliente;
	private JTextField txtIdCliente;
	private JTable tblClientes;
	private JTextField txtEquipamento;
	private JTextField txtDefeito;
	private JTextField txtServico;
	private JTextField txtTecnico;
	private JTextField txtValorTotal;
	private JRadioButton rdbtnOrcamento;
	private String tipo;
	@SuppressWarnings("rawtypes")
	private JComboBox comboSituacao;
	private JRadioButton rbbtnOs;
	private JButton btnAddOs;

	//===== COMPONENTS =====//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ScreenOs() {
		
		//===== INICIANDO FORM - MARCA ORÇAMENTO =====//
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				rdbtnOrcamento.setSelected(true);
				tipo = "Orçamento";
			}
		});

		setClosable(true);
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		setTitle("OS");
		setBounds(0, 0, 660, 540);
		getContentPane().setLayout(null);
		
		JPanel panelOs = new JPanel();
		panelOs.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelOs.setBounds(10, 11, 239, 126);
		getContentPane().add(panelOs);
		panelOs.setLayout(null);
		
		JLabel lblNumOs = new JLabel("Nº OS:");
		lblNumOs.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNumOs.setBounds(6, 22, 46, 14);
		panelOs.add(lblNumOs);
		
		txtNumOs = new JTextField();
		txtNumOs.setFont(new Font("Arial", Font.PLAIN, 12));
		txtNumOs.setEditable(false);
		txtNumOs.setBounds(6, 40, 65, 25);
		panelOs.add(txtNumOs);
		txtNumOs.setColumns(10);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 12));
		lblData.setBounds(91, 22, 46, 14);
		panelOs.add(lblData);
		
		txtDataOs = new JTextField();
		txtDataOs.setEditable(false);
		txtDataOs.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDataOs.setBounds(91, 40, 138, 25);
		panelOs.add(txtDataOs);
		txtDataOs.setColumns(10);
		
		//===== ATRIBUIÇÃO A VARIAVEL TIPO =====//
		ButtonGroup tipoServico = new ButtonGroup(); 
		rdbtnOrcamento = new JRadioButton("Orçamento");
		rdbtnOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipo = "Orçamento";
			}
		});
		rdbtnOrcamento.setFont(new Font("Arial", Font.PLAIN, 12));
		rdbtnOrcamento.setBounds(6, 85, 87, 23);
		panelOs.add(rdbtnOrcamento);
		
		//===== ATRIBUIÇÃO A VARIAVEL TIPO =====//
		rbbtnOs = new JRadioButton("OS");
		rbbtnOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "OS";
			}
		});
		rbbtnOs.setFont(new Font("Arial", Font.PLAIN, 12));
		rbbtnOs.setBounds(113, 86, 48, 23);
		panelOs.add(rbbtnOs);
		
		tipoServico.add(rdbtnOrcamento);
		tipoServico.add(rbbtnOs);
		
		JLabel lblSituacao = new JLabel("Situação:");
		lblSituacao.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSituacao.setBounds(10, 150, 58, 20);
		getContentPane().add(lblSituacao);
		
		comboSituacao = new JComboBox();
		comboSituacao.setFont(new Font("Arial", Font.PLAIN, 12));
		comboSituacao.setModel(new DefaultComboBoxModel(new String[] {"Na bancada", "Entrega finalizada", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retorno"}));
		comboSituacao.setBounds(68, 148, 181, 26);
		getContentPane().add(comboSituacao);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCliente.setBounds(259, 11, 375, 159);
		getContentPane().add(panelCliente);
		panelCliente.setLayout(null);
		
		//===== CAMPO BUSCA =====//
		txtBuscaCliente = new JTextField();
		txtBuscaCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				busca_cliente();
			}
		});
		txtBuscaCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtBuscaCliente.setBounds(10, 31, 214, 25);
		panelCliente.add(txtBuscaCliente);
		txtBuscaCliente.setColumns(10);
		
		JLabel lblIconBusca = new JLabel("");
		lblIconBusca.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/search-icon.png")));
		lblIconBusca.setBounds(234, 31, 24, 25);
		panelCliente.add(lblIconBusca);
		
		JLabel lblIdObrigatorio = new JLabel("* ID:");
		lblIdObrigatorio.setFont(new Font("Arial", Font.PLAIN, 12));
		lblIdObrigatorio.setBounds(268, 37, 24, 14);
		panelCliente.add(lblIdObrigatorio);
		
		txtIdCliente = new JTextField();
		txtIdCliente.setEditable(false);
		txtIdCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		txtIdCliente.setBounds(302, 31, 63, 25);
		panelCliente.add(txtIdCliente);
		txtIdCliente.setColumns(10);
		
		JScrollPane scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setBounds(10, 67, 355, 81);
		panelCliente.add(scrollPaneClientes);
		
		//===== EVENT MOUSE CLICK - Setar campos =====//
		tblClientes = new JTable();
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
				"ID", "Nome", "Fone"
			}
		));
		scrollPaneClientes.setViewportView(tblClientes);
		
		JPanel panelCadastro = new JPanel();
		panelCadastro.setBorder(new TitledBorder(null, "Cadastro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCadastro.setBounds(10, 192, 624, 307);
		getContentPane().add(panelCadastro);
		panelCadastro.setLayout(null);
		
		JLabel lblEquipamento = new JLabel("* Equipamento:");
		lblEquipamento.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEquipamento.setBounds(10, 52, 90, 14);
		panelCadastro.add(lblEquipamento);
		
		txtEquipamento = new JTextField();
		txtEquipamento.setFont(new Font("Arial", Font.PLAIN, 12));
		txtEquipamento.setBounds(102, 48, 472, 25);
		panelCadastro.add(txtEquipamento);
		txtEquipamento.setColumns(10);
		
		JLabel lblDefeito = new JLabel("* Defeito:");
		lblDefeito.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDefeito.setBounds(44, 88, 56, 14);
		panelCadastro.add(lblDefeito);
		
		txtDefeito = new JTextField();
		txtDefeito.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDefeito.setBounds(102, 84, 472, 25);
		panelCadastro.add(txtDefeito);
		txtDefeito.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Serviço:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(54, 124, 46, 14);
		panelCadastro.add(lblNewLabel);
		
		txtServico = new JTextField();
		txtServico.setFont(new Font("Arial", Font.PLAIN, 12));
		txtServico.setBounds(104, 120, 369, 25);
		panelCadastro.add(txtServico);
		txtServico.setColumns(10);
		
		JLabel lblTecnico = new JLabel("Técnico:");
		lblTecnico.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTecnico.setBounds(52, 160, 46, 14);
		panelCadastro.add(lblTecnico);
		
		txtTecnico = new JTextField();
		txtTecnico.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTecnico.setBounds(104, 156, 214, 25);
		panelCadastro.add(txtTecnico);
		txtTecnico.setColumns(10);
		
		JLabel lblValorTotal = new JLabel("Valor total:");
		lblValorTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		lblValorTotal.setBounds(343, 161, 64, 14);
		panelCadastro.add(lblValorTotal);
		
		txtValorTotal = new JTextField();
		txtValorTotal.setText("0");
		txtValorTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		txtValorTotal.setBounds(408, 157, 127, 25);
		panelCadastro.add(txtValorTotal);
		txtValorTotal.setColumns(10);
		
		//===== BOTÃO ADICIONA =====//
		btnAddOs = new JButton("");
		btnAddOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitir_Os();
			}
		});
		btnAddOs.setToolTipText("Adicionar novo OS");
		btnAddOs.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/add-os.png")));
		btnAddOs.setBounds(151, 218, 56, 54);
		panelCadastro.add(btnAddOs);
		
		//===== BOTÃO ALTERAR =====//
		JButton btnEditOs = new JButton("");
		btnEditOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alteraOs();
			}
		});
		btnEditOs.setToolTipText("Editar OS");
		btnEditOs.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/edit-os.png")));
		btnEditOs.setBounds(217, 218, 56, 54);
		panelCadastro.add(btnEditOs);
		
		//===== BOTÃO DELETAR =====//
		JButton btnDeleteOs = new JButton("");
		btnDeleteOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluiOs();
			}
		});
		btnDeleteOs.setToolTipText("Excluir OS");
		btnDeleteOs.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/delete-os.png")));
		btnDeleteOs.setBounds(283, 218, 56, 54);
		panelCadastro.add(btnDeleteOs);
		
		//===== BOTÃO BUSCA =====//
		JButton btnSearchOs = new JButton("");
		btnSearchOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaOs();
			}
		});
		btnSearchOs.setToolTipText("Buscar OS");
		btnSearchOs.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/search-os.png")));
		btnSearchOs.setBounds(351, 218, 56, 54);
		panelCadastro.add(btnSearchOs);
		
		//===== BOTÃO IMPRIMIR =====//
		JButton btnImprime = new JButton("");
		btnImprime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprime.setToolTipText("Imprimir OS");
		btnImprime.setIcon(new ImageIcon(ScreenOs.class.getResource("/br/com/natalysoares/sistemaos/icons/printer-os.png")));
		btnImprime.setBounds(417, 218, 56, 54);
		panelCadastro.add(btnImprime);
		
		//==================== DATABASE ====================//

		//Retorna informações do banco de dados
		connection = ConnectionModule.connector();
	}
	
	//===== BUSCA CLIENTES =====//
	private void busca_cliente() {
		String sql = "SELECT idcliente AS ID, nomecliente AS Nome, fonecliente AS Fone FROM clientes WHERE nomecliente LIKE ?";
		
		try {
			prepared = connection.prepareStatement(sql);
			prepared.setString(1, txtBuscaCliente.getText() + "%");
			
			result = prepared.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(result));
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== SETA CAMPOS =====//
	private void set_campos() {
		int setar = tblClientes.getSelectedRow();
		txtIdCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
	}
	
	//===== ADICIONA OS =====//
	private void emitir_Os() {
		String sql = "INSERT INTO os (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcliente) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			prepared = connection.prepareStatement(sql);
			prepared.setString(1, tipo);
			prepared.setString(2, comboSituacao.getSelectedItem().toString());
			prepared.setString(3, txtEquipamento.getText());
			prepared.setString(4, txtDefeito.getText());
			prepared.setString(5, txtServico.getText());
			prepared.setString(6, txtTecnico.getText());
			prepared.setString(7, txtValorTotal.getText().replace(",", "."));
			prepared.setString(8, txtIdCliente.getText());
			
			// Validação de campos obrigatórios
			if ((txtIdCliente.getText().isEmpty()) || (txtEquipamento.getText().isEmpty()) || txtDefeito.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "Campos em branco", JOptionPane.ERROR_MESSAGE);
			} else {
				int adicionado = prepared.executeUpdate();
				
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					
					limpaCampos();
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== BUSCA OS =====//
	private void buscaOs() {
		String num_os = JOptionPane.showInputDialog("Número da OS:");
		
		String sql = "SELECT * FROM os WHERE os = " + num_os;
		
		try {
			prepared = connection.prepareStatement(sql);
			result = prepared.executeQuery();
			
			if (result.next()) {
				txtNumOs.setText(result.getString(1));
				txtDataOs.setText(result.getString(2));

				String rbtTipo = result.getString(3);
				if (rbtTipo.equals("OS")) {
					rbbtnOs.setSelected(true);
					tipo = "OS";
				} else {
					rdbtnOrcamento.setSelected(true);
					tipo = "Orçamento";
				}
				
				comboSituacao.setSelectedItem(result.getString(4));
				txtEquipamento.setText(result.getString(5));
				txtDefeito.setText(result.getString(6));
				txtServico.setText(result.getString(7));
				txtTecnico.setText(result.getString(8));
				txtValorTotal.setText(result.getString(9));
				txtIdCliente.setText(result.getString(10));
				
				btnAddOs.setEnabled(false);
				txtBuscaCliente.setEnabled(false);
				tblClientes.setVisible(false);
				
			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada", "Não encontrado", JOptionPane.WARNING_MESSAGE);
			}
		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "OS inválida.", "Dados inválidos", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}
	
	//===== ALTERA OS =====//
	private void alteraOs() {
		String sql = "UPDATE os SET tipo = ?, situacao = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ? WHERE os = ?";
		
		try {
			prepared = connection.prepareStatement(sql);
			prepared.setString(1, tipo);
			prepared.setString(2, comboSituacao.getSelectedItem().toString());
			prepared.setString(3, txtEquipamento.getText());
			prepared.setString(4, txtDefeito.getText());
			prepared.setString(5, txtServico.getText());
			prepared.setString(6, txtTecnico.getText());
			prepared.setString(7, txtValorTotal.getText().replace(",", "."));
			prepared.setString(8, txtNumOs.getText());
			
			// Validação de campos obrigatórios
			if ((txtIdCliente.getText().isEmpty()) || (txtEquipamento.getText().isEmpty()) || txtDefeito.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "Campos em branco", JOptionPane.ERROR_MESSAGE);
			} else {
				int adicionado = prepared.executeUpdate();
				
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS alterada com sucesso!", "OS Alterada", JOptionPane.INFORMATION_MESSAGE);
					
					limpaCampos();
					
					// Habilita objetos
					btnAddOs.setEnabled(true);
					txtBuscaCliente.setEnabled(true);
					tblClientes.setVisible(true);
				}
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//===== EXCLUI OS =====//
	private void excluiOs() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
		
		if(confirma == JOptionPane.YES_OPTION) {
			String sql = "DELETE FROM os WHERE os= ?";
			
			try {
				prepared = connection.prepareStatement(sql);
				prepared.setString(1, txtNumOs.getText());
				
				int apagado = prepared.executeUpdate();
				
				if(apagado > 0) {
					JOptionPane.showMessageDialog(null, "OS excluída com sucesso!");
					
					limpaCampos();
					
					// Habilita objetos
					btnAddOs.setEnabled(true);
					txtBuscaCliente.setEnabled(true);
					tblClientes.setVisible(true);
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	//===== IMPRIME OS =====//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void imprimir() {
		int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir o relatório de OS?","Atenção", JOptionPane.YES_NO_OPTION);
		
		if(confirma == JOptionPane.YES_OPTION) {
			
			//JasperReport
			try {
				// Classe HashMap para filtro
				HashMap filtro = new HashMap();
				// "os" Criada na ferramenta iReport
				filtro.put("os", Integer.parseInt(txtNumOs.getText()));
				
				// Classe JasperPrint
				JasperPrint print = JasperFillManager.fillReport("C:/Users/natal/Desktop/reports/os.jasper", filtro, connection);
				
				// Exibe relatório - JasperViewer
				JasperViewer.viewReport(print, false);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//===== LIMPA CAMPOS =====//
	private void limpaCampos() {
		txtNumOs.setText(null);
		txtDataOs.setText(null);
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtServico.setText(null);
		txtTecnico.setText(null);
		txtValorTotal.setText(null);
		txtIdCliente.setText(null);
	}
	
	//===== GETTERS =====//
	@SuppressWarnings("rawtypes")
	public JComboBox getComboSituacao() {
		return comboSituacao;
	}
	public JTextField getTxtEquipamento() {
		return txtEquipamento;
	}
	public JTextField getTxtDefeito() {
		return txtDefeito;
	}
	public JTextField getTxtServico() {
		return txtServico;
	}
	public JTextField getTextField() {
		return txtTecnico;
	}
	public JRadioButton getRdbtnOrcamento() {
		return rdbtnOrcamento;
	}
	public JRadioButton getRbbtnOs() {
		return rbbtnOs;
	}
	public JButton getBtnAddOs() {
		return btnAddOs;
	}
}
