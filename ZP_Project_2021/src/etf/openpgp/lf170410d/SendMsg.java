package etf.openpgp.lf170410d;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

@SuppressWarnings("serial")
public class SendMsg extends JFrame {

	private JTextField textField_File;
	private JRadioButton radioBtn_3DES;
	private JRadioButton radioBtnEnc_IDEA;
	JRadioButton[] buttonsEncRSA;
	private JTextField textField_Email;
	private JCheckBox encryption;

	private File file;
	private JCheckBox authentication;
	private JCheckBox zip;
	private JCheckBox radix;
	private JFileChooser fc;
	private JTextField textField_PKey;
	private JTextField textField_Sign_Key;

	public SendMsg() {
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

		encryption = new JCheckBox("Encription");
		encryption.setFont(new Font("Arial", Font.PLAIN, 15));
		encryption.setSize(250, 20);
		encryption.setLocation(150, 30);
		add(encryption);

		encryption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < buttonsEncRSA.length; i++)
					buttonsEncRSA[i].setEnabled(!buttonsEncRSA[i].isEnabled());

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
		ButtonGroup bg = new ButtonGroup();

		buttonsEncRSA = new JRadioButton[] { radioBtnEnc_IDEA, radioBtn_3DES };

		bg.add(radioBtnEnc_IDEA);
		bg.add(radioBtn_3DES);

		JLabel lblPublicKey = new JLabel("Select Public Key");
		lblPublicKey.setBounds(80, 150, 186, 24);
		lblPublicKey.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblPublicKey);

		/*
		 * ****************************************************** Chose Public Key
		 * ****************************************************
		 */

		textField_PKey = new JTextField();
		textField_PKey.setBounds(45, 190, 230, 25);
		getContentPane().add(textField_PKey);
		textField_PKey.setColumns(10);
		/*
		JButton btnchosePKey = new JButton("Chose Key");

		btnchosePKey.setFont(new Font("Arial", Font.BOLD, 10));
		btnchosePKey.setBounds(200, 190, 89, 18);
		getContentPane().add(btnchosePKey);

		btnchosePKey.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});*/

		/*****************************************
		 * 
		 ********************************************/
		JLabel lblSignKey = new JLabel("Select Sign Key");
		lblSignKey.setBounds(80, 280, 186, 24);
		lblSignKey.setFont(new Font("Times New Roman", 1, 18));
		getContentPane().add(lblSignKey);
		
		textField_Sign_Key = new JTextField();
		textField_Sign_Key.setBounds(45, 320, 230, 25);
		getContentPane().add(textField_Sign_Key);
		textField_Sign_Key.setColumns(10);
	
	
		authentication = new JCheckBox("Authentication");
		authentication.setFont(new Font("Arial", Font.PLAIN, 15));
		authentication.setSize(250, 20);
		authentication.setLocation(150, 240);
		add(authentication);
		
		
		/******************************
		 * 
		 ******************************/

		zip = new JCheckBox("Zip");
		zip.setFont(new Font("Arial", Font.PLAIN, 15));
		zip.setSize(250, 20);
		zip.setLocation(530, 80);
		add(zip);

		radix = new JCheckBox("Radix64");
		radix.setFont(new Font("Arial", Font.PLAIN, 15));
		radix.setSize(250, 20);
		radix.setLocation(530, 120);
		add(radix);

		/*
		 * ****************************************************** Chose File
		 * ****************************************************
		 */

		textField_File = new JTextField();
		textField_File.setBounds(430, 30, 130, 30);
		getContentPane().add(textField_File);
		textField_File.setColumns(10);

		JButton btnchoseFile = new JButton("ChooseFile");

		btnchoseFile.setFont(new Font("Arial", Font.BOLD, 10));
		btnchoseFile.setBounds(600, 30, 89, 28);
		getContentPane().add(btnchoseFile);

		fc = new JFileChooser();
		// fc.setBounds(80, 180, 36, 14);
		getContentPane().add(fc);

		btnchoseFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(SendMsg.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					// This is where a real application would open the file.
					textField_File.setText(file.getAbsolutePath());
				} else {
					System.out.println("Open command cancelled by user.\n");
				}

			}
		});

		/*****************************************
		 * Submit
		 ********************************************/

		JButton btnSubmit = new JButton("SUBMIT");

		btnSubmit.setFont(new Font("Arial", Font.BOLD, 12));
		btnSubmit.setBounds(430, 207, 89, 30);
		getContentPane().add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PGPPublicKey public_key= PublicKeyRingCollection.getInstance().get("Name1&Mail1");
				if (public_key == null) System.out.println("Null");
				
				int alg=-1;
				if (encryption.isSelected()) alg= radioBtnEnc_IDEA.isSelected()? PGPEncryptedData.IDEA: PGPEncryptedData.TRIPLE_DES;
				
				PGPPrivateKey private_key=null;
				PGPSecretKey pgpSec= null;
				if (authentication.isSelected()) {
					pgpSec = SecretKeyRingCollection.getInstance().get("Name1&Mail1").getSecretKey();
					String str="hello";
					try {
						private_key = pgpSec
								.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().build(str.toCharArray()));
					} catch (PGPException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PGPSignatureGenerator sGen = new PGPSignatureGenerator(
							new JcaPGPContentSignerBuilder(pgpSec.getPublicKey().getAlgorithm(), PGPUtil.SHA1));
				}
				
				SendMsgUtil.encryptMsg(file,public_key,radix.isSelected(), zip.isSelected(),encryption.isSelected(), authentication.isSelected(), alg,private_key,pgpSec);
			}
		});

		JButton btnBack = new JButton("BACK");

		btnBack.setFont(new Font("Arial", Font.BOLD, 12));
		btnBack.setBounds(580, 207, 89, 30);
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
