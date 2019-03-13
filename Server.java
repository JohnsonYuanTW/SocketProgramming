package Socket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.net.BindException;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.table.TableColumn;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

public class Server {
	private JFrame frmServer;
	private static LoginDialog l;
	private JToggleButton btnStock;
	private JToggleButton btnSync;
	private JToggleButton btnCustomer;
	private JPanel panelSync;
	private JPanel panelCustomer;
	private JPanel panelStock;
	private JPanel panelInit;
	private JScrollPane spA;
	private JTable tableA;
	private JScrollPane spB;
	private JTable tableB;
	private JRadioButton rdbtna;
	private JRadioButton rdbtnb;
	private int currentTable;
	private String dataA = "";
	private String dataB = "";
	private JScrollPane spData;
	private JScrollPane spDataB;
	private SocketForServer s;
	private JTextField textFieldSyncData;
	private JTextArea textArea;
	private Thread socket;
	private JTextArea textAreaChatBox;
	
	Runnable callSocketFrom = new Runnable() {
	    public void run() {
	    	if(rdbtna.isSelected())
	    	{
	    		s = new SocketForServer(textFieldSyncData, textArea, dataA, "From");
	    	}else if (rdbtnb.isSelected())
	    	{
	    		s = new SocketForServer(textFieldSyncData, textArea, dataB, "From");
	    	}
	    	
	    }
	};
	
	Runnable callSocketTo = new Runnable() {
	    public void run() {
	    	if(rdbtna.isSelected() || rdbtnb.isSelected())
	    	{
	    		s = new SocketForServer(textFieldSyncData, textArea, dataA, "To");
	    	}
	    }
	};
	
	Runnable callSocketServer = new Runnable() {
	    public void run() {
	    	s = new SocketForServer(null, textAreaChatBox, dataA, "Server");
	    }
	};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					window.frmServer.setVisible(false);
					l = new LoginDialog(window.frmServer, "Server");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void changeTable()
	{
		if(currentTable == 0)
		{
			spA.setVisible(true);
			spB.setVisible(false);
		} else
		{
			spA.setVisible(false);
			spB.setVisible(true);
		}
	}
	
	Object[][] data1={ //Default Data for Store A
			{"pencil", new Integer(16)},
			{"pen", new Integer(16)},
			{"eraser", new Integer(16)},
			{"ruler", new Integer(16)},
			{"pencil_case", new Integer(16)}
	};
	Object[][] data2={ //Default Data for Store B
			{"pencil", new Integer(2)},
			{"pen", new Integer(2)},
			{"eraser", new Integer(2)},
			{"ruler", new Integer(2)},
			{"pencil_case", new Integer(2)}
	};
	
	String[] columns = {"name", "quantity"};
	
	public Server() {
		initialize();
	}
	
