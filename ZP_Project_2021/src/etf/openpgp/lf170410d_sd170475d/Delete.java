package etf.openpgp.lf170410d_sd170475d;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Delete extends JFrame {
	
	
	private JRadioButton radioBtn_Public;
	private JRadioButton radioBtn_Secret;
	private JTextField textField_UserID;
	private JTextField textField_KeyId;

	public Delete() {
		setTitle("Remove Key");
		setBounds(300, 300, 430, 289);
		getContentPane().setLayout(null);
		// setLayeredPane(getLayeredPane());
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PublicKeyRingCollection.getInstance().encode();
				SecretKeyRingCollection.getInstance().encode();
				e.getWindow().dispose();
			}
		});
		
		
		JLabel lblName = new JLabel("UserID");
		lblName.setBounds(65, 31, 46, 14);
		getContentPane().add(lblName);

		textField_UserID = new JTextField();
		textField_UserID.setBounds(128, 28, 186, 20);
		getContentPane().add(textField_UserID);
		textField_UserID.setColumns(10);

		JLabel lblEmail = new JLabel("KeyID");
		lblEmail.setBounds(65, 68, 46, 14);
		getContentPane().add(lblEmail);

		textField_KeyId = new JTextField();
		textField_KeyId.setBounds(128, 65, 186, 20);
		getContentPane().add(textField_KeyId);
		textField_KeyId.setColumns(10);
		
		
		radioBtn_Public = new JRadioButton("Public Key");
		radioBtn_Public.setBounds(80, 104, 160, 23);
		radioBtn_Public.setEnabled(true);
		radioBtn_Public.setSelected(true);
		getContentPane().add(radioBtn_Public);

		radioBtn_Secret = new JRadioButton("Secret Key");
		radioBtn_Secret.setBounds(260, 104, 160, 23);
		radioBtn_Secret.setEnabled(true);
		getContentPane().add(radioBtn_Secret);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtn_Public);
		bg.add(radioBtn_Secret);
		
		
		
		
		
		
		
		
		
		/*
		 * BUTTONS
		 */
		
		JButton btnDelete = new JButton("DELETE");

		btnDelete.setFont(new Font("Arial", Font.BOLD, 12));
		btnDelete.setBounds(110, 180, 89, 30);
		getContentPane().add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				long keyId;
				String strKeyId=textField_KeyId.getText();
				if (strKeyId.length()==0) keyId=0;
				else keyId= Long.parseLong(strKeyId);
				
				if (radioBtn_Public.isSelected()) {
					PublicKeyRingCollection.getInstance().remove(textField_UserID.getText(), keyId);
				} else {
					SecretKeyRingCollection.getInstance().remove(textField_UserID.getText(), keyId);
				}
			}
		});
		
		JButton btnBack = new JButton("BACK");

		btnBack.setFont(new Font("Arial", Font.BOLD, 12));
		btnBack.setBounds(220, 180, 89, 30);
		getContentPane().add(btnBack);

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Display();
				dispose();
			}
		});

		getContentPane().add(btnBack);

		setVisible(true);
	}
}
