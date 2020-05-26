import java.util.*;
import java.awt.*;		//Shape
import java.awt.geom.*; //Path, list, etc
import java.io.*;
import ArchivosdeNivel.*;

public class Camino{
    java.util.List<Point2D> puntos;
    java.util.List<Point2D> puntos2;
    java.util.List<Point2D> puntos3;
	private GeneralPath poligonoCerradoCamino=new GeneralPath();
	CargaRecursos cr=new CargaRecursos();
	
	public static void main(String [] strs){
	Camino c=new Camino(1);
		}
	
    public Camino(int nivel) {
	puntos=cr.cargarCamino(1,nivel);
	puntos2=cr.cargarCamino(2,nivel);
	puntos3=cr.cargarCamino(3,nivel);
	poligonoCerradoCamino=cr.cargarPoligono(nivel);
    }
    
    public GeneralPath getPoligono(){
		return poligonoCerradoCamino;
	}
    
    public java.util.List getCarril(int i){
		java.util.List<Point2D> aux = new ArrayList<>();
		switch(i){
				case 1:
					aux = puntos;
					break;
				case 2:
					aux = puntos2;
					break;
				case 3:
					aux = puntos3;
					break;
		}
		return aux;
	}
}
