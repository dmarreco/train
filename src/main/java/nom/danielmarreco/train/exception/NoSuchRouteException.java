package nom.danielmarreco.train.exception;


/**
 * Thrown when a route between given cities is inexistent
 * 
 * @author Daniel Marreco
 *
 */
public class NoSuchRouteException extends Exception
{
	private static final long serialVersionUID = 7917986841874042435L;

	/**
	 * @see Exception#Exception()
	 */
	public NoSuchRouteException () {
		super();
	}
	
	/**
	 * @see Exception#Exception(String)
	 */
	public NoSuchRouteException (String message) {
		super(message);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public NoSuchRouteException (String message, Throwable cause) {
		super(message, cause);
	}
}
