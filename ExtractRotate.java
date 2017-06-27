
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExtractRotate {
		public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) throws IOException {
		Processing.textArea.append("Cropping to increase output efficiency...."+"\n");
	    int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = output.createGraphics();

	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);

	    g2.dispose();
	    File fo1 = new File("C:\\Users\\images\\");
		ImageIO.write(output, "jpg", fo1);
	    return output;
	}
	
	 private static BufferedImage cropImage(BufferedImage src,int width, int height) {
	      BufferedImage dest = src.getSubimage(0, 0, width, height);
	      return dest; 
	   }
	 
	public static BufferedImage roundRotated(String inputpath, float angle) throws IOException
	{
		File theimg = new File(inputpath);
		BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		bi = ImageIO.read(theimg);
		bi = ImagePHash.resize(bi, 32, 32);
		
		BufferedImage img2 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB );
		BufferedImage img3 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB );
		
		img2 = makeRoundedCorner(bi, 32);
		File fo = new File("C:\\Users\\images\\inter.jpg");
		ImageIO.write(img2, "jpg", fo);
		Processing.textArea.append("Rotatig to "+angle+"\n");
		AffineTransform af = new AffineTransform();
		af.rotate(Math.toRadians(angle),img2.getWidth()/2,img2.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_BICUBIC);
		img2= op.filter(img2,null);
		
	
		img3= cropImage(img2,32,32);
		return img3;
	}
	
	public static BufferedImage roundRotated(BufferedImage bi, float angle) throws IOException
	{
		bi = ImagePHash.resize(bi, 32, 32);
		
		BufferedImage img2 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB );
		BufferedImage img3 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB );
		
		img2 = makeRoundedCorner(bi, 32);
		Processing.textArea.append("Rotatig to "+angle+"\n");
		AffineTransform af = new AffineTransform();
		af.rotate(Math.toRadians(angle),img2.getWidth()/2,img2.getHeight()/2);
		
		AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_BICUBIC);
		img2= op.filter(img2,null);

		img3= cropImage(img2,32,32);
		return img3;
	}

}
