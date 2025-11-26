package xadrez;

import tabuleiro.Mesa;
import tabuleiro.Peca;

public class PecasXadrez extends Peca {

	private Cor cor;

	public PecasXadrez(Mesa mesa, Cor cor) {
		super(mesa);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	//Pois não haverá permissão para trocar a cor da peça seja modificada. Somente acessada
//	public void setCor(Cor cor) {
//		this.cor = cor;
//	}

}
