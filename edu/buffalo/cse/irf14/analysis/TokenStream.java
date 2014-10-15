/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @author nikhillo Class that represents a stream of Tokens. All
 *         {@link Analyzer} and {@link TokenFilter} instances operate on this to
 *         implement their behavior
 */
public class TokenStream implements Iterator<Token> {

	public List<Token> tlist = new ArrayList<Token>();
	Iterator<Token> itr;
	public Token t;
	public int i, size = 0, pointer = 0;

	public TokenStream(Token[] t) {
		size = t.length;
		for (i = 0; i < size; i++) {
			tlist.add(t[i]);
			// System.out.println(tlist.get(i).getTermText());
		}
		itr = tlist.iterator();
	}

	/**
	 * Method that checks if there is any Token left in the stream with regards
	 * to the current pointer. DOES NOT ADVANCE THE POINTER
	 * 
	 * @return true if at least one Token exists, false otherwise
	 */
	@Override
	public boolean hasNext() {

		if (pointer < size)
			return itr.hasNext();

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
		try {
			if (hasNext() && pointer < size) {
				t = itr.next();
				pointer++;
			} else
				t = null;
		} catch (ConcurrentModificationException e) {

		}

		return t;
	}

	/**
	 * Method to remove the current Token from the stream. Note that "current"
	 * token refers to the Token just returned by the next method. Must thus be
	 * NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		try {
			itr.remove();
			pointer--;
			size--;
		} catch (Exception e) {
		}
		t = null;
	}

	/**
	 * Method to reset the stream to bring the iterator back to the beginning of
	 * the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		itr = null;
		pointer = 0;
		itr = tlist.iterator();

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
		try {
			if (stream != null) {
				for (int i = 0; i < stream.size; i++) {
					tlist.add(stream.tlist.get(i));
				}
				size = size + stream.size;
				int p = pointer;
				reset();
				int p2 = 0;
				t = null;
				while (itr.hasNext() && p2 < p) {
					p2++;
					t = itr.next();
					// System.out.println(p2);
				}

			}
		} catch (ConcurrentModificationException e) {

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
		return t;
	}

	public void replace(String s) {
		Token t1 = new Token(s);
		tlist.set(pointer - 1, t1);
	}
}
