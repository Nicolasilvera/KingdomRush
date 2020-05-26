import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.*;
public class SistemaDeSoftware extends JFrame implements ItemListener,ActionListener {
	
	// Declaro los atributos
	private KingdomRush KR;
	
	protected Thread t;
	private JMenuBar principal;
	private JMenu archivo,ver,opciones,ayuda;
	private JMenuItem archivo_agregar, archivo_salir;
	private JMenuItem ayuda_ayuda,ayuda_donar;
	private JPanel panel;
	private List lst;
	private JButton boton;
	private JLabel etiqueta;
	private JPanel canvas;
	private Image imagen[] = new Image[5];
	private String arrImagenes[]={"images/asdd.jpg","images/adventure-island-1.jpg","images/PES-2017.jpg","images/ClashRoyale1.jpg"};
	private ImagenFondo fondo;
		
	// Constructor
	public SistemaDeSoftware(){
		super ("Plataforma Videojuegos");
		super.setLocation(190,60);
		super.setMinimumSize(new Dimension(700,450));
		
		//cargo las imagenes
		for(int i=0; i<arrImagenes.length ; i++){
			imagen[i] = Toolkit.getDefaultToolkit().getImage(arrImagenes[i]);
		}
		fondo = new ImagenFondo(imagen[0]);
		
		panel = new JPanel(new BorderLayout());
		principal = new JMenuBar();
		archivo = new JMenu("Archivo");
		ver = new JMenu("Ver");
		opciones = new JMenu ("Opciones");
		ayuda = new JMenu ("Ayuda");
		boton = new JButton("Lanzar");
		etiqueta = new JLabel ("Juegos agregados");
		lst = new List();
		lst.add("Kingdom Rush");
		lst.add("Adventure Island");
		lst.add("PES 2017");
		lst.add("Clash Royale");
		lst.addItemListener(this);
		archivo_agregar = new JMenuItem ("Agregar juego...");
		archivo_salir = new JMenuItem("Salir");
		ayuda_ayuda = new JMenuItem("Ayuda");
		ayuda_donar = new JMenuItem("Donar...");
		canvas = new JPanel();
		archivo.add(archivo_agregar);
		archivo.add(archivo_salir);
		ayuda.add(ayuda_ayuda);
		ayuda.add(ayuda_donar);
		principal.add(archivo);
		principal.add(ver);
		principal.add(opciones);
		principal.add(ayuda);
		boton.addActionListener(this);
		
		panel.add("North",etiqueta);
		panel.add("Center",lst);
		panel.add("South",boton);

	// Asigno Layouts y agrego objetos a "this"
		this.setJMenuBar(principal);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.add("West",panel);
		this.add("Center",fondo);
		
	
	//---------------------capturar evento para cerrar la aplicacion
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowClosing(java.awt.event.WindowEvent e){
				System.exit(0);
			}
		});
		this.pack();
		this.setVisible(true);
		}
		
//METODO PRINCIPAL
	public static void main (String[] args) {
		SistemaDeSoftware prueba = new SistemaDeSoftware();
	}
	
// EVENTOS
	
	public void itemStateChanged(ItemEvent e){
		fondo.cambiarImagen(imagen[lst.getSelectedIndex()]);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("Lanzar")&& lst.getSelectedItem().equals("Kingdom Rush"))
			KR = new KingdomRush(false);
			t = new Thread() {
			public void run(){
				KR.run(1.0 / 60.0);
			}
			};
			t.start();
			this.dispose();
		}
}
	


