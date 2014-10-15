package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.analysis.FilterRules.AccentRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.CapitalizationRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.DateRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.NumberRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.SpecialCharRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.StemmerRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.StopWordsRule;
import edu.buffalo.cse.irf14.analysis.FilterRules.SymbolRule;
import edu.buffalo.cse.irf14.document.FieldNames;

public class AnalyzerClass implements Analyzer {
	FieldNames fname;
	TokenStream ts;

	public AnalyzerClass(FieldNames name, TokenStream stream) {
		this.fname = name;
		this.ts = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {

		if (fname == FieldNames.AUTHOR) {
			AccentRule accentrule = new AccentRule(ts);
			ts = accentrule.getStream();
			ts.reset();
			CapitalizationRule caprule = new CapitalizationRule(ts);
			ts.reset();
			ts = caprule.getStream();
		}

		if (fname == FieldNames.CONTENT) {

			SymbolRule symrule = new SymbolRule(ts);
			ts = symrule.getStream();
			ts.reset();
			SpecialCharRule spchrule = new SpecialCharRule(ts);
			ts = spchrule.getStream();
			ts.reset();
			AccentRule accentrule = new AccentRule(ts);
			ts = accentrule.getStream();
			ts.reset();
			StopWordsRule stoprule = new StopWordsRule(ts);
			ts = stoprule.getStream();
			ts.reset();
			DateRule daterule = new DateRule(ts);
			ts = daterule.getStream();
			ts.reset();
			NumberRule numrule = new NumberRule(ts);
			ts = numrule.getStream();
			ts.reset();
			StemmerRule stemrule = new StemmerRule(ts);
			ts = stemrule.getStream();
			ts.reset();
			CapitalizationRule caprule = new CapitalizationRule(ts);
			ts = caprule.getStream();
		}

		if (fname == FieldNames.NEWSDATE) {

			SymbolRule symrule = new SymbolRule(ts);
			ts = symrule.getStream();
			ts.reset();
			SpecialCharRule spchrule = new SpecialCharRule(ts);
			ts = spchrule.getStream();
			ts.reset();
			DateRule daterule = new DateRule(ts);
			ts = daterule.getStream();
			ts.reset();
		}

		if (fname == FieldNames.PLACE) {

			SymbolRule symrule = new SymbolRule(ts);
			ts = symrule.getStream();
			ts.reset();
			SpecialCharRule spchrule = new SpecialCharRule(ts);
			ts = spchrule.getStream();
			ts.reset();
			AccentRule accentrule = new AccentRule(ts);
			ts = accentrule.getStream();
			ts.reset();
			StopWordsRule stoprule = new StopWordsRule(ts);
			ts = stoprule.getStream();
			ts.reset();
			CapitalizationRule caprule = new CapitalizationRule(ts);
			ts = caprule.getStream();
		}

		if (fname == FieldNames.TITLE) {

			SymbolRule symrule = new SymbolRule(ts);
			ts = symrule.getStream();
			ts.reset();
			SpecialCharRule spchrule = new SpecialCharRule(ts);
			ts = spchrule.getStream();
			ts.reset();
			AccentRule accentrule = new AccentRule(ts);
			ts = accentrule.getStream();
			ts.reset();
			StopWordsRule stoprule = new StopWordsRule(ts);
			ts = stoprule.getStream();
			ts.reset();
			DateRule daterule = new DateRule(ts);
			ts = daterule.getStream();
			ts.reset();
			NumberRule numrule = new NumberRule(ts);
			ts = numrule.getStream();
			ts.reset();
			StemmerRule stemrule = new StemmerRule(ts);
			ts = stemrule.getStream();
			ts.reset();
			CapitalizationRule caprule = new CapitalizationRule(ts);
			ts = caprule.getStream();

		}

		return false;
	}

	@Override
	public TokenStream getStream() {
		return ts;
	}

}
