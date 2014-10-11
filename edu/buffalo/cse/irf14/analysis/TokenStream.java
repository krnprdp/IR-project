/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author nikhillo Class that represents a stream of Tokens. All
 *         {@link Analyzer} and {@link TokenFilter} instances operate on this to
 *         implement their behavior
 */
public class TokenStream implements Iterator<Token> {

	ArrayList<String> list = new ArrayList<String>();
	int size, i = 0, pointer = 0;

	public TokenStream(StringTokenizer st) {
		this.size = st.countTokens();
		while (i < size) {
			i++;
			while (st.hasMoreElements()) {
				list.add(st.nextToken());
			}
		}
	}

	/**
	 * Method that checks if there is any Token left in the stream with regards
	 * to the current pointer. DOES NOT ADVANCE THE POINTER
	 * 
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {

		if (list != null && !list.isEmpty()) {
			if (pointer >= 0 && pointer < size) {
				if (list.get(pointer) != null && list.get(pointer) != "")
					return true;
			}
		}
		return false;
	}

	/**
	 * Method to return the next Token in the stream. If a previous hasNext()
	 * call returned true, this method must return a non-null Token. If for any
	 * reason, it is called at the end of the stream, when all tokens have
	 * already been iterated, return null
	 */
	@Override
	public Token next() {
		Token t;
		if (!(pointer == size)) {
			t = new Token();
			t.setTermText(list.get(pointer++));
			return t;
		}
		return null;
	}

	/**
	 * Method to remove the current Token from the stream. Note that "current"
	 * token refers to the Token just returned by the next method. Must thus be
	 * NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		if (pointer != 0 && pointer < size) {
			list.remove(pointer--);
			size--;
		}
	}

	/**
	 * Method to reset the stream to bring the iterator back to the beginning of
	 * the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		if (list != null) {
			pointer = 0;
		}
	}

	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the
	 * iterator currently stands. After appending, the iterator pointer must be
	 * unchanged Of course this means if the iterator was at the end of the
	 * stream and a new stream was appended, the iterator hasn't moved but that
	 * is no longer the end of the stream.
	 * 
	 * @param stream
	 *            : The stream to be appended
	 */
	public void append(TokenStream stream) {
		// if (stream != null&&!stream.list.isEmpty()) {
		// for (int i = 0; i < stream.list.size(); i++) {
		// list.add(stream.list.get(i));
		// }
		// }
		if (stream != null && !stream.list.isEmpty()) {
			for (int i = 0; i < stream.list.size(); i++) {
				if (stream.list.get(i) != null) {
					list.add(stream.list.get(i));
					size++;
				}
			}
		}
	}

	/**
	 * Method to get the current Token from the stream without iteration. The
	 * only difference between this method and {@link TokenStream#next()} is
	 * that the latter moves the stream forward, this one does not. Calling this
	 * method multiple times would not alter the return value of
	 * {@link TokenStream#hasNext()}
	 * 
	 * @return The current {@link Token} if one exists, null if end of stream
	 *         has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		if (pointer > 0) {
			Token t = new Token();
			String s = list.get(--pointer).trim();
			t.setTermText(s);
			return t;
		}
		return null;
	}

	public void replace(String s) {
		list.set(pointer, s);
	}

}
