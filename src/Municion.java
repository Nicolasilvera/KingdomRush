import java.awt.geom.*;
import java.awt.*;
import javax.imageio.*;
import processing.core.*;
import java.awt.image.*;
import java.io.*;

public abstract class Municion extends ObjetoGrafico{
	// Inspirado en el código del libro
	// http://natureofcode.com/book/chapter-3-oscillation/
	// 3.4 Pointing in the Direction of Movement
	
	private PVector posicion;
  	private PVector velocidad;
  	private PVector aceleracion;
	
	
  	private float maxVelocidad;
  	private float xoff, yoff;
  	private float r = 16;
  	private int enemigoX,enemigoY;

  	public Municion(String filename,int x, int y){
		super(filename,x,y);
		posicion= new PVector(x,y);
    	velocidad = new PVector(0, 0);
		maxVelocidad =7/2;
	    xoff = 1000;
	    yoff = 0;
	}
	
  	public void setTarget(int x,int y){
  		 enemigoX=x;
  		 enemigoY=y;
  	}
  	
  	
  	public void paint(Graphics2D g){
  		double theta = Math.atan2((double)velocidad.y,(double)velocidad.x);
		 AffineTransform affineT=g.getTransform();// almaceno la AffineTransform antes de hacer las operaciones
  		 g.translate((int)posicion.x, (int)posicion.y);
  		 g.rotate(theta);
  		 g.drawImage(img,0,0,null);
  		 g.translate(-(int)posicion.x, -(int)posicion.y);
  		 g.setTransform(affineT);// restauro la AffineTransform
  	}
  	
  	public void update(){
  		    PVector vdestino = new PVector(enemigoX, enemigoY);
		    PVector vDireccion = PVector.sub(vdestino, posicion);
		    vDireccion.normalize();
		    vDireccion.mult(0.5f);
		    aceleracion = vDireccion;

		    velocidad.add(aceleracion);
		    velocidad.limit(maxVelocidad);
		    posicion.add(velocidad);
		    this.setPosition(posicion.x,posicion.y);

  	}

  	public boolean intersects(double x, double y, double w, double h){
  		return area.intersects(x,y,w,h);
  	}
  }
