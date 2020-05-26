package com;
import javax.swing.*;
import java.awt.*;
public class ImagenFondo extends JPanel {
		private Image fondo;
   
		public ImagenFondo(Image img){
			fondo = img;
		}
	
	
		public void paint(Graphics g){
			g.drawImage(fondo,0,0,getWidth(),getHeight(),this);
			setOpaque(false);
			super.paint(g);
		}
	
		public void cambiarImagen(Image i){
		fondo = i;
		repaint();
	}
}
