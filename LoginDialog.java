package Socket;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel message;

	private void checkAccount(JFrame parent, String who)
	{
		char[] input = passwordField.getPassword();
		char[] correctPassword = { '1', '0', '4', '4', '0', '3', '5' ,'2', '0'};
		if(who.equals("Client"))
		{
			if((usernameField.getText().equals("userA") || usernameField.getText().equals("userB")) && Arrays.equals(input, correctPassword))
			{
				this.setVisible(false);
				parent.setVisible(true);
			}else
			{
				passwordField.setText("");
				usernameField.setText("");
				message.setText("Incorrect Input. Please re-enter.");
				message.setForeground(Color.red);
			}
		}else
		{
			if(usernameField.getText().equals("admin") && Arrays.equals(input, correctPassword))
			{
				this.setVisible(false);
				parent.setVisible(true);
			}else
			{
				passwordField.setText("");
				usernameField.setText("");
				message.setText("Incorrect Input. Please re-enter.");
				message.setForeground(Color.red);
			}
		}
	}
	
	public String getUsername()
	{
		return usernameField.getText();
	}
	
	/**
	 * Create the dialog.
	 */
	public LoginDialog(JFrame parent, String who) {
		setTitle("Login");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsername = new JLabel("Username : ");
			lblUsername.setFont(new Font("Arial", Font.PLAIN, 18));
			lblUsername.setBounds(14, 90, 97, 41);
			contentPanel.add(lblUsername);
		}
		
		message = new JLabel("Please Login with Account Provided.");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Arial", Font.PLAIN, 20));
		message.setBounds(39, 13, 350, 55);
		contentPanel.add(message);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPassword.setBounds(14, 144, 97, 41);
		contentPanel.add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setText("");
		usernameField.setBounds(125, 99, 264, 25);
		contentPanel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125, 153, 264, 25);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkAccount(parent, who);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
