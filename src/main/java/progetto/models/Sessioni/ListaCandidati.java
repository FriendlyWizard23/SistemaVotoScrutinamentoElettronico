package progetto.models.Sessioni;

import java.util.ArrayList;
import java.util.List;

public class ListaCandidati {
	private Partito partitolista;
	private int idlista;
	private List<Candidato> candidati;

	public ListaCandidati(Partito partitolista, List<Candidato> candidati, int idlista) {
		this.partitolista = partitolista;
		candidati = new ArrayList<Candidato>(candidati);
		this.idlista = idlista;
	}

	public ListaCandidati(Partito partitolista) {
		this.partitolista = partitolista;
		candidati = new ArrayList<Candidato>();
	}

	/**
	 * Aggiunge un candidato alla lista
	 * 
	 * @param candidato candidato da inserire
	 */
	public void addCandidato(Candidato candidato) {
		candidati.add(candidato);
	}

	/**
	 * rimuove un candidato dalla lista
	 * 
	 * @param candidato da rimuovere dalla lista
	 */
	public void removeCandidato(Candidato candidato) {
		int i = 0;
		if (!candidati.contains(candidato))
			return;
		for (Candidato c : candidati) {
			if (c.equals(candidato)) {
				candidati.remove(i);
				break;
			}
			i++;
		}
	}

	public int getIdLista() {
		return idlista;
	}

	public void setIdLista(int idlista) {
		this.idlista = idlista;
	}

	/**
	 * Si ritorna una nuova lista di candidati per evitare di modificare la
	 * rappresentazione interna
	 * 
	 * @return lista di candidati
	 */
	public List<Candidato> getCandidati() {
		return new ArrayList<Candidato>(candidati);
	}

	/**
	 * restituisce il partito della lista
	 * 
	 * @return partito della lista
	 */
	public Partito getPartito() {
		return partitolista;
	}

	@Override
	public String toString() {
		return "LISTA [ " + idlista + "] ==> PARTITO=" + partitolista.getNome() + ", CANDIDATI={" + niceCandidati()
				+ "}";
	}

	private String niceCandidati() {
		StringBuilder strbld = new StringBuilder();
		for (Candidato c : candidati) {
			strbld.append(c.toString() + " ");
		}
		return strbld.toString();
	}

}
