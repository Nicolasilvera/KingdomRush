import java.awt.geom.*;
import java.awt.*;
//import javax.imageio.*;
//import processing.core.*;
//import java.awt.image.*;


public abstract class Torre extends ObjetoGrafico {
	//ATRIBUTOS CARACTERISTICOS DE CADA TORRE
	protected  int radioAlcance;
	protected  int poder;
	protected  int frecuencia;
	protected  int nivelTorre;
	protected  int costoCompra;
	protected  int costoMejora;
	protected Ellipse2D elipse= new Ellipse2D.Double(x,y,50,50);
	protected Municion misil;
	protected Individuo persona; //SOLO LA POSEEN MAGO Y ARQUERO
	protected int indexTarget=-1;
	//ATRIBUTOS QUE AYUDAN CON EL CONTROL
	protected boolean atacando;
	protected boolean municionCargada=true;
	protected String tipoTorre;
	

	
//CREACION DE LA TORRE
	public Torre(String filename,int x,int y){
		super(filename,x,y);
	}
//REALIZA LA MEJORA(MODIFICADA POR SUS SUBCLASES)
	public void mejorar() {
		nivelTorre++;
		frecuencia--;
	}

//RELACIONADAS AL ATAQUE DE LA TORRE
	public abstract boolean atacarEnemigos(int x, int y);
	
	public abstract void noColisiono();				//REINICIA LA MUNICION
	
	public boolean checkEnemigo(int x,int y){		//CHEQUEA SI UN PUNTO(PERTENECIENTE A UN INDIVIDUO) FORMA PARTE DEL ALCANCE DE TORRE
		return(elipse.contains(x,y));
	}
	
	public void setIndexTarget(int idx){			//GUARDA UN INDICE DEL ENENMIGO
		indexTarget=idx;
		if(idx==-1)
			atacando=false;
		else
			atacando=true;
	}
	
	public void setMunicionCargada(boolean param){
		municionCargada=param;
	}

//METODOS GET DE LOS ATRIBUTOS DE LA CLASE
	public int getFrecuencia(){
		return frecuencia;
	}
	
	public int  getIndexTarget(){
		return indexTarget;
	}
	
	public int getPoder(){
		return poder;
	}
		
	public boolean getMunicionCargada(){
		return municionCargada;
	}
	
	public boolean estaAtacando(){
		return atacando;
	}
	
	public int getCostoMejora(){
		return costoMejora;
	}
	
	public int getNivel(){
		return(nivelTorre);	
	}
	
	public int getCostoVenta(){
		return ((int)((costoCompra*80)/100));
	}
	
	public Ellipse2D getElipse(){
		return elipse;
	}
	
	public String getTipo(){
		return tipoTorre;
	}
	

	
	//LOS SIGUIENTES MUEVEN LOS ELEMENTOS SI SON CREADOS DESPLAZADOS DEL LUGAR DESEADO
	
	public void setElipse(){
		elipse.setFrame((x+(area.getWidth()/2))-(radioAlcance/2),(y+(area.getHeight()/2))-(radioAlcance/2),radioAlcance,radioAlcance);	
	}
	
	public void moverPersonita(){		//Lo dejamos aca por si hay nuevos estilos de torre, aunque artilleria y barraca no lo usen
		persona.setPosition((this.getX()+(this.getWidth()/2))-(persona.getWidth()/2),this.getY()-persona.getHeight()/2);
	}
	
	

	
	public void paint(Graphics2D g){
		super.paint(g);
		
			if(tipoTorre!="Barraca"){
				misil.paint(g);
			if(tipoTorre!="Artilleria")
				persona.paint(g);
			}
		}

	
}




