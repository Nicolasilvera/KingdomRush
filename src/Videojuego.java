import com.entropyinteractive.* ;
import processing.core.* ;
import java.util.*;
import java.awt.*;

public abstract class Videojuego extends Game{
	protected Jugador jugadorActual;
	public void iniciar(){};
	public void finalizar(){};
	public void configurar(){};
	public void ranking(){};
	public void seleccionarNivel(){};
	
	public Videojuego(String x, int ancho, int alto) {
		super(x,ancho,alto);
	}
	

}

