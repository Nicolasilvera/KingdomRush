/*
 * Importante en la carpeta images tiene que existir un archivo llamado flecha_02.png
 * si no esta copiarlo
 */


import java.awt.*;
import java.awt.event.*;

import java.awt.geom.*;

import java.util.*;
import java.text.*;

import com.entropyinteractive.*;
import processing.core.*;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;



public class DemoRutaPoligonoCaminoMunicion extends Game {
	Date dInit = new Date( );
	Date dAhora;
	SimpleDateFormat ft = new SimpleDateFormat ("mm:ss");
	protected float mundoAncho=700f;
	protected float mundoAlto=600f;



    Shape carrilUno;
    java.util.List<Point2D> puntos;

    int radius=16;
    Point2D pos=new Point2D.Float(310 - (radius/2),0 - (radius/2) );
	double dbl_idx;

	long sketchMilliseconds=-1;
	long ms_mouse_click,ms_mouse_released,diff;

	Polygon perimetroCamino = new Polygon();

	GeneralPath poligonoCerradoCamino=new GeneralPath();
	boolean firstRun=true;

	Area areaOne;

	Point  centroPoligono;
	Polygon polygonPerimetroArea;

	Shape circuTorre = new Ellipse2D.Double(100,140,250,100);
	Shape torreRect=new Rectangle2D.Double(460,260,32,64);
	Municion misil;
	boolean disparo=false;
	Area circuTorre_poligono;


	BufferedImage img_fondo = null;
	BufferedImage img_fondo_mask = null;

    public static void main(String[] args) {
        DemoRutaPoligonoCaminoMunicion game = new DemoRutaPoligonoCaminoMunicion();
        game.run(1.0 / 60.0);
        System.exit(0);
    }

    public DemoRutaPoligonoCaminoMunicion() {
           super("DemoRutaPoligonoCaminoMunicion ", 700, 600);
    }

    public void gameStartup() {
    	ms_mouse_click=ms_mouse_released=-1;
    	/**/
    	  //perimetroCamino.addPoint(285,0);
    	  	try {
				img_fondo= ImageIO.read(getClass().getResource("images/nivel1.png"));
				//img_fondo_mask = ImageIO.read(getClass().getResource("images/nivel1-mascara.png"));

				/*
				 System.out.println(img_fondo.getRGB(320,10));
				System.out.println(img_fondo_mask.getRGB(320,10));
				System.out.println(img_fondo_mask.getRGB(20,10));
				*/
			} catch (IOException e) {
				System.out.println(e);
			}

    	 /********************/
		/*Creacion de la ruta */
		Path2D path = new Path2D.Double();
		path.moveTo(310,0);
		path.curveTo(310,50,315,100,   320,140);
		path.curveTo(320,170,310,190,  285,220);
        path.curveTo(260,230, 230,250, 200,250);
	    path.curveTo(170,275,155,320,  150,350);
		path.curveTo(165,380,190,420,  240,430);
		path.curveTo(280,430,330,430,  400,420);
		path.curveTo(440,440,470,430,  500,450);
		path.curveTo(520,420,550,400,  575,370);
		path.curveTo(620,360,660,360,  700,360);
		carrilUno = path;
		/*
		 * Extraer puntos de la ruta
		 **/
	 		puntos = new ArrayList<>(50);
            PathIterator pi = carrilUno.getPathIterator(null, 0.01);
            while (!pi.isDone()) {
                double[] coords = new double[6];
                switch (pi.currentSegment(coords)) {
                    case PathIterator.SEG_MOVETO:
                    case PathIterator.SEG_LINETO:
                        puntos.add(new Point2D.Double(coords[0], coords[1]));
                        break;
                }
                pi.next();
            }


		misil=new Municion(460,260);

    }

