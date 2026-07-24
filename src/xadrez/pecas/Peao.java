package xadrez.pecas;

import tabuleiro.Mesa;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecasXadrez;

public class Peao extends PecasXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Mesa mesa, Cor cor, PartidaXadrez partidaXadrez) {
		super(mesa, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possiveisMovimentos() {

		boolean[][] mat = new boolean[getMesa().getLinhas()][getMesa().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p) && getMesa().posicaoExistente(p2)
					&& !getMesa().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento Especial: en passant branco
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getMesa().posicaoExistente(esquerda) && existeAlgumaPeçaOponente(esquerda)
						&& getMesa().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getMesa().posicaoExistente(direita) && existeAlgumaPeçaOponente(direita)
						&& getMesa().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p) && getMesa().posicaoExistente(p2)
					&& !getMesa().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento Especial: en passant preta
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getMesa().posicaoExistente(esquerda) && existeAlgumaPeçaOponente(esquerda)
						&& getMesa().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getMesa().posicaoExistente(direita) && existeAlgumaPeçaOponente(direita)
						&& getMesa().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
