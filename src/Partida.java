import java.awt.*;
import java.awt.event.*;
import java.util.*;
//~ import java.text.*;
import com.entropyinteractive.*;//BUCLE DE JUEGO
import ArchivosdeNivel.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*; //Rectangulo


public class Partida extends Game  {
	//Atributos determinantes de la clase
	protected Camino camino;
	private Enemigo[] ene= new Enemigo[30];
	private PuntoEstrategico[] pe = new PuntoEstrategico[12];
	private int frecuenciaHorda;
	private int cantEnemigo;
	private int cantPE;
	private int nivelPartida;
	Point2D[] pos = new Point2D.Double[15];	//Ubicaran a los PE.
	
	//Atributos de logica y control
	private KingdomRush kr;
	private CargaRecursos cr= new CargaRecursos();
	private Date dInit = new Date( );
	private Date dAhora;
	private Random rndEnemigo= new Random();
	private Thread t;
	private int enemigosLanzados;
	private int enemigosActuales;
	private	boolean level2disp;
	private boolean generoEnemigos;
	private boolean partidaPerdida;
	private boolean partidaGanada;
	private boolean atacoRecien;
	private long indMovimiento;
	//Potenciales atributos de jugador
	private Integer monedas;
	private Integer vidas;
	
	//Imagenes almacenadas
	private BufferedImage img_fondo = null;
	private BufferedImage[] img_finPartida=new BufferedImage[2];
	private BufferedImage atributos= null; 
	public int getMonedas(){
		return monedas;
	}
	
	public void setMonedas(int num){
	monedas=monedas+num;
	}

	
	public Partida(KingdomRush kr,int nivel, boolean lvl2) {					
		 super("Patida ",700,600);
		 int[] atributos= new int[5];
		 camino=new Camino(nivel);
		 nivelPartida=nivel;
		 this.kr=kr;
		 level2disp=lvl2;
		 atributos=cr.cargarAtributos(nivel);
		 cantPE=atributos[0];
		 cantEnemigo=atributos[1];
		 frecuenciaHorda=atributos[2];
		 vidas=atributos[3];
		 monedas=atributos[4];
		 pos=cr.cargarPuntosEstrategicos(nivel,cantPE);
		
		 for(int i=0;i<cantPE;i++)
			pe[i] = new PuntoEstrategico((int)pos[i].getX(),(int)pos[i].getY(),this);
		}

	public void gameStartup() {
			try {
				img_fondo= ImageIO.read(getClass().getResource("images/nivel"+nivelPartida+".png"));
				img_finPartida[0]= ImageIO.read(getClass().getResource("images/victoria.png"));
				img_finPartida[1]= ImageIO.read(getClass().getResource("images/derrota.png"));
				atributos= ImageIO.read(getClass().getResource("images/atributos.png"));
			} catch (IOException e) {
				System.out.println(e);
			}
		System.out.println("Cantidad de puntos de la ruta 1: " + camino.getCarril(1).size());
		System.out.println("Cantidad de puntos de la ruta 2: " + camino.getCarril(2).size());
		System.out.println("Cantidad de puntos de la ruta 3: " + camino.getCarril(3).size());
	}



