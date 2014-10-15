/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.FilterRules.AccentRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.CapitalizationRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.DateRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.NumberRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.SpecialCharRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.StemmerRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.StopWordsRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.SymbolRule;

/**
 * Factory class for instantiating a given TokenFilter
 * 
 * @author nikhillo
 *
 */
public class TokenFilterFactory {
	/**
	 * Static method to return an instance of the factory class. Usually factory
	 * classes are defined as singletons, i.e. only one instance of the class
	 * exists at any instance. This is usually achieved by defining a private
	 * static instance that is initialized by the "private" constructor. On the
	 * method being called, you return the static instance. This allows you to
	 * reuse expensive objects that you may create during instantiation
	 * 
	 * @return An instance of the factory
	 */
	private static TokenFilterFactory tff = null;

	public static TokenFilterFactory getInstance() {
		if (tff == null)
			tff = new TokenFilterFactory();
		return tff;
	}

	/**
	 * Returns a fully constructed {@link TokenFilter} instance for a given
	 * {@link TokenFilterType} type
	 * 
	 * @param type
	 *            : The {@link TokenFilterType} for which the
	 *            {@link TokenFilter} is requested
	 * @param stream
	 *            : The TokenStream instance to be wrapped
	 * @return The built {@link TokenFilter} instance
	 */
	public TokenFilter getFilterByType(TokenFilterType type, TokenStream stream) {
		switch (type) {
		case DATE:
			return new DateRule(stream);
		case CAPITALIZATION:
			return new CapitalizationRule(stream);
		case ACCENT:
			return new AccentRule(stream);
		case STOPWORD:
			return new StopWordsRule(stream);
		case NUMERIC:
			return new NumberRule(stream);
		case SYMBOL:
			return new SymbolRule(stream);
		case SPECIALCHARS:
			return new SpecialCharRule(stream);
		case STEMMER:
			return new StemmerRule(stream);

		default:
			return null;
		}
	}
}
