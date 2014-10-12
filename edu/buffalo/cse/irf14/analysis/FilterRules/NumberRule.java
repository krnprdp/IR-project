package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberRule extends TokenFilter {

	TokenStream stream;

	public NumberRule(TokenStream stream) {
		super(stream);
		this.stream = stream;

		while (stream.hasNext()) {
			String token = stream.next().toString();

			if (token.contains("/")) {
				token = "/";
				stream.replace("/");
			}
			if (token.contains("%")) {
				token = "%";
				stream.replace("%");
			}

			if (isNumeric(token)) {
				stream.remove();
			}

		}
		stream.reset();
	}

	private boolean isNumeric(String token) {

		Pattern p1 = Pattern.compile("\\d+");
		Pattern p2 = Pattern.compile("\\d+,\\d+");
		Pattern p3 = Pattern.compile("\\d+[.]\\d+%");
		Matcher m1 = p1.matcher(token);
		Matcher m2 = p2.matcher(token);
		Matcher m3 = p3.matcher(token);
		if (m1.find())
			return true;
		else if (m2.find())
			return true;
		else if (m3.find())
			return false;
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
