package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;

public class CapitalizationRule extends TokenFilter {

	TokenStream ts;
	Token t1, t2;
	int i = 0;
	String s1, s2;

	public CapitalizationRule(TokenStream stream) {
		super(stream);
		this.ts = stream;

		while (stream.hasNext()) {

			s1 = stream.next().toString();
			i++;
			if (i != stream.size) {
				s2 = stream.tlist.get(i);
			}
			// s2 = stream.tokenList.get(i);
			System.out.println(s1);
			System.out.println(s2);

			if (s1.contains("\'")) {
				// Do nothing
			} else if (s1.equals(s1.toUpperCase())) {
				// All caps, so do nothing
			} else if (Character.isUpperCase(s1.charAt(0))
					&& Character.isUpperCase(s2.charAt(0))) {
				if (i == stream.size) {
					// Do nothing for last word
				} else {
					String s3 = s1 + " " + s2;
					stream.replace(s3);
					stream.next();
					stream.remove();
				}

			} else if (Character.isUpperCase(s1.charAt(0))
					&& (s1.substring(1).equals(s1.substring(1).toLowerCase()))) {
				s1 = s1.toLowerCase();
				stream.replace(s1);
			}

		}

	}

	@Override
	public boolean increment() throws TokenizerException {
		return false;
	}

	@Override
	public TokenStream getStream() {
		return ts;
	}
}