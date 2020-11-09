import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Impressao extends Thread {

	Semaphore sem1;
	Semaphore sem2;
	ArrayList<Pedido> pedidos;
	String nome;
	Metricas metricas;

	public Impressao(Semaphore sem1, Semaphore sem2, ArrayList<Pedido> pedidos, Metricas metricas, String nome) {
		super();
		this.sem1 = sem1;
		this.sem2=sem2;
		this.pedidos = pedidos;
		this.metricas = metricas;
		this.nome = nome;
	}

	@Override
	public void run() {

		try {
			//SEMAFORO PEDIDOS SIZE
			while (pedidos.size() > 0) {
				
				sem1.acquire();
				Pedido p = pedidos.remove(0);
				sem1.release();
				
				double tempo = p.paginas / 80.0;
				double receita = p.paginas * p.precoPagina;

				Thread.sleep((long) tempo);
				
				sem2.acquire();
				metricas.addMetricas(tempo, receita, p.prazo);
				sem2.release();

			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}

	}
}
