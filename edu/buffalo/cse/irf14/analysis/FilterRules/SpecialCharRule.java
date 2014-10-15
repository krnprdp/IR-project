package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;

public class SpecialCharRule extends TokenFilter {

	TokenStream stream;

	public SpecialCharRule(TokenStream stream) {
		super(stream);
		this.stream = stream;

		while (stream.hasNext()) {
			String token = stream.next().toString();

			token = symbol(token);

			if (token == "")
				stream.remove();
			else
				stream.replace(token);

			System.out.println(token);
		}

	}

	private String symbol(String input) {
		String punctuation = " !\"',;:.-_?)([]<>*#~$@";
		char[] c = input.toCharArray();
		int length = c.length;
		int start = 0;

		if (input.equals("^") || input.equals("&") || input.equals("-")
				|| input.equals("-") || input.equals("=") || input.equals("*")
				|| input.equals("%") || input.equals("|") || input.equals("/")
				|| input.equals("\\")) {
			return "";
		}

		if (input.contains("%")) {
			input = input.replaceAll("%", "");
			return input;
		}

		if (input.contains("*") && input.contains("^")) {
			input = input.replaceAll("\\^", "");
			input = input.replaceAll("\\*", "");
			return input;
		}

		if (input.contains("^")) {
			input = input.replaceAll("\\^", "");
			return input;
		}
		if (input.contains("*")) {
			input = input.replaceAll("\\*", "");
			return input;
		}

		if (input.contains("__/\\__")) {
			input = input.replace("__/\\__", "");
			return input;
		}

		if (input.contains("+") && input.contains("-")) {
			String result;
			result = input.replaceAll("[-+%]", "");
			return result;
		}
		if (input.contains("-")) {
			String[] str = input.split("-");
			if ((input.startsWith("-"))
					&& (Character.isLetter(input.charAt(1))))
				return input;
			if (Character.isLetter(str[1].charAt(0))) {
				return str[0].replaceAll("[+%]", "") + " "
						+ str[1].replaceAll("[+%]", "");
			}
		}

		if (input.contains("'ve")) {
			input = input.replaceAll("'ve", " have");
		}
		while (start < length && punctuation.indexOf(c[start]) > -1) {
			start++;
		}
		while (start < length && punctuation.indexOf(c[length - 1]) > -1) {
			length--;
		}
		if (start > 0 || length < c.length) {
			return input.substring(start, length).replace("@", "")
					.replace(" 've", " have");
		} else {
			return input.replace("@", "").replace(" 've", " have");
		}
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
