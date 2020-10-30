
public class Impressora{

	double tempoGasto;
	double lucro;

	public Impressora(Pedido pedido) {
		this.tempoGasto = pedido.paginas / 80.0;
		this.lucro = pedido.paginas * pedido.precoPagina;
	}

}
