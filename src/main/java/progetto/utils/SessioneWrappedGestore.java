package progetto.utils;

public class SessioneWrappedGestore {
	SessioneInitObject sio;
	String tomodify;
	String tocreate;

	public SessioneWrappedGestore(SessioneInitObject sio, String tomodify, String tocreate) {
		super();
		this.sio = sio;
		this.tomodify = tomodify;
		this.tocreate = tocreate;
	}

	public SessioneWrappedGestore() {
		super();
	}

	public SessioneWrappedGestore(SessioneInitObject sio) {
		super();
		this.sio = sio;
	}

	public SessioneInitObject getSio() {
		return sio;
	}

	public void setSio(SessioneInitObject sio) {
		this.sio = sio;
	}

	public String getTomodify() {
		return tomodify;
	}

	public void setTomodify(String tomodify) {
		this.tomodify = tomodify;
	}

	public String getTocreate() {
		return tocreate;
	}

	public void setTocreate(String tocreate) {
		this.tocreate = tocreate;
	}

}
