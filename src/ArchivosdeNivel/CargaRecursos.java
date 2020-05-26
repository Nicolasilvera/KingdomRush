package ArchivosdeNivel;
import java.awt.*;		//Shape
import java.awt.geom.*; //Path, list, etc
import java.io.*;
import java.util.*;


public class CargaRecursos {
	private GeneralPath poligonoCerradoCamino;
	private String buffer;
	private RandomAccessFile rf;
	
	public CargaRecursos(){
	}
	
				public java.util.List cargarCamino(int carrilCargando, int nivel){
					/*Creacion de la ruta */
					Shape carrilUno=new Path2D.Double();
					java.util.List<Point2D> puntos;
					Path2D path = new Path2D.Double();
					Path2D path2 = new Path2D.Double();
					Path2D path3= new Path2D.Double();
					int[] v= new int[6];
					RandomAccessFile rf;
					
						
						try{
							//CARGA DEL CAMINO
							rf=new RandomAccessFile("src/ArchivosdeNivel/puntosCamino"+nivel+".txt", "r");
							rf.seek(0);
							
						for(int k=0;k<3;k++){
							buffer=rf.readLine();
							
							switch(k){
									case 0:	path.moveTo(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));
										break;
									case 1: path2.moveTo(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));
										break;
									case 2:path3.moveTo(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));
										break;
								}
							
							while(!(buffer=rf.readLine()).equals("#")){
								for(int j=0;j<24;j=j+4)
									v[j/4]=Integer.parseInt(buffer.substring(j,j+3));
								
								switch(k){
									case 0:	path.curveTo(v[0],v[1],v[2],v[3],v[4],v[5]);
										break;
									case 1: path2.curveTo(v[0],v[1],v[2],v[3],v[4],v[5]);
										break;
									case 2: path3.curveTo(v[0],v[1],v[2],v[3],v[4],v[5]);
										break;
								}
							}
							
						}
						rf.close();
							}catch(IOException e){
								System.out.println(e);
							}
							
					switch(carrilCargando){
						case 1:	carrilUno = path;
						break;
						case 2: carrilUno= path2;
						break;
						case 3: carrilUno=path3;
						break;
					}
					
					
					//Extraigo puntos de la primer ruta en una lista
					puntos = new ArrayList<>(50);
					PathIterator pi = carrilUno.getPathIterator(null, 0.0008);
					if(nivel==2)
						pi = carrilUno.getPathIterator(null, 0.01);
						
					while (!pi.isDone()) {
						double[] coords = new double[6];
						switch (pi.currentSegment(coords)) {
							case PathIterator.SEG_MOVETO:
							case PathIterator.SEG_LINETO:
								puntos.add(new Point2D.Double(coords[0], coords[1]));
								break;
						}
						pi.next();
					}
					System.out.println("El carril "+carrilCargando+" del nivel "+nivel+" se cargo correctamente");
					return puntos;
				}
	
	
	//CARGA DEL POLIGONO
	public GeneralPath cargarPoligono(int nivel){
		poligonoCerradoCamino=new GeneralPath();
			try{
			rf=new RandomAccessFile("src/ArchivosdeNivel/poligonoCamino"+nivel+".txt", "r");
			rf.seek(0);
			buffer=rf.readLine();
			poligonoCerradoCamino.moveTo(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));
			while(!(buffer=rf.readLine()).equals("#"))
				poligonoCerradoCamino.lineTo(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));	
				
			rf.close();
				}catch(IOException e){
					System.out.println(e);
				}
	System.out.println("El poligono del nivel "+nivel+" se cargo correctamente");
	return poligonoCerradoCamino;
	}
	
	
	public int[] cargarAtributos(int nivel){
	int[] atributos=new int[5];
	int i=1;
		try{
			rf=new RandomAccessFile("src/ArchivosdeNivel/atributosN"+nivel+".txt", "r");
			rf.seek(0);
			
			for(int j=0;j<5;j++){
				buffer=rf.readLine();
				atributos[j]=Integer.parseInt(buffer);
			}
			rf.close();
				}catch(IOException e){
					System.out.println(e);
				}
	System.out.println("Los atributos del nivel "+nivel+" se cargaron correctamente");
	return atributos;	
	}
	
	public Point2D[] cargarPuntosEstrategicos(int nivel, int ce){
		Point2D[] pos = new Point2D.Double[ce];
		try{
			rf=new RandomAccessFile("src/ArchivosdeNivel/puntosEstrategicosN"+nivel+".txt", "r");
			rf.seek(0);
			System.out.println("LEYO");
			for(int j=0;j<ce;j++){
				buffer=rf.readLine();
				pos[j]=new Point2D.Double(Integer.parseInt(buffer.substring(0,3)),Integer.parseInt(buffer.substring(4,7)));
			}
			rf.close();
				}catch(IOException e){
					System.out.println(e);
				}
		System.out.println("Se han cargado correctamente los "+ce+" puntos estrategicos del nivel "+nivel);
	return pos;
	}
	
}

