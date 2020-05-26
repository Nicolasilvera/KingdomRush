import java.awt.geom.*;
import java.util.*;
public class Ogro extends Enemigo {
	private int ind_img=1;
	private static final int ind_max=12;
		
	public Ogro(java.util.List<Point2D> carril){	
		super("images/individuos/ogro/viniendo0.png",(int)carril.get(0).getX(),(int)carril.get(0).getY(),carril);
		peleando=false;
		recompensa=15;
		velMovimiento=4;
		velAtaque=2;
		this.setVida(150);
		poder=20;
	}
	
	public void moverse(){
		super.moverse();
		this.setPosition(this.getX()-this.getWidth()/2,this.getY()-this.getHeight()/2);
		this.cambiarImagen("images/individuos/ogro/viniendo"+ind_img+".png");
		
		if(ind_img==ind_max)
			ind_img=0;
		else
			ind_img++;
	}
}

