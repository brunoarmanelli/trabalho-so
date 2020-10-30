import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class App {

	public static ArrayList<Pedido> lerArquivo(String arq) throws Exception {
		Scanner entrada = new Scanner(new File(arq));

		int NumPedidos = Integer.parseInt(entrada.nextLine());

		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		while (entrada.hasNextLine()) {
			String linha = entrada.nextLine();
			String[] pedido = linha.split(";");
			String nome = pedido[0];
			int paginas = Integer.parseInt(pedido[1]);
			Double precoPagina = Double.parseDouble(pedido[2]);
			int prazo = Integer.parseInt(pedido[3]);
			pedidos.add(new Pedido(nome, paginas, precoPagina, prazo));
		}

		return pedidos;
	}

	public static void metodoTradicional(ArrayList<Pedido> pedidos, int nImpressoras) {

		System.out.println("Metodo tradicional da grafica:");
		System.out.println("Pedidos são realizados na ordem de sua chegada\n");
		impressao(pedidos, nImpressoras);
	}

	public static void priorizarTempoMedio(ArrayList<Pedido> pedidos, int nImpressoras) {
		Collections.sort(pedidos, new Comparator<Pedido>() {
			@Override
			public int compare(Pedido o1, Pedido o2) {
				if (o1.paginas > o2.paginas)
					return 1;
				if (o1.paginas < o2.paginas)
					return -1;
				return 0;
			}
		});
		System.out.println("\nMinimizando o tempo medio:");
		System.out.println(
				"Para isso priozimamos pedidos com menor tempo de execuxão, portanto com menor numero de paginas\n");
		impressao(pedidos, nImpressoras);
	}

	public static void priorizar12h(ArrayList<Pedido> pedidos, int nImpressoras) {
		Collections.sort(pedidos, new Comparator<Pedido>() {
			@Override
			public int compare(Pedido o1, Pedido o2) {
				if (o1.paginas > o2.paginas)
					return 1;
				if (o1.paginas < o2.paginas)
					return -1;
				return 0;
			}
		});
		System.out.println("\nMaximixando a quantidade de trabalhos impressoes antes do meio dia:");
		System.out.println("Para isso priozimamos pedidos com menor numero de paginas\n");
		impressao(pedidos, nImpressoras);
	}

	public static void priorizarPrazo(ArrayList<Pedido> pedidos, int nImpressoras) {
		Collections.sort(pedidos, new Comparator<Pedido>() {
			@Override
			public int compare(Pedido o1, Pedido o2) {
				if (o1.prazo == 0)
					return 1;
				if (o2.prazo == 0)
					return -1;
				if (o1.prazo > o2.prazo)
					return 1;
				if (o1.prazo < o2.prazo)
					return -1;
				return 0;
			}
		});

		System.out.println("\nAtendendo a prazos estritos:");
		System.out.println("Para isso priozimamos pedidos que possuem prazos menores\n");
		impressao(pedidos, nImpressoras);
	}

	public static void impressao(ArrayList<Pedido> pedidos, int nImpressoras) {

		long tamanho = pedidos.stream().count();

		double tempoTotal = 0;
		double receita = 0;

		double tempos = 0;

		int pedidosAte12h = 0;
		int horas4 = 240;
		boolean passou12 = false;

		int pedidosPrazo = 0;
		int pedidosPrazoCount = 0;

		while (pedidos.stream().count() > 0) {
			if (pedidos.stream().count() > 2)
				tempoTotal += 0.25;
			for (int i = 0; i < nImpressoras; i++) {
				try {
					Pedido pedido = pedidos.remove(0);
					Impressora imp = new Impressora(pedido);
					tempoTotal += imp.tempoGasto;
					receita += imp.lucro;
					tempos += tempoTotal;
					if (horas4 > 0 && horas4 > imp.tempoGasto && !passou12) {
						pedidosAte12h++;
						horas4 -= imp.tempoGasto;
					} else
						passou12 = true;
					if (pedido.prazo >= tempoTotal && pedido.prazo != 0)
						pedidosPrazo++;
					if (pedido.prazo != 0)
						pedidosPrazoCount++;
				} catch (IndexOutOfBoundsException e) {
//					System.out.println(e);
				}
			}
		}

		double dias = Math.ceil(tempoTotal / 480.0);
		double custo = 900 * dias * nImpressoras;

		double tempoMedio = Math.round((tempos / tamanho) * 100);
		tempoMedio /= 100;
		receita = Math.round(receita * 100);
		receita /= 100;

		System.out.println("Tempo total: " + tempoTotal + " minutos");
		System.out.println("Tempo medio: " + tempoMedio + " minutos");
		System.out.println("Receita: " + receita + " dinheiros");
		System.out.println("Custo: " + custo + " dinheiros");
		System.out.println("Lucro: " + (receita - custo) + " dinheiros");
		System.out.println("Pedidos antes do meio dia: " + pedidosAte12h);
		System.out.println("Pedidos atendidos dentro do prazo: " + pedidosPrazo + " (total de pedidos com prazo: "
				+ pedidosPrazoCount + ")");
	}

	public static double lucroNImpressoras(int dias, int nImpressoras, int custoInicial) {
		double receita = 250 * nImpressoras * dias * 8;
		double custo = custoInicial + (900 * nImpressoras * dias);
		double lucro = receita - custo;
		return lucro;
	}

	public static void valeAPena() {
		System.out.println("ADICIONAR SE VALE A PENA COM PREÇO REAL DO ARQUIVO");
		int i = 0;
		while (true) {
			if (lucroNImpressoras(i, 2, 0) < lucroNImpressoras(i, 3, 45000))
				break;
			i++;
		}
		System.out
				.println("\nCaso compre uma nova impressora, o lucro só será maior do que o atual após " + i + " dias");
	}

	public static void main(String[] args) throws Exception {
		metodoTradicional(lerArquivo("pedidos.txt"), 2);
		System.out.println("------------------------------------------");
		priorizarTempoMedio(lerArquivo("pedidos.txt"), 2);
		System.out.println("------------------------------------------");
		priorizar12h(lerArquivo("pedidos.txt"), 2);
		System.out.println("------------------------------------------");
		priorizarPrazo(lerArquivo("pedidos.txt"), 2);
		System.out.println("------------------------------------------");
		valeAPena();
	}
}
