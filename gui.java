

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.beans.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


@SuppressWarnings("unused")

public class gui {
	Integer ID;
	public static JFrame frmPlagiarimgV;
	static JTextField tbplag;
	static JCheckBox cb;
	static JLabel lblResult;
	static JFrame outputFrame;
	static JLabel lbloutput, lblNewLabel;
	static JTextField tbplag1;
	String filepath;
String path,destinationpath;
String hashvalue;
int value;
int index;
BufferedImage image = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					gui.frmPlagiarimgV.setVisible(true);
					Processing.runProcess();
					//DatabaseHandling.createtables();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ImagePHash.initCoefficients();
		
	}

	public gui() {
		initialize();
	}

	private void initialize() {
		
		
		frmPlagiarimgV = new JFrame();
		frmPlagiarimgV.setTitle("Plagiarimg v1.0");
		//frame.setSize(new Dimension(800, 600));
		frmPlagiarimgV.setBackground(Color.GRAY);
		frmPlagiarimgV.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frmPlagiarimgV.setBounds((int)((screenSize.getWidth()-880)/2),(int)((screenSize.getHeight()-600)/2-50), 880, 650);
		frmPlagiarimgV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		frmPlagiarimgV.setJMenuBar(menuBar);
		
		
		

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		final JMenuItem mntmInsert = new JMenuItem("Insert");
		mntmInsert.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				JFileChooser openFile = new JFileChooser();
                openFile.showOpenDialog(null);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getSource()==mntmInsert){  
					try {
						try {
							/*DatabaseHandling.*/addFile();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					}  
			}
			public void addFile() throws IOException, ClassNotFoundException, SQLException {
				// TODO Auto-generated method stub
				DatabaseHandling.connectDatabase();
				JFileChooser fc=new JFileChooser();  
				int i=fc.showOpenDialog(frmPlagiarimgV);  
				          
				if(i==JFileChooser.APPROVE_OPTION){  
				File f=fc.getSelectedFile();  
				filepath=f.getPath();
				path=filepath;
	            File imagefile = new File(path);
	            image = ImageIO.read(imagefile);
	            int index = path.lastIndexOf('\\');
	            String name = path.substring(index+1);
	            System.out.println(name);
	            int ii=name.lastIndexOf('.');
	            String prop=name.substring(ii+1);
	            System.out.println(prop);
	            destinationpath="E:\\images\\"+name;
	            
	            image = ImagePHash.resize(image, 768, 768);
	            
	            System.out.println("Here");
	            if(prop.equalsIgnoreCase("jpg"))
	            {
	            	   ImageIO.write(image, "jpg",new File(destinationpath));
	            }
	            	if(prop.equalsIgnoreCase("png"))
	            	{
	            		 ImageIO.write(image, "bmp",new File(destinationpath));
	            	}
	            		if(prop.equalsIgnoreCase("bmp"))
	            		{
	            			 ImageIO.write(image, "bmp",new File(destinationpath));
	            		}
	            			if(prop.equalsIgnoreCase("gif"))
	            			{
	            				 ImageIO.write(image, "bmp",new File(destinationpath));
	            			}
	            			int itable=DatabaseHandling.idtable();
	            			
	            			ID=itable;
	            			System.out.println("id is here"+ID);
	            			
	            			ID++;
	            			DatabaseHandling.connectDatabase();
	            			PreparedStatement statement = DatabaseHandling.con.prepareStatement("INSERT INTO IMGDB (id, path) VALUES ( ?, ?)");
	            			statement.setInt(1, ID);
	            			statement.setString(2, destinationpath);
	            			statement.execute();
	            			System.out.println("added to imgdb");
	            			System.out.println(destinationpath);
	            			String obj = HistoChecks.getHisto(destinationpath);
	            			System.out.println(obj);
	            			String histovalue= HistoChecks.getHisto(destinationpath);
	            			
	            			PreparedStatement statement1 = DatabaseHandling.con.prepareStatement("INSERT INTO HISTODB (id, histo) VALUES ( ?, ?)");
	            			statement1.setInt(1, ID);
	            			statement1.setString(2, histovalue);
	            			statement1.execute();
	            			System.out.println("added to histodb");
	            			
				}
			}
		});
		mnFile.add(mntmInsert);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frmPlagiarimgV, "Plagiarism Version 1.0");
			}
		});
		mnHelp.add(mntmAbout);
		JMenuItem mntmLicense = new JMenuItem("License");
		mntmLicense.addMouseListener(new MouseAdapter() {
			@Override

			public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(frmPlagiarimgV, "Plagiarism Version 1.0");	
			}
		});
		mnHelp.add(mntmLicense);
		
		JMenu mnExit = new JMenu("Exit");
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		menuBar.add(mnExit);
		frmPlagiarimgV.getContentPane().setLayout(null);
		
		final JPanel panel = new JPanel();
		panel.setBounds(frmPlagiarimgV.getWidth()/2-150,frmPlagiarimgV.getHeight()/2-200,300,300);
		frmPlagiarimgV.getContentPane().add(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JLabel lbl1 = new JLabel("Image:");
		lbl1.setBounds(22, 234, 70, 20);
		
		tbplag = new JTextField();
		tbplag.setEditable(false);
		tbplag.setBounds(80, 234, 180, 24);
		tbplag.setColumns(10);
		
		final JLabel lblplagimg = new JLabel("");
		lblplagimg.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		lblplagimg.setBounds(20, 0, 260, 220);
		
		final JButton btnbrwplagimg = new JButton("Browse..");
		btnbrwplagimg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==btnbrwplagimg){  
					try {
						openFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					}  
			}

			private void openFile() throws IOException {
				// TODO Auto-generated method stub
				JFileChooser fc=new JFileChooser(new File("C:\\Users\\images\\"));  
				int i=fc.showOpenDialog(frmPlagiarimgV);  
				          
				if(i==JFileChooser.APPROVE_OPTION){  
				File f=fc.getSelectedFile();  
				filepath=f.getPath();
				
				tbplag.setText(filepath);
				BufferedImage img=ImageIO.read(new File(filepath));
				img = ImagePHash.resize(img, lblplagimg.getWidth(),lblplagimg.getHeight());
		        ImageIcon icon=new ImageIcon(img);
				lblplagimg.setIcon(icon);
				frmPlagiarimgV.getContentPane().add(panel);
				}
			}
		});

		btnbrwplagimg.setBounds(55, 265, 95, 25);
		GroupLayout gl_panel1 = new GroupLayout(panel);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGap(20)
					.addComponent(lblplagimg, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel1.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel1.createSequentialGroup()
							.addGap(58)
							.addComponent(tbplag, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))))
				.addGroup(gl_panel1.createSequentialGroup()
					.addGap(55)
					.addComponent(btnbrwplagimg, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(130))
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addComponent(lblplagimg, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbplag, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(btnbrwplagimg, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel1);
		
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(520, 75, 300, 300);
		frmPlagiarimgV.getContentPane().add(panel_1);
		panel_1.setVisible(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		tbplag1 = new JTextField();
		tbplag1.setEditable(false);
		tbplag1.setBounds(80, 234, 180, 24);
		panel_1.add(tbplag1);
		tbplag1.setColumns(10);
		
		JLabel lblImage_1 = new JLabel("Image:");
		lblImage_1.setBounds(22, 234, 70, 20);
		panel_1.add(lblImage_1);
		
		final JLabel lblplagimg1 = new JLabel("");
		lblplagimg1.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		lblplagimg1.setBackground(Color.DARK_GRAY);
		lblplagimg1.setBounds(20, 0, 260, 220);
		panel_1.add(lblplagimg1);
		
		final JButton btnbrwplagimg1 = new JButton("Browse..");
		btnbrwplagimg1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==btnbrwplagimg1){  
					try {
						openFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					}  
			}

			private void openFile() throws IOException {
				// TODO Auto-generated method stub
				JFileChooser fc=new JFileChooser(new File("C:\\Users\\images\\"));  
				int i=fc.showOpenDialog(frmPlagiarimgV);  
				          
				if(i==JFileChooser.APPROVE_OPTION){  
				File f=fc.getSelectedFile();  
				filepath=f.getPath();
				tbplag1.setText(filepath);
				BufferedImage img=ImageIO.read(new File(filepath));
				img = ImagePHash.resize(img, lblplagimg1.getWidth(),lblplagimg1.getHeight());
		        ImageIcon icon=new ImageIcon(img);
				lblplagimg1.setIcon(icon);
				frmPlagiarimgV.getContentPane().add(panel);
				}
			}
		});

		btnbrwplagimg1.setBounds(55, 269, 95, 25);
		panel_1.add(btnbrwplagimg1);
		
		
		
		
		final JButton btnCheckPlagiarism = new JButton("Check Plagiarism");
	
		btnCheckPlagiarism.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==btnCheckPlagiarism){  
					try {
						
						Algo.algotest();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					}  
			}
		});
	
		btnCheckPlagiarism.setBounds(360, 532, 176, 25);
		frmPlagiarimgV.getContentPane().add(btnCheckPlagiarism);

		
		
		cb = new JCheckBox("Upload Both images");
		cb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 panel_1.setVisible(cb.isSelected());
				 if(cb.isSelected())
				 {
					 Processing.textArea.append("For 2 images plagiarism detection "+"\n");
					 panel.setBounds(100,75,300,300);
					 frmPlagiarimgV.getContentPane().add(panel);
					 panel.setLayout(null);
				 }

					else
					{
						panel.setBounds(frmPlagiarimgV.getWidth()/2-150,frmPlagiarimgV.getHeight()/2-200,300,300);
						frmPlagiarimgV.getContentPane().add(panel);
						panel.setLayout(null);
					}
			}
		});
		cb.setBounds(132, 487, 167, 23);
		frmPlagiarimgV.getContentPane().add(cb);
		
		
		lblResult = new JLabel("");
		lblResult.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		lblResult.setBounds(frmPlagiarimgV.getWidth()/2+100, 450,frmPlagiarimgV.getWidth()/3, 60);
		frmPlagiarimgV.getContentPane().add(lblResult);
		
		
		lblNewLabel = new JLabel("Plagiarimg");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBorder(null);
		lblNewLabel.setBounds(400, 11, 200, 30);
		frmPlagiarimgV.getContentPane().add(lblNewLabel);
		
	
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}