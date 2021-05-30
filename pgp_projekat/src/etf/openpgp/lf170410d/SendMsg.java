package etf.openpgp.lf170410d;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SendMsg extends JFrame {
	
	private JTextField textField_Name;
	private JRadioButton radioBtn_3DES;
	private JRadioButton radioBtnEnc_IDEA;
	JRadioButton[] buttonsEncRSA;
	private JTextField textField_Email;
	private JCheckBox encryption;
	
	private JCheckBox authentication;
	private JCheckBox zip;
	private JCheckBox radix;
	
	public SendMsg() {
		setTitle("Key_Generator");
		setBounds(100, 100, 730, 489);
		getContentPane().setLayout(null);
		// setLayeredPane(getLayeredPane());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		encryption = new JCheckBox("Encription");
        encryption.setFont(new Font("Arial", Font.PLAIN, 15));
        encryption.setSize(250, 20);
        encryption.setLocation(150, 30);
        add(encryption);
        
        
        encryption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<buttonsEncRSA.length;i++) buttonsEncRSA[i].setEnabled(!buttonsEncRSA[i].isEnabled());
				
			}
		});
		
		JLabel lblEncAlg = new JLabel("Encryption Algorithm");
		lblEncAlg.setBounds(80, 70, 186, 24);
		lblEncAlg.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblEncAlg);
		

		radioBtnEnc_IDEA = new JRadioButton("IDEA");
		radioBtnEnc_IDEA.setBounds(100, 104, 60, 23);
		radioBtnEnc_IDEA.setEnabled(false);
		getContentPane().add(radioBtnEnc_IDEA);
		
		
		radioBtn_3DES = new JRadioButton("3DES");
		radioBtn_3DES.setBounds(200, 104, 60, 23);
		radioBtn_3DES.setEnabled(false);
		getContentPane().add(radioBtn_3DES);
		ButtonGroup bg= new ButtonGroup();
		
		buttonsEncRSA = new JRadioButton[]{radioBtnEnc_IDEA,radioBtn_3DES};
		
		
		bg.add(radioBtnEnc_IDEA);
		bg.add(radioBtn_3DES);
		
		JLabel lblPublicKey = new JLabel("Select Public Key");
		lblPublicKey.setBounds(80, 150, 186, 24);
		lblPublicKey.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblPublicKey);
		
		authentication = new JCheckBox("Authentication");
		authentication.setFont(new Font("Arial", Font.PLAIN, 15));
		authentication.setSize(250,20);
		authentication.setLocation(150,220);
		add(authentication);
		
		zip = new JCheckBox("Zip");
		zip.setFont(new Font("Arial", Font.PLAIN, 15));
		zip.setSize(250,20);
		zip.setLocation(150,280);
		add(zip);
		
		radix = new JCheckBox("Radix64");
		radix.setFont(new Font("Arial", Font.PLAIN, 15));
		radix.setSize(250,20);
		radix.setLocation(150,310);
		add(radix);
		
		
		
		setVisible(true);
	}
}
