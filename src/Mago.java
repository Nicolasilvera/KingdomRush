import java.awt.*;
public class Mago extends Torre {
String arrImagen[]={"images/torres/mago_nivel_02.png","images/torres/mago_nivel_03.png"};
int arrPrecios[]={100,160,240};

	public void atacarEnemigos() {	
	}
	public short getCosto(short level){
		short devolucion=0;
		switch(level){
			case 1: devolucion=100;
			break;
			case 2: devolucion=160;
			break;
			case 3: devolucion=240;
			break;
		}
	return(devolucion);
	}
		
		
	public Mago(int x,int y){
		super("images/torres/mago_nivel_01.png",x,y);
		tipoTorre="Mago";
		nivelTorre=1;
		costoCompra=arrPrecios[nivelTorre-1];
		costoMejora=arrPrecios[nivelTorre];
		poder=20;
		radioAlcance=150;
		frecuencia=4;
		misil=new Hechizo(this.getX()+this.getWidth()/2,this.getY()-4);
		persona=new MagoPersona(x,y);
	}
	
	public void noColisiono(){
	misil=new Hechizo(this.getX()+this.getWidth()/2,this.getY()-4);
	}
	
	public boolean atacarEnemigos(int x, int y){
		boolean intersecto=false;	
			misil.setTarget(x,y);
			misil.update();
			
			if (misil.intersects(x,y,8,8)){
				misil=new Hechizo(this.getX()+this.getWidth()/2,this.getY());								
				intersecto=true;
			}
		((MagoPersona)persona).moverse();
		return intersecto;							
	
	}
			
	public void mejorar(){
		super.mejorar();
		poder+=10;
		radioAlcance+=100;
		costoCompra=arrPrecios[nivelTorre-1];
		if(nivelTorre!=3)
			costoMejora=arrPrecios[nivelTorre];
		cambiarImagen(arrImagen[nivelTorre-2]);
		setElipse();
		}
}
