
public class Pedido {
	String nome;
	int paginas;
	Double precoPagina;
	int prazo;
	int tipoPapel;

	public Pedido(String nome, int paginas, Double precoPagina, int prazo, int tipoPapel) {
		super();
		this.nome = nome;
		this.paginas = paginas;
		this.precoPagina = precoPagina;
		this.prazo = prazo;
		this.tipoPapel = tipoPapel;
	}

	@Override
	public String toString() {
		return "Pedido [nome=" + nome + ", paginas=" + paginas + ", precoPagina=" + precoPagina + ", prazo=" + prazo
				+ ", tipoPapel=" + tipoPapel + "]";
	}

}
