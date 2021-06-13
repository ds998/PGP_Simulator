package etf.openpgp.lf170410d_sd170475d;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ImportExport extends JFrame implements MouseListener, MouseMotionListener {
	
	
	public ImportExport() {
		//game = DnB;
				//game.setDisplay(this);
				setTitle("PGP_Menu");
				setSize(900, 550);
				setResizable(true);
				//setLayeredPane(getLayeredPane());
				//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						PublicKeyRingCollection.getInstance().encode();
						SecretKeyRingCollection.getInstance().encode();
						e.getWindow().dispose();
					}
				});
				
				//setBackground(Color.WHITE);
				//key = new ArrayList<String>();
				
				
				
				JPanel panel= new JPanel();
				panel.setLocation(100, 0);
				panel.setLayout(new FlowLayout());
			    panel.setMaximumSize(new Dimension(500,35));
				JLabel label= new JLabel("UVOZ/IZVOZ KLJUCEVA");
				label.setFont(new java.awt.Font("Times New Roman", 1, 24));
				
				
	
				
				JLabel file_label=new JLabel("Ime fajla(bez .asc ekstenzije)");
				file_label.setHorizontalAlignment(JLabel.CENTER);;
				
				JTextField file_field = new JTextField(12);
				file_field.setHorizontalAlignment(JTextField.CENTER);
				
                JLabel user_id_label=new JLabel("User ID (samo za izvoz)");
                user_id_label.setHorizontalAlignment(JLabel.CENTER);
				
				JTextField user_id_field = new JTextField(12);
				user_id_field.setHorizontalAlignment(JTextField.CENTER);
				
				JPanel jrb1=new JPanel(new GridLayout(1,2));
				
				
				JRadioButton export_rb = new JRadioButton("Izvoz");
				export_rb.setHorizontalAlignment(JRadioButton.CENTER);
				JRadioButton import_rb=new JRadioButton("Uvoz");
				import_rb.setHorizontalAlignment(JRadioButton.CENTER);
				
				ButtonGroup exp_imp = new ButtonGroup();
				
				exp_imp.add(import_rb);
				exp_imp.add(export_rb);
				
				JPanel jrb2=new JPanel(new GridLayout(1,2));
				
				JRadioButton secret_rb = new JRadioButton("Tajni");
				secret_rb.setHorizontalAlignment(JRadioButton.CENTER);
				JRadioButton public_rb=new JRadioButton("Javni");
				public_rb.setHorizontalAlignment(JRadioButton.CENTER);
				
				
				ButtonGroup sec_pub = new ButtonGroup();
				sec_pub.add(public_rb);
				sec_pub.add(secret_rb);
				
				JButton do_it = new JButton("Akcija");
				do_it.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String filename = file_field.getText();
						String user_id = user_id_field.getText();
						boolean pub=true;
						boolean exp=true;
						if(import_rb.isSelected()) {
							exp=false;
						}
						if(secret_rb.isSelected()) {
							pub=false;
						}
						if(pub) {
							if(exp) {
								PublicKeyRingCollection.getInstance().export_key_ring(user_id, filename);
							}
							else {
								PublicKeyRingCollection.getInstance().import_key_ring(filename);
							}
						}
						else {
							if(exp) {
								SecretKeyRingCollection.getInstance().export_key_ring(user_id, filename);
							}
							else {
								SecretKeyRingCollection.getInstance().import_key_ring(filename);
							}
						}
					}
				});
				do_it.setFont(new Font("Arial", Font.PLAIN, 40));
				JButton back = new JButton("Nazad");
				back.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						new Display();
						dispose();
					}
				});
				back.setFont(new Font("Arial", Font.PLAIN, 40));
				
				
				
		        //label.setBorder(border);
		        //label.setSize(300, 100);
				
				jrb1.add(import_rb,Component.CENTER_ALIGNMENT);
				jrb1.add(export_rb,Component.CENTER_ALIGNMENT);
				
				jrb2.add(public_rb);
				jrb2.add(secret_rb);
				
			
				
				JPanel main= new JPanel(new GridLayout(9,1));
				panel.add(label);
				main.add(panel);
				main.add(file_label);
				main.add(file_field);
				main.add(user_id_label);
				main.add(user_id_field);
				main.add(jrb1,Component.CENTER_ALIGNMENT);
				main.add(jrb2,Component.CENTER_ALIGNMENT);
				main.add(do_it);
				main.add(back);
				
				//pack();
				add(main);
				setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
