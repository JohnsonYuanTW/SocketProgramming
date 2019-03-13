package Socket;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultCaret;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;

public class Client {
	private static LoginDialog l;
	private JFrame frmClient;
	private JToggleButton btnStock;
	private JToggleButton btnSync;
	private JToggleButton btnCustomer;
	private JPanel panelSync;
	private JPanel panelCustomer;
	private JPanel panelStock;
	private JPanel panelInit;
	private JScrollPane spForStock;
	private JTable tableOfTheStore;
	private JRadioButton rdbtna;
	private String data = "";
	private JScrollPane spData;
	private static String username;
	private SocketForClient s;
	private StringBuffer sb = new StringBuffer();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Client window = new Client();
					window.frmClient.setVisible(false);
					l = new LoginDialog(window.frmClient, "Client");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Runnable callSocketFrom = new Runnable() {
	    public void run() {
	    	s = new SocketForClient(textFieldSyncData, textArea, null, "From");
	    }
	};
	
	Runnable callSocketTo = new Runnable() {
	    public void run() {
	    	s = new SocketForClient(textFieldSyncData, textArea, null, "To");
	    }
	};
	
	Runnable callSocketServer = new Runnable() {
	    public void run() {
	    	s = new SocketForClient(textFieldInputBox, textAreaChatBox, sb.toString(), "Server");
	    	
	    }
	};
	
	Object[][] data1={
			{"pencil", new Integer(16)},
			{"pen", new Integer(16)},
			{"eraser", new Integer(16)},
			{"ruler", new Integer(16)},
			{"pencil_case", new Integer(16)}
	};
	Object[][] data2={
			{"pencil", new Integer(2)},
			{"pen", new Integer(2)},
			{"eraser", new Integer(2)},
			{"ruler", new Integer(2)},
			{"pencil_case", new Integer(2)}
	};
	
	String[] columns = {"name", "quantity"};
	private JTextField textFieldSyncData;
	private JTextField textFieldInputBox;
	private JButton btnSend;
	private JTextArea textArea;
	private JScrollPane spChatBox;
	private JTextArea textAreaChatBox;
	private JButton btnStartServer;
	
	
	
	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setVisible(true);
		frmClient.setBounds(100, 100, 787, 631);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		frmClient.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
			    /* code run when component shown */
				username = l.getUsername();
				
