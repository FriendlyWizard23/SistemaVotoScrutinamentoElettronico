package progetto.models.Sessioni;

public class SessioneVoto {
	/*
	 * @ invariant tipologiaVoto.equals("referednum") -> domandaReferendum != null;@
	 */
	private String /* @ non_null; spec_public @ */ IDSessione;
	private String /* @ non_null; spec_public @ */ tipologiaVoto;
	private String /* @ non_null; spec_public @ */ strategiaVincitore;
	private boolean /* @ non_null; spec_public @ */ status;
	private String domandaReferendum = null;
	private int numero_si = 0;
	private int numero_no = 0;
	private boolean /* @ non_null; spec_public @ */ isnew;

	public SessioneVoto(String iDSessione, String tipologiaVoto, String strategiaVincitore, String domandaReferendum) {
		super();
		this.IDSessione = iDSessione;
		this.tipologiaVoto = tipologiaVoto;
		this.strategiaVincitore = strategiaVincitore;
		this.domandaReferendum = domandaReferendum;
		status = false;
		isnew = true;
	}

	public SessioneVoto(String iDSessione, String tipologiaVoto, String strategiaVincitore, String domandaReferendum,
			int numero_si, int numero_no, boolean status, boolean isnew) {
		super();
		this.IDSessione = iDSessione;
		this.tipologiaVoto = tipologiaVoto;
		this.strategiaVincitore = strategiaVincitore;
		this.domandaReferendum = domandaReferendum;
		this.numero_si = numero_si;
		this.numero_no = numero_no;
		this.status = status;
		isnew = true;
	}

	public SessioneVoto(String iDSessione, String tipologiaVoto, String strategiaVincitore) {
		super();
		this.IDSessione = iDSessione;
		this.tipologiaVoto = tipologiaVoto;
		this.strategiaVincitore = strategiaVincitore;
		status = false;
		isnew = true;
	}

	/**
	 * Avvia la sessione modificando il flag status a true e sporcando il flag isnew
	 */
	public void start() {
		status = true;
		isnew = false;
	}

	/**
	 * Ferma la sessione modificando il flag status a false
	 */
	public void stop() {
		status = false;
	}

	/**
	 * Restituisce lo stato della sessione nuova
	 * 
	 * @return true se la sessione e' nuova, false altrimenti
	 */
	public boolean isNew() {
		return isnew;
	}

	/**
	 * restituisce lo stato della sessione (true = attiva, false = disattivata)
	 * 
	 * @return true se la sessione e' ancora attiva
	 */
	public boolean isAlive() {
		return status;
	}

	/**
	 * restituisce la domanda del referendum
	 * 
	 * @return domanda referendum
	 */
	public String getDomandaReferendum() {
		return domandaReferendum;
	}

	/**
	 * restituisce il numero di si alla domanda del referendum
	 * 
	 * @return numero di si alla domanda del referendum
	 */
	public int getNumeroSi() {
		return numero_si;
	}

	/**
	 * restituisce il numero di no alla domanda del referendum
	 * 
	 * @return numero di no alla domanda del referendum
	 */
	public int getNumeroNo() {
		return numero_no;
	}

	/**
	 * Setter della variabile numero_si
	 * 
	 * @param numero_si
	 */
	public void setNumeroSi(int numero_si) {
		this.numero_si = numero_si;
	}

	/**
	 * Setter della variabile numero_no
	 * 
	 * @param numero_no
	 */
	public void setNumeroNo(int numero_no) {
		this.numero_no = numero_no;
	}

	public String getStrategiaVincitore() {
		return strategiaVincitore;
	}

	public String getTipologiaVoto() {
		return tipologiaVoto;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getIsnew() {
		return isnew;
	}

	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}

	public String getIDSessione() {
		return IDSessione;
	}

	public void setIDSessione(String iDSessione) {
		IDSessione = iDSessione;
	}

	@Override
	public String toString() {
		return "SessioneVoto [IDSessione=" + IDSessione + ", tipologiaVoto=" + tipologiaVoto + ", strategiaVincitore="
				+ strategiaVincitore + ", status=" + status + ", domandaReferendum=" + domandaReferendum
				+ ", numero_si=" + numero_si + ", numero_no=" + numero_no + ", isnew=" + isnew + "]";
	}

}
