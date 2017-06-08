package com.vumobile.shaboxbuddy.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {
	public static boolean isValidEmailAddress(final String emailAddress) {
		// a null string is invalid
		if (emailAddress == null) {
			return false;
		}

		// a string without a "@" is an invalid email address
		if (emailAddress.indexOf("@") < 0) {
			return false;
		}

		// a string without a "." is an invalid email address
		if (emailAddress.indexOf(".") < 0) {
			return false;
		}
		if (emailAddress.endsWith(".")) {
			return false;
		}
		return lastEmailFieldTwoCharsOrMore(emailAddress) != false;

	}

	/**
	 * Returns true if the last email field (i.e., the country code, or
	 * something like .com, .biz, .cc, etc.) is two chars or more in length,
	 * which it really must be to be legal.
	 */
	private static boolean lastEmailFieldTwoCharsOrMore(
			final String emailAddress) {
		if (emailAddress == null) {
			return false;
		}
		final StringTokenizer st = new StringTokenizer(emailAddress, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		return lastToken.length() >= 2;
	}

	public static boolean validateEmail(final String text) {

		final Pattern p = Pattern
				.compile("^[a-z][a-z0-9_.]*@[a-z][a-z0-9_.]{1,}\\.[a-z][a-z0-9_]{1,}$");

		final Matcher m = p.matcher(text);

		// check whether match is found

		final boolean matchFound = m.matches();

		final StringTokenizer st = new StringTokenizer(text, ".");

		String lastToken = null;

		while (st.hasMoreTokens()) {

			lastToken = st.nextToken();

		}

		// validate the country code
		return matchFound && lastToken.length() >= 2
				&& text.length() - 1 != lastToken.length();
	}

}
