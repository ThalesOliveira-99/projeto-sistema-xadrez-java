package xadrez;

import tabuleiro.Mesa;
import tabuleiro.Posicao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Mesa mesa;

	public PartidaXadrez() {
		// É nesta classe que será que o tabuleiro será 8x8
		mesa = new Mesa(8, 8);
		//Para chamar o método configuracaoInicial, tem que ser chamado no construtor da classe 
		// Assim que for iniciado o programa será iniciado uma mesa 8x8 com e chamará o método
		configuracaoInicial();
	}

	// Este método terá que retornar uma matriz de peças de xadrez corresponde a
	// PartidaXadrez
	public PecasXadrez[][] getPecas() {
		PecasXadrez[][] mat = new PecasXadrez[mesa.getLinhas()][mesa.getColunas()];
		for (int i = 0; i < mesa.getLinhas(); i++) {
			for (int j = 0; j < mesa.getColunas(); j++) {
				mat[i][j] = (PecasXadrez) mesa.peca(i, j);
			}
		}
		return mat;
	}
	
	private void configuracaoInicial() {
		mesa.ponto(new Torre(mesa, Cor.WHITE), new Posicao(2,1));
		mesa.ponto(new Rei(mesa, Cor.BLACK), new Posicao(0,4));
		mesa.ponto(new Rei(mesa, Cor.WHITE), new Posicao(7,4));
	}
}
