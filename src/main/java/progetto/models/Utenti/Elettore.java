package progetto.models.Utenti;

public class Elettore extends ABSUtente {

	public Elettore(String cf) {
		super(cf);
	}

	public Elettore(String cf, String pwd) {
		super(cf, pwd);
	}

	@Override
	public boolean isGestore() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isElettore() {
		// TODO Auto-generated method stub
		return true;
	}

}
