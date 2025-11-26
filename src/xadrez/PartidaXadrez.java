package xadrez;

import tabuleiro.Mesa;

public class PartidaXadrez {

	private Mesa mesa;
	
	public PartidaXadrez() {
		//É nesta classe que será que o tabuleiro será 8x8
		mesa = new Mesa(8,8);
	}
	
	//Este método terá que retornar uma matriz de peças de xadrez corresponde a PartidaXadrez
	public PecasXadrez[][] getPecas(){
		PecasXadrez[][] mat = new PecasXadrez[mesa.getLinhas()][mesa.getColunas()];
		for (int i=0; i<mesa.getLinhas(); i++) {
			for (int j=0; j<mesa.getColunas(); j++) {
				mat[i][j] = (PecasXadrez) mesa.peca(i,j);
			}
		}
		return mat;
	}
}
