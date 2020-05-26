import java.awt.*;
import java.awt.geom.*;

import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ObjetoGrafico {
	protected BufferedImage img;
	protected Rectangle2D area;
	protected int x,y;
	
    public ObjetoGrafico(String filename,int x, int y) {
    		try {
				img= ImageIO.read(getClass().getResource(filename));

			} catch (IOException e) {
				System.out.println(e);
			}
			this.x=x;
			this.y=y;
			area = new Rectangle2D.Double(x,y,img.getWidth(),img.getHeight());
    }

	public int getWidth(){
		return img.getWidth();
	}
	
	public int getHeight(){
		return img.getHeight();
	}
	public int getX(){   // SACAR
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setPosition(double X,double Y){
		x=(int)X;
		y=(int)Y;
		area.setRect(X,Y,img.getWidth(),img.getHeight());
	}

	
	public Rectangle2D getAreaObj(){
		return area;
	}
	
	public void cambiarImagen(String filename){
			try{
			img = ImageIO.read(getClass().getResource(filename));
			} catch (IOException e) {
				System.out.println(e);
			}
	}
	
	
   public void paint(Graphics2D g2) {
		
		g2.drawImage(img,x,y,null);
  }

}
