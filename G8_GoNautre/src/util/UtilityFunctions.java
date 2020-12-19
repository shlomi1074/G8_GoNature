package util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UtilityFunctions {

	/* Check for valid email
	 * Valid Email -> xxxx@xxxxx */
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	/* Check whether a string consists of only numbers */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