	public void gameUpdate(double delta) {
		
	//Proceso cada uno de los eventos de teclado
	if(!partidaGanada&&!partidaPerdida){
			//~ //Actualizo
			indMovimiento++;
			dAhora=new Date();
			if((dInit.getSeconds()-dAhora.getSeconds())%frecuenciaHorda==0&&(enemigosLanzados<cantEnemigo)&&!generoEnemigos){
				for(int i=0;i<3;i++){
					switch(rndEnemigo.nextInt(3)){
					case 0:	ene[enemigosLanzados+i]=new Bandido(camino.getCarril(((enemigosLanzados+i)%3)+1));
						break;
					case 1: ene[enemigosLanzados+i]=new Goblin(camino.getCarril(((enemigosLanzados+i)%3)+1));
						break;
					case 2:	ene[enemigosLanzados+i]=new Ogro(camino.getCarril(((enemigosLanzados+i)%3)+1));
						break;
					}
				}
				enemigosLanzados+=3;
				enemigosActuales+=3;
				generoEnemigos=true;
			}else if((dInit.getSeconds()-dAhora.getSeconds())%2!=0)
					generoEnemigos=false;
			

		
		for(int j=0;j<enemigosLanzados;j++)														//chequea a enemigos cuando no esta atacando
			for(int i=0;i<cantPE;i++)										//y guarda al primero que detecte para comenzar el ataque
				if(pe[i].hayTorre()&&ene[j]!=null)			
					if(pe[i].torre.checkEnemigo(ene[j].getX()+ene[j].getWidth()/2,ene[j].getY()+ene[j].getHeight()/2)&&!pe[i].torre.estaAtacando()){
						pe[i].torre.setIndexTarget(j);
					}		
		
		for(int i=0;i<cantPE;i++)
			if(pe[i].hayTorre()){
				if(pe[i].torre.estaAtacando()){																//Si el enemigo no murio
						if(pe[i].torre.checkEnemigo(ene[pe[i].torre.getIndexTarget()].getX(),ene[pe[i].torre.getIndexTarget()].getY())&&pe[i].torre.getMunicionCargada()){	//Si el enemigo sigue en el radio de alcance
								if(pe[i].torre.atacarEnemigos(ene[pe[i].torre.getIndexTarget()].getX()+ene[pe[i].torre.getIndexTarget()].getWidth()/2,ene[pe[i].torre.getIndexTarget()].getY()+ene[pe[i].torre.getIndexTarget()].getHeight()/2)){	//Ataca al enemigo																																										//y le resta vida
									ene[pe[i].torre.getIndexTarget()].restarVida(pe[i].torre.getPoder());
									pe[i].torre.setMunicionCargada(false);	
									atacoRecien=true;
							}
							if(ene[pe[i].torre.getIndexTarget()].getVida()<=0){			
								monedas+=ene[pe[i].torre.getIndexTarget()].getRecompensa();	//Luego controla si murio//obtengo su recompensa y lo borro
								pe[i].torre.setIndexTarget(-1);								//y dejo a la torre disponible nuevamente, setIndexTarget se encarga de estaAtacando=false si idx=-1
							}
						}
						else if(pe[i].torre.getIndexTarget()!=-1)
								if(!pe[i].torre.checkEnemigo(ene[pe[i].torre.getIndexTarget()].getX(),ene[pe[i].torre.getIndexTarget()].getY())){//Luego chequea si el enemigo se fue del alcance
									pe[i].torre.setIndexTarget(-1);		
									pe[i].torre.noColisiono();																			//De ser asi, la torre vuelve a estar "desocupada"
								}
					}else
						pe[i].torre.noColisiono();
					
					if(((indMovimiento)%(pe[i].torre.getFrecuencia()*20))!=0&&!atacoRecien)
						pe[i].torre.setMunicionCargada(true);	
					if(((indMovimiento)%(pe[i].torre.getFrecuencia()*20))==0)
						atacoRecien=false;
			}
			
			// movimiento individuo
		for(int j=0;j<cantEnemigo;j++){		
			if(ene[j]!=null){
				if(!ene[j].getPeleando())
					if(!ene[j].puedeMov()||ene[j].getVida()<=0){
						if(!ene[j].puedeMov())
							vidas--;
						ene[j]=null;;
						enemigosActuales--;
					}
					else 
						if(indMovimiento%(ene[j].getVelMovimiento())==0)
							ene[j].moverse();
					
						
			}
			
			if(ene[j]==null)
			 for(int i=0;i<cantPE;i++)
					if(pe[i].hayTorre())
						if(pe[i].torre.getIndexTarget()==j){
							pe[i].torre.setIndexTarget(-1);
							pe[i].torre.noColisiono();
						}
			}		
			Mouse mouse=getMouse();
			
			if (mouse.isLeftButtonPressed()){
				System.out.println("X: "+ mouse.getX() + "Y:" +mouse.getY());
				//mouse.setSoltado();
			}

			if (mouse.isLeftButtonPressed()){
				for (int i=0;i<cantPE;i++){
					pe[i].checkClick(mouse.getX(), mouse.getY());
				}
			mouse.setSoltado();
			}
		
		}
			Keyboard keyboard = this.getKeyboard();
			LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
			for (KeyEvent event : keyEvents) {

				if ((event.getID() == KeyEvent.KEY_RELEASED)) {
				}

				//~ //La tecla ESC Termina la ejecucion del juego
				if ((event.getID() == KeyEvent.KEY_PRESSED) &&(event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
					this.stop();
				}
				if ((event.getID() == KeyEvent.KEY_PRESSED) &&(event.getKeyCode() == KeyEvent.VK_G)) {
					partidaGanada=true;
				}
				if ((event.getID() == KeyEvent.KEY_PRESSED) &&(event.getKeyCode() == KeyEvent.VK_P)) {
					partidaPerdida=true;
				}
				
				
				
			}
			
		if(vidas>0&&enemigosLanzados==cantEnemigo&&enemigosActuales==0)
			partidaGanada=true;
		if(vidas==0)
			partidaPerdida=true;
			
	}
	
	   public void drawText(Graphics2D g,String texto,int x,int y){
    	 g.setColor(Color.black);
    	 g.drawString(texto,x+2,y+2);
    	 g.setColor(Color.white);
    	 g.drawString(texto,x,y);
		}

	public void gameDraw(Graphics2D g) {
		if(!partidaGanada&&!partidaPerdida)
		dAhora= new Date();
		long dateDiff = dAhora.getTime() - dInit.getTime();
    	long diffSeconds = dateDiff / 1000 % 60;
		long diffMinutes = dateDiff / (60 * 1000) % 60;
		
		
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);


		g.drawImage(img_fondo,0,0,null);// fondo
			
		g.drawImage(atributos,15,5,null);
		g.setColor(Color.white);
		g.drawString(monedas.toString(),115,28);
		g.drawString(vidas.toString(),60,28);
		
		for (int i=0;i<cantPE;i++)
			pe[i].paint(g);
	
		for(int j=0;j<cantEnemigo;j++)
			if(ene[j]!=null)
				ene[j].paint(g);
			
		g.setColor(Color.red);
		if(diffMinutes<10&&diffSeconds<10)
			drawText(g,"Tiempo:   0"+diffMinutes+":0"+diffSeconds,47,54);
		if(diffMinutes<10&&diffSeconds>9)
			drawText(g,"Tiempo:   0"+diffMinutes+":"+diffSeconds,47,54);
		if(diffMinutes>9&&diffSeconds<10)
			drawText(g,"Tiempo:   "+diffMinutes+":0"+diffSeconds,47,54);
		drawText(g,"Enemigos restantes"+((cantEnemigo-enemigosLanzados)+enemigosActuales),47,74);
			
		
	if(partidaGanada)
		g.drawImage(img_finPartida[0],175,200,null);
	if(partidaPerdida)
		g.drawImage(img_finPartida[1],0,0,null);
		
	}


	public void gameShutdown() {
		if(level2disp||partidaGanada)
			kr = new KingdomRush(true);
		if(!level2disp&&!partidaGanada)
			kr = new KingdomRush(false);
			t = new Thread() {
			public void run(){
				kr.run(1.0 / 60.0);
			}
			};
			t.start();
	}
}

