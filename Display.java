package etf.openpgp.lf170410d;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class Display extends JFrame implements MouseMotionListener, MouseListener {
	public static final int DOT_SIZE = 20;
	public static final Color[] colors = { Color.CYAN, Color.red };

	
	//private DotsAndBoxes game;
	
	//private static JPanel panel;
	
	public Display() {
		//game = DnB;
		//game.setDisplay(this);
		setTitle("PGP_Menu");
		setSize(1200, 850);
		setResizable(true);
		//setLayeredPane(getLayeredPane());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//setBackground(Color.WHITE);
		//key = new ArrayList<String>();
		
		
		
		JPanel panel= new JPanel();
		panel.setLayout(new FlowLayout());
	    panel.setPreferredSize(new Dimension(645,35));
		JLabel label= new JLabel("DOBRODOSLI U PGP SIMULATOR");
		label.setFont(new java.awt.Font("Times New Roman", 1, 24));
		
		
		JPanel panelButton= new JPanel(new GridLayout(2, 3));
		
		JButton button1= new JButton("Unos Kljuceva");
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Generate();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		button1= new JButton("Brisanje Kljuceva");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Delete();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		
		button1= new JButton("Prikaz Prstena");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new ShowRing();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		
		button1= new JButton("Uvoz");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new ImportExport();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		
		
		button1= new JButton("Slanje Poruke");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new SendMsg();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		button1= new JButton("Primanje Poruke");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new ReceiveMsg();
				dispose();
			}
		});
		button1.setFont(new Font("Arial", Font.PLAIN, 40));
		panelButton.add(button1);
		
		
		
        //label.setBorder(border);
        //label.setSize(300, 100);
		
		JPanel main= new JPanel(new GridLayout(2, 1));
		panel.add(label);
		main.add(panel);
		main.add(panelButton);
		//pack();
		add(main);
		setVisible(true);
	}

	






	

	@Override
	public void mouseClicked(MouseEvent event) {
	

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent event) {
		mouseMoved(event);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
			/*mouseX = event.getX();
			mouseY = event.getY();
			repaint();*/
		
	}


	
	
	

	

	/*public void paint(Graphics g) {
		/*if (!game.isMinMaxCalc() || game.isShowAlgorithm()) {
			dimension = getSize();
			if (newGame || game.isShowAlgorithm()) {
				g.setColor(Color.white);
				g.fillRect(0, 0, dimension.width, dimension.height);
				newGame = false;
			}
			if (drawn) {
				deleteRect(g);
				drawn = false;
			}
			dotGapHor = (dimension.width - startX - m * DOT_SIZE - 50) / m;
			dotGapVer = (dimension.height - startY - n * DOT_SIZE - 100) / n;
			drawDots(g);
			
		}

	}*/

	public static void main(String args[]) {
		//DotsAndBoxes d = DotsAndBoxes.getInstance();
		Display y = new Display();
		//d.start();
		// d.setDisplay(y);
	}

	
}
