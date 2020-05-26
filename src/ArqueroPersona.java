public class ArqueroPersona extends Individuo{
private int ind_img=0;	
private final int ind_max=4;

ArqueroPersona(int x,int y){
super("/images/individuos/arqueros/arquero0.png",x,y);
}

public void moverse(){
cambiarImagen("/images/individuos/arqueros/arquero"+ind_img+".png");
if(ind_img==ind_max-1)
	ind_img=0;
else
	ind_img++;
	
}

}



