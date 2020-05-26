import java.awt.geom.*;
import java.util.*;
public abstract class Enemigo extends Individuo{
	
	protected short recompensa;
	protected Point2D punto;
	protected int posicion;
	protected List<Point2D> carril;
	
	
	public Enemigo(String filename,int x,int y,List<Point2D> carril){
		super(filename,x,y);
		this.carril = carril;
	}
	
	public void moverse(){
		super.moverse((int)carril.get(posicion+1).getX(),(int)carril.get(posicion+1).getY());
		posicion++;
	}
	
	public boolean puedeMov(){
		boolean aux=false;
		if(carril.size()-1>posicion)
			aux=true;
		return aux;
	}
	
	public int getRecompensa(){
	return recompensa;
	}
	
	public void atacarSoldados(){}
	
	
}

