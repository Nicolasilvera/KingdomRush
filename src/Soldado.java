
public class Soldado extends Individuo {
	public Soldado(int x, int y){
		super("images/individuos/barraca/2919.png",x,y);
		peleando=false;
		velMovimiento=5;
		velAtaque=4;
		this.setVida(90);
		poder=15;
	}
}

