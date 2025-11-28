package xadrez.pecas;

import tabuleiro.Mesa;
import xadrez.Cor;
import xadrez.PecasXadrez;

public class Torre extends PecasXadrez {

	public Torre(Mesa mesa, Cor cor) {
		super(mesa, cor);

	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getMesa().getLinhas()][getMesa().getColunas()];
		return mat;
	}
}
