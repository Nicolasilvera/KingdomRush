import java.awt.geom.*;
import java.awt.*;

public class TorreBarraca extends Torre {
//ATRIBUTOS CARACTERISTICOS DE BARRACA
	Soldado[] soldado=new Soldado[3];
	private Area areaSoldados;
	Point  centroPoligono;
	
//ATRIBUTOS QUE CONTRIBUYEN AL FUNCIONAMIENTO
	String arrImagen[]={"images/torres/barraca_nivel_02.png","images/torres/barraca_nivel_03.png"};
	int arrPrecios[]={70,110,160};
	Polygon polygonPerimetroArea;

//CREACION DE LA BARRACA	
	public TorreBarraca(int x, int y,Area a){
		super("images/torres/barraca_nivel_01.png",x,y);
		tipoTorre="Barraca";
		nivelTorre=1;
		costoCompra=arrPrecios[nivelTorre-1];
		costoMejora=arrPrecios[nivelTorre];
		radioAlcance=150;
		frecuencia=3;
		setElipse();
		setAreaSoldados(a);
		setCentroPoligono();
		soldado[0]=new Soldado((int)polygonCenterOfMass(polygonPerimetroArea).getX(),(int)polygonCenterOfMass(polygonPerimetroArea).getY());
		soldado[0].setPosition(soldado[0].getX()-soldado[0].getWidth()/2,soldado[0].getY()-soldado[0].getHeight()/2);
		soldado[1]=new Soldado(soldado[0].getX()+15,soldado[0].getY()+5);
		soldado[2]=new Soldado(soldado[0].getX()-15,soldado[0].getY()-5);
		System.out.println("Se ha creado una barraca");
	}
	
//Mejora de la barraca	
	public void mejorar(Area a){
		super.mejorar();
		radioAlcance+=100;
		costoCompra=arrPrecios[nivelTorre-1];
		if(nivelTorre!=3)
			costoMejora=arrPrecios[nivelTorre];
		cambiarImagen(arrImagen[nivelTorre-2]);
		setElipse();
		setAreaSoldados(a);
		System.out.println("La barraca ha sido mejorada");
		}
	
//Otros metodos	
	public void generarSoldado() {
	}
	
	public short getCosto(short level){
		short devolucion=0;
		switch(level){
			case 1: devolucion=70;
			break;
			case 2: devolucion=110;
			break;
			case 3: devolucion=160;
			break;
		}
	return(devolucion);
	}
	
	public void setAreaSoldados(Area a){
		areaSoldados=new Area(elipse);
		areaSoldados.intersect(a);
	}
	
	public void paint(Graphics2D g){
		super.paint(g);
		for(int i=0;i<3;i++)
				soldado[i].paint(g);
	}

//Cambia el centro del poligono		
	public void setCentroPoligono(){
	/* Area intersectada */
		PathIterator iterator = areaSoldados.getPathIterator(null);
		float[] floats = new float[6];

		this.polygonPerimetroArea = new Polygon();
		while (!iterator.isDone()) {
			int enemigoYpe = iterator.currentSegment(floats);
			int x1= (int) floats[0];
			int y1 = (int) floats[1];

			if(enemigoYpe != PathIterator.SEG_CLOSE) {
				polygonPerimetroArea.addPoint(x1, y1);
			   //
			}
			iterator.next();
		}

		/* Calculo del Centro del Area intersectada */

	   centroPoligono= polygonCenterOfMass(polygonPerimetroArea);
	 }
	 
//Métodos que sirven para generar el area de intersección y el cálculo de su centro respectivamente.
	public static double PolygonArea(Point[] polygon, int N) {
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
	
//METODOS DESTRUIDOS
	public void moverPersonita(){}
	public void noColisiono(){}
	public boolean atacarEnemigos(int x,int y){return false;};
}
