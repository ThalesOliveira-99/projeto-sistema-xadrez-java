package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecasXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecasXadrez> capturadas = new ArrayList<>();

		while (true) {
			try {
				UI.clearScreen();
				UI.escreverPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				XadrezPosicao origem = UI.lerXadrezPosicao(sc);

				boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.clearScreen();
				UI.escreverTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.print("Destino: ");
				XadrezPosicao destino = UI.lerXadrezPosicao(sc);

				PecasXadrez pecaCapturada = partidaXadrez.executaMovimentoXadrez(origem, destino);

				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