    public void gameUpdate(double delta) {
		Keyboard keyboard = getKeyboard();
		Mouse mouse=getMouse();
		
		dbl_idx += 0.0005;
		int index = Math.min(Math.max(0, (int) (puntos.size() * dbl_idx)), puntos.size() - 1);

		pos = puntos.get(index); // saco una posicion
/*
		int clr=  img_fondo_mask.getRGB((int)pos.getX(),(int)pos.getY());
		int alpha = (clr >> 24) & 0x000000FF;
		int r = (clr >> 16) & 0x000000FF;
		int g = (clr >>8 ) & 0x000000FF;
		int b = clr & 0x000000FF;
		System.out.println("En la posicion ("+(int)pos.getX()+","+(int)pos.getY()+") el color de la mascara es "+r+","+g+","+b);
*/
		dbl_idx = (index >=puntos.size() - 1) ? 0.0 : dbl_idx;

		/* Maña para detectar click */
		if (mouse.isLeftButtonPressed()){
			System.out.println(mouse.getX()+"      "+mouse.getY());
			ms_mouse_click = (ms_mouse_click==-1) ? System.currentTimeMillis() : ms_mouse_click ;
			ms_mouse_released =-1;
		}else{
			ms_mouse_released = System.currentTimeMillis();
			if ((ms_mouse_click!=-1)){
				diff =  ms_mouse_released - ms_mouse_click ;
				if ((diff>=50) && (diff<=250) ) {
					if (firstRun){
						firstRun=false;
						poligonoCerradoCamino.moveTo(mouse.getX(),mouse.getY());
					}else{
						poligonoCerradoCamino.lineTo(mouse.getX(),mouse.getY());
					}
					perimetroCamino.addPoint(mouse.getX(),mouse.getY());
				}

			}

			ms_mouse_click=-1;


		}

		if (keyboard.isKeyPressed(KeyEvent.VK_S)){
			disparo=true;
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_SPACE)){
			 perimetroCamino.reset();
			 firstRun=true;
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_C)){
			 //centroPoligono = centroid();
			 poligonoCerradoCamino.closePath();
			 firstRun=true;
        }

         if (keyboard.isKeyPressed(KeyEvent.VK_A)){

			 areaOne=new Area(circuTorre);

		  	Area areaDos = new Area(poligonoCerradoCamino);
		  	areaOne.intersect(areaDos);

			/* Area intersectada */
		  	PathIterator iterator = areaOne.getPathIterator(null);
		  	float[] floats = new float[6];

		    this.polygonPerimetroArea = new Polygon();
		    while (!iterator.isDone()) {
		        int enemigoYpe = iterator.currentSegment(floats);
		        int x = (int) floats[0];
		        int y = (int) floats[1];

		        if(enemigoYpe != PathIterator.SEG_CLOSE) {
		            polygonPerimetroArea.addPoint(x, y);
		           //
		        }
		        iterator.next();
		    }

			/* Calculo del Centro del Area intersectada */

		   centroPoligono= polygonCenterOfMass(polygonPerimetroArea);



        }

        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (KeyEvent event: keyEvents) {

            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                stop();
            }
        }

		if (disparo){

			misil.setTarget((int)pos.getX(),(int)pos.getY());
			misil.update();

			if (misil.intersects((int)pos.getX(),(int)pos.getY() ,8,8)){
					misil=new Municion(460,260);
					disparo=false;
			}
		}

    }



    public void drawText(Graphics2D g,String texto,int x,int y){
    	 g.setColor(Color.black);
    	 g.drawString(texto,x+2,y+2);
    	 g.setColor(Color.white);
    	 g.drawString(texto,x,y);
    }
    public void gameDraw(Graphics2D g) {

    	dAhora= new Date();
    	long dateDiff = dAhora.getTime() - dInit.getTime();
    	long diffSeconds = dateDiff / 1000 % 60;
		long diffMinutes = dateDiff / (60 * 1000) % 60;

		g.drawImage(img_fondo,0,0,null);// fondo


		g.setColor(Color.red);
		g.draw(carrilUno);

		g.setColor(Color.green);
		g.draw(circuTorre);

		g.setColor(Color.gray);
		g.fill(torreRect);
		g.setColor(Color.white);
		g.draw(torreRect);

		/* pelotita anda sobre la ruta */
		g.setColor(Color.black);
		g.fillOval((int)(pos.getX()- (radius/2)),  (int)(pos.getY()- (radius/2)), radius,radius);
		//////////////////////////////////

		/* Dibuja el poligono que se arma con el click */
		g.setColor(Color.cyan);
		g.draw(poligonoCerradoCamino);
		//////////////////////////////////



		g.setColor(Color.green);
		if (areaOne!=null){
			g.fill(areaOne);
			//Dibuja la interseccion de la elipse y el poligono construido
		}

		/* Dibuja el centro de masa que corresponde a la figura resultante de la interseccion de la elipse y el poligono construido */
		if (centroPoligono!=null) {
				g.setColor(Color.red);
				g.draw(polygonPerimetroArea);
				g.setColor(Color.black);
				//Pinta el centro del poligono
				g.fillOval((int)(centroPoligono.getX()-8),  (int)(centroPoligono.getY()- 8), 8,8);
			}


		misil.draw(g);


		/* Textos */
		drawText(g,"Tiempo de Juego: "+diffMinutes+":"+diffSeconds,10,20);
		drawText(g,"Tecla ESC = Fin del Juego ",490,20);
		drawText(g,"Tecla C = Cierra Poligono. Tecla A = Interseccion. Tecla S = Disparo",10,40);

    }

    public void gameShutdown() {

    }


	public static double PolygonArea(Point[] polygon, int N) {
		/* http://www.shodor.org/~jmorrell/interactivate/org/shodor/util11/PolygonUtils.java*/
		int i, j;
		double area = 0;

		for (i = 0; i < N; i++) {
			j = (i + 1) % N;
			area += polygon[i].x * polygon[j].y;
			area -= polygon[i].y * polygon[j].x;
		}

		area /= 2.0;
		return (Math.abs(area));
	}

	public static Point polygonCenterOfMass(Polygon pg) {
		/*
		 * http://www.shodor.org/~jmorrell/interactivate/org/shodor/util11/PolygonUtils.java
		 **/
		if (pg == null)
			return null;

		int N = pg.npoints;
		Point[] polygon = new Point[N];

		for (int q = 0; q < N; q++)
			polygon[q] = new Point(pg.xpoints[q], pg.ypoints[q]);

		double cx = 0, cy = 0;
		double A = PolygonArea(polygon, N);
		int i, j;

		double factor = 0;
		for (i = 0; i < N; i++) {
			j = (i + 1) % N;
			factor = (polygon[i].x * polygon[j].y - polygon[j].x * polygon[i].y);
			cx += (polygon[i].x + polygon[j].x) * factor;
			cy += (polygon[i].y + polygon[j].y) * factor;
		}
		factor = 1.0 / (6.0 * A);
		cx *= factor;
		cy *= factor;
		return new Point((int) Math.abs(Math.round(cx)), (int) Math.abs(Math.round(cy)));
	}
}

