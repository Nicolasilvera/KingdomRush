
public abstract class Individuo extends ObjetoGrafico {
	protected int velMovimiento;
	protected int velAtaque;
	private int vida;
	protected int poder;
	protected boolean peleando;

	public Individuo(String filename,int x,int y){
		super(filename,x,y);
	}
	
	public void moverse(int x,int y){
		setPosition((double)x,(double)y);
	}
	
	public void restarVida(int p){
		vida-=p;
	}
	
	public boolean getPeleando(){
		return peleando;
	}
	
	public int getVelMovimiento(){
		return velMovimiento;
	}
	public void setVida(int vida){
		this.vida=vida;
	}
	
	public int getVida(){
		return vida;
	}
	
	
}

