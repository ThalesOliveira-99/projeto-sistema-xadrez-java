package tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Mesa mesa;

	public Peca(Mesa mesa) {
		// this.posicao = posicao de uma peça recém criada, será nula. Ou seja, não foi
		// colocada no tabuleiro ainda
		this.mesa = mesa;
		posicao = null; // Mesmo se não for atribuido o "null", por padrão Java será considerado como
						// nulo.
	}

	// Somente classes e subclasses dentro do pacote podem ter acesso a mesa de uma
	// peça
	// Ou seja, só será de uso interno do pacote tabuleiro
	protected Mesa getMesa() {
		return mesa;
	}

	// Não será permitido que a mesa seja alterada
//	public void setMesa(Mesa mesa) {
//		this.mesa = mesa;
//	}

	public abstract boolean[][] possiveisMovimentos();

	public boolean possivelMovimento(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean temUmPossivelMovimento() {
		boolean[][] mat = possiveisMovimentos();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat[i].length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
