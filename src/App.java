import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

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

	public static void metodoTradicional(ArrayList<Pedido> pedidos, int nImpressoras) throws InterruptedException {

		System.out.println("Metodo tradicional da grafica:");
		System.out.println("Pedidos são realizados na ordem de sua chegada\n");
		imprimir(pedidos);
	}

	public static void priorizarTempoMedio(ArrayList<Pedido> pedidos, int nImpressoras) throws InterruptedException {
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
		imprimir(pedidos);
	}

	public static void priorizar12h(ArrayList<Pedido> pedidos, int nImpressoras) throws InterruptedException {
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
		imprimir(pedidos);
	}

	public static void priorizarPrazo(ArrayList<Pedido> pedidos, int nImpressoras) throws InterruptedException {
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
		imprimir(pedidos);
	}

	public static void imprimir(ArrayList<Pedido> pedidos) throws InterruptedException {

		Semaphore sem = new Semaphore(1);

		Metricas met = new Metricas();

		Impressao t1 = new Impressao(sem, pedidos, met, "Impressora 1");
		Impressao t2 = new Impressao(sem, pedidos, met, "Impressora 2");

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		double dias = Math.ceil(met.getTempoTotal()/ 480.0);
		double custo = 900 * dias * 2;

		double tempoMedio = Math.round((met.getSomaTempos() / met.getCount()) * 100);
		tempoMedio /= 100;

		System.out.println("Tempo total: " + met.getTempoTotal() + " minutos");
		System.out.println("Tempo medio: " + tempoMedio + " minutos");
		System.out.println("Receita: " + met.getReceita() + " dinheiros");
		System.out.println("Custo: " + custo + " dinheiros");
		System.out.println("Lucro: " + (met.getReceita() - custo) + " dinheiros");
		System.out.println("Pedidos antes do meio dia: " + met.getPedidosAntes12()+ " (total de pedidos com prazo: "
				+ met.getCount() + ")");
		System.out.println("Pedidos atendidos dentro do prazo: " + met.getPedidosPrazo() + " (total de pedidos com prazo: "
				+ met.getPedidosPrazoCount() + ")");
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
//		valeAPena();
	}
}
