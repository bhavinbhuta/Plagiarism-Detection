
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class Algo{
	
	private static int degrees; //This will be used to set rotation degrees
	private static int degrees_new_1;
	static int totals = 65534;
	private static int degrees_new_2;
	private static int degrees_new_3;
	private static int degrees_new_4;
	private int degrees_new_5;
	static BufferedImage imgsrc,imgplag,imgsrcmod,imgsrcmod_15p,imgsrcmod_15m,imgplag2,imgsrc2;
	static BufferedImage imgsrcmod_7p,imgsrcmod_7m,imgsrcmod_1125p,imgsrcmod_1125m;
	static BufferedImage imgsrcmod_4,imgsrcmod_3,imgsrcmod_5;
	static String issrc,isplag,isplag2,issrc2;
	static String srchash,plaghash;
	static int round2_flag=151;
	static int round3_flag=2251;
	static int round4_flag=11251;
	static int ham_dist;
	static int prev_ham_dist = 1025;
	static int ham_dist_15p;
	static int ham_dist_15m;
	static int ham_dist_7p;
	static int ham_dist_7m;
	static int ham_dist_1125p;
	static int ham_dist_1125m;
	static String h1_ser,h2_ser;
	static long[][][] h1,h2;
	static int[] index;
	static int[] differ;
	static int num = 0;
	static int[] arrl5= {-1,-1,-1,-1,-1};
	static int arrl5index=0;
	static int thresh=400;
	
	public static void algotest() throws IOException, ClassNotFoundException, SQLException { 
		
		reinit();
		Processing.textArea.append("Starting Plagiarism detection......"+"\n");
		if(gui.cb.isSelected())
		 {
			issrc = gui.tbplag.getText();
			isplag = gui.tbplag1.getText();
			Processing.textArea.append("Received both images..."+"\n");
			thresh=400;
			checkPlag();
		 }
		else
		{	
			issrc = gui.tbplag.getText();
			System.out.println(issrc);
			Processing.textArea.append("Source File: "+issrc+"\n");
			
			if(new File("E:\\images").listFiles().length==0)
			{
				HistoChecks.getHisto(issrc);
				System.out.println(issrc);
			}
			if(new File("E:\\images").listFiles().length>0);
			{
				filter();
				DatabaseHandling.connectDatabase();
				Statement stmt = DatabaseHandling.con.createStatement();
				for(int i=0;i<arrl5index;i++)
				{
				ResultSet rs = stmt.executeQuery("select path from imgdb where id = " + arrl5[i]);
				Processing.textArea.append("Received both images..."+"\n");
				String path = null; 
				if(rs.next())
				{
				path= rs.getString("path") ;
				}
				isplag = path;
				System.out.println("FOR *********************************    "+arrl5[i]);
				Processing.textArea.append("CALCULATING FOR ************   "+arrl5[i]);
				checkPlag();
				}
			}
		}
		
		
		
}
	
	public static int checkPlag() throws IOException
	{
		try {
			imgsrc = ExtractRotate.roundRotated(issrc, 0);
			File fo = new File("C:\\Users\\images\\final1.jpg");
			ImageIO.write(imgsrc, "jpg", fo);
			Processing.setImage(imgsrc);	
			try {
				srchash = ImagePHash.getHash(imgsrc);
				System.out.println("Source Img "+srchash);
				Processing.textArea.append("Source Image Hash: "+srchash+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		File fo;
		
		
		try {
			imgplag = ExtractRotate.roundRotated(isplag, 0);
			File fo1 = new File("C:\\Users\\images\\final2.jpg");
			ImageIO.write(imgplag, "jpg", fo1);
			Processing.setImage(imgplag);
			try {
				plaghash = ImagePHash.getHash(imgplag);
				System.out.println("Plag Img "+plaghash);
				Processing.textArea.append("Plag Image Hash: "+plaghash+"\n");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		
		
	
		for (degrees=0; degrees<=360; degrees+=30)
		{
		imgsrcmod = ExtractRotate.roundRotated(issrc, degrees);
		fo = new File("C:\\Users\\images\\final"+degrees+".jpg");
		ImageIO.write(imgsrcmod, "jpg", fo);
		Processing.setImage(imgsrcmod);
		try {
			srchash = ImagePHash.getHash(imgsrcmod);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ham_dist = ImagePHash.distance(plaghash, srchash);
		System.out.println("Sourc Img: "  + "  		Degree: " + degrees + "Hamming: " + ham_dist);
		Processing.textArea.append("Img: "  + "  	Degree: " + degrees + "Hamming: " + ham_dist+"\n");
			
		if(ham_dist < prev_ham_dist)
			{
			degrees_new_1=degrees;
			if(degrees_new_1==0)
				degrees_new_1=360;
			prev_ham_dist=ham_dist;
			}
		
		}
		imgsrcmod = ExtractRotate.roundRotated(issrc, degrees_new_1);
		System.out.println("Hamming distance: "+prev_ham_dist+"	degrees:" + degrees_new_1);
		Processing.textArea.append("Hamming distance: "+prev_ham_dist+"	degrees:" + degrees_new_1+"\n");
		Processing.setImage(imgsrcmod);
		System.out.println("		degrees:" + degrees_new_1);
		if(prev_ham_dist<thresh)
		{
			dispOriginal(isplag);
			return 0;
		}
		
		{
		imgsrcmod_15p = ExtractRotate.roundRotated(imgsrcmod, degrees_new_1+15);
		File fo2 = new File("C:\\Users\\images\\final15p.jpg");
		ImageIO.write(imgsrcmod_15p, "jpg", fo2);
		Processing.setImage(imgsrcmod_15p);
		try {
			srchash = ImagePHash.getHash(imgsrcmod_15p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ham_dist_15p = ImagePHash.distance(plaghash, srchash);
		System.out.println("Sourc Img: "  + "  		Degree: " + (degrees_new_1+15) + "Hamming: " + ham_dist_15p);
		Processing.textArea.append("Img: "  + "		Degree: " + (degrees_new_1+15) + "Hamming: " + ham_dist_15p+"\n");
		if(ham_dist_15p<thresh)
		{
			dispOriginal(isplag);
			return 0;
		}
		}
		
		{
		imgsrcmod_15m = ExtractRotate.roundRotated(imgsrcmod, degrees_new_1-15);
		fo = new File("C:\\Users\\images\\final15m.jpg");
		ImageIO.write(imgsrcmod_15m, "jpg", fo);
		Processing.setImage(imgsrcmod_15m);
		try {
			srchash = ImagePHash.getHash(imgsrcmod_15m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ham_dist_15m = ImagePHash.distance(plaghash, srchash);
		System.out.println("Sourc Img: "  + "  		Degree: " + (degrees_new_1-15) + "Hamming: " + ham_dist_15m);
		Processing.textArea.append("Img: "  + "  	Degree: " + (degrees_new_1-15) + "Hamming: " + ham_dist_15m+"\n");
		}		
		
		if(ham_dist_15p < ham_dist_15m)
			{
			round2_flag=151;
			degrees_new_2=degrees_new_1+15;
			System.out.println("Hamming distance: "+ham_dist_15p+"	degrees:" + degrees_new_2);
			Processing.textArea.append("Hamming distance: "+ham_dist_15p+"	degrees:" + degrees_new_2+"\n");	
			}
		else
			{
			round2_flag=150;
			degrees_new_2=degrees_new_1-15;
			System.out.println("Hamming distance: "+ham_dist_15m+"	degrees:" + degrees_new_2);
			Processing.textArea.append("Hamming distance: "+ham_dist_15m+"	degrees:" + degrees_new_2+"\n");
			}
		System.out.println("		degrees2:" + degrees_new_2);
		if(ham_dist_15m<thresh)
		{
			dispOriginal(isplag);
			return 0;
		}
		gui.lblResult.setText("Image has not been plagiarised.");
		return 0;
	}
	
	public static int filter() throws ClassNotFoundException, SQLException, IOException
	{	
		long diff = 0;
		
		DatabaseHandling.connectDatabase();
		Statement stmt = DatabaseHandling.con.createStatement();
		ResultSet rs = stmt.executeQuery("select id from imgdb order by id desc");
		
		if(rs.next())
		{
		num = rs.getInt("id") ;
		System.out.println(num);
		Processing.textArea.append("Get the total numer of images in DB : "+num+"\n");
		} 
		Store[] store = new Store[num+1];
		
		for(int i=1;i<=num;i++)
		{
			store[i]=new Store(-1,101);
		}
		
		h1_ser = HistoChecks.getHisto(issrc);
		h1=HistoChecks.deser(h1_ser);
		
		for(int files=1; files<=num; files++) {
			ResultSet histors = stmt.executeQuery("select histo from histodb where id="+files);
			if(histors.next())
			{
			h2_ser = histors.getString("histo");
			}
			h2=HistoChecks.deser(h2_ser);
			
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					for (int k = 0; k < 4; k++) {
						diff = HistoChecks.getHistoDiff(h1, h2);
					}
				}
			}
			store[files].setIndex(files);
			store[files].setDiffer(diff);

			System.out.println("index: " + store[files].getIndex()+"	difference: " +store[files].getDiffer());
			Processing.textArea.append("Retrieved Index: " + store[files].getIndex()+" Difference %: " +store[files].getDiffer());
						
		}
		if(arrl5index==0)
		{
			store[0]= new Store(-1,101);//to avoid null pointer error and not store anything in 0th place of store
			Arrays.sort(store);
			for(int i=0;i<=num;i++)
			Processing.textArea.append("Sorting.......\n"+"Retrieved Index: " + store[i].getIndex()+" Difference %: " +store[i].getDiffer());
			for(int i = 0; i<= num; i++)
			{
				if(store[i].getDiffer()==0)
				{
					System.out.println("Image has been plagiarised.");
					Processing.textArea.append("Image has been plagiarised.");
					gui.lblResult.setText("");
					gui.lblResult.setText("Image has been plagiarised.");
					store[i].getIndex();
					
					DatabaseHandling.connectDatabase();
					Statement stmt2 = DatabaseHandling.con.createStatement();
					ResultSet rs2 = stmt2.executeQuery("select path from imgdb where id = " + store[i].getIndex());
					Processing.textArea.append("Retrieving path from imgdb");
					String filepath = null; 
					if(rs2.next())
					{
					filepath= rs2.getString("path") ;
					}
					dispOriginal(filepath);
								
					return 0;
				}
				else if(store[i].getDiffer()<20 && arrl5index<5)//change the getdiffer from 5 to higher value
				{
					arrl5[arrl5index]=store[i].getIndex();
					arrl5index++;
				}
				if(arrl5index==0)
				{
					System.out.println("The image has not been plagiarised.");
					Processing.textArea.append("Image has not been plagiarised.");
					gui.lblResult.setText("<html>The image has not been plagiarised. <br>"
							+ "Image has been added to the database");
					Processing.textArea.append("The image has not been plagiarised. \n"
							+ "Image has been added to the database");
					
					DatabaseHandling.addFile(issrc);
				}
			}
		}
		for(int i=0;i<arrl5index;i++)
			System.out.println(arrl5[i]);//Display purpose can be removed once tested.
		return 0;
		}	
	public static void reinit()
	{
		arrl5index=0;
		num=0;
		Processing.textArea.append("Initialized for new set.");
	}
	
	public static void dispOriginal(String filepath) throws IOException
	{
		gui.lblResult.setText("Image has been plagiarised.");
		BufferedImage img=ImageIO.read(new File(filepath));
		Processing.setImage(img);
    	gui.outputFrame = new JFrame();
		gui.outputFrame.setTitle("Original Image");
		gui.outputFrame.setLocationRelativeTo(gui.lblNewLabel);
		gui.lbloutput = new JLabel();
		gui.lbloutput.setBounds(110, 39, 97, 45);
		
		gui.outputFrame.setSize(300,300);
		gui.outputFrame.getContentPane().add(gui.lbloutput);
		
		gui.lbloutput.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		gui.lbloutput.setBounds((gui.outputFrame.getContentPane().getWidth()/2)+90, (gui.outputFrame.getContentPane().getHeight()/2)+20, 260, 220);
		img = ImagePHash.resize(img, gui.lbloutput.getWidth(),gui.lbloutput.getHeight());
        ImageIcon icon=new ImageIcon(img);
		gui.lbloutput.setIcon(icon);
		gui.outputFrame.setVisible(true);
	}
	
}
	
