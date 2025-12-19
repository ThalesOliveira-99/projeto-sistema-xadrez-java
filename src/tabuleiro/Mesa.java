package tabuleiro;

public class Mesa {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Mesa(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException(
					"Erro na criação do tabuleiro: é necessário que haja pelo menos um linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posição não está no tabuleiro");
		}
		return pecas[linha][coluna];
	}

	// Sobrecarga
	public Peca peca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição não está no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	// PlacePiece
	public void colocarPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peça posicionada " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	public Peca removendoPeca(Posicao posicao) {
		// Programação defensiva
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição não está no tabuleiro");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}

	private boolean posicaoExistente(int linha, int coluna) {

		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}

	// thereIsAPiece
	public boolean temUmaPeca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição não está no tabuleiro");
		}
		return peca(posicao) != null;
	}

}
