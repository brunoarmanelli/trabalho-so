
public class Metricas {
	private double tempoTotal;
	private double somaTempos;
	private double receita;
	private int count;
	private int pedidosAntes12;
	private int pedidosPrazo;
	private int pedidosPrazoCount;

	public Metricas() {
		this.tempoTotal = 0;
		this.receita = 0;
		this.count = 0;
		this.pedidosAntes12 = 0;
		this.pedidosPrazo = 0;
		this.pedidosPrazoCount = 0;
	}

	public void addMetricas(double tempo, double receita, double prazo) {
		this.count++;
		this.tempoTotal += (tempo + 0.25);
		this.receita += receita;
		this.somaTempos += tempoTotal;
		
		if (tempoTotal < 240)
			this.pedidosAntes12++;
		
		if (prazo > 0) {
			this.pedidosPrazoCount++;
		if (tempoTotal <= prazo)
			this.pedidosPrazo++;
		}
	}

	public double getTempoTotal() {
		return tempoTotal;
	}
	
	public double getSomaTempos() {
		return somaTempos;
	}

	public double getReceita() {
		return receita;
	}

	public int getCount() {
		return count;
	}

	public int getPedidosAntes12() {
		return pedidosAntes12;
	}

	public int getPedidosPrazo() {
		return pedidosPrazo;
	}
	
	public int getPedidosPrazoCount() {
		return pedidosPrazoCount;
	}

}
