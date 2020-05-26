import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.io.*;

public class PuntoEstrategico extends ObjetoGrafico{
	
	boolean isOpcionesEnabled = false;
	boolean isOpcionesTorreEnabled=false;
	private boolean isTorre=false;
	protected Torre torre;
	BufferedImage[] opcionesTorres = new BufferedImage [4];
	Rectangle2D[] areaOpciones= new Rectangle2D.Double[4];
	Rectangle2D[] areaMejorar= new Rectangle2D.Double[2];
	private Partida partida;
	private final static int COSTO_BARRACA=70;
	private final static int COSTO_MAGO=100;				//VOSTOS INICIALES DE TORRES NIVEL 1
	private final static int COSTO_ARQUERO=70;
	private final static int COSTO_ARTILLERIA=125;
	private int opc = -1;							//SWITCH PARA INSTANCIAR DISTINTOS TIPOS DE TORRE
	
	public PuntoEstrategico(int x, int y, Partida game){
		super("images/animaciones/PuntoEstrategicoSF.png",x,y);
		partida= game;
		try{
		opcionesTorres[0] = ImageIO.read(getClass().getResource("images/torres/OpcionesTorresSF.png"));
		opcionesTorres[1] = ImageIO.read(getClass().getResource("images/torres/OpcionesTorreVenderSF.png"));
		opcionesTorres[2] = ImageIO.read(getClass().getResource("images/torres/OpcionesMejorar_lvl3.png"));
		opcionesTorres[3] = ImageIO.read(getClass().getResource("images/torres/img_costoMejora.png"));
		} catch (IOException e) {
				System.out.println(e);
			}
			areaOpciones[0]=new Rectangle2D.Double(x-28,y-32,30,30);
			areaOpciones[1]=new Rectangle2D.Double(x+40,y-31,30,30);
			areaOpciones[2]=new Rectangle2D.Double(x-28,y+36,30,30);
			areaOpciones[3]=new Rectangle2D.Double(x+39,y+37,30,30);
			areaMejorar[0]=new Rectangle2D.Double(x+5,y-53,40,38);
			areaMejorar[1]=new Rectangle2D.Double(x+13,y+58,28,22);
	}
	
	public boolean hayTorre(){
	return isTorre;
	}
	
	public Integer intToInteger(int variable){
		Integer clase= new Integer(variable);
		return clase;
	}
	

	public void checkClick(int mX, int mY){
		
		//~ if(isTorre)
		//~ System.out.println(torre.getX()+" "+torre.getY());
		
		if (area.contains(mX, mY)&& !isTorre)
				isOpcionesEnabled=!isOpcionesEnabled;
				
		if(isTorre){																	
			if(torre.area.contains(mX, mY)){
				isOpcionesTorreEnabled=!isOpcionesTorreEnabled;
			}else{
				if(isOpcionesTorreEnabled && areaMejorar[0].contains(mX,mY)&& torre.getNivel()<3 && partida.getMonedas()>=torre.getCostoMejora()){
					partida.setMonedas(-torre.getCostoMejora());												//MEJORAR TORRE
					if(torre.getTipo()!="Barraca"){
						torre.mejorar();
						if(torre.getTipo()!="Artilleria")
							torre.moverPersonita();
					}else
						((TorreBarraca)torre).mejorar(new Area(partida.camino.getPoligono()));
					torre.setPosition((this.getX()+this.getWidth()/2)-torre.getWidth()/2,(this.getY()+this.getHeight()/2)-torre.getHeight()/2);				
					}else if(isOpcionesTorreEnabled && areaMejorar[1].contains(mX,mY)){
						isTorre=false;
						System.out.println(torre.getCostoVenta());
						partida.setMonedas(torre.getCostoVenta());												//VENDER TORRE
						torre=null;
						}
				isOpcionesTorreEnabled=false;
			}
		}		
			
		if(!isTorre && isOpcionesEnabled){							//COMPRAR TORRE
			for (int i=0;i<4;i++)
				if (areaOpciones[i].contains(mX,mY))
					opc = i;
			switch(opc){
				case 0: 
				if(partida.getMonedas()>=COSTO_ARQUERO){
					torre = new TorreArquero(x,y);
					isTorre=true;
					torre.setPosition((this.getX()+this.getWidth()/2)-torre.getWidth()/2,(this.getY()+this.getHeight()/2)-torre.getHeight()/2);	
					torre.moverPersonita();
					partida.setMonedas(-COSTO_ARQUERO);
					torre.setElipse();
					}
					break;
				case 1:
					if(partida.getMonedas()>=COSTO_BARRACA){
						torre = new TorreBarraca(x,y,new Area(partida.camino.getPoligono()));
						isTorre=true;
						torre.setPosition((this.getX()+this.getWidth()/2)-torre.getWidth()/2,(this.getY()+this.getHeight()/2)-torre.getHeight()/2);	
						partida.setMonedas(-COSTO_BARRACA);
					}
					break;
				case 2: 	if(partida.getMonedas()>=COSTO_MAGO){
								torre = new TorreMago(x,y);
								isTorre=true;
								torre.setPosition((this.getX()+this.getWidth()/2)-torre.getWidth()/2,(this.getY()+this.getHeight()/2)-torre.getHeight()/2);	
								torre.moverPersonita();
								partida.setMonedas(-COSTO_MAGO);
								torre.setElipse();
							}
					break;
				case 3: 	if(partida.getMonedas()>=COSTO_ARTILLERIA){
								torre = new TorreArtilleria(x,y);
								isTorre=true;
								torre.setPosition((this.getX()+this.getWidth()/2)-torre.getWidth()/2,(this.getY()+this.getHeight()/2)-torre.getHeight()/2);	
								partida.setMonedas(-COSTO_ARTILLERIA);
								torre.setElipse();
							}
					break;
					default:
					break;
			}
		}
		

				
		
		if (!area.contains(mX, mY))
			isOpcionesEnabled = false;
		opc=-1;
	
	 }
		

	public void paint(Graphics2D g2){
	if(!isTorre)
		super.paint(g2);
		
	if(isOpcionesEnabled&&!isTorre)
		g2.drawImage(opcionesTorres[0],x-40,y-45,null);
		
	if(isOpcionesTorreEnabled&&isTorre){
		if(torre.getNivel()!=3){
			g2.drawImage(opcionesTorres[1],torre.getX()+(torre.getWidth()/2)-62,torre.getY()+(torre.getHeight()/2)-71,null);
			g2.drawImage(opcionesTorres[3],torre.getX()+(torre.getWidth()/2)-17,torre.getY()+(torre.getHeight()/2)-41,null);//Contenedor del precio
			g2.setColor(Color.yellow);
			g2.drawString(this.intToInteger(torre.getCostoMejora()).toString(),x+16,y-15);
		}
		else
			g2.drawImage(opcionesTorres[2],torre.getX()+(torre.getWidth()/2)-62,torre.getY()+(torre.getHeight()/2)-71,null);
	}

	
	if(isTorre)
		torre.paint(g2);
	}
	
}
