package progetto.models.Utenti;

public abstract class ABSUtente {
	private String cf;
	private String pwd;

	public ABSUtente(String cf) {

		this.cf = cf;
	}

	public ABSUtente(String cf, String pwd) {
		this.cf = cf;
		this.pwd = pwd;
	}

	public String getPwd() {
		return this.pwd;
	}

	public String getCF() {
		return this.cf;
	}

	public abstract boolean isGestore();

	public abstract boolean isElettore();

	@Override
	public String toString() {
		return "ABSUtente [cf=" + cf + ", pwd=" + pwd + "]" + "-->{ELETTORE: " + this.isElettore() + ", GESTORE "
				+ this.isGestore() + "}";
	}

}