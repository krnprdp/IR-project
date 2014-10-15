package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolRule extends TokenFilter {

	TokenStream stream;

	public SymbolRule(TokenStream stream) {
		super(stream);
		this.stream = stream;

		while (stream.hasNext()) {
			String token = stream.next().toString();

			if (token.contains("'s")) {
				token = token.replaceAll("'s", "");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("won't")) {
				token = token.replaceAll("won't", "will not");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("shan't")) {
				token = token.replaceAll("shan't", "shall not");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("s'")) {
				token = token.replaceAll("'", "");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("n't")) {
				token = token.replaceAll("n't", " not");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("'t")) {
				token = token.replaceAll("'t", "");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("'m")) {
				token = token.replaceAll("'m", " am");
				token = token.trim();
				stream.replace(token);
			}
			if (token.contains("'re")) {
				token = token.replaceAll("'re", " are");
				token = token.trim();
				stream.replace(token);
			}
			if (token.contains("'ve")) {
				token = token.replaceAll("'ve", " have");
				token = token.trim();
				stream.replace(token);
			}
			if (token.contains("'d")) {
				token = token.replaceAll("'d", " would");
				token = token.trim();
				stream.replace(token);
			}
			if (token.contains("'ll")) {
				token = token.replaceAll("'ll", " will");
				token = token.trim();
				stream.replace(token);
			}

			if (token.equals("'em")) {
				token = "them";
				stream.replace(token);
			}
			if (token.contains("'")) {
				token = token.replaceAll("'", "");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("--")) {
				token = token.replaceAll("--", "");
				token = token.trim();
				stream.replace(token);
			}

			if (token.contains("-")) {
				if (token.equals("-")) {
					token = token.replaceAll("-", "");
					token = token.trim();
					stream.replace(token);
				} else {
					String[] str = token.split("-");
					int len = str[0].length();
					if (Character.isAlphabetic(str[1].charAt(0))
							&& Character.isAlphabetic(str[0].charAt(len - 1))
							&& !isNumeric(str[1])) {
						token = str[0] + " " + str[1];
						token = token.trim();
						stream.replace(token);
					}
				}

			}
			token = token.replaceAll("[!,?.]*$", "");
			stream.replace(token);
		}
		stream.reset();
	}

	private boolean isNumeric(String token) {

		Pattern p1 = Pattern.compile(".*\\d.*");
		Matcher m1 = p1.matcher(token);
		if (m1.find())
			return true;
		else
			return false;
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
