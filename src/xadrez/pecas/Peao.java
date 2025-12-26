package xadrez.pecas;

import tabuleiro.Mesa;
import tabuleiro.Posicao;
import xadrez.Cor;
import xadrez.PecasXadrez;

public class Peao extends PecasXadrez {

	public Peao(Mesa mesa, Cor cor) {
		super(mesa, cor);
	}
	
	@Override
	public boolean [][] possiveisMovimentos() {
		
		boolean[][] mat = new boolean[getMesa().getLinhas()][getMesa().getColunas()];

		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.WHITE) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p) && getMesa().posicaoExistente(p2) && !getMesa().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if(getMesa().posicaoExistente(p) && !getMesa().temUmaPeca(p) && getMesa().posicaoExistente(p2) && !getMesa().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getMesa().posicaoExistente(p) && existeAlgumaPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
}
