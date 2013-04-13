package uk.ac.stand.cs.jfdm.cs4099.search;

/**
 * Used to indicate the presence of Search related errors.
 * 
 * @author jfdm
 * @version 1
 * 
 */
@SuppressWarnings("serial")
public class SearchException extends Exception {
	/**
	 * Creates a new Search Exception.
	 */
	public SearchException() {
		super();
	}

	/**
	 * Creates a new Search Exception that displays a message to Standard Error.
	 * 
	 * @param message
	 *            The message to be sent.
	 */
	public SearchException(String message) {
		super(message);
	}
}
