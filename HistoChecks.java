
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class HistoChecks {
	
	public static String getHisto(String str) throws IOException
	{
		long [][][] ch = new long [4][4][4];
		String obj;
		InputStream is = new FileInputStream(new File(str));
	    BufferedImage image = ImageIO.read(is);
	    
	    image=ImagePHash.resize(image,768,768);
	    Processing.textArea.append("Calculating histogram of given Image"+"\n");
	    for(int x = 0; x < image.getWidth(); x++)
	        for(int y = 0; y < image.getHeight(); y++) {
	            int color = image.getRGB(x, y);
	            int red = (color & 0x00ff0000) >> 16;
	            int green = (color & 0x0000ff00) >> 8;
	            int blue = color & 0x000000ff;
	            ch[red / 64][green / 64][blue / 64]++;
	        }
	    
	    obj = ObjectSerializer.serialize(ch);
	    return obj;

	}
	
	
	public static String getHisto(BufferedImage image) throws IOException
	{
		long [][][] ch = new long [4][4][4];
		String obj;

	    image=ImagePHash.resize(image,768,768);
	    Processing.textArea.append("Calculating histogram of given Image"+"\n");
	    for(int x = 0; x < image.getWidth(); x++)
	        for(int y = 0; y < image.getHeight(); y++) {
	            int color = image.getRGB(x, y);
	            int red = (color & 0x00ff0000) >> 16;
	            int green = (color & 0x0000ff00) >> 8;
	            int blue = color & 0x000000ff;
	            ch[red / 64][green / 64][blue / 64]++;
	        }	    
	    obj = ObjectSerializer.serialize(ch);
	    return obj;
	}

	
	
	public static long[][][] deser(String str) throws IOException
	{
		long[][][] histo = (long[][][]) ObjectSerializer.deserialize(str);
		return histo;
	}
	
	public static long getHistoDiff(long[][][] h1, long[][][] h2)
	{
		long[][][] diff = new long [4][4][4];
		long sum =0;
		for(int i = 0; i < diff.length; i++)
	        for(int j = 0; j < diff[i].length; j++)
	            for(int k = 0; k < diff[i][j].length; k++)
	            {
	            	diff[i][j][k] = Math.abs(h1[i][j][k] - h2[i][j][k]);
	            	sum = sum + diff[i][j][k];
	            }
		long lo = sum*100/(2*768*768);
		Processing.textArea.append("Computing the difference"+"sum"+"\n");
		Processing.textArea.append("Generating percentage... "+lo+"\n");
		return lo;
	}

}