package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenFilter;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

public class AccentRule extends TokenFilter {
	private static final String PLAIN_ASCII = "AaEeIiOoUu" + "AaEeIiOoUuYy"
			+ "AaEeIiOoUuYy" + "AaOoNn" + "AaEeIiOoUuYy" + "Aa" + "Cc" + "OoUu";
	private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
			+ "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
			+ "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
			+ "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
			+ "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
			+ "\u00C5\u00E5" + "\u00C7\u00E7" + "\u0150\u0151\u0170\u0171";
	TokenStream stream;

	public AccentRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
		
		while (stream.hasNext()) {
			String token = stream.next().toString();
			token = checkSpecialCharacters(token);
			stream.replace(token);
			System.out.println(token);
		}

	}

	private static String checkSpecialCharacters(String string) {
		String[] t = string.split("");
		char c;
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < t.length; j++) {
			c = t[j].charAt(0);
			int pos = UNICODE.indexOf(c);
			if (pos > -1) {
				sb.append(PLAIN_ASCII.charAt(pos));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	@Override
	public boolean increment() throws TokenizerException {
		return false;
	}

	@Override
	public TokenStream getStream() {
		return stream;
	}

}
