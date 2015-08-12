package nom.danielmarreco.train.exception;

/**
 * Thrown when an invalid data is fed to the system
 * @author Daniel Marreco
 *
 */
public class InvalidArgumentException extends Exception {
	

	private static final long serialVersionUID = -5150518183752800123L;

	/**
	 * Constructor
	 * @param arg The invalid argument
	 */
	public InvalidArgumentException (String arg) {
		super("Invalid argument: '" + arg + "'");
	}

}
