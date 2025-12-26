package xadrez.pecas;

import tabuleiro.Mesa;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.PecasXadrez;

public class Bispo extends PecasXadrez {

	public Bispo(Mesa mesa, Cor cor) {
		super(mesa, cor);

	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getMesa().getLinhas()][getMesa().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
			;
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
			;
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}

		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