	private void initialize(){
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setVisible(true);
		frmServer.setBounds(100, 100, 787, 631);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		panelCustomer = new JPanel();
		panelCustomer.setBounds(0, 52, 769, 532);
		frmServer.getContentPane().add(panelCustomer);
		panelCustomer.setLayout(null);
		panelCustomer.setVisible(false);
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(socket == null)
				{
					textAreaChatBox.setText("");
					socket = new Thread(callSocketServer);
					socket.start();
				} else if (socket.isAlive())
				{
					textAreaChatBox.append("\n" + "please finish this Socket first.");
				}else
				{
					textAreaChatBox.setText("");
					socket = new Thread(callSocketServer);
					socket.start();
				}
			}
		});
		btnStartServer.setBounds(589, 181, 120, 27);
		panelCustomer.add(btnStartServer);
		
		textAreaChatBox = new JTextArea();
		textAreaChatBox.setBounds(52, 40, 470, 310);
		panelCustomer.add(textAreaChatBox);
		
		JScrollPane spChatBox = new JScrollPane(textAreaChatBox);
		spChatBox.setBounds(50, 40, 470, 407);
		panelCustomer.add(spChatBox);
		
		DefaultCaret caretChatBox = (DefaultCaret)textAreaChatBox.getCaret();
		caretChatBox.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		panelSync = new JPanel();
		panelSync.setBounds(0, 52, 769, 532);
		frmServer.getContentPane().add(panelSync);
		panelSync.setLayout(null);
		panelSync.setVisible(false);
		
		JButton btnSyncFromServer = new JButton("Sync from Server");
		btnSyncFromServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(socket == null)
				{
					textArea.setText("");
					socket = new Thread(callSocketFrom);
					socket.start();
				} else if (socket.isAlive())
				{
					textArea.append("\n" + "please finish this Socket first.");
				}else
				{
					textArea.setText("");
					socket = new Thread(callSocketFrom);
					socket.start();
				}
			}
		});
		btnSyncFromServer.setBounds(464, 193, 193, 27);
		panelSync.add(btnSyncFromServer);
		
		JButton btnSyncFromClient = new JButton("Sync from Client");
		btnSyncFromClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(socket == null)
				{
					textArea.setText("");
					socket = new Thread(callSocketTo);
					socket.start();
				} else if (socket.isAlive())
				{
					textArea.append("\n" + "please finish this Socket first.");
				}else
				{
					textArea.setText("");
					socket = new Thread(callSocketTo);
					socket.start();
				}
			}
		});
		btnSyncFromClient.setBounds(464, 261, 193, 27);
		panelSync.add(btnSyncFromClient);
		
		rdbtna = new JRadioButton("\u5206\u5E97A");
		rdbtna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnb.setSelected(false);
				textFieldSyncData.setText("");
				dataA = "";
				int number = tableA.getRowCount();
				for (int i=0; i < number; i++)
				{
					
					dataA += (String) tableA.getValueAt(i, 0);
					dataA += " ";
					dataA += String.valueOf(tableA.getValueAt(i, 1));
					dataA += " ";
				}
				textFieldSyncData.setText(dataA);
			}
		});
		rdbtna.setBounds(48, 39, 127, 27);
		panelSync.add(rdbtna);
		
		rdbtnb = new JRadioButton("\u5206\u5E97B");
		rdbtnb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtna.setSelected(false);
				textFieldSyncData.setText("");
				dataB = "";
				int number = tableB.getRowCount();
				for (int i=0; i < number; i++)
				{
					
					dataB += (String) tableB.getValueAt(i, 0);
					dataB += " ";
					dataB += String.valueOf(tableB.getValueAt(i, 1));
					dataB += " ";
				}
				textFieldSyncData.setText(dataB);
			}
		});
		rdbtnb.setBounds(285, 39, 127, 27);
		panelSync.add(rdbtnb);
		
		textFieldSyncData = new JTextField();
		textFieldSyncData.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSyncData.setEditable(false);
		textFieldSyncData.setBounds(60, 98, 331, 362);
		panelSync.add(textFieldSyncData);
		
		textArea = new JTextArea();
		textArea.setBounds(424, 386, 313, 74);
		panelSync.add(textArea);
		
		textFieldSyncData.getDocument().addDocumentListener(new DocumentListener() {

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	String data = textFieldSyncData.getText();
        		String[] parts = data.split(" ");
        		if(rdbtna.isSelected())
        		{
        			for(int i=0; i<parts.length; i++)
            		{
            			if(i%2==1)
            			{
            				tableA.setValueAt(Integer.valueOf(parts[i]), i/2, 1);
            			}else
            			{
            				tableA.setValueAt(parts[i], i/2, 0);
            			}
            		}
        		}
        		else
        		{
        			for(int i=0; i<parts.length; i++)
            		{
            			if(i%2==1)
            			{
            				tableB.setValueAt(Integer.valueOf(parts[i]), i/2, 1);
            			}else
            			{
            				tableB.setValueAt(parts[i], i/2, 0);
            			}
            		}
        		}
        		
	        }

	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
        		
        	}
	        
	    });
		
		spData = new JScrollPane();
		spData.setBounds(60, 102, 331, 358);
		panelSync.add(spData);
		
		JScrollPane spStatus = new JScrollPane(textArea);
		spStatus.setBounds(424, 386, 313, 74);
		panelSync.add(spStatus);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		panelInit = new JPanel();
		panelInit.setBounds(0, 52, 769, 532);
		frmServer.getContentPane().add(panelInit);
		GridBagLayout gbl_panelInit = new GridBagLayout();
		gbl_panelInit.columnWidths = new int[]{204, 360, 0};
		gbl_panelInit.rowHeights = new int[]{24, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelInit.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelInit.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelInit.setLayout(gbl_panelInit);
		
		JLabel lblInit = new JLabel("Please Select Either One To Get Started");
		lblInit.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_lblInit = new GridBagConstraints();
		gbc_lblInit.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblInit.gridx = 1;
		gbc_lblInit.gridy = 8;
		panelInit.add(lblInit, gbc_lblInit);
		
		
		panelStock = new JPanel();
		panelStock.setBounds(0, 52, 769, 532);
		frmServer.getContentPane().add(panelStock);
		panelStock.setLayout(null);
		panelStock.setVisible(false);
		
		panelStock.setVisible(false);
		
		JButton btnDec = new JButton("dec");
		btnDec.setBounds(625, 75, 99, 27);
		panelStock.add(btnDec);
		
		JButton btnInc = new JButton("inc");
		btnInc.setBounds(503, 75, 99, 27);
		panelStock.add(btnInc);
		
		spB = new JScrollPane();
		spB.setBounds(43, 75, 309, 428);
		panelStock.add(spB);
		
		tableA = new JTable(data1, columns);
		tableB = new JTable(data2, columns);
		JTable[] table = {tableA, tableB};
		
		tableB.setFont(new Font("Arial", Font.PLAIN, 16));
		tableB.setFillsViewportHeight(true);
		spB.setColumnHeaderView(tableB);
		spB.setViewportView(tableB);
		tableB.setColumnSelectionAllowed(true);
		
		spA = new JScrollPane();
		spA.setBounds(43, 75, 309, 428);
		panelStock.add(spA);
		
		tableA.setFont(new Font("Arial", Font.PLAIN, 16));
		tableA.setFillsViewportHeight(true);
		spA.setColumnHeaderView(tableA);
		spA.setViewportView(tableA);
		tableA.setColumnSelectionAllowed(true);
		
		JLabel lblb = new JLabel("\u5206\u5E97B");
		lblb.setBounds(503, 13, 57, 19);
		panelStock.add(lblb);
		
		JLabel lbla = new JLabel("\u5206\u5E97A");
		lbla.setBounds(206, 13, 57, 19);
		panelStock.add(lbla);
		
		JSlider slider = new JSlider();
		slider.setBounds(274, 13, 200, 26);
		panelStock.add(slider);
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				currentTable = slider.getValue();
				changeTable();
			}
		});
		slider.setMaximum(1);
		
		btnStock = new JToggleButton("\u5EAB\u5B58\u7BA1\u7406");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(socket == null) && socket.isAlive())
				{
					if(btnSync.isSelected())
					{
						textArea.append("\n" + "please finish this Socket first.");
						btnStock.setSelected(false);
					}else
					{
						textAreaChatBox.append("\n" + "please finish this Socket first.");
						btnStock.setSelected(false);
					}
				}else
				{
					panelStock.setVisible(true);
					panelInit.setVisible(false);
					panelSync.setVisible(false);
					panelCustomer.setVisible(false);
					btnSync.setSelected(false);
					btnCustomer.setSelected(false);
				}
			}
		});
		btnStock.setBounds(77, 13, 137, 27);
		frmServer.getContentPane().add(btnStock);
		
		btnSync = new JToggleButton("\u8CC7\u6599\u540C\u6B65");
		btnSync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(socket == null) && socket.isAlive())
				{
					if(btnCustomer.isSelected())
					{
						textAreaChatBox.append("\n" + "please finish this Socket first.");
						btnSync.setSelected(false);
					}else
					{
						textArea.append("\n" + "please finish this Socket first.");
						btnSync.setSelected(false);
					}
				}else
				{
					panelStock.setVisible(false);
					panelInit.setVisible(false);
					panelSync.setVisible(true);
					panelCustomer.setVisible(false);
					btnStock.setSelected(false);
					btnCustomer.setSelected(false);
					textFieldSyncData.setText("");
					rdbtna.setSelected(false);
					rdbtnb.setSelected(false);
				}
			}
		});
		btnSync.setBounds(329, 12, 137, 27);
		frmServer.getContentPane().add(btnSync);
		
		btnCustomer = new JToggleButton("\u5BA2\u670D\u670D\u52D9");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(socket == null) && socket.isAlive())
				{
					if(btnSync.isSelected())
					{
						textArea.append("\n" + "please finish this Socket first.");
						btnStock.setSelected(false);
					}else
					{
						textAreaChatBox.append("\n" + "please finish this Socket first.");
						btnStock.setSelected(false);
					}
				}else
				{
					if(socket != null && socket.isAlive())
					{
						textArea.append("\n" + "please finish this Socket first.");
						btnCustomer.setSelected(false);
					}else
					{
						panelStock.setVisible(false);
						panelInit.setVisible(false);
						panelSync.setVisible(false);
						panelCustomer.setVisible(true);
						btnStock.setSelected(false);
						btnSync.setSelected(false);
					}
				}
			}
		});
		btnCustomer.setBounds(555, 12, 137, 27);
		frmServer.getContentPane().add(btnCustomer);
		
		btnInc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = (int)table[currentTable].getValueAt(table[currentTable].getSelectedRow(), 1) +1;
				table[currentTable].setValueAt(a , table[currentTable].getSelectedRow(), 1);
			}
		});
		
		btnDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = (int)table[currentTable].getValueAt(table[currentTable].getSelectedRow(), 1) -1;
				table[currentTable].setValueAt(a , table[currentTable].getSelectedRow(), 1);
			}
		});
		
		tableA.getColumnModel().getColumn(0).setPreferredWidth(350);
		tableB.getColumnModel().getColumn(0).setPreferredWidth(350);
	}
}
