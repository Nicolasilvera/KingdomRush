import java.awt.*;
public class Artilleria extends Torre {
String arrImagen[]={"images/torres/artilleria_nivel_02.png","images/torres/artilleria_nivel_03.png"};
int arrPrecios[]={125,220,320};

	public void atacarEnemigos() {	
	}
	public short getCosto(short level){
		short devolucion=0;
		switch(level){
			case 1: devolucion=125;
			break;
			case 2: devolucion=220;
			break;
			case 3: devolucion=320;
			break;
		}
	return(devolucion);
	}
	
	public Artilleria(int x, int y){
		super("images/torres/artilleria_nivel_01.png",x,y);
		misil=new Bomba(x+this.getWidth()/2-5,y);
		tipoTorre="Artilleria";
		nivelTorre=1;
		poder=20;
		costoCompra=arrPrecios[nivelTorre-1];
		costoMejora=arrPrecios[nivelTorre];
		radioAlcance=150;
		frecuencia=4;
		}

	public boolean atacarEnemigos(int x, int y){
		boolean intersecto=false;	
			misil.setTarget(x,y);
			misil.update();
			
			if (misil.intersects(x,y,8,8)){
				misil=new Bomba(this.getX()+this.getWidth()/2,this.getY());								//municion
				intersecto=true;
			}
		return intersecto;
	}
	public void noColisiono(){
		misil=new Bomba(this.getX()+this.getWidth()/2,this.getY());	
	}
	
	public void mejorar(){
		super.mejorar();
		poder+=10;;
		radioAlcance+=100;
		costoCompra=arrPrecios[nivelTorre-1];
		if(nivelTorre!=3)
			costoMejora=arrPrecios[nivelTorre];
		cambiarImagen(arrImagen[nivelTorre-2]);
		setElipse();
		}
}
