package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Mesa;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Mesa mesa;
	private boolean check;
	private boolean checkMate;
	private PecasXadrez enPassanteVulneravel;
	private PecasXadrez promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		// É nesta classe que será que o tabuleiro será 8x8
		mesa = new Mesa(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		// Para chamar o método configuracaoInicial, tem que ser chamado no construtor
		// da classe
		// Assim que for iniciado o programa será iniciado uma mesa 8x8 com e chamará o
		// método
		check = false; // Facultivo. Ou seja, serve somente para enfatizar
		configuracaoInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecasXadrez getEnPassantVulneravel() {
		return enPassanteVulneravel;
	}

	public PecasXadrez getPromocao() {
		return promocao;
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

	public boolean[][] possiveisMovimentos(XadrezPosicao posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validarProcuraPosicao(posicao);
		return mesa.peca(posicao).possiveisMovimentos();

	}

	public PecasXadrez executaMovimentoXadrez(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarProcuraPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca capturadaPeca = fazerMovimento(origem, destino);

		PecasXadrez pecaMovida = (PecasXadrez) mesa.peca(destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturadaPeca);
			throw new XadrezException("Você não pode se colocar em check");
		}

		// Movimento especial: Promoção
		promocao = null;
		if (pecaMovida.getCor() == Cor.WHITE && destino.getLinha() == 0
				|| pecaMovida.getCor() == Cor.BLACK && destino.getLinha() == 7) {
			promocao = (PecasXadrez) mesa.peca(destino);
			promocao = substituirPecaPromocao("r");
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// #Movimento Especial: en passant
		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || (destino.getLinha() == origem.getLinha() + 2))) {
			enPassanteVulneravel = pecaMovida;
		} else {
			enPassanteVulneravel = null;
		}

		return (PecasXadrez) capturadaPeca;
	}

	public PecasXadrez substituirPecaPromocao(String type) {
		if (promocao == null) {
			 throw new IllegalStateException("Não há peça para ser promovida");
		}
		
		if (!type.equals("B") && !type.equals("C") && !type.equals("r") && !type.equals("T")) {
			throw new InvalidParameterException("Type inválido para promoção");
		}
		
		Posicao pos = promocao.getXadrezPosicao().toPosicao();
		Peca p = mesa.removendoPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecasXadrez novaPeca = novaPeca(type, promocao.getCor());
		mesa.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecasXadrez novaPeca(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(mesa, cor);
		if (type.equals("C")) return new Cavalo(mesa, cor);
		if (type.equals("r")) return new Rainha(mesa, cor);
		return new Torre(mesa, cor);
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecasXadrez p = (PecasXadrez) mesa.removendoPeca(origem);
		p.somaContagemMovimentos();
		Peca capturadaPeca = mesa.removendoPeca(destino);
		mesa.colocarPeca(p, destino);

		if (capturadaPeca != null) {
			pecasNoTabuleiro.remove(capturadaPeca);
			pecasCapturadas.add(capturadaPeca);
		}

		// movimento especial Torre do lado do Rei
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecasXadrez torre = (PecasXadrez) mesa.removendoPeca(origemT);
			mesa.colocarPeca(torre, destinoT);
			torre.somaContagemMovimentos();
		}

		// movimento especial Torre do lado da Rainha
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecasXadrez torre = (PecasXadrez) mesa.removendoPeca(origemT);
			mesa.colocarPeca(torre, destinoT);
			torre.somaContagemMovimentos();
		}

		// movimento especial: en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturadaPeca == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				capturadaPeca = mesa.removendoPeca(peaoPosicao);
				pecasCapturadas.add(capturadaPeca);
				pecasNoTabuleiro.remove(capturadaPeca);
			}
		}

		return capturadaPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecasCapturada) {
		PecasXadrez p = (PecasXadrez) mesa.removendoPeca(destino);
		p.substraiContagemMovimentos();
		mesa.colocarPeca(p, origem);

		if (pecasCapturada != null) {
			mesa.colocarPeca(pecasCapturada, destino);
			pecasCapturadas.remove(pecasCapturada);
			pecasNoTabuleiro.add(pecasCapturada);
		}

		// movimento especial: roque pequeno (lado do Rei)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecasXadrez torre = (PecasXadrez) mesa.removendoPeca(origemT);
			mesa.colocarPeca(torre, destinoT);
			torre.somaContagemMovimentos();
			;
		}

		// movimento especial: roque grande (lado da Rainha)
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecasXadrez torre = (PecasXadrez) mesa.removendoPeca(destinoT);
			mesa.colocarPeca(torre, origemT);
			torre.somaContagemMovimentos();
		}

		// movimento especial: en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecasCapturada == enPassanteVulneravel) {
				PecasXadrez peao = (PecasXadrez) mesa.removendoPeca(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				mesa.colocarPeca(peao, peaoPosicao);
			}
		}

	}

	// validateSourcePosition
	private void validarProcuraPosicao(Posicao posicao) {
		// Verifica limites do tabuleiro
		if (!mesa.posicaoExistente(posicao)) {
			throw new XadrezException("Não existe posição no tabuleiro");
		}

		// VERIFICAÇÃO QUE FALTA: A casa está vazia?
		// Se você não tiver o método temPeca, use a verificação de null
		if (mesa.peca(posicao) == null) {
			throw new XadrezException("Não existe peça na posição de origem");
		}

		if (jogadorAtual != ((PecasXadrez) mesa.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}

		// Verifica se a peça está travada
		if (!mesa.peca(posicao).temUmPossivelMovimento()) {
			throw new XadrezException("Não existe possíveis movimentos para a peça escolhida");
		}
	}

	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!mesa.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peça escolhida, não pode mover para a posição de destino");
		}
	}

	private void proximoTurno() {
		turno++;
		// Expressão condicional alternária
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private PecasXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecasXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o Rei na cor " + cor + " no tabuleiro");
	}

	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < mesa.getLinhas(); i++) {
				for (int j = 0; j < mesa.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecasXadrez) p).getXadrezPosicao().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	// placeNewPiece
	private void pecaPosicaoNova(char coluna, int linha, PecasXadrez peca) {
		mesa.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	// initialSetup
	private void configuracaoInicial() {
		pecaPosicaoNova('a', 1, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('b', 1, new Cavalo(mesa, Cor.WHITE));
		pecaPosicaoNova('c', 1, new Bispo(mesa, Cor.WHITE));
		pecaPosicaoNova('d', 1, new Rainha(mesa, Cor.WHITE));
		pecaPosicaoNova('e', 1, new Rei(mesa, Cor.WHITE, this));
		pecaPosicaoNova('f', 1, new Bispo(mesa, Cor.WHITE));
		pecaPosicaoNova('g', 1, new Cavalo(mesa, Cor.WHITE));
		pecaPosicaoNova('h', 1, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('a', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('b', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('c', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('d', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('e', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('f', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('g', 2, new Peao(mesa, Cor.WHITE, this));
		pecaPosicaoNova('h', 2, new Peao(mesa, Cor.WHITE, this));

		pecaPosicaoNova('a', 8, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('b', 8, new Cavalo(mesa, Cor.BLACK));
		pecaPosicaoNova('c', 8, new Bispo(mesa, Cor.BLACK));
		pecaPosicaoNova('d', 8, new Rainha(mesa, Cor.BLACK));
		pecaPosicaoNova('e', 8, new Rei(mesa, Cor.BLACK, this));
		pecaPosicaoNova('f', 8, new Bispo(mesa, Cor.BLACK));
		pecaPosicaoNova('g', 8, new Cavalo(mesa, Cor.BLACK));
		pecaPosicaoNova('h', 8, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('a', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('b', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('c', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('d', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('e', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('f', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('g', 7, new Peao(mesa, Cor.BLACK, this));
		pecaPosicaoNova('h', 7, new Peao(mesa, Cor.BLACK, this));
	}

}
