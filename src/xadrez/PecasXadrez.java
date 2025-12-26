package xadrez;

import tabuleiro.Mesa;
import tabuleiro.Peca;
import tabuleiro.Posicao;

public abstract class PecasXadrez extends Peca {

	private Cor cor;
	private int contadorMovimentos;

	public PecasXadrez(Mesa mesa, Cor cor) {
		super(mesa);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorMovimentos() {
		return contadorMovimentos;
	}	
	
	public void somaContagemMovimentos() {
		contadorMovimentos++;
	}
	
	public void substraiContagemMovimentos() {
		contadorMovimentos--;
	}
	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.fromPosicao(posicao);
	}

	// Pois não haverá permissão para trocar a cor da peça seja modificada. Somente
	// acessada
//	public void setCor(Cor cor) {
//		this.cor = cor;
//	}

	protected boolean existeAlgumaPeçaOponente(Posicao posicao) {

		// Foi realizado um downcast para poder funcionar
		// (PecasXadrez)getMesa().peca(posicao)
		PecasXadrez p = (PecasXadrez)getMesa().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}
