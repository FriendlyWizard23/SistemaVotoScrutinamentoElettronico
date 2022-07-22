package progetto.utils;

import java.util.Objects;

/**
 * This class is a java Bean for a user. The user consists of a tax code and a
 * password Java beans are classes that encapsulate one or more objects into a
 * single standardized object (the bean). This standardization allows the beans
 * to be handled in a more generic fashion, allowing easier code reuse and
 * introspection. This in turn allows the beans to be treated as software
 * components, and to be manipulated visually by editors and IDEs without
 * needing any initial configuration, or to know any internal implementation
 * details.
 * 
 * @author Alessandro
 *
 */

public class UserBean {
	private String codiceFiscale;
	private String password;
	private boolean role; // true se gestore, false altrimenti

	public UserBean() {

	}

	public UserBean(String codiceFiscale, String password) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.password = password;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public boolean getRole() {
		return role;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User:[codiceFiscale=" + codiceFiscale + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(password, other.password);
	}

}
