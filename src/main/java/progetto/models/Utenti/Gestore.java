package progetto.models.Utenti;

public class Gestore extends ABSUtente {

	public Gestore(String cf) {
		super(cf);
	}

	public Gestore(String cf, String pwd) {
		super(cf, pwd);
	}

	@Override
	public boolean isGestore() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isElettore() {
		// TODO Auto-generated method stub
		return false;
	}

}
