package nom.danielmarreco.train;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nom.danielmarreco.train.exception.InvalidArgumentException;

/**
 * This class is used to parse the input data representing a set of cities and routes between them (a graph instance)
 * and build an instance of such data in the format of a {@link World} object.
 * 
 * @see World
 */
public class WorldBuilder
{

	/**
	 * The mask expected for the string representing a route in the system.
	 * 
	 * These strings are a simplified notation, passed as parameters or fed as data to the program.
	 */
	private static final Pattern ROUTE_NOTATION = Pattern.compile("([A-Z])([A-Z])([0-9]*)");
	
	/**
	 * The World to be build
	 * 
	 * -- "... and in the first day, where there was only darkness, He created light" =)
	 */
	private World world;
	
	public WorldBuilder ()
	{
		reset();
	}
	
	/**
	 * Resets the builder, undoing all routes already added
	 */
	private void reset()
	{
		this.world = new World();
	}


	/**
	 * Adds a route in the expected format from one city to another with the gien distance, creating {@link City} instances as needed and adding
	 * them to the newly created {@link World}.
	 * 
	 * @param arg An argument representing a route, in the format "([A-Z])([A-Z])([0-9]*)". 
	 * 			Ex.: 'AB4' defines a route from city A to city B with a distance of 4
	 * @return An instance of this WorldBuilder object
	 * @throws InvalidArgumentException In the case the argument is not in the format given above, or the distance exceeds {@link Integer#MAX_VALUE}
	 */
	public WorldBuilder addRoute(String arg) throws InvalidArgumentException
	{
		Matcher m = ROUTE_NOTATION.matcher(arg);
		
		if (! m.matches()) {
			throw new InvalidArgumentException (arg);
		}

		String cityFromName = m.group(1);
		String cityToName = m.group(2);
		
		City cityFrom, cityTo;
		
		if ((cityTo = world.getCity(cityToName)) == null) {
			cityTo = world.addCity(cityToName);
		}
		
		if ((cityFrom = world.getCity(cityFromName)) == null) {
			cityFrom = world.addCity(cityFromName);
		}
		
		try {
			Integer distance = Integer.parseInt(m.group(3));
			cityFrom.addAdjacentCity(cityTo, distance);
		}
		catch (NumberFormatException nfe) {
			/*
			 * in theory it is protected by the regex to be a valid number, 
			 * but there may still be a problem if distance exceeds Integer.MAX_VALUE
			 */
			throw new InvalidArgumentException (arg);
		}
		
		return this; 
	}

	/**
	 * Returns an instance of the newly created world with all the routes previously added to it
	 * <b>and resets the builder</b>
	 * 
	 * @return An instance of the newly created world with all the routes previously added to it
	 */
	public World build()
	{
		World buf = this.world;
		reset();
		return buf;
	}

}
