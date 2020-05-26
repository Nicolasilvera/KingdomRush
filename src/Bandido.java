import java.awt.geom.*;
import java.util.*;

public class Bandido extends Enemigo{
	private int ind_img=2;
	private static final int ind_max=11;
	
	public Bandido(java.util.List<Point2D> carril){	
	super("images/individuos/bandidos/viniendo0.png",(int)carril.get(0).getX(),(int)carril.get(0).getY(),carril);
	peleando=false;
	recompensa=10;
	velMovimiento=4;
	velAtaque=2;
	this.setVida(100);
	poder=15;
	posicion=0;
	}
	
	public void moverse(){
	super.moverse();
	this.setPosition(this.getX()-this.getWidth()/2,this.getY()-this.getHeight()/2);
	this.cambiarImagen("images/individuos/bandidos/viniendo"+ind_img+".png");
	
	if(ind_img==ind_max-1)
		ind_img=0;
	else
		ind_img++;
	}


}


