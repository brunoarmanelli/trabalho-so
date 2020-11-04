import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Impressao extends Thread {

	Semaphore sem;
	ArrayList<Pedido> pedidos;
	String nome;
	Metricas metricas;

	public Impressao(Semaphore sem, ArrayList<Pedido> pedidos, Metricas metricas, String nome) {
		super();
		this.sem = sem;
		this.pedidos = pedidos;
		this.metricas = metricas;
		this.nome = nome;
	}

	@Override
	public void run() {

		try {
			while (pedidos.size() > 0) {
				
				sem.acquire();
				Pedido p = pedidos.remove(0);
				sem.release();
				
				double tempo = p.paginas / 80.0;
				double receita = p.paginas * p.precoPagina;

				Thread.sleep((long) tempo);
				
				sem.acquire();
				metricas.addMetricas(tempo, receita, p.prazo);
				sem.release();

//				System.out.println(nome + p);
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}

	}
}
