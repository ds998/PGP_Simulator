package etf.openpgp.lf170410d_sd170475d;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchProviderException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class ReceiveMsg extends JFrame {
	
	private JTextField password_text;
	private JTextField file_text;
	private JFileChooser fc;
	
	private JButton submit;
	private JButton back;
	private JButton choose_file;
	public ReceiveMsg() {
		setTitle("Decryptor");
		setBounds(100, 100, 730, 489);
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
		
		file_text = new JTextField();
		file_text.setBounds(430, 30, 130, 30);
		getContentPane().add(file_text);
		file_text.setColumns(10);

		 choose_file = new JButton("Choose A File");

		choose_file.setFont(new Font("Arial", Font.BOLD, 10));
		choose_file.setBounds(600, 30, 89, 28);
		getContentPane().add(choose_file);

		fc = new JFileChooser();
		// fc.setBounds(80, 180, 36, 14);
		getContentPane().add(fc);

		choose_file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(ReceiveMsg.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					file_text.setText(file.getAbsolutePath());
				} else {
					System.out.println("Open command cancelled by user.\n");
				}

			}
		});
		
		
		JLabel lblPass = new JLabel("Enter password");
		lblPass.setBounds(80, 150, 186, 24);
		lblPass.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblPass);

		/*
		 * ****************************************************** Chose Public Key
		 * ****************************************************
		 */

		password_text = new JTextField();
		password_text.setBounds(30, 190, 130, 20);
		getContentPane().add(password_text);
		password_text.setColumns(10);
		
		
		
		submit = new JButton("SUBMIT");

		submit.setFont(new Font("Arial", Font.BOLD, 12));
		submit.setBounds(430, 207, 89, 30);
		getContentPane().add(submit);

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String file_str = file_text.getText();
				String pass_str = password_text.getText();
				try {
					ReceiveMsgUtil.decryptFile(file_str, pass_str.toCharArray());
				} catch (NoSuchProviderException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});

		 back = new JButton("BACK");

		back.setFont(new Font("Arial", Font.BOLD, 12));
		back.setBounds(580, 207, 89, 30);
		getContentPane().add(back);

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Display();
				dispose();
			}
		});

		getContentPane().add(back);

		setVisible(true);
	}
	
}