				if(username.equals("userA"))
				{
					tableOfTheStore = new JTable(data1, columns);
				}else
				{
					tableOfTheStore = new JTable(data2, columns);
				}
				tableOfTheStore.setFont(new Font("Arial", Font.PLAIN, 16));
				tableOfTheStore.setFillsViewportHeight(true);
				spForStock.setColumnHeaderView(tableOfTheStore);
				spForStock.setViewportView(tableOfTheStore);
				tableOfTheStore.setColumnSelectionAllowed(true);
				tableOfTheStore.getColumnModel().getColumn(0).setPreferredWidth(350);
			}
			});
		
		panelCustomer = new JPanel();
		panelCustomer.setBounds(0, 52, 769, 532);
		frmClient.getContentPane().add(panelCustomer);
		panelCustomer.setLayout(null);
		panelCustomer.setVisible(false);
		
		textFieldInputBox = new JTextField();
		textFieldInputBox.setBounds(52, 349, 384, 69);
		panelCustomer.add(textFieldInputBox);
		textFieldInputBox.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sb.append(textFieldInputBox.getText() + "\n");
				textAreaChatBox.append(textFieldInputBox.getText() + "\n");
				textFieldInputBox.setText("");
			}
		});
		btnSend.setBounds(434, 349, 88, 69);
		panelCustomer.add(btnSend);
		
		btnStartServer = new JButton("Connect to Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaChatBox.append("Connecting...\n");
				new Thread(callSocketServer).start();
			}
		});
		btnStartServer.setBounds(567, 180, 173, 27);
		panelCustomer.add(btnStartServer);
		
		textAreaChatBox = new JTextArea();
		textAreaChatBox.setBounds(52, 39, 470, 311);
		panelCustomer.add(textAreaChatBox);
		
		spChatBox = new JScrollPane(textAreaChatBox);
		spChatBox.setBounds(52, 39, 470, 311);
		panelCustomer.add(spChatBox);
		
		panelSync = new JPanel();
		panelSync.setBounds(0, 52, 769, 532);
		frmClient.getContentPane().add(panelSync);
		panelSync.setLayout(null);
		panelSync.setVisible(false);
		
		JButton btnSyncFromServer = new JButton("Sync from Server");
		btnSyncFromServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("Connecting...\n");
				new Thread(callSocketFrom).start();
			}
		});
		btnSyncFromServer.setBounds(464, 193, 193, 27);
		panelSync.add(btnSyncFromServer);
		
		JButton btnSyncFromClient = new JButton("Sync from Client");
		btnSyncFromClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("Connecting...\n");
				new Thread(callSocketTo).start();
			}
		});
		btnSyncFromClient.setBounds(464, 261, 193, 27);
		panelSync.add(btnSyncFromClient);
		
		rdbtna = new JRadioButton("\u5206\u5E97\u8CC7\u6599");
		rdbtna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtna.setSelected(true);
				textFieldSyncData.setText("");
				data = "";
				int number = tableOfTheStore.getRowCount();
				for (int i=0; i < number; i++)
				{
					
					data += (String) tableOfTheStore.getValueAt(i, 0);
					data += " ";
					data += String.valueOf(tableOfTheStore.getValueAt(i, 1));
					data += " ";
				}
				textFieldSyncData.setText(data);
			}
		});
		rdbtna.setBounds(48, 39, 127, 27);
		panelSync.add(rdbtna);
		
		textFieldSyncData = new JTextField();
		textFieldSyncData.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSyncData.setEditable(false);
		textFieldSyncData.setBounds(60, 98, 331, 362);
		panelSync.add(textFieldSyncData);
		
		spData = new JScrollPane();
		spData.setBounds(60, 102, 331, 358);
		panelSync.add(spData);
		
		
		textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	
	        }

	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	if(textArea.getText().contains("Data retrieved."))
	        	{
	        		String data = textFieldSyncData.getText();
	        		String[] parts = data.split(" ");
	        		
//	        		int i = 0;
//	        		for (String part : parts) {
//	        		    //do something interesting here
//	        			
//	        		}
	        		
	        		for(int i=0; i<parts.length; i++)
	        		{
	        			
	        			if(i%2==1)
	        			{
	        				tableOfTheStore.setValueAt(Integer.valueOf(parts[i]), i/2, 1);
	        			}else
	        			{
	        				tableOfTheStore.setValueAt(parts[i], i/2, 0);
	        			}
	        		}
	        		rdbtna.setSelected(false);
	        	}
	        }

	        @Override
	        public void changedUpdate(DocumentEvent arg0) {
	        	
	        }
	    });
		
		JScrollPane spStatus = new JScrollPane(textArea);
		spStatus.setBounds(424, 386, 313, 74);
		panelSync.add(spStatus);
		spStatus.setBounds(424, 386, 313, 74);
		
		panelStock = new JPanel();
		panelStock.setBounds(0, 52, 769, 532);
		frmClient.getContentPane().add(panelStock);
		panelStock.setLayout(null);
		panelStock.setVisible(false);
		
		panelStock.setVisible(false);
		
		JButton btnDec = new JButton("dec");
		btnDec.setBounds(625, 75, 99, 27);
		panelStock.add(btnDec);
		
		JButton btnInc = new JButton("inc");
		btnInc.setBounds(503, 75, 99, 27);
		panelStock.add(btnInc);
		
		
		
		spForStock = new JScrollPane();
		spForStock.setBounds(43, 75, 309, 428);
		panelStock.add(spForStock);
		
		
		
		JLabel lbla = new JLabel("\u5206\u5E97\u7BA1\u7406");
		lbla.setFont(new Font("·L³n¥¿¶ÂÅé", Font.PLAIN, 20));
		lbla.setBounds(352, 13, 90, 27);
		panelStock.add(lbla);
		
		btnInc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = (int)tableOfTheStore.getValueAt(tableOfTheStore.getSelectedRow(), 1) +1;
				tableOfTheStore.setValueAt(a , tableOfTheStore.getSelectedRow(), 1);
			}
		});
		
		btnDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = (int)tableOfTheStore.getValueAt(tableOfTheStore.getSelectedRow(), 1) -1;
				tableOfTheStore.setValueAt(a , tableOfTheStore.getSelectedRow(), 1);
			}
		});
		
		
		
		panelInit = new JPanel();
		panelInit.setBounds(0, 52, 769, 532);
		frmClient.getContentPane().add(panelInit);
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
		
		btnStock = new JToggleButton("\u5EAB\u5B58\u7BA1\u7406");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelStock.setVisible(true);
				panelInit.setVisible(false);
				panelSync.setVisible(false);
				panelCustomer.setVisible(false);
				btnSync.setSelected(false);
				btnCustomer.setSelected(false);
			}
		});
		btnStock.setBounds(77, 13, 137, 27);
		frmClient.getContentPane().add(btnStock);
		
		btnSync = new JToggleButton("\u8CC7\u6599\u540C\u6B65");
		btnSync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelStock.setVisible(false);
				panelInit.setVisible(false);
				panelSync.setVisible(true);
				panelCustomer.setVisible(false);
				btnStock.setSelected(false);
				btnCustomer.setSelected(false);
				textFieldSyncData.setText("");
				
			}
		});
		btnSync.setBounds(329, 12, 137, 27);
		frmClient.getContentPane().add(btnSync);
		
		btnCustomer = new JToggleButton("\u5BA2\u670D\u670D\u52D9");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelStock.setVisible(false);
				panelInit.setVisible(false);
				panelSync.setVisible(false);
				panelCustomer.setVisible(true);
				btnStock.setSelected(false);
				btnSync.setSelected(false);
			}
		});
		btnCustomer.setBounds(555, 12, 137, 27);
		frmClient.getContentPane().add(btnCustomer);
	}
}
