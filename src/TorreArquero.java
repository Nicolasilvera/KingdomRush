public class TorreArquero extends Torre {
	String arrImagen[]={"images/torres/arquero_nivel_02.png","images/torres/arquero_nivel_03.png"};
	 int arrPrecios[]={70,110,160};
	 
	 public TorreArquero(int x, int y){
		super("images/torres/arquero_nivel_01.png",x,y);
		tipoTorre="Arquero";
		misil=new Flecha(x,y);
		nivelTorre=1;
		costoCompra=arrPrecios[nivelTorre-1];
		costoMejora=arrPrecios[nivelTorre];
		poder=15;
		radioAlcance=250;
		frecuencia=4;
		persona=new ArqueroPersona(x,y);
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
		
	public void noColisiono(){
		misil=new Flecha(x,y);
	}
	
	public boolean atacarEnemigos(int x, int y){//devuelve true si intersectó, else si está en camino
		boolean intersecto=false;	
			misil.setTarget(x,y);
			misil.update();	
			if (misil.intersects(x,y,8,8)){
				misil=new Flecha(this.getX()+this.getWidth()/2,this.getY());				
				intersecto=true;
			}
			
		((ArqueroPersona)persona).moverse();
		return intersecto;
	}
	
	public void mejorar(){
		super.mejorar();
		poder+=5;
		radioAlcance+=30;
		costoCompra=arrPrecios[nivelTorre-1];
		if(nivelTorre!=3)
			costoMejora=arrPrecios[nivelTorre];
		cambiarImagen(arrImagen[nivelTorre-2]);
		setElipse();
	}
	
}
