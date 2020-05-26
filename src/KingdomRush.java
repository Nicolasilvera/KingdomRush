import java.awt.image.*;	//BufferedImage
import java.io.*;			//Exception catch
import javax.imageio.*;			//cargar imagen
import java.awt.*;			//Graphics2D
import java.awt.geom.*;		//Rectangle
import com.entropyinteractive.* ; //Clase Mouse


public class KingdomRush extends Game{
	protected Thread t;
	private BufferedImage[] img= new BufferedImage[7];
	private boolean nivel2desbloqueado=true;
	private Partida partida;
	
//Areas Presionables
	private Rectangle2D areaPlay =new Rectangle2D.Double(235,350,230,80);
	private Rectangle2D area_lvl_1 =new Rectangle2D.Double(85,165,200,171);
	private Rectangle2D area_lvl_2 =new Rectangle2D.Double(415,165,200,171);
//Controles de mouse y seleccion
	private int nivelSeleccionado=1;	
	private boolean playSelec=false;	//si el mouse esta sobre el boton=true
	private boolean playPressed=false;	//si el boton play fue presionado=true
	private boolean lvl_1Pressed=true;	//uno true, el otro false.
	private boolean lvl_2Pressed;
	
	// Constructor
	public KingdomRush(boolean nivel2){
		super("K.Rush",700,600);
		this.nivel2desbloqueado=nivel2;
		if(nivel2){
			lvl_1Pressed=false;
			lvl_2Pressed=true;
			nivelSeleccionado=2;
		}
	}

	public void gameStartup() {
		try{
			img[0]=ImageIO.read(getClass().getResource("images/videojuego_00.png"));
			img[1]=ImageIO.read(getClass().getResource("images/videojuego_01.png"));
			img[2]=ImageIO.read(getClass().getResource("images/lvl_1_NOselected.png"));
			img[3]=ImageIO.read(getClass().getResource("images/lvl_1_selected.png"));
			img[4]=ImageIO.read(getClass().getResource("images/lvl_2_locked.png"));
			img[5]=ImageIO.read(getClass().getResource("images/lvl_2_NOselected.png"));
			img[6]=ImageIO.read(getClass().getResource("images/lvl_2_selected.png"));
		}catch (IOException e){
				System.out.println(e);
			}
	}
	
	public void gameUpdate(double delta) {	
			Mouse mouse=getMouse();
			
			if(mouse.isLeftButtonPressed())
				System.out.println(mouse.getX()+"    "+mouse.getY());
			
			if(areaPlay.contains((double)mouse.getX(),(double)mouse.getY()))
				playSelec=true;
			else
				playSelec=false;
				
			if(area_lvl_1.contains((double)mouse.getX(),(double)mouse.getY())&&mouse.isLeftButtonPressed()){
				lvl_2Pressed=false;
				lvl_1Pressed=true;
				nivelSeleccionado=1;
			}
			
			if(area_lvl_2.contains((double)mouse.getX(),(double)mouse.getY())&&mouse.isLeftButtonPressed()&&nivel2desbloqueado){
				lvl_1Pressed=false;
				lvl_2Pressed=true;
				nivelSeleccionado=2;
			}
				

			if(areaPlay.contains((double)mouse.getX(),(double)mouse.getY())&&mouse.isLeftButtonPressed()){
				playPressed=true;
				this.stop();
			}else
				playPressed=false;
				
			Keyboard keyboard = this.getKeyboard();
			
			 
	}
	
	public void gameDraw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img[0],0,0,null);// fondo
		g.drawImage(img[2],85,165,null);// fondo
		g.drawImage(img[4],415,165,null);// fondo
		
		if(nivel2desbloqueado)
			g.drawImage(img[5],415,165,null);
		
		if(playSelec)
			g.drawImage(img[1],0,0,null);		//boton play amarillo
		
		if(lvl_1Pressed)
			g.drawImage(img[3],85,165,null);	//si se clickea
			
		if(nivel2desbloqueado)
			if(lvl_2Pressed)
				g.drawImage(img[6],415,165,null);
			
	}

	public void gameShutdown() {
		partida=new Partida(this,nivelSeleccionado,nivel2desbloqueado);
		t = new Thread() {
		public void run() {
			partida.run(1.0 / 60.0);
		}
		};
		t.start();
	}
}