class Municion{
	// Inspirado en el código del libro
	// http://natureofcode.com/book/chapter-3-oscillation/
	// 3.4 Pointing in the Direction of Movement

	PVector posicion;
  	PVector velocidad;
  	PVector aceleracion;

  	float maxVelocidad;
  	float xoff, yoff;
  	float r = 16;
  	int enemigoX,enemigoY;


	BufferedImage img = null;
	Rectangle2D municionModel;

  	public Municion(int x,int y){
  		posicion = new PVector(x,y);
    	velocidad = new PVector(0, 0);
	    maxVelocidad = 4;
	    xoff = 1000;
	    yoff = 0;


	    municionModel=new Rectangle2D.Double(posicion.x, posicion.y, 13,4);

	    try{
	    	img= ImageIO.read(getClass().getResource("images/flecha_02.png"));
	    }catch(Exception e){

	    }
  	}
  	public void setTarget(int x,int y){
  		 enemigoX=x;
  		 enemigoY=y;
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


		    municionModel=new Rectangle2D.Double(posicion.x, posicion.y,13,4);

  	}

  	public void draw(Graphics2D g){

  		double theta = Math.atan2((double)velocidad.y,(double)velocidad.x);
		AffineTransform affineT=g.getTransform();// almaceno la AffineTransform antes de hacer las operaciones

  		 g.translate((int)posicion.x, (int)posicion.y);
  		 g.rotate(theta);

			//if (img!=null){
					g.drawImage(img,0,0,null);
			//}else{
			//	g.setColor(Color.blue);
			//	g.fillRect(0,0,(int)municionModel.getWidth() ,(int)municionModel.getHeight());
			//}

  		 g.translate(-(int)posicion.x, -(int)posicion.y);
		g.setTransform(affineT);// restauro la AffineTransform
  	}

  	public boolean intersects(double x, double y, double w, double h){
  		return municionModel.intersects(x,y,w,h);
  	}
}
