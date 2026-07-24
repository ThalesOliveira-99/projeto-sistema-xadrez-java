package xadrez.pecas;

import tabuleiro.Mesa;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.PecasXadrez;

public class Rainha extends PecasXadrez {

	public Rainha(Mesa mesa, Cor cor) {
		super(mesa, cor);

	}

	@Override
	public String toString() {
		return "r";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getMesa().getLinhas()][getMesa().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
			;
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
			;
		}
		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Para baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}

		if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

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
