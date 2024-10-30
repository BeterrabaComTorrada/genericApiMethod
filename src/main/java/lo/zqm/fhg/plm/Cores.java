package lo.zqm.fhg.plm;

public enum Cores {
	BRANCO(1),
	PRETO(10),
	VERMELHO(3),
	AMARELO(2);
	
	int Favoritismo;
	Cores(int favoritismo){
		this.Favoritismo = favoritismo;
	}
}
