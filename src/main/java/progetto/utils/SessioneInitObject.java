package progetto.utils;

public class SessioneInitObject {
	private String IDSessione;
	private boolean iserror;
	private String errortype;

	public SessioneInitObject() {
	}

	UserBean usr;

	public boolean isIserror() {
		return iserror;
	}

	public void setIserror(boolean iserror) {
		this.iserror = iserror;
	}

	public String getErrortype() {
		return errortype;
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}

	public UserBean getUsr() {
		return usr;
	}

	public void setUsr(UserBean usr) {
		this.usr = usr;
	}

	public String getIDSessione() {
		return IDSessione;
	}

	public void setIDSessione(String iDSessione) {
		IDSessione = iDSessione;
	}

}
