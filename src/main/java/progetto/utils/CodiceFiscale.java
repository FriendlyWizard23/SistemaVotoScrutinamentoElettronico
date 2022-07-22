package progetto.utils;

public class CodiceFiscale {
	private String cf;

	/**
	 * Normalizes a CF by removing white spaces and converting to upper-case. Useful
	 * to clean-up user's input and to save the result in the DB.
	 * 
	 * @param cf Raw CF, possibly with spaces.
	 * @return Normalized CF.
	 */
	public CodiceFiscale(String cf) {
		this.cf = cf;
	}

	private String normalize(String cf) {
		cf = cf.replaceAll("[ \t\r\n]", "");
		cf = cf.toUpperCase();
		return cf;
	}

	/**
	 * Returns the formatted CF. Currently does nothing but normalization.
	 * 
	 * @param cf Raw CF, possibly with spaces.
	 * @return Formatted CF.
	 */
	private String format() {
		return normalize(cf);
	}

	/**
	 * Validates a regular CF.
	 * 
	 * @param cf Normalized, 16 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private boolean validate_regular() {
		if (!cf.matches("^[0-9A-Z]{16}$"))
			return false;
		int s = 0;
		String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
		for (int i = 0; i < 15; i++) {
			int c = cf.charAt(i);
			int n;
			if ('0' <= c && c <= '9')
				n = c - '0';
			else
				n = c - 'A';
			if ((i & 1) == 0)
				n = even_map.charAt(n) - 'A';
			s += n;
		}
		if (s % 26 + 'A' != cf.charAt(15))
			return false;
		return true;
	}

	/**
	 * Verifies the basic syntax, length and control code of the given CF.
	 * 
	 * @param cf Raw CF, possibly with spaces.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	public boolean validate() {
		cf = normalize(cf);
		if (cf.length() == 0)
			return false;
		else if (cf.length() == 16)
			return validate_regular();
		else
			return false;
	}

}