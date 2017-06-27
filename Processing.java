
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import java.awt.Font;
import java.awt.image.BufferedImage;


public class Processing {

	private JFrame frmProcessing;
	static JTextArea textArea;
	static JLabel lblimg;
	static JScrollPane ja;
	
	/**
	 * Launch the application.
	 * @return 
	 */
	public static void runProcess()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Processing window = new Processing();
					window.frmProcessing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*public static void main(String[] args) {
	
	}*/

	/**
	 * Create the application.
	 */
	public Processing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProcessing = new JFrame();
		frmProcessing.setTitle("Processing...");
		frmProcessing.setResizable(false);
		frmProcessing.setBounds(100, 100, 515, 330);
		frmProcessing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProcessing.getContentPane().setLayout(new GridLayout(1, 2, 10, 30));
		
		textArea = new JTextArea("",5,30);
		textArea.setFont(new Font("Arial", Font.PLAIN, 11));
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		textArea.setBounds(15, 13, 301, 347);
		
		
		ja = new JScrollPane(textArea);
		ja.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ja.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frmProcessing.getContentPane().add(ja);
		

		lblimg = new JLabel("");
		lblimg.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblimg.setBounds(227, 72, -173, 200);
		frmProcessing.getContentPane().add(lblimg);
	    
	}
	
	public static void setImage(BufferedImage bi)
	{
		bi = ImagePHash.resize(bi, Processing.lblimg.getWidth(),Processing.lblimg.getHeight());
        ImageIcon icon=new ImageIcon(bi);
        Processing.lblimg.setIcon(icon);	
	}
	
	public static void setImage(String strimg) throws IOException
	{
		BufferedImage img=ImageIO.read(new File(strimg));
		img = ImagePHash.resize(img, Processing.lblimg.getWidth(),Processing.lblimg.getHeight());
        ImageIcon icon=new ImageIcon(img);
        Processing.lblimg.setIcon(icon);
	}
}
