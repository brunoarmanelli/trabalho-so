
public class Metricas {
	private double tempoTotal1;
	private double somaTempos1;
	private double tempoTotal2;
	private double somaTempos2;
	private double tempoTotal3;
	private double somaTempos3;
	private double receita;
	private int count;
	private int pedidosAntes12;
	private int pedidosPrazo;
	private int pedidosPrazoCount;
	private int faltasPaginaFIFO;
	private int faltasPaginaEnvelhecimento;

	public Metricas() {
		this.tempoTotal1 = 0;
		this.somaTempos1 = 0;
		this.tempoTotal2 = 0;
		this.somaTempos2 = 0;
		this.tempoTotal3 = 0;
		this.somaTempos3 = 0;
		this.receita = 0;
		this.count = 0;
		this.pedidosAntes12 = 0;
		this.pedidosPrazo = 0;
		this.pedidosPrazoCount = 0;
		this.faltasPaginaFIFO = 0;
		this.faltasPaginaEnvelhecimento = 0;
	}

	public void addMetricas(double tempo, double receita, double prazo, int faltasDePaginaFIFO,
			int faltasPaginaEnvelhecimento, String nome) {
		if (nome.equals("Impressora 1")) {
			this.tempoTotal1 += (tempo + 0.25);
			this.somaTempos1 += tempoTotal1;
		}
		if (nome.equals("Impressora 2")) {
			this.tempoTotal2 += (tempo + 0.25);
			this.somaTempos2 += tempoTotal2;
		}
		if (nome.equals("Impressora 3")) {
			this.tempoTotal3 += (tempo + 0.25);
			this.somaTempos3 += tempoTotal3;
		}
		this.count++;
		this.receita += receita;
		this.faltasPaginaFIFO += faltasDePaginaFIFO;
		this.faltasPaginaEnvelhecimento += faltasPaginaEnvelhecimento;

		if (getTempoTotal() < 240)
			this.pedidosAntes12++;

		if (prazo > 0) {
			this.pedidosPrazoCount++;
			if (getTempoTotal() <= prazo)
				this.pedidosPrazo++;
		}
	}

	public double getTempoTotal() {
		if (tempoTotal1 >= tempoTotal2 && tempoTotal1 >= tempoTotal3)
			return tempoTotal1;
		if (tempoTotal2 >= tempoTotal1 && tempoTotal2 >= tempoTotal3)
			return tempoTotal2;
		if (tempoTotal3 >= tempoTotal1 && tempoTotal3 >= tempoTotal2)
			return tempoTotal3;
		return -1;
	}

	public double getSomaTempos() {
		return somaTempos1 + somaTempos2 + somaTempos3;
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

	public int getFaltasPaginaFIFO() {
		return faltasPaginaFIFO;
	}

	public int getFaltasPaginaEnvelhecimento() {
		return faltasPaginaEnvelhecimento;
	}
}
