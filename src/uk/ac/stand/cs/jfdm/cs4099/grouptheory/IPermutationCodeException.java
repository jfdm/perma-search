package uk.ac.stand.cs.jfdm.cs4099.grouptheory;

/**
 * Indicates the presence of Permutation Code Errors.
 * 
 * @author jfdm
 * 
 */
@SuppressWarnings("serial")
public class IPermutationCodeException extends Exception {

	/**
	 * Creates a new IPermutation Code.
	 */
	public IPermutationCodeException() {
		super();
	}

	/**
	 * Creates a new IPermutation Code Exception that displays a message to
	 * Standard Error.
	 * 
	 * @param message
	 *            The message to be sent.
	 */
	public IPermutationCodeException(String message) {
		super(message);
	}
}