package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;

public class StemmerRule extends TokenFilter {

	TokenStream stream;
	Stemmer s = new Stemmer();

	public StemmerRule(TokenStream stream) {
		super(stream);
		this.stream = stream;

		while (stream.hasNext()) {
			String token = stream.next().toString();
			System.out.println(token);
			char c[] = token.toCharArray();
			for (int i = 0; i < token.length(); i++) {
				s.add(c[i]);
			}
			s.stem();
			String u = s.toString();
			stream.replace(u);
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
