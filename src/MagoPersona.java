public class MagoPersona extends Individuo{
private int ind_img=1;	
private final int ind_max=9;
MagoPersona(int x,int y){
super("/images/individuos/magos/frente0.png",x,y);
}

public void moverse(){
cambiarImagen("/images/individuos/magos/frente"+ind_img+".png");
if(ind_img==ind_max-1)
	ind_img=0;
else
	ind_img++;
	
}

}

