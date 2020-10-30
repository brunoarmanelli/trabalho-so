
public class Pedido {
	String nome;
	int paginas;
	Double precoPagina;
	int prazo;
	
	public Pedido(String nome, int paginas, Double precoPagina, int prazo) {
		super();
		this.nome = nome;
		this.paginas = paginas;
		this.precoPagina = precoPagina;
		this.prazo = prazo;
	}

	@Override
	public String toString() {
		return "Pedido [nome=" + nome + ", paginas=" + paginas + ", precoPagina=" + precoPagina + ", prazo=" + prazo
				+ "]";
	}

}
