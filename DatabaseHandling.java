
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


public class DatabaseHandling {
		static int value;
	static Connection con=null;

	static String host,uPass,uName;
	
	public static void connectDatabase() throws ClassNotFoundException, SQLException
	{
            Statement stmt = null;
		ResultSet rs = null;

		Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "";
			String connectionUser = "";
			String connectionPassword = "";
                        con = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
                        Processing.textArea.append("Connecting to database......"+"\n");
	              stmt = con.createStatement();
        }
	
	//Function to get the last index from the image database table
	public static int idtable() throws ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		 String connectionUrl = "";
	try {

	connectDatabase();
	java.sql.Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery("select id from imgdb order by id desc");
	
	if( rs.next() )
	{
	value = rs.getInt("id") ; //index is the column that you want
	System.out.println(value);
	} 

	}
	catch ( SQLException err ) {
	System.out.println( err.getMessage( ) );
	}
	return value;
	}
	
	public static void addFile(String path) throws IOException, ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		DatabaseHandling.connectDatabase();
        File imagefile = new File(path);
        BufferedImage image = ImageIO.read(imagefile);
        int index = path.lastIndexOf('\\');
        String name = path.substring(index+1);
        System.out.println(name);
        int ii=name.lastIndexOf('.');
        String prop=name.substring(ii+1);
        System.out.println(prop);
        String destinationpath="E:\\images\\"+name;
        
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
        			
        			int ID=itable;
        			System.out.println("id is here"+ID);
        			
        			ID++;
        			DatabaseHandling.connectDatabase();
        			PreparedStatement statement = DatabaseHandling.con.prepareStatement("INSERT INTO IMGDB (id, path) VALUES ( ?, ?)");
        			Processing.textArea.append("INSERT INTO IMGDB (id,path) VALUES (?,?)"+"\n");
        			statement.setInt(1, ID);
        			statement.setString(2, destinationpath);
        			statement.execute();
        			System.out.println("added to imgdb");
        			Processing.textArea.append("Image added to database"+"\n");
        			System.out.println(destinationpath);
        			String obj = HistoChecks.getHisto(destinationpath);
        			System.out.println(obj);
        			String histovalue= HistoChecks.getHisto(destinationpath);
        			
        			PreparedStatement statement1 = DatabaseHandling.con.prepareStatement("INSERT INTO HISTODB (id, histo) VALUES ( ?, ?)");
        			Processing.textArea.append("INSERT INTO HISTODB (id,histo) VALUES ( ? , ? )"+"\n");
        			statement1.setInt(1, ID);
        			statement1.setString(2, histovalue);
        			statement1.execute();
        			System.out.println("added to histodb");
        			Processing.textArea.append("Histogram added to corresponding database"+"\n");
		}
	
	public static void createtables(){
	   Statement stmt = null;
	   try{
		   connectDatabase();
	      System.out.println("Creating table in given database...");
	      stmt = con.createStatement();
	      
	      String sql = "CREATE TABLE IMGDB " +
	                   "(id INTEGER not NULL, " +
	                   " path VARCHAR(4000) NOT NULL UNIQUE, " + 
	                   " PRIMARY KEY ( id ))";
	      String sql1 = "CREATE TABLE HISTODB " +
                  "(id INTEGER not NULL, " +
                  " histo VARCHAR(4000) UNIQUE, " + 
                  " FOREIGN KEY ( id ) REFERENCES IMGDB(id))";
	      
	      stmt.executeUpdate(sql);
	      stmt.executeUpdate(sql1);
	      System.out.println("Created table in given database...");
	   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }finally{
	      try{
	         if(stmt!=null)
	            con.close();
	      }catch(SQLException se){
	      }
	      try{
	         if(con!=null)
	            con.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	}
	
}
