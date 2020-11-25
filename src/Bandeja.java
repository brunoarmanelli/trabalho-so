import java.util.ArrayList;

public class Bandeja {
	ArrayList<Integer> papeisFIFO = new ArrayList<Integer>();
	ArrayList<Integer[]> papeisEnvelhecimento = new ArrayList<Integer[]>();
	int epoca = 0;

	public boolean trocarPapelFIFO(int tipo) {

		if (papeisFIFO.size() >= 4) { //bandeja esta cheia
			if (papeisFIFO.contains(tipo)) {
				return false; //nao houve falta de pagina
			} else
				papeisFIFO.remove(0);
			papeisFIFO.add(tipo);
			return true; //houve falta de pagina
		} else { //bandeja tem espaco livre
			papeisFIFO.add(tipo);
			return false; //nao houve falta de pagina
		}
	}
	
	private int contem(int tipo) { //retorna o indice correspondente ao tipo de papel, se não existit retorna -1
		for (int i = 0; i < papeisEnvelhecimento.size(); i++) {
			if (papeisEnvelhecimento.get(i)[0] == tipo)
				return i;
		}
		return -1;
	}

	public boolean trocarPapelEnvelhecimento(int tipo) {
		if (epoca % 4 == 0 && epoca != 0) { //mudanca de epoca
			for (int i = 0; i < papeisEnvelhecimento.size(); i++) {
				papeisEnvelhecimento.get(i)[2] = papeisEnvelhecimento.get(i)[2] >> 1;
				papeisEnvelhecimento.get(i)[2] = papeisEnvelhecimento.get(i)[1] << 3 | papeisEnvelhecimento.get(i)[2];
				papeisEnvelhecimento.get(i)[1] = 0; 
			}
		}
		epoca++;

		if (papeisEnvelhecimento.size() >= 4) { //bandeja está cheia
			int indice = contem(tipo);
			if (indice != -1) {
				papeisEnvelhecimento.get(indice)[1] = 1;
				return false; //nao houve falta de pagina
			} else {

				int indiceMenor = 0;
				int valorMenor = Integer.MAX_VALUE;
				for (int i = 0; i < papeisEnvelhecimento.size(); i++) { //compara o R e CONT de cada tipo de papel
					int valor = papeisEnvelhecimento.get(i)[1] << 3 | papeisEnvelhecimento.get(i)[2] >> 1;
					if (valorMenor > valor) {
						valorMenor = valor;
						indiceMenor = i;
					}
				}
				Integer[] papel = { tipo, 1, 000 }; //tipo, R, CONT
				papeisEnvelhecimento.remove(indiceMenor);
				papeisEnvelhecimento.add(papel);

				return true; //houve falta de pagina
			}
		} else { //bandeja tem espaco livre
			Integer[] papel = { tipo, 1, 000 }; //tipo, R, CONT
			papeisEnvelhecimento.add(papel);
			return false; //nao houve falta de pagina
		}
	}
}
