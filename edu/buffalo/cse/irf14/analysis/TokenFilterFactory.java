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
	public static TokenFilterFactory getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		return null;
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
		TokenFilter tf = null;
		switch (type) {
		case DATE:
			tf = new DateRule(stream);
			break;
		case CAPITALIZATION:
			tf = new CapitalizationRule(stream);
			break;
		case ACCENT:
			tf = new AccentRule(stream);
			break;
		case STOPWORD:
			tf = new StopWordsRule(stream);
			break;
		case NUMERIC:
			tf = new NumberRule(stream);
			break;
		case SYMBOL:
			tf = new SymbolRule(stream);
			break;
		case SPECIALCHARS:
			tf = new SpecialCharRule(stream);
			break;
		case STEMMER:
			tf = new StemmerRule(stream);
		default:
			break;
		}
		return tf;
	}
}
