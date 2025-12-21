package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Mesa;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Mesa mesa;
	private boolean check;

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

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturadaPeca);
			throw new XadrezException("Você não pode se colocar em check");
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (PecasXadrez) capturadaPeca;
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = mesa.removendoPeca(origem);
		Peca capturadaPeca = mesa.removendoPeca(destino);
		mesa.colocarPeca(p, destino);

		if (capturadaPeca != null) {
			pecasNoTabuleiro.remove(capturadaPeca);
			pecasCapturadas.add(capturadaPeca);
		}

		return capturadaPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecasCapturada) {
		Peca p = mesa.removendoPeca(destino);
		mesa.colocarPeca(p, origem);

		if (pecasCapturada != null) {
			mesa.colocarPeca(pecasCapturada, destino);
			pecasCapturadas.remove(pecasCapturada);
			pecasNoTabuleiro.add(pecasCapturada);
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

	// placeNewPiece
	private void pecaPosicaoNova(char coluna, int linha, PecasXadrez peca) {
		mesa.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	// initialSetup
	private void configuracaoInicial() {
		pecaPosicaoNova('c', 1, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('c', 2, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('d', 2, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('e', 2, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('e', 1, new Torre(mesa, Cor.WHITE));
		pecaPosicaoNova('d', 1, new Rei(mesa, Cor.WHITE));

		pecaPosicaoNova('c', 7, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('c', 8, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('d', 7, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('e', 7, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('e', 8, new Torre(mesa, Cor.BLACK));
		pecaPosicaoNova('d', 8, new Rei(mesa, Cor.BLACK));
	}

}
