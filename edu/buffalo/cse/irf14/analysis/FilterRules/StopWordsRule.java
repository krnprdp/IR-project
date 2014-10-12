package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;

public class StopWordsRule extends TokenFilter {

	TokenStream ts;
	String[] stopwords = { "this", "is", "a", "do", "not", "of" };
	String s1;

	public StopWordsRule(TokenStream stream) {
		super(stream);
		this.ts = stream;
		while (stream.hasNext()) {
			s1 = stream.next().toString();
			for (int i = 0; i < stopwords.length; i++) {
				if (stopwords[i].equals(s1))
					stream.remove();
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
