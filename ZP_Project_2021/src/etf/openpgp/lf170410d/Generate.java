package etf.openpgp.lf170410d;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.OptionPaneUI;

@SuppressWarnings("serial")
public class Generate extends JFrame {

	private JTextField textField_Name;
	private JRadioButton radioBtn_4096;
	private JRadioButton radioBtn_2048;
	private JRadioButton radioBtn_1024;
	private JTextField textField_Email;

	public Generate() {
		setTitle("Key_Generator");
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

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(65, 31, 46, 14);
		getContentPane().add(lblName);

		textField_Name = new JTextField();
		textField_Name.setBounds(128, 28, 186, 20);
		getContentPane().add(textField_Name);
		textField_Name.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(65, 68, 46, 14);
		getContentPane().add(lblEmail);

		textField_Email = new JTextField();
		textField_Email.setBounds(128, 65, 186, 20);
		getContentPane().add(textField_Email);
		textField_Email.setColumns(10);

		JLabel lblRSA = new JLabel("Key length in RSA");
		lblRSA.setBounds(127, 115, 186, 24);
		lblRSA.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblRSA);

		radioBtn_1024 = new JRadioButton("1024");
		radioBtn_1024.setBounds(65, 154, 60, 23);
		getContentPane().add(radioBtn_1024);

		radioBtn_2048 = new JRadioButton("2048");
		radioBtn_2048.setBounds(160, 154, 60, 23);
		getContentPane().add(radioBtn_2048);

		radioBtn_4096 = new JRadioButton("4096");
		radioBtn_4096.setBounds(255, 154, 60, 23);
		getContentPane().add(radioBtn_4096);

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtn_1024);
		bg.add(radioBtn_2048);
		bg.add(radioBtn_4096);

		JButton btnSubmit = new JButton("SUBMIT");

		btnSubmit.setFont(new Font("Arial", Font.BOLD, 14));
		btnSubmit.setBounds(110, 207, 89, 30);
		getContentPane().add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = 1024;
				if (radioBtn_2048.isSelected())
					value = 2048;
				else if (radioBtn_4096.isSelected())
					value = 4096;
				try {
					RSAGen.GenKeyRing(textField_Name.getText() + "&" + textField_Email.getText(), "pass", value);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(textField_Name.getText() + "\n" + textField_Email.getText() + "\n" + value + "");

			}
		});

		JButton btnBack = new JButton("BACK");

		btnBack.setFont(new Font("Arial", Font.BOLD, 14));
		btnBack.setBounds(210, 207, 89, 30);
		getContentPane().add(btnBack);

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Display();
				dispose();
			}
		});

		setVisible(true);
	}

}
