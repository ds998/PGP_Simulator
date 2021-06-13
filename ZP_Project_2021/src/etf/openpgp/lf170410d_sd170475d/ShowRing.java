package etf.openpgp.lf170410d_sd170475d;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.util.Arrays.Iterator;

@SuppressWarnings("serial")
public class ShowRing extends JFrame {
	
	private JTable jtable;
	private boolean secret;
	private JScrollPane sp;
	public ShowRing() {
        setTitle("JTable Example");
		setSize(1200, 850);
		setResizable(true);
        
       
        
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PublicKeyRingCollection.getInstance().encode();
				SecretKeyRingCollection.getInstance().encode();
				e.getWindow().dispose();
			}
		});
  
        // Initializing the JTable
        jtable = new JTable(new PkrTableModel());
        jtable.setBounds(30, 40, 400, 800);
        
        // adding it to JScrollPane
        sp = new JScrollPane(jtable);
        
        //c.gridwidth = 3;
        add(sp,BorderLayout.NORTH);
        add(new JSeparator(),BorderLayout.CENTER);
        
        
        Panel btnPanel=new Panel(new GridLayout(1, 3));
        btnPanel.setSize(1200, 100);
        JButton button = new JButton("Public");    
        btnPanel.add(button);
        button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (secret) {
					sp.getViewport().remove(jtable);
					jtable = new JTable(new PkrTableModel());
			        jtable.setBounds(30, 40, 400, 800);
			        sp.getViewport().add(jtable);
					secret=false;
				}
			}
		});
        
        button = new JButton("Secret");       
        btnPanel.add(button);
        button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!secret) {
					sp.getViewport().remove(jtable);
					jtable = new JTable(new SkrTableModel());
			        jtable.setBounds(30, 40, 400, 800);
			        sp.getViewport().add(jtable);
					secret=false;
					secret=true;
				}
			}
		});

        button = new JButton("Back");  
        btnPanel.add(button);
        button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Display();
				dispose();
			}
		});
        
        add(btnPanel);
        
        // Frame Size
        //setSize(500, 200);
        // Frame Visible = true
        setVisible(true);
	}
}
